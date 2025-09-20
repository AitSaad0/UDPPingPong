package UDPPingPong;

import java.net.*;
import java.io.*;

public class Client implements Runnable {
    private final int size = 1024;
    private final int port = 900;
    private DatagramSocket ds;

    public Client(DatagramSocket ds) {
        this.ds = ds;
    }

    public void sendMessage() {
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(System.in))) {
            while (true) {
                System.out.print("Enter message (type 'quit' to exit): ");
                String message = reader.readLine();
                if ("quit".equalsIgnoreCase(message.trim()))
                    System.out.println("Quitting");
                    System.exit(1);
                byte[] buff = message.getBytes();
                DatagramPacket packet = new DatagramPacket(buff, buff.length, InetAddress.getLocalHost(), port);
                ds.send(packet);
            }
        } catch (IOException e) {
            System.out.println(e);
        }
    }

    public void receiveRes() {
        byte[] receivedBuff = new byte[size];
        DatagramPacket packet = new DatagramPacket(receivedBuff, receivedBuff.length);
        try {
            while (true) {
                ds.receive(packet);
                System.out.println();
                System.out.println("Server says: " + new String(packet.getData(), 0, packet.getLength()));
                System.out.print("Enter message (type 'quit' to exit): ");
            }
        } catch (IOException e) {
            System.out.println("Couldn't receive data: " + e);
        }
    }

    public void run() {

        new Thread(() -> {
            receiveRes();
        }).start();

        new Thread(() -> {
            sendMessage();
        }).start();
        ;

        // try (BufferedReader reader = new BufferedReader(new
        // InputStreamReader(System.in))) {
        // while (true) {
        // String message = reader.readLine();
        // message.toLowerCase();
        // switch (message.trim()) {
        // case "quit":
        // System.out.println("Quitting...");
        // return;
        // default:
        // sendMessage();
        // break;
        // }
        // }
        // } catch (IOException e) {

        // }
    }
}
