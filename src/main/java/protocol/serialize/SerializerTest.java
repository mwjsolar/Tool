package protocol.serialize;

import java.io.ByteArrayOutputStream;
import java.io.ObjectOutputStream;

/**
 * Created by hzmawenjun on 2016/4/17.
 */
public class SerializerTest {

    public static void  main(String[] args) {
        ByteArrayOutputStream hessianBaos = new ByteArrayOutputStream();

        ByteArrayOutputStream objectBaos = new ByteArrayOutputStream();
        try {
            TestObject testObject = new TestObject();
            testObject.setTestA(2);
            HessianSerializer serializer = new HessianSerializer();
            serializer.serialize(testObject,hessianBaos);

            JdkSerializer jdkSerializer = new JdkSerializer();
            jdkSerializer.serialize(testObject,objectBaos);

            System.out.println(hessianBaos);

            System.out.println(objectBaos);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
