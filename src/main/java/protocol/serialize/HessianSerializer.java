package protocol.serialize;

import com.caucho.hessian.io.Hessian2Input;
import com.caucho.hessian.io.Hessian2Output;
import com.caucho.hessian.io.HessianFactory;
import com.caucho.hessian.io.HessianInput;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

/**
 * hessian Serializer
 *
 * Created by hzmawenjun on 2016/4/17.
 */
public class HessianSerializer implements Serializer {

    private static HessianFactory _hessianFactory = new HessianFactory();

    @Override
    public void serialize(Object serializableObject, OutputStream out) {
        try {
            Hessian2Output hessian2Output = _hessianFactory.createHessian2Output(out);
            hessian2Output.writeObject(serializableObject);
            hessian2Output.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public Object desrialSize(InputStream inputStream) {
        try {
            Hessian2Input hessian2Input = _hessianFactory.createHessian2Input(inputStream);
            Object result = hessian2Input.readObject();
            System.out.println(result);
            return result;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}
