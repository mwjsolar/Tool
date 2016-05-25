package encrpt.encode;

import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

import java.io.IOException;

/**
 * @author hzmawenjun .
 */
public class Base64 {

    public String encode(byte[] encodeContent) {
        return new BASE64Encoder().encode(encodeContent);
    }

    public byte[] decode(String decodeContent) {
        try {
            return new BASE64Decoder().decodeBuffer(decodeContent);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}
