/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package databases;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.TableView;
import models.Affectation;
import models.Cheque;
import models.Facture;
import models.Fournisseur;
import utils.Utilities;

/**
 *
 * @author RiPou
 */
public class DBQueries {
    
    static final String SQL_TABLE_FOURNISSEURS = "SELECT * FROM Fournisseurs";
    static final String SQL_TABLE_FACTURES = "SELECT * FROM Factures";
    static final String SQL_TABLE_CHEQUES = "SELECT * FROM Cheques";
    static final String SQL_TABLE_AFFECTATIONS = "SELECT * FROM Affectations";
    
    //static final String SQL_TABLE_SORTIE = "SELECT * FROM fournisseur";
    private static PreparedStatement ps = null;
    private static ResultSet rs = null;
    private static final Connection CONNECTION = ConnectionSingleton.getConnection();;
    
    public static boolean login(String username, String password){
        return false;
    }
    
    //AFFECTATION ALL CRUD METHODS NEEDED
    public static ObservableList<Affectation> getListAffectation(TableView<Affectation> tbv) throws SQLException {
        ObservableList<Affectation> listAffectation = FXCollections.observableArrayList();
        try{
            rs = CONNECTION.createStatement().executeQuery(SQL_TABLE_AFFECTATIONS);
            while(rs.next()){
                listAffectation.add(new Affectation(rs.getString(1), rs.getString(2), rs.getString(3), rs.getDouble(4),
                rs.getString(5), rs.getString(6), rs.getDouble(7), rs.getString(8), rs.getString(9),
                rs.getDouble(10), rs.getDouble(11)));
            }
            tbv.setItems(listAffectation);
        }catch(SQLException e){}
        finally{
            try{rs.close();}    catch(SQLException e){}
        }
        return listAffectation;
    }
    
    public static boolean insertAffectation(Facture facture, Cheque cheque){
        String sql = "INSERT INTO Affectations (fournisseur, num_cheque, date_cheque, montant_cheque,"
                + " num_facture, date_facture, montant_facture,"
                + " bon_commande, bon_reception, reste_cheque, reste_facture) "
                + " VALUES(?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
        try{
            double montantChequeRestant = 0;
            double montantFactureRestant = 0;
            
            if(getChequeMontantRestant(cheque.getNumCheque()) == 0){
                Utilities.showAlert("WARNING", "CE CHEQUE EST VIDE ESSAYER AVEC UN AUTRE ?!!");
            }else if(getFactureMontantRestant(facture.getNumFacture()) == 0){
                Utilities.showAlert("WARNING", "CETTE FACTURE EST VIDE ESSAYER AVEC UN AUTRE ?!!");
            }else{
                if(getChequeMontantRestant(cheque.getNumCheque())>=getFactureMontantRestant(facture.getNumFacture())){
                    montantChequeRestant = getChequeMontantRestant(cheque.getNumCheque()) - getFactureMontantRestant(facture.getNumFacture());
                }else if(getChequeMontantRestant(cheque.getNumCheque())<getFactureMontantRestant(facture.getNumFacture())){
                    montantFactureRestant = (getChequeMontantRestant(cheque.getNumCheque()) - getFactureMontantRestant(facture.getNumFacture())) * -1;
                }

                updateMontantCheque(cheque.getNumCheque(), montantChequeRestant);
                updateMontantFacture(facture.getNumFacture(),montantFactureRestant);

                ps = CONNECTION.prepareStatement(sql);
                ps.setString(1, cheque.getFournisseur());
                ps.setString(2, cheque.getNumCheque());
                ps.setString(3, cheque.getDateCheque());
                ps.setDouble(4, cheque.getMontantCheque());
                ps.setString(5, facture.getNumFacture());
                ps.setString(6, facture.getDateFacture());
                ps.setDouble(7, facture.getMontantFacture());
                ps.setString(8, facture.getBonCommande());
                ps.setString(9, facture.getBonReception());
                ps.setDouble(10, montantChequeRestant);
                ps.setDouble(11, montantFactureRestant);
                ps.execute();                
            }
                            
        }catch(SQLException e){e.printStackTrace(); return false;}
        finally{
            try{ps.close();} catch(SQLException e){e.printStackTrace(); return false;}
        }
        return true;
    }
    
    public static boolean deleteAffectation(String numCheque, String numFacture){
        String sql = "DELETE FROM Affectations WHERE num_cheque = ? AND num_facture = ?";

        try {
            ps = CONNECTION.prepareStatement(sql);
            ps.setString(1, numCheque);
            ps.setString(2, numFacture);
            int row = ps.executeUpdate();

            if(row>0)
                return true;

        } catch (SQLException ex) {
            Logger.getLogger(DBQueries.class.getName()).log(Level.SEVERE, null, ex);
        }finally{
            try { ps.close(); } catch (Exception e) { /* Ignored */ }
        }          
        return false;
    }
    
    public static Double getChequeMontantRestant(String numCheque){
        String sql = "SELECT * FROM Cheques WHERE num_cheque=?";
        double montantCheque = 0.00;
        try{
            ps = CONNECTION.prepareStatement(sql);
            ps.setString(1, numCheque);
            rs = ps.executeQuery();
            if(rs.next()){
                montantCheque = rs.getDouble("montant_cheque");
            }
        }catch(SQLException e){
        }finally{
            try{ps.close(); rs.close();}catch(Exception e){}
        }
        return montantCheque;
    }
    
    public static double getFactureMontantRestant(String numFacture){
        String sql = "SELECT * FROM Factures WHERE num_facture=?";
        double montantFacture = 0.00;
        try{
            ps = CONNECTION.prepareStatement(sql);
            ps.setString(1, numFacture);
            rs = ps.executeQuery();
            if(rs.next()){
                montantFacture = rs.getDouble("montant_facture");
            }
        }catch(SQLException e){
        }finally{
            try{ps.close(); rs.close();}catch(Exception e){}
        }
        return montantFacture;
    }
    
    //FOURNISSEUR ALL CRUD METHODS NEEDED
    public static ObservableList<Fournisseur> getListFournisseurs(TableView<Fournisseur> tbv) throws SQLException{
        ObservableList<Fournisseur> listFournisseurs = FXCollections.observableArrayList();
        rs = CONNECTION.createStatement().executeQuery(SQL_TABLE_FOURNISSEURS);
        try{
            while(rs.next()) {
//                System.out.println("Not null fournisseur");
                listFournisseurs.add(new Fournisseur(
                                rs.getString(1), rs.getString(2),
                                rs.getString(3), rs.getString(4),
                                rs.getString(5), rs.getString(6),
                                rs.getString(7)));
            }
            tbv.setItems(listFournisseurs);
        }catch(SQLException e){
        }
        finally{
            try { rs.close(); } catch (Exception e) { /* Ignored */ }
        }
        return listFournisseurs;
    }
    
    public static boolean insertFournisseur(Fournisseur fournisseur){  
        if(isFournisseurExist(fournisseur.getCodeFournisseur())){
            Utilities.showAlert("AJOUTER", "LE FOURNISSEUR EST DEJA EXISTE DANS LA BASE");
        }else{
            
            String sql = "INSERT INTO Fournisseurs (code_fournisseur, fournisseur, adresse, num_telephone,"
                + "num_reg_commerce, code_fiscale, article_positif) "
                            + "VALUES (?, ?, ?, ?, ?, ?, ?)";
            
            try {
                ps = CONNECTION.prepareStatement(sql);
                ps.setString(1, fournisseur.getCodeFournisseur());
                ps.setString(2, fournisseur.getFournisseur());
                ps.setString(3, fournisseur.getAdresse());
                ps.setString(4, fournisseur.getNumTelephone());
                ps.setString(5, fournisseur.getNumRegCommerce());
                ps.setString(6, fournisseur.getCodeFiscale());
                ps.setString(7, fournisseur.getArticlePositif());
                ps.execute();
            } catch (SQLException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
            }finally {
                try { ps.close(); } catch (Exception e) { /* Ignored */ }
            }
        }      
        return true;
    }
        
    public static boolean updateFournisseur(Fournisseur fournisseur, String codeFournisseur){
        String sql = "UPDATE Fournisseurs SET code_fournisseur=?, fournisseur=?,"
            + "adresse=?, num_telephone=?, num_reg_commerce=?, code_fiscale=?,"
            + "article_positif=? WHERE code_fournisseur=?";       
        try {
            ps = CONNECTION.prepareStatement(sql);
            ps.setString(1, fournisseur.getCodeFournisseur());
            ps.setString(2, fournisseur.getFournisseur());
            ps.setString(3, fournisseur.getAdresse());
            ps.setString(4, fournisseur.getNumTelephone());
            ps.setString(5, fournisseur.getNumRegCommerce());
            ps.setString(6, fournisseur.getCodeFiscale());
            ps.setString(7, fournisseur.getArticlePositif());
            ps.setString(8, codeFournisseur);
//            System.out.println(fournisseur.getCodeFournisseur());
//            System.out.println(fournisseur.getFournisseur());
//            System.out.println(fournisseur.getAdresse());
//            System.out.println(fournisseur.getNumTelephone());
//            System.out.println(fournisseur.getNumRegCommerce());
//            System.out.println(fournisseur.getCodeFiscale());
//            System.out.println(fournisseur.getArticlePositif());
     
            int row = ps.executeUpdate();

            if (row > 0) {
                System.out.println("Fournisseur avec code : "+ fournisseur.getCodeFournisseur()+" Updated ");
                return true;
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }finally{
            try { ps.close(); } catch (Exception e) { /* Ignored */ }
        }      
        return false;
    }
    
    public static boolean isFournisseurExist(String codeFournisseur) {
    	String sql = "SELECT code_fournisseur FROM Fournisseurs WHERE code_fournisseur = ?";
    	try {
    		ps = CONNECTION.prepareStatement(sql);
    		ps.setString(1, codeFournisseur);
    		rs = ps.executeQuery();
    		
    		if(rs.next()) {
                    return true;
    		}
    	}catch(SQLException e) {
    		e.printStackTrace();
    	}finally {
    		try { ps.close(); } catch (Exception e) { /* Ignored */ }
                try { rs.close(); } catch (Exception e) { /* Ignored */ }
        }
    	return false;
    }
    
    public static boolean deleteFournisseur(String codeFournisseur){
        String sql = "DELETE FROM Fournisseurs WHERE code_fournisseur = ?";

        try {
            ps = CONNECTION.prepareStatement(sql);
            ps.setString(1, codeFournisseur);
            int row = ps.executeUpdate();

            if(row>0)
                return true;

        } catch (SQLException ex) {
            Logger.getLogger(DBQueries.class.getName()).log(Level.SEVERE, null, ex);
        }finally{
            try { ps.close(); } catch (Exception e) { /* Ignored */ }
        }          
        return false;
    }   
    
    //CHEQUE CRUD METHODS 
    public static ObservableList<Cheque> getListCheques(TableView<Cheque> tbv) throws SQLException{
        ObservableList<Cheque> listCheque = FXCollections.observableArrayList();
        rs = CONNECTION.createStatement().executeQuery(SQL_TABLE_CHEQUES);
        try{
            while(rs.next()) {
                listCheque.add(new Cheque(
                                rs.getString(1), rs.getString(2),
                                rs.getString(3), rs.getDouble(4), rs.getDouble(5)));
            }
            tbv.setItems(listCheque);
        }catch(SQLException e){
        }
        finally{
            try { rs.close(); } catch (Exception e) { /* Ignored */ }
        }
        return listCheque;
    }
    
    public static boolean insertCheque(Cheque cheque){
        if(isChequeExist(cheque.getNumCheque())){
            Utilities.showAlert("ATTENTION", "CHEQUE EST DEJA EXISTE");
            return false;
        }else{
            String sql = "INSERT INTO Cheques (fournisseur, num_cheque, date_cheque,"
                    + "montant_cheque, montant_cheque_primaire) VALUES (?, ?, ?, ?, ?)";
            
//            String sql2 = "INSERT INTO Affectations (fournisseur, num_cheque, date_cheque, montant_cheque)"
//                    + " VALUES (?, ?, ?, ?)";
            try{
                ps = CONNECTION.prepareStatement(sql);
                ps.setString(1, cheque.getFournisseur());
                ps.setString(2, cheque.getNumCheque());
                ps.setString(3, cheque.getDateCheque());
                ps.setDouble(4, cheque.getMontantCheque());
                ps.setDouble(5, cheque.getMontantChequePrimaire());
                ps.execute();
                
//                ps = CONNECTION.prepareStatement(sql2);
//                ps.setString(1, cheque.getFournisseur());
//                ps.setString(2, cheque.getNumCheque());
//                ps.setString(3, cheque.getDateCheque());
//                ps.setDouble(4, cheque.getMontantCheque());
//                ps.execute();
            }catch(SQLException e){
                e.printStackTrace();
            }finally{
                try{ps.close();}catch(Exception e){}
            }
        }
        return true;
    }
    
    public static boolean updateCheque(Cheque cheque, String NumCheque){
        if(cheque == null)
            Utilities.showAlert("MISE A JOUR", "IF FAUT SELECTIONNER UNE LIGNE D'ABORD");
        else{
            String sql = "UPDATE Cheques SET fournisseur=?, num_cheque=?,"
                    + "date_cheque=?, montant_cheque=?, montant_cheque_primaire=?"
                    + " WHERE num_cheque=?";
            
            try {
                             
                ps = CONNECTION.prepareStatement(sql);
                ps.setString(1, cheque.getFournisseur());
                ps.setString(2, cheque.getNumCheque());
                ps.setString(3, cheque.getDateCheque());
                ps.setDouble(4, cheque.getMontantCheque());
                ps.setDouble(5, cheque.getMontantChequePrimaire());
                ps.setString(6, NumCheque);
                int row = ps.executeUpdate();

                if (row > 0) {
                    return true;
                }
                               
            } catch (SQLException ex) {
                Logger.getLogger(DBQueries.class.getName()).log(Level.SEVERE, null, ex);
            }finally{
                try { ps.close(); } catch (Exception e) { /* Ignored */ }
            }
        }
        return false;
    }

    public static boolean updateChequeAffectation(Cheque cheque, String NumCheque){
        if(cheque == null)
            Utilities.showAlert("MISE A JOUR", "IF FAUT SELECTIONNER UNE LIGNE D'ABORD");
        else{
            String sql = "UPDATE Affectations SET fournisseur=?, num_cheque=?,"
                    + "date_cheque=?, montant_cheque=?"
                    + " WHERE num_cheque=?"; 
            
            try {            
                ps = CONNECTION.prepareStatement(sql);
                ps.setString(1, cheque.getFournisseur());
                ps.setString(2, cheque.getNumCheque());
                ps.setString(3, cheque.getDateCheque());
                ps.setDouble(4, cheque.getMontantCheque());
                ps.setString(5, NumCheque);
                int row = ps.executeUpdate();

                if (row > 0) {
                    return true;
                }
                                           
            } catch (SQLException ex) {
                Logger.getLogger(DBQueries.class.getName()).log(Level.SEVERE, null, ex);
            }finally{
                try { ps.close(); } catch (Exception e) { /* Ignored */ }
            }
        }
        return false;
    }
    
    public static boolean isChequeExist(String numCheque){
        String sql = "SELECT * FROM Cheques WHERE num_cheque=?";
        try{
            ps = CONNECTION.prepareStatement(sql);
            ps.setString(1, numCheque);
            rs = ps.executeQuery();
            
            if(rs.next())
                return true;
            
        }catch(SQLException e){
            e.printStackTrace();
        }finally{
            try{ps.close();}catch(SQLException e){}
            try{rs.close();}catch(SQLException e){}
        }
        return false;
    }
    
    public static boolean deleteCheque(String numCheque){
        String sql = "DELETE FROM Cheques WHERE num_cheque=?";
        try{
            ps = CONNECTION.prepareStatement(sql);
            ps.setString(1, numCheque);
            int row = ps.executeUpdate();

            if(row>0){
                Utilities.showAlert("SUPPRISSION", "LE CHEQUE NUMERO : " + numCheque + " A ETE SUPPRIMER");
                return true;
            }
        }catch(SQLException e){}
        finally{
            try{ps.close();}catch(SQLException e){}
        }
        return false;
    }
    
    //FACTURE CRUD METHODS
    public static ObservableList<Facture> getListFactures(TableView<Facture> tbv) throws SQLException{
        ObservableList<Facture> listFactures = FXCollections.observableArrayList();
        rs = CONNECTION.createStatement().executeQuery(SQL_TABLE_FACTURES);
        try{
            while(rs.next()) {   
                listFactures.add(new Facture(
                        rs.getString(1), rs.getString(2),
                        rs.getString(3), rs.getDouble(4), rs.getDouble(5),
                        rs.getString(6), rs.getString(7)));
            }
            tbv.setItems(listFactures);
        }catch(SQLException e){
        }
        finally{
            try { rs.close(); } catch (Exception e) { /* Ignored */ }
        }
        return listFactures;
    }

    public static boolean insertFacture(Facture facture){
        if(isFactureExist(facture.getNumFacture())){
            Utilities.showAlert("ATTENTION", "CHEQUE EST DEJA EXISTE");
            return false;
        }
        else{
            String sql = "INSERT INTO Factures (fournisseur, num_facture, date_facture,"
                    + "montant_facture, montant_facture_primaire, bc, br) VALUES (?, ?, ?, ?, ?, ?, ?)";
            try{
                ps = CONNECTION.prepareStatement(sql);
                ps.setString(1, facture.getFournisseur());
                ps.setString(2, facture.getNumFacture());
                ps.setString(3, facture.getDateFacture());
                ps.setDouble(4, facture.getMontantFacture());
                ps.setDouble(5, facture.getMontantFacturePrimaire());
                ps.setString(6, facture.getBonCommande());
                ps.setString(7, facture.getBonReception());
                ps.execute();
            }catch(SQLException e){
                e.printStackTrace();
            }finally{
                try{ps.close();}catch(Exception e){}
            }
        }
        return true;
    }
    
    public static boolean updateFacture(Facture facture, String NumFacture){
        String sql = "UPDATE Factures SET fournisseur=?,"
                + "num_facture=?, date_facture=?, montant_facture=?, montant_facture_primaire=?, bc=?, br=?"
                + " WHERE num_facture=?";       
        try {
            ps = CONNECTION.prepareStatement(sql);
            ps.setString(1, facture.getFournisseur());
            ps.setString(2, facture.getNumFacture());
            ps.setString(3, facture.getDateFacture());
            ps.setDouble(4, facture.getMontantFacture());
            ps.setDouble(5, facture.getMontantFacturePrimaire());
            ps.setString(6, facture.getBonCommande());
            ps.setString(7, facture.getBonReception());
            ps.setString(8, NumFacture);

            int row = ps.executeUpdate();

            if (row > 0) {
                return true;
            }
        } catch (SQLException ex) {
            Logger.getLogger(DBQueries.class.getName()).log(Level.SEVERE, null, ex);
        }finally{
            try { ps.close(); } catch (Exception e) { /* Ignored */ }
        }
        return false;
    }
    
    public static boolean isFactureExist(String numFacture){
        String sql = "SELECT * FROM Factures WHERE num_facture=?";
        try{
            ps = CONNECTION.prepareStatement(sql);
            ps.setString(1, numFacture);
            rs = ps.executeQuery();
            
            if(rs.next()){
                return true;            
            }
     
        }catch(SQLException e){
            e.printStackTrace();
        }finally{
            try{ps.close(); rs.close();}catch(SQLException e){}
        }
        return false;
    }
    
    public static boolean deleteFacture(String numFacture){
        String sql = "DELETE FROM Factures WHERE num_facture=?";
        try{
            ps = CONNECTION.prepareStatement(sql);
            ps.setString(1, numFacture);
            int row = ps.executeUpdate();

            if(row>0){
                Utilities.showAlert("SUPPRISSION", "LA FACTURE NUMERO : " +numFacture+ " A ETE SUPPRIMER");
                return true;
            }
        }catch(SQLException e){}
        finally{
            try{ps.close();}catch(SQLException e){}
        }
        return false;
    }
        
    public static Facture getFacture(String numFacture){
        String sql = "SELECT * FROM Factures WHERE num_facture=?";
        Facture facture = null;
        try{
            ps = CONNECTION.prepareStatement(sql);
            ps.setString(1, numFacture);
            rs = ps.executeQuery();
            if(rs.next())
                facture = new Facture(rs.getString(2), rs.getString(3), rs.getDouble(4), rs.getDouble(5), rs.getString(6), rs.getString(7));
        }catch(SQLException e){
            e.printStackTrace();
        }finally{
            try{rs.close();} catch(SQLException e){e.printStackTrace();}
        }
        return facture;
    }
    
    public static Cheque getCheque(String numCheque){
        String sql = "SELECT * FROM Cheques WHERE num_cheque=?";
        Cheque cheque = null;
        try{
            ps = CONNECTION.prepareStatement(sql);
            ps.setString(1, numCheque);
            rs = ps.executeQuery();
            if(rs.next())
                cheque = new Cheque(rs.getString(1), rs.getString(2), rs.getString(3), rs.getDouble(4), rs.getDouble(5));
        }catch(SQLException e){
            e.printStackTrace();
        }finally{
            try{rs.close();} catch(SQLException e){e.printStackTrace();}
        }
        return cheque;
    }
    
    public static boolean updateMontantCheque(String numCheque, double montantCheque){
        String sql = "UPDATE Cheques SET montant_cheque=?"
                + " WHERE num_cheque=?";       
        try {
            ps = CONNECTION.prepareStatement(sql);
            ps.setDouble(1, montantCheque);
            ps.setString(2, numCheque);
            
            int row = ps.executeUpdate();

            if (row > 0) {
                return true;
            }
        } catch (SQLException ex) {
            Logger.getLogger(DBQueries.class.getName()).log(Level.SEVERE, null, ex);
        }finally{
            try { ps.close(); } catch (Exception e) { /* Ignored */ }
        }
        return false;
    }
    
    public static boolean updateMontantFacture(String numFacture, double montantFacture){
        String sql = "UPDATE Factures SET montant_facture=?"
                + " WHERE num_facture=?";       
        try {
            ps = CONNECTION.prepareStatement(sql);
            ps.setDouble(1, montantFacture);
            ps.setString(2, numFacture);
            
            int row = ps.executeUpdate();

            if (row > 0) {
                return true;
            }
        } catch (SQLException ex) {
            Logger.getLogger(DBQueries.class.getName()).log(Level.SEVERE, null, ex);
        }finally{
            try { ps.close(); } catch (Exception e) { /* Ignored */ }
        }
        return false;
    }
    
    //GET LIST OF CHEQUE FOR SPECIFIC FORNISSUER
//    public static ArrayList<String> getListChequeForSpecificFournisseur(String fournisseurName){
//        
//    }
}
