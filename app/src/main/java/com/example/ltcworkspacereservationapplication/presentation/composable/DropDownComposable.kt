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
import com.example.ltcworkspacereservationapplication.presentation.utils.Floors
import com.example.ltcworkspacereservationapplication.presentation.utils.Spacing

@Composable
fun CustomDropdownMenu(modifier: Modifier, onSelect: (Int) -> Unit) {
    var expanded by remember { mutableStateOf(false) }
    var selectedText by remember { mutableStateOf("Select floor") }

    Box(
        modifier = modifier
            .padding(Spacing.Size_10)
            .clickable { expanded = true },
    ) {
        Row(
            horizontalArrangement = Arrangement.SpaceAround
        ) {
            Text(
                text = selectedText,
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
                            .align(alignment = Alignment.CenterHorizontally)
                    ) {
                        Text(floor.text)
                    }
                }
            }
            Spacer(modifier = Modifier.weight(1f))
            if (expanded) {
                Icon(
                    imageVector = Icons.Filled.KeyboardArrowUp,
                    contentDescription = null
                )
            } else {
                Icon(
                    imageVector = Icons.Filled.KeyboardArrowDown,
                    contentDescription = null
                )
            }
        }
    }
}

