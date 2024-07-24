package com.example.ltcworkspacereservationapplication

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.BottomNavigation
import androidx.compose.material.BottomNavigationItem
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.ltcworkspacereservationapplication.presentation.composable.CustomDatePickerDialog
import com.example.ltcworkspacereservationapplication.presentation.composable.CustomDropdownMenu
import com.example.ltcworkspacereservationapplication.presentation.composable.HomePage.LogoutButton
import com.example.ltcworkspacereservationapplication.presentation.mvvm.AppIntent
import com.example.ltcworkspacereservationapplication.presentation.mvvm.ReservationViewModel
import com.example.ltcworkspacereservationapplication.presentation.screens.HistoryScreen
import com.example.ltcworkspacereservationapplication.presentation.screens.HomeScreen
import com.example.ltcworkspacereservationapplication.presentation.screens.LoginScreenComposable
import com.example.ltcworkspacereservationapplication.presentation.screens.OtpComposableScreen
import com.example.ltcworkspacereservationapplication.presentation.screens.PhoneNumberVerificationScreen
import com.example.ltcworkspacereservationapplication.presentation.screens.ScannerScreenComposable
import com.example.ltcworkspacereservationapplication.presentation.utils.PreferencesManager
import com.example.ltcworkspacereservationapplication.presentation.utils.Routes
import com.example.ltcworkspacereservationapplication.presentation.utils.Spacing
import com.example.ltcworkspacereservationapplication.presentation.utils.color.AppColor

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val viewModel: ReservationViewModel by viewModels()
        enableEdgeToEdge()
        setContent {
            val employeeId = PreferencesManager.getEmployeeId(this)
            val startDestination =
                if (employeeId.isNullOrEmpty()) Routes.LOGIN else Routes.HOME_SCREEN
            viewModel.updateStartDestination(startDestination)

            BottomTabNavigation(viewModel)
        }
    }
}

@Composable
fun RenderBottomTabNavigation(navController: NavHostController) {
    val selectedTab = remember { mutableStateOf(0) }
    BottomNavigation(
        backgroundColor = Color.White,
        contentColor = AppColor.primaryColor,
    ) {
        BottomNavigationItem(
            icon = { Icon(painterResource(R.drawable.home), contentDescription = null) },
            label = {
                Text(
                    "Home",
                    color = if (selectedTab.value == 0) AppColor.primaryColor else Color.Gray
                )
            },
            selected = selectedTab.value == 0,
            onClick = {
                selectedTab.value = 0
                navController.navigate(Routes.HOME_SCREEN) {
                    launchSingleTop = true
                    restoreState = true
                    popUpTo(navController.graph.startDestinationId) {
                        saveState = true
                    }
                }
            },
            selectedContentColor = AppColor.primaryColor,
            unselectedContentColor = Color.Gray
        )
        BottomNavigationItem(
            icon = {
                Icon(
                    painterResource(R.drawable.qr_code_scan),
                    contentDescription = null
                )
            },
            label = {
                Text(
                    "Scan",
                    color = if (selectedTab.value == 1) AppColor.primaryColor else Color.Gray
                )
            },
            selected = selectedTab.value == 1,
            onClick = {
                selectedTab.value = 1
                navController.navigate(Routes.SCANNER) {
                    launchSingleTop = true
                    restoreState = true
                    popUpTo(navController.graph.startDestinationId) {
                        saveState = true
                    }
                }
            },
            selectedContentColor = AppColor.primaryColor,
            unselectedContentColor = Color.Gray
        )
        BottomNavigationItem(
            icon = { Icon(painterResource(R.drawable.history), contentDescription = null) },
            label = {
                Text(
                    "History",
                    color = if (selectedTab.value == 2) AppColor.primaryColor else Color.Gray
                )
            },
            selected = selectedTab.value == 2,


            onClick = {
                selectedTab.value = 2
                navController.navigate(Routes.HISTORY_SCREEN) {
                    launchSingleTop = true
                    restoreState = true
                    popUpTo(navController.graph.startDestinationId) {
                        saveState = true
                    }
                }
            },
            selectedContentColor = AppColor.primaryColor,
            unselectedContentColor = Color.Gray
        )
    }
}

@SuppressLint("StateFlowValueCalledInComposition")
@Composable
private fun BottomTabNavigation(viewModel: ReservationViewModel) {
    val isLogin = remember { mutableStateOf(false) }
    val uiState = viewModel.uiState.collectAsState()
    val navController = rememberNavController()
    Scaffold(
        bottomBar = {
            if (uiState.value.startDestination == Routes.HOME_SCREEN) {
                Log.d("BottomTabNavigation", "BottomTabNavigation: in if ")
                RenderBottomTabNavigation(navController)
            }
        }
    )
    { innerPadding ->
        AppNavHost(
            navController = navController,
            viewModel,
            modifier = Modifier.padding(innerPadding),
            onLogin = {
                isLogin.value = true
            }
        )
    }
}

@Composable
fun AppNavHost(
    navController: NavHostController,
    viewModel: ReservationViewModel,
    modifier: Modifier,
    onLogin: () -> Unit
) {
    val uiState = viewModel.uiState.collectAsState()
    NavHost(navController, startDestination = uiState.value.startDestination) {
        composable(Routes.HOME_SCREEN) { HomePage(navController, modifier, viewModel) }
        composable(Routes.HISTORY_SCREEN) { HistoryScreen(viewModel, modifier) }
        composable(Routes.LOGIN) {
            LoginScreenComposable(navController, viewModel = viewModel)
        }
        composable(Routes.SCANNER) {
            ScannerScreenComposable()
        }
        composable(Routes.VERIFY_PHONE_NUMBER) {
            PhoneNumberVerificationScreen(navController = navController)
        }
        composable(
            route = "${Routes.OTP_SCREEN}?verificationId={verificationId}&phoneNumber={phoneNumber}",
            arguments = listOf(
                navArgument("verificationId") { type = NavType.StringType },
                navArgument("phoneNumber") { type = NavType.StringType }
            )
        ) { backStackEntry ->
            val verificationId = backStackEntry.arguments?.getString("verificationId") ?: ""
            val phoneNumber = backStackEntry.arguments?.getString("phoneNumber") ?: ""
            OtpComposableScreen(
                navController = navController,
                verificationId = verificationId,
                phoneNumber = phoneNumber
            ) {
                viewModel.updateStartDestination(Routes.HOME_SCREEN)
                onLogin()
            }
        }

    }
}


@SuppressLint("StateFlowValueCalledInComposition")
@Composable
private fun HomePage(
    navController: NavHostController,
    modifier: Modifier,
    viewModel: ReservationViewModel
) {
    val userName = viewModel.uiState.value.employeeName
    val selectedDate = remember { mutableStateOf(viewModel.uiState.value.selectedDate) }
    val showDialog = remember { mutableStateOf(false) }

    Column(
        modifier = modifier
            .padding(
                top = Spacing.Size_20,
                start = Spacing.Size_20,
                end = Spacing.Size_20,
                bottom = Spacing.Size_0
            )
    ) {
        Row(
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(
                text = "Dear $userName",
                modifier = Modifier.padding(vertical = Spacing.Size_8),
                style = MaterialTheme.typography.body2
            )
            LogoutButton(onLogout = {
                PreferencesManager.clearEmployeeId(navController.context)
                viewModel.updateStartDestination(Routes.LOGIN)
                navController.navigate(Routes.LOGIN) {
                    popUpTo(navController.graph.startDestinationId) {
                        inclusive = true
                    }
                }
            })
        }
        Row(
            horizontalArrangement = Arrangement.SpaceAround,
            verticalAlignment = Alignment.CenterVertically
        ) {
            CustomDropdownMenu(
                modifier = Modifier
                    .width(Spacing.Size_160)
                    .border(
                        Spacing.Size_1,
                        color = AppColor.primaryColor,
                        shape = RoundedCornerShape(Spacing.Size_10)
                    )
            ) {
                viewModel.sendIntent(AppIntent.OnFloorSelect(it))
            }
            Spacer(modifier = Modifier.weight(1f))
            Box(
                modifier = Modifier
                    .width(Spacing.Size_160)
                    .height(Spacing.Size_40)
                    .border(
                        Spacing.Size_1,
                        color = AppColor.primaryColor,
                        shape = RoundedCornerShape(Spacing.Size_10)
                    )
                    .clickable { showDialog.value = true },
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = selectedDate.value,
                )
            }
            if (showDialog.value) {
                CustomDatePickerDialog(onDateSelected = { date ->
                    selectedDate.value = date
                    showDialog.value = false
                    viewModel.sendIntent(AppIntent.OnDatePickerClick(selectedDate.value))
                })
            }
        }
        HomeScreen(viewModel)
    }
}