
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import javax.swing.JOptionPane;

public class Matrices {

    //declaramos las variables
    private String path;
    private int columnas;
    private int filas;
    private String matriz[][];
    private Map<String, ArrayList> map = new HashMap<>();
    String matrizContent="";

    //constructor
    public Matrices() {
        //instanciar las varibales
        path = "matriz.txt";

        leerArchivo();
        leerMatriz();
        matrizSecuenciaUno();
        //leerRutas();
        mostrarExcel();
    }

    //método para leer el archivo de texto
    private void leerArchivo() {
        File archivo = new File(path);
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
                    System.out.println(linea);
                    matrizContent+=linea+"\n";

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
            JOptionPane.showMessageDialog(null, "El archivo " + path + " no existe");
            System.exit(0);
        }

        //borrar valores null
        for (int i = 0; i < columnas; i++) {
            for (int j = 0; j < filas; j++) {
                if (matriz[j][i] == null) {
                    matriz[j][i] = "";
                }
            }
        }
    }

    private void leerMatriz() {
        //leer las filas
        for (int i = 0; i < filas; i++) {
            //leer las celdas
            for (int j = 0; j < columnas; j++) {
                //nos aseguramos de no leer la primer fila ni la primer columna
                if (i != 0 && j != 0 && !matriz[i][j].equals("")) {
                    //encontramos una ruta!
                    Ruta ruta = new Ruta();
                    ruta.maquina = matriz[0][j];
                    ruta.codigoPieza = matriz[i][0];
                    ruta.orden = Integer.parseInt(matriz[i][j]);
                    ruta.minutos = Float.parseFloat(matriz[i][j + 1].replace(",", "."));

                    if (map.get(ruta.maquina) == null) {
                        ArrayList<Ruta> rutas = new ArrayList();
                        map.put(ruta.maquina, rutas);
                    }

                    map.get(ruta.maquina).add(ruta);
                    //nos saltamos el siguiente elemento
                    j++;
                }
            }
        }
    }

    public void matrizSecuenciaUno() {
        for (Map.Entry<String, ArrayList> entry : map.entrySet()) {

            //recorriendo cada maquina mapeada
            ArrayList<Ruta> array = entry.getValue();

            System.out.println(entry.getKey() + "/");
            ArrayList<LineaSecuenciaUno> matriz = new ArrayList<>();
            for (int i = 0; i < array.size(); i++) {
                if (array.get(i).orden == 1) {
                    LineaSecuenciaUno linea = new LineaSecuenciaUno(array.get(i).codigoPieza, array.get(i).minutos, buscarOrdenDos(array.get(i).codigoPieza));
                    matriz.add(linea);
                }
                //array.get(i).orden=99;
                System.out.println(array.get(i).maquina + " - " + array.get(i).codigoPieza + " - " + array.get(i).orden + " - " + array.get(i).minutos);
            }

            //leyendo matriz
            System.out.println("Matriz secuencia uno: " + matriz.size());
            for (int i = 0; i < matriz.size(); i++) {
                System.out.println("[" + matriz.get(i).codigoPieza + "][" + matriz.get(i).minutos + "][" + matriz.get(i).siguiente + "][" + (matriz.get(i).minutos + matriz.get(i).siguiente) + "]");
            }

            //procesar matriz secuencia uno
            if (matriz.size() > 0) {
                int mayor = matriz.size();
                int menor = 1;
                procesarMatrizSecuenciaUno(matriz, array.get(0).maquina,mayor,menor);
            }
        }
    }

    public void leerRutas() {
        System.out.println("LEYENDO RUTAS");
        for (Map.Entry<String, ArrayList> entry : map.entrySet()) {

            ArrayList<Ruta> array = entry.getValue();

            for (int i = 0; i < array.size(); i++) {
                System.out.println(array.get(i).maquina + " - " + array.get(i).codigoPieza + " - " + array.get(i).orden + "(" + array.get(i).secuenciaMaquina + ")" + " - " + array.get(i).minutos);
            }
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

    private float buscarOrdenDos(String codigo) {
        for (Map.Entry<String, ArrayList> entry : map.entrySet()) {
            ArrayList<Ruta> array = entry.getValue();
            for (int i = 0; i < array.size(); i++) {
                if (array.get(i).codigoPieza.equals(codigo) && array.get(i).orden == 2) {
                    return array.get(i).minutos;
                }
            }
        }
        return 0f;
    }

    private void procesarMatrizSecuenciaUno(ArrayList<LineaSecuenciaUno> matriz, String maquina,int mayor, int menor) {
        //1.Buscamos la suma mas baja
        float contador = 999999f;
        for (int i = 0; i < matriz.size(); i++) {
            if (matriz.get(i).minutos + matriz.get(i).siguiente < contador) {
                contador = matriz.get(i).minutos + matriz.get(i).siguiente;
            }
        }

        //buscamos ese menor
        for (int i = 0; i < matriz.size(); i++) {
            if (matriz.get(i).minutos + matriz.get(i).siguiente == contador) {
                //2. preguntamos si es menor la maquina o la siguiente
                if (matriz.get(i).minutos < matriz.get(i).siguiente) {
                    //maquina actual menor
                    updateSecuencia(matriz.get(i).codigoPieza, maquina, menor);
                    menor++;
                } else {
                    //maquina siguiente menor
                    updateSecuencia(matriz.get(i).codigoPieza, maquina, mayor);
                    mayor--;
                }
                matriz.remove(i);
                procesarMatrizSecuenciaUno(matriz, maquina,mayor,menor);
                break;
            }
        }
    }

    private void updateSecuencia(String codigoPieza, String maquina, int secuencia) {
        for (Map.Entry<String, ArrayList> entry : map.entrySet()) {

            ArrayList<Ruta> array = entry.getValue();

            for (int i = 0; i < array.size(); i++) {
                if (array.get(i).codigoPieza.equals(codigoPieza) && array.get(i).maquina.equals(maquina)) {
                    array.get(i).secuenciaMaquina = secuencia;
                }
            }
        }
    }

    private void mostrarExcel() {
        new Ventana(map,matrizContent);
    }
}
