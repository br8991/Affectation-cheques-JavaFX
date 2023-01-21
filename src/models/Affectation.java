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
public class Affectation {
    String fournisseur, numCheque, dateCheque, numFacture, dateFacture, bonCommande, bonReception; 
    double montantCheque, montantFacture, resteCheque, resteFacture;
    
    public Affectation(){}

    public Affectation(String fournisseur, String numCheque, String dateCheque, double montantCheque,
            String numFacture, String dateFacture, double montantFacture, String bonCommande, String bonReception,
            double resteCheque, double resteFacture) {
        this.fournisseur = fournisseur;
        this.numCheque = numCheque;
        this.dateCheque = dateCheque;
        this.numFacture = numFacture;
        this.dateFacture = dateFacture;
        this.bonCommande = bonCommande;
        this.bonReception = bonReception;
        this.montantCheque = montantCheque;
        this.montantFacture = montantFacture;
        this.resteCheque = resteCheque;
        this.resteFacture = resteFacture;
    }

    public double getResteCheque() {
        return resteCheque;
    }

    public void setResteCheque(double resteCheque) {
        this.resteCheque = resteCheque;
    }

    public double getResteFacture() {
        return resteFacture;
    }

    public void setResteFacture(double resteFacture) {
        this.resteFacture = resteFacture;
    }

    public String getFournisseur() {
        return fournisseur;
    }

    public void setFournisseur(String fournisseur) {
        this.fournisseur = fournisseur;
    }

    public String getNumCheque() {
        return numCheque;
    }

    public void setNumCheque(String numCheque) {
        this.numCheque = numCheque;
    }

    public String getDateCheque() {
        return dateCheque;
    }

    public void setDateCheque(String dateCheque) {
        this.dateCheque = dateCheque;
    }

    public String getNumFacture() {
        return numFacture;
    }

    public void setNumFacture(String numFacture) {
        this.numFacture = numFacture;
    }

    public String getDateFacture() {
        return dateFacture;
    }

    public void setDateFacture(String dateFacture) {
        this.dateFacture = dateFacture;
    }

    public String getBonCommande() {
        return bonCommande;
    }

    public void setBonCommande(String bonCommande) {
        this.bonCommande = bonCommande;
    }

    public String getBonReception() {
        return bonReception;
    }

    public void setBonReception(String bonReception) {
        this.bonReception = bonReception;
    }

    public double getMontantCheque() {
        return montantCheque;
    }

    public void setMontantCheque(double montantCheque) {
        this.montantCheque = montantCheque;
    }

    public double getMontantFacture() {
        return montantFacture;
    }

    public void setMontantFacture(double montantFacture) {
        this.montantFacture = montantFacture;
    }
    
    
}
