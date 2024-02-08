package com.github.nearapps.barcodescanner.domain.repositories

import com.github.nearapps.barcodescanner.domain.entity.dependencies.Country

interface CountriesRepository {

    suspend fun getCountriesList(fileNameWithExtension: String,
                                 fileUrlName: String,
                                 tagList: List<String>): List<Country>

    suspend fun getCountries(fileNameWithExtension: String,
                             fileUrlName: String,
                             tagList: List<String>): String
}