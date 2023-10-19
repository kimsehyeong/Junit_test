package chat2;

import lombok.extern.slf4j.Slf4j;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.Socket;

@Slf4j
public class CharClient {

    public static void main(String[] args) throws Exception{

        if(args.length != 1){
            System.out.println("사용법 : java com.example.chat2.CharClient 닉네임");
            return;
        }

        String name = args[0];
        Socket socket = new Socket("127.0.0.1", 8888);
        BufferedReader br = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        PrintWriter pw = new PrintWriter(new OutputStreamWriter(socket.getOutputStream()));
        BufferedReader keyboard = new BufferedReader(new InputStreamReader(System.in));

        pw.println(name); //닉네임 전송
        pw.flush();

        //백그라운드로 서버가 보내준 메시지를 읽어들여서 화면에 출력한다
        InputThread inputThread = new InputThread(br);      //사용자메세지를 읽어들인다.
        inputThread.start();

        //클라이언트는 읽어들인 메시지를 서버에 전송한다
        try{
            String line = null;
            while ((line = keyboard.readLine()) != null){ //접속 연결이 끊어졌을때
                if ("/quit".equals(line)){
                    pw.println("/quite");
                    pw.flush();
                    break;
                }
                pw.println(line);
                pw.flush();
            }
        }catch (Exception e){
            System.out.println("....");
        }
        try {
            br.close();
        }catch (Exception e){
                log.info("1111");
        }

        try {
            pw.close();
        }catch (Exception e){
            log.info("1111");
        }

        try {
            log.info("socket closed!");
            socket.close();
        }catch (Exception e){
            log.info("1111");
        }




   }
}


class InputThread extends Thread{
    BufferedReader br;
    public InputThread(BufferedReader br) {
        this.br = br;
    }

    @Override
    public void run() {
        try{
            String line = null;
            while ((line = br.readLine()) != null){
                System.out.println(line);
            }
        }catch (Exception e){
            System.out.println("....");
        }
    }
}
