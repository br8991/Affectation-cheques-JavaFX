/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package models;

/**
 *
 * @author RiPou
 */
public class Cheque {
    String numCheque, fournisseur, dateCheque;
    double montantCheque, montantChequePrimaire;
    
    public Cheque(){}

    public Cheque(String fournisseur, String numCheque, String dateCheque, double montantCheque, double montantChequePrimaire) {
        this.numCheque = numCheque;
        this.fournisseur = fournisseur;
        this.dateCheque = dateCheque;
        this.montantCheque = montantCheque;
        this.montantChequePrimaire = montantChequePrimaire;
    }

    public double getMontantChequePrimaire() {
        return montantChequePrimaire;
    }

    public void setMontantChequePrimaire(double montantChequePrimaire) {
        this.montantChequePrimaire = montantChequePrimaire;
    }

    public String getNumCheque() {
        return numCheque;
    }

    public void setNumCheque(String numCheque) {
        this.numCheque = numCheque;
    }

    public String getFournisseur() {
        return fournisseur;
    }

    public void setFournisseur(String fournisseur) {
        this.fournisseur = fournisseur;
    }

    public String getDateCheque() {
        return dateCheque;
    }

    public void setDateCheque(String dateCheque) {
        this.dateCheque = dateCheque;
    }

    public double getMontantCheque() {
        return montantCheque;
    }

    public void setMontantCheque(double montantCheque) {
        this.montantCheque = montantCheque;
    }
    
}
