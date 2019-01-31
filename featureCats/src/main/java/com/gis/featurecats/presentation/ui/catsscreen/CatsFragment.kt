package com.gis.featurecats.presentation.ui.catsscreen

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
import com.gis.featurecats.R
import com.gis.featurecats.databinding.FragmentCatsBinding
import com.gis.featurecats.presentation.ui.catsscreen.CatsIntent.RefreshCats
import com.gis.featurecats.presentation.ui.catsscreen.CatsIntent.SearchById
import com.gis.utils.BaseView
import com.gis.utils.domain.ImageLoader
import com.google.android.material.snackbar.Snackbar
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.subjects.PublishSubject
import org.koin.android.ext.android.get
import org.koin.androidx.viewmodel.ext.android.viewModel
import java.util.concurrent.TimeUnit

class CatsFragment : Fragment(), BaseView<CatsState> {

  private val eventsPublisher = PublishSubject.create<CatsIntent>()
  private lateinit var viewSubscriptions: Disposable
  private var binding: FragmentCatsBinding? = null

  private val vmCats: CatsViewModel by viewModel()

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    handleStates()
  }

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

    return binding!!.root
  }

  override fun onDestroyView() {
    binding = null
    viewSubscriptions.dispose()
    super.onDestroyView()
  }

  private fun initBinding(inflater: LayoutInflater, container: ViewGroup?) {
    binding = DataBindingUtil.inflate(inflater, R.layout.fragment_cats, container, false)
  }

  private fun initRecyclerView(context: Context) {
    val imageLoader: ImageLoader = get()
    val adapter = CatsAdapter(eventsPublisher, imageLoader)
    binding!!.rvPeople
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
    binding!!.tbPeople.inflateMenu(R.menu.menu_people)
    (binding!!.tbPeople.menu.findItem(R.id.action_search).actionView as SearchView).setOnQueryTextListener(
      object : SearchView.OnQueryTextListener {
        override fun onQueryTextSubmit(query: String?): Boolean {
          return false
        }

        override fun onQueryTextChange(newText: String): Boolean {
          eventsPublisher.onNext(SearchById(newText))
          return false
        }
      })
  }

  private fun initSwipeRefresh() {
    binding!!.srlRefreshPeople.setOnRefreshListener {
      eventsPublisher.onNext(RefreshCats)
    }
  }

  @SuppressLint("CheckResult")
  override fun initIntents() {
    viewSubscriptions = Observable.merge(
      listOf(
        eventsPublisher,

        eventsPublisher.ofType(SearchById::class.java)
          .debounce(300, TimeUnit.MILLISECONDS, AndroidSchedulers.mainThread())
          .distinctUntilChanged()
      )
    ).subscribe(vmCats.viewIntentsConsumer())
  }

  override fun handleStates() {
    vmCats.stateReceived().observe(this, Observer { state -> render(state) })
  }

  override fun render(state: CatsState) {
    binding!!.srlRefreshPeople.isRefreshing = state.loading

    if (state.error != null)
      Snackbar.make(view!!, state.error.message!!, Snackbar.LENGTH_SHORT).show()

    (binding!!.rvPeople.adapter as CatsAdapter).submitList(state.cats)
  }
}