package com.gis.featureusersimpl.presentation.ui.peoplescreen


data class CatsState(
  val loading: Boolean = false,
  val cats: List<CatsListItem> = emptyList(),
  val error: Throwable? = null
)


sealed class CatsIntent {
  class SearchById(val name: String) : CatsIntent()
  object RefreshCats : CatsIntent()
  class LoadNextPage(val page: Int) : CatsIntent()
  class ItemMoved(val from: Int, val to: Int) : CatsIntent()
  class ItemDeleted(val position: Int) : CatsIntent()
}


sealed class CatsStateChange {
  object StartLoading : CatsStateChange()
  object RefreshFinished : CatsStateChange()
  object StartLoadNextPage : CatsStateChange()
  class LoadNextPageError(val error: Throwable) : CatsStateChange()
  class CatsReceived(val cats: List<CatsListItem>) : CatsStateChange()
  class ItemMoved(val from: Int, val to: Int) : CatsStateChange()
  class ItemDeleted(val position: Int) : CatsStateChange()
  class Error(val error: Throwable) : CatsStateChange()
  object HideError : CatsStateChange()
}


sealed class CatsListItem {
  data class CatDefaultItem(val id: String, val url: String) : CatsListItem()
  object CatLoadingItem : CatsListItem()
}