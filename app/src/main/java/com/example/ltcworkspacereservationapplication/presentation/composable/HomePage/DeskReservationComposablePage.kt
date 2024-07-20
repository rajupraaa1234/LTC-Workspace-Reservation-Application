package com.example.ltcworkspacereservationapplication.presentation.composable.HomePage
import androidx.compose.runtime.Composable


@Composable
internal fun DeskReservationComposablePage(
    deskList: List<Pair<Int, String>>,
) {
    GridList(items = deskList,true)
}
