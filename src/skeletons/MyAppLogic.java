import networks.ConnectionCreatedListener;

import java.io.*;

public class MyAppLogic implements ConnectionCreatedListener {
    @Override
    public void connectionCreated(InputStream is, OutputStream os, boolean asServer, String otherPeerAddress) {
        System.out.println("new connection created");

        // proof that we are intelligent beings. Send first five prime numbers
        DataOutputStream dos = new DataOutputStream(os);
        try {
            for(int i=250; i<=260; i++) {
                dos.writeInt(i);
            }
//            os.write(2);
//            os.write(3);
//            os.write(5);
//            os.write(7);
//            os.write(11);
//
            int i = -1;
            DataInputStream dis = new DataInputStream(is);
            do {
                System.out.println("going to read");
                i = dis.readInt();
                System.out.println("read: " + i);
                if (i == -1) {
                    System.out.println("no more data in stream");
                }
                if (i != 2 && i != 3 && i != 5 && i != 7 && i != 11) {
                    System.out.println("there is no intelligente life on the other side :(");
                } else {
                    System.out.println("prime number, is there intelligent live on the other side?");
                }
            }while (i > 0);
        } catch (IOException e) {
            System.err.println("give up: " + e.getLocalizedMessage());
        }

    }
}