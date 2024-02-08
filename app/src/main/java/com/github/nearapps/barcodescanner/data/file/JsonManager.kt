package com.github.nearapps.barcodescanner.data.file

import com.google.gson.Gson
import org.json.JSONException
import org.json.JSONObject
import java.io.File
import kotlin.reflect.KClass

class JsonManager<T: Any>(file: File) {

    private val gson: Gson = Gson()
    private var jsonObject: JSONObject? = null

    init {
        try {
            val inputStream = file.inputStream()

            val size = inputStream.available()
            val buffer = ByteArray(size)
            inputStream.read(buffer)
            inputStream.close()

            jsonObject = JSONObject(String(buffer))
        } catch (e: JSONException){
            e.printStackTrace()
        }
    }

    fun get(tag: String, kClass: KClass<T>): T? {

        try {

            // On récupère sous forme de chaine de caractères les données Json représentées par
            // l'attribut donné en paramètre
            val str = jsonObject?.getString(tag)

            // Récupère sous forme d'objet les données Json récupéré en String précédement
            return gson.fromJson(str, kClass.java)

        }catch (e: JSONException){
            e.printStackTrace()
        }
        return null
    }
}