package com.sample.myapplication

import android.os.Bundle
import android.view.Choreographer
import android.view.SurfaceView
import androidx.activity.ComponentActivity
import com.google.android.filament.utils.ModelViewer
import com.google.android.filament.utils.Utils
import java.nio.ByteBuffer

class CameraActivity : ComponentActivity() {

    companion object {
        init {
            Utils.init()
        }
    }

    var choreographer: Choreographer? = null

    private lateinit var mSurfaceView: SurfaceView

    lateinit var modelViewer: ModelViewer

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_camera)

        mSurfaceView = findViewById(R.id.surface_view)
        choreographer = Choreographer.getInstance()
        choreographer?.postFrameCallback(frameCallback)

        modelViewer = ModelViewer(mSurfaceView)

        loadGlb("DamagedHelmet")
    }

    private fun loadGlb(name: String) {
        val buffer = readAsset("models/${name}.glb")
        modelViewer.loadModelGlb(buffer)
        modelViewer.transformToUnitCube()
    }

    private fun readAsset(assetName: String): ByteBuffer {
        val input = assets.open(assetName)
        val bytes = ByteArray(input.available())
        input.read(bytes)
        return ByteBuffer.wrap(bytes)
    }

    private val frameCallback = object : Choreographer.FrameCallback {
        override fun doFrame(currentTime: Long) {
            choreographer?.postFrameCallback(this)
            modelViewer.render(currentTime)
        }
    }


}
