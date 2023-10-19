package chat2;

import lombok.extern.slf4j.Slf4j;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Slf4j
public class ChatThread extends Thread{

    private String name;
    private BufferedReader br;
    private PrintWriter pw;
    private Socket socket;
    List<ChatThread> list;

    public ChatThread(Socket socket, List<ChatThread> list) throws Exception{
        this.socket = socket;
        BufferedReader br = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        PrintWriter pw = new PrintWriter(new OutputStreamWriter(socket.getOutputStream()));

        this.br = br;
        this.pw = pw;
        this.name = br.readLine(); // 닉네임
        this.list = list;
        this.list.add(this); // chatclient 자체를 list를 넣어준다 .
    }

    /*현재 chatThread에 연결되어있는 클라에게 채팅을 보낸다.*/
    public void sendMessage(String msg){
        pw.println(msg);
        pw.flush();
    }

    @Override
    public void run() {
        /* #broadCast
        * charThread는 사용자가 보낸 메세지를 읽어들여서
        * 접속된모든 클라에게 메시지를 보낸다
        * */

        //나를 제외한 모든 사용자에게 "000님이 연결되었습니다. 라고 떠야할것"
        // 현재 ChatThread를 제외하고 보낸다.
        try {
        broadcast(name + "님이 연결되었습니다", false);

        String line = null;

            while ((line = br.readLine()) != null){
                if ("/quit".equals(line)){
                    throw new RuntimeException("접속종료");
                }

                //나를 포함한 ChatThread에게 메시지를 보낸다
                broadcast(name +":"+line,true);
            }
        }catch (Exception e){// ChatThread가 연결이 끊어졌을경우 .
            e.printStackTrace();
            broadcast(name + "님이 연결이 끊어젔습니다", false);
            this.list.remove(this);
        }finally {
            try {
                br.close();
            }catch (Exception e){

            }
            try {
                pw.close();
            }catch (Exception e){

            }
            try {
                socket.close();
            }catch (Exception e){

            }
        }
    }

    private void broadcast(String msg, boolean includeMe){
        List<ChatThread> chatThreads = new ArrayList<>();
            for (int i=0; i < this.list.size(); i++){
                chatThreads.add(list.get(i));
            }

        try {
            for(int i =0; i < chatThreads.size(); i++){
                ChatThread ct = chatThreads.get(i);
                if (!includeMe) { // 나를 포함하지 않는것
                        if(ct == this){ // this : chatThread 자체
                            continue;
                        }
                    }
                ct.sendMessage(msg);
                }
        }catch (Exception e){
            System.out.println("///");
        }
    }
}
