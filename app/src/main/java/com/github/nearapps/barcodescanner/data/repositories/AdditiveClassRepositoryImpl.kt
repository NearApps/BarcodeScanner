package com.github.nearapps.barcodescanner.data.repositories

import android.content.Context
import com.github.nearapps.barcodescanner.R
import com.github.nearapps.barcodescanner.data.file.FileFetcher
import com.github.nearapps.barcodescanner.data.file.JsonManager
import com.github.nearapps.barcodescanner.data.model.openFoodFactsDependenciesResponse.AdditiveClassResponse
import com.github.nearapps.barcodescanner.domain.entity.dependencies.AdditiveClass
import com.github.nearapps.barcodescanner.domain.repositories.AdditiveClassRepository
import java.io.File

class AdditiveClassRepositoryImpl(private val context: Context,
                                  private val fileFetcher: FileFetcher): AdditiveClassRepository {


    override suspend fun getAdditiveClassList(fileNameWithExtension: String,
                                              fileUrlName: String,
                                              tagList: List<String>): List<AdditiveClass> {

        val file = fileFetcher.fetchFile(fileNameWithExtension, fileUrlName)

        return if(file.exists()) getAdditiveClassList(tagList, file) else listOf()
    }

    /**
     * Récupère une liste d'AdditiveClass.
     */
    private fun getAdditiveClassList(tagList: List<String>, jsonFile: File): List<AdditiveClass> {

        val additiveClassList = mutableListOf<AdditiveClass>()
        val jsonManager = JsonManager<AdditiveClassResponse>(jsonFile)

        for (tag in tagList) {
            // On récupère un AdditiveClassResponse dans le fichier Json correspondant au tag
            val additiveClassResponse: AdditiveClassResponse? = jsonManager.get(tag, AdditiveClassResponse::class)

            // Si on n'a pas le nom de l'AdditiveClass, on met celui du tag
            val additiveClassName = additiveClassResponse?.name?.toLocaleLanguage() ?: tag

            if(additiveClassName.isNotBlank()) {
                // On génère un AdditiveClass à partir de l'AdditiveClassResponse qu'on ajoute à la liste
                additiveClassList.add(
                    AdditiveClass(
                        tag = tag,
                        name = additiveClassName.trim(),
                        description = getDescription(tag, additiveClassResponse).trim()
                    )
                )
            }
        }

        return additiveClassList
    }

    private fun getDescription(tag: String, additiveClassResponse: AdditiveClassResponse?): String {

        val description = additiveClassResponse?.description?.toLocaleLanguage()

        return if(description.isNullOrBlank()){

            // Non présent dans fichier: additives_classes.json
            if(tag == "en:stabilizer"){
                context.getString(R.string.stabilizer_description_label)
            }else{
                context.getString(R.string.additive_no_information_found_label)
            }
        } else  {
            description
        }
    }
}