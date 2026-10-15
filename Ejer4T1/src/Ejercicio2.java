
/*
Ejercicio 2 — Hilo principal esperando a un hilo secundario
Crea una aplicación Java formada por un hilo principal y un hilo secundario.
El hilo secundario deberá mostrar los siguientes mensajes:
Inicio del programa
Procesos
Servicios
Multihilo
Sincronización
Fin del programa

Entre cada mensaje deberá esperar 3 segundos.
El hilo principal deberá lanzar el hilo secundario y quedarse esperando su
finalización.
Mientras está esperando, el hilo principal deberá mostrar cada segundo:
Hilo principal esperando...
El hilo principal podrá esperar como máximo el número de segundos indicado
mediante un parámetro recibido por main.
Por ejemplo:
java Ejercicio2 7
En este caso, el hilo principal esperará como máximo 7 segundos.
Si el hilo secundario todavía no ha terminado después de ese tiempo, el hilo
principal deberá:
• Interrumpir al hilo secundario.
• El hilo secundario deberá detectar la interrupción.
• Mostrar los mensajes que todavía no haya mostrado sin realizar
esperas entre ellos.
• Finalizar.
• El hilo principal deberá esperar finalmente a que termine el hilo
secundario.
• Mostrar el tiempo total de ejecución del programa.
Todos los mensajes deberán indicar qué hilo los está mostrando.
Por ejemplo:
[main] Hilo principal esperando...
[Hilo-Secundario] Procesos
[main] Hilo principal esperando...
*/

public class Ejercicio2 {
    public static void main(String[] args) throws InterruptedException {
        // Segundos máximos de espera (3 por defecto si no hay argumento)
        int max = (args.length > 0) ? Integer.parseInt(args[0]) : 3;
        long inicio = System.currentTimeMillis();

        Thread sec = new Thread(new Tarea(), "Hilo-Secundario");
        sec.start();

        int esperado = 0;
        while (sec.isAlive() && esperado < max) {
            sec.join(1000); // espera como máximo 1 s
            if (sec.isAlive()) {
                System.out.println("[main] Hilo principal esperando...");
                esperado++;
            }
        }

        if (sec.isAlive()) {
            sec.interrupt(); // se acabó el tiempo
        }
        sec.join(); // espera final

        long seg = (System.currentTimeMillis() - inicio) / 1000;
        System.out.println("[main] Tiempo total: " + seg + " s");
    }
}

class Tarea implements Runnable {
    public void run() {
        String[] msgs = {"Inicio del programa", "Procesos", "Servicios",
                "Multihilo", "Sincronización", "Fin del programa"};
        String nombre = Thread.currentThread().getName();
        boolean interrumpido = false; // true = ya no esperamos

        for (String m : msgs) {
            System.out.println("[" + nombre + "] " + m);
            if (!interrumpido) {
                try {
                    Thread.sleep(3000); // 3 s entre mensajes
                } catch (InterruptedException e) {
                    interrumpido = true; // sigue sin pausas
                }
            }
        }
    }
}
