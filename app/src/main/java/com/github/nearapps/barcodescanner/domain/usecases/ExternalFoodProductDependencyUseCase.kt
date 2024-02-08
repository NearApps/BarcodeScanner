package com.github.nearapps.barcodescanner.domain.usecases

import androidx.lifecycle.LiveData
import androidx.lifecycle.liveData
import com.github.nearapps.barcodescanner.domain.entity.dependencies.Additive
import com.github.nearapps.barcodescanner.domain.entity.dependencies.Allergen
import com.github.nearapps.barcodescanner.domain.entity.dependencies.Country
import com.github.nearapps.barcodescanner.domain.entity.dependencies.Label
import com.github.nearapps.barcodescanner.domain.repositories.AdditivesRepository
import com.github.nearapps.barcodescanner.domain.repositories.AllergensRepository
import com.github.nearapps.barcodescanner.domain.repositories.CountriesRepository
import com.github.nearapps.barcodescanner.domain.repositories.LabelsRepository
import kotlinx.coroutines.Dispatchers

class ExternalFoodProductDependencyUseCase(private val labelsRepository: LabelsRepository,
                                           private val additivesRepository: AdditivesRepository,
                                           private val allergensRepository: AllergensRepository,
                                           private val countriesRepository: CountriesRepository) {

    fun obtainLabelsList(
        fileNameWithExtension: String,
        fileUrlName: String,
        tagList: List<String>): LiveData<List<Label>> = liveData(Dispatchers.IO) {

        emit(labelsRepository.getLabels(fileNameWithExtension, fileUrlName, tagList))
    }

    fun obtainAdditivesList(
        additiveFileNameWithExtension: String,
        additiveFileUrlName: String,
        tagList: List<String>,
        additiveClassFileNameWithExtension: String,
        additiveClassFileUrlName: String): LiveData<List<Additive>> = liveData(
        Dispatchers.IO) {

        emit(
            additivesRepository.getAdditivesList(
                additiveFileNameWithExtension = additiveFileNameWithExtension,
                additiveFileUrlName = additiveFileUrlName,
                tagList = tagList,
                additiveClassFileNameWithExtension = additiveClassFileNameWithExtension,
                additiveClassFileUrlName = additiveClassFileUrlName)
        )
    }

    fun obtainAllergensList(
        fileNameWithExtension: String,
        fileUrlName: String,
        tagList: List<String>): LiveData<List<Allergen>> = liveData(Dispatchers.IO) {

        emit(allergensRepository.getAllergensList(fileNameWithExtension, fileUrlName, tagList))
    }

    fun obtainCountriesList(
        fileNameWithExtension: String,
        fileUrlName: String,
        tagList: List<String>): LiveData<List<Country>> = liveData(Dispatchers.IO) {

        emit(countriesRepository.getCountriesList(fileNameWithExtension, fileUrlName, tagList))
    }
}