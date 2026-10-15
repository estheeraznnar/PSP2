import java.io.File;
import java.io.IOException;

/*Hemos visto en los ejemplos que podemos lanzar un proceso con
ProcessBuilder y ejecutar comandos de consola (cmd) en él. En el ejemplo
indicábamos exactamente el comando a ejecutar y mostrábamos por consola
el resultado de la ejecución. La clase ProcessBuilder permite redirigir la
entrada, salida y los errores en la ejecución de proceso a ficheros. Busca
información en la ayuda de Java sobre los métodos redirectInput,
redirectOutput y redirectError. Deberás desarrollar un programa que lance un
subproceso cmd con ProcessBuilder, el programa debe obtener los comandos
a ejecutar por la consola de un fichero .bat que habrás creado tú
previamente. El programa dejará el log de ejecución en un fichero de salida y
el log de errores en otro fichero. Deberás utilizar los métodos redirectInput,
redirectOutput y redirectError.
El fichero bat podría tener por ejemplo los siguientes comandos:
ping www.dam2chomon.org
ping www.google.es
pring www.iesch.org
De esa forma veremos que ocurre en cada situación:
• un comando correcto con una dirección que no existe
• un comando correcto con una dirección que existe
• un comando incorrecto*/

public class Ejercicio2 {
    public static void main(String[] args) {
        // Rutas de los archivos
        String rutaDocumentos = System.getProperty("user.home") + "\\Documents\\";
        File ficheroBat = new File(rutaDocumentos + "comandos.bat");
        File logSalida = new File(rutaDocumentos + "salida.log");
        File logErrores = new File(rutaDocumentos +"errores.log");

        // Crear el ProcessBuilder
        ProcessBuilder pb = new ProcessBuilder("cmd.exe", "/c", ficheroBat.getAbsolutePath());

        // Redirecciones de entrada, salida y error
        pb.redirectInput(ficheroBat);      // El proceso leerá los comandos desde el .bat
        pb.redirectOutput(logSalida);      // Salida estándar -> salida.log
        pb.redirectError(logErrores);      // Errores -> errores.log

        try {
            System.out.println("Ejecutando el .bat...");
            Process proceso = pb.start();
            int exitCode = proceso.waitFor(); // Esperar a que termine
            System.out.println("Proceso terminado con código: " + exitCode);
            System.out.println("Revisa los logs en: " + rutaDocumentos);
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
    }
}