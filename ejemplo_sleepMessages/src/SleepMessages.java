public class SleepMessages implements Runnable {
    public void run() {
// mensajes
        String importantInfo[] = { "Programas", "Procesos"
                , "Servicios", "Hilos" };
        for (int j = 0; j < 100; j++) {
            for (int i = 0; i < importantInfo.length; i++) {
// Mostrar mensaje
                System.out.println(importantInfo[i]);
            }
        }
        System.out.println("***Hilo finalizado***");
    }
    public static void main(String[] args) throws InterruptedException {
// Crear nuevo hilo
        Thread t = new Thread(new SleepMessages());
// Arrancar hilo
        t.start();
// interrumpir el hilo
        Thread.sleep(5);
        t.interrupt();
// mostrar el flag interrupted del hilo
        System.out.println("Hilo " + t.getName() + " interrumpido: "
                + t.isInterrupted());
    }
}