import java.io.IOException;

public class Referee {

    private Player xPlayer;
    private Player oPlayer;
    private Board board;

    public Referee() {
        xPlayer = null;
        oPlayer = null;
        board = null;
    }

    
    /** 
     * @param xPlayer
     */
    public void setxPlayer(Player xPlayer) {
        this.xPlayer = xPlayer;
    }

    
    /** 
     * @param oPlayer
     */
    public void setoPlayer(Player oPlayer) {
        this.oPlayer = oPlayer;
    }

    
    /** 
     * @param board
     */
    public void setBoard(Board board) {
        this.board = board;
    }

    
    /** 
     * @return Player
     */
    public Player getxPlayer() {
        return xPlayer;
    }

    
    /** 
     * @return Player
     */
    public Player getoPlayer() {
        return oPlayer;
    }

    
    /** 
     * @return Board
     */
    public Board getBoard() {
        return board;
    }

    
    /** 
     * @throws IOException
     */
    public void runTheGame() throws IOException{
        xPlayer.setOpponent(oPlayer);
        oPlayer.setOpponent(xPlayer);
    }

    
    /** 
     * @return String
     */
    public String getoPlayerName(){
        return oPlayer.getName();
    }

    
    /** 
     * @return String
     */
    public String getxPlayerName(){
        return xPlayer.getName();
    }

}
