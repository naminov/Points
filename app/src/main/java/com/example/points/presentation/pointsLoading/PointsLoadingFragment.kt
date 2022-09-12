package com.example.points.presentation.pointsLoading

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.example.points.app.App
import com.example.points.databinding.PointsLoadingFragmentBinding
import com.example.points.presentation.extension.hideKeyboard
import com.google.android.material.snackbar.Snackbar
import javax.inject.Inject

class PointsLoadingFragment : Fragment() {

    private var _binding: PointsLoadingFragmentBinding? = null
    private val binding get() = _binding!!

    @Inject
    lateinit var viewModelFactory: PointsLoadingViewModelFactory
    private val viewModel: PointsLoadingViewModel by viewModels { viewModelFactory }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        (context?.applicationContext as App).appComponent.inject(this)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = PointsLoadingFragmentBinding.inflate(inflater, container, false)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewLifecycleOwner.lifecycleScope.launchWhenCreated {
            viewModel.state.collect { handleState(it) }
        }

        viewLifecycleOwner.lifecycleScope.launchWhenCreated {
            viewModel.action.collect { handleAction(it) }
        }

        binding.loadBtn.setOnClickListener {
            viewLifecycleOwner.lifecycleScope.launchWhenCreated {
                viewModel.event.emit(UiEvent.OnLoadClick)
            }
        }

        binding.countEt.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable) {}

            override fun beforeTextChanged(
                s: CharSequence, start: Int,
                count: Int, after: Int
            ) {
            }

            override fun onTextChanged(
                s: CharSequence, start: Int,
                before: Int, _count: Int
            ) {
                viewLifecycleOwner.lifecycleScope.launchWhenCreated {
                    viewModel.event.emit(UiEvent.OnCountChange(s.toString()))
                }
            }
        }
        )
    }

    private fun handleState(state: UiState) {
        binding.progress.isVisible = state.loading
        binding.countEt.isVisible = !state.loading
        binding.loadBtn.isVisible = !state.loading
        binding.loadBtn.isEnabled = state.loadingAvailable

        binding.countEt.setText(state.count)
        binding.countEt.setSelection(state.count.length)
    }

    private fun handleAction(action: UiAction) {
        when (action) {
            is UiAction.NavigateToShowing -> handleActionNavigateToShowing(action)
            is UiAction.ShowMessage -> handleActionShowMessage(action)
            is UiAction.HideKeyboard -> handleActionHideKeyboard()
        }
    }

    private fun handleActionNavigateToShowing(action: UiAction.NavigateToShowing) {
        findNavController()
            .navigate(
                PointsLoadingFragmentDirections
                    .actionToShowing(action.points.toTypedArray())
            )
    }

    private fun handleActionShowMessage(action: UiAction.ShowMessage) {
        Snackbar
            .make(binding.root, action.messageId, Snackbar.LENGTH_SHORT)
            .show()
    }

    private fun handleActionHideKeyboard() {
        hideKeyboard()
    }

    override fun onDestroyView() {
        _binding = null
        super.onDestroyView()
    }
}