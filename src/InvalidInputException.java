/**
 * this class is self make exception and using for report
 * @author wenkaizheng
 *
 */
public class InvalidInputException extends Exception{
    
	
	private static final long serialVersionUID = 1L;
	/**
	 * simple constructor for passing message
	 * @param mess report message
	 */
    public InvalidInputException(String mess){
        super(mess);
    }
}