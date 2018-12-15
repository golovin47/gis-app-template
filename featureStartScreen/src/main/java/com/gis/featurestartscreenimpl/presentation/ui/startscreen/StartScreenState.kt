package com.gis.featurestartscreenimpl.presentation.ui.startscreen

class StartScreenState


sealed class StartScreenIntent


sealed class StartScreenStateChange {
  class Error(val error: Throwable) : StartScreenStateChange()
  object GoToLogin : StartScreenStateChange()
  object GoToMain : StartScreenStateChange()
}