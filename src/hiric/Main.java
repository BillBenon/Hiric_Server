package hiric;

import com.fasterxml.jackson.databind.JsonNode;
import hiric.dbconnection.DbConnectionVariables;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Iterator;
import java.util.Map;
import com.fasterxml.jackson.databind.ObjectMapper;

public class Main {
    public void startServer() throws Exception{
        String url = "jdbc:mysql://remotemysql.com:3306/ZKZ7qI2OW3?"+"autoReconnect=true&useSSL=false";
        String user = "ZKZ7qI2OW3";
        String password = "pWgWkTztns";

        DbConnectionVariables connectionVariables = new DbConnectionVariables(url, user, password, "3306", 1200L);
        connectionVariables.saveDbConnectionVariablesInFile();

    }

    public static void main(String[] args) throws Exception {
        ServerSocket server = null;

        try{
            server = new ServerSocket(8888);
            server.setReuseAddress(true);

            //running infinite loop to accept
            //incoming clients

            while (true){

                // socket object to receive incoming client requests
                Socket client = server.accept();

                // Displaying that new client is connected
                // to server
                System.out.println("New client connected "
                        + client.getInetAddress()
                        .getHostAddress());

                //Create new thread object
                ClientHandler clientSocket = new ClientHandler(client);

                //this thread will handler new client separately
                new Thread(clientSocket).start();
            }

        }catch (IOException e) {
            e.printStackTrace();
        }
        finally {
            if (server != null) {
                try {
                    server.close();
                }
                catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public static class ClientHandler implements Runnable{
        private final Socket socket;

        public ClientHandler(Socket socket){
            this.socket = socket;
        }

        public void run() {
            try{
                DataOutputStream out = new DataOutputStream(socket.getOutputStream());
                DataInputStream in = new DataInputStream(socket.getInputStream());

                String requestBody = "";

                while (!requestBody.equals("exit")){

                    requestBody = in.readUTF();

                    ObjectMapper objectMapper = new ObjectMapper();
                    JsonNode jsonNode = objectMapper.readTree(requestBody);


                    String url = jsonNode.get("url").asText();
                    String action = jsonNode.get("action").asText();

                    JsonNode userData = jsonNode.get("object");
                    Iterator<Map.Entry<String, JsonNode>> iterator = userData.fields();

                    String userId = iterator.next().toString();
                    String userName = iterator.next().toString();

                    System.out.println(url);


                    switch (url){
                        case "/users":
                            out.flush();
                            out.writeUTF("hey you!!");
                            out.flush();
                            break;
                        default:
                            System.out.println("something went wrong");
                    }
                }
            }catch (Exception e){
                e.printStackTrace();
                System.out.println("eee" +e.getMessage());
            }
        }
    }
}