
/*Crea un archivo de texto con la carpeta Documentos de tu ordenador. Crea un
programa en java que lance un proceso para abrir este fichero con
Notepad++.*/

import java.io.File;
import java.io.IOException;

public class Ejercicio1 {
    public static void main(String[] args) {
        String rutaArchivo = System.getProperty("user.home") + "\\Documents\\ejemplo.txt";

        //Creo el archivo si no existe
        File archivo = new File(rutaArchivo);
        try {
            if (archivo.createNewFile()){
                System.out.println("Archivo creado: " + archivo.getAbsolutePath());
            }else {
                System.out.println("El archivo ya existe: " + archivo.getAbsolutePath());
            }

            //Ruta al ejecutale de NotePad++
            String rutaNote = "C:\\Program Files\\Notepad++\\notepad++.exe";

            // Crear el proceso para abrir el archivo con Notepad++
            ProcessBuilder pb = new ProcessBuilder(rutaNote, archivo.getAbsolutePath());
            pb.start();
        }catch (IOException e) {
            e.printStackTrace();
        }
    }
}