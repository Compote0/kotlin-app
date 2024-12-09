package com.example.courskotlin.exo


fun sansLettreE(chaine: String): String {
    return chaine.filter { it != 'e' }
}

fun nombreDeA(chaine: String): Int {
    return chaine.count { it == 'a' }
}

fun chaineInversee(chaine: String): String {
    return chaine.reversed()
}

fun nombreDeMajuscules(chaine: String): Int {
    return chaine.count { it in 'A'..'Z' }
}

fun sansVoyelles(chaine: String): String {
    val voyelles = "aeiouy"
    return chaine.filterNot { it in voyelles }
}

fun sansMajuscules(chaine: String): String {
    return chaine.filter { !it.isUpperCase() }
}

fun plusGrandCaractere(chaine: String): Char? {
    return chaine.maxOrNull()
}

fun retirerEspacesDebut(chaine: String): String {
    return chaine.trimStart()
}

fun remplacerEspaces(chaine: String): String {
    return chaine.replace(" ", "")
}


fun retirerEspacesDebutFin(chaine: String): String {
    return chaine.trim()
}

fun estPalindrome(chaine: String): Boolean {
    val nettoyee = chaine.filter { it.isLetterOrDigit() }.lowercase()
    return nettoyee == nettoyee.reversed()
}

fun codeAsciiMoyen(chaine: String): Double {
    return chaine.map { it.code }.average()
}

fun main() {
    val testChaine = "Toto en vacances"

    println("Sans 'e' : ${sansLettreE(testChaine)}")
    println("Nombre de 'a' : ${nombreDeA(testChaine)}")
    println("Chaine inversée : ${chaineInversee(testChaine)}")
    println("Nombre de majuscules : ${nombreDeMajuscules(testChaine)}")
    println("Sans voyelles : ${sansVoyelles(testChaine)}")
    println("Sans majuscules : ${sansMajuscules(testChaine)}")
    println("Plus grand caractère : ${plusGrandCaractere(testChaine)}")
    println("Sans espaces au début : '${retirerEspacesDebut(testChaine)}'")
    println("Remplacer espaces par '': '${remplacerEspaces(testChaine)}'")
    println("Trim espaces début et fin : '${retirerEspacesDebutFin(testChaine)}'")
    println("Est palindrome : ${estPalindrome("kayak")}")
    println("Code ASCII moyen : ${codeAsciiMoyen(testChaine)}")
}
