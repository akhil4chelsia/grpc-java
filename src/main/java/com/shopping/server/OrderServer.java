package com.shopping.server;

import com.shopping.service.OrderServiceImpl;
import com.shopping.service.UserServiceImpl;
import io.grpc.Server;
import io.grpc.ServerBuilder;

import java.io.IOException;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
import java.util.logging.Logger;

public class OrderServer {

    private static final Logger logger = Logger.getLogger(OrderServer.class.getName());
    private Server server;

    public void startServer() {
        try {
            server = ServerBuilder.forPort(50052).addService(new OrderServiceImpl()).build().start();
            logger.info("Order server started on port 50052");
            Runtime.getRuntime().addShutdownHook(new Thread(
                    () -> {
                        logger.info("Clean server shutdown in case JVM was shutdown.");
                        try{
                            OrderServer.this.stopServer();
                        }catch (Exception e){
                            logger.log(Level.SEVERE, "Server did not shutdown.", e);
                        }

                    }
            ));
        } catch (IOException e) {
            logger.log(Level.SEVERE, "Server did not start.", e);
        }
    }

    public void stopServer() {
        if (server != null) {
            try {
                server.shutdown().awaitTermination(30, TimeUnit.SECONDS);
            } catch (InterruptedException e) {
                logger.log(Level.SEVERE, "Server did not stop.", e);
            }
        }
    }

    public void blockUntilShutdown() {
        if (server != null) {
            try {
                server.awaitTermination();
            } catch (InterruptedException e) {
                logger.log(Level.SEVERE, "Exception while shutting down.", e);
            }
        }
    }

    public static void main(String[] args) {
        OrderServer server = new OrderServer();
        server.startServer();
        server.blockUntilShutdown();
    }
}
