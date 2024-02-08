package com.github.nearapps.barcodescanner.common.extensions

fun List<String?>.convertToString(separator: String = ", "): String {
    val filteredList = this.filterNotNull().filter { it.isNotBlank() }
    return filteredList.joinToString(separator)
}