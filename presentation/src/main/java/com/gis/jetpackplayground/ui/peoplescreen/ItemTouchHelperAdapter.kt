package com.gis.jetpackplayground.ui.peoplescreen

interface ItemTouchHelperAdapter {

  fun onItemMove(from: Int, to: Int)

  fun onItemDismiss(position: Int)
}