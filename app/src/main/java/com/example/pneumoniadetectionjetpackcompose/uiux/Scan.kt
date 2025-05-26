package com.example.pneumoniadetectionjetpackcompose.uiux

import android.content.Context
import android.graphics.Bitmap
import android.graphics.Color
import org.tensorflow.lite.Interpreter
import org.tensorflow.lite.support.common.FileUtil
import java.nio.ByteBuffer
import java.nio.ByteOrder

object PneumoniaPredictor {
    private const val MODEL_NAME = "modelpneumonia.tflite"
    private const val INPUT_IMAGE_SIZE = 48

    fun preprocessImage(bitmap: Bitmap): ByteBuffer {
        val inputBuffer = ByteBuffer.allocateDirect(4 * INPUT_IMAGE_SIZE * INPUT_IMAGE_SIZE * 3)
        inputBuffer.order(ByteOrder.nativeOrder())

        val scaledBitmap =
            Bitmap.createScaledBitmap(bitmap, INPUT_IMAGE_SIZE, INPUT_IMAGE_SIZE, true)
        for (y in 0 until INPUT_IMAGE_SIZE) {
            for (x in 0 until INPUT_IMAGE_SIZE) {
                val pixel = scaledBitmap.getPixel(x, y)
                val r = Color.red(pixel) / 255.0f
                val g = Color.green(pixel) / 255.0f
                val b = Color.blue(pixel) / 255.0f
                inputBuffer.putFloat(r)
                inputBuffer.putFloat(g)
                inputBuffer.putFloat(b)
            }
        }

        inputBuffer.rewind()
        return inputBuffer
    }

    fun predict(context: Context, bitmap: Bitmap): String {
        val model = FileUtil.loadMappedFile(context, MODEL_NAME)
        val interpreter = Interpreter(model)

        val input = preprocessImage(bitmap)
        val output = Array(1) { FloatArray(2) } // 2 kelas: NORMAL dan PNEUMONIA

        interpreter.run(input, output)

        val result = output[0]
        val predictedClass = result.indices.maxByOrNull { result[it] } ?: -1
        val labels = listOf("NORMAL", "PNEUMONIA")

        return labels.getOrElse(predictedClass) { "Tidak diketahui" }
    }
}
