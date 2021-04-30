import com.sun.javaws.Globals
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

    /*
    println("\nEl profesor va a corregir los examenes")

    runBlocking {
        this.launch {
            repeat(30) {
                val profesor = Profesor("Profesor", nombreAlumno = it + 1)
                profesor.corregirExamen(this)
            }
        }
    }
    */
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
                val examen = Examen(it.nombre)
                listaExamenes.add(examen)
                it.hacerExamen(examen,this)
            }
        }

    }

    /*
    fun corregirExamen(coroutineScope: CoroutineScope){
        coroutineScope.launch {
            val listaNotas = mutableListOf<Int>()
            val nota = (0..10).random()
            listaNotas.add(nota)

            listaNotas.filter {
                listaNotas.forEach {

                }
                true
            }
        }
    }
    */
}

class Examen(var nombreAlumno : Int){

    fun hacer( tiempoTardado : Long, coroutineScope: CoroutineScope){
        coroutineScope.launch {
            delay(tiempoTardado)
            println("El Alumno $nombreAlumno ha terminado el examen")

        }
    }
}