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
public class Fournisseur {
    String codeFournisseur, fournisseur, adresse,
            numTelephone, numRegCommerce, codeFiscale,
            articlePositif;

    public Fournisseur(){}
    
    public Fournisseur(String codeFournisseur, String fournisseur, String adresse, String numTelephone, String numRegCommerce, String codeFiscale, String articlePositif) {
        this.codeFournisseur = codeFournisseur;
        this.fournisseur = fournisseur;
        this.adresse = adresse;
        this.numTelephone = numTelephone;
        this.numRegCommerce = numRegCommerce;
        this.codeFiscale = codeFiscale;
        this.articlePositif = articlePositif;
    }

    public String getCodeFournisseur() {
        return codeFournisseur;
    }

    public void setCodeFournisseur(String codeFournisseur) {
        this.codeFournisseur = codeFournisseur;
    }

    public String getFournisseur() {
        return fournisseur;
    }

    public void setFournisseur(String fournisseur) {
        this.fournisseur = fournisseur;
    }

    public String getAdresse() {
        return adresse;
    }

    public void setAdresse(String adresse) {
        this.adresse = adresse;
    }

    public String getNumTelephone() {
        return numTelephone;
    }

    public void setNumTelephone(String numTelephone) {
        this.numTelephone = numTelephone;
    }

    public String getNumRegCommerce() {
        return numRegCommerce;
    }

    public void setNumRegCommerce(String numRegCommerce) {
        this.numRegCommerce = numRegCommerce;
    }

    public String getCodeFiscale() {
        return codeFiscale;
    }

    public void setCodeFiscale(String codeFiscale) {
        this.codeFiscale = codeFiscale;
    }

    public String getArticlePositif() {
        return articlePositif;
    }

    public void setArticlePositif(String articlePositif) {
        this.articlePositif = articlePositif;
    }
      
}
