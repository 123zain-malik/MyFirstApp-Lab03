package com.example.myfirstapp

import androidx.compose.foundation.layout.Column
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.example.myfirstapp.ui.theme.MyFirstAppTheme

// Simple reusable block: event title on top, venue below it (vertical = Column).
@Composable
fun EventSummary(
    title: String,
    venue: String,
    modifier: Modifier = Modifier
) {
    Column(modifier = modifier) {
        Text(text = title, style = MaterialTheme.typography.titleMedium)
        Text(text = venue, style = MaterialTheme.typography.bodyMedium)
    }
}

// Preview 1: workshop
@Preview(showBackground = true)
@Composable
fun EventSummaryWorkshopPreview() {
    MyFirstAppTheme {
        EventSummary(
            title = "Android Compose Workshop",
            venue = "Seminar Hall, Software Engineering Department"
        )
    }
}

// Preview 2: sports day (different details)
@Preview(showBackground = true)
@Composable
fun EventSummarySportsDayPreview() {
    MyFirstAppTheme {
        EventSummary(
            title = "Annual Sports Day",
            venue = "University Sports Ground"
        )
    }
}