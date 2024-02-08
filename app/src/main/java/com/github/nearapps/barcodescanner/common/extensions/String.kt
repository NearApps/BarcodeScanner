package com.github.nearapps.barcodescanner.common.extensions

import android.os.Build
import android.text.Html
import android.text.Spanned
import java.util.Locale

/**
 * Cette méthode permet d'enjoliver une chaine de caractère. Elle est utilisée pour les textes
 * d'Open Food Facts principalement.
 *
 * Sépare la chaine au niveau des virgules, et réassemble le tout avec:
 *      - La méthode trim(), permettant de suprimer les espaces inutile en début et fin de chaine
 *      - La méthode capitalize() mettant en majuscule chaque premier mot de la chaine.
 */
fun String.polishText(): String {
    return if(this.isNotBlank()){
        val textSplited = this.split(',')
        val strBuilder = StringBuilder()

        textSplited.forEach { text ->

            strBuilder.append(text.trim().firstCharacterIntoCapital())

            if(text!=textSplited.last())
                strBuilder.append(", ")
        }

        strBuilder.toString()
    } else this
}

fun String.canBeConvertibleToLong(): Boolean = this.toLongOrNull()?.let { true } ?: false

fun String.toHtmlSpanned(): Spanned =
    if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
        Html.fromHtml(this, Html.FROM_HTML_MODE_LEGACY)
    } else {
        @Suppress("DEPRECATION")
        Html.fromHtml(this)
    }

fun String.firstCharacterIntoCapital(): String = replaceFirstChar {
    if (it.isLowerCase())
        it.titlecase(Locale.getDefault())
    else
        it.toString()
}