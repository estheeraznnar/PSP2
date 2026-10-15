import java.io.File;

/*Ejercicio 2 — Ejecutar comandos y guardar la salida en un fichero
Crea un programa Java que lance un proceso cmd utilizando ProcessBuilder.
El programa deberá ejecutar varios comandos de Windows, por ejemplo:
echo Usuario actual:
whoami
echo Directorio actual:
cd
echo Contenido del directorio:
dir
La salida producida por estos comandos deberá almacenarse en un fichero
llamado salida.txt.
Además:
• Los errores deberán almacenarse en un fichero llamado errores.txt.
• Deberás utilizar redirectOutput() y redirectError().
• El programa deberá esperar a que finalice el proceso.
• Finalmente, deberá mostrar por consola el código de finalización.*/

public class Ejercicio2 {
    public static void main(String[] args) {
        try {
            // Lanzamos cmd con /c: ejecuta los comandos y se cierra al terminar.
            // El símbolo & encadena los comandos para que se ejecuten uno tras otro.
            ProcessBuilder pb = new ProcessBuilder("cmd", "/c",
                    "echo Usuario actual: & whoami & " +
                            "echo Directorio actual: & cd & " +
                            "echo Contenido del directorio: & dir");

            // La salida normal de los comandos se guarda en salida.txt
            pb.redirectOutput(new File("salida.txt"));

            // Los mensajes de error se guardan en errores.txt
            pb.redirectError(new File("errores.txt"));

            // Arrancamos el proceso (las redirecciones ya están configuradas)
            Process p = pb.start();

            // Esperamos a que cmd termine y guardamos su código de salida
            int codigo = p.waitFor();

            // Mostramos el código: 0 = correcto, otro valor = hubo algún problema
            System.out.println("Código de finalización: " + codigo);

        } catch (Exception e) {
            // IOException: no se pudo lanzar el proceso o crear los ficheros.
            // InterruptedException: alguien interrumpió la espera de waitFor().
            e.printStackTrace();
        }
    }
}