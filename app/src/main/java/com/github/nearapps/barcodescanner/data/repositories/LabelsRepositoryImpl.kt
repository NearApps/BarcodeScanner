package com.github.nearapps.barcodescanner.data.repositories

import com.github.nearapps.barcodescanner.data.file.FileFetcher
import com.github.nearapps.barcodescanner.data.file.JsonManager
import com.github.nearapps.barcodescanner.data.model.openFoodFactsDependenciesResponse.LabelResponse
import com.github.nearapps.barcodescanner.domain.entity.dependencies.Label
import com.github.nearapps.barcodescanner.domain.repositories.LabelsRepository
import java.io.File

class LabelsRepositoryImpl(private val fileFetcher: FileFetcher): LabelsRepository {

    override suspend fun getLabels(fileNameWithExtension: String,
                                   fileUrlName: String,
                                   tagList: List<String>): List<Label> {

        val file = fileFetcher.fetchFile(fileNameWithExtension, fileUrlName)

        return if(file.exists()) obtainList(file, tagList) else listOf()
    }

    /**
     * Récupère une liste de Label.
     */
    private fun obtainList(jsonFile: File, tagList: List<String>): List<Label> {

        val labels = mutableListOf<Label>()

        val jsonManager = JsonManager<Array<LabelResponse>>(jsonFile)
        val jsonLabelResponses: Array<LabelResponse>? = getFromJson(jsonManager)

        if(!jsonLabelResponses.isNullOrEmpty()) {

            for(tag in tagList){
                for(schema in jsonLabelResponses){
                    if(tag == schema.id){
                        labels.add(Label(tag = tag, imageUrl = schema.image))
                        break
                    }
                }
            }
        }

        return labels
    }

    /**
     * La clé "tags" est le nom du tableau que l'on récupère dans le fichier Json.
     */
    private inline fun <reified T: Any> getFromJson(jsonManager: JsonManager<T>) = jsonManager.get("tags", T::class)
}