package com.github.nearapps.barcodescanner.presentation.views.recyclerView.aboutBdd

import com.github.nearapps.barcodescanner.R

enum class Bdd(val nameResource: Int, val descriptionResource: Int, val webLinkResource: Int, val drawableResource: Int) {
    OPEN_FOOD_FACTS(R.string.open_food_facts_label, R.string.bdd_open_food_facts_description_label, R.string.search_engine_open_food_facts_url, R.drawable.open_food_facts_logo),
    OPEN_PET_FOOD_FACTS(R.string.open_pet_food_facts_label, R.string.bdd_open_pet_food_facts_description_label, R.string.search_engine_open_pet_food_facts_url, R.drawable.open_pet_food_facts_logo_en_356x300),
    OPEN_BEAUTY_FACTS(R.string.open_beauty_facts_label, R.string.bdd_open_beauty_facts_description_label, R.string.search_engine_open_beauty_facts_url, R.drawable.open_beauty_facts_logo_en_356x300),
    MUSIC_BRAINZ(R.string.musicbrainz_label, R.string.bdd_music_brainz_description_label, R.string.search_engine_music_brainz_url, R.drawable.music_brainz_logo),
    OPEN_LIBRARY(R.string.open_library_label, R.string.bdd_open_library_description_label, R.string.search_engine_open_library_url, R.drawable.open_library_logo)
}