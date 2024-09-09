/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package models;

import controller.ClientController;
import domain.Artikal;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.table.AbstractTableModel;

/**
 *
 * @author PC
 */
public class TableModelArtikli extends AbstractTableModel implements Runnable {

    private ArrayList<Artikal> lista;
    private String[] kolone = {"ID", "Tip artikla", "Naziv", "Cena"};
    private String parametar = "";

    public TableModelArtikli() {
        try {
            lista = ClientController.getInstance().getAllArtikal();
        } catch (Exception ex) {
            Logger.getLogger(TableModelArtikli.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public int getRowCount() {
        return lista.size();
    }

    @Override
    public int getColumnCount() {
        return kolone.length;
    }

    @Override
    public String getColumnName(int i) {
        return kolone[i];
    }

    @Override
    public Object getValueAt(int row, int column) {
        Artikal a = lista.get(row);

        switch (column) {
            case 0:
                return a.getArtikalID();
            case 1:
                return a.getTipArtikla();
            case 2:
                return a.getNaziv();
            case 3:
                return a.getCena() + "din";

            default:
                return null;
        }
    }

    public Artikal getSelectedArtikal(int row) {
        return lista.get(row);
    }

    @Override
    public void run() {
        try {
            while (!Thread.currentThread().isInterrupted()) {
                Thread.sleep(10000);
                refreshTable();
            }
        } catch (InterruptedException ex) {
            Logger.getLogger(TableModelArtikli.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void setParametar(String parametar) {
        this.parametar = parametar;
        refreshTable();
    }

    public void refreshTable() {
        try {
            lista = ClientController.getInstance().getAllArtikal();
            if (!parametar.equals("")) {
                ArrayList<Artikal> novaLista = new ArrayList<>();
                for (Artikal a : lista) {
                    if (a.getNaziv().toLowerCase().contains(parametar.toLowerCase())) {
                        novaLista.add(a);
                    }
                }
                lista = novaLista;
            }

            fireTableDataChanged();

        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

}
