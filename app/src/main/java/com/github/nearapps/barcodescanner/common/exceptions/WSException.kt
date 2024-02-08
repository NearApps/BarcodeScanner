package com.github.nearapps.barcodescanner.common.exceptions

import okhttp3.Response
import java.io.IOException
import java.net.HttpURLConnection

class WSException(val code: Int, val type: Type, message: String?): IOException(message) {
    constructor(response: Response): this(response.code, response.type(), response.message)

    enum class Type {
        NOT_FOUND,
        UNAUTHORISED,
        UPDATE_REQUIRED,
        FORBIDDEN,
        OTHER
    }

    override fun toString(): String {
        return "Error type: $type (Error $code)\n${super.toString()}"
    }

    companion object {
        private fun Response.type(): Type {
            return when(code){
                HttpURLConnection.HTTP_NOT_FOUND -> Type.NOT_FOUND
                HttpURLConnection.HTTP_UNAUTHORIZED -> Type.UNAUTHORISED
                426 -> Type.UPDATE_REQUIRED
                HttpURLConnection.HTTP_FORBIDDEN -> Type.FORBIDDEN
                else -> Type.OTHER
            }
        }
    }
}