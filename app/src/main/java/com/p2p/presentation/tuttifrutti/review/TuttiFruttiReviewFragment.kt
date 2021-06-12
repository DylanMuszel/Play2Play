package com.p2p.presentation.tuttifrutti.review

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.p2p.R
import com.p2p.databinding.FragmentReviewTuttiFruttiBinding
import com.p2p.presentation.basegame.BaseGameFragment
import com.p2p.presentation.tuttifrutti.TuttiFruttiViewModel
import com.p2p.presentation.tuttifrutti.create.categories.TuttiFruttiReviewRoundAdapter

class TuttiFruttiReviewFragment : BaseGameFragment<
        FragmentReviewTuttiFruttiBinding,
        TuttiFruttiReviewEvents,
        TuttiFruttiReviewViewModel,
        TuttiFruttiViewModel>() {

    override val gameViewModel: TuttiFruttiViewModel by activityViewModels()

    override val viewModel: TuttiFruttiReviewViewModel by viewModels()

    override val gameInflater: (LayoutInflater, ViewGroup?, Boolean) -> FragmentReviewTuttiFruttiBinding =
        FragmentReviewTuttiFruttiBinding::inflate

    private lateinit var tuttiFruttiReviewRoundAdapter: TuttiFruttiReviewRoundAdapter


    override fun initValues() { //TODO
    }

    override fun initUI() {
        super.initUI()
        viewModel.initializeBaseRoundPoints(gameViewModel.actualRound.value!!)
        setupReviewCategoriesRecycler()
        gameBinding.finishReviewButton.setOnClickListener { viewModel.sendRoundPoints() }
    }

    private fun setupReviewCategoriesRecycler() = with(gameBinding.reviewCategoriesRecycler) {
        layoutManager = LinearLayoutManager(context)
        adapter = TuttiFruttiReviewRoundAdapter(viewModel.finishedRoundInfo, viewModel.finishedRoundPointsInfo).also {
            this@TuttiFruttiReviewFragment.tuttiFruttiReviewRoundAdapter = it
        }
    }

    override fun setupObservers() {
        super.setupObservers()
        with(gameViewModel) {
            actualRound.observe(viewLifecycleOwner) {
                gameBinding.round.text =
                    resources.getString(R.string.tf_round, it.number, totalRounds.value)
                gameBinding.letter.text = resources.getString(R.string.tf_letter, it.letter)
            }
        }
    }

    companion object {

        /** Create a new instance of the [TuttiFruttiReviewFragment]. */
        fun newInstance() = TuttiFruttiReviewFragment()

    }
}
