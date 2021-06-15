package com.shopping.service;

import com.google.protobuf.Timestamp;
import com.google.protobuf.util.Timestamps;
import com.shopping.db.Order;
import com.shopping.db.OrderDAO;
import com.shopping.stubs.order.OrderRequest;
import com.shopping.stubs.order.OrderResponse;
import com.shopping.stubs.order.OrderServiceGrpc;
import io.grpc.stub.StreamObserver;

import java.util.List;
import java.util.stream.Collectors;

public class OrderServiceImpl extends OrderServiceGrpc.OrderServiceImplBase {

    OrderDAO dao = new OrderDAO();

    @Override
    public void getOrdersForUser(OrderRequest request, StreamObserver<OrderResponse> responseObserver) {
        List<Order> orders = dao.getOrderForUser(request.getUserid());
        List<com.shopping.stubs.order.Order> orderForUsers = orders.stream()
                .map(order -> com.shopping.stubs.order.Order.newBuilder()
                        .setOrderId(order.getOrderId())
                        .setUserId(order.getUserId())
                        .setAmount(order.getAmount())
                        .setNoOfItems(order.getNoOfItems())
                        .setOrderDate(Timestamps.fromMillis(order.getOrderDate().getDate()))
                        .build()
                ).collect(Collectors.toList());
        OrderResponse response = OrderResponse.newBuilder().addAllOrders(orderForUsers).build();
        responseObserver.onNext(response);
        responseObserver.onCompleted();
    }
}
