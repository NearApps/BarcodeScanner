package com.github.nearapps.barcodescanner.domain.library

class VEventBuilder {

    companion object{
        private const val BEGIN_VEVENT = "BEGIN:VEVENT"
        private const val END_VEVENT = "END:VEVENT"

        private const val SUMMARY = "SUMMARY:"
        private const val DTSTART = "DTSTART:"
        private const val DTEND = "DTEND:"
        private const val LOCATION = "LOCATION:"
        private const val DESCRIPTION = "DESCRIPTION:"
    }

    private val stringBuilder: StringBuilder = StringBuilder()

    init {
        stringBuilder.clear()

        stringBuilder.append(BEGIN_VEVENT)
        stringBuilder.append("\n")

    }

    fun setSummary(summary: String): VEventBuilder {
        if(summary.isNotBlank()) {
            stringBuilder.append(SUMMARY)
            stringBuilder.append(summary)
            stringBuilder.append("\n")
        }
        return this
    }

    fun setDtStart(dtStart: String): VEventBuilder {
        if(dtStart.isNotBlank()) {
            stringBuilder.append(DTSTART)
            stringBuilder.append(dtStart)
            stringBuilder.append("\n")
        }
        return this
    }

    fun setDtEnd(dtEnd: String): VEventBuilder {
        if(dtEnd.isNotBlank()) {
            stringBuilder.append(DTEND)
            stringBuilder.append(dtEnd)
            stringBuilder.append("\n")
        }
        return this
    }

    fun setLocation(location: String): VEventBuilder {
        if(location.isNotBlank()) {
            stringBuilder.append(LOCATION)
            stringBuilder.append(location)
            stringBuilder.append("\n")
        }
        return this
    }

    fun setDescription(description: String): VEventBuilder {
        if(description.isNotBlank()) {
            stringBuilder.append(DESCRIPTION)
            stringBuilder.append(description)
            stringBuilder.append("\n")
        }
        return this
    }

    fun build(): String {
        stringBuilder.append(END_VEVENT)
        return stringBuilder.toString()
    }
}