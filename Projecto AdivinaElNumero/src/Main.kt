import kotlin.system.exitProcess
import java.io.File
const val colorVerde     = "\u001B[32m"
const val colorBlanco    = "\u001B[37m"
const val fondoVerde     = "\u001B[42m"
const val fondoAmarillo  = "\u001B[43m"
const val reset          = "\u001B[0m"
val rango = 1..9    // Defino el rango del número secreto
val longitud = 4        //Defino la longitud del número secreto

/*Funcion para la selección de opciones*/
fun main() {
    val file = File("mifichero.txt")

    if (!file.exists()) {
        file.createNewFile()
    }

    println("${colorVerde}1. Jugar")
    println("${colorVerde}2. Ver traza de último intento")
    println("${colorVerde}3. Salir")
    print("${colorVerde}opción: ")

    val numero= readln()

    when {
        numero == "1" -> {
            println()
            jugar()
        }
        numero == "2" -> {
            println()
            ultimoIntento()
        }
        numero == "3" -> {
            println("Gracias por jugar, vuelva pronto")
             exitProcess(0)
        }
    }
}

/*Inicio de "Jugar"*/
fun jugar() {

    var numeroAleatorio = generarNumeroAleatorio(rango, longitud)
    val set= mutableSetOf<String>() //LLamo a la función que genera el número

    /*Compruebo que no tiene numeros repetidos con la estructura de datos SET*/
    for (item in numeroAleatorio) {
        set.add(item.toString())
    }
    if (set.size == longitud) {
        val numeroInt = numeroAleatorio.toString()
        File("./mifichero.txt").writeText("El numero Secreto es: $numeroInt\n")
        comprobarNumero(set, longitud, numeroAleatorio)}
    else numeroAleatorio = generarNumeroAleatorio(rango, longitud)
}

/*función que genera el número*/
fun generarNumeroAleatorio(rango: IntRange, longitud: Int): StringBuilder {
    val numeroGenerado = StringBuilder()
    repeat(longitud) {
        val digito = rango.random()
        numeroGenerado.append(digito)
    }
    return numeroGenerado
}

/*Funcion que comprueba el número aleatorio con los números introducidos por el usuario en el juego*/
fun comprobarNumero(myset:MutableSet<String>, longitud:Int, numeroSecreto:StringBuilder) { /*Comprobar el numero introducido por el usuario con el aleatorio*/
    var numeroIntentos =4
    var numeroAciertos =0
    var numeroFallos   =0
    val mylist=myset.toList()
    val numeroSecretoInt = numeroSecreto.toString().toInt()

    /*Si quiero saber el numero de secreto
    * println(myset)
    * */

    print("${colorBlanco}Teclea un número de $longitud cifras sin números repetidos (Tienes $numeroIntentos intentos restantes): ")

    while (numeroIntentos!=0) {
        var numeroUsuario= readln()
        val linea= mutableListOf<String>()

        if (numeroUsuario.lastIndex!=longitud-1) {
            print("El numero especificado no cumple con los requisitos de longitud. Por favor, ingrese un numero con $longitud digitos: ")
            numeroUsuario= readln()
        }

            for (j in 0 until longitud) {
                linea.add(numeroUsuario[j].toString())
            }

            for (i in 0 until longitud ) {
                if (linea[i] == mylist[i]) numeroAciertos+=1
                else numeroFallos+=1
            }

            println("${colorBlanco}$numeroUsuario  ${fondoVerde}$numeroAciertos ${fondoAmarillo}$numeroFallos ${reset}")
            crearFichero(numeroIntentos, numeroFallos, numeroAciertos)
            numeroIntentos--


        /*Condiciones*/
        when {
            numeroAciertos==longitud -> {
                println("${colorBlanco}Felicidades has adivinado el numero secreto: $numeroUsuario!")
                main()
            }
            numeroIntentos==0 -> {
                println("${colorBlanco}Lo siento, no adivinaste el número secreto: $numeroSecretoInt")
                println()
                main()
            }
            else -> print("${colorBlanco}Teclea un número de 4 cifras sin números repetidos (Tienes $numeroIntentos intentos restantes): ")
        }
        numeroAciertos=0
        numeroFallos=0
    }
}

fun crearFichero(numeroIntento:Int, numeroFallos:Int, numeroAciertos:Int) {
        File("./mifichero.txt").appendText("${colorVerde}Intento: $numeroIntento\n")
        File("./mifichero.txt").appendText("Numero de aciertos: $numeroAciertos\n")
        File("./mifichero.txt").appendText("Numero de fallos: $numeroFallos\n")
}

fun ultimoIntento() {
    val leer = File("./mifichero.txt").readText()
    println(leer)
}


