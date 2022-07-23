package com.ba6ba.sadapaycasestudy.home.presentation

import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import com.ba6ba.sadapaycasestudy.BaseViewHolder
import com.ba6ba.sadapaycasestudy.databinding.HomeListItemBinding
import com.ba6ba.sadapaycasestudy.home.data.HomeItemUiData
import com.ba6ba.sadapaycasestudy.managers.inflater

class HomeItemAdapter(val itemClickListener: (String) -> Unit) :
    PagingDataAdapter<HomeItemUiData, HomeItemViewHolder>(HomeItemDiffUtil) {

    override fun onBindViewHolder(holder: HomeItemViewHolder, position: Int) {
        getItem(position)?.let { data ->
            holder.onBind(data, position)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HomeItemViewHolder {
        return HomeItemViewHolder(
            HomeListItemBinding.inflate(parent.inflater, parent, false),
            itemClickListener
        )
    }
}

class HomeItemViewHolder(
    private val binding: HomeListItemBinding,
    private val itemClickListener: (String) -> Unit
) :
    BaseViewHolder<HomeItemUiData>(binding.root) {
    override fun onBind(item: HomeItemUiData, position: Int) {
        binding.run {
            uiData = item
            rootItem.setOnClickListener {
                itemClickListener(item.metadata.repoUrl)
            }
        }
    }
}

object HomeItemDiffUtil : DiffUtil.ItemCallback<HomeItemUiData>() {

    override fun areItemsTheSame(oldItem: HomeItemUiData, newItem: HomeItemUiData): Boolean {
        return oldItem.metadata.id == newItem.metadata.id
    }

    override fun areContentsTheSame(oldItem: HomeItemUiData, newItem: HomeItemUiData): Boolean {
        return oldItem == newItem
    }
}