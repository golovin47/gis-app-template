package com.gis.featureusersimpl.presentation.ui.peoplescreen

import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.gis.featureusersimpl.R
import com.gis.repoapi.entity.Cat
import io.reactivex.subjects.Subject

class PeopleAdapter(
  private val itemMovedPublisher: Subject<CatsIntent.ItemMoved>,
  private val itemDeletedPublisher: Subject<CatsIntent.ItemDeleted>
) : ListAdapter<Cat, PeopleViewHolder>(object : DiffUtil.ItemCallback<Cat>() {

  override fun areItemsTheSame(oldItem: Cat, newItem: Cat): Boolean = oldItem.id == newItem.id

  override fun areContentsTheSame(oldItem: Cat, newItem: Cat): Boolean = oldItem == newItem

}), ItemTouchHelperAdapter {

  override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PeopleViewHolder {
    val tvName = TextView(parent.context).apply {
      textSize = resources.getDimension(R.dimen.rv_text_size)
    }

    return PeopleViewHolder(tvName)
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


class PeopleViewHolder(val view: View) : RecyclerView.ViewHolder(view) {

  fun bind(cat: Cat) {
    (view as TextView).text = cat.id
  }
}