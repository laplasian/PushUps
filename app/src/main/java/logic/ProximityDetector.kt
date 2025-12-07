package logic

import android.content.Context
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager

class PushupSensor(context: Context) : SensorEventListener {

    interface PushupListener {
        fun onPushup()
        fun onSensorStateChanged(currentLux: Float, isShadow: Boolean)
    }

    private val sensorManager = context.getSystemService(Context.SENSOR_SERVICE) as SensorManager

    private val lightSensor: Sensor? = sensorManager.getDefaultSensor(Sensor.TYPE_LIGHT)

    private var listener: PushupListener? = null
    private var isPositionDown = false

    private var baseLux: Float = 0f

    fun setListener(listener: PushupListener) {
        this.listener = listener
    }

    fun start() {
        lightSensor?.let {
            sensorManager.registerListener(this, it, SensorManager.SENSOR_DELAY_GAME)
        }
        isPositionDown = false
        baseLux = 0f
    }

    fun stop() {
        sensorManager.unregisterListener(this)
    }

    override fun onSensorChanged(event: SensorEvent?) {
        if (event == null || lightSensor == null) return

        if (event.sensor.type == Sensor.TYPE_LIGHT) {
            val currentLux = event.values[0]

            if (baseLux == 0f) {
                baseLux = currentLux
                return
            }

            val shadowThreshold = baseLux * 0.5f
            val lightThreshold = baseLux * 0.8f

            val isShadow = currentLux < shadowThreshold

            listener?.onSensorStateChanged(currentLux, isShadow)

            if (isShadow && !isPositionDown) {
                isPositionDown = true
            } else if (currentLux > lightThreshold && isPositionDown) {
                isPositionDown = false
                listener?.onPushup()
            }
        }
    }

    override fun onAccuracyChanged(sensor: Sensor?, accuracy: Int) {
    }
}