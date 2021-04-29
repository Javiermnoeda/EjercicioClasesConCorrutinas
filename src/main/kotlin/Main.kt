import kotlinx.coroutines.*
import kotlin.random.Random

fun main(){



    val listaAlumnos = mutableListOf<Alumno>()
    val listaExamenes = mutableListOf<Examen>()


    println("Los alumnos salen de su casa")

    runBlocking {
            repeat (30){
                val alumno = Alumno(nombre=it+1,Random.nextLong(1,6)*1000)
                listaAlumnos.add(alumno)
                alumno.haLlegado(this)
                /*
                this.launch {
                    alumno.haLlegado2()
                }

                 */
            }

    }

    println("Todos estan ya en clase")

    Thread.sleep(5000)

    println("\nEl profesor empieza a repartir los examenes")

    runBlocking {
            repeat(30){
                val examen = Examen(it+1)
                listaExamenes.add(examen)
                examen.hacer(Random.nextLong(1,4)*1000, this)
            }
    }

    Thread.sleep(5000)

    println("Ya tengo los ${listaExamenes.size} exámenes, hemos terminado")

    Thread.sleep(5000)

    println("\nEl profesor va a corregir los examenes")

    Thread.sleep(5000)

    repeat(30) {
        val profesor = Profesor("Profesor", nombreAlumno = it + 1)
        profesor.corregirExamen()
    }

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

    fun hacerExamen(){
        println(" Soy $nombre voy a hacer el examen")
    }
}

class Profesor (var Nombre: String, var nombreAlumno: Int){

    fun corregirExamen(){
        val listaNotas = mutableListOf<Int>()
        val nota = (0..10).random()
        listaNotas.add(nota)

        val listaOrdenada = listaNotas.sortedDescending()

       listaOrdenada.forEach {
           println ("El Alumno $nombreAlumno ha sacado $it")
       }
    }
}

class Examen(var nombreAlumno : Int){

    fun hacer( tiempoTardado : Long, coroutineScope: CoroutineScope){
        coroutineScope.launch {
            delay(tiempoTardado)
            println("El Alumno $nombreAlumno ha terminado el examen")
        }
    }
}

/*
Vamos a imaginar el siguiente contexto, tenemos un profesor con 30 alumnos.

Los alumnos son de una clase Alumno. Los alumnos tienen 2 métodos, decir "he llegado" y "hacer el examen". Hecho

Cada alumno tarda de 1 a 6 segundos en llegar (Número aleatorio). Cuando llegan dicen "Alumno X ha llegado". Hecho

Cuando todos han llegado, el profesor reparte los exámenes. Hecho

Los exámenes son otra clase que tiene un método que se llama hacer y que lleva un número aleatorio de 1 a 4 segundos en completarse. Hecho

Cuando el alumno ha completado el examen, dice "El Alumno X ha terminado el examen".

Cuando todos los alumnos han terminado y el profesor ha recibido todos los exámenes y da por concluido el examen.
 */