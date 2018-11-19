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
import com.gis.featureusersimpl.presentation.ui.peoplescreen.PeopleIntent.*
import com.google.android.material.snackbar.Snackbar
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.subjects.PublishSubject
import org.koin.androidx.viewmodel.ext.android.viewModel
import java.util.concurrent.TimeUnit

class PeopleFragment : Fragment() {

  private val searchByNamePublisher = PublishSubject.create<SearchByName>()
  private val refreshPeoplePublisher = PublishSubject.create<RefreshPeople>()
  private val itemMovedPublisher = PublishSubject.create<ItemMoved>()
  private val itemDeletedPublisher = PublishSubject.create<ItemDeleted>()
  private lateinit var binding: FragmentPeopleBinding

  private val vmPeople: PeopleViewModel by viewModel()

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
    val adapter = PeopleAdapter(
      itemMovedPublisher,
      itemDeletedPublisher
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
          searchByNamePublisher.onNext(SearchByName(newText))
          return false
        }
      })
  }

  private fun initSwipeRefresh() {
    binding.srlRefreshPeople.setOnRefreshListener {
      refreshPeoplePublisher.onNext(RefreshPeople)
    }
  }

  @SuppressLint("CheckResult")
  private fun initIntents() {
    val intents = Observable.merge(
      listOf(

        refreshPeoplePublisher,

        searchByNamePublisher
          .debounce(300, TimeUnit.MILLISECONDS, AndroidSchedulers.mainThread())
          .distinctUntilChanged(),

        itemMovedPublisher,

        itemDeletedPublisher
      )
    )

    vmPeople.handleIntents(intents)
  }

  private fun handleStates() {
    vmPeople.stateReceived().observe(this, Observer { state -> render(state) })
  }

  private fun render(state: PeopleState) {
    binding.srlRefreshPeople.isRefreshing = state.loading

    if (state.error != null)
      Snackbar.make(view!!, state.error.message!!, Snackbar.LENGTH_SHORT).show()

    (binding.rvPeople.adapter as PeopleAdapter).submitList(state.people)
  }
}