package com.example.courskotlin.exo

class ThermometerBean private constructor(var value: Int, val minValue: Int, val maxValue: Int) {

    companion object {
        fun getCelsiusThermometer(): ThermometerBean {
            return ThermometerBean(0, -30, 50)
        }

        fun getFahrenheitThermometer(): ThermometerBean {
            return ThermometerBean(32, 20, 120)
        }
    }
}

fun main() {
    val tCelsius = ThermometerBean.getCelsiusThermometer()
    tCelsius.value = 18
    println("La valeur est de ${tCelsius.value}")
    tCelsius.value = -45
    println("La valeur est de ${tCelsius.value.coerceIn(tCelsius.minValue, tCelsius.maxValue)}")  // -30 (coercion)

    val tFahrenheit = ThermometerBean.getFahrenheitThermometer()
    tFahrenheit.value = 70
    println("La valeur est de ${tFahrenheit.value}")
    tFahrenheit.value = 130
    println("La valeur est de ${tFahrenheit.value.coerceIn(tFahrenheit.minValue, tFahrenheit.maxValue)}")  // 120 (coercion)
}
