import java.io.*;
import java.net.*;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import java.util.Scanner;



public class WifiTelnetServer {
    private static final int TELNET_PORT = 23;  // 표준 텔넷 포트인 23 대신 다른 포트 번호를 사용

    public static void main(String[] args) {
        try (ServerSocket serverSocket = new ServerSocket(TELNET_PORT)) {
            System.out.println("Telnet server started on port: " + TELNET_PORT);

            while (true) {
                Socket clientSocket = serverSocket.accept();
                new Thread(new ClientHandler(clientSocket)).start();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static class ClientHandler implements Runnable {
        private Socket clientSocket;

        public ClientHandler(Socket socket) {
            this.clientSocket = socket;
        }

        @Override
        public void run() {
            //System.outl.prinLn("Coonection
            // vim text is going well but  i dont thinkg i can get used it
            System.out.println("Connected device IP: " + clientSocket.getInetAddress().getHostAddress() + ", Port: " + clientSocket.getPort());
            System.out.println("Connected device IP: " + clientSocket.getInetAddress().getHostAddress() + ", Port: " + clientSocket.getPort());
            System.out.println("Connected device IP: " + clientSocket.getInetAddress().getHostAddress() + ", Port: " + clientSocket.getPort());
            System.out.println("Connected device IP: " + clientSocket.getInetAddress().getHostAddress() + ", Port: " + clientSocket.getPort());


            try (InputStream input = clientSocket.getInputStream();
                 OutputStream output = clientSocket.getOutputStream();
                 BufferedReader reader = new BufferedReader(new InputStreamReader(input));
                 PrintWriter writer = new PrintWriter(output, true);
                 Scanner scanner = new Scanner(System.in)) {  // 콘솔 입력을 위한 Scanner
                // Priner writer anner 의 수정하기 모두 콘솔 압력을 위한 Scanner로 처리할것

                String line;
                writer.println("welcome to client server nice too meet you ");

                while ((line = reader.readLine()) != null) {
                    System.out.println("Received: " + line);

                    // 콘솔에서 메시지 입력
                    System.out.print("hello there is a practicing .");
                    String messageToSend = scanner.nextLine();

                    writer.println(messageToSend);
                }



            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}