package com.gis.featureusersimpl.presentation.ui.peoplescreen

import com.gis.repoapi.entity.Cat


data class CatsState(
  val loading: Boolean = false,
  val cats: List<Cat> = emptyList(),
  val error: Throwable? = null
)


sealed class CatsIntent {
  class SearchById(val name: String) : CatsIntent()
  object RefreshCats : CatsIntent()
  class ItemMoved(val from: Int, val to: Int) : CatsIntent()
  class ItemDeleted(val position: Int) : CatsIntent()
}


sealed class CatsStateChange {
  object StartLoading : CatsStateChange()
  object RefreshFinished : CatsStateChange()
  class CatsReceived(val people: List<Cat>) : CatsStateChange()
  class ItemMoved(val from: Int, val to: Int) : CatsStateChange()
  class ItemDeleted(val position: Int) : CatsStateChange()
  class Error(val error: Throwable) : CatsStateChange()
  object HideError : CatsStateChange()
}