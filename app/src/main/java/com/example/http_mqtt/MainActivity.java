package com.example.http_mqtt;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{
    private MyMqtt myMqtt;
    private EditText mqttHostName;
    private EditText mqttUserName;
    private EditText mqttPassword;


    @Override
    protected void onDestroy() {
        myMqtt.closeConnect();
        super.onDestroy();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button connectButton = findViewById(R.id.connectButton);
        connectButton.setOnClickListener(this);
        Button disconnectButton = findViewById(R.id.disconnectButton);
        disconnectButton.setOnClickListener(this);
        Button topicButton = findViewById(R.id.topicButton);
        topicButton.setOnClickListener(this);
        Button userButton1 = findViewById(R.id.userButton1);
        userButton1.setOnClickListener(this);
        Button userButton2 = findViewById(R.id.userButton2);
        userButton2.setOnClickListener(this);
        Button userButton3 = findViewById(R.id.userButton3);
        userButton3.setOnClickListener(this);
        Button userButton4 = findViewById(R.id.userButton4);
        userButton4.setOnClickListener(this);
        Button userButton5 = findViewById(R.id.userButton5);
        userButton5.setOnClickListener(this);
        Button userButton6 = findViewById(R.id.userButton6);
        userButton6.setOnClickListener(this);
        Button userButton7 = findViewById(R.id.userButton7);
        userButton7.setOnClickListener(this);
        Button userButton8 = findViewById(R.id.userButton8);
        userButton8.setOnClickListener(this);
        Button userButton9 = findViewById(R.id.userButton9);
        userButton9.setOnClickListener(this);

        mqttHostName = findViewById(R.id.serverAddr);
        mqttUserName = findViewById(R.id.mqttUserName);
        mqttPassword = findViewById(R.id.mqttPassword);


//        myMqtt = new MyMqtt("AndroidTest1");
//        Toast.makeText(getApplicationContext(), "启动", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.connectButton:
                myMqtt = new MyMqtt("AndroidClient1", mqttHostName.getText().toString(), mqttUserName.getText().toString(), mqttPassword.getText().toString());
                if (-1 != myMqtt.connection(10, 20)) {
                    Toast.makeText(getApplicationContext(), "连接成功", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(getApplicationContext(), "连接失败", Toast.LENGTH_SHORT).show();
                }
                break;
            case R.id.disconnectButton:
                if (null != myMqtt) {
                    if (0 == myMqtt.closeConnect()) {
                        Toast.makeText(getApplicationContext(), "断开成功", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(getApplicationContext(), "断开失败", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(getApplicationContext(), "断开失败，空指针！", Toast.LENGTH_SHORT).show();
                }
                break;
            case R.id.topicButton:
                break;
            default:
                break;
        }
    }
}
