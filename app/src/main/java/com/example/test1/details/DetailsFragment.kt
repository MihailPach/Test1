package com.example.mvvm.details

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.navArgs
import coil.load
import com.example.mvvm.R
import com.example.mvvm.databinding.FragmentDetailsBinding
import com.example.mvvm.extension.applyWindowInsets
import com.google.android.material.snackbar.Snackbar
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.koin.core.parameter.parametersOf

class DetailsFragment : Fragment() {
    private var _binding: FragmentDetailsBinding? = null
    private val binding get() = requireNotNull(_binding)

    private val args by navArgs<DetailsFragmentArgs>()
    private val viewModel by viewModel<DetailsViewModel> {
        parametersOf(args.username)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return FragmentDetailsBinding.inflate(inflater, container, false)
            .also { _binding = it }
            .root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        with(binding) {
            appBar.applyWindowInsets()
            viewModel
                .detailsFlow
                .onEach { lceState ->
                    lceState.onContent { userDetails ->
                        login.text = userDetails.login
                        following.text =
                            getString(R.string.user_details_following, userDetails.following)
                        followers.text =
                            getString(R.string.user_details_followers, userDetails.followers)
                        imageDetails.load(userDetails.avatarUrl)
                    }
                        .onError { Snackbar.LENGTH_SHORT }
                }
                .launchIn(viewLifecycleOwner.lifecycleScope)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}