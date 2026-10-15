
/*Ejercicio 4 — Productor y consumidor
Crea una aplicación Java que simule una pequeña cola de trabajos utilizando
varios hilos.
Crea una clase llamada ColaTrabajos que almacene números enteros.
La cola tendrá una capacidad máxima de 5 elementos.
El programa deberá tener:
• 2 hilos productores.
• 2 hilos consumidores.
Cada productor deberá generar 10 números y añadirlos a la cola.
Cada consumidor deberá extraer números de la cola y mostrarlos por
consola.
Los productores deberán esperar 500 ms entre cada elemento producido.
Los consumidores deberán esperar 800 ms entre cada elemento consumido.
Los métodos para añadir y extraer elementos deberán estar correctamente
sincronizados para evitar problemas de concurrencia.
Cuando la cola esté llena, los productores deberán esperar.
Cuando la cola esté vacía, los consumidores deberán esperar.
Utiliza los mecanismos de sincronización de Java necesarios para resolver el
problema.
Cada mensaje deberá indicar el nombre del hilo:
[Productor-1] Produce: 25
[Productor-2] Produce: 67
[Consumidor-1] Consume: 25
[Consumidor-2] Consume: 67
El programa deberá finalizar cuando todos los productores hayan terminado y
todos los elementos producidos hayan sido consumidos.*/


import java.util.Random;

class ColaTrabajos {
    private final int MAX = 5;                    // capacidad máxima
    private final int[] datos = new int[MAX];     // almacén de números
    private int inicio = 0;                       // posición del próximo a sacar
    private int fin = 0;                          // posición donde se añade el siguiente
    private int cantidad = 0;                     // elementos que hay ahora

    public synchronized void poner(int n) throws InterruptedException {
        while (cantidad == MAX) {
            wait();                               // llena: el productor espera
        }
        datos[fin] = n;                           // guardamos el número
        fin = (fin + 1) % MAX;                    // avanzamos; al llegar a 5 vuelve a 0
        cantidad++;
        notifyAll();                              // avisamos a los consumidores
    }

    public synchronized int sacar() throws InterruptedException {
        while (cantidad == 0) {
            wait();                               // vacía: el consumidor espera
        }
        int n = datos[inicio];                    // leemos el más antiguo
        inicio = (inicio + 1) % MAX;              // avanzamos de forma circular
        cantidad--;
        notifyAll();                              // avisamos a los productores
        return n;
    }
}

class Productor implements Runnable {
    private final ColaTrabajos cola;
    Productor(ColaTrabajos c) { this.cola = c; }

    public void run() {
        Random rnd = new Random();
        String nombre = Thread.currentThread().getName();
        try {
            for (int i = 0; i < 10; i++) {        // 10 números por productor
                int n = rnd.nextInt(100);
                cola.poner(n);
                System.out.println("[" + nombre + "] Produce: " + n);
                Thread.sleep(500);                // 500 ms entre elementos
            }
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }
}

class Consumidor implements Runnable {
    private final ColaTrabajos cola;
    Consumidor(ColaTrabajos c) { this.cola = c; }

    public void run() {
        String nombre = Thread.currentThread().getName();
        try {
            // 2 productores x 10 = 20 elementos; 2 consumidores x 10 = 20
            for (int i = 0; i < 10; i++) {
                int n = cola.sacar();
                System.out.println("[" + nombre + "] Consume: " + n);
                Thread.sleep(800);                // 800 ms entre elementos
            }
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }
}

public class Ejercicio4 {
    public static void main(String[] args) throws InterruptedException {
        ColaTrabajos cola = new ColaTrabajos();
        Thread[] hilos = {
                new Thread(new Productor(cola), "Productor-1"),
                new Thread(new Productor(cola), "Productor-2"),
                new Thread(new Consumidor(cola), "Consumidor-1"),
                new Thread(new Consumidor(cola), "Consumidor-2")
        };
        for (Thread t : hilos) t.start();
        for (Thread t : hilos) t.join();          // acaba cuando todo se consumió
        System.out.println("Fin: todo producido y consumido");
    }
}

