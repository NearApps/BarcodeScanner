package com.github.nearapps.barcodescanner.domain.entity.product.bookProduct

import com.github.nearapps.barcodescanner.data.model.openLibraryResponse.OpenLibraryResponse
import com.github.nearapps.barcodescanner.data.model.openLibraryResponse.records.commons.Author
import com.github.nearapps.barcodescanner.data.model.openLibraryResponse.records.data.Name
import com.github.nearapps.barcodescanner.data.model.openLibraryResponse.records.data.UrlNameSchema
import com.google.gson.internal.LinkedTreeMap

fun obtainUrl(response: OpenLibraryResponse): String? = response.informationSchema?.getBook()?.recordURL ?: response.informationSchema?.getBook()?.details?.infoUrl ?: response.informationSchema?.getBook()?.data?.url ?: response.informationSchema?.getBook()?.details?.previewUrl

fun obtainTitle(response: OpenLibraryResponse): String? = response.informationSchema?.getBook()?.details?.details?.title ?: response.informationSchema?.getBook()?.data?.title

fun obtainSubtitle(response: OpenLibraryResponse): String? = response.informationSchema?.getBook()?.details?.details?.subtitle ?: response.informationSchema?.getBook()?.data?.subtitle

fun obtainOriginalTitle(response: OpenLibraryResponse): String? = response.informationSchema?.getBook()?.details?.details?.translationOf


fun obtainAuthorsStringList(response: OpenLibraryResponse): List<String> {
    val mutableList = mutableListOf<String>()

    val authorList: List<Author>? = response.informationSchema?.getBook()?.details?.details?.authors ?: response.informationSchema?.getBook()?.data?.authors

    authorList?.forEach {
        if(!it.name.isNullOrBlank()){
            mutableList.add(it.name)
        }
    }

    return mutableList
}

fun obtainCoverUrl(response: OpenLibraryResponse): String? = response.informationSchema?.getBook()?.data?.cover?.large ?: response.informationSchema?.items?.firstOrNull()?.cover?.large ?: response.informationSchema?.getBook()?.details?.thumbnailUrl

fun obtainDescription(response: OpenLibraryResponse): String? {

    // La description de certain livre est récupéré en format String et d'autres en LinkedTreeMap.
    // On récupère donc la description au format Any puis on détermine son véritable type.
    val description = response.informationSchema?.getBook()?.details?.details?.description
    return when(description){
        is String -> description
        is LinkedTreeMap<*, *> -> description["value"] as? String
        else -> null
    }
}

fun obtainPublishDate(response: OpenLibraryResponse): String? = response.informationSchema?.getBook()?.publishDates?.firstOrNull() ?: response.informationSchema?.getBook()?.data?.publishDate

fun obtainNumberPages(response: OpenLibraryResponse): Int? = response.informationSchema?.getBook()?.details?.details?.numberOfPages ?: response.informationSchema?.getBook()?.data?.numberOfPages

fun obtainContributions(response: OpenLibraryResponse): List<String>? = response.informationSchema?.getBook()?.details?.details?.contributions

fun obtainPublishers(response: OpenLibraryResponse): List<String>? = response.informationSchema?.getBook()?.details?.details?.publishers ?: obtainPublishersFromData(response)

private fun obtainPublishersFromData(response: OpenLibraryResponse): List<String>? {

    val publishersList: List<Name>? = response.informationSchema?.getBook()?.data?.publishers

    if(publishersList?.isNotEmpty() == true) {

        val mutableList = mutableListOf<String>()

        publishersList.forEach {
            if (!it.name.isNullOrBlank()) {
                mutableList.add(it.name)
            }
        }

        return mutableList
    }

    return null
}

fun obtainCategories(response: OpenLibraryResponse): List<String>? = response.informationSchema?.getBook()?.details?.details?.subjects ?: obtainCategoriesFromData(response)

private fun obtainCategoriesFromData(response: OpenLibraryResponse): List<String>? {

    val publishersList: List<UrlNameSchema>? = response.informationSchema?.getBook()?.data?.subjects

    if(publishersList?.isNotEmpty() == true) {

        val mutableList = mutableListOf<String>()

        publishersList.forEach {
            if (!it.name.isNullOrBlank()) {
                mutableList.add(it.name)
            }
        }

        return mutableList
    }

    return null
}