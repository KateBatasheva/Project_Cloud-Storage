package netty;

public enum Commands {
    UPLOAD ((byte) 30), DOWNLOAD ((byte) 20), RENAME ((byte) 40), DELETE ((byte) 50);

    byte command;

    Commands(byte command) {
        this.command = command;
    }

    public byte getCommand() {
        return command;
    }
}
