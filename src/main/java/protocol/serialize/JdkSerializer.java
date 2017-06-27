package protocol.serialize;

import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;

/**
 * Created by mwjsolar on 16/5/31.
 */
public class JdkSerializer implements Serializer {

    public void serialize(Object seriableObject, OutputStream out) {
        try {
            ObjectOutputStream oos = new ObjectOutputStream(out);
            oos.writeObject(seriableObject);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    @Override
    public Object desrialSize(InputStream inputStream) {
        return null;
    }
}
