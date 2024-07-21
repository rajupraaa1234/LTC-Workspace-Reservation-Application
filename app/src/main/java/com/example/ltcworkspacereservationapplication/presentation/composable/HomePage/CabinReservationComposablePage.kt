package com.example.ltcworkspacereservationapplication.presentation.composable.HomePage

import androidx.compose.runtime.Composable
import com.example.ltcworkspacereservationapplication.domain.model.MeetingItemModel

@Composable
internal fun CabinReservationComposablePage(cabinList: List<MeetingItemModel>,onClickItem: (MeetingItemModel)-> Unit,onSubmit: (String,String,String,String) -> Unit) {
    CabinGridList(items = cabinList,onClickItem,onSubmit)
}