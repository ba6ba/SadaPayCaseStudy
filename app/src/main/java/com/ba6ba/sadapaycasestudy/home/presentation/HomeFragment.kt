package com.ba6ba.sadapaycasestudy.home.presentation

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.ba6ba.sadapaycasestudy.R
import com.ba6ba.sadapaycasestudy.managers.dataBinding
import com.ba6ba.sadapaycasestudy.databinding.FragmentHomeBinding
import com.ba6ba.sadapaycasestudy.managers.browseUrl
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@AndroidEntryPoint
class HomeFragment : Fragment(R.layout.fragment_home) {

    private val binding: FragmentHomeBinding by dataBinding(FragmentHomeBinding::bind)
    private val homeViewModel: HomeViewModel by viewModels()
    private val homeItemAdapter by lazy {
        HomeItemAdapter(this::onItemClickListener)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        homeViewModel.setPersistedDisplayMode()
        setBindingVariables()
        listenAdapterUpdates()
    }

    private fun listenAdapterUpdates() {
        lifecycleScope.launch {
            homeViewModel.collectPagingData().collectLatest { pagingData ->
                homeItemAdapter.submitData(pagingData)
            }
        }
        homeItemAdapter.addLoadStateListener { combinedLoadStates ->
            homeViewModel.processCombinedStates(combinedLoadStates)
        }
    }

    private fun onItemClickListener(repoUrl: String) {
        requireContext().browseUrl(repoUrl)
    }

    private fun setBindingVariables() {
        binding.run {
            viewModel = homeViewModel
            adapter = homeItemAdapter
        }
    }
}