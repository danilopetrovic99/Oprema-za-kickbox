/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package models;

import controller.ClientController;
import domain.Narudzbina;
import domain.StavkaNarudzbine;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.table.AbstractTableModel;

/**
 *
 * @author PC
 */
public class TableModelStavke extends AbstractTableModel {

    private ArrayList<StavkaNarudzbine> lista;
    private String[] kolone = {"ID", "Artikal", "Kolicina", "Cena"};
    private int rb = 0;

    public TableModelStavke() {
        lista = new ArrayList<>();
    }

    public TableModelStavke(Narudzbina n) {
        try {
            lista = ClientController.getInstance().getAllStavkaNarudzbine(n);
        } catch (Exception ex) {
            Logger.getLogger(TableModelStavke.class.getName()).log(Level.SEVERE, null, ex);
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
        StavkaNarudzbine sn = lista.get(row);

        switch (column) {
            case 0:
                return sn.getRb();
            case 1:
                return sn.getArtikal().getNaziv();
            case 2:
                return sn.getKolicina();
            case 3:
                return sn.getCena() + "din";

            default:
                return null;
        }
    }

    public void dodajStavku(StavkaNarudzbine sn) {

        for (StavkaNarudzbine stavkaNarudzbine : lista) {
            if (stavkaNarudzbine.getArtikal().getArtikalID().equals(sn.getArtikal().getArtikalID())) {
                stavkaNarudzbine.setKolicina(stavkaNarudzbine.getKolicina() + sn.getKolicina());
                stavkaNarudzbine.setCena(stavkaNarudzbine.getCena() + sn.getCena());
                fireTableDataChanged();
                return;
            }
        }

        rb = lista.size();
        sn.setRb(++rb);
        lista.add(sn);
        fireTableDataChanged();
    }

    public void obrisiStavku(int row) {
        lista.remove(row);

        rb = 0;
        for (StavkaNarudzbine stavkaNarudzbine : lista) {
            stavkaNarudzbine.setRb(++rb);
        }

        fireTableDataChanged();
    }

    public double vratiKonacnuCenu() {
        double konacnaCena = 0;

        for (StavkaNarudzbine stavkaNarudzbine : lista) {
            konacnaCena += stavkaNarudzbine.getCena();
        }

        return konacnaCena;
    }

    public ArrayList<StavkaNarudzbine> getLista() {
        return lista;
    }

}
