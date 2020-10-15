/**
 * This class is to store the borad's situation
 * pass though the ReversiBoard using the net
 * and get the board situation in the UI view
 * 
 * @author Wenkai Zheng
 */
import java.io.Serializable;
public class ReversiBoard implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private char[][] picture;
	public ReversiBoard() {
		picture=new char[9][9];
	}
	public char[][] get() {
		return picture;
	}
	public void set(char[][] temp) {
		picture=temp;
		
	}
	
}
