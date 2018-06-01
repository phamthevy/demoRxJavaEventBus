package com.example.vypt.demorxjavaeventbus;

import android.app.Application;
import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import io.reactivex.functions.Consumer;

public class MyApp extends Application {

    private RxEventBus bus;

    @Override
    public void onCreate() {
        super.onCreate();
        bus = new RxEventBus();
    }

    public RxEventBus bus() {
        return bus;
    }

    public void sendAutoEvent() {
        Observable.timer(2, TimeUnit.SECONDS)
                .subscribe(new Consumer<Long>() {
                    @Override
                    public void accept(Long aLong) throws Exception {
                        bus.send(new Events.AutoEvent());
                    }
                });
    }
}
