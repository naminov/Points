package com.example.points.presentation.pointsShowing

import android.Manifest
import android.content.pm.PackageManager
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.points.databinding.PointsShowingFragmentBinding
import com.example.points.presentation.adapter.PointsAdapter
import com.jjoe64.graphview.series.DataPoint
import com.jjoe64.graphview.series.LineGraphSeries

class PointsShowingFragment : Fragment() {

    private var _binding: PointsShowingFragmentBinding? = null
    private val binding get() = _binding!!

    private val viewModel: PointsShowingViewModel by viewModels()
    private val args: PointsShowingFragmentArgs by navArgs()

    private val pointAdapter = PointsAdapter()

    private val saveGraphRequestPermission =
        registerForActivityResult(
            ActivityResultContracts.RequestPermission()
        ) { isGranted: Boolean ->
            viewLifecycleOwner.lifecycleScope.launchWhenCreated {
                viewModel.event.emit(UiEvent.OnSaveGraphPermissionRequest(isGranted))
            }
        }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = PointsShowingFragmentBinding.inflate(inflater, container, false)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        if (savedInstanceState == null) {
            viewLifecycleOwner.lifecycleScope.launchWhenCreated {
                viewModel.event.emit(UiEvent.OnLoadPoints(args.points.toList()))
            }
        }

        viewLifecycleOwner.lifecycleScope.launchWhenCreated {
            viewModel.state.collect { handleState(it) }
        }

        viewLifecycleOwner.lifecycleScope.launchWhenCreated {
            viewModel.action.collect { handleAction(it) }
        }

        binding.saveGraphBtn.setOnClickListener {
            viewLifecycleOwner.lifecycleScope.launchWhenCreated {
                viewModel.event.emit(UiEvent.OnSaveGraphClick)
            }
        }

        binding.rv.layoutManager =
            LinearLayoutManager(requireActivity(), RecyclerView.VERTICAL, false)
        binding.rv.adapter = pointAdapter

        binding.graph.viewport.isScalable = true
    }

    private fun handleState(state: UiState) {
        pointAdapter.items = state.points

        val series: LineGraphSeries<DataPoint> = LineGraphSeries(
            state.points
                .map { DataPoint(it.x, it.y) }
                .toTypedArray()
        )

        binding.graph.apply {
            removeAllSeries()
            addSeries(series)
            viewport.apply {
                setMinX(getMinX(true))
                setMaxX(getMaxX(true))
                setMinY(getMinY(true))
                setMaxY(getMaxY(true))
            }
        }
    }

    private fun handleAction(action: UiAction) {
        when (action) {
            is UiAction.SaveGraph -> handleActionSaveGraph(action)
            is UiAction.SaveGraphPermissionCheck -> handleActionSaveGraphPermissionCheck()
            is UiAction.SaveGraphPermissionRequest -> handleActionSaveGraphPermissionRequest()
        }
    }

    private fun handleActionSaveGraph(action: UiAction.SaveGraph) {
        viewLifecycleOwner.lifecycleScope.launchWhenCreated {
            binding.graph.takeSnapshotAndShare(
                requireActivity(),
                getString(action.name),
                getString(action.name)
            )
        }
    }

    private fun handleActionSaveGraphPermissionCheck() {
        val granted = ContextCompat.checkSelfPermission(
            requireActivity(),
            Manifest.permission.WRITE_EXTERNAL_STORAGE
        ) == PackageManager.PERMISSION_GRANTED

        viewLifecycleOwner.lifecycleScope.launchWhenCreated {
            viewModel.event.emit(UiEvent.OnSaveGraphPermissionCheck(granted))
        }
    }

    private fun handleActionSaveGraphPermissionRequest() {
        saveGraphRequestPermission.launch(
            Manifest.permission.WRITE_EXTERNAL_STORAGE
        )
    }

    override fun onDestroyView() {
        _binding = null
        super.onDestroyView()
    }
}