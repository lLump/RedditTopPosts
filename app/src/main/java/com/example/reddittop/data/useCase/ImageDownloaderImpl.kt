package com.example.reddittop.data.useCase

import android.content.Context
import android.graphics.Bitmap
import android.graphics.drawable.BitmapDrawable
import android.media.MediaScannerConnection
import android.os.Environment
import android.util.Log
import coil.ImageLoader
import coil.request.ImageRequest
import coil.request.SuccessResult
import com.example.reddittop.domain.useCase.ImageDownloader
import java.io.File
import java.io.FileOutputStream
import java.io.IOException

class ImageDownloaderImpl(private val context: Context): ImageDownloader {
    override suspend fun invoke(url: String): Boolean {
        return try {
            val loader = ImageLoader(context)
            val request = ImageRequest.Builder(context)
                .data(url)
                .allowHardware(false)
                .build()

            val result = (loader.execute(request) as SuccessResult).drawable
            val bitmap = (result as BitmapDrawable).bitmap

            saveImageToFile(context, bitmap, "photo_${getSafeFileName(url)}")
            true
        } catch (e: Exception) {
            e.printStackTrace()
            false
        }
    }

    private fun saveImageToFile(context: Context, bitmap: Bitmap, fileName: String): File? {
        val downloadsDir = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS)
        val imageFile = File(downloadsDir, fileName)

        try {
            val outputStream = FileOutputStream(imageFile)
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, outputStream)
            outputStream.flush()
            outputStream.close()

            MediaScannerConnection.scanFile(context, arrayOf(imageFile.toString()), null) { path, _ ->
                Log.d("Image_Downloader", "Image saved. Path: $path")
            }
            return imageFile
        } catch (e: IOException) {
            e.printStackTrace()
            return null
        }
    }

    private fun getSafeFileName(imageUrl: String) = imageUrl
        .replace("https://", "")
        .replace("http://", "")
        .replace("[^a-zA-Z0-9.-]".toRegex(), "_")
}