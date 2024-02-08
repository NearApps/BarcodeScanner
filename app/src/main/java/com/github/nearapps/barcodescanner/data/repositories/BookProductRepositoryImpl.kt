package com.github.nearapps.barcodescanner.data.repositories

import com.github.nearapps.barcodescanner.data.api.OpenLibraryService
import com.github.nearapps.barcodescanner.domain.entity.analysis.BookBarcodeAnalysis
import com.github.nearapps.barcodescanner.domain.entity.analysis.RemoteAPI
import com.github.nearapps.barcodescanner.domain.entity.barcode.Barcode
import com.github.nearapps.barcodescanner.domain.repositories.BookProductRepository

class BookProductRepositoryImpl(private val service: OpenLibraryService): BookProductRepository {
    override suspend fun getBookProduct(barcode: Barcode): BookBarcodeAnalysis? {
        val bookProductResponse = service.getOpenLibraryData(barcode.contents)

        if(bookProductResponse.informationSchema == null)
            return null

        return bookProductResponse.toModel(barcode, RemoteAPI.OPEN_LIBRARY)
    }
}