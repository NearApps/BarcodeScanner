package com.github.nearapps.barcodescanner.data.model.openLibraryResponse.records.commons

import androidx.annotation.Keep
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

@Keep
data class Identifiers(
    @SerializedName("openlibrary")
    @Expose
    val openlibrary: List<String>? = null,

    @SerializedName("isbn_13")
    @Expose
    val isbn13: List<String>? = null,
    
    @SerializedName("amazon")
    @Expose
    val amazon: List<String>? = null,
    
    @SerializedName("isbn_10")
    @Expose
    val isbn10: List<String>? = null,
    
    @SerializedName("oclc")
    @Expose
    val oclc: List<String>? = null,
    
    @SerializedName("goodreads")
    @Expose
    val goodreads: List<String>? = null,
    
    @SerializedName("librarything")
    @Expose
    val librarything: List<String>? = null
)