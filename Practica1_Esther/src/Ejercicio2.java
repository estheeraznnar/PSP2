import java.util.Random;

class ColaTareas{
    private final int maximo = 3; //la capacidad maxima que tiene mi cola
    private final int[] datos = new int[maximo]; //aqui es donde almaceno los numeros
    private int inicio = 0;    //posicion del proximo a sacar
    private int fin = 0; //posicion donde se añade el siguiente
    private int cantidad = 0; //elementos que hay actualmente

    public synchronized void producir(int tarea) throws InterruptedException{
        while (cantidad == maximo){
            wait();
        }
        datos[fin] = tarea; //guardo la tarea
        fin = (fin + 1) % maximo; //avanzo hasta que llege a 3 y este vuelve a 0
        cantidad ++; //añado un elemento
        notify();
    }

    public synchronized int consumir() throws InterruptedException{
        while(cantidad == 0){
            wait();
        }

        int n = datos[inicio];
        inicio = (inicio + 1) %maximo;
        cantidad --;
        notifyAll();
        return n;
    }
}

class Productor implements Runnable{
    private final ColaTareas tareas;
    Productor(ColaTareas ct1) {this.tareas = ct1;}

    public void run(){
        Random random = new Random();
        String nom = Thread.currentThread().getName();
        try {
            for (int i = 0; i < 10; i++) {
                int n = random.nextInt(100);
                tareas.producir(n);
                System.out.println("[" + nom + "] Tarea producida: " + n);
                Thread.sleep(500);
            }
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }
}

class Consumidor implements Runnable{
    private final ColaTareas tareas;
    Consumidor(ColaTareas ct2) {this.tareas = ct2;}

    public void run() {
        String nom = Thread.currentThread().getName();

        try {
            for (int i = 0; i < 10; i++) {
               int n = tareas.consumir();
                System.out.println("[" + nom + "] Tareas procesadas: " + n);
                Thread.sleep(800);
            }
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }
}

public class Ejercicio2 {
    static void main() throws InterruptedException{
        ColaTareas colaTareas = new ColaTareas();
        Thread[] hilos = {
                new Thread(new Productor(colaTareas), "Productor"),
                new Thread(new Consumidor(colaTareas), "Consumidor 1"),
                new Thread(new Consumidor(colaTareas), "Consumidor 2"),
        };

        for(Thread thread :hilos) thread.start();
        for (Thread thread:hilos) thread.join();

        System.out.println("FIN");
    }
}