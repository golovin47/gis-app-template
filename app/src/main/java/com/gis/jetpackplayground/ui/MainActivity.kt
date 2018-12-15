package com.gis.jetpackplayground.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.navigation.fragment.NavHostFragment
import com.gis.jetpackplayground.R
import com.gis.jetpackplayground.databinding.ActivityMainBinding
import com.gis.navigation.AppNavigator
import org.koin.android.ext.android.get
import org.koin.android.ext.android.inject

class MainActivity : AppCompatActivity() {

  private lateinit var binding: ActivityMainBinding
  private var navigator: AppNavigator = get()

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    initBinding()
    if (savedInstanceState == null) {
      initNavController()
    }
  }

  private fun initBinding() {
    binding = DataBindingUtil.setContentView(this, R.layout.activity_main)
  }

  private fun initNavController() {
    val mainNavController =
      (supportFragmentManager.findFragmentById(R.id.main_nav_host) as NavHostFragment).navController
    navigator.setNavController(mainNavController)
  }
}
