package com.p2p.presentation.basegame

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.annotation.CallSuper
import androidx.viewbinding.ViewBinding
import com.google.android.material.appbar.MaterialToolbar
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.p2p.R
import com.p2p.databinding.BaseGameBinding
import com.p2p.presentation.base.BaseFragment
import com.p2p.presentation.base.BaseViewModel

/**
 * Base implementation of a [BaseGameFragment] used to simplify boilerplate.
 * It is a [BaseFragment] with base content of game screen
 * [GVB]: GameViewBinding
 * [E]: Event
 * [VM]: BaseViewModel with the same event defined
 */
abstract class BaseGameFragment<GVB : ViewBinding, E : Any, VM : BaseViewModel<E>, GVM : GameViewModel> :
    BaseFragment<BaseGameBinding, E, VM>() {

    protected abstract val gameViewModel: GVM

    override val inflater: (LayoutInflater, ViewGroup?, Boolean) -> BaseGameBinding =
        { inflater, container, boolean ->
            val baseView = BaseGameBinding.inflate(inflater)
            _gameBinding = gameInflater(inflater, container, boolean)
            val gameView = gameBinding.root
            baseView.content.addView(gameView)
            baseView
        }

    /** The binding is used to access to the views declared on the gameLayout [GVB]. */
    private var _gameBinding: GVB? = null
    protected val gameBinding
        get() = requireNotNull(_gameBinding) {
            "The game view binding is required but the view was already destroyed."
        }

    abstract val gameInflater: (LayoutInflater, ViewGroup?, Boolean) -> GVB

    @CallSuper
    override fun initUI() {
        setHeaderEvents(binding.header)
    }

    @CallSuper
    override fun setupObservers() {
        super.setupObservers()
        gameViewModel.singleTimeEvent.observe(viewLifecycleOwner) { onGameEvent(it) }
        gameViewModel.game.observe(viewLifecycleOwner) { binding.header.title = context?.getText(it.nameRes) }
    }

    @CallSuper
    protected fun onGameEvent(event: GameEvent) {
        if (event is OpenInstructions) showInstructions(event.instructions)
    }

    private fun setHeaderEvents(header: MaterialToolbar) {
        header.setOnMenuItemClickListener { menuItem ->
            when (menuItem.itemId) {
                R.id.instructions -> {
                    gameViewModel.showInstructions()
                    true
                }
                else -> false
            }
        }
        header.setNavigationOnClickListener { activity?.onBackPressed() }
    }

    private fun showInstructions(instructions: String) = MaterialAlertDialogBuilder(requireContext())
        .setMessage(instructions)
        //It is positive to be shown on the right
        .setPositiveButton(resources.getString(android.R.string.ok)) { _, _ ->
            // Respond to positive button press
        }
        .show()
}
