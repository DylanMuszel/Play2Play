package com.p2p.presentation.tuttifrutti.create.categories

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.p2p.databinding.FragmentCreateTuttiFruttiBinding
import com.p2p.presentation.basegame.BaseGameFragment
import com.p2p.presentation.tuttifrutti.TuttiFruttiViewModel
import com.p2p.presentation.tuttifrutti.create.rounds.TuttiFruttiRoundsNumberFragment

class CreateTuttiFruttiFragment : BaseGameFragment<
        FragmentCreateTuttiFruttiBinding,
        TuttiFruttiCategoriesEvents,
        CreateTuttiFruttiViewModel,
        TuttiFruttiViewModel>(isAddedToBackStack = true) {

    override val gameViewModel: TuttiFruttiViewModel by activityViewModels()
    override val viewModel: CreateTuttiFruttiViewModel by viewModels {
        CreateTuttiFruttiViewModelFactory(requireContext())
    }
    override val gameInflater: (LayoutInflater, ViewGroup?, Boolean) -> FragmentCreateTuttiFruttiBinding =
            FragmentCreateTuttiFruttiBinding::inflate

    private lateinit var tuttiFruttiCategoriesAdapter: TuttiFruttiCategoriesAdapter
    private lateinit var tuttiFruttiSelectedCategoriesAdapter: TuttiFruttiSelectedCategoriesAdapter

    override fun initUI() {
        super.initUI()
        setupCategoriesRecycler()
        setupCategoriesSelectedRecycler()
        gameBinding.continueButton.setOnClickListener { viewModel.continueToNextScreen() }
    }

    override fun setupObservers() = with(viewModel) {
        observe(allCategories) { tuttiFruttiCategoriesAdapter.categories = it }
        observe(selectedCategories) {
            tuttiFruttiCategoriesAdapter.selectedCategories = it
            tuttiFruttiSelectedCategoriesAdapter.selectedCategories = it
        }
        observe(continueButtonEnabled) {
            gameBinding.continueButton.isEnabled = it
        }
        super.setupObservers()
    }


    override fun onEvent(event: TuttiFruttiCategoriesEvents) = when (event) {
        is GoToSelectRounds -> {
            gameViewModel.setCategoriesToPlay(event.categories)
            TuttiFruttiRoundsNumberFragment.newInstance().show(childFragmentManager, "RoundsNumberDialog")
        }
    }

    private fun setupCategoriesRecycler() = with(gameBinding.categoriesRecycler) {
        layoutManager = LinearLayoutManager(context)
        adapter = TuttiFruttiCategoriesAdapter(viewModel::changeCategorySelection).also {
            this@CreateTuttiFruttiFragment.tuttiFruttiCategoriesAdapter = it
        }
    }

    private fun setupCategoriesSelectedRecycler() = with(gameBinding.categoriesSelectedRecycler) {
        layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
        adapter = TuttiFruttiSelectedCategoriesAdapter(viewModel::changeCategorySelection).also {
            this@CreateTuttiFruttiFragment.tuttiFruttiSelectedCategoriesAdapter = it
        }
    }

    companion object {

        /** Create a new instance of the [CreateTuttiFruttiFragment]. */
        fun newInstance() = CreateTuttiFruttiFragment()
    }
}