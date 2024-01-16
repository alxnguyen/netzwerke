package htpEngine;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public interface HTPSerializer {
    void serialize(GET_PDU pdu, OutputStream os) throws IOException;
    void serialize(PUT_PDU pdu, OutputStream os) throws IOException;
    void serialize(ERROR_PDU pdu, OutputStream os) throws IOException;
    PDU parsePDU(InputStream is) throws IOException;
}
