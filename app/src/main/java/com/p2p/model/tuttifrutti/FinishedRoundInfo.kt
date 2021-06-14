package com.p2p.model.tuttifrutti

import com.p2p.presentation.tuttifrutti.create.categories.Category

data class FinishedRoundInfo(
    val player: String,
    val categoriesWords: LinkedHashMap<Category, String>,
    val saidEnough: Boolean
)
