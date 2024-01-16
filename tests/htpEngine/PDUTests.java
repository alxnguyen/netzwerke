package htpEngine;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Arrays;

public class PDUTests {
    private static final String FILENAME = "test.txt";
    private static final HTPSerializer serializer = new HTPSerializerImpl();
    private static final int AMOUNT_BYTES = 20;
    private static final byte[] BYTES = "abc".getBytes();
    private static final byte ERROR_CODE = (byte) 0x01;
    private static final String ERROR_MESSAGE = "Error message";

    @Test
    public void getPduTest() throws IOException {
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        GET_PDU getPdu = new GET_PDUImpl(FILENAME);
        serializer.serialize(getPdu, bos);

        ByteArrayInputStream bis = new ByteArrayInputStream(bos.toByteArray());
        GET_PDU pdu = (GET_PDU) serializer.parsePDU(bis);
        bos.close();
        bis.close();

        Assertions.assertEquals(FILENAME ,pdu.getFileName());
    }

    @Test
    public void putPduTest() throws IOException {
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        PUT_PDU putPdu = new PUT_PDUImpl(FILENAME, AMOUNT_BYTES, BYTES);
        serializer.serialize(putPdu, bos);

        ByteArrayInputStream bis = new ByteArrayInputStream(bos.toByteArray());
        PUT_PDU pdu = (PUT_PDU) serializer.parsePDU(bis);
        bos.close();
        bis.close();

        Assertions.assertEquals(FILENAME, pdu.getFileName());
        Assertions.assertEquals(AMOUNT_BYTES, pdu.getAmountBytes());
        Assertions.assertArrayEquals(BYTES, pdu.getBytes());
    }

    @Test
    public void errorPduTest() throws IOException {
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        ERROR_PDU errorPdu = new ERROR_PDUImpl(FILENAME, ERROR_CODE, ERROR_MESSAGE);
        serializer.serialize(errorPdu, bos);

        ByteArrayInputStream bis = new ByteArrayInputStream(bos.toByteArray());
        ERROR_PDU pdu = (ERROR_PDU) serializer.parsePDU(bis);
        bos.close();
        bis.close();

        Assertions.assertEquals(FILENAME, pdu.getFileName());
        Assertions.assertEquals(ERROR_CODE, pdu.getErrorCode());
        Assertions.assertEquals(ERROR_MESSAGE, pdu.getErrorMessage());
    }
}
