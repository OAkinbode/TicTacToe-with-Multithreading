/**
 * Author: Olusola Akinbode
 * Class Game: Runner class for the game. 
 */
import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

//STUDENTS SHOULD ADD CLASS COMMENTS, METHOD COMMENTS, FIELD COMMENTS 


public class GameServer implements Constants  {

	private Board theBoard;
	private Referee theRef;
    private Socket aSocket;
    private ServerSocket serverSocket;
    private PrintWriter socketOut;
    private BufferedReader socketIn;
    ExecutorService pool;
    final int threadPoolSize;
    private int counter;
    private ArrayList <ServerThread> serverThreads = new ArrayList<ServerThread>();
    private Game game;
    
	
    public GameServer() throws IOException {
        threadPoolSize = 10;
        counter = 0;
        theBoard  = new Board();
        pool = Executors.newFixedThreadPool(threadPoolSize);
        
        try {
            serverSocket = new ServerSocket(8080);
        } catch (IOException e) {
            e.printStackTrace();
        }  
    
	}

	public void run() throws IOException {

        while(true){
        System.out.println("Server is running...");
        Socket playerSocket = serverSocket.accept();
        socketIn = new BufferedReader(new InputStreamReader(playerSocket.getInputStream()));
        socketOut =new PrintWriter(playerSocket.getOutputStream(), true);
        ServerThread playerThreads = new ServerThread(playerSocket, serverThreads, game );
        serverThreads.add(playerThreads);
        pool.execute(playerThreads);
        }

	}

    public void threadManager(String playerName){
        
    }
    
	
	/** 
	 * @param args
	 * @throws IOException
	 */
	public static void main(String[] args) throws IOException {
        GameServer games = new GameServer();
        games.run();
	}
	

}

