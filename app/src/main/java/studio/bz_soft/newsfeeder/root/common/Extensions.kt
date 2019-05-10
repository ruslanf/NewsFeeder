package studio.bz_soft.newsfeeder.root.common

import org.joda.time.DateTime
import org.joda.time.format.DateTimeFormat
import java.util.*

fun transformDate(date: String): String =
    DateTime(date).toString(DateTimeFormat.forPattern("HH:mm yyyy-MM-dd").withLocale(Locale.ENGLISH))
