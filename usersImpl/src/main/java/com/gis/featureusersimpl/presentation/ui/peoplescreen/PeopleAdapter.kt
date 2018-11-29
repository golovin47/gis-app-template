package com.gis.featureusersimpl.presentation.ui.peoplescreen

import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.gis.featureusersimpl.R
import com.gis.repoapi.entity.People
import io.reactivex.subjects.Subject

class PeopleAdapter(
  private val itemMovedPublisher: Subject<PeopleIntent.ItemMoved>,
  private val itemDeletedPublisher: Subject<PeopleIntent.ItemDeleted>
) : ListAdapter<People, PeopleViewHolder>(object : DiffUtil.ItemCallback<People>() {

  override fun areItemsTheSame(oldItem: People, newItem: People): Boolean = oldItem.id == newItem.id

  override fun areContentsTheSame(oldItem: People, newItem: People): Boolean = oldItem == newItem

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
    itemMovedPublisher.onNext(PeopleIntent.ItemMoved(from, to))
  }

  override fun onItemDismiss(position: Int) {
    itemDeletedPublisher.onNext(PeopleIntent.ItemDeleted(position))
  }
}


class PeopleViewHolder(val view: View) : RecyclerView.ViewHolder(view) {

  fun bind(people: People) {
    (view as TextView).text = people.name
  }
}