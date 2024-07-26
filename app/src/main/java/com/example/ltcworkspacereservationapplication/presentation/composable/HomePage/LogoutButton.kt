package com.example.ltcworkspacereservationapplication.presentation.composable.HomePage

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.padding
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics
import com.example.ltcworkspacereservationapplication.presentation.utils.Spacing
import com.example.ltcworkspacereservationapplication.presentation.utils.color.AppColor

@Composable
fun LogoutButton(onLogout: () -> Unit) {
    Text(
        text = "Logout",
        color = AppColor.primaryColor,
        modifier = Modifier
            .clickable(onClick = onLogout)
            .padding(vertical = Spacing.Size_8)
            .semantics { contentDescription = "Logout button" },
        style = MaterialTheme.typography.body1
    )
}