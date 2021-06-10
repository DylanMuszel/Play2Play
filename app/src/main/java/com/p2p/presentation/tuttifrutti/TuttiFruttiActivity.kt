package com.p2p.presentation.tuttifrutti

import android.bluetooth.BluetoothDevice
import android.content.Context
import androidx.activity.viewModels
import com.p2p.presentation.basegame.GameActivity
import com.p2p.presentation.tuttifrutti.countdown.TuttiFruttiCountdownFragment
import com.p2p.presentation.tuttifrutti.create.categories.CreateTuttiFruttiFragment
import com.p2p.presentation.tuttifrutti.review.TuttiFruttiReviewFragment

class TuttiFruttiActivity : GameActivity<TuttiFruttiSpecificGameEvent, TuttiFruttiViewModel>() {

    override val viewModel: TuttiFruttiViewModel by viewModels {
        TuttiFruttiViewModelFactory(this, gameViewModelFactoryData)
    }

    // TODO: sacar, esto esta para probar fragment
    override fun goToCreate() = addFragment(TuttiFruttiReviewFragment.newInstance(), shouldAddToBackStack = false)

    override fun goToPlay() = addFragment(TuttiFruttiCountdownFragment.newInstance(), shouldAddToBackStack = false)

    override fun goToClientLobby() =
        Unit // TODO: addFragment(ClientLobbyTuttiFruttiFragment.newInstance(), shouldAddToBackStack = false)

    override fun goToServerLobby() =
        Unit // TODO: addFragment(ServerLobbyTuttiFruttiFragment.newInstance(), shouldAddToBackStack = false)

    companion object {

        fun startCreate(context: Context) = startCreate(TuttiFruttiActivity::class, context)

        fun startJoin(context: Context, serverDevice: BluetoothDevice) {
            startJoin(TuttiFruttiActivity::class, context, serverDevice)
        }
    }
}
