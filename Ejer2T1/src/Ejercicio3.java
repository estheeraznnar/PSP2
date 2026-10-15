import java.io.File;

/*Ejercicio 3 — Ejecutar comandos desde un fichero BAT
Crea un fichero llamado comandos.bat que contenga varios comandos de
consola. Por ejemplo:
echo Comenzando ejecución
date /t
time /t
dir
ipconfig
echo Fin de la ejecución
A continuación, desarrolla un programa Java que utilice ProcessBuilder para
ejecutar dicho fichero .bat.
El programa deberá:
• Lanzar cmd mediante ProcessBuilder.
• Indicarle que ejecute el fichero comandos.bat.
• Guardar la salida normal en salida.log.
• Guardar los errores en errores.log.
• Esperar a la finalización del proceso.
• Mostrar por consola si el proceso ha finalizado correctamente.
Deberás utilizar los métodos redirectOutput() y redirectError().*/

public class Ejercicio3 {
    public static void main(String[] args) {
        try {
            // cmd /c ejecuta el fichero .bat y termina
            ProcessBuilder pb = new ProcessBuilder("cmd", "/c", "comandos.bat");

            // Guardamos la salida y los errores en logs
            pb.redirectOutput(new File("salida.log"));
            pb.redirectError(new File("errores.log"));

            Process p = pb.start();
            int codigo = p.waitFor();   // esperamos a que acabe

            // Código 0 = terminó bien
            if (codigo == 0) {
                System.out.println("El proceso ha finalizado correctamente");
            } else {
                System.out.println("El proceso terminó con error. Código: " + codigo);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}