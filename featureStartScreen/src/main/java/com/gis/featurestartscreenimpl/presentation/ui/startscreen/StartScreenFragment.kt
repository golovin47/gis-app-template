package com.gis.featurestartscreenimpl.presentation.ui.startscreen

import android.graphics.drawable.Animatable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import com.gis.featurestartscreenimpl.R
import com.gis.featurestartscreenimpl.databinding.FragmentStartScreenBinding
import com.gis.utils.BaseView

class StartScreenFragment : Fragment(), BaseView<StartScreenState> {

  private lateinit var binding: FragmentStartScreenBinding

  override fun onCreateView(
    inflater: LayoutInflater,
    container: ViewGroup?,
    savedInstanceState: Bundle?
  ): View? {

    binding = DataBindingUtil.inflate(inflater, R.layout.fragment_start_screen, container, false)
    (binding.ivStartScreenIcon.drawable as Animatable).start()

    return binding.root
  }

  override fun initIntents() {
    TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
  }

  override fun handleStates() {
    TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
  }

  override fun render(state: StartScreenState) {
    TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
  }


}