package htpEngine;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class Endpoint {
    private static boolean asServer = false;
    private static final int PORT = 7777;
    private static ServerSocket servSock;
    private static String fileName;
    private static ProtocolEngine clientProtocolEngine;
    private static ProtocolEngine servProtocolEngine;

    public static void main(String[] args) throws IOException {
        if(args.length == 0) {
            asServer = true;
            servSock = new ServerSocket(PORT);
            System.out.println("Server started on localhost:" + PORT);
        }

        else if(args.length == 2) {
            Socket sock = new Socket(args[0], PORT);
            fileName = args[1];
            clientProtocolEngine = new ProtocolEngineImpl(new HTPSerializerImpl(), sock.getInputStream(), sock.getOutputStream(), fileName);
        }

        while(asServer) {
            Socket clientSock = servSock.accept();
            servProtocolEngine = new ProtocolEngineImpl(new HTPSerializerImpl(), clientSock.getInputStream(), clientSock.getOutputStream());

            Thread servThread = new Thread(servProtocolEngine);
            servThread.start();
        }

        Thread clientThread = new Thread(clientProtocolEngine);
        clientThread.start();
    }
}
