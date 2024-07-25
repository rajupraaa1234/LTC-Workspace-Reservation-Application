package com.example.ltcworkspacereservationapplication.presentation.screens

import android.Manifest
import android.content.pm.PackageManager
import android.text.method.LinkMovementMethod
import android.text.util.Linkify
import android.util.Log
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.camera.core.CameraSelector
import androidx.camera.core.ImageAnalysis
import androidx.camera.core.Preview
import androidx.camera.lifecycle.ProcessCameraProvider
import androidx.camera.view.PreviewView
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.material.ButtonDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.core.content.ContextCompat
import androidx.core.text.util.LinkifyCompat
import androidx.navigation.NavController
import com.example.ltcworkspacereservationapplication.QRCodeAnalyzer
import com.example.ltcworkspacereservationapplication.presentation.mvvm.AppIntent
import com.example.ltcworkspacereservationapplication.presentation.mvvm.ReservationViewModel
import com.example.ltcworkspacereservationapplication.presentation.mvvm.ReservationViewModelEffects
import com.example.ltcworkspacereservationapplication.presentation.utils.Routes
import com.example.ltcworkspacereservationapplication.presentation.utils.color.AppColor
import com.google.common.util.concurrent.ListenableFuture
import kotlinx.coroutines.launch
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors

@Composable
fun ScannerScreenComposable(viewModel: ReservationViewModel,navController: NavController) {
    val scope = rememberCoroutineScope()
    val barCodeVal = remember { mutableStateOf("") }
    val context = LocalContext.current
    val lifecycleOwner = LocalLifecycleOwner.current
    var hasCameraPermission by remember {
        mutableStateOf(
            ContextCompat.checkSelfPermission(
                context,
                Manifest.permission.CAMERA
            ) == PackageManager.PERMISSION_GRANTED
        )
    }

    val launcher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.RequestPermission(),
        onResult = { isGranted ->
            hasCameraPermission = isGranted
        })

    LaunchedEffect(key1 = true) {
        if (!hasCameraPermission) {
            launcher.launch(Manifest.permission.CAMERA)
        }
    }

    var previewBoth by remember { mutableStateOf<Preview?>(null) }
    var imageAnalysis by remember { mutableStateOf<ImageAnalysis?>(null) }
    var isScanning by remember { mutableStateOf(false) }

    if (hasCameraPermission) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(Color.Black),
            contentAlignment = Alignment.Center
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(horizontal = 32.dp, vertical = 16.dp),
                verticalArrangement = Arrangement.Center
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(bottom = 16.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(text = "Selected Seat -> ${barCodeVal.value}", color = AppColor.backgroundColor)
                    AndroidView(
                        factory = {
                            TextView(context).apply {
                                textSize = 20f
                            }
                        },
                        update = { textView ->
                            Log.d("code",barCodeVal.value)
                            textView.text = barCodeVal.value
                            LinkifyCompat.addLinks(textView, Linkify.ALL)
                            textView.movementMethod = LinkMovementMethod.getInstance()
                        }
                    )
                }
                Box(
                    modifier = Modifier
                        .border(2.dp, AppColor.backgroundColor, RectangleShape)
                        .size(250.dp)
                        .background(Color.Transparent)
                        .align(Alignment.CenterHorizontally)
                ) {
                    AndroidView(
                        factory = { context ->
                            PreviewView(context).apply {
                                layoutParams = ViewGroup.LayoutParams(
                                    ViewGroup.LayoutParams.MATCH_PARENT,
                                    ViewGroup.LayoutParams.MATCH_PARENT
                                )
                                implementationMode = PreviewView.ImplementationMode.COMPATIBLE
                            }
                        },
                        modifier = Modifier
                            .size(250.dp)
                            .clip(RectangleShape)
                            .background(Color.Transparent)
                            .align(Alignment.Center),
                        update = { previewView ->
                            val cameraSelector = CameraSelector.DEFAULT_BACK_CAMERA
                            val cameraExecutor: ExecutorService =
                                Executors.newSingleThreadExecutor()
                            val cameraProviderFuture: ListenableFuture<ProcessCameraProvider> =
                                ProcessCameraProvider.getInstance(context)

                            cameraProviderFuture.addListener(
                                {
                                    previewBoth = Preview.Builder().build().also {
                                        it.setSurfaceProvider(previewView.surfaceProvider)
                                    }
                                    val cameraProvider = cameraProviderFuture.get()
                                    val barcodeAnalyzer = QRCodeAnalyzer { barcodes ->
                                        barcodes.forEach { barcode ->
                                            barcode.rawValue?.let { barcodeValue ->
                                                barCodeVal.value = barcodeValue
                                            }
                                        }
                                    }
                                    imageAnalysis = ImageAnalysis.Builder()
                                        .setBackpressureStrategy(ImageAnalysis.STRATEGY_KEEP_ONLY_LATEST)
                                        .build()
                                        .also {
                                            it.setAnalyzer(cameraExecutor, barcodeAnalyzer)
                                        }

                                    try {
                                        cameraProvider.unbindAll()
                                        cameraProvider.bindToLifecycle(
                                            lifecycleOwner,
                                            cameraSelector,
                                            previewBoth
                                        )
                                        if (isScanning) {
                                            cameraProvider.bindToLifecycle(
                                                lifecycleOwner,
                                                cameraSelector,
                                                imageAnalysis
                                            )
                                        }
                                    } catch (e: Exception) {
                                        Log.e(
                                            "ScannerScreenComposable",
                                            "Camera binding failed: ${e.localizedMessage}"
                                        )
                                    }
                                }, ContextCompat.getMainExecutor(context)
                            )
                        }
                    )
                }

                Button(
                    onClick = {
                        if (!isScanning) {
                            isScanning = true
                            val cameraProvider = ProcessCameraProvider.getInstance(context).get()
                            cameraProvider.bindToLifecycle(
                                lifecycleOwner,
                                CameraSelector.DEFAULT_BACK_CAMERA,
                                imageAnalysis
                            )
                        }
                    },
                    colors = ButtonDefaults.buttonColors(backgroundColor = AppColor.primaryColor),
                    shape = RectangleShape,
                    modifier = Modifier
                        .padding(vertical = 16.dp)
                        .align(Alignment.CenterHorizontally),
                    enabled = !isScanning
                ) {
                    Text(text = "Scan QR Code", color = Color.White)
                }
                Spacer(modifier = Modifier.height(8.dp))
                Button(
                    onClick = {
                        scope.launch {
                            viewModel.sendIntent(AppIntent.OnQRCodeScanned(seatId = barCodeVal.value))
                            navController.navigate(Routes.HOME_SCREEN)
                            Toast.makeText(context,"Your Booking ${barCodeVal.value} is confirmed",Toast.LENGTH_SHORT).show()
                        }

                    },
                    colors = ButtonDefaults.buttonColors(backgroundColor = AppColor.primaryColor),
                    shape = RectangleShape,
                    modifier = Modifier
                        .padding(vertical = 16.dp)
                        .align(Alignment.CenterHorizontally),
                    enabled = isScanning
                ) {
                    Text(text = "Confirm the Booking", color = Color.White)
                }
            }
        }
    } else {
        Text(text = "Camera permission not granted", color = Color.Red)
    }
}