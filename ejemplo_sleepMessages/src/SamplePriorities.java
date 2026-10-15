public class SamplePriorities extends Thread {
    public SamplePriorities(String name) {
        this.setName(name);
    }
    public void run() {
// mostrar información por consola
        System.out.println("Executing [" + this.getName() + "]");
        for (int i = 0; i < 11; i++) {
            System.out.println("\t(" + this.getName() + ": " + i + ")");
        }
    }
    public static void main(String[] args) {
// crear hilos
        SamplePriorities h1 = new SamplePriorities("One");
        SamplePriorities h2 = new SamplePriorities("Two");
        SamplePriorities h3 = new SamplePriorities("Three");
        SamplePriorities h4 = new SamplePriorities("Four");
        SamplePriorities h5 = new SamplePriorities("Five");
        SamplePriorities h6 = new SamplePriorities("6");
        SamplePriorities h7 = new SamplePriorities("7");
        SamplePriorities h8 = new SamplePriorities("8");
        SamplePriorities h9= new SamplePriorities("9");
        SamplePriorities h10 = new SamplePriorities("10");
        SamplePriorities h11= new SamplePriorities("11");
// establecer prioridades
        h1.setPriority(MIN_PRIORITY);
        h2.setPriority(3);
        h3.setPriority(NORM_PRIORITY);
        h4.setPriority(7);
        h5.setPriority(MAX_PRIORITY);
        h6.setPriority(8);
        h7.setPriority(6);
        h8.setPriority(9);
        h9.setPriority(4);
        h10.setPriority(1);
        h11.setPriority(10);
// ejecución
        h1.start();
        h2.start();
        h3.start();
        h4.start();
        h5.start();
        h6.start();
        h7.start();
        h8.start();
        h9.start();
        h10.start();
        h11.start();
    }
}