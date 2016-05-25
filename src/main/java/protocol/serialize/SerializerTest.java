package protocol.serialize;

import protocol.serialize.hessian.HessianSerializer;
import protocol.serialize.hessian.TestObject;

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


            ObjectOutputStream objectOutputStream = new ObjectOutputStream(objectBaos);
            objectOutputStream.writeObject(testObject);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
