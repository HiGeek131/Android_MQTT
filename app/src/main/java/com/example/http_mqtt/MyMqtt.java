package com.example.http_mqtt;

import androidx.appcompat.app.AppCompatActivity;
import android.widget.Toast;

import org.eclipse.paho.client.mqttv3.*;
import org.eclipse.paho.client.mqttv3.persist.MemoryPersistence;

public class MyMqtt {
//    private String host = "tcp://39.104.26.227:1883";
//    private String userName = "test";
//    private String passWord = "test";
    private MqttClient mqttClient;
    private String mqttClientID;
    private MemoryPersistence memoryPersistence;
    private MqttConnectOptions mqttConnectOptions;
    public MyMqtt(String clientID, String host, String userName, String passWord) {
        this(clientID, host, userName, passWord,  null, true);
        mqttClientID = clientID;
    }

    public MyMqtt(String clientID, String host, String userName, String passWord, MqttCallback callback, boolean cleanSession) {
        try {
            mqttClient = new MqttClient("tcp://" + host + ":1883", clientID, memoryPersistence = new MemoryPersistence());
            mqttConnectOptions = new MqttConnectOptions();
            mqttConnectOptions.setCleanSession(cleanSession);
            mqttConnectOptions.setUserName(userName);
            mqttConnectOptions.setPassword(passWord.toCharArray());
            if (callback == null) {
                mqttClient.setCallback(new MqttCallback() {
                    @Override
                    public void connectionLost(Throwable throwable) {
                        System.out.println(mqttClientID + " connectionLost " + throwable);
                        reConnect();
                    }

                    @Override
                    public void messageArrived(String s, MqttMessage mqttMessage) throws Exception {
                        System.out.println(mqttClientID + " messageArrived: " + mqttMessage.toString());
                    }

                    @Override
                    public void deliveryComplete(IMqttDeliveryToken iMqttDeliveryToken) {
                        System.out.println(mqttClientID + " deliveryComplete " + iMqttDeliveryToken);
                    }
                });
            } else {
                mqttClient.setCallback(callback);
            }
//            mqttClient.connect(mqttConnectOptions);
        } catch (MqttException e) {
            e.printStackTrace();
        }
    }

    public int connection(int timeout, int aliveInterval) {
        mqttConnectOptions.setConnectionTimeout(timeout);
        mqttConnectOptions.setKeepAliveInterval(aliveInterval);
        try {
            mqttClient.connect(mqttConnectOptions);
        } catch (MqttException e) {
            e.printStackTrace();
            return -1;
        }
        return 0;
    }

    public int publishMessage(String topic, String msg) {
        try {
            MqttMessage message = new MqttMessage();
            message.setQos(1);
            message.setRetained(true);
            message.setPayload(msg.getBytes());
            MqttTopic mqttTopic = mqttClient.getTopic(topic);
            MqttDeliveryToken token = mqttTopic.publish(message);
            token.waitForCompletion();
        }
        catch (MqttException e) {
            e.printStackTrace();
            return -1;
        }
        return 0;
    }

    public int subTopic(String[] topicFilters, int[] qos) {
        try {
            mqttClient.subscribe(topicFilters, qos);
        } catch (MqttException e) {
            e.printStackTrace();
            return -1;
        }
        return 0;
    }

    public int cleanTopic(String[] topicFilters) {
        if (null != mqttClient && !mqttClient.isConnected()) {
            try {
                mqttClient.unsubscribe(topicFilters);
            } catch (MqttException e) {
                e.printStackTrace();
            }
        } else {
            System.out.println("mqttClient is error");
            return -1;
        }
        return 0;
    }

     int closeConnect() {
        if(null != memoryPersistence) {
            try {
                memoryPersistence.close();
            } catch (MqttPersistenceException e) {
                e.printStackTrace();
            }
        } else {
            System.out.println("memoryPersistence is null");
            return -3;
        }

        if(null != mqttClient) {
            if (mqttClient.isConnected()) {
                try {
                    mqttClient.disconnect();
                    mqttClient.close();
                } catch (MqttException e) {
                    e.printStackTrace();
                }
            } else {
                System.out.println("mqttClient is not connect");
                return -2;
            }
        } else {
            System.out.println("mqttClient is null");
            return -1;
        }
        return 0;
    }

    private void reConnect() {
        if (null != mqttClient) {
            if (!mqttClient.isConnected()) {
                if (null != mqttConnectOptions) {
                    try {
                        mqttClient.connect(mqttConnectOptions);
                    } catch (MqttException e) {
                        e.printStackTrace();
                    }
                } else {
                    System.out.println("mqttConnectOptions is null");
                }
            } else {
                System.out.println("mqttClient is connect");
            }
        } else {
            System.out.println("mqttClient is null");
        }
    }
}
