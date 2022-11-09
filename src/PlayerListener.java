import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;

public class PlayerListener extends Thread{

    private Socket listen;
    private BufferedReader in;
    private String statement;
    
    public PlayerListener(Socket s) throws IOException{
        listen = s;
        in = new BufferedReader(new InputStreamReader(listen.getInputStream()));
        statement = "";
    }

    public void run(){
        try {
            while (true){
                String response = "";
                response = in.readLine();
                System.out.println("> " + response);
                statement = response;
            }
    }
    catch(IOException e){
        e.printStackTrace();

    }
    finally{
        try {
            in.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    }

    public String getStatement(){
        return statement;
    }
    
}
