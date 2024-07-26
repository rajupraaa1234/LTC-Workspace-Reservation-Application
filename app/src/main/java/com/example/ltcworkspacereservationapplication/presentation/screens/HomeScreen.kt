package com.example.ltcworkspacereservationapplication.presentation.screens

import TabSelection
import android.annotation.SuppressLint
import android.util.Log
import android.app.Activity
import androidx.activity.compose.BackHandler
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
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics
import com.example.ltcworkspacereservationapplication.presentation.composable.HomePage.CabinReservationComposablePage
import com.example.ltcworkspacereservationapplication.presentation.composable.HomePage.DeskReservationComposablePage
import com.example.ltcworkspacereservationapplication.domain.model.HomeTabs
import com.example.ltcworkspacereservationapplication.presentation.composable.Loader.LoaderOverlay
import com.example.ltcworkspacereservationapplication.presentation.mvvm.AppIntent
import com.example.ltcworkspacereservationapplication.presentation.mvvm.ReservationViewModelEffects
import kotlinx.coroutines.flow.filterIsInstance
import com.example.ltcworkspacereservationapplication.presentation.mvvm.ReservationViewModel
import kotlinx.coroutines.launch

@SuppressLint("StateFlowValueCalledInComposition")
@OptIn(ExperimentalFoundationApi::class)
@Composable
internal fun HomeScreen(viewModel: ReservationViewModel) {
    val scope = rememberCoroutineScope()
    val pagerState = rememberPagerState(pageCount = { HomeTabs.entries.size })
    val selectedTabIndex = remember { derivedStateOf { pagerState.currentPage } }

    val uiState =
        viewModel.uiState.collectAsState() // This is the observer list once any changes happen in this list it will reflect on the UI

    val deskList = remember { mutableStateOf(uiState.value.currentFilteredList) }

    val meetingList = remember { mutableStateOf(uiState.value.currentMeetingRoomFilteredList) }
    val context = LocalContext.current



    BackHandler {
        (context as? Activity)?.finishAffinity()
    }

    LaunchedEffect(viewModel.effects) {
        viewModel.effects
            .filterIsInstance<ReservationViewModelEffects.Composable>()
            .collect {
                when (it) {
                    is ReservationViewModelEffects.Composable.SelectedFloor -> scope.launch {
                        deskList.value = it.list
                        uiState.value.currentFilteredList = it.list
                        viewModel.sendIntent(AppIntent.OnDeskListFilter(uiState.value.currentFilteredList))
                    }

                    ReservationViewModelEffects.Composable.deskListUpdated -> {
                        scope.launch {
                            deskList.value = uiState.value.currentFilteredList
                        }
                    }

                    is ReservationViewModelEffects.Composable.MeetingRoomListByFilter -> scope.launch {
                        meetingList.value = it.list
                        uiState.value.currentMeetingRoomFilteredList = it.list
                        viewModel.sendIntent(AppIntent.OnMeetingListFilterUpdate(uiState.value.currentMeetingRoomFilteredList))
                    }

                    ReservationViewModelEffects.Composable.meetingListUpdated -> {
                        scope.launch {
                            meetingList.value = uiState.value.currentMeetingRoomFilteredList
                        }
                    }
                }
            }
    }


    Scaffold(modifier = Modifier.semantics { contentDescription = "Home Screen" }
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(top = it.calculateTopPadding())
        ) {
            TabSelection(modifier = Modifier.semantics { contentDescription = "Tab Selection" }
            ) {
                scope.launch {
                    pagerState.animateScrollToPage(it.ordinal)
                }
            }
        }
        LoaderOverlay(isLoading = viewModel.isLoading.value) {
            Scaffold(modifier = Modifier.semantics { contentDescription = "Home Content" }
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(top = it.calculateTopPadding())
                ) {
                    TabSelection(modifier = Modifier.semantics {
                        contentDescription = "Tab Selection"
                    }
                    ) {
                        scope.launch {
                            pagerState.animateScrollToPage(it.ordinal)
                        }
                    }

                    HorizontalPager(
                        state = pagerState,
                        modifier = Modifier
                            .fillMaxWidth()
                            .weight(1f)
                            .semantics { contentDescription = "Pager Content" }

                    ) {
                        Box(
                            modifier = Modifier.fillMaxSize(),
                            contentAlignment = Alignment.Center
                        ) {
                            when (selectedTabIndex.value) {
                                0 -> DeskReservationComposablePage(
                                    deskList.value,
                                    onClickItem = { it, index ->
                                        scope.launch {
                                            viewModel.sendIntent(
                                                AppIntent.OnDeskItemClick(
                                                    it,
                                                    index
                                                )
                                            )
                                        }
                                    }) {
                                    scope.launch {
                                        viewModel.sendIntent(AppIntent.OnDeskBookButtonClicked)
                                    }
                                }

                                1 -> CabinReservationComposablePage(
                                    meetingList.value,
                                    onClickItem = { it, index ->
                                        scope.launch {
                                            viewModel.sendIntent(
                                                AppIntent.OnMeetingItemClick(
                                                    it,
                                                    index
                                                )
                                            )
                                        }
                                    },
                                    onSubmit = { startTime, endTime, capacity, meetingId ->
                                        scope.launch {
                                            viewModel.sendIntent(
                                                AppIntent.OnMeetingRoomBooking(
                                                    startTime,
                                                    endTime,
                                                    capacity,
                                                    meetingId
                                                )
                                            )
                                        }

                                    }
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}