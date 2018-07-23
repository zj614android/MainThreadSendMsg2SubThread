package com.haha.mmp;

import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;


public class MainActivity extends AppCompatActivity {

    private String MyTag = "MyTag";
    private int num = 0;

    private TextView tvObj;
    private Button btnObj;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        tvObj = (TextView) findViewById(R.id.tvid);
        btnObj = (Button) findViewById(R.id.btnid);

        final LooperThread looperThread = new LooperThread();

        looperThread.start();

        btnObj.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Message message = Message.obtain();
                message.arg1 = num;
                tvObj.setText("主线程发送了 ："+String.valueOf(message.arg1));
                looperThread.handler.sendMessage(message);
                num++;
            }
        });


    }

    class LooperThread extends Thread {

        public Handler handler;

        @Override
        public void run() {
            super.run();

            Looper.prepare();

            handler = new Handler() {
                @Override
                public void handleMessage(Message msg) {
                    super.handleMessage(msg);

                    Toast.makeText(MainActivity.this,"LooperThread handler 收到消息 ："+msg.arg1,Toast.LENGTH_LONG).show();
                    Log.i(MyTag, "LooperThread handler 收到消息 ：" + msg.arg1);
                }
            };

            Looper.loop();
        }
    }

}
