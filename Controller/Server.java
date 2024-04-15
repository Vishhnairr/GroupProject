package Controller;

import java.io.*;
import java.net.*;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Server {
    private static String[] names = {"Wily", "Felix", "Carlsbad", "Hobob"};
    private static final int PORT = 9090;
    private static ConcurrentHashMap<String, ClientHandler> usersOnline = new ConcurrentHashMap<>();
    private static ArrayList<ClientHandler> clients = new ArrayList<>();
    private static ExecutorService pool = Executors.newFixedThreadPool(20);
    public static void main(String[] args) throws IOException {
        ServerSocket listener = new ServerSocket(PORT);
        while (true) {
            System.out.println("[SERVER] Waiting for client connection...");
            Socket client = listener.accept();
            System.out.println("[SERVER] Connected to client!");
            ClientHandler clientThread = new ClientHandler(client, clients, usersOnline);
            clients.add(clientThread);
            pool.execute(clientThread);
        }
    }

    public static String getRandomName() {
        Random random = new Random();
        return names[random.nextInt(4)];
    }
}