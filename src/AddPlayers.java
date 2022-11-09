
// import java.io.*;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class AddPlayers {

    private PrintWriter socketOut;
	private Socket playerSocket;
	private BufferedReader stdIn;
	private BufferedReader socketIn;
    private boolean entered = false;
    private PlayerListener listener;

    public AddPlayers(String serverName, int portNumber){
        try {
			playerSocket = new Socket(serverName, portNumber);
			stdIn = new BufferedReader(new InputStreamReader(System.in));
			socketOut = new PrintWriter((playerSocket.getOutputStream()), true);
            listener = new PlayerListener(playerSocket);

		} catch (IOException e) {
			System.err.println(e.getStackTrace());
		}

    }

    public void register()  {

		String line = "";
        listener.start();
		while (true) {

            if(!entered){
			try {
				System.out.println("To join game, please enter you name: ");
				line = stdIn.readLine();
				socketOut.println(line);
                entered = true;
      
                if(line.equals("QUIT")){
                    break;
                }
			} catch (IOException e) {
				System.out.println("Sending error: " + e.getMessage());
			}
            }

            else if (entered){
                try {
                    line = stdIn.readLine();
                    socketOut.println(line); 

                    if(line.equals("QUIT")){
                        break;
                    }
                } catch (IOException e) {
                    System.out.println("Sending error: " + e.getMessage());
                }
                
            }

		}
        
		try {
			stdIn.close();
			socketIn.close();
			socketOut.close();
		} catch (IOException e) {
			System.out.println("Closing error: " + e.getMessage());
		}
        
	}


    public static void main(String[] args) throws IOException {
		AddPlayers aClient = new AddPlayers("localhost", 8080);
		aClient.register();
	}
}
