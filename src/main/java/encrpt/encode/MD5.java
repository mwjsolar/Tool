package encrpt.encode;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * MD5
 * @author hzmawenjun .
 */
public class MD5 {
    private Logger logger = LoggerFactory.getLogger(MD5.class);

    public String encode(String encodeContent) {
        String encodeResult = "";
        try {
            MessageDigest messageDigest = MessageDigest.getInstance("MD5");
            messageDigest.update(encodeContent.getBytes());
            byte[] encodeBytes = messageDigest.digest();
            encodeResult = byteArrayToHex(encodeBytes);
        } catch (NoSuchAlgorithmException e) {
            logger.error("encode error",e);
        }
        return encodeResult;
    }


    private   String byteArrayToHex(byte[] bytes) {
        char[] hexReferChars = { '0', '1', '2', '3', '4', '5', '6', '7', '8',
                '9', 'A', 'B', 'C', 'D', 'E', 'F' };
        char[] hexChars = new char[bytes.length * 2];
        int index = 0;
        for (byte b : bytes) {
            hexChars[index++] = hexReferChars[b >>> 4 & 0xf];
            hexChars[index++] = hexReferChars[b & 0xf];
        }
        return new String(hexChars);
    }
}
