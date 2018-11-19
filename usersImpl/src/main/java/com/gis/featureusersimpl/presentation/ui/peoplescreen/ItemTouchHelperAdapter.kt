package com.gis.featureusersimpl.presentation.ui.peoplescreen

interface ItemTouchHelperAdapter {

  fun onItemMove(from: Int, to: Int)

  fun onItemDismiss(position: Int)
}