package mqtt;

import java.awt.BorderLayout;
import java.awt.ScrollPane;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;

import org.eclipse.paho.client.mqttv3.MqttMessage;
import org.json.JSONObject;

public class IoTFrame extends JFrame implements
        ActionListener, ReceiveEvenListener {
    private static final long serialVersionUID = 1L;
    private JTextField tmp = new JTextField(10);
    private JTextField hum = new JTextField(10);
    private JButton ledOn = new JButton("LED ON");
    private JButton ledOff = new JButton("LED OFF");
    private JLabel msg = new JLabel("온도/습도 모니터링");
    private JTextArea out = new JTextArea(20, 40);
    private JPanel panel = new JPanel();
    private JPanel panel1 = new JPanel();
    private JPanel panel2 = new JPanel();
    private ScrollPane sp = new ScrollPane();
    private MqttClass mqtt = null;

    public IoTFrame(MqttClass mqtt) {
        this();
        this.mqtt = mqtt;
        this.mqtt.setMyEventListner(this);
    }

    public IoTFrame() {
        super("MQTT 사물인터넷 통신 프로젝트");
        setSize(400, 400);
        panel.add(msg);
        panel.add(ledOn);
        panel.add(ledOff);
        panel1.add(tmp);
        panel1.add(hum);
        sp.add(out);
        add(BorderLayout.NORTH, panel);
        add(BorderLayout.CENTER, sp);
        add(BorderLayout.SOUTH, panel1);

        ledOn.addActionListener(this);
        ledOff.addActionListener(this);
        setVisible(true);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    @Override
    public void recvMsg(String topic, MqttMessage msg) {
        System.out.println(topic+","+msg);
        String append = out.getText();
        out.setText(topic+","+msg+"\n"+append);
        JSONObject obj = new JSONObject(new String(msg.getPayload()));
        tmp.setText("온도 : " + obj.get("tmp").toString());
        tmp.setText("습도 : " + obj.get("hum").toString());
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        JButton b = (JButton) e.getSource();
        if (b.getText().equals("LED_NO")) {
            mqtt.sendMessage("1");
        } else if (b.getText().equals("LED_OFF")) {
            mqtt.sendMessage("2");
        }
    }
}