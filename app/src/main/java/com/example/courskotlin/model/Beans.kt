package com.example.courskotlin.model

class CarBean(var marque: String, var model: String) {
    companion object {
        var couleur: String = "grise"

        fun afficherDescription(car: CarBean) {
            println("C'est une ${car.marque} ${car.model} de couleur $couleur")
        }
    }
}

fun main() {
    /*
    // TP seat leon
    val seatLeon = CarBean("Seat", "Leon")
    println(CarBean.couleur)
    CarBean.afficherDescription(seatLeon)
     */

    // TP arraylist
    val randomName = RandomName()
    randomName.add("PierreAuCarrer")
    randomName.addAll("Joe", "Seb")
    repeat(10) {
        println(randomName.next() + "")
    }


}

class RandomName {
    // private list
    private val names = arrayListOf("Seb", "Bob", "Joe")

    // add a surname to the list
    fun add(name: String) {
        if(name.isNotBlank() && name !in names) {
            names.add(name)
        }
    }

    // return a random name of the list
    fun next(): String {
        return names.random()
    }

    // add several names to the list
    fun addAll(vararg newNames: String) {
        newNames.forEach { add(it) }
    }

    // fun nextDiff(vararg newNames: Array<out String>)
}



