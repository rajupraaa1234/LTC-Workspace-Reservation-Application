package com.example.ltcworkspacereservationapplication.presentation.composable.History

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Card
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import com.example.ltcworkspacereservationapplication.domain.model.AvailabilityType
import com.example.ltcworkspacereservationapplication.domain.model.History.CabinHistoryModel
import com.example.ltcworkspacereservationapplication.presentation.utils.Spacing
import com.example.ltcworkspacereservationapplication.presentation.utils.color.AppColor

@Composable
fun CabinHistoryComposablePage(cabinHistoryList: List<CabinHistoryModel>) {
    LazyColumn(modifier = Modifier.padding(top = Spacing.Size_10)) {
        items(cabinHistoryList) { item ->
            CabinHistoryItem(item)
        }
    }
}


@Composable
private fun CabinHistoryItem(item: CabinHistoryModel) {
    Surface(
        modifier = Modifier.fillMaxSize(),
    ) {
        ElevatedCard(item)
    }
}

@Composable
private fun ElevatedCard(item: CabinHistoryModel) {
    Card(
        shape = MaterialTheme.shapes.medium,
        backgroundColor = AppColor.whiteColor,
        elevation = Spacing.Size_8,
        modifier = Modifier
            .padding(Spacing.Size_10)
            .fillMaxWidth()
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(Spacing.Size_10),
            horizontalArrangement = Arrangement.Absolute.SpaceAround,
            verticalAlignment = Alignment.CenterVertically
        ) {
            with(item) {
                Column {
                    Text(
                        text = "Meeting Room Id : $meetingRoomId",
                        style = MaterialTheme.typography.subtitle2,
                    )
                    Text(
                        text = "Status : $status",
                        color = if (status == AvailabilityType.RESERVED.type) AppColor.primaryColorLight else Color.Red,
                        style = MaterialTheme.typography.subtitle2,
                    )
                }
                Column {
                    Text(
                        text = "Floor : $floor",
                        style = MaterialTheme.typography.caption,
                    )
                    Text(
                        text = "Booking Date : $date",
                        style = MaterialTheme.typography.caption,
                    )
                    Text(
                        text = "Booking Time : $time",
                        style = MaterialTheme.typography.caption,
                    )
                }
            }
        }
    }
}