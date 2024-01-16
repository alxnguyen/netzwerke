package htpEngine;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Arrays;

public class EngineTests {
    private static final HTPSerializer serializer = new HTPSerializerImpl();
    private static final String HOST = "localhost";
    private static final int PORT = 7777;
    private static final String FILENAME = "test.txt";
    private static final byte[] CONTENT = "ABC".getBytes();

    @Test
    public void completeTest() throws IOException {
        File file = new File(FILENAME);
        OutputStream fos = new FileOutputStream(file);
        fos.write(CONTENT);
        fos.close();

        ServerSocket servSock = new ServerSocket(PORT);
        Socket sock = new Socket(HOST, PORT);
        Socket clientSock = servSock.accept();

        ProtocolEngine clientProtocolEngine = new ProtocolEngineImpl(serializer, sock.getInputStream(), sock.getOutputStream(), FILENAME);
        ProtocolEngine serverProtocolEngine = new ProtocolEngineImpl(serializer, clientSock.getInputStream(), clientSock.getOutputStream());

        Thread servThread = new Thread(serverProtocolEngine);
        servThread.start();

        Thread clientThread = new Thread(clientProtocolEngine);
        clientThread.start();

        File newFile = new File(FILENAME);
        InputStream fis = new FileInputStream(newFile);
        String content = Arrays.toString(fis.readAllBytes());
        fis.close();

        Assertions.assertEquals(content, Arrays.toString(CONTENT));
    }
}
