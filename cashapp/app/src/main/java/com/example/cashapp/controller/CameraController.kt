package com.example.cashapp.controller

import android.Manifest
import android.app.Activity
import android.content.Context
import android.content.pm.PackageManager
import android.graphics.Matrix
import android.os.Handler
import android.os.HandlerThread
import android.util.Rational
import android.util.Size
import android.view.Surface
import android.view.TextureView
import android.widget.Button
import androidx.camera.core.*
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.lifecycle.LifecycleOwner
import com.example.cashapp.model.Scanner
import com.google.android.gms.tasks.Tasks.await
import kotlinx.coroutines.*
import org.w3c.dom.Text
import kotlin.concurrent.thread


/**
 * jlegay 2019 # EPITECH
 * CameraController
 * Control the camera, preview and scanner.
 */
class CameraController {
    private val context : Context
    private val activity : Activity
    private val viewFinder : TextureView
    private val lifecycle : LifecycleOwner

    private var analyser : Scanner?

    constructor(activity : Activity, lifecycle : LifecycleOwner, view : TextureView) {
        this.context = activity.applicationContext
        this.activity = activity
        this.viewFinder = view
        this.lifecycle = lifecycle
        this.analyser = null
        if (allPermissionsGranted()) {
            return
        } else {
            ActivityCompat.requestPermissions(this.activity, REQUIRED_PERMISSIONS, REQUEST_CODE_PERMISSIONS)
        }
    }

    fun start() : CameraController {
        val analyzerUseCase = createAnalyzer()
        viewFinder.post { startCamera(analyzerUseCase) }
        return this
    }

    fun result(callback : (String?) -> Unit) {
        if (analyser != null) {
            //thread(start = true) {
                println("new runablle")
                (analyser as Scanner).waitForResult({
                    callback(it)
                })
            //}
        } else {
            println("ANALYZER SET TO NULL...")
        }
    }


    /**
     * PRIVATE FUNCTIONS
     */
    private fun createAnalyzer() : ImageAnalysis {
        val analyzerConfig = ImageAnalysisConfig.Builder().apply {
            val analyzerThread = HandlerThread(
                "ScannerController"
            ).apply { start() }

            setCallbackHandler(Handler(analyzerThread.looper))
            setImageReaderMode(ImageAnalysis.ImageReaderMode.ACQUIRE_LATEST_IMAGE)

        }.build()

        val analyzerUseCase = ImageAnalysis(analyzerConfig).apply {
            analyzer = Scanner()
        }
        println("ANALYZER CURRENTLY SET")
        this.analyser = analyzerUseCase.analyzer as Scanner
        return analyzerUseCase
    }

    private fun startCamera(analyzerUseCase : ImageAnalysis) {
        val previewConfig = PreviewConfig.Builder().apply {
            setTargetAspectRatio(Rational(1, 1))
            setTargetResolution(Size(640, 640))
        }.build()
        val preview = Preview(previewConfig)

        preview.setOnPreviewOutputUpdateListener {
            viewFinder.surfaceTexture = it.surfaceTexture
            updateTransform()
        }

        // Bind use cases to lifecycle
        // If Android Studio complains about "this" being not a LifecycleOwner
        // try rebuilding the project or updating the appcompat dependency to
        // version 1.1.0 or higher.
        CameraX.bindToLifecycle(lifecycle, preview, analyzerUseCase)
    }
    private fun updateTransform() {

        val matrix = Matrix()

        // Compute the center of the view finder
        val centerX = viewFinder.width / 2f
        val centerY = viewFinder.height / 2f

        // Correct preview output to account for display rotation
        val rotationDegrees = when (viewFinder.display.rotation) {
            Surface.ROTATION_0 -> 0
            Surface.ROTATION_90 -> 90
            Surface.ROTATION_180 -> 180
            Surface.ROTATION_270 -> 270
            else -> return
        }

        matrix.postRotate(-rotationDegrees.toFloat(), centerX, centerY)

        // Finally, apply transformations to our TextureView
        viewFinder.setTransform(matrix)
    }
    private fun allPermissionsGranted(): Boolean {
        for (permission in REQUIRED_PERMISSIONS) {
            if (ContextCompat.checkSelfPermission(this.context, permission) != PackageManager.PERMISSION_GRANTED) {
                return false
            }
        }
        return true
    }

    companion object {
        enum class TYPE {
            BARCODE, QRCODE, ALL
        }
        private val REQUEST_CODE_PERMISSIONS = 10
        private val REQUIRED_PERMISSIONS = arrayOf(Manifest.permission.CAMERA)
    }
}