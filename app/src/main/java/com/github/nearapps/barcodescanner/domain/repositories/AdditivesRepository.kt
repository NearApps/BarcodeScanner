package com.github.nearapps.barcodescanner.domain.repositories

import com.github.nearapps.barcodescanner.domain.entity.dependencies.Additive

interface AdditivesRepository {
    suspend fun getAdditivesList(additiveFileNameWithExtension: String,
                                 additiveFileUrlName: String,
                                 tagList: List<String>,
                                 additiveClassFileNameWithExtension: String,
                                 additiveClassFileUrlName: String): List<Additive>
}