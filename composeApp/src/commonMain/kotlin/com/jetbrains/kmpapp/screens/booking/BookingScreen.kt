package com.jetbrains.kmpapp.screens.booking

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlinx.datetime.LocalDate



@Composable
fun BookingScreen(viewModel: BookingViewModel) {
    var selectedField by remember { mutableStateOf("Project Management") }
    var selectedDate by remember { mutableStateOf<LocalDate?>(null) }
    var selectedTime by remember { mutableStateOf("8:00 AM") }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(20.dp),
        verticalArrangement = Arrangement.spacedBy(20.dp)
    ) {
        Text(
            text = "Pilih bidang keahlian",
            style = MaterialTheme.typography.headlineMedium,
            modifier = Modifier.padding(bottom = 10.dp)
        )
        DropdownMenu(
            options = listOf("Project Management", "Software Development", "UI/UX Design"),
            selectedOption = selectedField,
            onOptionSelected = { selectedField = it }
        )

        Spacer(modifier = Modifier.height(20.dp))

        Text(
            text = "Pilih waktu",
            style = MaterialTheme.typography.headlineMedium,
            modifier = Modifier.padding(bottom = 10.dp)
        )
        CalendarPicker(

            onDateSelected = { selectedDate = it }
        )
        Spacer(modifier = Modifier.height(20.dp))

        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            Text(text = "Pukul", style = MaterialTheme.typography.bodyLarge)
            BasicTextField(
                value = TextFieldValue(selectedTime),
                onValueChange = { selectedTime = it.text },
                textStyle = MaterialTheme.typography.bodyMedium.copy(fontSize = 14.sp),
                modifier = Modifier
                    .border(1.dp, Color.Gray, shape = RoundedCornerShape(5.dp))
                    .padding(10.dp)
            )
        }
        Spacer(modifier = Modifier.height(20.dp))

        Button(
            onClick = {
                viewModel.saveBooking(selectedField, selectedDate, selectedTime)
            },
            modifier = Modifier.fillMaxWidth(),
            colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF007B83))
        ) {
            Text("Booking", color = Color.White)
        }

        Spacer(modifier = Modifier.height(20.dp))

        if (selectedDate != null) {
            Text("Waktu yang dipilih: ${selectedDate.toString()} - $selectedTime")
        }
    }
}

@Composable
fun DropdownMenu(
    options: List<String>,
    selectedOption: String,
    onOptionSelected: (String) -> Unit
) {
    var expanded by remember { mutableStateOf(false) }
    Box {
        TextField(
            value = selectedOption,
            onValueChange = {},
            readOnly = true,
            label = { Text("Bidang Keahlian") },
            trailingIcon = {
                IconButton(onClick = { expanded = true }) {
                    Icon(Icons.Default.ArrowDropDown, contentDescription = null)
                }
            },
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 10.dp),
            textStyle = MaterialTheme.typography.headlineSmall.copy(fontSize = 14.sp)

        )
        DropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false },
            modifier = Modifier.fillMaxWidth()
        ) {
            options.forEach { option ->
                DropdownMenuItem(
                    onClick = {
                        onOptionSelected(option)
                        expanded = false
                    },
                    text = {
                        Text(option)
                    }
                )

            }
        }
    }
}

@Composable
fun CalendarPicker(onDateSelected: (LocalDate) -> Unit) {
    val currentMonth = "June 2024"
    val daysOfWeek = listOf("Sun", "Mon", "Tue", "Wed", "Thu", "Fri", "Sat")
    val dates = (1..30).toList()

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(10.dp)
            .background(Color.White, shape = RoundedCornerShape(10.dp))
            .border(1.dp, Color(0xFFD1D5DB), shape = RoundedCornerShape(10.dp))
            .padding(10.dp)
    ) {
        Text(
            text = currentMonth,
            style = MaterialTheme.typography.headlineSmall,
            modifier = Modifier.padding(bottom = 10.dp)
        )
        Row(
            modifier = Modifier
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            daysOfWeek.forEach { day ->
                Text(
                    text = day,
                    style = MaterialTheme.typography.bodySmall.copy(color = Color(0xFF6B7280)),
                    modifier = Modifier.weight(1f),
                    textAlign = TextAlign.Center
                )
            }
        }
        Spacer(modifier = Modifier.height(10.dp))
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            dates.forEach { date ->
                Box(
                    contentAlignment = Alignment.Center,
                    modifier = Modifier
                        .weight(1f)
                        .padding(5.dp)
                        .background(if (date == 26) Color(0xFFA7C5EB) else Color.Transparent, shape = CircleShape)
                        .clickable { onDateSelected(LocalDate.parse("2024-06-$date")) }
                ) {
                    Text(
                        text = date.toString(),
                        style = MaterialTheme.typography.bodySmall.copy(
                            color = if (date == 26) Color.White else Color.Black
                        )
                    )
                }
            }
        }
    }
}
