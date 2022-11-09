import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.ArrayList;

public class ServerThread extends Thread implements Constants{
    private Socket client;
    private BufferedReader in;
    private PrintWriter out;
    private String name;
    private boolean engaged;
    private boolean started;
    private boolean ended;
    private boolean turn;
    private boolean requestPlay;
    private boolean exit;
    private Player player;
    public Game game;
    private ArrayList <ServerThread> multiPlayer;
    private int oppNumberId;

    public ServerThread (Socket clientSocket, ArrayList <ServerThread> multiPlayer , Game game) throws IOException{
        this.client = clientSocket;
        in = new BufferedReader(new InputStreamReader(client.getInputStream()));
        out = new PrintWriter(client.getOutputStream(), true);
        this.multiPlayer = multiPlayer;
        this.game = game;
        this.engaged = false;
        this.started = false;
        this.turn = false;
        this.ended = false;
        this.requestPlay = false;
        this.exit = false;
        
    }

    @Override
    public void run(){
        String line = "";
		while (!ended && !exit) {
			try {
                line = in.readLine();

            } catch (IOException e) {
                e.printStackTrace();
            }
			if (line != null && (multiPlayer.size() % 2 == 1) && !started) {
                xPlayerSetup(line);
			}
            // Creating second player from second thread and creating a game.
            else if (line != null && (multiPlayer.size() % 2 == 0) && !started){
                name = line;
                requestPlay = true;
                out.println("Would you like to play a game with: " + (multiPlayer.get(multiPlayer.size() - 2)).returnName() 
                + ". Enter 'Y' for yes and 'N' for no.");
                multiPlayer.get(multiPlayer.size() - 2).setRequest();
                while (requestPlay){
                    String word = " ";
                    try {
                        word = in.readLine();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    if (word.equals("Y")){
                        requestPlay = false;
                    }
                    else if(word.equals("N")){
                        closeDown();
                    }
                    
                }

                oPlayerSetup(line);
            }


            else if(started && !requestPlay){
                out.println("enter a play for row and column, e.g. (0,1): ");
                try {
                    sleep(300);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                ended = player.checkWinner();
                
                if (turn && !ended){
                    out.println("You played: " + line);
                    player.makeMove(line);
                    ended = player.checkWinner();

                    displayBoard();
                    multiPlayer.get(oppNumberId).displayBoard();
                    if (ended){
                        multiPlayer.get(oppNumberId).setEnded();
                        endTieGame();
                        endWinnerGame();
                        try {
                            sleep(5000);
                            exit = true;
                            return;
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                    if (!ended){
                        out.println("Please wait for " + multiPlayer.get(oppNumberId).returnName() + " to make a move");
                        multiPlayer.get(oppNumberId).setTurn();
                        multiPlayer.get(oppNumberId).announce();
                        turn = false;
                    }
                }
            }
            else{
                closeDown();
            }
		}        
        closeDown();
    }

    public void xPlayerSetup(String line){
        name = line;
        String word1 = "";
		out.println("Welcome " + line + " Please wait while we pair you with a player");
        player = new Player(line, LETTER_X);

        while(!requestPlay){
            out.println("Looking for an opponent for you...");
            try {
                sleep(4000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            // requestPlay = false;
            out.println("Would you like to play a game with: " + (multiPlayer.get(multiPlayer.size() - 1)).returnName() 
            + ". Enter 'Y' for yes and 'N' for no.");
        while (requestPlay){
            try {
                word1 = in.readLine();
            } catch (IOException e1) {

                e1.printStackTrace();
            }
            try {
                sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            if (word1.equals("Y")){
                requestPlay = false;
            }
            else if(word1.equals("N")){
                return;
            }
        }

        while(game == null){
            try {
                sleep(2500);
                out.println("Waiting for the other player's response...");
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
        }

        displayBoard();
        out.println("Second player accepted, you are playing against: " + multiPlayer.get(oppNumberId).returnName() 
        + ". \ngame started...");
        started = true;
        turn = true;
        out.println("enter a play for row and column, e.g. (0,1): ");
                
    }

    public void oPlayerSetup(String line){
        
        // getting an opponent
        oppNumberId = multiPlayer.size() - 2;
        multiPlayer.get(oppNumberId).setOpponentId(oppNumberId + 1);

        
        out.println("Welcome " + line + " Opponent found. You are playing with: " 
        + multiPlayer.get(oppNumberId).returnName());

        player = new Player(line, LETTER_O);
        game = new Game(multiPlayer.get(oppNumberId).getPlayer(), player);
        multiPlayer.get(oppNumberId).game = game;

        engaged = true;
        multiPlayer.get(oppNumberId).setEngaged();

        displayBoard();
        out.println("Game Started...");
        out.println("Please wait for " + multiPlayer.get(oppNumberId).returnName() + " to make a move");
        started = true;

    }

    public void setOpponentId(int num){
        oppNumberId = num;
    }

    public void displayBoard(){
        out.println(game.getTheBoard().display());
    }

    public String returnName(){
        return name;
    }

    public boolean isEngaged(){
        return engaged;
    }

    public void setEngaged(){
        engaged = true;
    }
    
    public Player getPlayer(){
        return player;
    }

    public void setTurn(){
        turn = true;
    }

    public void setRequest(){
        requestPlay = true;
    }

    public void announce(){
        out.println("It's your turn. Enter a play for x and y, e.g. (0,1): ");
    }

    public void endWinnerGame(){
        ended = true;
        out.println(" You win. Game Ended!");
        multiPlayer.get(oppNumberId).endLoserGame();
    }

    public void endLoserGame(){
        out.println(multiPlayer.get(oppNumberId).returnName() + " Won the game \nGame ended...");
    }

    public void endTieGame(){
        ended = true;
        if(game.getTheBoard().isFull()){
            out.println("The game is a tie");
        }
    }

    public void setEnded(){
        ended = true;
        exit = true;
    }

    public void closeDown(){
        // exit = true;
        try {
            in.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
		out.close();
    }
    
}
