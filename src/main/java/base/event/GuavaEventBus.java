package base.event;

import com.google.common.eventbus.EventBus;
import com.google.common.eventbus.Subscribe;

/**
 * Created by mwjsolar on 16/11/20.
 */
public class GuavaEventBus {
    public static void main(String[] args) {
        EventBus eventBus = new EventBus();
        eventBus.register(new EventListener());
        eventBus.post(new TestEvent());
    }
}


class TestEvent {
    public TestEvent() {
    }
}

class EventListener {
    @Subscribe
    public void listenter(TestEvent testEvent) {
        System.out.println("test");
    }
}
