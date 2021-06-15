package com.shopping.db;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class UserDAO {

    private static final Logger logger = Logger.getLogger(UserDAO.class.getName());

    public User getDetails(String username){
        User user = new User();
        try {
            Connection conn = H2DatabaseConnection.getConnectionToDatabase();
            PreparedStatement ps = conn.prepareStatement("select * from user where username=?");
            ps.setString(1,username);
            final ResultSet resultSet = ps.executeQuery();
            if(resultSet.next()){
                user.setId(resultSet.getInt("id"));
                user.setAge(resultSet.getInt("age"));
                user.setUsername(resultSet.getString("username"));
                user.setGender(resultSet.getString("gender"));
                user.setName(resultSet.getString("name"));
            }
        }catch (SQLException e){
            logger.log(Level.SEVERE,"Could not execute query.",e);
        }
        return user;
    }
}
