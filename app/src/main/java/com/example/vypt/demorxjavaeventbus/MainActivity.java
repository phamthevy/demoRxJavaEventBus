package com.example.vypt.demorxjavaeventbus;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

public class MainActivity extends AppCompatActivity {

    public static final String TAG = MainActivity.class.getSimpleName();
    TextView tv;
    Button bt;
    private CompositeDisposable disposable = new CompositeDisposable();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        tv = (TextView) findViewById(R.id.textView);
        bt = (Button) findViewById(R.id.button);

        disposable.add(((MyApp) getApplication())
                .bus()
                .toObservable()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<Object>() {
                    @Override
                    public void accept(Object object) throws Exception {
                        if (object instanceof Events.AutoEvent) {
                            tv.setText("Auto Event Received");
                        } else if (object instanceof Events.TapEvent) {
                            tv.setText("Tap Event Received");
                        }
                    }
                }));


        bt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((MyApp) getApplication())
                        .bus()
                        .send(new Events.TapEvent());
            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        disposable.clear();
    }


}
