package com.jetbrains.kmpapp.screens.booking


import androidx.lifecycle.ViewModel
import kotlinx.datetime.LocalDate

class BookingViewModel : ViewModel() {
    private val bookings = mutableListOf<Booking>()

    fun saveBooking(field: String, date: LocalDate?, time: String) {
        if (date != null) {
            bookings.add(Booking(field, date, time))
        }
    }

    data class Booking(val field: String, val date: LocalDate, val time: String)
}

