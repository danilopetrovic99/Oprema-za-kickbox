/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import domain.Administrator;
import domain.Artikal;
import domain.Kupac;
import domain.Narudzbina;
import domain.StavkaNarudzbine;
import domain.TipArtikla;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import session.Session;
import transfer.Request;
import transfer.Response;
import transfer.util.ResponseStatus;
import transfer.util.Operation;

/**
 *
 * @author PC
 */
public class ClientController {

    private static ClientController instance;

    private ClientController() {
    }

    public static ClientController getInstance() {
        if (instance == null) {
            instance = new ClientController();
        }
        return instance;
    }

    public Administrator login(Administrator administrator) throws Exception {
        return (Administrator) sendRequest(Operation.LOGIN, administrator);
    }

    public void logout(Administrator ulogovani) throws Exception {
        sendRequest(Operation.LOGOUT, ulogovani);
    }

    public void addArtikal(Artikal artikal) throws Exception {
        sendRequest(Operation.ADD_ARTIKAL, artikal);
    }

    public void addKupac(Kupac kupac) throws Exception {
        sendRequest(Operation.ADD_KUPAC, kupac);
    }

    public void addNarudzbina(Narudzbina narudzbina) throws Exception {
        sendRequest(Operation.ADD_NARUDZBINA, narudzbina);
    }

    public void deleteArtikal(Artikal artikal) throws Exception {
        sendRequest(Operation.DELETE_ARTIKAL, artikal);
    }

    public void deleteKupac(Kupac kupac) throws Exception {
        sendRequest(Operation.DELETE_KUPAC, kupac);
    }

    public void deleteNarudzbina(Narudzbina narudzbina) throws Exception {
        sendRequest(Operation.DELETE_NARUDZBINA, narudzbina);
    }

    public void updateArtikal(Artikal artikal) throws Exception {
        sendRequest(Operation.UPDATE_ARTIKAL, artikal);
    }

    public void updateKupac(Kupac kupac) throws Exception {
        sendRequest(Operation.UPDATE_KUPAC, kupac);
    }

    public void updateNarudzbina(Narudzbina narudzbina) throws Exception {
        sendRequest(Operation.UPDATE_NARUDZBINA, narudzbina);
    }

    public ArrayList<Artikal> getAllArtikal() throws Exception {
        return (ArrayList<Artikal>) sendRequest(Operation.GET_ALL_ARTIKAL, null);
    }

    public ArrayList<Narudzbina> getAllNarudzbina(Kupac k) throws Exception {
        return (ArrayList<Narudzbina>) sendRequest(Operation.GET_ALL_NARUDZBINA, k);
    }

    public ArrayList<Kupac> getAllKupac() throws Exception {
        return (ArrayList<Kupac>) sendRequest(Operation.GET_ALL_KUPAC, null);
    }

    public ArrayList<StavkaNarudzbine> getAllStavkaNarudzbine(Narudzbina n) throws Exception {
        return (ArrayList<StavkaNarudzbine>) sendRequest(Operation.GET_ALL_STAVKA_NARUDZBINE, n);
    }

    public ArrayList<TipArtikla> getAllTipArtikla() throws Exception {
        return (ArrayList<TipArtikla>) sendRequest(Operation.GET_ALL_TIP_ARTIKLA, null);
    }

    private Object sendRequest(int operation, Object data) throws Exception {
        Request request = new Request(operation, data);

        ObjectOutputStream out = new ObjectOutputStream(Session.getInstance().getSocket().getOutputStream());
        out.writeObject(request);

        ObjectInputStream in = new ObjectInputStream(Session.getInstance().getSocket().getInputStream());
        Response response = (Response) in.readObject();

        if (response.getResponseStatus().equals(ResponseStatus.Error)) {
            throw response.getException();
        } else {
            return response.getData();
        }

    }

    public ArrayList<StavkaNarudzbine> getAllStavkeArtikla(Artikal a) throws Exception {
        return (ArrayList<StavkaNarudzbine>) sendRequest(Operation.GET_ALL_STAVKA_NARUDZBINE, a);
    }
}
