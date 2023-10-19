
import com.sun.net.httpserver.Headers;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpServer;

import java.io.IOException;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.charset.Charset;
import java.text.SimpleDateFormat;
import java.util.Date;

public class HttpServerManager {
    private final String HOSTNAME = "0.0.0.0";
    private final int PORT = 8080;
    private final int BACKLOG = 0;
    private HttpServer server = null;


    // 생성자 생성
    public HttpServerManager() throws IOException {
        createServer(HOSTNAME, PORT);
    }

    public HttpServerManager(int port) throws IOException {

        createServer(HOSTNAME, port);
    }
    public HttpServerManager(String host, int port) throws IOException {
        createServer(host, port);
    }

    //서버 생성
    private void createServer(String host, int port) throws IOException {
        //HTTP SERVER 생성
        this.server = HttpServer.create(new InetSocketAddress(host, port),BACKLOG);
        // HTTP SERVER Context 설정
        server.createContext("/", new RootHandler());
    }
    // 서버 실행
    public void start(){
        server.start();
    }

    //서버 중지
    public void stop(int delay){
        server.stop(delay);
    }

    public static void main(String[] args) {
        HttpServerManager httpServerManager = null;

        try{
            System.out.println(
                    //시작 로그 String 형식을 매핑해줌
                    String.format(
                            "[%s][HTTP SERVER][START]",
                            new SimpleDateFormat("yyyy-MM-dd H:mm:ss").format(new Date())
                    )
            );
            //서버 생성
            httpServerManager = new HttpServerManager("localhost",3000);
            httpServerManager.start();

            /*Runtime.getRuntime().addShutdownHook() -> 프로그램 종료 시 특정 작업을 수행하는 메소드
             * 어플리케이션의 안전한 종료 처리
             * */
            Runtime.getRuntime().addShutdownHook(new Thread(new Runnable() {
                @Override
                public void run() {
                    // 종료 로그
                    System.out.println(
                            String.format(
                                    "[%s][HTTP SERVER][STOP]",
                                    new SimpleDateFormat("yyyy-MM-dd H:mm:ss").format(new Date())
                            )
                    );
                }
            }));

            // Enter 입력 시 종료
            System.out.print("Please press 'Enter' to stop the server");
            System.in.read();

        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        } finally {
            // 0초 대기 후 종료
            httpServerManager.stop(0);
        }
    }


    private class RootHandler implements HttpHandler {
        @Override
        public void handle(HttpExchange exchange) throws IOException {
            OutputStream responseBody = exchange.getResponseBody();

            try {
                // 서버 생성 시 돌려줄 html을 그려내는 작업
                StringBuilder sb = new StringBuilder();
                sb.append("<!DOCTYPE html>");
                sb.append("<html>");
                sb.append("   <head>");
                sb.append("       <meta charset=\"UTF-8\">");
                sb.append("       <meta name=\"author\" content=\"Dochi\">");
                sb.append("       <meta name=\"viewport\" content=\"width=device-width, initial-scale=1.0\">");
                sb.append("       <title>Example</title>");
                sb.append("   </head>");
                sb.append("   <body>");
                sb.append("       <h5>Hello, HttpServer!!!</h5>");
                sb.append("       <span>Method: "+(exchange.getRequestMethod())+"</span></br>");
                sb.append("       <span>URI: "+(exchange.getRequestURI())+"</span></br>");
                sb.append("       <span>PATH: "+(exchange.getRequestURI().getPath())+"</span></br>");
                sb.append("       <span>QueryString: "+(exchange.getRequestURI().getQuery())+"</span></br>");
                sb.append("   </body>");
                sb.append("</html>");

                // UTF-8 인코딩 작업 (위의 html을 그려낸 작업을 인코딩 해주는 것)
                ByteBuffer bb = Charset.forName("UTF-8").encode(sb.toString());
                int contentLength = bb.limit();
                byte[] content = new byte[contentLength];
                bb.get(content, 0, contentLength);

                /* Response headers 보내는 type에 따라서 다르게 설정해주면 된다.
                 * 그려낸 화면이 응답될 때 뜨지 않는다면 이부분의 문제일 경우가 다분하다.
                 * 텍스트, js, css를 가져오고 싶다면 text/plain
                 * HTML의 경우엔 text/html
                 * JSON은 application/json 으로 설정해주면 된다.
                 * */
                Headers headers = exchange.getResponseHeaders();
                headers.add("Content-Type", "text/html;charset=UTF-8");
                headers.add("Content-Length", String.valueOf(contentLength));

                // Send Response headers(상태코드, contentLength)
                exchange.sendResponseHeaders(200, contentLength);

                responseBody.write(content);

                //Response Header을 보낸 후에는 반드시 닫아주어야 한다.
                responseBody.close();

            } catch ( IOException e ) {
                e.printStackTrace();

                if( responseBody != null ) {
                    responseBody.close();
                }
            } finally {
                exchange.close();
            }
        }
    }
}