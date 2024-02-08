package com.github.nearapps.barcodescanner.domain.entity

enum class FileFormat(val mimeType: String, val extension: String) {
    CSV("text/csv", ".csv"),
    JSON("application/json", ".json")
}