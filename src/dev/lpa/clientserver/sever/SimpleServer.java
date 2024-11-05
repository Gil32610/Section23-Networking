package dev.lpa.clientserver.sever;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

public class SimpleServer {

    public static void main(String[] args) {
        try(ServerSocket serverSocket = new ServerSocket(5000)){
           try(Socket socket = serverSocket.accept();){
               System.out.println("The server accepted the client connection!!!");
               BufferedReader input = new BufferedReader(
                       new InputStreamReader(socket.getInputStream()));
               PrintWriter output =
                       new PrintWriter(socket.getOutputStream(),true);
               while(true){
                   String echoString = input.readLine();
                   System.out.println("Request received: " + echoString);
                   if(echoString.equals("exit")){
                       break;
                   }
                   output.println("Echo message: " + echoString);
               }
           }
        } catch (IOException e) {
            System.out.println("Server application failed" + e.getMessage());
        }
    }
}


