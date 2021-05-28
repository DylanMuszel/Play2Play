package com.p2p.presentation.home.games

/** A base class for all events that could occur on the Games screen. */
sealed class GamesEvents

object GoToCreateTuttiFrutti : GamesEvents()

object TurnOnBluetooth : GamesEvents()

object JoinGame : GamesEvents()
