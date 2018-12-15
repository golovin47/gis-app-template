package com.gis.featureloginscreen.presentation

data class LoginState(
  val loading: Boolean = false,
  val name: String = "",
  val error: Throwable? = null
)


sealed class LoginIntent {
  class NameChanged(val name: String) : LoginIntent()
  class StartLogin(val name: String) : LoginIntent()
}


sealed class LoginStateChange {
  class NameChanged(val name: String) : LoginStateChange()
  object LoginStarted : LoginStateChange()
  object LoginSuccessful : LoginStateChange()
  class LoginError(val error: Throwable) : LoginStateChange()
}