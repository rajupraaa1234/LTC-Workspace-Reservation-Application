package com.example.ltcworkspacereservationapplication.presentation.composable.HomePage

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.asPaddingValues
import androidx.compose.foundation.layout.navigationBars
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.material.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import com.example.ltcworkspacereservationapplication.R
import com.example.ltcworkspacereservationapplication.domain.model.DeskItemModel
import com.example.ltcworkspacereservationapplication.domain.model.MeetingItemModel
import com.example.ltcworkspacereservationapplication.presentation.composable.ConfirmationPopup
import com.example.ltcworkspacereservationapplication.presentation.composable.CustomButton
import com.example.ltcworkspacereservationapplication.presentation.utils.Spacing


@Composable
fun CustomGridItem(item: MeetingItemModel, onClickItem: (MeetingItemModel) -> Unit,onSubmit : (String,String,String,String)-> Unit) {
    val bottomPadding = WindowInsets.navigationBars.asPaddingValues().calculateBottomPadding()
    var showDialog by remember { mutableStateOf(false) }

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
        modifier = Modifier
            .padding(Spacing.Size_8, bottom = bottomPadding)
            .clickable {
                showDialog = true
                onClickItem(item)
            }
    ) {
        Image(
            painter = if(item.reservedSlot.size == 0) painterResource(id = R.drawable.selectedcabin) else painterResource(id = R.drawable.nonselectedcabin),
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
    onClickItem: (MeetingItemModel)-> Unit,
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
                    CustomGridItem(items[index], onClickItem,onSubmit)
                }
            }
        }
    }
}


@Composable
fun DeskGridList(
    items: List<DeskItemModel>,
    onClickItem: (DeskItemModel)-> Unit
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
                    DeskGridItem(items[index],onClickItem)
                }
            }
        }

        CustomButton(modifier = Modifier.align(Alignment.End), "Book") {

        }
    }
}



@Composable
fun DeskGridItem(item: DeskItemModel, onClickItem: (DeskItemModel) -> Unit) {
    val bottomPadding = WindowInsets.navigationBars.asPaddingValues().calculateBottomPadding()
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
        modifier = Modifier
            .padding(Spacing.Size_8, bottom = bottomPadding)
            .clickable { onClickItem(item) }
    ) {
        Image(
            painter = if(item.status == "reserved") painterResource(id = R.drawable.reservedseat) else painterResource(id = R.drawable.nonreservedseeat),
            contentDescription = "Filter Icon",
            Modifier
                .size(Spacing.Size_40)
        )
        Text(text = item.deskId, style = MaterialTheme.typography.body2)
    }
}
