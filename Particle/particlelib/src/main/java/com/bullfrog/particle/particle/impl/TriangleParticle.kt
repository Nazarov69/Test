package com.bullfrog.particle.particle.impl

import android.graphics.*
import com.bullfrog.particle.particle.IParticle
import com.bullfrog.particle.particle.configuration.ParticleConfiguration
import com.bullfrog.particle.path.IPathGenerator

class TriangleParticle : IParticle {

    override var configuration: ParticleConfiguration? = null

    override var initialX: Int = 0

    override var initialY: Int = 0

    override var x: Int = 0

    override var y: Int = 0

    override var angle: Float = 0f

    override var pathGenerator: IPathGenerator? = null

    private val path = Path()

    private val matrix = Matrix()

    override fun draw(canvas: Canvas, paint: Paint) {
        paint.color = configuration!!.color
        paint.style = Paint.Style.FILL
        val width = configuration!!.width
        val height = configuration!!.height
        matrix.reset()
        matrix.postRotate(angle, x.toFloat(), y.toFloat())
        path.reset()
        path.moveTo(x.toFloat(), y - height / 2f)
        path.lineTo(x - width / 2f, y + height / 2f)
        path.lineTo(x + width / 2f, y + height / 2f)
        path.close()
        path.transform(matrix)
        canvas.drawPath(path, paint)
    }
}