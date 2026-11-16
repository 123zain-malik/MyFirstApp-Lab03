package com.example.myfirstapp

import android.content.res.Configuration
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.myfirstapp.ui.theme.MyFirstAppTheme

@Composable
fun CampusEventCard(
    title: String,
    date: String,
    venue: String,
    modifier: Modifier = Modifier
) {
    // Surface = rounded card with matching background + text colour
    Surface(
        modifier = modifier.fillMaxWidth(),
        shape = RoundedCornerShape(16.dp),
        color = MaterialTheme.colorScheme.surfaceVariant,
        contentColor = MaterialTheme.colorScheme.onSurfaceVariant
    ) {
        Row(
            modifier = Modifier.padding(12.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            Image(
                painter = painterResource(id = R.drawable.event_workshop),
                // null = decorative image. The title/date/venue next to it already
                // tell the full story, so a screen reader should not repeat it.
                contentDescription = null,
                modifier = Modifier
                    .size(96.dp)
                    .clip(RoundedCornerShape(12.dp)),
                contentScale = ContentScale.Crop
            )
            Column(
                modifier = Modifier.weight(1f),
                verticalArrangement = Arrangement.spacedBy(4.dp)
            ) {
                Text(text = title, style = MaterialTheme.typography.titleMedium)
                Text(text = date, style = MaterialTheme.typography.bodyMedium)
                Text(text = venue, style = MaterialTheme.typography.bodyMedium)
            }
        }
    }
}

// Normal width: 360dp
@Preview(name = "Normal 360dp", widthDp = 360, showBackground = true)
@Composable
fun CampusEventCardNormalPreview() {
    MyFirstAppTheme {
        CampusEventCard(
            title = "Android Compose Workshop",
            date = "12 November 2026, 10:00 AM",
            venue = "Seminar Hall, SE Department",
            modifier = Modifier.padding(8.dp)
        )
    }
}

// Narrow width: 280dp, longer title and venue to test wrapping
@Preview(name = "Narrow 280dp", widthDp = 280, showBackground = true)
@Composable
fun CampusEventCardNarrowPreview() {
    MyFirstAppTheme {
        CampusEventCard(
            title = "Introduction to Mobile Application Development with Jetpack Compose",
            date = "12 November 2026, 10:00 AM",
            venue = "Main Seminar Hall, Department of Software Engineering, UAJK",
            modifier = Modifier.padding(8.dp)
        )
    }
}

// Dark theme
@Preview(
    name = "Dark theme",
    widthDp = 360,
    showBackground = true,
    uiMode = Configuration.UI_MODE_NIGHT_YES
)
@Composable
fun CampusEventCardDarkPreview() {
    MyFirstAppTheme(darkTheme = true) {
        CampusEventCard(
            title = "Android Compose Workshop",
            date = "12 November 2026, 10:00 AM",
            venue = "Seminar Hall, SE Department",
            modifier = Modifier.padding(8.dp)
        )
    }
}