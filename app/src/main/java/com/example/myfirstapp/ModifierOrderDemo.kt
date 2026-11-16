package com.example.myfirstapp

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.myfirstapp.ui.theme.MyFirstAppTheme

// Same text, same colour, same padding. Only the ORDER of modifiers is different.
@Composable
fun ModifierOrderDemo() {
    Column(
        modifier = Modifier.padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(24.dp)
    ) {
        Text(
            text = "background THEN padding",
            modifier = Modifier
                .background(Color(0xFFFFC107))   // colour is applied first (to the small box)
                .padding(16.dp)                  // padding then grows the box -> inside the colour
        )
        Text(
            text = "padding THEN background",
            modifier = Modifier
                .padding(16.dp)                  // padding is applied first (outside space)
                .background(Color(0xFFFFC107))   // colour only covers the text area
        )
    }
}

@Preview(showBackground = true, widthDp = 360)
@Composable
fun ModifierOrderDemoPreview() {
    MyFirstAppTheme { ModifierOrderDemo() }
}