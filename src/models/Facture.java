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
public class Facture {
    String numFacture, fournisseur, dateFacture, bonCommande, bonReception;
    double montantFacture, montantFacturePrimaire;

    public Facture(String fournisseur, String numFacture, String dateFacture, double montantFacture, double montantFacturePrimaire,
            String bonCommande, String bonReception) {
        this.numFacture = numFacture;
        this.fournisseur = fournisseur;
        this.dateFacture = dateFacture;
        this.montantFacture = montantFacture;
        this.montantFacturePrimaire = montantFacturePrimaire;
        this.bonCommande = bonCommande;
        this.bonReception = bonReception;
    }
    
    public Facture(String numFacture, String dateFacture, double montantFacture, double montantFacturePrimaire, String bonCommande, String bonReception){
        this.numFacture = numFacture;
        this.dateFacture = dateFacture;
        this.montantFacture = montantFacture;
        this.montantFacturePrimaire = montantFacturePrimaire;
        this.bonCommande = bonCommande;
        this.bonReception = bonReception;
    }

    public Facture() {
    }

    public double getMontantFacturePrimaire() {
        return montantFacturePrimaire;
    }

    public void setMontantFacturePrimaire(double montantFacturePrimaire) {
        this.montantFacturePrimaire = montantFacturePrimaire;
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
    
    public String getNumFacture() {
        return numFacture;
    }

    public void setNumFacture(String numFacture) {
        this.numFacture = numFacture;
    }

    public String getFournisseur() {
        return fournisseur;
    }

    public void setFournisseur(String fournisseur) {
        this.fournisseur = fournisseur;
    }

    public String getDateFacture() {
        return dateFacture;
    }

    public void setDateFacture(String dateFacture) {
        this.dateFacture = dateFacture;
    }

    public double getMontantFacture() {
        return montantFacture;
    }

    public void setMontantFacture(double montantFacture) {
        this.montantFacture = montantFacture;
    }
    
}
