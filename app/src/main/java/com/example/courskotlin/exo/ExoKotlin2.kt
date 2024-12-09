package com.example.courskotlin.exo
import com.example.courskotlin.exo.baguette
import com.example.courskotlin.exo.croissant
import com.example.courskotlin.exo.sandwich

fun main() {
    println(boulangerie(1,2,3))
}
fun boulangerie(nbCroissant: Int=0, nbBaguette: Int=0, nbSandwich: Int=0)
    =  nbCroissant * croissant + nbBaguette * baguette + nbSandwich * sandwich
