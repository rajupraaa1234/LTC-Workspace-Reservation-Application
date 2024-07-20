package com.example.ltcworkspacereservationapplication.presentation.composable.HomePage
import androidx.compose.runtime.Composable


@Composable
internal fun DeskReservationComposablePage(
    deskList: List<Pair<Int, String>>,
    selectedDate: String,
    onDatePickerClick: (String)->Unit
) {
    GridList(items = deskList,true,selectedDate,onDatePickerClick)
}
