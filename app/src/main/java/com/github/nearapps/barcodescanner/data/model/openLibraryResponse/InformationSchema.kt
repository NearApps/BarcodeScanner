package com.github.nearapps.barcodescanner.data.model.openLibraryResponse

import androidx.annotation.Keep
import com.github.nearapps.barcodescanner.data.model.openLibraryResponse.items.Item
import com.github.nearapps.barcodescanner.data.model.openLibraryResponse.records.Book
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

@Keep
data class InformationSchema(
    @SerializedName("records")
    @Expose
    val records: Map<String, Book>? = null,

    @SerializedName("items")
    @Expose
    val items: List<Item>? = null) {

    fun getBook(): Book? = if(records?.isNullOrEmpty() == true) null else records.values.first()
}