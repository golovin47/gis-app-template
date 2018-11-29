package com.gis.featurestartscreenimpl

import androidx.navigation.NavController
import com.gis.featurestartscreenapi.FeatureStartScreenStarter

class FeatureStartScreenStarterImpl(private val navController: NavController) :
  FeatureStartScreenStarter {

  override fun start() {
    navController.setGraph(R.navigation.start_screen_nav_graph)
  }
}