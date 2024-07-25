package com.example.ltcworkspacereservationapplication.presentation.utils

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import com.example.ltcworkspacereservationapplication.presentation.utils.color.AppColor

@Composable
fun ProgressBar(isDisplayed:Boolean) {
    if(isDisplayed) {
        CircularProgressIndicator(
            modifier = Modifier.fillMaxSize().wrapContentSize(Alignment.Center),
            color = AppColor.primaryColor
        )
    }
}