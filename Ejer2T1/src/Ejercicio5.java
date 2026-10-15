import java.io.File;

/*
Ejercicio 5 — Lanzamiento de varios procesos
Crea un programa Java que lance tres procesos independientes utilizando
ProcessBuilder.
Cada proceso deberá ejecutar un comando diferente de Windows:
• Proceso 1: mostrar la configuración de red mediante ipconfig.
• Proceso 2: mostrar el nombre del equipo mediante hostname.
• Proceso 3: realizar un ping a www.google.es.
Cada proceso deberá guardar su resultado en un fichero diferente:
ipconfig.txt
hostname.txt
ping.txt
El programa deberá:
• Crear los tres objetos ProcessBuilder.
• Lanzar los tres procesos.
• Redirigir la salida de cada proceso a su correspondiente fichero.
• Esperar a que terminen los tres procesos utilizando waitFor().
• Mostrar por consola el código de finalización de cada uno.
• Controlar las excepciones que puedan producirse.
Ampliación: modifica el programa para que los tres procesos se lancen antes de
esperar a que termine cualquiera de ellos. De esta forma podrás observar que los
procesos pueden ejecutarse de forma concurrente.
*/

public class Ejercicio5 {
    public static void main(String[] args) {
        try {
            // Proceso 1: configuración de red -> ipconfig.txt
            ProcessBuilder pb1 = new ProcessBuilder("cmd", "/c", "ipconfig");
            pb1.redirectOutput(new File("ipconfig.txt"));

            // Proceso 2: nombre del equipo -> hostname.txt
            ProcessBuilder pb2 = new ProcessBuilder("cmd", "/c", "hostname");
            pb2.redirectOutput(new File("hostname.txt"));

            // Proceso 3: ping a Google -> ping.txt
            ProcessBuilder pb3 = new ProcessBuilder("cmd", "/c", "ping www.google.es");
            pb3.redirectOutput(new File("ping.txt"));

            // AMPLIACIÓN: lanzamos los tres seguidos, sin esperar
            // (así se ejecutan a la vez, de forma concurrente)
            Process p1 = pb1.start();
            Process p2 = pb2.start();
            Process p3 = pb3.start();

            // Ahora esperamos a cada uno y mostramos su código de salida
            System.out.println("ipconfig terminó con código: " + p1.waitFor());
            System.out.println("hostname terminó con código: " + p2.waitFor());
            System.out.println("ping terminó con código: " + p3.waitFor());

        } catch (Exception e) {
            // IOException si falla el lanzamiento, InterruptedException en la espera
            e.printStackTrace();
        }
    }
}