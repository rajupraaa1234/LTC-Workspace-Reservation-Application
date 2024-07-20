package com.example.ltcworkspacereservationapplication.presentation.composable.HomePage

import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.asPaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBars
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import com.example.ltcworkspacereservationapplication.presentation.composable.CustomButton
import com.example.ltcworkspacereservationapplication.presentation.composable.CustomDatePickerDialog
import com.example.ltcworkspacereservationapplication.presentation.utils.Spacing
import com.example.ltcworkspacereservationapplication.presentation.utils.color.AppColor


@Composable
fun CustomGridItem(iconResId: Int, text: String) {
    val bottomPadding = WindowInsets.navigationBars.asPaddingValues().calculateBottomPadding()

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
        modifier = Modifier
            .padding(Spacing.Size_8, bottom = bottomPadding)
    ) {
        Image(
            painter = painterResource(id = iconResId),
            contentDescription = "Filter Icon",
            Modifier
                .size(Spacing.Size_40)
        )
        Text(text = text, style = MaterialTheme.typography.body2)
    }
}

@Composable
fun GridList(
    items: List<Pair<Int, String>>,
    isBookButtonVisible: Boolean,
) {
    Column(
        verticalArrangement = Arrangement.Center,
        modifier = Modifier.padding(Spacing.Size_8)
    ) {
        Box(
            modifier = Modifier
                .padding(top = Spacing.Size_5, bottom = Spacing.Size_10)
                .weight(1f)
        ) {
            LazyVerticalGrid(
                columns = GridCells.Fixed(5),
            ) {
                items(items.size) { index ->
                    val (iconResId, text) = items[index]
                    CustomGridItem(iconResId = iconResId, text = text)
                }
            }
        }
        if (isBookButtonVisible) {
                CustomButton(modifier = Modifier.align(Alignment.End), "Book") {

                }
            }
        }
    }
