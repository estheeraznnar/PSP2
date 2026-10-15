
/*Crea una aplicación que conste de 2 hilos; el primero el hilo principal de la
aplicación Java. El hilo principal deberá lanzar un nuevo hilo encargado de
imprimir por consola los siguientes mensajes con un intervalo de 4 segundos
entre cada uno de ellos (Mensajes: “Programas”, “Procesos”, “Servicios”,
“Hilos”). El hilo principal debe quedar a la espera hasta que termine,
mostrando cada segundo que está esperando por la finalización del hilo hijo.
La ejecución del programa debe durar 16s ya que son 4 mensajes y 4s de
espera por cada uno.
Para poder reducir la duración de la ejecución el programa debe aceptar por
parámetro (método main) el tiempo de espera máximo que el hilo principal
esperará a la ejecución del hilo secundario, una vez superado ese tiempo el
hilo principal debe interrumpir la ejecución del hilo secundario y a partir de
ese momento el hilo secundario mostrará los mensajes restantes sin esperas
entre la impresión de los mensajes para finalizar la ejecución del hilo cuanto
antes. El hilo principal debe mostrar por consola el mensaje de finalización de
la ejecución del programa. Puedes imprimir los mensajes que consideres
oportunos para verificar la correcta ejecución del programa. Calcula el tiempo
de ejecución del hilo principal y muéstralo por consola. Incluye el nombre del
hilo que imprime por consola cada vez que muestres un mensaje de salida.*/

public class Ejercicio4 {
    public static void main(String[] args) throws InterruptedException {
        // Tiempo máximo de espera (en segundos) recibido por parámetro
        int maxEspera = Integer.parseInt(args[0]);
        String nombre = Thread.currentThread().getName(); // "main"

        // Guardamos el instante de inicio para calcular el tiempo total
        long inicio = System.currentTimeMillis();

        System.out.println("Hilo: " + nombre + ". Tiempo de espera: " + maxEspera + "s");

        // Creamos y lanzamos el hilo secundario
        Thread hijo = new Thread(new Mensajes());
        hijo.start();
        System.out.println("Hilo: " + nombre + ". Esperando a que el hilo "
                + hijo.getName() + " termine");

        // Esperamos de segundo en segundo mientras el hijo siga vivo
        // y no hayamos superado el tiempo máximo
        int esperado = 0;
        while (hijo.isAlive() && esperado < maxEspera) {
            hijo.join(1000); // espera como máximo 1 s
            if (hijo.isAlive()) {
                System.out.println("Hilo: " + nombre + ". Todavía esperando...");
                esperado++;
            }
        }

        // Si se acabó el tiempo y el hijo sigue vivo, lo interrumpimos
        if (hijo.isAlive()) {
            System.out.println("Hilo: " + nombre + ". Cansado de esperar");
            hijo.interrupt();
        }

        // Esperamos a que el hijo termine del todo
        hijo.join();

        // Calculamos y mostramos el tiempo total de ejecución
        long segundos = (System.currentTimeMillis() - inicio) / 1000;
        System.out.println("Hilo: " + nombre + ". *** Finalizado. Tiempo de ejecución:"
                + segundos + "s. ***");
    }
}

// Tarea del hilo secundario: muestra 4 mensajes con 4 s entre cada uno
class Mensajes implements Runnable {
    public void run() {
        String[] msgs = {"Programas", "Procesos", "Servicios", "Hilos"};
        String nombre = Thread.currentThread().getName(); // "Thread-0"
        boolean interrumpido = false; // si es true, ya no esperamos

        for (String m : msgs) {
            System.out.println("Hilo: " + nombre + ". " + m);

            if (!interrumpido) {
                try {
                    Thread.sleep(4000); // pausa de 4 s
                } catch (InterruptedException e) {
                    // Nos interrumpieron: no salimos, pero dejamos de dormir
                    // para mostrar los mensajes que faltan sin esperas
                    interrumpido = true;
                }
            }
        }
        System.out.println("Hilo: " + nombre + ". *** Finalizado ***");
    }
}