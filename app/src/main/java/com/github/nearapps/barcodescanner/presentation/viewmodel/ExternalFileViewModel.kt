package com.github.nearapps.barcodescanner.presentation.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.github.nearapps.barcodescanner.common.utils.ADDITIVES_CLASSES_LOCALE_FILE_NAME
import com.github.nearapps.barcodescanner.common.utils.ADDITIVES_CLASSES_URL
import com.github.nearapps.barcodescanner.common.utils.ADDITIVES_LOCALE_FILE_NAME
import com.github.nearapps.barcodescanner.common.utils.ADDITIVES_URL
import com.github.nearapps.barcodescanner.common.utils.ALLERGENS_LOCALE_FILE_NAME
import com.github.nearapps.barcodescanner.common.utils.ALLERGENS_URL
import com.github.nearapps.barcodescanner.common.utils.COUNTRIES_LOCALE_FILE_NAME
import com.github.nearapps.barcodescanner.common.utils.COUNTRIES_URL
import com.github.nearapps.barcodescanner.common.utils.LABELS_LOCALE_FILE_NAME
import com.github.nearapps.barcodescanner.common.utils.LABELS_URL
import com.github.nearapps.barcodescanner.domain.entity.dependencies.Additive
import com.github.nearapps.barcodescanner.domain.entity.dependencies.Allergen
import com.github.nearapps.barcodescanner.domain.entity.dependencies.Country
import com.github.nearapps.barcodescanner.domain.entity.dependencies.Label
import com.github.nearapps.barcodescanner.domain.usecases.ExternalFoodProductDependencyUseCase

/**
 * Certaines données récupérées sur OpenFoodFacts / OpenBeautyFacts / OpenPetFoodFacts contiennent
 * des tags permettant de récupérer des informations supplémentaires dans d'autres fichiers
 * externes. Ce ViewModel permet de récupérer ces données dans ces fichiers externes.
 */
class ExternalFileViewModel(
    private val externalFoodProductDependencyUseCase: ExternalFoodProductDependencyUseCase
    ): ViewModel() {

    fun obtainLabelsList(tagList: List<String>): LiveData<List<Label>> {

        return externalFoodProductDependencyUseCase.obtainLabelsList(
            fileNameWithExtension = LABELS_LOCALE_FILE_NAME,
            fileUrlName = LABELS_URL,
            tagList = tagList
        )
    }

    fun obtainAdditivesList(tagList: List<String>): LiveData<List<Additive>> {
        return externalFoodProductDependencyUseCase.obtainAdditivesList(
            additiveFileNameWithExtension = ADDITIVES_LOCALE_FILE_NAME,
            additiveFileUrlName = ADDITIVES_URL,
            tagList = tagList,
            additiveClassFileNameWithExtension = ADDITIVES_CLASSES_LOCALE_FILE_NAME,
            additiveClassFileUrlName = ADDITIVES_CLASSES_URL
        )
    }

    fun obtainAllergensList(tagList: List<String>): LiveData<List<Allergen>> {

        return externalFoodProductDependencyUseCase.obtainAllergensList(
            fileNameWithExtension = ALLERGENS_LOCALE_FILE_NAME,
            fileUrlName = ALLERGENS_URL,
            tagList = tagList
        )
    }

    fun obtainCountriesList(tagList: List<String>): LiveData<List<Country>> {

        return externalFoodProductDependencyUseCase.obtainCountriesList(
            fileNameWithExtension = COUNTRIES_LOCALE_FILE_NAME,
            fileUrlName = COUNTRIES_URL,
            tagList = tagList
        )
    }
}