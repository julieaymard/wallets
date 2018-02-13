package io.pax.cryptos.dao;


import io.pax.cryptos.domain.jdbc.SimpleWallet;
import io.pax.cryptos.domain.Wallet;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by AELION on 06/02/2018.
 */
public class WalletDao {

    JdbcConnector connector = new JdbcConnector();



    public List<Wallet> listWallets() throws SQLException {

        List<Wallet> wallets = new ArrayList<>();
        Connection conn = this.connector.getConnection();
        Statement stmt = conn.createStatement();
        ResultSet rs = stmt.executeQuery("SELECT * FROM wallet");

        while (rs.next()) {
            String name = rs.getString("name");
            int id = rs.getInt("id");
            wallets.add(new SimpleWallet(id, name));

        }
        rs.close();
        stmt.close();
        conn.close();

        return wallets;
    }

    public int createWallet(int userId, String name) throws SQLException {
        // most important stuff : never ever String concatenation in JDBC
        String query = "INSERT INTO wallet(name, user_id) VALUES(?,?)";
        //query= "INSERT INTO wallet (name, user_id) VALUES (`test`,2)";

        System.out.println(query);

        Connection conn = this.connector.getConnection();
        PreparedStatement stmt = conn.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
        stmt.setString(1, name);
        stmt.setInt(2, userId);

        stmt.executeUpdate();


        ResultSet keys = stmt.getGeneratedKeys();
        keys.next();
        int id = keys.getInt(1);
        stmt.close();
        conn.close();

        return id;

    }


    public void deleteWallet(int walletId) throws SQLException {
        String query = "DELETE FROM wallet WHERE id=?";
        System.out.println(query);

        Connection conn = this.connector.getConnection();
        PreparedStatement stmt = conn.prepareStatement(query);
        stmt.setInt(1, walletId);
        stmt.executeUpdate();

        stmt.close();
        conn.close();
    }


    public void deleteByName(String name) throws SQLException {
        String query = "DELETE * FROM wallet WHERE name = ?";
        System.out.println(query);
        Connection conn = this.connector.getConnection();
        PreparedStatement stmt = conn.prepareStatement(query);
        stmt.setString(1, name);
        stmt.executeUpdate();
        stmt.close();
        conn.close();
    }

    public List<Wallet> findByName(String extract) throws SQLException {
        String query = "SELECT * FROM wallet WHERE name LIKE ?";
        Connection conn = this.connector.getConnection();
        PreparedStatement stmt = conn.prepareStatement(query);
        stmt.setString(1, extract +"%");
        ResultSet rs = stmt.executeQuery();

        List<Wallet> wallets = new ArrayList<>();
        while (rs.next()) {
            wallets.add(new SimpleWallet(rs.getInt("id"), rs.getString("name")));
            System.out.println(rs.getString("name"));
        }
        stmt.close();

        return wallets;
    }


    public void updateWallet (int walletId, String newName) throws SQLException {
        String query = "UPDATE  wallet SET name = ? WHERE id = ?";
        System.out.println(query);
        Connection conn = this.connector.getConnection();
        PreparedStatement stmt = conn.prepareStatement(query);
        stmt.setString(1, newName);
        stmt.setInt(2, walletId);
        stmt.executeUpdate();
        stmt.close();
        conn.close();
    }

    public void deleteAll(int userId) throws SQLException {
        String query = "DELETE  FROM wallet WHERE user_id = ?";
        System.out.println(query);
        Connection conn = this.connector.getConnection();
        PreparedStatement stmt = conn.prepareStatement(query);
        stmt.setInt(1, userId);
        stmt.executeUpdate();
        stmt.close();
        conn.close();
    }


    /**
     * delete all wallet from a user
     *
     * @param args
     * @throws SQLException
     */
//    public void deleteAll(int userId){
//
//    }
    public static void main(String[] args) throws SQLException {
        WalletDao dao = new WalletDao();
        //int id = dao.createWallet(2, "test2");

        // dao.deleteWallet(id);

//        List<Wallet> wallets = new ArrayList<Wallet>(); // déclaration liste
//        wallets = dao.findByName("Y");// trouve wallets commencant par b
//        System.out.println(wallets.size()); // console : vérifie taille wde wallets

       // dao.deleteByName("test");

       // dao.updateWallet(10, "Julie");

        dao.deleteAll(1);
    }


}
