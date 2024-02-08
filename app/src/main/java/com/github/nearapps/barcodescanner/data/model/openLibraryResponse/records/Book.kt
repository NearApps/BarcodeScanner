package com.github.nearapps.barcodescanner.data.model.openLibraryResponse.records

import androidx.annotation.Keep
import com.github.nearapps.barcodescanner.data.model.openLibraryResponse.records.data.Data
import com.github.nearapps.barcodescanner.data.model.openLibraryResponse.records.details.DetailsMain
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

@Keep
data class Book(
    @SerializedName("recordURL")
    @Expose
    val recordURL: String? = null,

    @SerializedName("oclcs")
    @Expose
    val oclcs: List<String>? = null,
    
    @SerializedName("publishDates")
    @Expose
    val publishDates: List<String>? = null,
    
    @SerializedName("lccns")
    @Expose
    val lccns: List<Any>? = null,
    
    @SerializedName("details")
    @Expose
    val details: DetailsMain? = null,
    
    @SerializedName("isbns")
    @Expose
    val isbns: List<String>? = null,
    
    @SerializedName("olids")
    @Expose
    val olids: List<String>? = null,
    
    @SerializedName("issns")
    @Expose
    val issns: List<Any>? = null,
    
    @SerializedName("data")
    @Expose
    val data: Data? = null
)