/* 
 * I should creat the pool outside the loop to not recreat a pool in each loop 
 * don't make a shared buff to not overrite data 
 * the main thread should block for each receive and not only the pool threads
 */

package UDPPingPong;

import java.net.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.io.*;

public class Server implements Runnable {
    private final int size = 1024;
    private final int port = 900;
    private DatagramSocket ds;
    private volatile boolean running = true;

    public Server(DatagramSocket ds) {
        this.ds = ds;
    }

    public void run() {
        ExecutorService pool = Executors.newFixedThreadPool(5);
        while (running) {

            byte[] buff = new byte[size];
            DatagramPacket packet = new DatagramPacket(buff, buff.length);

            try {
                ds.receive(packet);
            } catch (IOException e) {
                if (!running)
                    break;
                System.out.println("Error receiving packet: " + e.getMessage());
                continue;
            }

            pool.execute(() -> {
                String message = new String(packet.getData(), 0, packet.getLength());
                System.out.println(message);

                byte[] reply = "waealaykomsalam".getBytes();
                DatagramPacket replyPacket = new DatagramPacket(reply, reply.length, packet.getAddress(),
                        packet.getPort());

                try {
                    ds.send(replyPacket);
                } catch (IOException e) {
                    System.out.println("Error sending response: " + e.getMessage());
                }

            });

        }

    }
}
