package com.github.nearapps.barcodescanner.domain.repositories

import com.github.nearapps.barcodescanner.domain.entity.dependencies.Label

interface LabelsRepository {
    suspend fun getLabels(fileNameWithExtension: String,
                          fileUrlName: String,
                          tagList: List<String>): List<Label>
}