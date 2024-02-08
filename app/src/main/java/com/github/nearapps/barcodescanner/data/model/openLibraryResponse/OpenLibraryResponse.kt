package com.github.nearapps.barcodescanner.data.model.openLibraryResponse

import androidx.annotation.Keep
import com.github.nearapps.barcodescanner.domain.entity.analysis.BookBarcodeAnalysis
import com.github.nearapps.barcodescanner.domain.entity.analysis.RemoteAPI
import com.github.nearapps.barcodescanner.domain.entity.barcode.Barcode
import com.github.nearapps.barcodescanner.domain.entity.product.*
import com.github.nearapps.barcodescanner.domain.entity.product.bookProduct.obtainAuthorsStringList
import com.github.nearapps.barcodescanner.domain.entity.product.bookProduct.obtainCategories
import com.github.nearapps.barcodescanner.domain.entity.product.bookProduct.obtainContributions
import com.github.nearapps.barcodescanner.domain.entity.product.bookProduct.obtainCoverUrl
import com.github.nearapps.barcodescanner.domain.entity.product.bookProduct.obtainDescription
import com.github.nearapps.barcodescanner.domain.entity.product.bookProduct.obtainNumberPages
import com.github.nearapps.barcodescanner.domain.entity.product.bookProduct.obtainOriginalTitle
import com.github.nearapps.barcodescanner.domain.entity.product.bookProduct.obtainPublishDate
import com.github.nearapps.barcodescanner.domain.entity.product.bookProduct.obtainPublishers
import com.github.nearapps.barcodescanner.domain.entity.product.bookProduct.obtainSubtitle
import com.github.nearapps.barcodescanner.domain.entity.product.bookProduct.obtainTitle
import com.github.nearapps.barcodescanner.domain.entity.product.bookProduct.obtainUrl
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

@Keep
data class OpenLibraryResponse(
    @SerializedName("information")
    @Expose
    val informationSchema: InformationSchema? = null
) {

    fun toModel(barcode: Barcode, source: RemoteAPI): BookBarcodeAnalysis = BookBarcodeAnalysis(
        barcode =  barcode,
        source = source,
        url = obtainUrl(this),
        title = obtainTitle(this),
        subtitle = obtainSubtitle(this),
        originalTitle = obtainOriginalTitle(this),
        authors = obtainAuthorsStringList(this),
        coverUrl = obtainCoverUrl(this),
        description = obtainDescription(this),
        publishDate = obtainPublishDate(this),
        numberPages = obtainNumberPages(this),
        contributions = obtainContributions(this),
        publishers = obtainPublishers(this),
        categories = obtainCategories(this)
    )
}