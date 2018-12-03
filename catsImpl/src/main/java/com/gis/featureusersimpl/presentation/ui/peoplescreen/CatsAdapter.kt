package com.gis.featureusersimpl.presentation.ui.peoplescreen

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.gis.featureusersimpl.R
import com.gis.featureusersimpl.databinding.ItemCatsListBinding
import com.gis.repoapi.entity.Cat
import com.gis.utils.domain.ImageLoader
import io.reactivex.subjects.Subject

class PeopleAdapter(
  private val itemMovedPublisher: Subject<CatsIntent.ItemMoved>,
  private val itemDeletedPublisher: Subject<CatsIntent.ItemDeleted>,
  private val imageLoader: ImageLoader
) : ListAdapter<Cat, PeopleViewHolder>(object : DiffUtil.ItemCallback<Cat>() {

  override fun areItemsTheSame(oldItem: Cat, newItem: Cat): Boolean = oldItem.id == newItem.id

  override fun areContentsTheSame(oldItem: Cat, newItem: Cat): Boolean = oldItem == newItem

}), ItemTouchHelperAdapter {

  override fun onFailedToRecycleView(holder: PeopleViewHolder): Boolean = true

  override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PeopleViewHolder {
    val binding = DataBindingUtil.inflate<ItemCatsListBinding>(
      LayoutInflater.from(parent.context),
      R.layout.item_cats_list,
      parent,
      false
    )
    return PeopleViewHolder(binding, imageLoader)
  }

  override fun onBindViewHolder(holder: PeopleViewHolder, position: Int) {
    holder.bind(getItem(position))
  }

  override fun onItemMove(from: Int, to: Int) {
    itemMovedPublisher.onNext(CatsIntent.ItemMoved(from, to))
  }

  override fun onItemDismiss(position: Int) {
    itemDeletedPublisher.onNext(CatsIntent.ItemDeleted(position))
  }
}


class PeopleViewHolder(val binding: ItemCatsListBinding, val imageLoader: ImageLoader) :
  RecyclerView.ViewHolder(binding.root) {

  fun bind(cat: Cat) {
    binding.tvCatId.text = cat.id
    imageLoader.loadImg(binding.ivCatImg, cat.url)
  }
}