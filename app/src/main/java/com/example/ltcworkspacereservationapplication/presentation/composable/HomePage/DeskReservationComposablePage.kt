package com.example.ltcworkspacereservationapplication.presentation.composable.HomePage
import androidx.compose.runtime.Composable
import com.example.ltcworkspacereservationapplication.domain.model.DeskReservation.Response.DeskResponseItemModel


@Composable
internal fun DeskReservationComposablePage(
    deskList: List<DeskResponseItemModel>,
    onClickItem: (DeskResponseItemModel, Int)-> Unit,
    onSubmit: ()-> Unit
) {
    DeskGridList(items = deskList,onClickItem,onSubmit)
}
