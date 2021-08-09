package com.p2p.presentation.home.games

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.p2p.R
import com.p2p.data.bluetooth.BluetoothStateProvider
import com.p2p.data.userInfo.UserSession
import com.p2p.presentation.base.BaseViewModel

class GamesViewModel(
    private val userSession: UserSession,
    private val bluetoothStateProvider: BluetoothStateProvider
) : BaseViewModel<GamesEvents>() {

    /** The list of games available to play. */
    private val _games = MutableLiveData<List<Game>>()
    val games: LiveData<List<Game>> = _games

    /** Whether the create and join button are enabled or not. */
    private val _buttonsEnabled = MutableLiveData<Boolean>()
    val buttonsEnabled: LiveData<Boolean> = _buttonsEnabled

    /** The current saved user name. */
    private val _userName = MutableLiveData(userSession.getUserName())
    val userName: LiveData<String?> = _userName

    private var selectedGame: Game? = null

    init {
        _games.value = Game.values().toList()
    }

    /** Select the given [game] and allow to create it. */
    fun selectGame(game: Game?) {
        _buttonsEnabled.value = game != null
        selectedGame = game
    }

    /** Open the view that corresponds to create the [selectedGame]. */
    fun createGame(userName: String?) {
        when {
            !validateAndSaveName(userName) -> return
            !bluetoothStateProvider.isEnabled() -> dispatchSingleTimeEvent(TurnOnBluetooth)
            else -> when (selectedGame) {
                Game.TUTTI_FRUTTI -> dispatchSingleTimeEvent(GoToCreateTuttiFrutti)
                Game.IMPOSTOR -> dispatchSingleTimeEvent(GoToCreateImpostor)
            }
        }
    }

    /** Open the view to join to a game. */
    fun joinGame(userName: String?) {
        when {
            !validateAndSaveName(userName) -> return
            !bluetoothStateProvider.isEnabled() -> dispatchSingleTimeEvent(TurnOnBluetooth)
            else -> dispatchSingleTimeEvent(JoinGame(requireNotNull(selectedGame)))
        }
    }

    private fun validateAndSaveName(name: String?) = when {
        name.isNullOrBlank() -> {
            dispatchMessage(
                textRes = R.string.games_name_error,
                type = MessageData.Type.ERROR
            )
            false
        }
        name.length > NAME_MAX_LENGTH -> {
            dispatchMessage(
                textRes = R.string.games_name_max_length_error,
                type = MessageData.Type.ERROR
            )

            false
        }
        else -> {
            saveName(name)
            true
        }
    }

    private fun saveName(name: String) {
        _userName.value = name
        userSession.saveUserName(name)
    }

    companion object {

        private const val NAME_MAX_LENGTH = 7
    }
}
