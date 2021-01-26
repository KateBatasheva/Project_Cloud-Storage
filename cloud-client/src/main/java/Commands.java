public enum Commands {
    UPLOAD ((byte) 3), DOWNLOAD ((byte) 2), RENAME ((byte) 4), DELETE ((byte) 5);

    byte command;

    Commands(byte command) {
        this.command = command;
    }

    public byte getCommand() {
        return command;
    }
}
