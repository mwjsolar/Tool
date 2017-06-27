package hystrix.example;

import rx.Observable;
import rx.Observer;
import rx.Subscriber;
import rx.functions.Action1;

/**
 * Created by mwjsolar on 16/9/26.
 */
public class ExampleTest {
    public static void main(String[] args) {
        //正常执行
        HelloWorldCommand command = new HelloWorldCommand("test");
        String result = command.execute();
        System.out.println(result);

        //事件监听
//        Observable<String> observable = new HelloWorldCommand("observable").toObservable();
//        observable.subscribe(new Action1<String>() {
//            @Override
//            public void call(String s) {
//                System.out.println("print execute result");
//                System.out.println("observable call");
//            }
//        });
//
//        observable.subscribe(new Observer<String>() {
//            @Override
//            public void onCompleted() {
//                System.out.println("subscribe completed");
//            }
//
//            @Override
//            public void onError(Throwable e) {
//                System.out.println("subscribe error");
//            }
//
//            @Override
//            public void onNext(String s) {
//                System.out.println("subscribe next");
//            }
//        });
    }
}
