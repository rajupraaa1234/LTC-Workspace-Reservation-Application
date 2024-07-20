package com.example.ltcworkspacereservationapplication

import android.annotation.SuppressLint
import android.util.Log
import androidx.camera.core.ImageAnalysis
import androidx.camera.core.ImageProxy
import com.google.mlkit.vision.barcode.BarcodeScannerOptions
import com.google.mlkit.vision.barcode.BarcodeScanning
import com.google.mlkit.vision.barcode.common.Barcode
import com.google.mlkit.vision.common.InputImage
import java.util.concurrent.TimeUnit

class QRCodeAnalyzer(
    private val onCodeScanned: (barcodes: List<Barcode>) -> Unit
) : ImageAnalysis.Analyzer {

    private var lastAnalyzedTimeStamp = 0L

    @SuppressLint("UnsafeOptInUsageError")
    override fun analyze(image: ImageProxy) {
        val currentTimeStamp = System.currentTimeMillis()
        if (currentTimeStamp - lastAnalyzedTimeStamp >= TimeUnit.SECONDS.toMillis(1)) {
            image.image?.let { imageToAnalyze ->
                val options = BarcodeScannerOptions.Builder()
                    .setBarcodeFormats(Barcode.FORMAT_ALL_FORMATS)
                    .build()
                val barcodeScanner = BarcodeScanning.getClient(options)
                val imageToProcess = InputImage.fromMediaImage(imageToAnalyze, image.imageInfo.rotationDegrees)

                barcodeScanner.process(imageToProcess)
                    .addOnSuccessListener { barcodes ->
                        if (barcodes.isNotEmpty()) {
                            onCodeScanned(barcodes)
                        } else {
                            Log.d("QRCodeAnalyzer", "No barcode scanned")
                        }
                    }
                    .addOnFailureListener { exception ->
                        Log.e("QRCodeAnalyzer", "Error processing image: $exception")
                    }
                    .addOnCompleteListener {
                        image.close()
                    }
            } ?: run {
                Log.e("QRCodeAnalyzer", "No image available for analysis")
                image.close()
            }
            lastAnalyzedTimeStamp = currentTimeStamp
        } else {
            image.close()
        }
    }
}
