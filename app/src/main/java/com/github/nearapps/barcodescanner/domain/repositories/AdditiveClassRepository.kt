package com.github.nearapps.barcodescanner.domain.repositories

import com.github.nearapps.barcodescanner.domain.entity.dependencies.AdditiveClass

interface AdditiveClassRepository {
    suspend fun getAdditiveClassList(fileNameWithExtension: String, fileUrlName: String, tagList: List<String>): List<AdditiveClass>
}