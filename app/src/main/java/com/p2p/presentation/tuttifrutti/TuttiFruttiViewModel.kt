package com.p2p.presentation.tuttifrutti

import com.p2p.data.bluetooth.BluetoothConnectionCreator
import com.p2p.data.userInfo.UserSession
import com.p2p.presentation.base.game.ConnectionType
import com.p2p.presentation.base.game.GameEvent
import com.p2p.presentation.base.game.GameViewModel

class TuttiFruttiViewModel(
    connectionType: ConnectionType,
    userSession: UserSession,
    bluetoothConnectionCreator: BluetoothConnectionCreator
) : GameViewModel(connectionType, userSession, bluetoothConnectionCreator) {

    init {
        createOrJoin()
        startConnection() // TODO: This should be called when the creation is finished, from the Lobby
    }

    override fun getCreationEvent(): GameEvent = GoToSelectCategories
}
