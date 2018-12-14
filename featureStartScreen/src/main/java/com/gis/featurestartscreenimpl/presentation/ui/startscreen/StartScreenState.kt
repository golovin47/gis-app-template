package com.gis.featurestartscreenimpl.presentation.ui.startscreen

class StartScreenState


sealed class StartScreenIntent {
  object CheckLoggedInIntent : StartScreenIntent()
}


sealed class StartScreenStateChange {
}