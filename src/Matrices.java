
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.StringTokenizer;
import javax.swing.JOptionPane;

public class Matrices {

    //declaramos las variables
    private String ruta;
    private int columnas;
    private int filas;
    private String matriz[][];

    //constructor
    public Matrices() {
        //instanciar las varibales
        ruta = "matriz.txt";

        leerArchivo();
        primerMatriz();
    }

    //método para leer el archivo de texto
    private void leerArchivo() {
        File archivo = new File(ruta);
        if (archivo.exists()) {
            //LEER ARCHIVO PLANO...
            BufferedReader entradas;

            //intentamos leer el archivo
            try {
                filas = contarFilas(archivo);
                columnas = 19;
                matriz = new String[filas][columnas];
                //INICIO DE LECTURA DEL ARCHIVO DE TEXTO
                entradas = new BufferedReader(new FileReader(archivo));
                String linea;
                //lectura linea por linea
                int contadorColumnas = 0;
                while (entradas.ready()) {
                    linea = entradas.readLine();

                    //separar la linea por tabulado
                    int contadorFilas = 0;
                    System.out.println("linea:");
                    System.out.println(linea);

                    for (String celda : linea.split("\\t")) {
                        matriz[contadorColumnas][contadorFilas] = celda;
                        contadorFilas++;
                    }
                    contadorColumnas++;
                }
                //FIN DE LECTURA DEL ARCHIVO DE TEXTO

                //mostramos el contenido de la matriz
                //leerMatriz();
            } catch (IOException en) {
                //error de lectura del archivo
                JOptionPane.showMessageDialog(null, "Hubo un error al leer el archivo\n" + en.getMessage());
            } //al finalizar la lectura cerramos el buffer (liberamos el archivo)
            finally {
                try {
                    entradas = new BufferedReader(new FileReader(archivo));
                    entradas.close();
                } catch (IOException e1) {
                    //error de lectura del archivo
                    JOptionPane.showMessageDialog(null, "Hubo un error al leer el archivo\n" + e1.getMessage());
                }
            }
        } else {
            JOptionPane.showMessageDialog(null, "El archivo " + ruta + " no existe");
            System.exit(0);
        }
    }

    public void leerMatriz() {
        //leer las filas
        for (int i = 0; i < filas; i++) {
            //System.out.println("FILA NÚMERO: "+i);
            //leer las celdas
            for (int j = 0; j < columnas; j++) {
                //System.out.println("CELDA NUMERO: "+j);

                //si es la columna 0 o la fila 0, se muestra tal cual
                if (i == 0 || j == 0) {
                    System.out.print("[" + matriz[i][j] + "]");
                } //si no, se opera la celda
                else {
                    //float valor = Float.parseFloat(matriz[i][j]);
                    //ejemplo de operacion, multiplicamos por 2 la celda
                    //valor=valor*2;
                    System.out.print("[" + matriz[i][j] + "]");
                }
            }
            System.out.println("");
        }
    }

    //metodo para contar las filas del archivo
    public int contarFilas(File archivo) throws FileNotFoundException, IOException {
        BufferedReader entradas = new BufferedReader(new FileReader(archivo));

        int contador = 0;

        while (entradas.ready()) {
            entradas.readLine();
            contador++;
        }
        return contador;
    }

    //metodo para comparar dos maquinas
    private void primerMatriz() {
        //leerMatriz();
        //leer las filas
        //PENDIENTE: validar cuando la maquina no tega ninguna secuencia

        //contar filas con secuencia 1
        int filasNuevaMatriz = 0;
        for (int i = 0; i < filas; i++) {
            if (matriz[i][3].equals("1")) {
                filasNuevaMatriz++;
            }
        }

        //armar matriz de comparación
        String[][] matrizComparacion = new String[filasNuevaMatriz][4];
        filasNuevaMatriz = 0;
        for (int i = 0; i < filas; i++) {
            if (matriz[i][3].equals("1")) {
                matrizComparacion[filasNuevaMatriz][0] = matriz[i][0];
                matrizComparacion[filasNuevaMatriz][1] = matriz[i][4];
                //matrizComparacion[0][filasNuevaMatriz] = matriz[i][0];
                matrizComparacion[filasNuevaMatriz][3] = "SUMA";
                filasNuevaMatriz++;
            }
        }

        for (int i = 0; i < filasNuevaMatriz; i++) {
            for (int j = 0; j <4 ; j++) {
                System.out.print("    "+matrizComparacion[i][j]);
            }
            System.out.println("");
        }
    }
}
