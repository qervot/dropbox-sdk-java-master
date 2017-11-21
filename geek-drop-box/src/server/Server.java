package server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {
 private ServerSocket serverSocket;
 public Server() {
     try {
         SqlHandler.connnect();
         serverSocket = new ServerSocket(8189);
         System.out.println("Сервер запущен");
         while (true) {
             Socket client = serverSocket.accept();
             System.out.println("Клиент подключился");
             new ClientHandler(this,client);
             }
         } catch(IOException e){
             e.printStackTrace();
         }   finally{
             try {
                 serverSocket.close();
             } catch (IOException e) {
                 e.printStackTrace();
             }
              SqlHandler.disconnnect();
         }
     }
 }


