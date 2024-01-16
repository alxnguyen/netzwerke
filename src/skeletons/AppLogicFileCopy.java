import networks.ConnectionCreatedListener;

import java.io.*;

public class  AppLogicFileCopy implements ConnectionCreatedListener {
    private final String fileName;

    public AppLogicFileCopy(String fileName) {
        this.fileName = fileName;
    }

    void copy(OutputStream os, InputStream is) throws IOException {
        int value = 0;
//        while(value > -1) {
//            value = is.read();
//            if(value > -1) os.write(value);
//            else os.close();
//        }
        while(true) {
            value = is.read();
            os.write(value);
        }
    }

    @Override
    public void connectionCreated(InputStream is, OutputStream os, boolean asServer, String otherPeerAddress) {
        InputStream sourceIS = null;
        OutputStream targetOS = null;

        if(asServer) {
            // I am data sink
            // open file output stream - TODO
            OutputStream fos;
            try {
                fos = new FileOutputStream(fileName);
            } catch (FileNotFoundException e) {
                throw new RuntimeException(e);
            }
            // what is source IS? - TODO
            targetOS = fos;
            sourceIS = is;
        } else {
            // I am data source
            // open file input stream and write into output stream - TODO
            InputStream fis;
            try {
                fis = new FileInputStream(fileName);
            } catch (FileNotFoundException e) {
                throw new RuntimeException(e);
            }
            // what is targetOS? - TODO
            sourceIS = fis;
            targetOS = os;
        }

        try {
            this.copy(targetOS, sourceIS);
            sourceIS.close();
        } catch (IOException e) {
            System.err.println(e.getLocalizedMessage());
        }
    }
}