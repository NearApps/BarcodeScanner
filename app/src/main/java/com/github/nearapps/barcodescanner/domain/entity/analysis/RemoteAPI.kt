package com.github.nearapps.barcodescanner.domain.entity.analysis

import com.github.nearapps.barcodescanner.R
import com.github.nearapps.barcodescanner.domain.entity.barcode.BarcodeType

enum class RemoteAPI(val nameResource: Int, val urlResource: Int, val barcodeType: BarcodeType, val layout: Int) {
    OPEN_FOOD_FACTS(R.string.open_food_facts_label, R.string.search_engine_open_food_facts_product_url, BarcodeType.FOOD, R.layout.template_quote_open_food_facts_layout),
    OPEN_PET_FOOD_FACTS(R.string.open_pet_food_facts_label, R.string.search_engine_open_pet_food_facts_product_url, BarcodeType.PET_FOOD, R.layout.template_quote_open_pet_food_facts_layout),
    OPEN_BEAUTY_FACTS(R.string.open_beauty_facts_label, R.string.search_engine_open_beauty_facts_product_url, BarcodeType.BEAUTY, R.layout.template_quote_open_beauty_facts_layout),
    MUSICBRAINZ(R.string.musicbrainz_label, R.string.search_engine_musicbrainz_product_url, BarcodeType.MUSIC, R.layout.template_quote_music_brainz_layout),
    OPEN_LIBRARY(R.string.open_library_label, R.string.search_engine_open_library_product_url, BarcodeType.BOOK, R.layout.template_quote_open_library_layout),
    NONE(R.string.empty, R.string.empty, BarcodeType.UNKNOWN_PRODUCT, -1)
}