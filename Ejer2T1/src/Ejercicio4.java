import java.io.File;

/*Crea un fichero de texto llamado comandos.txt que contenga diferentes
comandos que puedan ejecutarse desde cmd, por ejemplo:
echo Inicio
hostname
whoami
ipconfig
echo Fin
Desarrolla un programa Java que cree un proceso cmd mediante ProcessBuilder.
En lugar de proporcionar los comandos directamente desde Java, el proceso
deberá obtenerlos mediante la entrada estándar redirigida desde el fichero
comandos.txt.
El programa deberá:
• Utilizar redirectInput() para indicar el fichero de entrada.
• Utilizar redirectOutput() para guardar la salida en resultado.txt.
• Utilizar redirectError() para guardar los errores en errores.txt.
• Esperar a que termine el proceso.
• Mostrar el código de finalización por consola.
Objetivo: comprobar cómo se pueden utilizar ficheros como entrada y salida de
un proceso.*/

public class Ejercicio4 {
    public static void main(String[] args) {
        try {
            // Lanzamos una consola cmd vacía
            ProcessBuilder pb = new ProcessBuilder("cmd");

            // ENTRADA: cmd lee los comandos de comandos.txt
            pb.redirectInput(new File("comandos.txt"));
            // SALIDA: resultado.txt
            pb.redirectOutput(new File("resultado.txt"));
            // ERRORES: errores.txt
            pb.redirectError(new File("errores.txt"));

            Process p = pb.start();
            int codigo = p.waitFor();   // esperamos a que termine

            System.out.println("Código de finalización: " + codigo);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}