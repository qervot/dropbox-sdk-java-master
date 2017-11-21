package common;

public class CommandMessage extends AbstractMessage{
    private int cmd;
    private Object[] attachment;

    public static final int AUTH_OK = 45023847;


    public CommandMessage(int cmd, Object... attachment) {
        this.cmd = cmd;
        this.attachment = attachment;
    }
}
