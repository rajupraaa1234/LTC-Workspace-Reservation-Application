package com.example.ltcworkspacereservationapplication.presentation.composable.HomePage

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.asPaddingValues
import androidx.compose.foundation.layout.navigationBars
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
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
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.ltcworkspacereservationapplication.R
import com.example.ltcworkspacereservationapplication.domain.model.DeskItemModel
import com.example.ltcworkspacereservationapplication.domain.model.MeetingItemModel
import com.example.ltcworkspacereservationapplication.presentation.composable.ConfirmationPopup
import com.example.ltcworkspacereservationapplication.presentation.composable.CustomButton
import com.example.ltcworkspacereservationapplication.presentation.utils.Spacing
import com.example.ltcworkspacereservationapplication.presentation.utils.color.AppColor


@Composable
fun CustomGridItem(
    item: MeetingItemModel,
    onClickItem: (MeetingItemModel, Int) -> Unit,
    onSubmit: (String, String, String, String) -> Unit,
    index: Int
) {
    val bottomPadding = WindowInsets.navigationBars.asPaddingValues().calculateBottomPadding()
    var showDialog by remember { mutableStateOf(false) }

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
        modifier = Modifier
            .padding(Spacing.Size_8, bottom = bottomPadding)
            .clickable {
                showDialog = true
                onClickItem(item, index)
            }
    ) {
        Image(
            painter = painterResource(item.imageId),
            contentDescription = "Filter Icon",
            Modifier
                .size(Spacing.Size_40)
        )
        Text(text = item.meetingRoomId, style = MaterialTheme.typography.body2)
    }
    if (showDialog) {
        ConfirmationPopup(onDismiss = { showDialog = false }, onSubmit = { startTime, endTime, capacity ->
            onSubmit(startTime, endTime, capacity, item.meetingRoomId)
            showDialog = false
        })
    }
}

@Composable
fun CabinGridList(
    items: List<MeetingItemModel>,
    onClickItem: (MeetingItemModel,Int)-> Unit,
    onSubmit: (String,String,String,String) -> Unit
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
            LazyVerticalGrid(
                columns = GridCells.Fixed(5),
            ) {
                items(items.size) { index ->
                    CustomGridItem(items[index], onClickItem,onSubmit,index)
                }
            }
        }
        TimeCard()
    }
}


@Composable
fun DeskGridList(
    items: List<DeskItemModel>,
    onClickItem: (DeskItemModel,Int)-> Unit
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

        Row(
            horizontalArrangement = Arrangement.SpaceAround,
            verticalAlignment = Alignment.CenterVertically,
        ) {

            TimeCard()
            Spacer(modifier = Modifier.weight(1f))
            Button(
                onClick = {
                },
                colors = ButtonDefaults.buttonColors(
                    containerColor = if (isAnyItemClicked) AppColor.primaryColor else AppColor.primaryColorLight,
                    disabledContainerColor = AppColor.primaryColorLight
                ),
                enabled = isAnyItemClicked,
                modifier = Modifier
                    .background(if (isAnyItemClicked) AppColor.primaryColor else AppColor.primaryColorLight)
                    .border(
                        1.dp,
                        if (isAnyItemClicked) AppColor.primaryColor else AppColor.primaryColorLight
                    )
            ) {
                Text(
                    text = "Book",
                    color = AppColor.backgroundColor,
                    fontSize = 12.sp,
                    fontWeight = FontWeight.Bold
                )
            }
            }
        }
    }

@Composable
fun TimeCard() {
    Card(
        shape = RoundedCornerShape(Spacing.Size_10),
        backgroundColor = Color(0xFFF5F5F5),
        modifier = Modifier
    ) {
        Row(
            horizontalArrangement = Arrangement.Start,
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.padding(Spacing.Size_5)
        ) {
            Indicator("Reserved",AppColor.reservedIndicator)
            Indicator("Selected",AppColor.selectedIndicator)
            Indicator("Available",AppColor.availableIndicator)
        }
    }
}

@Composable
fun Indicator(text : String,color: Color){
    Row(modifier = Modifier.padding(Spacing.Size_3),
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
        Text(text = text, style = androidx.compose.material3.MaterialTheme.typography.labelSmall, color = Color.Gray)
    }
}




@Composable
fun DeskGridItem(item: DeskItemModel, onClickItem: (DeskItemModel,Int) -> Unit, index: Int) {
    val bottomPadding = WindowInsets.navigationBars.asPaddingValues().calculateBottomPadding()
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
        modifier = Modifier
            .padding(Spacing.Size_8, bottom = bottomPadding)
            .clickable { onClickItem(item, index) }
    ) {
        Image(
            painter = painterResource(id = item.imageId),
            contentDescription = "Filter Icon",
            Modifier
                .size(Spacing.Size_40)
        )
        Text(text = item.deskId, style = MaterialTheme.typography.body2)
    }
}