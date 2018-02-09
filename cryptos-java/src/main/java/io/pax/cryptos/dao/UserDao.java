package io.pax.cryptos.dao;

import io.pax.cryptos.domain.SimpleUser;
import io.pax.cryptos.domain.User;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by AELION on 08/02/2018.
 */
public class UserDao {

    JdbcConnector connector = new JdbcConnector();

    public List<User> listUser() throws SQLException {

        List<User> users = new ArrayList<>();
        Connection conn = this.connector.getConnection();
        Statement stmt = conn.createStatement();
        ResultSet rs = stmt.executeQuery("SELECT * FROM user");

        while (rs.next()) {
            String name = rs.getString("name");
            int id = rs.getInt("id");
            users.add(new SimpleUser(id, name));

        }
        rs.close();
        stmt.close();
        conn.close();

        return users;
    }



    public int createUser(String name) throws SQLException {
        String query = "INSERT INTO user(name) VALUES (?);";

        System.out.println(query);

        Connection conn = this.connector.getConnection();
        PreparedStatement stmt = conn.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
        stmt.setString(1, name);

        stmt.executeUpdate();

        ResultSet keys = stmt.getGeneratedKeys();
        keys.next();
        int id = keys.getInt(1);
        stmt.close();
        conn.close();
            return id;
    }

    public void deleteUser(int userId) throws SQLException {
        String query = "DELETE FROM user WHERE id=?;";
        System.out.println(query);

        Connection conn = this.connector.getConnection();
        PreparedStatement stmt = conn.prepareStatement(query);
        stmt.setInt(1, userId);
        stmt.executeUpdate();

        stmt.close();
        conn.close();
    }

    public void deleteByName(String name) throws SQLException {
        String query = "DELETE FROM user WHERE name = ?";
        System.out.println(query);
        Connection conn = this.connector.getConnection();
        PreparedStatement stmt = conn.prepareStatement(query);
        stmt.setString(1, name);
        stmt.executeUpdate();
        stmt.close();
        conn.close();
    }



    public static void main(String[] args) throws SQLException {
        UserDao dao = new UserDao();
      dao.createUser("test");

        //dao.deleteUser(28);

        //dao.deleteByName("test2");
    }
}
