package com.example.ltcworkspacereservationapplication.presentation.composable.HomePage

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.asPaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBars
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.MaterialTheme
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.ltcworkspacereservationapplication.domain.model.AvailabilityType
import com.example.ltcworkspacereservationapplication.domain.model.DeskReservation.Response.DeskResponseItemModel
import com.example.ltcworkspacereservationapplication.domain.model.MeetingItemModel
import com.example.ltcworkspacereservationapplication.presentation.composable.ConfirmationPopup
import com.example.ltcworkspacereservationapplication.presentation.composable.EmptyMessageComposable
import com.example.ltcworkspacereservationapplication.presentation.utils.Spacing
import com.example.ltcworkspacereservationapplication.presentation.utils.color.AppColor


@Composable
fun CustomGridItem(
    item: MeetingItemModel,
    onClickItem: (MeetingItemModel, Int) -> Unit,
    onSubmit: (String, String, String, String) -> Unit,
    index: Int
) {
    var showDialog by remember { mutableStateOf(false) }
    if (showDialog) {
        ConfirmationPopup(
            onDismiss = { showDialog = false },
            onSubmit = { startTime, endTime, capacity ->
                onSubmit(startTime, endTime, capacity, item.meetingRoomId.toString())
                showDialog = false
            })
    }

    Card(
        shape = RoundedCornerShape(Spacing.Size_10),
        backgroundColor = Color(0xFFF5F5F5),
        modifier = Modifier.padding(8.dp)
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center,
            modifier = Modifier
                .padding(Spacing.Size_5, top = Spacing.Size_8, bottom = Spacing.Size_8)
                .clickable {
                    showDialog = true
                    onClickItem(item, index)
                }
        ) {
            Row(
                horizontalArrangement = Arrangement.SpaceAround,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Image(
                    painter = painterResource(item.imageId),
                    contentDescription = "Filter Icon",
                    Modifier
                        .size(Spacing.Size_40)
                )
                Column(modifier = Modifier.padding(Spacing.Size_10)) {
                    Text(text = "R-${item.meetingRoomId}", style = MaterialTheme.typography.body2)
                    Text(text = "Size-${item.capacity}", style = MaterialTheme.typography.subtitle2)
                }
            }

            if(item.reservedSlot.size > 0) {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(Spacing.Size_5)
                        .background(AppColor.primaryColorLight)
                ) {
                    LazyRow(modifier = Modifier.padding(2.dp)) {
                        items(item.reservedSlot.size) { index ->
                            if (item.reservedSlot.size == index + 1)
                                Text(text = item.reservedSlot[index], color = Color.White)
                            else
                                Text(text = "${item.reservedSlot[index]}, ", color = Color.White)
                        }
                    }
                }
            }
        }
    }
}


@Composable
fun CabinGridList(
    items: List<MeetingItemModel>,
    onClickItem: (MeetingItemModel, Int) -> Unit,
    onSubmit: (String, String, String, String) -> Unit
) {
    Column(
        verticalArrangement = Arrangement.Center,
        modifier = Modifier.padding(Spacing.Size_8)
    ) {
        Box(
            modifier = Modifier
                .padding(top = Spacing.Size_5, bottom = Spacing.Size_10)
                .weight(1f)
        ) {
            if (items.size == 0) {
                EmptyMessageComposable(
                    title = "No meetings room available at this floor",
                    subtitle = "There is no data to show you right now."
                )
            } else {
                LazyVerticalGrid(
                    columns = GridCells.Fixed(2),
                ) {
                    items(items.size) { index ->
                        CustomGridItem(items[index], onClickItem, onSubmit, index)
                    }
                }
            }
        }
        TimeCard(false)
    }
}


@Composable
fun DeskGridList(
    items: List<DeskResponseItemModel>,
    onClickItem: (DeskResponseItemModel, Int) -> Unit,
    onSubmit: () -> Unit
) {
    var isAnyItemClicked by remember { mutableStateOf(false) }

    Column(
        verticalArrangement = Arrangement.Center,
        modifier = Modifier.padding(Spacing.Size_8)
    ) {
        Box(
            modifier = Modifier
                .padding(top = Spacing.Size_5, bottom = Spacing.Size_10)
                .weight(1f)
        ) {
            if (items.size == 0) {
                EmptyMessageComposable(
                    title = "No desk available at this floor",
                    subtitle = "There is no data to show you at the moment."
                )
            } else {
                LazyVerticalGrid(
                    columns = GridCells.Fixed(5),
                ) {
                    items(items.size) { index ->
                        DeskGridItem(items[index], onClickItem = { item, idx ->
                            onClickItem(item, idx)
                            isAnyItemClicked = true
                        }, index)
                    }
                }
            }
        }

        Row(
            horizontalArrangement = Arrangement.SpaceAround,
            verticalAlignment = Alignment.CenterVertically,
        ) {

            TimeCard(true)
            Spacer(modifier = Modifier.weight(1f))
            Button(
                onClick = {
                    onSubmit()
                },

                colors = ButtonDefaults.buttonColors(
                    containerColor = if (isAnyItemClicked) AppColor.primaryColor else AppColor.primaryColorLight,
                    disabledContainerColor = AppColor.primaryColorLight
                ),
                enabled = isAnyItemClicked,
                modifier = Modifier
                    .background(if (isAnyItemClicked) AppColor.primaryColor else AppColor.primaryColorLight)
                    .height(40.dp)
                    .width(80.dp)
            ) {
                Text(
                    text = "Book",
                    color = AppColor.backgroundColor,
                    fontSize = 10.sp
                )
            }
        }
    }
}

@Composable
fun TimeCard(isBookedShow: Boolean) {
    Card(
        shape = RoundedCornerShape(Spacing.Size_10),
        backgroundColor = Color(0xFFF5F5F5),
    ) {
        Row(
            horizontalArrangement = Arrangement.Start,
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.padding(Spacing.Size_5)
        ) {
            Indicator("Reserved", AppColor.reservedIndicator)
            Indicator("Selected", AppColor.selectedIndicator)
            Indicator("Available", AppColor.availableIndicator)
            if (isBookedShow)
                Indicator("Booked", AppColor.bookedDeskBackgroundColour)
        }
    }
}

@Composable
fun Indicator(text: String, color: Color) {
    Row(
        modifier = Modifier.padding(2.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Center
    ) {
        Canvas(modifier = Modifier.size(Spacing.Size_10)) {
            drawCircle(
                color = color,
                radius = size.minDimension / 2
            )
        }
        Spacer(modifier = Modifier.width(Spacing.Size_5))
        Text(
            text = text,
            style = androidx.compose.material3.MaterialTheme.typography.labelSmall,
            color = Color.Gray
        )
    }
}


@Composable
fun DeskGridItem(item: DeskResponseItemModel, onClickItem: (DeskResponseItemModel, Int) -> Unit, index: Int) {
    val bottomPadding = WindowInsets.navigationBars.asPaddingValues().calculateBottomPadding()
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
        modifier = Modifier
            .padding(Spacing.Size_8, bottom = bottomPadding)
            .clickable {
                if (item.reservationStatus == AvailabilityType.AVAILABLE.type) onClickItem(
                    item,
                    index
                )
            }
    ) {
        Image(
            painter = painterResource(id = item.imageId),
            contentDescription = "Filter Icon",
            Modifier
                .size(Spacing.Size_40)
        )
        Text(
            text = "${item.floorNumber} - ${item.seatNumber}",
            style = MaterialTheme.typography.body2
        )
    }
}