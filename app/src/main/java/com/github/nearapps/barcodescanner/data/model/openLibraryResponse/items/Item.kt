package com.github.nearapps.barcodescanner.data.model.openLibraryResponse.items

import androidx.annotation.Keep
import com.github.nearapps.barcodescanner.data.model.openLibraryResponse.records.commons.CoverSchema
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

@Keep
data class Item(
    @SerializedName("status")
    @Expose
    val status: String? = null,

    @SerializedName("ol-work-id")
    @Expose
    val olWorkId: String? = null,
    
    @SerializedName("ol-edition-id")
    @Expose
    val olEditionId: String? = null,
    
    @SerializedName("cover")
    @Expose
    val cover: CoverSchema? = null,
    
    @SerializedName("publishDate")
    @Expose
    val publishDate: String? = null,
    
    @SerializedName("itemURL")
    @Expose
    val itemURL: String? = null,
    
    @SerializedName("enumcron")
    @Expose
    val enumcron: Boolean? = null,
    
    @SerializedName("contributor")
    @Expose
    val contributor: String? = null,
    
    @SerializedName("fromRecord")
    @Expose
    val fromRecord: String? = null,
    
    @SerializedName("match")
    @Expose
    val match: String? = null
)