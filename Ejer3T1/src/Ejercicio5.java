
/*
Crea una clase llamada Saldo, con un atributo que indique el saldo disponible.
El constructor dará valor inicial al saldo. Crea los métodos get (público) y set
(privado) para el saldo incluyendo un sleep() aleatorio. Crea otro método que
reciba una cantidad y se la añada al saldo, este método debe informar de
quién realiza la operación, la cantidad, el saldo inicial y el final. Este método
debe ser definido como synchronized. Crea una clase que implemente
Runnable, desde el método run hemos de usar el método que añade la
cantidad al saldo. Crea el método main un objeto saldo con un valor inicial.
Visualiza el valor inicial. Crea varios hilos que compartan el objeto Saldo, a
cada hilo le asignamos un nombre y una cantidad. Lanzamos los hilos y
esperamos que finalicen para visualizar el saldo final. Comprueba el
funcionamiento de la aplicación si quitando synchronized de la declaración del
método.
*/

import java.util.Random;

// Clase Saldo: recurso compartido por todos los hilos
class Saldo {
    private int saldo; // saldo disponible
    private final Random rnd = new Random();

    // El constructor da el valor inicial
    Saldo(int inicial) {
        this.saldo = inicial;
    }

    // GET público, con pausa aleatoria
    public int getSaldo() {
        pausa();
        return saldo;
    }

    // SET privado, con pausa aleatoria
    private void setSaldo(int nuevo) {
        pausa();
        this.saldo = nuevo;
    }

    // Suma una cantidad al saldo.
    // synchronized: solo un hilo a la vez puede ejecutar este método.
    // PRUEBA: quita "synchronized" y ejecuta varias veces para ver el error.
    public synchronized void anadir(String quien, int cantidad) {
        int inicial = getSaldo(); // 1. leemos el saldo
        setSaldo(inicial + cantidad); // 2. lo modificamos
        // 3. informamos: quién, cantidad, saldo inicial y final
        System.out.println(quien + " añade " + cantidad
                + " | inicial: " + inicial + " | final: " + getSaldo());
    }

    // Duerme entre 0 y 99 ms para forzar que los hilos se intercalen
    private void pausa() {
        try {
            Thread.sleep(rnd.nextInt(100));
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt(); // conservamos la interrupción
        }
    }
}

// Tarea que ejecuta cada hilo: añade su cantidad al saldo compartido
class Operacion implements Runnable {
    private final Saldo saldo; // el MISMO objeto para todos
    private final int cantidad;

    Operacion(Saldo saldo, int cantidad) {
        this.saldo = saldo;
        this.cantidad = cantidad;
    }

    @Override
    public void run() {
        // El nombre del hilo identifica quién hace la operación
        saldo.anadir(Thread.currentThread().getName(), cantidad);
    }
}

public class Ejercicio5 {
    public static void main(String[] args) throws InterruptedException {
        // Creamos el saldo con un valor inicial y lo mostramos
        Saldo saldo = new Saldo(1000);
        System.out.println("Saldo inicial: " + saldo.getSaldo());

        // Varios hilos que comparten el mismo objeto Saldo,
        // cada uno con su nombre y su cantidad
        Thread[] hilos = {
                new Thread(new Operacion(saldo, 100), "Hilo-A"),
                new Thread(new Operacion(saldo, 200), "Hilo-B"),
                new Thread(new Operacion(saldo, 300), "Hilo-C"),
                new Thread(new Operacion(saldo, 400), "Hilo-D")
        };

        // Lanzamos todos los hilos
        for (Thread t : hilos) t.start();

        // Esperamos a que todos terminen antes de mostrar el saldo final
        for (Thread t : hilos) t.join();

        // Con synchronized: 1000 + 100 + 200 + 300 + 400 = 2000 siempre
        System.out.println("Saldo final: " + saldo.getSaldo());
    }
}
