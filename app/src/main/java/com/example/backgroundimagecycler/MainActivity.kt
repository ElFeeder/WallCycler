package com.example.backgroundimagecycler

import android.app.WallpaperManager
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity


class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        this.moveTaskToBack(true)   // Minimize app

        val numberOfImages = 13 // Change this accordingly (-1 because math)

        // Create a array to hold each image's bitmap
        val imagesBitmap = ArrayList<Bitmap>()
        for(image in IntRange(0, numberOfImages))  {
            var imageName = "wallpaper".plus(image)
            var drawableId = this.resources.getIdentifier(imageName,"drawable", this.packageName)
            imagesBitmap.add(BitmapFactory.decodeResource(resources, drawableId))
        }

        // Get screen details
        val wallpaperManager = WallpaperManager.getInstance(baseContext)
        val drawable = wallpaperManager.builtInDrawable
        wallpaperManager.setWallpaperOffsetSteps(0F, 0F)
        val height = drawable.intrinsicHeight
        val width = drawable.intrinsicWidth
        wallpaperManager.suggestDesiredDimensions(width, height)

        // Set wallpaper and periodically change it
        while (true) {
            changeWallpaper(numberOfImages, imagesBitmap, wallpaperManager)
            Thread.sleep((60*15*1000).toLong())
        }
    }

    private fun changeWallpaper(numberOfImages: Int, imagesBitmap: ArrayList<Bitmap>,
                                wallpaperManager: WallpaperManager) {
        // Choose image and set wallpaper (both lock and system)
        val homeScreen = imagesBitmap[(0..numberOfImages).random()]   // Generate a number between 0 and numberOfImages (both included)
        val lockScreen = imagesBitmap[(0..numberOfImages).random()]
        wallpaperManager.setBitmap(lockScreen, null, false, WallpaperManager.FLAG_LOCK)
        wallpaperManager.setBitmap(homeScreen, null, false, WallpaperManager.FLAG_SYSTEM)
    }
}