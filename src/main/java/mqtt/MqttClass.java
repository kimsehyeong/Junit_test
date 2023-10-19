package mqtt;

import org.eclipse.paho.client.mqttv3.*;

import java.util.UUID;

public class MqttClass implements MqttCallback {

    private MqttClient client = null;

    public MqttClass() {
        new Thread(task1).start();
    }

    private ReceiveEvenListener listener = null;

    Runnable task1 = new Runnable() {
        @Override
        public void run() {
            try {
                String clientId = UUID.randomUUID().toString();
                client = new MqttClient("tcp://192.168.10.117:1883",clientId);
                MqttConnectOptions connopt = new MqttConnectOptions();
                connopt.setCleanSession(true);
                client.connect(connopt);
                client.setCallback(MqttClass.this);
                client.subscribe("dht11");

                new IoTFrame(MqttClass.this);

            } catch (MqttException e) {
                System.out.println("Error"+e.getStackTrace());
                e.printStackTrace();
            }
        }
    };

    public void sendMessage(String payload){
        MqttMessage message= new MqttMessage();
        message.setPayload(payload.getBytes());
        try {
            if (client.isConnected()){
                client.publish("red",message);
            }
        }  catch (MqttException e) {
            System.out.println("Error1"+ e.getStackTrace());
        }
    }

    @Override
    public void connectionLost(Throwable cause) {
            try {
                System.out.println("disconect");
                client.close();
            }catch (MqttException e){
                System.out.println("error"+ e.getMessage());
            }
    }

    @Override
    public void messageArrived(String topic, MqttMessage msg) throws Exception {
            listener.recvMsg(topic,msg);
    }

    @Override
    public void deliveryComplete(IMqttDeliveryToken token) {

    }

    public void setMyEventListner(ReceiveEvenListener listener) {
        this.listener = listener;
    }

}
