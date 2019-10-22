package com.example.cashapp.model

import androidx.camera.core.ImageAnalysis
import androidx.camera.core.ImageProxy
import com.google.android.gms.tasks.Continuation
import com.google.firebase.ml.vision.FirebaseVision
import com.google.firebase.ml.vision.barcode.FirebaseVisionBarcode
import com.google.firebase.ml.vision.barcode.FirebaseVisionBarcodeDetector
import com.google.firebase.ml.vision.barcode.FirebaseVisionBarcodeDetectorOptions
import com.google.firebase.ml.vision.common.FirebaseVisionImage
import com.google.firebase.ml.vision.common.FirebaseVisionImageMetadata
import kotlinx.coroutines.*

class Scanner : ImageAnalysis.Analyzer {
    private var barcodes : MutableList<FirebaseVisionBarcode>

    constructor() {
        this.barcodes = arrayListOf<FirebaseVisionBarcode>();
    }

    private fun degreesToFirebaseRotation(degrees: Int): Int = when (degrees) {
        0 -> FirebaseVisionImageMetadata.ROTATION_0
        90 -> FirebaseVisionImageMetadata.ROTATION_90
        180 -> FirebaseVisionImageMetadata.ROTATION_180
        270 -> FirebaseVisionImageMetadata.ROTATION_270
        else -> throw Exception("Rotation must be 0, 90, 180, or 270.")
    }

    override fun analyze(imageProxy: ImageProxy?, degrees: Int) {
        val mediaImage = imageProxy?.image
        val imageRotation = degreesToFirebaseRotation(degrees)
        if (mediaImage != null) {
            val image = FirebaseVisionImage.fromMediaImage(mediaImage, imageRotation)
            val detector = FirebaseVision.getInstance().getVisionBarcodeDetector()
            val result = detector.detectInImage(image)
                .addOnSuccessListener { barcodes ->
                    if (barcodes.size > 0) {
                        this.barcodes = barcodes
                    }
                }
                .addOnFailureListener {
                    println(it.message)
                }
        }
    }

    fun waitForResult(callback : (String?) -> Unit) = runBlocking {
        GlobalScope.launch(Dispatchers.IO) {
            while (barcodes.size == 0) {
                println("current thread: ${Thread.currentThread().name}")
                delay(3_000)
            }
            println("FOUND")
            val barcode : FirebaseVisionBarcode = barcodes.get(barcodes.size - 1)
            println(barcode.valueType)
            println(barcode.rawValue)
            withContext(Dispatchers.Main) {
                callback(barcode.rawValue)
            }
        }
    }
}