package com.example.http_mqtt;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{
    private MyMqtt myMqtt;


    @Override
    protected void onDestroy() {
        myMqtt.closeConnect();
        super.onDestroy();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Button btn1 = findViewById(R.id.btn1);
        btn1.setOnClickListener(this);
        Button btn2 = findViewById(R.id.btn2);
        btn2.setOnClickListener(this);
        Button btn3 = findViewById(R.id.btn3);
        btn3.setOnClickListener(this);
        myMqtt = new MyMqtt("AndroidTest1");
        Toast.makeText(getApplicationContext(), "启动", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn1:
                if (-1 == myMqtt.publishMessage("test", "AndroidButton1")) {
                    Toast.makeText(getApplicationContext(), "数据发送失败", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(getApplicationContext(), "数据发送成功", Toast.LENGTH_SHORT).show();
                }
                break;
            case R.id.btn2:
                if (-1 == myMqtt.publishMessage("test", "AndroidButton2")){
                    Toast.makeText(getApplicationContext(), "数据发送失败", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(getApplicationContext(), "数据发送成功", Toast.LENGTH_SHORT).show();
                }
                break;
            case R.id.btn3:
                if (-1 == myMqtt.publishMessage("test", "AndroidButton3")){
                    Toast.makeText(getApplicationContext(), "数据发送失败", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(getApplicationContext(), "数据发送成功", Toast.LENGTH_SHORT).show();
                }
                break;
            default:
                break;
        }
    }
}
