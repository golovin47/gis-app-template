package com.gis.featurecats.presentation.ui.catsscreen

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.gis.featurecats.R
import com.gis.featurecats.databinding.ItemCatsListBinding
import com.gis.featurecats.databinding.ItemLoadingCatsListBinding
import com.gis.featurecats.presentation.ui.catsscreen.CatsListItem.CatDefaultItem
import com.gis.utils.domain.ImageLoader
import io.reactivex.subjects.Subject

const val DEFAULT_ITEM = 0x001
const val LOADING_ITEM = 0x002

class CatsAdapter(
  private val eventsPublisher: Subject<CatsIntent>,
  private val imageLoader: ImageLoader
) : ListAdapter<CatsListItem, RecyclerView.ViewHolder>(object :
  DiffUtil.ItemCallback<CatsListItem>() {

  override fun areItemsTheSame(oldItem: CatsListItem, newItem: CatsListItem): Boolean =
    when {
      oldItem is CatDefaultItem && newItem is CatDefaultItem -> oldItem.id == newItem.id
      else -> oldItem === newItem
    }

  override fun areContentsTheSame(oldItem: CatsListItem, newItem: CatsListItem): Boolean =
    oldItem == newItem

}), ItemTouchHelperAdapter {

  var curPage = 1

  override fun onFailedToRecycleView(holder: RecyclerView.ViewHolder): Boolean = true

  override fun getItemViewType(position: Int): Int {
    val curItem = getItem(position)

    return when (curItem) {
      is CatDefaultItem -> DEFAULT_ITEM
      else -> LOADING_ITEM
    }
  }

  override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder =
    when (viewType) {
      DEFAULT_ITEM -> {
        val binding = DataBindingUtil.inflate<ItemCatsListBinding>(
          LayoutInflater.from(parent.context),
          R.layout.item_cats_list,
          parent,
          false
        )
        CatsViewHolder(binding, imageLoader)
      }

      else -> {
        val binding = DataBindingUtil.inflate<ItemLoadingCatsListBinding>(
          LayoutInflater.from(parent.context),
          R.layout.item_loading_cats_list,
          parent,
          false
        )
        CatsLoadingViewHolder(binding)
      }
    }

  override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
    if (shouldLoadNextPage(position)) {
      eventsPublisher.onNext(CatsIntent.LoadNextPage(++curPage))
    }

    if (holder is CatsViewHolder)
      holder.bind(getItem(position) as CatDefaultItem)
  }

  override fun onItemMove(from: Int, to: Int) {
    eventsPublisher.onNext(CatsIntent.ItemMoved(from, to))
  }

  override fun onItemDismiss(position: Int) {
    eventsPublisher.onNext(CatsIntent.ItemDeleted(position))
  }

  private fun shouldLoadNextPage(position: Int): Boolean {
    val loadNextPageInProgress = getItem(itemCount - 1) is CatsListItem.CatLoadingItem
    return (position == itemCount - 4) && !loadNextPageInProgress
  }
}


class CatsViewHolder(val binding: ItemCatsListBinding, val imageLoader: ImageLoader) :
  RecyclerView.ViewHolder(binding.root) {

  fun bind(cat: CatDefaultItem) {
    binding.tvCatId.text = cat.id
    imageLoader.loadImg(binding.ivCatImg, cat.url)
  }
}


class CatsLoadingViewHolder(binding: ItemLoadingCatsListBinding) :
  RecyclerView.ViewHolder(binding.root)