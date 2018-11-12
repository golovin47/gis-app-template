package com.gis.jetpackplayground.ui.peoplescreen

import com.gis.domain.entity.People

data class PeopleState(
  val loading: Boolean = false,
  val people: List<People> = emptyList(),
  val error: Throwable? = null
)


sealed class PeopleIntent {
  class SearchByName(val name: String) : PeopleIntent()
  object RefreshPeople : PeopleIntent()
  class ItemMoved(val from: Int, val to: Int) : PeopleIntent()
  class ItemDeleted(val position: Int) : PeopleIntent()
}


sealed class PeopleStateChange {
  object StartLoading : PeopleStateChange()
  object RefreshFinished : PeopleStateChange()
  class PeopleReceived(val people: List<People>) : PeopleStateChange()
  class ItemMoved(val from: Int, val to: Int) : PeopleStateChange()
  class ItemDeleted(val position: Int) : PeopleStateChange()
  class Error(val error: Throwable) : PeopleStateChange()
  object HideError : PeopleStateChange()
}