package com.github.nearapps.barcodescanner.data.model.openLibraryResponse.records.data

import androidx.annotation.Keep
import com.github.nearapps.barcodescanner.data.model.openLibraryResponse.records.commons.Author
import com.github.nearapps.barcodescanner.data.model.openLibraryResponse.records.commons.CoverSchema
import com.github.nearapps.barcodescanner.data.model.openLibraryResponse.records.commons.Identifiers
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

@Keep
data class Data(
    @SerializedName("publishers")
    @Expose
    val publishers: List<Name>? = null,

    @SerializedName("number_of_pages")
    @Expose
    val numberOfPages: Int? = null,
    
    @SerializedName("subtitle")
    @Expose
    val subtitle: String? = null,
    
    @SerializedName("links")
    @Expose
    val links: List<Link>? = null,
    
    @SerializedName("weight")
    @Expose
    val weight: String? = null,
    
    @SerializedName("title")
    @Expose
    val title: String? = null,
    
    @SerializedName("url")
    @Expose
    val url: String? = null,
    
    @SerializedName("identifiers")
    @Expose
    val identifiers: Identifiers? = null,
    
    @SerializedName("cover")
    @Expose
    val cover: CoverSchema? = null,
    
    @SerializedName("subject_places")
    @Expose
    val subjectPlaces: List<UrlNameSchema>? = null,
    
    @SerializedName("subjects")
    @Expose
    val subjects: List<UrlNameSchema>? = null,
    
    @SerializedName("subject_people")
    @Expose
    val subjectPeople: List<UrlNameSchema>? = null,
    
    @SerializedName("key")
    @Expose
    val key: String? = null,
    
    @SerializedName("authors")
    @Expose
    val authors: List<Author>? = null,
    
    @SerializedName("publish_date")
    @Expose
    val publishDate: String? = null,
    
    @SerializedName("excerpts")
    @Expose
    val excerpts: List<Excerpt>? = null
)