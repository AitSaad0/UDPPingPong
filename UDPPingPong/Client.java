package UDPPingPong;

import java.net.*;
import java.io.*;

public class Client implements Runnable {
    private final int size = 1024;
    private byte[] buff = new byte[size];
    private final int port = 900;
    private DatagramSocket ds;

    public Client(DatagramSocket ds) {
        this.ds = ds;
    }

    public void sendMessage() {
        try {
            String message = "Ping";
            buff = message.getBytes();
            DatagramPacket packet = new DatagramPacket(buff, buff.length, InetAddress.getLocalHost(), 900);
            ds.send(packet);
            new Thread(()->{
                receiveRes();
            });
        } catch (UnknownHostException e) {
            System.out.println(e);
        } catch (IOException e) {
            System.out.println(e);
        }
    }

    public void receiveRes() {
        DatagramPacket packet = new DatagramPacket(buff, buff.length);
        try {
            ds.receive(packet);
        } catch (IOException e) {
            System.out.println("couldn' t receive data " + e);
        }
        System.out.println(new String(buff, 0, buff.length));
    }

    public void run() {
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(System.in))) {
            while (true) {
                String message = reader.readLine();
                message.toLowerCase();
                switch (message.trim()) {
                    case "quit":
                        System.out.println("Quitting...");
                        return;
                    default:
                        sendMessage();
                        break;
                }
            }
        } catch (IOException e) {

        }
    }
}
