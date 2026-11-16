package com.example.myfirstapp

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.selection.selectable
import androidx.compose.foundation.selection.toggleable
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Checkbox
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Slider
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.myfirstapp.ui.theme.MyFirstAppTheme
import kotlin.math.roundToInt

private val programmes = listOf(
    "Software Engineering Technology",
    "Computer Science",
    "Information Technology"
)
private val studyModes = listOf("On campus", "Online")

@Composable
fun StudentRegistrationScreen(modifier: Modifier = Modifier) {
    // ---- Form state: one variable for every control ----
    var fullName by remember { mutableStateOf("") }
    var rollNumber by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }
    var programme by remember { mutableStateOf<String?>(null) }
    var menuOpen by remember { mutableStateOf(false) }
    var studyMode by remember { mutableStateOf<String?>(null) }
    var mobileDev by remember { mutableStateOf(false) }
    var webDev by remember { mutableStateOf(false) }
    var softwareTesting by remember { mutableStateOf(false) }
    var courseUpdates by remember { mutableStateOf(false) }
    var studyHours by remember { mutableFloatStateOf(0f) }

    // ---- Screen state ----
    var attempted by remember { mutableStateOf(false) }   // Register pressed at least once
    var success by remember { mutableStateOf(false) }     // show the success message

    // ---- Validation (recalculated on every change, shown only after Register) ----
    val nameError = attempted && fullName.trim().isEmpty()
    val rollError = attempted && rollNumber.trim().isEmpty()
    val emailError = attempted && email.trim().isEmpty()
    val programmeError = attempted && programme == null
    val modeError = attempted && studyMode == null

    Column(
        modifier = modifier
            .fillMaxWidth()
            .imePadding()                         // keeps fields visible above the keyboard
            .verticalScroll(rememberScrollState())  // scrollable form
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        // ---- Heading ----
        Column {
            Text(
                text = "Student Registration",
                style = MaterialTheme.typography.headlineMedium,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.primary
            )
            Text(
                text = "Fill in the details below. Fields marked * are required.",
                style = MaterialTheme.typography.bodyMedium
            )
        }

        // ---- Section 1: personal details ----
        SectionCard(title = "Personal Details") {
            OutlinedTextField(
                value = fullName,
                onValueChange = { fullName = it; success = false },
                label = { Text("Full name *") },
                singleLine = true,
                isError = nameError,
                supportingText = { if (nameError) Text("Full name is required") },
                modifier = Modifier.fillMaxWidth()
            )
            OutlinedTextField(
                value = rollNumber,
                onValueChange = { rollNumber = it; success = false },
                label = { Text("Roll number *") },
                singleLine = true,
                isError = rollError,
                supportingText = { if (rollError) Text("Roll number is required") },
                modifier = Modifier.fillMaxWidth()
            )
            OutlinedTextField(
                value = email,
                onValueChange = { email = it; success = false },
                label = { Text("Email *") },
                singleLine = true,
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email),
                isError = emailError,
                supportingText = { if (emailError) Text("Email is required") },
                modifier = Modifier.fillMaxWidth()
            )
        }

        // ---- Section 2: programme dropdown ----
        SectionCard(title = "Programme *") {
            Box {
                OutlinedButton(
                    onClick = { menuOpen = true },
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text(
                        text = programme ?: "Select programme",
                        modifier = Modifier.weight(1f)
                    )
                    Text("\u25BE")
                }
                DropdownMenu(
                    expanded = menuOpen,
                    onDismissRequest = { menuOpen = false }
                ) {
                    programmes.forEach { option ->
                        DropdownMenuItem(
                            text = { Text(option) },
                            onClick = {
                                programme = option
                                menuOpen = false
                                success = false
                            }
                        )
                    }
                }
            }
            if (programmeError) ErrorText("Please select a programme")
        }

        // ---- Section 3: study mode (radio group) ----
        SectionCard(title = "Study Mode *") {
            studyModes.forEach { mode ->
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .selectable(
                            selected = studyMode == mode,
                            onClick = { studyMode = mode; success = false },
                            role = Role.RadioButton
                        )
                        .padding(vertical = 4.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    RadioButton(selected = studyMode == mode, onClick = null)
                    Text(text = mode, modifier = Modifier.padding(start = 12.dp))
                }
            }
            if (modeError) ErrorText("Please choose a study mode")
        }

        // ---- Section 4: interests (independent checkboxes) ----
        SectionCard(title = "Interests (optional)") {
            CheckboxRow("Mobile Development", mobileDev) { mobileDev = it; success = false }
            CheckboxRow("Web Development", webDev) { webDev = it; success = false }
            CheckboxRow("Software Testing", softwareTesting) { softwareTesting = it; success = false }
        }

        // ---- Section 5: switch and slider ----
        SectionCard(title = "Preferences (optional)") {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .toggleable(
                        value = courseUpdates,
                        onValueChange = { courseUpdates = it; success = false },
                        role = Role.Switch
                    ),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text("Receive course updates")
                Switch(checked = courseUpdates, onCheckedChange = null)
            }

            Text(
                text = "Weekly study hours: ${studyHours.roundToInt()}",
                fontWeight = FontWeight.SemiBold
            )
            Slider(
                value = studyHours,
                onValueChange = { studyHours = it.roundToInt().toFloat(); success = false },
                valueRange = 0f..20f,
                steps = 19            // 21 whole-hour positions: 0, 1, 2 ... 20
            )
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text("0", style = MaterialTheme.typography.bodySmall)
                Text("20", style = MaterialTheme.typography.bodySmall)
            }
        }

        // ---- Success message ----
        if (success) {
            Card(
                colors = CardDefaults.cardColors(
                    containerColor = MaterialTheme.colorScheme.primaryContainer
                ),
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(
                    text = "Registration form completed successfully",
                    modifier = Modifier.padding(16.dp),
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.onPrimaryContainer
                )
            }
        }

        // ---- Buttons ----
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            Button(
                onClick = {
                    attempted = true
                    success = fullName.trim().isNotEmpty() &&
                            rollNumber.trim().isNotEmpty() &&
                            email.trim().isNotEmpty() &&
                            programme != null &&
                            studyMode != null
                },
                modifier = Modifier.weight(1f)
            ) { Text("Register") }

            OutlinedButton(
                onClick = {
                    fullName = ""
                    rollNumber = ""
                    email = ""
                    programme = null
                    menuOpen = false
                    studyMode = null
                    mobileDev = false
                    webDev = false
                    softwareTesting = false
                    courseUpdates = false
                    studyHours = 0f
                    attempted = false
                    success = false
                },
                modifier = Modifier.weight(1f)
            ) { Text("Reset") }
        }

        Spacer(modifier = Modifier.height(24.dp))
    }
}

// ---- Small reusable pieces ----

@Composable
private fun SectionCard(title: String, content: @Composable ColumnScope.() -> Unit) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surfaceVariant
        )
    ) {
        Column(
            modifier = Modifier.padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Text(
                text = title,
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.SemiBold
            )
            content()
        }
    }
}

@Composable
private fun CheckboxRow(label: String, checked: Boolean, onCheckedChange: (Boolean) -> Unit) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .toggleable(
                value = checked,
                onValueChange = onCheckedChange,
                role = Role.Checkbox
            )
            .padding(vertical = 4.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Checkbox(checked = checked, onCheckedChange = null)
        Text(text = label, modifier = Modifier.padding(start = 12.dp))
    }
}

@Composable
private fun ErrorText(message: String) {
    Text(
        text = message,
        color = MaterialTheme.colorScheme.error,
        style = MaterialTheme.typography.bodySmall,
        modifier = Modifier.padding(start = 4.dp)
    )
}

@Preview(showBackground = true)
@Composable
private fun StudentRegistrationScreenPreview() {
    MyFirstAppTheme {
        StudentRegistrationScreen()
    }
}