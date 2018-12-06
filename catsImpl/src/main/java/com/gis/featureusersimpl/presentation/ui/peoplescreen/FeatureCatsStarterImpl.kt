package com.gis.featureusersimpl.presentation.ui.peoplescreen

import androidx.navigation.NavController
import com.gis.featureusers.FeatureCatsStarter
import com.gis.featureusersimpl.R

class FeatureCatsStarterImpl(private val navController: NavController) :
  FeatureCatsStarter {

  override fun start() {
    navController.setGraph(R.navigation.users_nav_graph)
  }
}