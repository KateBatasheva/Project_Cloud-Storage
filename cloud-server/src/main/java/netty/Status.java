package netty;

public enum Status {
    CLIENT ((byte) 7), WAIT((byte) 1), COMMAND((byte) 2), NAMELEN ((byte) 3), NAME((byte) 4), FILELEN((byte) 5), FILE((byte) 6);

    byte status;

    Status(byte status) {
        this.status = status;
    }
}
