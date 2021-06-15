package com.shopping.clients;

import com.shopping.stubs.order.Order;
import com.shopping.stubs.order.OrderRequest;
import com.shopping.stubs.order.OrderResponse;
import com.shopping.stubs.order.OrderServiceGrpc;
import io.grpc.Channel;

import java.util.List;

public class OrderClient {

    private OrderServiceGrpc.OrderServiceBlockingStub stub;

    public OrderClient(Channel channel) {
        stub = OrderServiceGrpc.newBlockingStub(channel);
    }

    public List<Order> getOrdersForUser(int userId){
        OrderRequest request = OrderRequest.newBuilder().setUserid(userId).build();
        OrderResponse ordersForUser = stub.getOrdersForUser(request);
        return ordersForUser.getOrdersList();
    }
}
