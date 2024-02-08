package com.github.nearapps.barcodescanner.common.extensions

fun Array<String?>.convertToString(separator: String = ", "): String {
    val filteredArray = this.filterNotNull().filter { it.isNotBlank() }
    return filteredArray.joinToString(separator)
}