package htpEngine;

public class GET_PDUImpl extends PDU implements GET_PDU{
    private final String fileName;

    public GET_PDUImpl(String fileName) {
        this.fileName = fileName;
    }

    @Override
    public String getFileName() {
        return this.fileName;
    }
}
