package UDPPingPong;
import java.net.*;

class PingPong {
    public static void main(String args[]) {
        try {

            if (args.length == 0) {
                Thread client = new Thread(new Client(new DatagramSocket()));
                client.start();
            } else {
                Thread server = new Thread(new Server(new DatagramSocket(900, InetAddress.getLocalHost())));
                server.start();
            }
        } catch (SocketException e) {
            System.out.println(e);
        } catch (UnknownHostException e) {
            System.out.println(e);
        }
    }
}