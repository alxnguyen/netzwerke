package htpEngine;

import java.io.FileNotFoundException;
import java.io.IOException;

public interface ProtocolEngine extends Runnable {
    void getFile(String fileName) throws IOException;
    void putFile(String fileName) throws IOException;
}
