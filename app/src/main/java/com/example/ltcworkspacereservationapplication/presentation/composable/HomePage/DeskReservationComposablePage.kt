package com.example.ltcworkspacereservationapplication.presentation.composable.HomePage
import androidx.compose.runtime.Composable
import com.example.ltcworkspacereservationapplication.domain.model.DeskItemModel


@Composable
internal fun DeskReservationComposablePage(
    deskList: List<DeskItemModel>,
    onClickItem: (DeskItemModel,Int)-> Unit
) {
    DeskGridList(items = deskList,onClickItem)
}
