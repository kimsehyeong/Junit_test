package chat;

import org.apache.catalina.filters.ExpiresFilter;
import util.BaseUtil;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.*;

public class ChatClient {
    public static void main(String[] args) throws Exception{
        ServerSocket serverSocket = new ServerSocket(9999); // 포트 9999에서 서버를 기다린다

        // 공유객체에서 쓰레드에 안전한 리스트를 만든다.
        List<PrintWriter> outList = Collections.synchronizedList(new ArrayList<>());


        while (true) // 반복해서 여러 클라이언트를 받을수있게한다
        {
            Socket socket = serverSocket.accept();// 사용자 대기 -> 클라이언트와 통신하기위해서 대기
            System.out.println("접속 = " + socket);

            ChatThread chatThread = new ChatThread(socket, outList);
            chatThread.start();
        }

    }

    }


    class ChatThread extends  Thread{

        private Socket socket;
        private List<PrintWriter> outList;
        private PrintWriter out;
        private BufferedReader in;


        public ChatThread(Socket socket,List<PrintWriter> outList) {
            this.socket = socket;
            this.outList = outList;
            try {
                //1. socket으로 부터 읽어들일 수있는 객채를 얻는다.
                //2. socket을 쓰기위한 객체를 얻는다 (현재 연결된 클라이언트에게 쓰는 객체)
                in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                out = new PrintWriter(new OutputStreamWriter(socket.getOutputStream()));

                outList.add(out);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        public void run(){

            String line = null;
            try {
                while ((line = in.readLine()) != null) {
                    for (int i =0; i < outList.size(); i++){ // 접속한 모든 클라이언트에게 메시지를 전송한다.
                        PrintWriter o = outList.get(i);
                        o.println(line); //메세지전송
                        o.flush();
                    }
                }


            } catch (Exception ex){
                ex.printStackTrace();
            }finally {
                try {
                    outList.remove(out);//접속이 끝어젔을경우.
                } catch (Exception ex) {
                    ex.printStackTrace();
                }

                for (int i = 0; i < outList.size(); i++) { // 접속한 모든 클라이언트에게 메시지를 전송한다.
                    PrintWriter o = outList.get(i);
                    o.println("클라이언트가 접속이 끊어졌습니다");
                    o.flush();

                }
                try {
                    socket.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

                //3. 클라이언트가 보낸 메세지를 읽는다

            //4. 접속된 모든 클라이언트에게 메세지를 보낸다.(현재 접속된 모든 클라이언트에게 쓸수있는 객체가 필요하다.)
        }
        /**
         * 히스테리설정 테스트
         */

private static final String ALARM_NORMAL = "0";
private static final String ALARM_CAUTIONL = "1";
private static final String ALARM_WARING = "2";


        public void hysTest(){
            HashMap<String, Object> map = new HashMap<>();
            ArrayList<Map<String,Object>> list = new ArrayList<>();
            for (Map<String,Object> status : list ) {
                if (!BaseUtil.equals(status.get("measurementId"),map.get("measurementId")) || !"N".equals(status.get("valveYn"))){
                    continue;
                }

                int nowVal = BaseUtil.getInt(map.get("measurementValve"));

            }
        }

        private String determineAlarmStatus(Map<String,Object> status, int nowVal){

            status.put("normalMaxValue","60");
            status.put("normalMinValue","40");
            status.put("cautionMaxValue","70");
            status.put("cautionMinValue","30");
            status.put("waringMaxValue","80");
            status.put("warningMinValue","22");
            status.put("settingHys","10");
            int normalMaxValue= BaseUtil.getInt(status.get("normalMaxValue"));
            int normalMinValue= BaseUtil.getInt(status.get("normalMinValue"));
            int cautionMaxValue= BaseUtil.getInt(status.get("cautionMaxValue"));
            int cautionMinValue= BaseUtil.getInt(status.get("cautionMinValue"));
            int waringMaxValue= BaseUtil.getInt(status.get("waringMaxValue"));
            int warningMinValue= BaseUtil.getInt(status.get("warningMinValue"));
            int hys= BaseUtil.getInt(status.get("settingHys"));

            if (nowVal >= warningMinValue+ hys || nowVal <= waringMaxValue ){
                return ALARM_NORMAL;
            }

            return "";
        }
    }

