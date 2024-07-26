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
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.unit.sp
import com.example.ltcworkspacereservationapplication.domain.model.History.MeetingHistory.MeetingRoomHistoryResponse
import com.example.ltcworkspacereservationapplication.presentation.composable.EmptyMessageComposable
import com.example.ltcworkspacereservationapplication.presentation.utils.Spacing
import com.example.ltcworkspacereservationapplication.presentation.utils.color.AppColor

@Composable
fun CabinHistoryComposablePage(cabinHistoryList: List<MeetingRoomHistoryResponse>) {
    if (cabinHistoryList.size == 0) {
        EmptyMessageComposable(
            title = "There is no any history available for meeting room reservation",
            subtitle = "You can book your interesting workSpot from home screen"
        )
    } else {
        LazyColumn(modifier = Modifier.padding(top = Spacing.Size_10)) {
            items(cabinHistoryList) { item ->
                CabinHistoryItem(item)
            }
        }
    }
}


@Composable
private fun CabinHistoryItem(item: MeetingRoomHistoryResponse) {
    Surface(
        modifier = Modifier.fillMaxSize().semantics { contentDescription = "Meeting room reservation history item" },
    ) {
        ElevatedCard(item)
    }
}


@Composable
fun extractHourAndMinute(time: String): String {
    val parts = time.split(":")
    if (parts.size >= 2) {
        return "${parts[0]}:${parts[1]}"
    }
    return ""
}

@Composable
private fun ElevatedCard(item: MeetingRoomHistoryResponse) {
    Card(
        shape = MaterialTheme.shapes.medium,
        backgroundColor = AppColor.whiteColor,
        elevation = Spacing.Size_8,
        modifier = Modifier
            .padding(Spacing.Size_10)
            .fillMaxWidth().semantics { contentDescription = "Meeting room reservation details" }
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(Spacing.Size_10),
            horizontalArrangement = Arrangement.Absolute.SpaceAround,
            verticalAlignment = Alignment.CenterVertically
        ) {
            with(item) {
                Column(
                    modifier = Modifier.semantics { contentDescription = "Meeting room details" }
                ) {
                    Text(
                        text = "Meeting Room Id : R:$floorNumber-$roomNumber",
                        style = MaterialTheme.typography.subtitle1.copy(fontSize = 12.sp),
                    )
                    Text(
                        text = "Status : Booked",
                        color =  AppColor.primaryColorLight,
                        style = MaterialTheme.typography.subtitle2,
                    )
                }
                Column(
                    modifier = Modifier.semantics { contentDescription = "Booking details" }
                ) {
                    Text(
                        text = "Floor : $bookingId",
                        style = MaterialTheme.typography.caption,
                    )
                    Text(
                        text = "Booking Date : $date",
                        style = MaterialTheme.typography.caption,
                    )
                    Text(
                        text = "Booking Date : LTC$bookingId",
                        style = MaterialTheme.typography.caption,
                    )
                    Text(
                        text = "Booking Time : ${extractHourAndMinute(startTime)}-${extractHourAndMinute(endTime)}",
                        style = MaterialTheme.typography.caption,
                    )
                }
            }
        }
    }
}