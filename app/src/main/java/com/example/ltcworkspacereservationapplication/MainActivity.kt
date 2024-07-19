package com.example.ltcworkspacereservationapplication

import TabSelection
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import com.example.ltcworkspacereservationapplication.presentation.composable.DropdownMenuSample
import com.example.ltcworkspacereservationapplication.presentation.composable.HomeComposable
import com.example.ltcworkspacereservationapplication.presentation.utils.Spacing
import com.example.ltcworkspacereservationapplication.presentation.utils.color.AppColor

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            App()
        }
    }
}

@Composable
fun App() {
    Column(
        modifier = Modifier
            .padding(
                top = Spacing.Size_50,
                start = Spacing.Size_20,
                end = Spacing.Size_20,
                bottom = Spacing.Size_20
            )
    ) {
        Row(
            horizontalArrangement = Arrangement.SpaceAround,
            verticalAlignment = Alignment.CenterVertically
        ) {
            DropdownMenuSample(
                modifier = Modifier
                    .width(Spacing.Size_160)
                    .border(
                        Spacing.Size_1,
                        color = AppColor.primaryColor,
                        shape = RoundedCornerShape(Spacing.Size_10)
                    )
            ) {
                //TODO
            }
            Spacer(modifier = Modifier.weight(1f))
            Image(
                painter = painterResource(id = R.drawable.filter_6535__1_),
                contentDescription = "Filter Icon",
                Modifier
                    .size(Spacing.Size_30)
                    .clickable { }
            )
        }
        HomeComposable(modifier = Modifier)
    }
}