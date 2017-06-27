package protocol.serialize;


import jdk.nashorn.internal.objects.annotations.Getter;
import jdk.nashorn.internal.objects.annotations.Setter;

import java.io.BufferedOutputStream;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.Serializable;
import java.nio.ByteBuffer;
import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * Created by mwjsolar on 16/11/15.
 */
public class SerializeExample {
    public static void main(String[] args) {
        Serializer serializer = new HessianSerializer();
        ByteArrayOutputStream bufferOutputStream = new ByteArrayOutputStream();
        SeriableObject seriableObject = new SeriableObject();
//        seriableObject.setDateTime(LocalDateTime.now());
//        seriableObject.setLocalDate(LocalDate.now());
        seriableObject.setTest("123");
        serializer.serialize(seriableObject,bufferOutputStream);
        ByteArrayInputStream inputStream=new ByteArrayInputStream(bufferOutputStream.toByteArray());

        Object result = serializer.desrialSize(inputStream);
        System.out.println(result);
    }


    public static class SeriableObject implements Serializable {
        private static final long serialVersionUID = 7247714666080613254L;
//        private LocalDateTime dateTime;
        private LocalDate localDate;
        private String test ;

        public String getTest() {
            return test;
        }

        public void setTest(String test) {
            this.test = test;
        }

//        public LocalDateTime getDateTime() {
//
//            return dateTime;
//        }
//
//        public void setDateTime(LocalDateTime dateTime) {
//            this.dateTime = dateTime;
//        }

//
//        public LocalDate getLocalDate() {
//            return localDate;
//        }
//
//        public void setLocalDate(LocalDate localDate) {
//            this.localDate = localDate;
//        }


        public LocalDate getLocalDate() {
            return localDate;
        }

        public void setLocalDate(LocalDate localDate) {
            this.localDate = localDate;
        }
    }
 }
