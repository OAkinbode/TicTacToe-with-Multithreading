/**
 * Author: Olusola Akinbode
 * Class Player: holding the player identifiers and methods required to participate in the game. 
 */

import java.io.*;

public class Player{
	
	private String name;
	private Board board;
	private Player opponent;
	private char mark;
	private Referee ref;
	private int playCount = 0;
	String plays[] = new String[9];
	
	public Player(String name, char mark) {
		this.name = name;
		this.mark = mark;
	}

	
	/** 
	 * @return String
	 */
	public String getName() {
		return name;
	}

	
	/** 
	 * @param name
	//  */
	public void setName(String name) {
		this.name = name;
	}

	
	/** 
	 * @return Board
	 */
	public Board getBoard() {
		return board;
	}

	
	/** 
	 * @param board
	 */
	public void setBoard(Board board) {
		this.board = board;
	}

	
	/** 
	 * @return char
	 */
	public char getMark() {
		return mark;
	}

	
	/** 
	 * @param mark
	 */
	public void setMark(char mark) {
		this.mark = mark;
	}
	
	
	/** 
	 * @param opponent
	 */
	public void setOpponent(Player opponent){
		this.opponent = opponent;
	}
	
	/** 
	 * @return Player
	 */
	public Player getOpponent(){
		return this.opponent;
	}

	
	/** 
	 * @param ref
	 */
	public void setRef(Referee ref){
		this.ref = ref;
	}

	
	/** 
	 * @throws IOException
	 * Promises: initiates a play from a player. 
	 */
	public void play(String move) {
		if(!board.oWins() && !board.xWins() && !board.isFull()){
			makeMove(move);
	
		}
		else{
			System.exit(0);
		}
	}

	
	/** 
	 * @throws IOException
	 * Promises: contains logic for a player move. 
	 */
	public void makeMove(String move){
		int row;
		int col;
		BufferedReader stdin;

		row = Character.getNumericValue(move.charAt(0));
		col = Character.getNumericValue(move.charAt(2));
		board.addMark(row, col, mark);
	}

	/*
	 * Promises: Checks if a player has won.
	 */
	public boolean checkWinner(){
		boolean ended = false;
		if(board.oWins()){
			ended = true;
		}
		else if(board.xWins()){
			ended = true;
		}
		else if(board.isFull()){
			ended = true;
		}
		return ended;
	}

	
	/** 
	 * @param move
	 * @return boolean
	 * Promises: a check on whether the location of a play has already been filled with a mark by another or same player. 
	 */
	
	/** 
	 * @param move
	 * Promises: blocking of a play location from further play.
	 */
	private void incrPlayCount(String move){
		plays[playCount] = move;
		playCount++;
	}
}
