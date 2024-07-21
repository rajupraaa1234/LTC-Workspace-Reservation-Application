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
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.example.ltcworkspacereservationapplication.presentation.composable.HomePage.CabinReservationComposablePage
import com.example.ltcworkspacereservationapplication.presentation.composable.HomePage.DeskReservationComposablePage
import com.example.ltcworkspacereservationapplication.presentation.mvvm.ReservationViewModel
import com.example.ltcworkspacereservationapplication.domain.model.HomeTabs
import com.example.ltcworkspacereservationapplication.presentation.mvvm.AppIntent
import kotlinx.coroutines.launch

@SuppressLint("StateFlowValueCalledInComposition")
@OptIn(ExperimentalFoundationApi::class)
@Composable
internal fun HomeScreen(viewModel: ReservationViewModel) {
    val scope = rememberCoroutineScope()
    val pagerState = rememberPagerState(pageCount = { HomeTabs.entries.size })
    val selectedTabIndex = remember { derivedStateOf { pagerState.currentPage } }

    Scaffold() {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(top = it.calculateTopPadding())
        ) {
            TabSelection(modifier = Modifier) {
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
                        0 -> DeskReservationComposablePage(viewModel.uiState.value.deskList) {
                            viewModel.sendIntent(AppIntent.OnDeskItemClick(it))
                        }

                        1 -> CabinReservationComposablePage(
                            viewModel.uiState.value.cabinList,
                            onClickItem = { viewModel.sendIntent(AppIntent.OnMeetingItemClick(it)) },
                            onSubmit = { startTime, endTime, capacity, meetingId ->
                                viewModel.sendIntent(
                                    AppIntent.OnMeetingRoomBooking(
                                        startTime,
                                        endTime,
                                        capacity,
                                        meetingId
                                    )
                                )
                            }
                        )
                    }
                }
            }
        }
    }
}