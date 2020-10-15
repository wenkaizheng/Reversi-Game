import java.io.FileWriter;
import java.util.Observable;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
/**
 * this class is used for represent model, which is 9*9 size
 * this class should be included in controller
 * 2d array will be used for representing the game
 *
 * @author wenkaizheng
 *
 */
public class ReversiModel extends Observable{
    private int size = 9;
    private char[][] picture;
    public String address ="localhost";
    public int port =4003;
    /**
     * simple constructor
     * initial 9*9 size array
     * there are some special position ' '
     */
    public ReversiModel() {

         setPicture(new char[9][9]);
    }
    /**
     * this method count all 'w' in the 2d array
     * @return total count
     */
    public int getAllWhite(){
        int count =0;
        for(int i=0;i<size;i++)
            for(int j=0;j<size;j++)
               if(getPicture()[i][j]=='W')
                  count+=1;
        return count;

    }
    /**
     * this method count all 'b' in the 2d array
     * @return total count
     */
    public int getAllBlack(){
        int count =0;
        for(int i=0;i<size;i++)
            for(int j=0;j<size;j++)
               if(getPicture()[i][j]=='B')
                  count+=1;
        return count;

    }
    /**
     *  this method put the chess int the model
     * @param i color
     * @param x  x position
     * @param y  y position
     */

    public void set_position(int i,int x, int y){
        // white
       // addObserver(grid);
        setChanged();

        String place =new String("");
        char first =(char)('a'+y-1);
        char second =(char)('1'+x);
        place+=first;
        place+=second;
        information p =new information(place,i);



        notifyObservers(p);

        System.out.println("a change has been make");

        if(i==0){
           getPicture()[x][y]='W';
        }
        else{
            getPicture()[x][y]='B';
        }

        // black

    }
    /**
     * this method is simple getter
     * @param x x position
     * @param y y position
     * @return the chess in this index
     */
    public char get_chess(int x,int y){
        return  getPicture()[x][y];
    }
    /**
     * getter
     * @return the size of model
     */
    public int getsize(){
        return size;
    }
    /**
     * this method return the String for whole model
     */
    @Override
    public String toString() {
        String result = new String("");
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                result += getPicture()[i][j];
                result+=' ';
            }
            result += '\n';
        }
        return result;
    }

    public void ReversiModelReset() {
    	for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                // first one
                if (j == 0) {
                    if (i != 8)
                        getPicture()[i][j] = (char) ((i + 1) + '0');
                    else
                        getPicture()[i][j] = ' ';
                } else {
                    if (i != 8)
                        getPicture()[i][j] = '-';
                    else
                        getPicture()[i][j] = (char) ('a' + j - 1);
                }
            }

        }
        getPicture()[3][4] = 'W';
        getPicture()[3][5] = 'B';
        getPicture()[4][4] = 'B';
        getPicture()[4][5] = 'W';
    }

    public void save(String filename) {
    	 try {
         	ObjectOutputStream objectOutputStream =
                     new ObjectOutputStream(new FileOutputStream(filename));
         	objectOutputStream.writeObject(getPicture());
             objectOutputStream.close();         

         } catch(IOException e) {
         }

    }

    public boolean load(String filename) {
    	try {
    		ObjectInputStream objectInputStream =
    	            new ObjectInputStream(new FileInputStream(filename));
            setPicture((char[][]) objectInputStream.readObject());
            objectInputStream.close();
         return true;

         } catch(IOException e) {
        	
         } catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	 return false;
       
    }
	public char[][] getPicture() {
		return picture;
	}
	public void setPicture(char[][] picture) {
		this.picture = picture;
	}

}