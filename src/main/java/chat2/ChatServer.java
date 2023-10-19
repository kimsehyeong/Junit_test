package chat2;

import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class ChatServer {

    public static void main(String[] args) throws  Exception{
        ServerSocket serverSocket = new ServerSocket(8888);

        List<ChatThread> list = Collections.synchronizedList(new ArrayList<>()); //동시에 접속하지 못하게한다.

        while (true){
            Socket socket = serverSocket.accept();// 클라이언트 접속
            ChatThread chatThread = new ChatThread(socket, list);
            chatThread.start();
        }

    }






}
