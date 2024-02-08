package com.github.nearapps.barcodescanner.domain.repositories

import com.github.nearapps.barcodescanner.domain.entity.dependencies.Allergen

interface AllergensRepository {

    suspend fun getAllergensList(fileNameWithExtension: String,
                                 fileUrlName: String,
                                 tagList: List<String>): List<Allergen>

    suspend fun getAllergens(fileNameWithExtension: String,
                             fileUrlName: String,
                             tagList: List<String>): String
}