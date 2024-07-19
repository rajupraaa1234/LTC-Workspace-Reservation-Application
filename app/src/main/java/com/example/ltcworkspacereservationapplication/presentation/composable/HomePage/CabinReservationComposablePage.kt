package com.example.ltcworkspacereservationapplication.presentation.composable.HomePage

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable

@Composable
internal fun CabinReservationComposablePage(cabinList: List<Pair<Int, String>>) {
    GridList(items = cabinList,false)
}