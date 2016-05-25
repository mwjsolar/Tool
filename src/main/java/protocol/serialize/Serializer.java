package protocol.serialize;

import java.io.OutputStream;

/**
 * Created by hzmawenjun on 2016/4/17.
 */
public interface Serializer {
    void serialize(Object seriableObject,OutputStream out);
}
