package encrpt;

import encrpt.encode.Base64;
import encrpt.encode.DES;
import encrpt.encode.MD5;
import encrpt.encode.RSA;

import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by hzmawenjun on 2015/11/4.
 */
public class Test {
   private static final String test = "test";

   public static void main(String[] args) {
       Pattern pattern = Pattern.compile("@(\\w)+((\\.\\w{2,3}){1,3})$/");
       System.out.println(pattern.matcher("@163.com").find());
       //MD5
       MD5 md5 = new MD5();
       System.out.println("md5:"+md5.encode(test));


       //BASE64
       Base64 base64 = new Base64();

       //DES
       DES des = new DES();
       String desKey = "VKkGTWwLCMo=";
       byte[] encrptResult = des.encrpt(test.getBytes(),desKey);
       String encodeResult = base64.encode(encrptResult);
       System.out.println("des encrpt:"+encodeResult);

       System.out.println(new String(des.decrpt(base64.decode(encodeResult),desKey)));

       //RSA
       RSA rsa = new RSA();
       Map<String,Object> keyMap = rsa.initKey();
       System.out.println("public:"+RSA.getPublicKey(keyMap));
       System.out.println("private:"+RSA.getPrivatekey(keyMap));

       try {
           String rsaEncrpted = base64.encode(RSA.encryptByPrivateKey(test.getBytes(), RSA.getPrivatekey(keyMap)));
           System.out.println("encode:"+rsaEncrpted);
           System.out.println("decode:" + new String(RSA.decryptByPublicKey(base64.decode(rsaEncrpted),RSA.getPublicKey(keyMap))));



       } catch (Exception e) {
           e.printStackTrace();
       }
   }
}

