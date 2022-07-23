package com.ba6ba.sadapaycasestudy.home.presentation

import android.view.ViewGroup
import androidx.paging.LoadState
import androidx.paging.LoadStateAdapter
import com.ba6ba.sadapaycasestudy.BaseViewHolder
import com.ba6ba.sadapaycasestudy.databinding.HomeLoadStateItemBinding
import com.ba6ba.sadapaycasestudy.managers.inflater

class HomeLoadStateAdapter : LoadStateAdapter<LoadingStateViewHolder>() {

    override fun onBindViewHolder(holder: LoadingStateViewHolder, loadState: LoadState) {
        holder.onBind(loadState, 0)
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        loadState: LoadState
    ): LoadingStateViewHolder {
        return LoadingStateViewHolder(
            HomeLoadStateItemBinding.inflate(parent.inflater, parent, false)
        )
    }
}

class LoadingStateViewHolder(private val binding: HomeLoadStateItemBinding) :
    BaseViewHolder<LoadState>(binding.root) {

    override fun onBind(item: LoadState, position: Int) {
        binding.loader.toggleVisibility(item is LoadState.Loading)
    }
}