import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/*Ejercicio 5 — Carrera de hilos y recurso compartido
Crea una aplicación Java que simule una carrera entre 4 corredores.
Cada corredor será un hilo independiente.
Cada corredor deberá recorrer una distancia de 20 metros.
En cada iteración, el corredor avanzará entre 1 y 3 metros de forma aleatoria.
Después de cada avance deberá realizar un:
sleep()
de entre 200 y 600 ms.
Cada vez que avance deberá mostrar:
[Corredor-1] Ha avanzado 2 metros. Distancia: 8/20
Cuando un corredor llegue a los 20 metros deberá acceder a un objeto
compartido llamado Clasificacion.
La clase Clasificacion tendrá que guardar la posición de llegada de cada
corredor.
El acceso a la clasificación deberá estar protegido mediante synchronized, de
forma que dos corredores no puedan obtener simultáneamente la misma
posición.
Por ejemplo:
[Corredor-3] ¡Ha terminado! Posición: 1
[Corredor-1] ¡Ha terminado! Posición: 2
[Corredor-4] ¡Ha terminado! Posición: 3
[Corredor-2] ¡Ha terminado! Posición: 4
El hilo principal deberá:
• Crear la clasificación.
• Crear los 4 corredores.
• Asignar un nombre a cada hilo.
• Lanzar los 4 hilos.
• Esperar mediante join() a que todos terminen.
• Mostrar la clasificación final.
• Mostrar el tiempo total de la carrera.
• Modificación
Una vez funcionando, elimina el synchronized del método que asigna la
posición y ejecuta varias veces el programa.
Explica qué problemas pueden aparecer y por qué.*/

class Clasificacion {
    private int siguiente = 1;                    // próxima posición libre
    private final List<String> llegada = new ArrayList<>();

    // synchronized: dos corredores no pueden obtener la misma posición.
    // MODIFICACIÓN: quítalo y ejecuta varias veces.
    public synchronized int registrar(String nombre) {
        int pos = siguiente;
        // Pausa mínima para hacer visible el fallo sin synchronized
        try { Thread.sleep(50); } catch (InterruptedException e) { }
        siguiente = pos + 1;
        llegada.add(pos + "º " + nombre);
        return pos;
    }

    public List<String> getLlegada() { return llegada; }
}

class Corredor implements Runnable {
    private final Clasificacion clasif;
    Corredor(Clasificacion c) { this.clasif = c; }

    public void run() {
        Random rnd = new Random();
        String nombre = Thread.currentThread().getName();
        int distancia = 0;

        try {
            while (distancia < 20) {
                int avance = 1 + rnd.nextInt(3);          // 1 a 3 m
                distancia = Math.min(20, distancia + avance);
                System.out.println("[" + nombre + "] Ha avanzado " + avance
                        + " metros. Distancia: " + distancia + "/20");
                Thread.sleep(200 + rnd.nextInt(401));     // 200 a 600 ms
            }
        } catch (InterruptedException e) {
            return;
        }

        // Llegó a la meta: se registra en la clasificación compartida
        int pos = clasif.registrar(nombre);
        System.out.println("[" + nombre + "] ¡Ha terminado! Posición: " + pos);
    }
}

public class Ejercicio5 {
    public static void main(String[] args) throws InterruptedException {
        long inicio = System.currentTimeMillis();
        Clasificacion clasif = new Clasificacion();

        Thread[] corredores = new Thread[4];
        for (int i = 0; i < 4; i++) {
            corredores[i] = new Thread(new Corredor(clasif), "Corredor-" + (i + 1));
        }
        for (Thread t : corredores) t.start();
        for (Thread t : corredores) t.join();          // esperamos a todos

        System.out.println("\n--- Clasificación final ---");
        for (String s : clasif.getLlegada()) System.out.println(s);

        long seg = (System.currentTimeMillis() - inicio) / 1000;
        System.out.println("Tiempo total de la carrera: " + seg + " s");
    }
}

