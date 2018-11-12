package com.gis.jetpackplayground.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.gis.jetpackplayground.R
import com.gis.jetpackplayground.databinding.ActivityMainBinding
import com.gis.jetpackplayground.ui.peoplescreen.PeopleFragment

class MainActivity : AppCompatActivity() {

  private lateinit var binding: ActivityMainBinding

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    binding = DataBindingUtil.setContentView(this, R.layout.activity_main)

    if (savedInstanceState == null)
      supportFragmentManager.beginTransaction()
        .replace(R.id.fragmentContainer, PeopleFragment(), PeopleFragment::class.java.name)
        .commit()
  }
}
