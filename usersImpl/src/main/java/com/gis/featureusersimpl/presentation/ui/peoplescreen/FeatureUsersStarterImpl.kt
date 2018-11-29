package com.gis.featureusersimpl.presentation.ui.peoplescreen

import androidx.navigation.NavController
import com.gis.featureusers.FeatureUsersStarter
import com.gis.featureusersimpl.R

class FeatureUsersStarterImpl(private val navController: NavController) :
  FeatureUsersStarter {

  override fun start() {
    navController.setGraph(R.navigation.users_nav_graph)
  }
}