package com.gis.featureusersimpl.presentation.ui.peoplescreen

import android.annotation.SuppressLint
import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.SearchView
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.gis.featureusersimpl.R
import com.gis.featureusersimpl.databinding.FragmentPeopleBinding
import com.gis.featureusersimpl.presentation.ui.peoplescreen.CatsIntent.*
import com.gis.utils.domain.ImageLoader
import com.google.android.material.snackbar.Snackbar
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.subjects.PublishSubject
import org.koin.android.ext.android.get
import org.koin.androidx.viewmodel.ext.android.viewModel
import java.util.concurrent.TimeUnit

class CatsFragment : Fragment() {

  private val searchByIdPublisher = PublishSubject.create<SearchById>()
  private val refreshCatsPublisher = PublishSubject.create<RefreshCats>()
  private val itemMovedPublisher = PublishSubject.create<ItemMoved>()
  private val itemDeletedPublisher = PublishSubject.create<ItemDeleted>()
  private lateinit var binding: FragmentPeopleBinding

  private val vmCats: CatsViewModel by viewModel()

  override fun onCreateView(
    inflater: LayoutInflater,
    container: ViewGroup?,
    savedInstanceState: Bundle?
  ): View? {

    initBinding(inflater, container)
    initRecyclerView(container!!.context)
    initToolbar()
    initSwipeRefresh()
    initIntents()

    return binding.root
  }


  override fun onActivityCreated(savedInstanceState: Bundle?) {
    super.onActivityCreated(savedInstanceState)
    handleStates()
  }

  private fun initBinding(inflater: LayoutInflater, container: ViewGroup?) {
    binding = DataBindingUtil.inflate(inflater, R.layout.fragment_people, container, false)
  }

  private fun initRecyclerView(context: Context) {
    val imageLoader:ImageLoader = get()
    val adapter = PeopleAdapter(
      itemMovedPublisher,
      itemDeletedPublisher,
      imageLoader
    )
    binding.rvPeople
      .apply {
        layoutManager = LinearLayoutManager(context, RecyclerView.VERTICAL, false)
        setAdapter(adapter)
      }.also {
        val itemTouchHelper = ItemTouchHelper(
          CustomItemTouchHelperCallback(
            adapter
          )
        )
        itemTouchHelper.attachToRecyclerView(it)
      }
  }

  private fun initToolbar() {
    binding.tbPeople.inflateMenu(R.menu.menu_people)
    (binding.tbPeople.menu.findItem(R.id.action_search).actionView as SearchView).setOnQueryTextListener(
      object : SearchView.OnQueryTextListener {
        override fun onQueryTextSubmit(query: String?): Boolean {
          return false
        }

        override fun onQueryTextChange(newText: String): Boolean {
          searchByIdPublisher.onNext(SearchById(newText))
          return false
        }
      })
  }

  private fun initSwipeRefresh() {
    binding.srlRefreshPeople.setOnRefreshListener {
      refreshCatsPublisher.onNext(RefreshCats)
    }
  }

  @SuppressLint("CheckResult")
  private fun initIntents() {
    Observable.merge(
      listOf(

        refreshCatsPublisher,

        searchByIdPublisher
          .debounce(300, TimeUnit.MILLISECONDS, AndroidSchedulers.mainThread())
          .distinctUntilChanged(),

        itemMovedPublisher,

        itemDeletedPublisher
      )
    )
      .subscribe(vmCats.viewIntentsConsumer())
  }

  private fun handleStates() {
    vmCats.stateReceived().observe(this, Observer { state -> render(state) })
  }

  private fun render(state: CatsState) {
    binding.srlRefreshPeople.isRefreshing = state.loading

    if (state.error != null)
      Snackbar.make(view!!, state.error.message!!, Snackbar.LENGTH_SHORT).show()

    (binding.rvPeople.adapter as PeopleAdapter).submitList(state.cats)
  }
}