package com.gis.featureloginscreen.presentation

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import com.gis.featureloginscreen.R
import com.gis.featureloginscreen.databinding.FragmentLoginBinding

class LoginFragment : Fragment() {

  private lateinit var binding: FragmentLoginBinding

  override fun onCreateView(
    inflater: LayoutInflater,
    container: ViewGroup?,
    savedInstanceState: Bundle?
  ): View? {

    binding = DataBindingUtil.inflate(inflater, R.layout.fragment_login, container, false)

    return binding.root
  }
}