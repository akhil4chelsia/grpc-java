package com.shopping.service;

import com.shopping.clients.OrderClient;
import com.shopping.db.OrderDAO;
import com.shopping.db.User;
import com.shopping.db.UserDAO;
import com.shopping.stubs.order.Order;
import com.shopping.stubs.user.*;
import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import io.grpc.stub.StreamObserver;

import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
import java.util.logging.Logger;

public class UserServiceImpl extends UserServiceGrpc.UserServiceImplBase {

    private static final Logger logger = Logger.getLogger(UserServiceImpl.class.getName());

    UserDAO dao = new UserDAO();

    @Override
    public void getUserDetails(UserRequest request, StreamObserver<UserResponse> responseObserver) {

        final User user = dao.getDetails(request.getUsername());

        //get user order details by gRPC call using OrderClient.java
        final List<Order> ordersForUser = getOrders(user);

        //Creating user response object
        UserResponse userResponse = UserResponse.newBuilder()
                .setAge(user.getAge())
                .setGender(Gender.valueOf(user.getGender()))
                .setId(user.getId())
                .setName(user.getName())
                .setUsername(user.getUsername())
                .setNoOfOrders(ordersForUser.size())
                .addAllOrders(ordersForUser)
                .build();

        responseObserver.onNext(userResponse);
        responseObserver.onCompleted();
    }

    private List<Order> getOrders(User user) {
        ManagedChannel channel = ManagedChannelBuilder.forTarget("localhost:50052")
                .usePlaintext()
                .build();
        OrderClient orderClient = new OrderClient(channel);
        final List<Order> ordersForUser = orderClient.getOrdersForUser(user.getId());
        try {
            channel.shutdown().awaitTermination(5, TimeUnit.SECONDS);
        } catch (InterruptedException e) {
            logger.log(Level.SEVERE, "Could not close order service channel", e);
        }
        return ordersForUser;
    }
}
