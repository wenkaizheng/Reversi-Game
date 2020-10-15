/**
 * this class is self make exception and using for report
 * @author wenkaizheng
 *
 */
public class InvalidMoveException extends Exception{
    
	
	private static final long serialVersionUID = 1L;
	/**
	 * simple constructor for passing message
	 * @param mess using for report
	 */
    public InvalidMoveException(String mess){
        super(mess);
    }
}