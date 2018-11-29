package com.gis.jetpackplayground.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import com.gis.jetpackplayground.R
import com.gis.jetpackplayground.databinding.ActivityMainBinding
import org.koin.android.ext.android.inject
import org.koin.core.parameter.parametersOf

class MainActivity : AppCompatActivity() {

  private lateinit var binding: ActivityMainBinding
  private val navController: NavController by inject {
    parametersOf(
      (supportFragmentManager.findFragmentById(R.id.main_nav_host) as NavHostFragment).navController
    )
  }
  private val startUsersScreen: () -> Unit by inject(name = "startUsersFeature")


  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    binding = DataBindingUtil.setContentView(this, R.layout.activity_main)
    if (savedInstanceState == null) {
      navController.also {
        startUsersScreen.invoke()
      }
    }
  }
}
