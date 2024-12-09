//package com.example.courskotlin.exo
//
////fun main() {
////    println("hello world")
////
////    var v1 : String = "toto"
////    println(v1?.uppercase())
////
////    var v2 : String = "toto"
////    println(v2?.uppercase())
////
//////    if(v2 != null) {
//////        printIn(v2.uppercase())
//////    }
////
////    var v3:String? = null
////    var v4 = v3 + v3
////
////    if(v3.isNullOrBlank() ) {
////        println("null or blank")
////    }
////
////}
//
//fun main() {
//    println(min(2,3,4))
//    println(pair(1))
//    println(pair(2))
//    myPrint("toto")
//}
//
////fun min(a:Int, b:Int, c:Int ) : Int{
////    if(a < b && a < c) {
////        return a
////    }
////    else if(b < a && b < c) {
////        return b
////    }
////    else {
////        return c
////    }
////}
//
//fun min(a:Int, b:Int, c:Int ) = if(a < b && a < c) a
//else if(b < a && b < c) b
//else  c
//
//// return true si c'est pair
////fun pair(c:Int) : Boolean {
////    if(c % 2 == 0 ) {
////        println("Pair est impair")
////    } else { println("Pair est pair")
////        return true }
////    return false
////}
//
//fun pair(c:Int) : Boolean = (c%2 == 0)
//
//// Affiche sur la console la chaine en paramètre entouré de #
//fun myPrint(chaine:String) = println("#"chaine"#")
