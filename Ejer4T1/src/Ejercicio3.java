
/*Ejercicio 3 — Cuenta bancaria con varios hilos
Crea una clase llamada Cuenta que tenga un atributo privado:
private double saldo;

El constructor recibirá el saldo inicial.
La clase deberá disponer de:
• Un método getSaldo().
• Un método para ingresar dinero.
• Un método para retirar dinero.

Los métodos de ingreso y retirada deberán incluir un sleep() aleatorio de entre
100 y 500 ms para simular que las operaciones tardan un tiempo.
Cada operación deberá mostrar:
• Nombre del hilo.
• Tipo de operación.
• Cantidad.
• Saldo anterior.
• Saldo posterior.

Por ejemplo:
[Ingresador-1] Ingreso: 100 €
Saldo anterior: 500 €
Saldo posterior: 600 €

Crea una clase que implemente Runnable.
Cada objeto Runnable deberá realizar varias operaciones sobre la misma
cuenta.
Desde main:
1. Crea una cuenta con 1000 €.
2. Crea 5 hilos que compartan la misma cuenta.
3. Algunos hilos realizarán ingresos.
4. Otros realizarán retiradas.
5. Cada hilo deberá realizar 5 operaciones.

6. Asigna un nombre diferente a cada hilo.
7. Lanza todos los hilos.
8. Utiliza join() para esperar a que todos finalicen.
9. Muestra el saldo final.
Después realiza una segunda versión del programa utilizando synchronized
en los métodos que modifican el saldo.
Comprueba y explica qué diferencias existen entre ambas versiones.
*/

import java.util.Random;

class Cuenta {
    private double saldo;
    private final Random rnd = new Random();

    Cuenta(double inicial) { this.saldo = inicial; }
    public double getSaldo() { return saldo; }

    // VERSIÓN 2: pon "synchronized" delante de "void" en estos dos métodos
    public void ingresar(double cantidad) {
        double anterior = saldo; // leemos
        pausa(); // simulamos que tarda
        saldo = anterior + cantidad; // escribimos
        mostrar("Ingreso", cantidad, anterior);
    }

    public void retirar(double cantidad) {
        double anterior = saldo;
        pausa();
        saldo = anterior - cantidad;
        mostrar("Retirada", cantidad, anterior);
    }

    private void mostrar(String tipo, double cant, double anterior) {
        System.out.println("[" + Thread.currentThread().getName() + "] "
                + tipo + ": " + cant + " €\n Saldo anterior: " + anterior
                + " €\n Saldo posterior: " + saldo + " €");
    }

    // Pausa aleatoria de 100 a 500 ms
    private void pausa() {
        try {
            Thread.sleep(100 + rnd.nextInt(401));
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }
}

class Operador implements Runnable {
    private final Cuenta cuenta;
    private final boolean ingresa; // true = ingresa, false = retira
    Operador(Cuenta c, boolean ingresa) { this.cuenta = c; this.ingresa = ingresa; }

    public void run() {
        for (int i = 0; i < 5; i++) { // 5 operaciones por hilo
            if (ingresa) cuenta.ingresar(100);
            else cuenta.retirar(50);
        }
    }
}

public class Ejercicio3 {
    public static void main(String[] args) throws InterruptedException {
        Cuenta cuenta = new Cuenta(1000);
        Thread[] hilos = {
                new Thread(new Operador(cuenta, true), "Ingresador-1"),
                new Thread(new Operador(cuenta, true), "Ingresador-2"),
                new Thread(new Operador(cuenta, true), "Ingresador-3"),
                new Thread(new Operador(cuenta, false), "Retirador-1"),
                new Thread(new Operador(cuenta, false), "Retirador-2")
        };
        for (Thread t : hilos) t.start();
        for (Thread t : hilos) t.join();

        // Esperado: 1000 + 3*5*100 - 2*5*50 = 2000
        System.out.println("Saldo final: " + cuenta.getSaldo() + " €");
    }
}

