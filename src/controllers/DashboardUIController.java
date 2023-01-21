/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controllers;

import databases.ConnectionSingleton;
import databases.DBQueries;
import java.io.InputStream;
import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import models.Affectation;
import models.Cheque;
import models.Facture;
import models.Fournisseur;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.design.JasperDesign;
import net.sf.jasperreports.engine.xml.JRXmlLoader;
import net.sf.jasperreports.view.JasperViewer;
import org.controlsfx.control.textfield.TextFields;
import utils.Utilities;

/**
 * FXML Controller class
 *
 * @author RiPou
 */
public class DashboardUIController implements Initializable {
    //FOURNISSEUR ATTRIBUTES
    @FXML 
    private Label lb_code_fournisseur_existe;
    @FXML
    private TextField tf_fournisseur_search;
    @FXML
    private TextField adresse;
    @FXML
    private TextField article_positif;
    @FXML
    private TextField code_fiscale;
    @FXML
    private TextField code_fournisseur;
    @FXML
    private TextField fournisseur;
    @FXML
    private TextField num_registre_commerce;
    @FXML
    private TextField num_telephone;
    @FXML
    private TableColumn<Fournisseur, String> col_adresse;
    @FXML
    private TableColumn<Fournisseur, String> col_article_positif;
    @FXML
    private TableColumn<Fournisseur, String> col_code_fiscale;
    @FXML
    private TableColumn<Fournisseur, String> col_code_fournisseur;
    @FXML
    private TableColumn<Fournisseur, String> col_fournisseur;
    @FXML
    private TableColumn<Fournisseur, String> col_num_registre_commerce;
    @FXML
    private TableColumn<Fournisseur, String> col_num_telephone;
   
    //FACTURE ATTRIBUTES
    @FXML 
    private Label lb_num_facture_existe;
    @FXML 
    private Label lb_montant_facture_total;
    @FXML 
    private Label lb_montant_facture_total_restant;
    @FXML
    private TextField tf_facture_fournisseurs;
    @FXML
    private DatePicker dp_facture_date_facture;
    @FXML
    private TextField tf_facture_search;
    @FXML
    private TextField tf_facture_bc_facture;
    @FXML
    private TextField tf_facture_br_facture;
    @FXML
    private TextField tf_facture_montant_facture;
    @FXML
    private TextField tf_facture_num_facture;  
    @FXML
    private TableColumn<Facture, String> col_facture_date;
    @FXML
    private TableColumn<Facture, Double> col_facture_montant;
    @FXML
    private TableColumn<Facture, Double> col_facture_montant_primaire;
    @FXML
    private TableColumn<Facture, String> col_facture_nom_fournisseur;
    @FXML
    private TableColumn<Facture, String> col_facture_num;
    @FXML
    private TableColumn<Facture, String> col_facture_bc;
    @FXML
    private TableColumn<Facture, String> col_facture_br;
    
    //CHEQUE ATTRIBUTES
    @FXML
    private Label lb_num_cheque_existe;
    @FXML
    private Label lb_montant_cheque_total;
    @FXML
    private Label lb_montant_cheque_total_restant;
    @FXML
    private TextField tf_cheque_fournisseur;
    @FXML
    private DatePicker dp_cheque_date_cheque;
    @FXML
    private TextField tf_cheque_search;
    @FXML
    private TextField tf_cheque_montant_cheque;
    @FXML
    private TextField tf_cheque_num_cheque;
    @FXML
    private TableColumn<Cheque, String> col_cheque_date;
    @FXML
    private TableColumn<Cheque, Double> col_cheque_montant;
    @FXML
    private TableColumn<Cheque, Double> col_cheque_montant_primaire;
    @FXML
    private TableColumn<Cheque, String> col_cheque_nom_fournisseur;
    @FXML
    private TableColumn<Cheque, String> col_cheque_num;
    
    //AFFECTATION ATTRIBUTES
    @FXML
    private TableColumn<Affectation, String> col_affectation_bc;
    @FXML
    private TableColumn<Affectation, String> col_affectation_br;
    @FXML
    private TableColumn<Affectation, String> col_affectation_date_cheque;
    @FXML
    private TableColumn<Affectation, String> col_affectation_date_facture;
    @FXML
    private TableColumn<Affectation, Double> col_affectation_reste_facture;
    @FXML
    private TableColumn<Affectation, Double> col_affectation_reste_cheque;
    @FXML
    private TableColumn<Affectation, String> col_affectation_fournisseur;
    @FXML
    private TableColumn<Affectation, Double> col_affectation_montant_cheque;
    @FXML
    private TableColumn<Affectation, Double> col_affectation_montant_facture;
    @FXML
    private TableColumn<Affectation, String> col_affectation_num_cheque;
    @FXML
    private TableColumn<Affectation, String> col_affectation_num_facture;
    @FXML
    private TextField tf_affectation_search;
    @FXML
    private TextField tf_affectation_num_cheque;
    @FXML
    private TextField tf_affectation_num_facture;
    @FXML
    private Label lb_affectation_montant_cheque_restant;
    @FXML
    private Label lb_affectation_montant_facture_restant;
    @FXML
    private Label lb_affectation_totale_facture_payee;
    @FXML 
    private Label lb_affectation_totale_cheque_payee;
    @FXML 
    private Label lb_affectation_reste_impayee_cheque;
    @FXML 
    private Label lb_affectation_reste_impayee_facture;

    //TABLE VIEWS 
    @FXML
    private TableView<Fournisseur> tbview_fournisseur;
    @FXML
    private TableView<Cheque> tbview_cheque;
    @FXML
    private TableView<Facture> tbview_facture;
    @FXML
    private TableView<Affectation> tbview_affectation;
    
    //CURRENT VALUES OF IDENTIFIER FOR EVERY TABLE, HERE WE SAVE THE CURRENT VALUE OF ID FOR EXAMPLE CODE FOURNISSEUR 
    //LIKE SO WE CAN UPDATE THE CURRENT CODE WITH NEW CODE
    String currentCodeFournisseur, currentNumFacture, currentNumCheque;
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        try {         
            Utilities.addTextLimiter(code_fournisseur, 4);

            loadAffectation();
            loadFournisseurs();
            loadFactures();
            loadCheques();      
            loadAutoCompletionTextFieldsFournisseur();
//            System.out.println("Cheque montant restant : " + DBQueries.getChequeMontant("2022"));
//            System.out.println("Facture montant restant : " + DBQueries.getFactureMontant("2022"));
        } catch (SQLException ex) {
            Logger.getLogger(DashboardUIController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }    
    
    public void loadAutoCompletionTextFieldsFournisseur(){
        ArrayList<String> array = new ArrayList<>();
        array.clear();
        
        ResultSet rs=null;
        try {
            String sql = "SELECT * FROM Fournisseurs";
            rs = ConnectionSingleton.getConnection().createStatement().executeQuery(sql);
            
            while(rs.next()) {
                array.add(rs.getString("fournisseur"));
            }        
            TextFields.bindAutoCompletion(tf_cheque_fournisseur, array.toArray());
            TextFields.bindAutoCompletion(tf_facture_fournisseurs, array.toArray());

            
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }finally {
            try { rs.close(); } catch (Exception e) { /* Ignored */ }
        }
        
        
    }
    
    //AFFECTATION ALL METHODS NEEDED
    private void loadAffectation() throws SQLException{
        this.tbview_affectation.setItems(null);
        this.col_affectation_fournisseur.setCellValueFactory(new PropertyValueFactory<>("fournisseur"));
        this.col_affectation_num_cheque.setCellValueFactory(new PropertyValueFactory<>("numCheque"));
        this.col_affectation_date_cheque.setCellValueFactory(new PropertyValueFactory<>("dateCheque"));
        /*
        THAT IS THE WAY TO SHOW DECIAMLS IN TABLE VIEW COLUMN
        */
        this.col_affectation_montant_cheque.setCellValueFactory(new PropertyValueFactory<>("montantCheque"));
        this.col_affectation_montant_cheque.setCellFactory(tc -> new TableCell<Affectation, Double>() {
            @Override
            protected void updateItem(Double value, boolean empty) {
                super.updateItem(value, empty) ;
                if (empty) {
                    setText(null);
                } else {
                    NumberFormat formatter = NumberFormat.getNumberInstance();
                    formatter.setMinimumFractionDigits(3);
                    setText(formatter.format(value.doubleValue()));
                }
            }
        });
        this.col_affectation_num_facture.setCellValueFactory(new PropertyValueFactory<>("numFacture"));
        this.col_affectation_date_facture.setCellValueFactory(new PropertyValueFactory<>("dateFacture"));
        /*
        THAT IS THE WAY TO SHOW DECIAMLS IN TABLE VIEW COLUMN
        */
        this.col_affectation_montant_facture.setCellValueFactory(new PropertyValueFactory<>("montantFacture"));
        this.col_affectation_montant_facture.setCellFactory(tc -> new TableCell<Affectation, Double>() {
            @Override
            protected void updateItem(Double value, boolean empty) {
                super.updateItem(value, empty) ;
                if (empty) {
                    setText(null);
                } else {
                    NumberFormat formatter = NumberFormat.getNumberInstance();
                    formatter.setMinimumFractionDigits(3);
                    setText(formatter.format(value.doubleValue()));
                }
            }
        });
        this.col_affectation_bc.setCellValueFactory(new PropertyValueFactory<>("bonCommande"));
        this.col_affectation_br.setCellValueFactory(new PropertyValueFactory<>("bonReception"));
        /*
        THAT IS THE WAY TO SHOW DECIAMLS IN TABLE VIEW COLUMN
        */
        this.col_affectation_reste_facture.setCellValueFactory(new PropertyValueFactory<>("resteFacture"));
        this.col_affectation_reste_facture.setCellFactory(tc -> new TableCell<Affectation, Double>() {
            @Override
            protected void updateItem(Double value, boolean empty) {
                super.updateItem(value, empty) ;
                if (empty) {
                    setText(null);
                } else {
                    NumberFormat formatter = NumberFormat.getNumberInstance();
                    formatter.setMinimumFractionDigits(3);
                    setText(formatter.format(value.doubleValue()));
                }
            }
        });
        this.col_affectation_reste_cheque.setCellValueFactory(new PropertyValueFactory<>("resteCheque"));
        /*
        THAT IS THE WAY TO SHOW DECIAMLS IN TABLE VIEW COLUMN
        */
        this.col_affectation_reste_cheque.setCellFactory(tc -> new TableCell<Affectation, Double>() {
            @Override
            protected void updateItem(Double value, boolean empty) {
                super.updateItem(value, empty) ;
                if (empty) {
                    setText(null);
                } else {
                    NumberFormat formatter = NumberFormat.getNumberInstance();
                    formatter.setMinimumFractionDigits(3);
                    setText(formatter.format(value.doubleValue()));
                }
            }
        });
        DBQueries.getListAffectation(this.tbview_affectation);
        
        if(this.tbview_affectation!=null)
            affectationPaymentCalculation();
                
        //SEARCH METHOD INSIDE TABLE CONTENTS
        FilteredList<Affectation> filteredList = new FilteredList<>(tbview_affectation.getItems(), e -> true);
        tf_affectation_search.setOnKeyReleased(e -> {
            tf_affectation_search.textProperty().addListener((observableValue, oldValue, newValue) -> {
                filteredList.setPredicate(affectation -> {
                    String lowerCaseFilter = newValue.toLowerCase();
                    if (newValue.isEmpty()){
                            return true;
                    }else if (affectation.getFournisseur().toLowerCase().contains(lowerCaseFilter) ||
                            affectation.getNumCheque().toLowerCase().contains(lowerCaseFilter) ||
                            affectation.getNumFacture().toLowerCase().contains(lowerCaseFilter) ||
                            affectation.getBonCommande().toLowerCase().contains(lowerCaseFilter) ||
                            affectation.getBonReception().toLowerCase().contains(lowerCaseFilter) ||
                            affectation.getDateCheque().toLowerCase().contains(lowerCaseFilter) ||
                            affectation.getDateFacture().contains(lowerCaseFilter)) {
                            return true;	
                    }					
                    return false;
                });
            });
            affectationPaymentCalculation();
        });
        
        SortedList<Affectation> sortedList = new SortedList<>(filteredList);
        sortedList.comparatorProperty().bind(tbview_affectation.comparatorProperty());
        this.tbview_affectation.setItems(sortedList);
    
    }
 
    @FXML
    void btnAffectation(ActionEvent event) throws SQLException {
        if(tf_affectation_num_cheque.getText().isEmpty() || tf_affectation_num_facture.getText().isEmpty())
            Utilities.showAlert("WARNING", "IL FAUT SAISIR LES DEUX CHAMPS NUM FACTURE ET CHEQUE POUR FAIRE L'AFFECTATION SUCCES");
        else{
            Cheque cheque = DBQueries.getCheque(tf_affectation_num_cheque.getText());
            Facture facture = DBQueries.getFacture(tf_affectation_num_facture.getText());
            DBQueries.insertAffectation(facture, cheque);
            loadAffectation(); 
            loadCheques();
            loadFactures();
            tf_affectation_num_cheque.clear();
            tf_affectation_num_facture.clear();
            lb_affectation_montant_cheque_restant.setText("");
            lb_affectation_montant_facture_restant.setText("");
        }
    }
    @FXML
    void btnSupprimerAffectation(ActionEvent event) throws SQLException {
        Affectation affectation = tbview_affectation.getSelectionModel().getSelectedItem();
        if(affectation == null){
            Utilities.showAlert("WARNING", "IL FAUT SELECTIONNER UNE LIGNE AVANT LA SUPPRESSION");
        }else{
            double montantChequeTotal = Double.valueOf(String.valueOf(tbview_affectation.getColumns().get(3).getCellObservableValue(0).getValue()));
            System.out.println("Montant Cheque total : " + montantChequeTotal);
            double montantFactureTotal = Double.valueOf(lb_affectation_totale_facture_payee.getText());
            System.out.println("Montant facture total : " + montantFactureTotal);
            DBQueries.updateMontantCheque(affectation.getNumCheque(), montantChequeTotal - montantFactureTotal);
            DBQueries.updateMontantFacture(affectation.getNumFacture(), affectation.getMontantFacture());
            DBQueries.deleteAffectation(affectation.getNumCheque(), affectation.getNumFacture());
            loadAffectation();
            loadCheques();
            loadFactures();
        }
    }
    @FXML
    void onMouseClickedAffectation(MouseEvent event) {
        Affectation affectation = tbview_affectation.getSelectionModel().getSelectedItem();
        if(affectation==null)
            System.out.println("No Affectation has been selected");
    }
    
    //FOURNISSEUR ALL METHODS NEEDED
    private void loadFournisseurs() throws SQLException {
        /*
        THE PROPERTY VALUE FACTORY HOLD A STRING NAME OF THE SAME VARIABLE NAME IN THE CLASS TO BE
        FILLED 
        */
        this.tbview_fournisseur.setItems(null);
        this.col_code_fournisseur.setCellValueFactory(new PropertyValueFactory<>("codeFournisseur"));
        this.col_fournisseur.setCellValueFactory(new PropertyValueFactory<>("fournisseur"));
        this.col_adresse.setCellValueFactory(new PropertyValueFactory<>("adresse"));
        this.col_num_telephone.setCellValueFactory(new PropertyValueFactory<>("numTelephone"));
        this.col_num_registre_commerce.setCellValueFactory(new PropertyValueFactory<>("numRegCommerce"));
        this.col_code_fiscale.setCellValueFactory(new PropertyValueFactory<>("codeFiscale"));
        this.col_article_positif.setCellValueFactory(new PropertyValueFactory<>("articlePositif"));
        DBQueries.getListFournisseurs(this.tbview_fournisseur);

        //UPDATE FOURNISSEUR LIST WE PUT IN TEXTFIELDS 
        loadAutoCompletionTextFieldsFournisseur();

        //SEARCH METHOD INSIDE TABLE CONTENTS
        FilteredList<Fournisseur> filteredList = new FilteredList<>(tbview_fournisseur.getItems(), e -> true);
        tf_fournisseur_search.setOnKeyReleased(e -> {
            tf_fournisseur_search.textProperty().addListener((observableValue, oldValue, newValue) -> {
            filteredList.setPredicate(fournisseur -> {
                if (newValue == null || newValue.isEmpty())
                        return true;
                
                String lowerCaseFilter = newValue.toLowerCase();
                if (fournisseur.getFournisseur().toLowerCase().contains(lowerCaseFilter) ||
                        fournisseur.getCodeFournisseur().toLowerCase().contains(lowerCaseFilter) ||
                        fournisseur.getNumTelephone().contains(lowerCaseFilter)) {
                        return true;	
                    }					
                    return false;
                });
            });
        });

        SortedList<Fournisseur> sortedList = new SortedList<>(filteredList);
        sortedList.comparatorProperty().bind(tbview_fournisseur.comparatorProperty());
        this.tbview_fournisseur.setItems(sortedList);
    }
    
    @FXML
    void CodeFournisseurExiste(KeyEvent event) {
        if(DBQueries.isFournisseurExist(this.code_fournisseur.getText())){
            lb_code_fournisseur_existe.setVisible(true);
        }else{
            lb_code_fournisseur_existe.setVisible(false);
        }
    }   
    @FXML
    void BtnAjouterFournisseur(ActionEvent event) throws SQLException {
        if(this.fournisseur.getText() == null || this.code_fournisseur.getText().isEmpty()){
            Utilities.showAlert("Attention", "SVP REMPLIR LE FOURNISSEUR ET VOTRE CODE");
        }else{
            Fournisseur fournisseurObject = new Fournisseur();
            fournisseurObject.setFournisseur(this.fournisseur.getText());
            fournisseurObject.setCodeFournisseur(this.code_fournisseur.getText());
            fournisseurObject.setAdresse(this.adresse.getText());
            fournisseurObject.setCodeFiscale(this.code_fiscale.getText());
            fournisseurObject.setNumTelephone(this.num_telephone.getText());
            fournisseurObject.setNumRegCommerce(this.num_registre_commerce.getText());
            fournisseurObject.setArticlePositif(this.article_positif.getText());
            DBQueries.insertFournisseur(fournisseurObject);
            loadFournisseurs();
            clearFieldsFournisseur();
        }
    }
    @FXML
    void BtnModifierFournisseur(ActionEvent event) throws SQLException {
        if(code_fournisseur.getText().isEmpty()){
            Utilities.showAlert("WARNING", "IL FAUT SELECTIONNER UNE LIGNE D'ABORD ET SAISIR LE CODE FOURNISSEUR");
        }else{
            Fournisseur f = new Fournisseur();
            f.setFournisseur(this.fournisseur.getText());
            f.setCodeFournisseur(this.code_fournisseur.getText());
            f.setAdresse(this.adresse.getText());
            f.setCodeFiscale(this.code_fiscale.getText());
            f.setNumTelephone(this.num_telephone.getText());
            f.setNumRegCommerce(this.num_registre_commerce.getText());
            f.setArticlePositif(this.article_positif.getText());
            
            if(code_fournisseur.getText().equals(currentCodeFournisseur)){
                DBQueries.updateFournisseur(f, currentCodeFournisseur);
                loadFournisseurs();
                clearFieldsFournisseur();
            }else if(!code_fournisseur.getText().equals(currentCodeFournisseur) && !DBQueries.isFournisseurExist(code_fournisseur.getText())){
                DBQueries.updateFournisseur(f, currentCodeFournisseur);
                loadFournisseurs();
                clearFieldsFournisseur();
            }else if(DBQueries.isFournisseurExist(code_fournisseur.getText())){
                Utilities.showAlert("WARNING", "FOURNISSEUR DEJA EXISTE DANS LA BASE");
            }        
        }
    }   
    @FXML
    void BtnSupprimerFournisseur(ActionEvent event) throws SQLException {
        if(tbview_facture.getSelectionModel().getSelectedItems() == null){
            Utilities.showAlert("WARNING", "IL FAUT SELECTION UNE LIGNE AVANT LA SUPPRESSION");
        }else{
            DBQueries.deleteFacture(tf_facture_num_facture.getText());
            loadFactures();
            loadAffectation();
            clearFieldsFacture();
            DBQueries.deleteFournisseur(this.code_fournisseur.getText());       
            loadFournisseurs();
            clearFieldsFournisseur();
        }
    }
    @FXML
    void MouseSelectFournisseur(MouseEvent event) {
        Fournisseur selectedFournisseur = tbview_fournisseur.getSelectionModel().getSelectedItem();
        if(selectedFournisseur == null){
            System.out.println("fournissseur is null");
        }else{
            currentCodeFournisseur = selectedFournisseur.getCodeFournisseur();
            this.code_fournisseur.setText(selectedFournisseur.getCodeFournisseur());
            this.fournisseur.setText(selectedFournisseur.getFournisseur());
            this.adresse.setText(selectedFournisseur.getAdresse());
            this.num_telephone.setText(selectedFournisseur.getNumTelephone());
            this.num_registre_commerce.setText(selectedFournisseur.getNumRegCommerce());
            this.code_fiscale.setText(selectedFournisseur.getCodeFiscale());
            this.article_positif.setText(selectedFournisseur.getArticlePositif());
        }
    }  
    @FXML
    void BtnClearFieldsFournisseur(ActionEvent event) {
        clearFieldsFournisseur();
    }
    
    void clearFieldsFournisseur(){
        this.fournisseur.clear();
        this.code_fournisseur.clear();
        this.adresse.clear();
        this.num_telephone.clear();
        this.code_fiscale.clear();
        this.num_registre_commerce.clear();
        this.article_positif.clear();
        lb_code_fournisseur_existe.setVisible(false);
    }
    
    /*  ###########################################################################################
    ###############################################################################################*/   

    //FACTURE ALL METHODS NEEDED
    private void loadFactures() throws SQLException {
        /*
        THE PROPERTY VALUE FACTORY HOLD A STRING NAME OF THE SAME VARIABLE NAME IN THE CLASS TO BE
        FILLED 
        */
        this.tbview_facture.setItems(null);
        this.col_facture_nom_fournisseur.setCellValueFactory(new PropertyValueFactory<>("fournisseur"));
        this.col_facture_date.setCellValueFactory(new PropertyValueFactory<>("dateFacture"));
//        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
//        this.col_facture_date.setCellFactory(tc -> new TableCell<Facture, LocalDate>() {
//            @Override
//            protected void updateItem(LocalDate date, boolean empty) {
//                super.updateItem(date, empty);
//                if (empty) {
//                    setText(null);
//                } else {
//                    setText(formatter.format(date));
//                }
//            }
//        });
        this.col_facture_num.setCellValueFactory(new PropertyValueFactory<>("numFacture"));
        this.col_facture_montant.setCellValueFactory(new PropertyValueFactory<>("montantFacture"));
        this.col_facture_montant_primaire.setCellValueFactory(new PropertyValueFactory<>("montantFacturePrimaire"));
        /*
        THAT IS THE WAY TO SHOW DECIAMLS IN TABLE VIEW COLUMN
        */
        this.col_facture_montant.setCellFactory(tc -> new TableCell<Facture, Double>() {
            @Override
            protected void updateItem(Double value, boolean empty) {
                super.updateItem(value, empty) ;
                if (empty) {
                    setText(null);
                } else {
                    NumberFormat formatter = NumberFormat.getNumberInstance();
                    formatter.setMinimumFractionDigits(3);
                    setText(formatter.format(value.doubleValue()));
                }
            }
        });
        this.col_facture_montant_primaire.setCellFactory(tc -> new TableCell<Facture, Double>() {
            @Override
            protected void updateItem(Double value, boolean empty) {
                super.updateItem(value, empty) ;
                if (empty) {
                    setText(null);
                } else {
                    NumberFormat formatter = NumberFormat.getNumberInstance();
                    formatter.setMinimumFractionDigits(3);
                    setText(formatter.format(value.doubleValue()));
                }
            }
        });
        
        this.col_facture_bc.setCellValueFactory(new PropertyValueFactory<>("bonCommande"));
        this.col_facture_br.setCellValueFactory(new PropertyValueFactory<>("bonReception"));
        DBQueries.getListFactures(this.tbview_facture);
        if(this.tbview_facture!=null)
            getFactureMontantTotal();
        
        //SEARCH METHOD INSIDE TABLE CONTENTS
        FilteredList<Facture> filteredList = new FilteredList<>(tbview_facture.getItems(), e -> true);
        tf_facture_search.setOnKeyReleased(e -> {
            tf_facture_search.textProperty().addListener((observableValue, oldValue, newValue) -> {
                filteredList.setPredicate(facture -> {
                    if (newValue == null || newValue.isEmpty())
                            return true;

                    String lowerCaseFilter = newValue.toLowerCase();
                    if (facture.getNumFacture().toLowerCase().contains(lowerCaseFilter) ||
                            facture.getDateFacture().toLowerCase().contains(lowerCaseFilter) ||
                            facture.getFournisseur().toLowerCase().contains(lowerCaseFilter) ||
                            facture.getBonReception().toLowerCase().contains(lowerCaseFilter) ||
                            facture.getBonCommande().contains(lowerCaseFilter)) {
                            return true;	
                    }					
                    return false;
                });
                    getFactureMontantTotal();
            });
        });

        SortedList<Facture> sortedList = new SortedList<>(filteredList);
        sortedList.comparatorProperty().bind(tbview_facture.comparatorProperty());
        this.tbview_facture.setItems(sortedList);
    }
    
    @FXML
    void NumFactureExiste(KeyEvent event) {
        if(DBQueries.isFactureExist(this.tf_facture_num_facture.getText())){
            lb_num_facture_existe.setVisible(true);
        }else{
            lb_num_facture_existe.setVisible(false);
        }
    }
    @FXML
    void btnModifierFacture(ActionEvent event) throws SQLException {
        if(tf_facture_num_facture.getText() == null || tf_facture_montant_facture.getText().isEmpty()){
            Utilities.showAlert("ATTENTION", "IL FAUT REMPLIR TOUS LES CHAMPS");
        }else{
            Facture facture = new Facture();
            facture.setFournisseur(tf_facture_fournisseurs.getText());
            facture.setNumFacture(tf_facture_num_facture.getText());
            facture.setDateFacture(dp_facture_date_facture.getEditor().getText());
            facture.setBonCommande(tf_facture_bc_facture.getText());
            facture.setBonReception(tf_facture_br_facture.getText());
            facture.setMontantFacture(Double.valueOf(tf_facture_montant_facture.getText()));
            facture.setMontantFacturePrimaire(Double.valueOf(tf_facture_montant_facture.getText()));

            if(tf_facture_num_facture.getText().equals(currentNumFacture)){
                DBQueries.updateFacture(facture, currentNumFacture);
                loadFactures();
                loadAffectation();
                clearFieldsFacture();  
            }else if(!tf_facture_num_facture.getText().equals(currentNumFacture) && !DBQueries.isFactureExist(tf_facture_num_facture.getText())){
                DBQueries.updateFacture(facture, currentNumFacture);
                loadFactures();
                loadAffectation();
                clearFieldsFacture();  
            }else if(DBQueries.isFactureExist(tf_facture_num_facture.getText())){
                Utilities.showAlert("WARNING", "FACTURE DEJA EXISTE DANS LA BASE");
            }
        }
    }
    @FXML
    void btnClearFactureFields(ActionEvent event) {
        clearFieldsFacture();
    }
    @FXML
    void btnAjouterFacture(ActionEvent event) throws SQLException {
        if(tf_facture_num_facture.getText() == null){
            Utilities.showAlert("ATTENTION", "IL FAUT REMPLIR TOUS LES CHAMPS");
        }else if(DBQueries.isFactureExist(tf_facture_num_facture.getText())){
            Utilities.showAlert("ATTENTION", "FACTURE EST DEJA EXISTE DANS LA BASE");            
        }else{
            Facture facture = new Facture();
            facture.setFournisseur(tf_facture_fournisseurs.getText());
            facture.setNumFacture(tf_facture_num_facture.getText());
            facture.setDateFacture(dp_facture_date_facture.getEditor().getText());
            facture.setMontantFacture(Double.valueOf(tf_facture_montant_facture.getText()));
            facture.setMontantFacturePrimaire(Double.valueOf(tf_facture_montant_facture.getText()));
            facture.setBonCommande(tf_facture_bc_facture.getText());
            facture.setBonReception(tf_facture_br_facture.getText());
            DBQueries.insertFacture(facture);
            loadFactures();
            loadAffectation();
            clearFieldsFacture();
        }
    }
    @FXML
    void btnSupprimerFacture(ActionEvent event) throws SQLException {
        if(tbview_facture.getSelectionModel().getSelectedItems() == null){
            Utilities.showAlert("WARNING", "IL FAUT SELECTION UNE LIGNE AVANT LA SUPPRESSION");
        }else{
            DBQueries.deleteFacture(tf_facture_num_facture.getText());
            loadFactures();
            loadAffectation();
            clearFieldsFacture();
        } 
    }  
    @FXML
    void MouseSelectFactureRow(MouseEvent event) {
        Facture facture = tbview_facture.getSelectionModel().getSelectedItem();
        if(facture!=null){
            currentNumFacture = facture.getNumFacture();
            tf_facture_num_facture.setText(facture.getNumFacture());
            tf_facture_montant_facture.setText(String.valueOf(facture.getMontantFacture()));
            tf_facture_bc_facture.setText(facture.getBonCommande());
            tf_facture_br_facture.setText(facture.getBonReception());
            tf_facture_fournisseurs.setText(facture.getFournisseur());
            dp_facture_date_facture.getEditor().setText(facture.getDateFacture());
        }
        
   }

    void clearFieldsFacture(){
        tf_facture_fournisseurs.clear();
        tf_facture_num_facture.clear();
        tf_facture_montant_facture.clear();
        tf_facture_bc_facture.clear();
        tf_facture_br_facture.clear();
        dp_facture_date_facture.getEditor().setText("");
        lb_num_facture_existe.setVisible(false);
    }
    
    //CHEQUE ALL METHODS NEEDED  
    private void loadCheques() throws SQLException {
        this.tbview_cheque.setItems(null);
        /*
        THE PROPERTY VALUE FACTORY HOLD A STRING NAME OF THE SAME VARIABLE NAME IN THE CLASS TO BE
        FILLED 
        */
        this.col_cheque_nom_fournisseur.setCellValueFactory(new PropertyValueFactory<>("fournisseur"));
        this.col_cheque_date.setCellValueFactory(new PropertyValueFactory<>("dateCheque"));
        this.col_cheque_num.setCellValueFactory(new PropertyValueFactory<>("numCheque"));
        this.col_cheque_montant.setCellValueFactory(new PropertyValueFactory<>("montantCheque"));
        this.col_cheque_montant.setCellFactory(tc -> new TableCell<Cheque, Double>() {
            @Override
            protected void updateItem(Double value, boolean empty) {
                super.updateItem(value, empty) ;
                if (empty) {
                    setText(null);
                } else {
                    NumberFormat formatter = NumberFormat.getNumberInstance();
                    formatter.setMinimumFractionDigits(3);
                    setText(formatter.format(value.doubleValue()));
                }
            }
        });
        this.col_cheque_montant_primaire.setCellValueFactory(new PropertyValueFactory<>("montantChequePrimaire"));
        this.col_cheque_montant_primaire.setCellFactory(tc -> new TableCell<Cheque, Double>() {
            @Override
            protected void updateItem(Double value, boolean empty) {
                super.updateItem(value, empty) ;
                if (empty) {
                    setText(null);
                } else {
                    NumberFormat formatter = NumberFormat.getNumberInstance();
                    formatter.setMinimumFractionDigits(3);
                    setText(formatter.format(value.doubleValue()));
                }
            }
        });
        DBQueries.getListCheques(this.tbview_cheque);
        if(this.tbview_cheque!=null)
            getChequeMontantTotal();

        //SEARCH METHOD INSIDE TABLE CONTENTS
        FilteredList<Cheque> filteredList = new FilteredList<>(tbview_cheque.getItems(), e -> true);
        tf_cheque_search.setOnKeyReleased(e -> {
            tf_cheque_search.textProperty().addListener((observableValue, oldValue, newValue) -> {
                filteredList.setPredicate(cheque -> {
                    if (newValue == null || newValue.isEmpty()){
                        return true;
                    }

                    String lowerCaseFilter = newValue.toLowerCase();
                    if (cheque.getFournisseur().toLowerCase().contains(lowerCaseFilter) ||
                            cheque.getNumCheque().toLowerCase().contains(lowerCaseFilter) ||
                            cheque.getDateCheque().contains(lowerCaseFilter)) {
                            return true;	
                    }					
                    return false;
                });
                getChequeMontantTotal();
            });
        });
        SortedList<Cheque> sortedList = new SortedList<>(filteredList);
        sortedList.comparatorProperty().bind(tbview_cheque.comparatorProperty());
        this.tbview_cheque.setItems(sortedList);
        
    } 
    @FXML
    void NumChequeExiste(KeyEvent event) {
        if(DBQueries.isChequeExist(this.tf_cheque_num_cheque.getText())){
            lb_num_cheque_existe.setVisible(true);
        }else{
            lb_num_cheque_existe.setVisible(false);
        }
    }  
    @FXML
    void btnAjouterCheque(ActionEvent event) throws SQLException {     
        if(this.tf_cheque_num_cheque.getText() == null){
            Utilities.showAlert("ATTENTION", "IL FAUT REMPLIR TOUS LES CHAMPS");
        }else if(DBQueries.isChequeExist(this.tf_cheque_num_cheque.getText())){
            Utilities.showAlert("ATTENTION", "CHEQUE EST DEJA EXISTE DANS LA BASE");            
        }else{
            Cheque cheque = new Cheque();
            cheque.setFournisseur(tf_cheque_fournisseur.getText());
            cheque.setDateCheque(dp_cheque_date_cheque.getEditor().getText());
            cheque.setMontantCheque(Double.valueOf(tf_cheque_montant_cheque.getText()));    
            cheque.setMontantChequePrimaire(Double.valueOf(tf_cheque_montant_cheque.getText()));         
            cheque.setNumCheque(tf_cheque_num_cheque.getText());
            DBQueries.insertCheque(cheque);
            loadCheques();
            clearFieldsCheque();
        }
    }
    @FXML
    void btnClearFieldsCheque(ActionEvent event) {
        clearFieldsCheque();
    }
    @FXML
    void btnModifierCheque(ActionEvent event) throws SQLException {      
        if(tf_cheque_num_cheque.getText().isEmpty() || tf_cheque_montant_cheque.getText().isEmpty()){
            Utilities.showAlert("ATTENTION", "IL FAUT REMPLIR CHEQUE NUM ET MONTANT");
        }else{
            Cheque cheque = new Cheque();
            cheque.setFournisseur(tf_cheque_fournisseur.getText());
            cheque.setDateCheque(dp_cheque_date_cheque.getEditor().getText());
            cheque.setNumCheque(tf_cheque_num_cheque.getText());
            cheque.setMontantCheque(Double.valueOf(tf_cheque_montant_cheque.getText()));  
            cheque.setMontantChequePrimaire(Double.valueOf(tf_cheque_montant_cheque.getText()));         
            
            if(tf_cheque_num_cheque.getText().equals(currentNumCheque)){
                DBQueries.updateCheque(cheque, currentNumCheque);
                DBQueries.updateChequeAffectation(cheque, currentNumCheque);
                loadCheques();
                loadAffectation();
                clearFieldsCheque();
            }else if(!tf_cheque_num_cheque.getText().equals(currentNumCheque) && !DBQueries.isChequeExist(tf_cheque_num_cheque.getText())){
                DBQueries.updateCheque(cheque, currentNumCheque);
                loadCheques();
                loadAffectation();
                clearFieldsCheque();
            }else if(DBQueries.isChequeExist(tf_cheque_num_cheque.getText())){
                Utilities.showAlert("WARNING", "CHEQUE DEJA EXISTE DANS LA BASE");
            }
        }
    }
    @FXML
    void btnSupprimerCheque(ActionEvent event) throws SQLException {
        if(tbview_cheque.getSelectionModel().getSelectedItems() == null){
            Utilities.showAlert("WARNING", "IL FAUT SELECTION UNE LIGNE AVANT LA SUPPRESSION");
        }else{
            DBQueries.deleteCheque(tf_cheque_num_cheque.getText());
            loadCheques();
            loadAffectation();
            clearFieldsCheque();
        } 
    } 
    @FXML
    void MouseSelectCheque(MouseEvent event) {
        Cheque cheque = tbview_cheque.getSelectionModel().getSelectedItem();
        //THROWS NULL POINTER IDK YET WHY
        if(cheque!=null){
            currentNumCheque = cheque.getNumCheque();
            tf_cheque_fournisseur.setText(cheque.getFournisseur());
            dp_cheque_date_cheque.getEditor().setText(cheque.getDateCheque());
            tf_cheque_montant_cheque.setText(String.valueOf(cheque.getMontantCheque()));
            tf_cheque_num_cheque.setText(cheque.getNumCheque());
        }
    }
   
    private void clearFieldsCheque() {
        this.tf_cheque_fournisseur.setText(null);
        this.dp_cheque_date_cheque.getEditor().setText("");
        this.tf_cheque_montant_cheque.clear();
        this.tf_cheque_num_cheque.clear();
    }
    
    boolean isEmptyFieldsCheque(){
        if(tf_cheque_fournisseur.getText() == null ||
                dp_cheque_date_cheque.getEditor().getText() == null ||
                tf_cheque_montant_cheque.getText() == null || tf_cheque_num_cheque.getText() == null){
            Utilities.showAlert("ATTENTION", "IL FAUT REMPLIR TOUS LES CHAMPS");
            return true;
        }
        return false;
    }
    
    @FXML
    void btnImprimerCheques(ActionEvent event) {
        //String path = "src//jasper//SORTIE.jrxml";
        String path = "/jasperreports/CHEQUESREPORT.jrxml";
       // File file = new File(path);
        jasperreportViewer(path);
    }

    @FXML
    void btnImprimerFactures(ActionEvent event) {
        //String path = "src//jasper//SORTIE.jrxml";
        String path = "/jasperreports/FACTURESREPORT.jrxml";
        // File file = new File(path);
        jasperreportViewer(path);
    }

    @FXML
    void btnImprimerFournisseurs(ActionEvent event) {
        //String path = "src//jasper//SORTIE.jrxml";
        String path = "/jasperreports/FOURNISSEURSREPORT.jrxml";
        // File file = new File(path);
        jasperreportViewer(path);
    }
    
    //JASPER REPORT LIBRARY USED TO PRINT PERSONALIZED PAPER WHICH IS GENERATED FROM DB
    //IMPORTANTTTTTTTTTTTTTTTTTTT : IF THE TABLE DATA IS REPEATED YOU NEED TO MOVE THE TABLE FROM DETAILED SECTION
    //TO THE HEADER PAGE SECTION AND EVERYTHING WILL BE OKAY DUDE LET'S HOPE U R IN GOOD STATE NOW
    //WITH BETTER FUTURE FIIIIIIGHHHHTOOOO
    void jasperreportViewer(String path){
        try {
            InputStream iStream = getClass().getResourceAsStream(path);
            JasperDesign jasperDesign = JRXmlLoader.load(iStream);
            JasperReport jasperReport = JasperCompileManager.compileReport(jasperDesign);
            JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, null, ConnectionSingleton.getConnection());
            JasperViewer.viewReport(jasperPrint, false);
        } catch (JRException e) {
        }
    }
    
    
    void affectationPaymentCalculation(){
        double totaleChequePayee = 0;
        double totaleFacturePayee = 0;
        double resteTotaleImpayeeCheque = 0;
        double resteTotaleImpayeeFacture = 0;

//        for(int i=0; i<tbview_affectation.getItems().size(); i++){
//            double value = Double.valueOf(String.valueOf(tbview_affectation.getColumns().get(6).getCellObservableValue(i).getValue()));
//            totaleFacturePayee = totaleFacturePayee + value;
//            String numCheque = String.valueOf(String.valueOf(tbview_affectation.getColumns().get(1).getCellObservableValue(0).getValue()));
//            if(!(numCheque.equals(String.valueOf(String.valueOf(tbview_affectation.getColumns().get(1).getCellObservableValue(0).getValue()))))){
//                totaleChequePayee = totaleChequePayee + Double.valueOf(String.valueOf(tbview_affectation.getColumns().get(3).getCellObservableValue(0).getValue()));
//            }
//        }
        
        if(totaleChequePayee >= totaleFacturePayee)
            resteTotaleImpayeeCheque = totaleChequePayee - totaleFacturePayee;
        else{
            resteTotaleImpayeeFacture = (totaleChequePayee - totaleFacturePayee)* -1;
        }
        
        NumberFormat formatter = NumberFormat.getNumberInstance();
        formatter.setMinimumFractionDigits(3);
        
        lb_affectation_totale_cheque_payee.setText(formatter.format(totaleChequePayee));
        lb_affectation_totale_facture_payee.setText(formatter.format(totaleFacturePayee));
        lb_affectation_reste_impayee_cheque.setText(formatter.format(resteTotaleImpayeeCheque));
        lb_affectation_reste_impayee_facture.setText(formatter.format(resteTotaleImpayeeFacture));
    }
    
    double getChequeMontantTotal(){
        double total = 0;
        double restant = 0;
        for(int i=0; i<tbview_cheque.getItems().size(); i++){
            double value = Double.valueOf(String.valueOf(tbview_cheque.getColumns().get(4).getCellObservableValue(i).getValue()));
            total = total + value;
            double valueRestant = Double.valueOf(String.valueOf(tbview_cheque.getColumns().get(3).getCellObservableValue(i).getValue()));
            restant = restant + valueRestant;
        }
        NumberFormat formatter = NumberFormat.getNumberInstance();
        formatter.setMinimumFractionDigits(3);
        lb_montant_cheque_total.setText(formatter.format(total) + " DA");
        lb_montant_cheque_total_restant.setText(formatter.format(restant) + " DA");
        return total;
    }

    double getFactureMontantTotal(){
        double total = 0;
        double restant = 0;
        
        for(int i=0; i<tbview_facture.getItems().size(); i++){
            double value = Double.valueOf(String.valueOf(tbview_facture.getColumns().get(4).getCellObservableValue(i).getValue()));
            double valueRestant = Double.valueOf(String.valueOf(tbview_facture.getColumns().get(3).getCellObservableValue(i).getValue()));
            total = total + value;
            restant = restant + valueRestant;
        }
        
        NumberFormat formatter = NumberFormat.getNumberInstance();
        formatter.setMinimumFractionDigits(3);
        lb_montant_facture_total.setText(formatter.format(total) + " DA");
        lb_montant_facture_total_restant.setText(formatter.format(restant) + " DA");
        return total;
    }
    
    @FXML
    void getChequeMontantRestant(KeyEvent event) {
        if(tf_affectation_num_cheque.getText().isEmpty()){
            lb_affectation_montant_cheque_restant.setText("0.00 DA");
        }else if(!DBQueries.isChequeExist(tf_affectation_num_cheque.getText())){
            lb_affectation_montant_cheque_restant.setText("N'EXISTE PAS");
        }else{
            double montantCheque = DBQueries.getChequeMontantRestant(tf_affectation_num_cheque.getText());
            NumberFormat formatter = NumberFormat.getNumberInstance();
            formatter.setMinimumFractionDigits(3);
            lb_affectation_montant_cheque_restant.setText(formatter.format(montantCheque) + " DA");
        }   
    }
    @FXML
    void getFactureMontantRestant(KeyEvent event) {
        if(tf_affectation_num_facture.getText().isEmpty()){
            lb_affectation_montant_facture_restant.setText("0.00 DA");
        }else if(!DBQueries.isFactureExist(tf_affectation_num_facture.getText())){
            lb_affectation_montant_facture_restant.setText("N'EXISTE PAS");
        }else{
            double montantFacture = DBQueries.getFactureMontantRestant(tf_affectation_num_facture.getText());
            NumberFormat formatter = NumberFormat.getNumberInstance();
            formatter.setMinimumFractionDigits(3);
            lb_affectation_montant_facture_restant.setText(formatter.format(montantFacture) + " DA");
        }
    }

}
