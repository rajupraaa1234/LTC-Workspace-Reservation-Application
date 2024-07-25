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
import androidx.compose.ui.unit.dp
import com.example.ltcworkspacereservationapplication.presentation.utils.Spacing
import com.example.ltcworkspacereservationapplication.presentation.utils.color.AppColor

@Composable
fun BannerScreen(onSubmit: () -> Unit, onCancel: () -> Unit) {
    Card(
        shape = RoundedCornerShape(Spacing.Size_10),
        backgroundColor = Color(0xFFF5F5F5), modifier = Modifier.padding(top = 5.dp)
    ) {
        Row(
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.padding(Spacing.Size_5)
        ) {
            Column(modifier = Modifier.weight(1f), verticalArrangement = Arrangement.SpaceBetween) {
                Text(text = "Booking id : 12345")
                Spacer(modifier = Modifier.height(10.dp))
                Text(text = "Floor Number : 5")
                Spacer(modifier = Modifier.height(10.dp))
                Text(text = "Seat Number : 23")
            }
            Column(verticalArrangement = Arrangement.SpaceAround) {
                Button(colors = ButtonDefaults.buttonColors(AppColor.primaryColor), onClick = onSubmit) {
                    Text(text = "Reserve", color = AppColor.backgroundColor)

                }
                Spacer(modifier = Modifier.width(10.dp))
                Button(colors = ButtonDefaults.buttonColors(AppColor.errorColor),onClick = onCancel) {
                    Text(text = "Cancel", color = AppColor.backgroundColor)

                }
            }
        }

    }

}

