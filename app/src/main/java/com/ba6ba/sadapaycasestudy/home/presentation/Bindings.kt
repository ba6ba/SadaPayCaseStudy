package com.ba6ba.sadapaycasestudy.home.presentation

import android.graphics.drawable.Drawable
import android.view.View
import android.widget.ImageView
import androidx.appcompat.widget.AppCompatImageView
import androidx.core.content.ContextCompat
import androidx.core.content.res.ResourcesCompat
import androidx.core.view.isVisible
import androidx.databinding.BindingAdapter
import androidx.paging.LoadStateAdapter
import androidx.paging.PagingData
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import coil.load
import coil.request.CachePolicy
import coil.request.ImageRequest
import coil.transform.CircleCropTransformation
import coil.transform.RoundedCornersTransformation
import coil.transition.CrossfadeTransition
import com.ba6ba.sadapaycasestudy.R
import com.ba6ba.sadapaycasestudy.home.data.HomeItemUiData
import com.ba6ba.sadapaycasestudy.managers.ViewState

@BindingAdapter(value = ["toggle_visibility"])
fun View.toggleVisibility(value: Boolean) {
    isVisible = value
}

@BindingAdapter(value = ["show_on_loading"])
fun View.showOnLoading(viewState: ViewState<*>) {
    toggleVisibility(viewState is ViewState.Loading)
}

@BindingAdapter(value = ["show_on_error"])
fun View.showOnError(viewState: ViewState<*>) {
    toggleVisibility(viewState is ViewState.Error)
}

@BindingAdapter(value = ["show_on_success"])
fun View.showOnSuccess(viewState: ViewState<*>) {
    toggleVisibility(viewState is ViewState.Success<*>)
}

@BindingAdapter(value = ["image_drawable_resource"])
fun AppCompatImageView.updateImageDrawable(imageDrawableResource: Int) {
    setImageResource(imageDrawableResource)
}

@BindingAdapter(value = ["load_circular_image"])
fun ImageView.loadCircularImage(url: String?) {
    load(url) {
        placeholder(ContextCompat.getDrawable(context, R.drawable.circle_icon_ripple_bg))
        error(drawable = ContextCompat.getDrawable(context, R.drawable.circle_icon_ripple_bg))
        fallback(drawable = ContextCompat.getDrawable(context, R.drawable.circle_icon_ripple_bg))
        crossfade(true)
        transition(CrossfadeTransition())
        target(
            onSuccess = { drawable ->
                setImageDrawable(drawable)
            },
            onError = { errorDrawable ->
                setImageDrawable(errorDrawable)
            },
            onStart = { placeholder ->
                setImageDrawable(placeholder)
            }
        )
        transformations(CircleCropTransformation())
    }.isDisposed
}

@BindingAdapter(value = ["set_paging_adapter"])
fun RecyclerView.setPagingAdapter(homeItemAdapter: HomeItemAdapter) {
    homeItemAdapter.stateRestorationPolicy = RecyclerView.Adapter.StateRestorationPolicy.ALLOW
    adapter = homeItemAdapter.withLoadStateFooter(HomeLoadStateAdapter())
}

@BindingAdapter(value = ["add_item_decoration"])
fun RecyclerView.addItemDecoration(drawable: Drawable? = null) {
    addItemDecoration(DividerItemDecoration(context, DividerItemDecoration.VERTICAL))
}

@BindingAdapter(value = ["on_refresh"])
fun SwipeRefreshLayout.onRefreshCallback(onRefresh: () -> Unit) {
    setOnRefreshListener {
        onRefresh()
        isRefreshing = false
    }
}