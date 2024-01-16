package htpEngine;

public class PUT_PDUImpl extends PDU implements PUT_PDU {
    private final String fileName;
    private final int amountBytes;
    private final byte[] bytes;

    public PUT_PDUImpl(String fileName, int amountBytes, byte[] bytes) {
        this.fileName = fileName;
        this.amountBytes = amountBytes;
        this.bytes = bytes;
    }
    @Override
    public String getFileName() {
        return this.fileName;
    }

    @Override
    public int getAmountBytes() {
        return this.amountBytes;
    }

    @Override
    public byte[] getBytes() {
        return this.bytes;
    }
}
