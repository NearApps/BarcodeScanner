package com.github.nearapps.barcodescanner.presentation.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.github.nearapps.barcodescanner.domain.entity.analysis.BarcodeAnalysis
import com.github.nearapps.barcodescanner.domain.entity.analysis.RemoteAPI
import com.github.nearapps.barcodescanner.domain.entity.barcode.Barcode
import com.github.nearapps.barcodescanner.domain.resources.Resource
import com.github.nearapps.barcodescanner.domain.usecases.ProductUseCase
import kotlinx.coroutines.launch

class ProductViewModel(private val productUseCase: ProductUseCase): ViewModel() {
    val product: LiveData<Resource<BarcodeAnalysis>> get() = productUseCase.productObserver

    fun fetchProduct(barcode: Barcode, apiRemote: RemoteAPI) = viewModelScope.launch {
        productUseCase.fetchProduct(barcode, apiRemote)
    }

    override fun onCleared() {
        super.onCleared()
        productUseCase.productObserver.value = null
    }
}