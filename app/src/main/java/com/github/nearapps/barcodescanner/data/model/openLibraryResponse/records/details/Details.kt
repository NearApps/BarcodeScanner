package com.github.nearapps.barcodescanner.data.model.openLibraryResponse.records.details

import androidx.annotation.Keep
import com.github.nearapps.barcodescanner.data.model.openLibraryResponse.records.commons.Author
import com.github.nearapps.barcodescanner.data.model.openLibraryResponse.records.commons.Identifiers
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

@Keep
data class Details(
    @SerializedName("number_of_pages")
    @Expose
    val numberOfPages: Int? = null,

    @SerializedName("weight")
    @Expose
    val weight: String? = null,
    
    @SerializedName("isbn_10")
    @Expose
    val isbn10: List<String>? = null,
    
    @SerializedName("series")
    @Expose
    val series: List<String>? = null,
    
    @SerializedName("covers")
    @Expose
    val covers: List<Int>? = null,
    
    @SerializedName("latest_revision")
    @Expose
    val latestRevision: Int? = null,
    
    @SerializedName("contributions")
    @Expose
    val contributions: List<String>? = null,
    
    @SerializedName("source_records")
    @Expose
    val sourceRecords: List<String>? = null,
    
    @SerializedName("title")
    @Expose
    val title: String? = null,
    
    @SerializedName("translation_of")
    @Expose
    val translationOf: String? = null,
    
    @SerializedName("languages")
    @Expose
    val languages: List<Key>? = null,
    
    @SerializedName("subjects")
    @Expose
    val subjects: List<String>? = null,
    
    @SerializedName("oclc_numbers")
    @Expose
    val oclcNumbers: List<String>? = null,
    
    @SerializedName("type")
    @Expose
    val type: Key? = null,
    
    @SerializedName("physical_dimensions")
    @Expose
    val physicalDimensions: String? = null,
    
    @SerializedName("revision")
    @Expose
    val revision: Int? = null,
    
    @SerializedName("publishers")
    @Expose
    val publishers: List<String>? = null,
    
    @SerializedName("subtitle")
    @Expose
    val subtitle: String? = null,
    
    @SerializedName("description")
    @Expose
    val description: Any? = null,
    
    @SerializedName("physical_format")
    @Expose
    val physicalFormat: String? = null,
    
    @SerializedName("last_modified")
    @Expose
    val lastModified: ValueTypeSchema? = null,
    
    @SerializedName("key")
    @Expose
    val key: String? = null,
    
    @SerializedName("authors")
    @Expose
    val authors: List<Author>? = null,
    
    @SerializedName("created")
    @Expose
    val created: ValueTypeSchema? = null,
    
    @SerializedName("identifiers")
    @Expose
    val identifiers: Identifiers? = null,
    
    @SerializedName("isbn_13")
    @Expose
    val isbn13: List<String>? = null,
    
    @SerializedName("local_id")
    @Expose
    val localId: List<String>? = null,
    
    @SerializedName("publish_date")
    @Expose
    val publishDate: String? = null,
    
    @SerializedName("works")
    @Expose
    val works: List<Key>? = null
)