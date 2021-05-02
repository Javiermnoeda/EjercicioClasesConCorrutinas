import kotlinx.coroutines.*
import javax.xml.bind.JAXBElement
import kotlin.random.Random

val listaAlumnos = mutableListOf<Alumno>()
val listaExamenes = mutableListOf<Examen>()

fun main(){

    println("Los alumnos salen de su casa")

    runBlocking {
        repeat (30){
            val alumno = Alumno(nombre=it+1,Random.nextLong(1,6)*1000)
            listaAlumnos.add(alumno)
            alumno.haLlegado(this)
        }
    }

    println("Todos estan ya en clase")

    val profesor = Profesor("Profesor")
    profesor.repartirExamen(listaAlumnos)

    println("Ya tengo los ${listaExamenes.size} exámenes, hemos terminado")

    println("\nEl profesor va a corregir los examenes")
    profesor.corregirExamen(listaExamenes)


}


class Alumno(var nombre : Int, var tiempoLlegada : Long){

    //SOLUCION 1
    fun haLlegado(coroutineScope: CoroutineScope) {
        coroutineScope.launch {
            delay(tiempoLlegada)
            println("Alumno $nombre ha llegado")
        }
    }

    //SOLUCION 2
    /*
    suspend fun haLlegado2() { //suspend significa que la funcion es llamada desde una corrutina
            delay(tiempoLlegada)
            println("Alumno $nombre ha llegado")
    }
    */

    fun hacerExamen(examen: Examen,coroutineScope: CoroutineScope){
        examen.hacer(Random.nextLong(1,4)*1000,coroutineScope)
    }
}

class Profesor (var Nombre: String){

    fun repartirExamen(listaAlumnos: List<Alumno>){
        println("\nEl profesor empieza a repartir los examenes")
        runBlocking {
            listaAlumnos.forEach{
                val examen = Examen(it.nombre,Random.nextInt(0,10))
                listaExamenes.add(examen)
                it.hacerExamen(examen,this)
            }
        }
    }

    fun corregirExamen(listaExamenes: List<Examen>){

        listaExamenes.forEach {
            it.nota = Random.nextInt(0, 10)
        }

        val prueba = listaExamenes.sortedByDescending {
            it.nota
        }

        prueba.forEach {
            println("Alumno ${it.nombreAlumno} ha sacado ${it.nota}")
        }
    }
}

class Examen(var nombreAlumno : Int, var nota : Int){

    fun hacer( tiempoTardado : Long, coroutineScope: CoroutineScope){
        coroutineScope.launch {
            delay(tiempoTardado)
            println("El Alumno $nombreAlumno ha terminado el examen")

        }
    }
}