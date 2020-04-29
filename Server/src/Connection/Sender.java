package Connection;

import Common.CommandShell;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.nio.ByteBuffer;

public class Sender {

    private Contact contact;

    public Sender(Contact contact){
        contact = contact;
    }

    private byte buf[] = new byte[32*1024];

    public boolean send(CommandShell command){
        ByteArrayOutputStream bais = new ByteArrayOutputStream();
        ObjectOutputStream ois = null;
        try {
            ois = new ObjectOutputStream(bais);
            ois.writeObject(command);
            buf = bais.toByteArray();
            ByteBuffer bbf = ByteBuffer.wrap(buf);
            contact.getChannel().write(bbf);
            return true;
        } catch (IOException e) {
            return false;
        }

    }

}
