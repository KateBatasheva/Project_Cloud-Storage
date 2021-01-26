package netty;

public class MyProtocol {

    private String fileName;
    private int fileNameLen;
    private byte [] fileNameBytes;
    private long dataSize;
    private byte [] dataBytes;
    private static final byte START = 31;
    private static final byte FINISH = 30;

    public String getFileName() {
        return fileName;
    }

    public int getFileNameLen() {
        return fileNameLen;
    }

    public byte[] getFileNameBytes() {
        return fileNameBytes;
    }

    public long getDataSize() {
        return dataSize;
    }

    public byte[] getDataBytes() {
        return dataBytes;
    }

    public void write (Package pack){

    }
}
