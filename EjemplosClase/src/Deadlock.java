public class Deadlock {
    static class Friend {
        private final String name;
        public Friend(String name) {
            this.name = name;
        }
        public String getName() {
            return this.name;
        }
        public void bow(Friend bower) {
// Ordenar los objetos para realizar el objeto de 2 objetos
// siempre en el mismo orden evitando de esta forma el deadlock
            Friend first, second;
            if(System.identityHashCode(this)<System.identityHashCode(bower)) {
                first = this;
                second = bower;
            } else {
                first = bower;
                second = this;
            }
// Sincronizar el saludo bloqueando ambos objetos
            synchronized (first) {
                synchronized (second) {
                    System.out.format("%s has bowed to me!%n"
                            , this.name, bower.getName());
                    bower.bowBack(this);
                }
            }
        }
        public synchronized void bowBack(Friend bower) {
            System.out.format("%s has bowed back to me!%n"
                    , this.name, bower.getName());

        }
    }
    public static void main(String[] args) {
        final Friend pedro = new Friend("Pedro");
        final Friend pablo = new Friend("Pablo");
// arrancamos el saludo de pedro a plablo
// ojo: clase anónima
        new Thread(new Runnable() {
            public void run() {
                pedro.bow(pablo);
            }
        }).start();
        new Thread(new Runnable() {
            public void run() {
                pablo.bow(pedro);
            }
        }).start();
    }
}