
/*Ejercicio 1 — Abrir una aplicación con ProcessBuilder
Crea un programa en Java que utilice ProcessBuilder para lanzar una
aplicación instalada en tu ordenador.
El programa deberá:
• Solicitar al usuario mediante teclado la ruta del programa que desea
ejecutar.
• Crear un proceso utilizando ProcessBuilder.
• Esperar a que el proceso termine mediante waitFor().
• Mostrar por consola el código de finalización del proceso.
• Controlar las posibles excepciones que puedan producirse.
Ejemplo: puedes probarlo ejecutando el Bloc de notas (notepad.exe) o la
calculadora de Windows.*/

import java.io.IOException;
import java.util.Scanner;

public class Ejercicio1 {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        // Pedimos la ruta del programa (p. ej. notepad.exe o calc.exe)
        System.out.print("Ruta del programa a ejecutar: ");
        String ruta = sc.nextLine();

        try {
            // Creamos y lanzamos el proceso
            ProcessBuilder pb = new ProcessBuilder(ruta);
            Process p = pb.start();

            // Esperamos a que el usuario cierre la aplicación
            int codigo = p.waitFor();
            System.out.println("Código de finalización: " + codigo);

        } catch (IOException e) {
            // Ruta incorrecta o programa que no se puede ejecutar
            System.out.println("No se pudo lanzar el programa: " + e.getMessage());
        } catch (InterruptedException e) {
            // Alguien interrumpió la espera
            System.out.println("La espera fue interrumpida");
        }
    }
}