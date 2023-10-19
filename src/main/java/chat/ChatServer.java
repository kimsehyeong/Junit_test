package chat;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.Socket;

public class ChatServer {

    public static void main(String[] args) throws Exception{
        String name = args[0]; // java com.example.chat.Chatclient {kim} -> 이부분

        Socket socket = new Socket("127.0.0.1", 9999);
        PrintWriter out = new PrintWriter(new OutputStreamWriter(socket.getOutputStream())); // out은 메세지를 쓰는기능
        BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream())); //다른 사용자의 메세지를 읽음

        BufferedReader keyboard = new BufferedReader(new InputStreamReader(System.in));

        String line = null;
        InputThread inputThread = new InputThread(in);
        inputThread.start();
        while ((line = keyboard.readLine()) != null){
            out.println(name + " : " + line);
            out.flush();// 네트워크로 쓸때는 flush 할것
        }
    }
}

class InputThread extends Thread {
    BufferedReader in = null;
    public  InputThread(BufferedReader in){
        this.in = in;
    }

    @Override
    public void run() {
        try{
            String line = null;
            while ((line = in.readLine()) != null){
                System.out.println(line);
            }

        }catch (Exception ex){
            ex.printStackTrace();
        }

    }
}