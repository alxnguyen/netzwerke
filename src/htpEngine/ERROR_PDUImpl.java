package htpEngine;

public class ERROR_PDUImpl extends PDU implements ERROR_PDU {
    private final String fileName;
    private final byte errorCode;
    private final String errorMessage;

    public ERROR_PDUImpl(String fileName, byte errorCode, String errorMessage) {
        this.fileName = fileName;
        this.errorCode = errorCode;
        this.errorMessage = errorMessage;
    }
    @Override
    public String getFileName() {
        return this.fileName;
    }

    @Override
    public byte getErrorCode() {
        return this.errorCode;
    }

    @Override
    public String getErrorMessage() {
        return this.errorMessage;
    }
}
