package htpEngine;

import java.io.*;
import java.util.Arrays;

public class HTPSerializerImpl implements HTPSerializer {
    @Override
    public void serialize(GET_PDU pdu, OutputStream os) throws IOException {
        DataOutputStream dos = new DataOutputStream(os);
        dos.writeInt(1);
        dos.writeUTF(pdu.getFileName());
    }

    @Override
    public void serialize(PUT_PDU pdu, OutputStream os) throws IOException {
        DataOutputStream dos = new DataOutputStream(os);
        dos.writeInt(2);
        dos.writeUTF(pdu.getFileName());
        dos.writeInt(pdu.getAmountBytes());
        dos.write(pdu.getBytes());
    }

    @Override
    public void serialize(ERROR_PDU pdu, OutputStream os) throws IOException {
        DataOutputStream dos = new DataOutputStream(os);
        dos.writeInt(3);
        dos.writeUTF(pdu.getFileName());
        dos.writeByte(pdu.getErrorCode());
        dos.writeUTF(pdu.getErrorMessage());
    }

    @Override
    public PDU parsePDU(InputStream is) throws IOException {
        DataInputStream dis = new DataInputStream(is);
        int type = dis.readInt();
        String fileName = dis.readUTF();

        if(type == 1) {
            return new GET_PDUImpl(fileName);
        }
        else if(type == 2) {
            int amountBytes = dis.readInt();
            byte[] bytes = dis.readNBytes(amountBytes);
            return new PUT_PDUImpl(fileName, amountBytes, bytes);
        }
        else {
            byte errorCode = dis.readByte();
            String errorMessage = dis.readUTF();
            return new ERROR_PDUImpl(fileName, errorCode, errorMessage);
        }
    }
}
