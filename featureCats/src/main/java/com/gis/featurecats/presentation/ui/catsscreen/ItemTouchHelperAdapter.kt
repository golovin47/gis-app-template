package com.gis.featurecats.presentation.ui.catsscreen

interface ItemTouchHelperAdapter {

  fun onItemMove(from: Int, to: Int)

  fun onItemDismiss(position: Int)
}