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
import com.example.ltcworkspacereservationapplication.domain.model.History.DeskHistoryModel
import com.example.ltcworkspacereservationapplication.presentation.composable.EmptyMessageComposable
import com.example.ltcworkspacereservationapplication.presentation.utils.Spacing
import com.example.ltcworkspacereservationapplication.presentation.utils.color.AppColor

@Composable
fun DeskHistoryComposablePage(deskHistoryList: List<DeskHistoryModel>) {
    if (deskHistoryList.size == 0) {
        EmptyMessageComposable(
            title = "There is no any history available for desk reservation",
            subtitle = "You can book your interesting workSpot from home screen"
        )
    } else {
        LazyColumn(modifier = Modifier.padding(top = Spacing.Size_10)) {
            items(deskHistoryList) { item ->
                DeskHistoryItem(item)
            }
        }
    }
}


@Composable
private fun DeskHistoryItem(item: DeskHistoryModel) {
    Surface(
        modifier = Modifier.fillMaxSize(),
    ) {
        ElevatedCard(item)
    }
}

@Composable
private fun ElevatedCard(item: DeskHistoryModel) {
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
                .padding(Spacing.Size_16),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            with(item) {
                Column {
                    Text(
                        text = "Desk Id : $seatNumber",
                        style = MaterialTheme.typography.subtitle2,
                    )
                    Text(
                        text = "Status : $reservationStatus",
                        color = if (reservationStatus == AvailabilityType.RESERVED.type) AppColor.primaryColorLight else if (reservationStatus == AvailabilityType.BOOKED.type) AppColor.bookedDeskBackgroundColour else Color.Red,
                        style = MaterialTheme.typography.subtitle2,
                    )
                }
                Column {
                    Text(
                        text = "Floor : $floorNumber",
                        style = MaterialTheme.typography.caption,
                    )
                    Text(
                        text = "Booking Date : $date",
                        style = MaterialTheme.typography.caption,
                    )
                    Text(
                        text = "Booking Id : $bookingId",
                        style = MaterialTheme.typography.caption,
                    )
                }
            }
        }
    }
}