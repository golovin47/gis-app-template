package com.gis.featurestartscreen.presentation.ui.startscreen

import android.annotation.SuppressLint
import android.graphics.drawable.Animatable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import com.gis.featurestartscreen.R
import com.gis.featurestartscreen.databinding.FragmentStartScreenBinding
import com.gis.utils.BaseView
import io.reactivex.Observable
import org.koin.androidx.viewmodel.ext.android.viewModel

class StartScreenFragment : Fragment(), BaseView<StartScreenState> {

  private var binding: FragmentStartScreenBinding? = null
  private val vmStartScreen: StartScreenViewModel by viewModel()

  override fun onCreateView(
    inflater: LayoutInflater,
    container: ViewGroup?,
    savedInstanceState: Bundle?
  ): View? {

    initBinding(inflater, container)
    startAnimation()
    initIntents()

    return binding!!.root
  }

  override fun onDestroyView() {
    binding = null
    super.onDestroyView()
  }

  private fun initBinding(inflater: LayoutInflater, container: ViewGroup?) {
    binding = DataBindingUtil.inflate(inflater, R.layout.fragment_start_screen, container, false)
  }

  private fun startAnimation() {
    (binding!!.ivStartScreenIcon.drawable as Animatable).start()
  }

  @SuppressLint("CheckResult")
  override fun initIntents() {
    Observable.never<Any>()
      .subscribe(vmStartScreen.viewIntentsConsumer())
  }

  override fun handleStates() {
    TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
  }

  override fun render(state: StartScreenState) {
    TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
  }
}