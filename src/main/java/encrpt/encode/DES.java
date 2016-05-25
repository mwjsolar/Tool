package encrpt.encode;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESKeySpec;
import java.security.Key;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;

/**
 * DES
 * @author hzmawenjun .
 */
public class DES {

    private final Base64 base64 = new Base64();
    private final String ALGORITHM = "DES";

    public String initKey() {
        return initKey(null);
    }

    public String initKey(String seed) {
        SecureRandom secureRandom = null;

        if (seed != null) {
            secureRandom = new SecureRandom(base64.decode(seed));
        } else {
            secureRandom = new SecureRandom();
        }

        SecretKey secretKey = null;
        try {
            KeyGenerator keyGenerator = KeyGenerator.getInstance(ALGORITHM);
            keyGenerator.init(secureRandom);
            secretKey = keyGenerator.generateKey();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return base64.encode(secretKey.getEncoded());
    }

    protected Key toKey(String key) {
        try {
            DESKeySpec desKeySpec = new DESKeySpec(base64.decode(key));
            SecretKeyFactory factory = SecretKeyFactory.getInstance(ALGORITHM);
           SecretKey secretKey = factory.generateSecret(desKeySpec);
           return secretKey;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

   public  byte[] encrpt(byte[] data,String key) {
       Key key1 = toKey(key);
       Cipher cipher = null;
       try {
           cipher = Cipher.getInstance(ALGORITHM);
           cipher.init(Cipher.ENCRYPT_MODE,key1);
           return cipher.doFinal(data);

       } catch (Exception e) {
           e.printStackTrace();
       }
       return null;
   }


    public byte[] decrpt(byte[] data,String key) {
        Key key1 = toKey(key);
        Cipher cipher = null;
        try {
            cipher = Cipher.getInstance(ALGORITHM);
            cipher.init(Cipher.DECRYPT_MODE,key1);
            return cipher.doFinal(data);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
