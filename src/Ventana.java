
import java.awt.Component;
import java.awt.GridLayout;
import java.util.ArrayList;
import java.util.Map;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author sebastian
 */
public class Ventana extends JFrame {

    public Ventana(Map<String, ArrayList> map) {

        JTabbedPane tabs = new JTabbedPane();
        for (Map.Entry<String, ArrayList> entry : map.entrySet()) {
            ArrayList<Ruta> array = entry.getValue();
            
            tabs.addTab(array.get(0).maquina, generarTab(array));
        }

        this.setLayout(new GridLayout(1, 1));
        this.add(tabs);

        this.setTitle("Secuenciador CB");
        this.setSize(600, 500);
        this.setExtendedState(JFrame.MAXIMIZED_BOTH);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setLocationRelativeTo(null);
        this.setVisible(true);
    }

    private Component generarTab(ArrayList<Ruta> array) {
        DefaultTableModel modelo = new DefaultTableModel();
        JTable tabla = new JTable();
        
        String titulo[]={"Pieza","Orden","Secuencia máquina"};
        String celdas[][] = new String[array.size()][3];
        
        for(int i=0;i<array.size();i++){
            celdas[i][0]=array.get(i).codigoPieza;
            celdas[i][1]=array.get(i).orden+"";
            celdas[i][2]=array.get(i).secuenciaMaquina+"";
        }
        
        modelo=new DefaultTableModel(celdas,titulo){public boolean isCellEditable(int row, int column) {return false;}};
        tabla.setModel(modelo);
        
        return new JScrollPane(tabla);
    }

}
