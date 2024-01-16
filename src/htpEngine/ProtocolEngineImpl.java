package htpEngine;

import java.io.*;
import java.util.Arrays;

public class ProtocolEngineImpl implements ProtocolEngine {
    private final HTPSerializer serializer;
    private final OutputStream os;
    private final InputStream is;
    private String fileName;
    private final boolean asServer;

    public ProtocolEngineImpl(HTPSerializer serializer, InputStream is, OutputStream os) {
        this.serializer = serializer;
        this.is = is;
        this.os = os;
        asServer = true;
    }

    public ProtocolEngineImpl(HTPSerializer serializer, InputStream is, OutputStream os, String fileName) {
        this.serializer = serializer;
        this.is = is;
        this.os = os;
        this.fileName = fileName;
        asServer = false;
    }

    private void read() throws IOException {
        while(this.is.available() == 0) {

        }

        PDU pdu = this.serializer.parsePDU(this.is);
        if(pdu instanceof GET_PDU) {
            System.out.println("GET RECEIVED");
            putFile(((GET_PDU) pdu).getFileName());
        }

        if(pdu instanceof PUT_PDU) {
            String fileName = ((PUT_PDU) pdu).getFileName();
            int length = ((PUT_PDU) pdu).getAmountBytes();
            byte[] bytes = ((PUT_PDU) pdu).getBytes();

            System.out.println("PUT RECEIVED");

            File file = new File(fileName + " (copy)");
            OutputStream fos = new FileOutputStream(file);
            fos.write(bytes, 0, length);
            fos.close();
        }

        if(pdu instanceof ERROR_PDU)
        {
            System.out.println("ERROR RECEIVED");
        }
    }
    @Override
    public void getFile(String fileName) throws IOException {
        GET_PDU getPdu = new GET_PDUImpl(fileName);
        this.serializer.serialize(getPdu, os);
        System.out.println("GET SENT");
        read();
    }

    @Override
    public void putFile(String fileName) throws IOException {
        File file = new File(fileName);
        if(!file.exists()) {
            ERROR_PDU errorPdu = new ERROR_PDUImpl(fileName, (byte) 0x01, "Die angefragte Datei existierte nicht.");
            this.serializer.serialize(errorPdu, os);
            System.out.println("ERROR SENT");
        }
        else {
            InputStream fis = new FileInputStream(file);
            byte[] content = new byte[(int) file.length()];
            fis.read(content);
            fis.close();
            PUT_PDU putPdu = new PUT_PDUImpl(fileName, (int) file.length(), content);
            this.serializer.serialize(putPdu, os);
            System.out.println("PUT SENT");
            read();
        }
    }

    @Override
    public void run() {
        if(asServer) {
            try {
                read();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
        
        else {
            try {
                getFile(fileName);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }
}
