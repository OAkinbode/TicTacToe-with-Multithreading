/**
 * Author: Olusola Akinbode
 * Class Game: Runner class for the game. 
 */
import java.io.*;

//STUDENTS SHOULD ADD CLASS COMMENTS, METHOD COMMENTS, FIELD COMMENTS 


public class Game implements Constants  {

	private Board theBoard;
	private Referee theRef;
	private Player xPlayer = null;
	private Player oPlayer = null;
	
    public Game(Player xPlayer, Player oPlayer) {
        theBoard  = new Board();
		this.xPlayer = xPlayer;
		this.oPlayer = oPlayer;
		run();
	}
	
	/** 
	 * @param args
	 * @throws IOException
	 */
	public void run(){
		
		xPlayer.setBoard(this.theBoard);
		oPlayer.setBoard(this.theBoard);

		// Letting the players know about the referee and vice versa. 
		
		theRef = new Referee();
		theRef.setBoard(this.theBoard);
		theRef.setoPlayer(oPlayer);
		theRef.setxPlayer(xPlayer);
		oPlayer.setRef(theRef);
        xPlayer.setRef(theRef);
	
	}
	
	public Player getOppPlayer(String name){
		if (xPlayer.getName().equals(name)){
			return xPlayer;
		}
		else if (oPlayer.getName().equals(name)){
			return oPlayer;
		}
		return null;
	}

	public Board getTheBoard() {
		return theBoard;
	}

	public void playerMoves(String move){
		xPlayer.makeMove(move);
	}
	

}
