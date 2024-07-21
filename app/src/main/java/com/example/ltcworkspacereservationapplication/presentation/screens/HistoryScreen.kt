package com.example.ltcworkspacereservationapplication.presentation.screens

import TabSelection
import android.annotation.SuppressLint
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.example.ltcworkspacereservationapplication.presentation.composable.History.CabinHistoryComposablePage
import com.example.ltcworkspacereservationapplication.presentation.composable.History.DeskHistoryComposablePage
import com.example.ltcworkspacereservationapplication.presentation.mvvm.ReservationViewModel
import com.example.ltcworkspacereservationapplication.domain.model.HomeTabs
import com.example.ltcworkspacereservationapplication.presentation.utils.Spacing
import kotlinx.coroutines.launch


@SuppressLint("StateFlowValueCalledInComposition")
@OptIn(ExperimentalFoundationApi::class)
@Composable
fun HistoryScreen(
    viewModel: ReservationViewModel,
    modifier: Modifier
) {
    val scope = rememberCoroutineScope()
    val pagerState = rememberPagerState(pageCount = { HomeTabs.entries.size })
    val selectedTabIndex = remember { derivedStateOf { pagerState.currentPage } }
    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(Spacing.Size_20)
    ) {

        Text(text = "Booking History", style = MaterialTheme.typography.h5)

        TabSelection(modifier = Modifier.padding(top = Spacing.Size_30)) {
            scope.launch {
                pagerState.animateScrollToPage(it.ordinal)
            }
        }

        HorizontalPager(
            state = pagerState,
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f)
        ) {
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                when (selectedTabIndex.value) {
                    0 -> DeskHistoryComposablePage(viewModel.uiState.value.deskHistoryList)
                    1 -> CabinHistoryComposablePage(viewModel.uiState.value.cabinHistoryList)
                }
            }
        }
    }
}