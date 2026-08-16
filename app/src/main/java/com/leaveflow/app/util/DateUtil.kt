package com.leaveflow.app.util

import java.text.SimpleDateFormat
import java.util.*
import java.util.concurrent.TimeUnit

object DateUtil {
    private val storageFormat = SimpleDateFormat(Constants.DATE_FORMAT, Locale.getDefault())
    private val displayFormat = SimpleDateFormat(Constants.DISPLAY_DATE_FORMAT, Locale.getDefault())

    /** Formats a storage date string ("yyyy-MM-dd") to a human-readable form ("dd MMM yyyy"). */
    fun formatForDisplay(dateStr: String): String = try {
        val date = storageFormat.parse(dateStr) ?: return dateStr
        displayFormat.format(date)
    } catch (e: Exception) {
        dateStr
    }

    /** Returns today's date in storage format. */
    fun today(): String = storageFormat.format(Date())

    /** Parses a storage-format date string to a Date object. */
    fun parse(dateStr: String): Date? = try {
        storageFormat.parse(dateStr)
    } catch (e: Exception) {
        null
    }

    /**
     * Calculates the number of working days between two dates (inclusive).
     * Counts all calendar days for simplicity; can be refined to skip weekends.
     */
    fun calculateDays(startDate: String, endDate: String): Int {
        val start = parse(startDate) ?: return 0
        val end   = parse(endDate)   ?: return 0
        if (end.before(start)) return 0
        val diff = end.time - start.time
        return (TimeUnit.MILLISECONDS.toDays(diff) + 1).toInt()
    }

    /** Returns true if endDate is NOT before startDate. */
    fun isValidRange(startDate: String, endDate: String): Boolean {
        val start = parse(startDate) ?: return false
        val end   = parse(endDate)   ?: return false
        return !end.before(start)
    }

    /**
     * Returns true if [dateStr] is today or in the future.
     * Comparison is date-only (time is stripped by using storage format parse).
     */
    fun isFutureOrToday(dateStr: String): Boolean {
        val date  = parse(dateStr) ?: return false
        val todayStr = today()
        val todayDate = parse(todayStr) ?: return false
        return !date.before(todayDate)   // date >= today
    }

    /** Returns true if the date string is in valid "yyyy-MM-dd" format. */
    fun isValidFormat(dateStr: String): Boolean = try {
        storageFormat.isLenient = false
        storageFormat.parse(dateStr) != null
    } catch (e: Exception) {
        false
    }

    /** Formats a timestamp (Long) to a display-friendly string. */
    fun formatTimestamp(timestamp: Long): String =
        displayFormat.format(Date(timestamp))
}
