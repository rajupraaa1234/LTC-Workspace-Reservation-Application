package com.example.ltcworkspacereservationapplication.presentation.composable.Loader

import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.unit.dp
import com.example.ltcworkspacereservationapplication.presentation.utils.color.AppColor

@Composable
fun CustomLoader() {
    val infiniteTransition = rememberInfiniteTransition(label = "")

    val scale by infiniteTransition.animateFloat(
        initialValue = 0.5f,
        targetValue = 1f,
        animationSpec = infiniteRepeatable(
            animation = tween(1000),
            repeatMode = RepeatMode.Reverse
        ), label = ""
    )

    Box(
        contentAlignment = Alignment.Center,
        modifier = Modifier
            .size(100.dp)
            .scale(scale).semantics { contentDescription = "Loading" }

    ) {
        Canvas(modifier = Modifier.fillMaxSize()) {
            drawCircle(
                color = AppColor.primaryColor,
                style = Stroke(width = 8.dp.toPx(), cap = StrokeCap.Round)
            )
        }
        Text(text = "Loading...")
    }
}