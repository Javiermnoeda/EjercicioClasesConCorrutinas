import kotlinx.coroutines.*

fun main(){


    val profesor = Profesor("Profesor")

    val listaAlumnos = mutableListOf<Alumno>()
    val listaExamenes = mutableListOf<Examen>()

    println("Soy el ${profesor.Nombre}")

    for (i in 1..30){
        val alumno = Alumno("Alumno $i")
        listaAlumnos.add(alumno)

        val examen = Examen (i)
        listaExamenes.add(examen)
    }

    listaAlumnos.forEach {
        print("Soy ${it.Nombre} y")
        it.heLLegado()
    }

}


class Alumno(var Nombre : String){

    fun heLLegado(){
        println(" he llegado")
    }

    fun hacerExamen(examen: Examen){
        println(", voy a hacer el examen ${examen.Nombre}")
    }
}

class Profesor(var Nombre: String){

}

class Examen(var Nombre : Int){

    fun hacer(){
        var tiempoTardado = (1..4).random()
        tiempoTardado
    }
}