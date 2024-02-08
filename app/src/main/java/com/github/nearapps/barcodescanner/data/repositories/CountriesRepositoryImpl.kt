package com.github.nearapps.barcodescanner.data.repositories

import com.github.nearapps.barcodescanner.data.file.FileFetcher
import com.github.nearapps.barcodescanner.data.file.JsonManager
import com.github.nearapps.barcodescanner.data.model.openFoodFactsDependenciesResponse.CountryResponse
import com.github.nearapps.barcodescanner.domain.entity.dependencies.Country
import com.github.nearapps.barcodescanner.domain.repositories.CountriesRepository
import java.io.File

class CountriesRepositoryImpl(private val fileFetcher: FileFetcher): CountriesRepository {

    override suspend fun getCountriesList(fileNameWithExtension: String,
                                          fileUrlName: String,
                                          tagList: List<String>): List<Country> {

        val file = fileFetcher.fetchFile(fileNameWithExtension, fileUrlName)

        return if(file.exists()) getCountriesList(tagList, file) else listOf()
    }

    override suspend fun getCountries(fileNameWithExtension: String,
                                      fileUrlName: String,
                                      tagList: List<String>): String {

        val file = fileFetcher.fetchFile(fileNameWithExtension, fileUrlName)

        return if(file.exists()) getCountries(tagList, file) else ""
    }

    /**
     * Récupère une liste de Country.
     */
    private fun getCountriesList(tagList: List<String>, jsonFile: File): List<Country> {

        val countryList = mutableListOf<Country>()
        val jsonManager = JsonManager<CountryResponse>(jsonFile)

        for (tag in tagList) {
            // On récupère un CountryResponse dans le fichier Json correspondant au tag
            val countryResponse: CountryResponse? = jsonManager.get(tag, CountryResponse::class)

            // Si on n'a pas le nom du pays, on met celui du tag
            val countryName = countryResponse?.name?.toLocaleLanguage() ?: tag

            if(countryName.isNotBlank()){
                // On génère un Country à partir de CountryResponse qu'on ajoute à la liste
                countryList.add(Country(tag = tag, name = countryName.trim()))
            }
        }

        return countryList
    }

    /**
     * Récupère une liste de Country au format String.
     */
    private fun getCountries(tagList: List<String>, jsonFile: File): String {

        val strBuilder = StringBuilder()
        val jsonManager = JsonManager<CountryResponse>(jsonFile)

        for (tag in tagList) {

            // On récupère un CountryResponse dans le fichier Json correspondant au tag
            val countryResponse: CountryResponse? = jsonManager.get(tag, CountryResponse::class)

            // Si on n'a pas le nom du pays, on met celui du tag
            val countryName = countryResponse?.name?.toLocaleLanguage() ?: tag

            if(countryName.isNotBlank()){
                strBuilder.append(countryName.trim())

                if (tagList.last() != tag)
                    strBuilder.append(", ")
            }
        }

        return strBuilder.toString()
    }
}