package dev.lpa.clientserver.sever;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class MultiThreadedSimpleServer {

    public static void main(String[] args) {
        ExecutorService executor = Executors.newCachedThreadPool();
        try (ServerSocket serverSocket = new ServerSocket(5000)) {
            while (true) {
                Socket socket = serverSocket.accept();
                System.out.println("Server accepts client connection");
                socket.setSoTimeout(900_000);
                executor.submit(() -> handleClientRequest(socket));
            }

        } catch (IOException e) {
            System.out.println("Server application failed" + e.getMessage());
        }
    }

    private static void handleClientRequest(Socket socket) {
        try (socket;
             BufferedReader input = new BufferedReader(
                     new InputStreamReader(socket.getInputStream()));
             PrintWriter output =
                     new PrintWriter(socket.getOutputStream(), true);) {
            while (true) {
                String echoString = input.readLine();
                System.out.println("Request received: " + echoString);
                if (echoString.equals("exit")) {
                    break;
                }
                output.println("Echo message: " + echoString);
            }

        } catch (IOException e) {
            System.out.println("Client socket was shut down!!!");
        }


    }
}


