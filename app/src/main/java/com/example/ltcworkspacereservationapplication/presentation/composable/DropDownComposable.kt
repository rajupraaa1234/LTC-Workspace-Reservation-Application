package com.example.ltcworkspacereservationapplication.presentation.composable

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.DropdownMenu
import androidx.compose.material.DropdownMenuItem
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics
import com.example.ltcworkspacereservationapplication.domain.model.Floors
import com.example.ltcworkspacereservationapplication.presentation.utils.Spacing

@Composable
fun CustomDropdownMenu(modifier: Modifier, onSelect: (Int) -> Unit) {
    var expanded by remember { mutableStateOf(false) }
    var selectedText by remember { mutableStateOf("All Floor") }

    Box(
        modifier = modifier
            .padding(Spacing.Size_8)
            .clickable { expanded = true }
            .semantics { contentDescription = "Select floor" },
        contentAlignment = Alignment.Center
    ) {
        Row(
            horizontalArrangement = Arrangement.SpaceAround,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = selectedText,
                modifier = Modifier.semantics { contentDescription = "Selected floor: $selectedText" }
            )
            DropdownMenu(
                expanded = expanded,
                onDismissRequest = { expanded = false },
                modifier = Modifier
                    .width(Spacing.Size_140)
            ) {

                Floors.entries.forEachIndexed { index, floor ->
                    DropdownMenuItem(
                        onClick = {
                            selectedText = floor.text
                            onSelect(floor.floor)
                            expanded = false
                        },
                        modifier = Modifier
                            .align(alignment = Alignment.CenterHorizontally).semantics { contentDescription = "Floor: ${floor.text}" }
                    ) {
                        Text(floor.text)
                    }
                }
            }
            Spacer(modifier = Modifier.weight(1f))
            if (expanded) {
                Icon(
                    imageVector = Icons.Filled.KeyboardArrowUp,
                    contentDescription = "Collapse dropdown"                )
            } else {
                Icon(
                    imageVector = Icons.Filled.KeyboardArrowDown,
                    contentDescription = "Expand dropdown"
                )
            }
        }
    }
}

