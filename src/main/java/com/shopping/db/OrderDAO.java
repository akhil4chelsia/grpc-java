package com.shopping.db;


import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class OrderDAO {

    private static final Logger logger = Logger.getLogger(OrderDAO.class.getName());

    public List<Order> getOrderForUser(int userid) {
        List<Order> orders = new ArrayList<>();
        try {
            Connection conn = H2DatabaseConnection.getConnectionToDatabase();
            PreparedStatement ps = conn.prepareStatement("select * from orders where user_id=?");
            ps.setInt(1, userid);
            final ResultSet rs = ps.executeQuery();
            while(rs.next()){
                Order order = new Order();
                try {
                    order.setOrderId(rs.getInt("order_id"));
                    order.setUserId(rs.getInt("user_id"));
                    order.setNoOfItems(rs.getInt("no_of_items"));
                    order.setAmount(rs.getDouble("total_amount"));
                    order.setOrderDate(rs.getDate("order_date"));
                } catch (SQLException e) {
                    logger.log(Level.SEVERE, "Could not collect query results.", e);
                }
                logger.info(String.valueOf(order));
                orders.add(order);
            }

        } catch (Exception e) {
            logger.log(Level.SEVERE,"Could not execute query.",e);
        }
        return orders;
    }

}
