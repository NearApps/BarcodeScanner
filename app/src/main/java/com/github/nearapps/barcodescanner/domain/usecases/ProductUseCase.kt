package com.github.nearapps.barcodescanner.domain.usecases

import androidx.lifecycle.MutableLiveData
import com.github.nearapps.barcodescanner.domain.entity.analysis.BarcodeAnalysis
import com.github.nearapps.barcodescanner.domain.entity.analysis.RemoteAPI
import com.github.nearapps.barcodescanner.domain.entity.analysis.RemoteAPIError
import com.github.nearapps.barcodescanner.domain.entity.analysis.UnknownProductBarcodeAnalysis
import com.github.nearapps.barcodescanner.domain.entity.barcode.Barcode
import com.github.nearapps.barcodescanner.domain.entity.barcode.BarcodeType
import com.github.nearapps.barcodescanner.domain.repositories.BeautyProductRepository
import com.github.nearapps.barcodescanner.domain.repositories.BookProductRepository
import com.github.nearapps.barcodescanner.domain.repositories.FoodProductRepository
import com.github.nearapps.barcodescanner.domain.repositories.MusicProductRepository
import com.github.nearapps.barcodescanner.domain.repositories.PetFoodProductRepository
import com.github.nearapps.barcodescanner.domain.resources.Resource

class ProductUseCase(private val foodProductRepository: FoodProductRepository,
                     private val beautyProductRepository: BeautyProductRepository,
                     private val petFoodProductRepository: PetFoodProductRepository,
                     private val musicProductRepository: MusicProductRepository,
                     private val bookProductRepository: BookProductRepository) {

    val productObserver = MutableLiveData<Resource<BarcodeAnalysis>>()

    suspend fun fetchProduct(barcode: Barcode, remoteAPI: RemoteAPI) {
        productObserver.postValue(Resource.loading())

        try {
            val barcodeAnalysis: BarcodeAnalysis = when(remoteAPI) {
                RemoteAPI.OPEN_FOOD_FACTS -> foodProductRepository.getFoodProduct(barcode) ?: UnknownProductBarcodeAnalysis(barcode, RemoteAPIError.NO_RESULT, source = remoteAPI)
                RemoteAPI.OPEN_BEAUTY_FACTS -> beautyProductRepository.getBeautyProduct(barcode) ?: UnknownProductBarcodeAnalysis(barcode, RemoteAPIError.NO_RESULT, source = remoteAPI)
                RemoteAPI.OPEN_PET_FOOD_FACTS -> petFoodProductRepository.getPetFoodProduct(barcode) ?: UnknownProductBarcodeAnalysis(barcode, RemoteAPIError.NO_RESULT, source = remoteAPI)
                RemoteAPI.MUSICBRAINZ -> musicProductRepository.getMusicProduct(barcode) ?: UnknownProductBarcodeAnalysis(barcode, RemoteAPIError.NO_RESULT, source = remoteAPI)
                RemoteAPI.OPEN_LIBRARY -> bookProductRepository.getBookProduct(barcode) ?: UnknownProductBarcodeAnalysis(barcode, RemoteAPIError.NO_RESULT, source = remoteAPI)
                RemoteAPI.NONE -> UnknownProductBarcodeAnalysis(
                    barcode = barcode,
                    apiError = RemoteAPIError.AUTOMATIC_API_RESEARCH_DISABLED,
                    source = when(barcode.getBarcodeType()) {
                        BarcodeType.FOOD -> RemoteAPI.OPEN_FOOD_FACTS
                        BarcodeType.PET_FOOD -> RemoteAPI.OPEN_PET_FOOD_FACTS
                        BarcodeType.BEAUTY -> RemoteAPI.OPEN_BEAUTY_FACTS
                        BarcodeType.MUSIC -> RemoteAPI.MUSICBRAINZ
                        BarcodeType.BOOK -> RemoteAPI.OPEN_LIBRARY
                        else -> remoteAPI
                    }
                )
            }

            productObserver.postValue(Resource.success(barcodeAnalysis))

        } catch (e: Exception) {
            productObserver.postValue(Resource.failure(e, UnknownProductBarcodeAnalysis(barcode, RemoteAPIError.ERROR, e.toString(), remoteAPI)))
            e.printStackTrace()
        }
    }
}