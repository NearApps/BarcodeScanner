package com.github.nearapps.barcodescanner.data.repositories

import com.github.nearapps.barcodescanner.data.file.FileFetcher
import com.github.nearapps.barcodescanner.data.file.JsonManager
import com.github.nearapps.barcodescanner.data.model.openFoodFactsDependenciesResponse.AdditiveResponse
import java.io.File

class AdditiveResponseRepository(private val fileFetcher: FileFetcher) {

    fun getAdditiveResponseList(fileNameWithExtension: String,
                                        fileUrlName: String,
                                        tagList: List<String>): List<AdditiveResponse> {

        val file = fileFetcher.fetchFile(fileNameWithExtension, fileUrlName)

        return if(file.exists()) getAdditiveResponseList(tagList, file) else listOf()
    }

    private fun getAdditiveResponseList(tagList: List<String>, jsonFile: File): List<AdditiveResponse> {

        val additiveResponseList = mutableListOf<AdditiveResponse>()
        val jsonManager = JsonManager<AdditiveResponse>(jsonFile)

        for (tag in tagList) {
            // On récupère un AllergenResponse dans le fichier Json correspondant au tag
            val additiveResponse: AdditiveResponse? = jsonManager.get(tag, AdditiveResponse::class)

            if(additiveResponse != null) {
                additiveResponse.tag = tag
                additiveResponseList.add(additiveResponse)
            }
        }

        return additiveResponseList
    }
}