/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package databases;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author RiPou
 */
public class ConnectionSingleton {
    private static Connection connection = null;
    private static ConnectionSingleton connectionSingleton;
    private static final String JDBC_PATH = "jdbc:sqlite:src/databases/factureDB.db";

    private ConnectionSingleton() {
        try
        {
            /** chargement des pilotes */
            Class.forName("org.sqlite.JDBC");
            System.out.println("Pilote charger");

            /** connexion a la database */
            connection = DriverManager.getConnection(JDBC_PATH); //connexion a la database
            System.out.println("Connexion etablie");

        } catch (ClassNotFoundException e)
        {
            System.err.println("Impossible de charger les Driver");
        } catch (SQLException ex) {
            Logger.getLogger(ConnectionSingleton.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public static Connection getConnection(){
        if(connectionSingleton == null){
            synchronized(ConnectionSingleton.class){
                if(connectionSingleton == null)
                    connectionSingleton = new ConnectionSingleton();
            }    
        }
        return connection;
    }
    
    public static boolean closeConnection(){
        if(connection != null){
            try {
                connection.close();
                System.out.println("Connection Closed");
            } catch (SQLException ex) {
                Logger.getLogger(ConnectionSingleton.class.getName()).log(Level.SEVERE, null, ex);
            }
            return true;
        }
        return false;
    }
    
}
