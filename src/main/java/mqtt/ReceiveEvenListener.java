package mqtt;

import org.eclipse.paho.client.mqttv3.MqttMessage;

public interface ReceiveEvenListener {
    public void recvMsg(String topic, MqttMessage msg);
}
