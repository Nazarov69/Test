package com.example.msystems

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.*
import android.util.AttributeSet
import android.util.Log
import android.view.SurfaceHolder
import android.view.SurfaceView

class Pan2(context: Context?, attrs: AttributeSet?) :
    SurfaceView(context, attrs), SurfaceHolder.Callback {
    private val thread: MainThread
    private val elaine: ElaineAnimated
    override fun surfaceChanged(
        holder: SurfaceHolder, format: Int, width: Int,
        height: Int
    ) {
    }

    override fun surfaceCreated(holder: SurfaceHolder) {
        thread.setRunning(true)
        thread.start()
    }

    override fun surfaceDestroyed(holder: SurfaceHolder) {
        Log.d(TAG, "Surface is being destroyed")
        thread.setRunning(false)
        Log.d(TAG, "Thread was shut down cleanly")
    }

    override fun onDraw(canvas: Canvas) {
        if (canvas != null) {
            canvas.drawColor(Color.GRAY)
            elaine.draw(canvas)
        } else {
            surfaceDestroyed(holder)
        }
    }

    fun update() {
        elaine.update(System.currentTimeMillis())
    }

    class MainThread(
        private val surfaceHolder: SurfaceHolder,
        private val gamePanel: Pan2
    ) :
        Thread() {
        private var running = false
        fun setRunning(running: Boolean) {
            this.running = running
        }

        @SuppressLint("WrongCall")
        override fun run() {
            var canvas: Canvas?
            Log.d(TAG, "Starting game loop")
            var beginTime: Long
            var timeDiff: Long
            var sleepTime: Int
            var framesSkipped: Int
            sleepTime = 0
            while (running) {
                canvas = null
                try {
                    canvas = surfaceHolder.lockCanvas()
                    synchronized(surfaceHolder) {
                        beginTime = System.currentTimeMillis()
                        framesSkipped = 0
                        gamePanel.update()
                        gamePanel.onDraw(canvas)
                        timeDiff = System.currentTimeMillis() - beginTime
                        sleepTime =
                            (FRAME_PERIOD - timeDiff).toInt()
                        if (sleepTime > 0) {
                            try {
                                sleep(sleepTime.toLong())
                            } catch (e: InterruptedException) {
                            }
                        }
                        while (sleepTime < 0 && framesSkipped < MAX_FRAME_SKIPS) {
                            gamePanel.update()
                            sleepTime += FRAME_PERIOD
                            framesSkipped++
                        }
                    }
                } finally {
                    if (canvas != null) {
                        surfaceHolder.unlockCanvasAndPost(canvas)
                    }
                }
            }
        }

        companion object {
            private const val MAX_FPS = 50
            private const val MAX_FRAME_SKIPS = 5
            private const val FRAME_PERIOD = 1000 / MAX_FPS
            private val TAG = MainThread::class.java.simpleName
        }
    }

    inner class ElaineAnimated(
        private val bitmap: Bitmap,
        private val x: Int,
        private val y: Int,
        width: Int,
        height: Int,
        fps: Int,
        private val frameNr: Int
    ) {
        private val sourceRect: Rect
        private var currentFrame = 0
        private var frameTicker: Long
        private val framePeriod: Int
        private val spriteWidth: Int
        private val spriteHeight: Int
        fun update(gameTime: Long) {
            if (gameTime > frameTicker + framePeriod) {
                frameTicker = gameTime
                currentFrame++
                if (currentFrame >= frameNr) {
                    currentFrame = 0
                }
            }
            sourceRect.left = currentFrame * spriteWidth
            sourceRect.right = sourceRect.left + spriteWidth
        }

        fun draw(canvas: Canvas) {
            val destRect = Rect(x, y, x + spriteWidth, y + spriteHeight)
            canvas.drawBitmap(bitmap, sourceRect, destRect, null)
        }

        init {
            spriteWidth = bitmap.width / frameNr
            spriteHeight = bitmap.height
            sourceRect = Rect(0, 0, spriteWidth, spriteHeight)
            framePeriod = 1000 / fps
            frameTicker = 0L
        }
    }

    companion object {
        private val TAG = Pan2::class.java.simpleName
    }

    init {
        holder.addCallback(this)
        elaine = ElaineAnimated(
            BitmapFactory.decodeResource(resources, R.drawable.walk_elaine), 10, 50, 30, 47, 5, 5
        )
        thread = MainThread(holder, this)
        isFocusable = true
    }
}