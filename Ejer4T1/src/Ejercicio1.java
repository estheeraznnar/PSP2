/*
Ejercicio 1 — Hilos escritores y sincronización
Crea un programa Java que trabaje con 3 hilos.
El hilo principal deberá crear y lanzar dos hilos:
El primer hilo deberá escribir por consola la palabra "Hola" 10 veces,
esperando 1 segundo entre cada escritura.
El segundo hilo deberá escribir por consola la palabra "DAM" 10 veces,
esperando 1 segundo entre cada escritura.
El hilo principal deberá lanzar primero el hilo que escribe "Hola" y, después de
50 ms, lanzar el hilo que escribe "DAM".
Cada mensaje deberá aparecer acompañado del nombre del hilo que lo ha
generado.
Por ejemplo:
Hilo-Hola: Hola
Hilo-DAM: DAM
Hilo-Hola: Hola
Hilo-DAM: DAM
Una vez lanzados los dos hilos, el hilo principal deberá esperar a que ambos
finalicen utilizando join().
Después de 5 segundos desde el comienzo de la ejecución de los dos hilos,
el hilo principal deberá interrumpir ambos hilos.
Cuando un hilo reciba la interrupción deberá:
• Mostrar un mensaje indicando que ha sido interrumpido.
• Finalizar su ejecución.
Finalmente, el hilo principal deberá mostrar:
Programa finalizado
*/

public class Ejercicio1 {
    public static void main(String[] args) throws InterruptedException {
        Thread hola = new Thread(new Escritor("Hola"), "Hilo-Hola");
        Thread dam = new Thread(new Escritor("DAM"), "Hilo-DAM");

        hola.start();
        Thread.sleep(50); // DAM arranca 50 ms después
        dam.start();

        Thread.sleep(5000); // pasan 5 s desde el arranque
        hola.interrupt(); // interrumpimos a los dos
        dam.interrupt();

        hola.join(); // esperamos a que terminen
        dam.join();
        System.out.println("Programa finalizado");
    }
}

class Escritor implements Runnable {
    private final String texto;
    Escritor(String texto) { this.texto = texto; }

    public void run() {
        String nombre = Thread.currentThread().getName();
        for (int i = 0; i < 10; i++) {
            System.out.println(nombre + ": " + texto);
            try {
                Thread.sleep(1000); // 1 s entre escrituras
            } catch (InterruptedException e) {
                System.out.println(nombre + " ha sido interrumpido");
                return; // termina el hilo
            }
        }
    }
}