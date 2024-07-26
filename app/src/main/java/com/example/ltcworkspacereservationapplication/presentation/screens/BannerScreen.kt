package com.example.ltcworkspacereservationapplication.presentation.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Card
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.unit.dp
import com.example.ltcworkspacereservationapplication.presentation.utils.Spacing
import com.example.ltcworkspacereservationapplication.presentation.utils.color.AppColor

@Composable
fun BannerScreen(bookingId:Int,floorNumber: Int, seatNumber: Int, onSubmit: () -> Unit, onCancel: () -> Unit) {
    Card(
        shape = RoundedCornerShape(Spacing.Size_10),
        backgroundColor = Color(0xFFF5F5F5),
        modifier = Modifier
            .padding(top = 5.dp)
            .semantics { contentDescription = "Booking details card" }
    ) {
        Row(
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.padding(Spacing.Size_5)
        ) {
            Column(modifier = Modifier.weight(1f), verticalArrangement = Arrangement.SpaceBetween) {
                Text(text = "Booking id : ${bookingId}")
            Column(
                modifier = Modifier
                    .weight(1f)
                    .semantics { contentDescription = "Booking details" },
                verticalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    text = "Booking id : 12345",
                    modifier = Modifier.semantics { contentDescription = "Booking ID: 12345" }
                )
                Spacer(modifier = Modifier.height(10.dp))
                Text(text = "Floor Number : ${floorNumber}")
                Text(text = "Floor Number : 5",
                    modifier = Modifier.semantics {
                        contentDescription = "Floor Number: 5"
                    }
                )
                Spacer(modifier = Modifier.height(10.dp))
                Text(text = "Seat Number : ${seatNumber}")
                Text(text = "Seat Number : 23",
                    modifier = Modifier.semantics { contentDescription = "Seat Number: 23" }
                )
            }
            Column(
                verticalArrangement = Arrangement.SpaceAround,
                modifier = Modifier.semantics { contentDescription = "Actions" }
            ) {
                Button(
                    colors = ButtonDefaults.buttonColors(AppColor.primaryColor),
                    onClick = onSubmit,
                    modifier = Modifier.semantics { contentDescription = "Reserve button" }

                ) {
                    Text(text = "Reserve", color = AppColor.backgroundColor)

                }
                Spacer(modifier = Modifier.width(10.dp))
                Button(
                    colors = ButtonDefaults.buttonColors(AppColor.errorColor),
                    onClick = onCancel,
                    modifier = Modifier.semantics { contentDescription = "Cancel button" }

                ) {
                    Text(text = "Cancel", color = AppColor.backgroundColor)

                }
            }
        }

    }

}

