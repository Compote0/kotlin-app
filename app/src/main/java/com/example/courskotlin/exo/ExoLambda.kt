package com.example.courskotlin.exo


fun exo1() {

    val lower: (String) -> String = { it.lowercase() }

    val hour: (Int) -> Double = { it / 60.0 }

    val max: (Int, Int) -> Int = { a, b -> kotlin.math.max(a, b) }

    val reverse: (String) -> String = { it.reversed() }

    println(lower("Coucou"))
    println(hour(170))
    println(max(3, 7))
    println(reverse("toto"))

}


// TP LAMBDA HIGH ORDER FUNCTION
/*
fun exo2(){
    val compareUsersByName = {u1: UserBean, u2: UserBean -> if( u1.name.lowercase() <= u2.name.lowercase()) u1 else u2 }
    val compareUserByOld = { u1: UserBean, u2: UserBean -> if (u1.old >= u2.old) u1 else u2 }

    val u1 = UserBean ("Bob", 19)
    val u2 = UserBean ("Toto", 45)
    val u3 = UserBean ("Charles", 26)

    println(compareUsers(u1, u2, u3, compareUsersByName)) // UserBean(name=Bob old=19)
    println(compareUsers(u1, u2, u3, compareUsersByOld)) // UserBean(name=Toto old=45)
    val near30 = compareUsers(u1, u2, u3) { a, b ->
        if(abs(30 - a.note) <= abs(30 - b.note)) a else b
    }
    println(near30) //UserBean("Charles", 26)
}


inline fun compareUser(u1: UserBean, u2 : UserBean, u3:UserBean, compare: (UserBean, UserBean) -> UserBean)= compare(compare(u1, u2), u3)
*/
data class Student(var name: String, var grade: Int)

fun exo3() {
    val students = listOf(
        Student("Toto", 12),
        Student("Bob", 8),
        Student("Alice", 15),
        Student("Toto", 9),
        Student("Charlie", 10)
    )

    // 1
    val above10 = students.filter { it.grade >= 10 }
    println("students with average of 10 and more $above10")

    // 2
    val numberOfTotos = students.count { it.name == "Toto" }
    println("toto number: $numberOfTotos")

    // 3
    val totoWithAverage = students.count { it.name == "Toto" && it.grade >= 10 }
    println("toto number with average $totoWithAverage")

    // 4
    val classAverage = students.map { it.grade }.average()
    val totoAboveAverage = students.count { it.name == "Toto" && it.grade > classAverage }
    println("number of toto with more than average of the class $totoAboveAverage")

    // 5
    val distinctNames = students.map { it.name }.distinct().sorted()
    println("names ABC order, without clone $distinctNames")

    // 6
    students.filter { it.grade < 10 }.forEach { it.grade += 1 }
    println("students after given a point to the stuedents that dont have the average $students")

    // 7
    students.filter { it.name == "Toto" }.forEach { it.grade += 1 }
    println("student after adding a point to all the toto : $students")

    // 8
    val minGrade = students.minByOrNull { it.grade }?.grade
    val filteredStudents = students.filterNot { it.grade == minGrade }
    println("student after removed the tiniest grade $students")

    // 9
    val namesAbove10 = students.filter { it.grade >= 10 }.map { it.name }.sorted()
    println("note of the students with a note of 10 and more ABC order : $namesAbove10")
}


fun main() {
    //exo1()
    //exo2()
    exo3()
}


