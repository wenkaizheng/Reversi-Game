/**
 * 
 * @author wenkaizheng this class is designed for all controlling the model it
 *         takes one model as parameter and do something for it it will be
 *         called by main and using for interact with model
 */
public class ReversiController {
    private ReversiModel model;
    private int current_score;
    private char current_x;
    private char current_y;
    public char HumC='?';
    public char	ComC='?';
    /**
     * simply constructor to accept model
     * 
     * @param mo a model
     */
    public ReversiController(ReversiModel mo) {
        model = mo;
        current_score = 0;
    }

    /**
     * this one used for calculating the white score which will interact with model
     * 
     * @return the score
     */
    public int white_score() {
        return model.getAllWhite();
    }

    /**
     * this one used for calculating the black score which will interact with model
     * 
     * @return the score
     */
    public int black_score() {
        return model.getAllBlack();
    }

    /**
     * this one return the current message which include where will the computer to
     * place
     * 
     * @return the message
     */

    public String getComputerDone() {
        return "The computer put chess in " + current_y + current_x;
    }

    /**
     * this method will check the input if the length is not exact 2 , we throws the
     * exception if the input with invalid position , we throws exception if this
     * position can not be placed, we throws the exception if it is valid , we will
     * call humanTurn to execute it;
     * 
     * @param coordinate from view
     * @throws InvalidInputException if there is some invalid input
     * @throws InvalidMoveException  if there is some invalid move
     */
    public void check_input(String coordinate) throws InvalidInputException, InvalidMoveException {
        if (coordinate.length() != 2) {
            throw new InvalidInputException("This coordinate is not correct please enter exactly 2 digit");

        } else {

            char x = coordinate.charAt(1);
            int x_coordinate;
            char y = coordinate.charAt(0);
            int y_coordinate;
            if (x <= '8' && x >= '1') {
                x_coordinate = x - '0' - 1;
            } else {
                throw new InvalidInputException("The coordinate is not with valid number");

            }
            if (y <= 'h' && y >= 'a') {
                y_coordinate = y - 'a' + 1;
            } else {
                throw new InvalidInputException("The coordinate is not with valid number");

            }
            if (humanTurn(x_coordinate, y_coordinate) == 1) {
                throw new InvalidMoveException("This coordinate is not valid try and use another one");

            }
        }
    }

    /**
     * this method will check four direction if one position is valid check will
     * complete the capture process after that we just put the chess into that
     * position
     * 
     * @param row from check_input
     * @param col from check_input
     * @return whether this position is good or not
     */
    public int humanTurn(int row, int col) {
        // if the place work , capture immediately
    	int color=0;//0 means white,1 means black
    	if(HumC=='?'||HumC=='W')	
    		color=0;
    	if(HumC=='B')
    		color=1;
        boolean result_one = check_horizontal(color, row, col, 1);
        boolean result_two = check_leftdigonal(color, row, col, 1);
        boolean result_three = check_rightdigonal(color, row, col, 1);
        boolean result_four = check_vertical(color, row, col, 1);
        if (result_one || result_two || result_three || result_four) {
            model.set_position(color, row, col);
            return 0;
        } else
            return 1;

    }

    /**
     * this method will check the all possible spot for computer the check method
     * won't complete capture this time just find the valid place, and we record the
     * max score and position, we used it for check method again, this time the
     * check method not only check but also complete capture and we pass the right
     * message to x and y for give user information
     * 
     * @return whether this position is good or not
     */
    public int computerTurn() {
        // computer will play black
    	int color=1;//0 means white,1 means black
    	System.out.println("the original color is "+ComC);
    	if(ComC=='?'||ComC=='B') {	
    		color=1;
    		System.out.println("first condition "+ComC);
    	}
    	else if(ComC=='W') {
    		color=0;
    		System.out.println("second condition "+ComC);
    	}
        boolean flag = false;
        int max = -100;
        int max_x = 0;
        int max_y = 0;
        for (int i = 0; i < model.getsize() - 1; i++) {
            for (int j = 1; j <= model.getsize() - 1; j++) {
                if (model.get_chess(i, j) == '-') {
                    // i don't need to know which one just call it and it will change the most color
                    boolean result_one;
                    boolean result_two;
                    boolean result_three;
                    boolean result_four;

                        result_one = check_horizontal(color, i, j, 0);
                        result_two = check_leftdigonal(color, i, j, 0);
                        result_three = check_rightdigonal(color, i, j, 0);
                        result_four = check_vertical(color, i, j, 0);
                    

                    if (result_one || result_two || result_three || result_four) {
                        flag = true;
                        char a = (char) (i + 1 + '0');
                        char b = (char) (j - 1 + 'a');
                        System.out.println("The current score is " + current_score + " and it was " + b + a);
                        if (current_score > max) {

                            max_x = i;
                            max_y = j;
                            max = current_score;
                        }
                    }

                }
                current_score = 0;
            }
            //System.out.println("the most value step is"+max);
        }
        // 5-1 +'a' x+1
        // System.out.println(max_x + " and " + max_y);
        // System.out.println(model);
        if (flag) {
            	 check_horizontal(color, max_x, max_y, 1);
                 check_leftdigonal(color, max_x, max_y, 1);
                 check_rightdigonal(color, max_x, max_y, 1);
                 check_vertical(color, max_x, max_y, 1);
                 // System.out.println(model);
                 model.set_position(color, max_x, max_y);
                 current_x = (char) (max_x + 1 + '0');
                 current_y = (char) (max_y - 1 + 'a');
        }
        // System.out.println("The current score is " + current_score + " and it was "
        // +current_y+current_x);
      //  last_turn ^= last_turn;
        current_score = 0;
        if (!flag)
            return 1;
        return 0;

    }

    /**
     * this method to check if there are some place left or not go through all
     * position and see if it is work or not
     * 
     * @param judge complete the capture or not
     * @return whether possible or not
     */
    public int checkAllPossible( int judge) {
    	//int color=0;//0 means white,1 means black
    	//if(HumC=='?'||HumC=='W')	
    		//color=0;
    	//if(HumC=='B')
    		//color=1;
        boolean flag = false;
        for (int i = 0; i < model.getsize() - 1; i++) {
            for (int j = 1; j <= model.getsize() - 1; j++) {
                if (model.get_chess(i, j) == '-') {
                    // i don't need to know which one just call it and it will change the most color
                    boolean result_one;
                    boolean result_two;
                    boolean result_three;
                    boolean result_four;

                        result_one = check_horizontal(judge, i, j, 0);
                        result_two = check_leftdigonal(judge, i, j, 0);
                        result_three = check_rightdigonal(judge, i, j, 0);
                        result_four = check_vertical(judge, i, j, 0);
                    

                    if (result_one || result_two || result_three || result_four) {
                        flag = true;
                    }

                }
            }
        }
        // 5-1 +'a' x+1
        // System.out.println(max_x + " and " + max_y);
        // System.out.println(model);

        if (!flag)
            return 1;
        return 0;

    }

    /**
     * this method check for left diagonal there is two part which is up and down if
     * it is up we check up part if we find the opposite color during the up part we
     * continue until we find the same color we decide complete capture or not if
     * there is '-' we just break, because if is not allowed we count all captured
     * score for computer AI if it the down part we do the same thing just different
     * direction
     * 
     * @param t     color
     * @param x     x position
     * @param y     y position
     * @param judge complete capture or not
     * @return whether the position is good or not
     */

    private boolean check_leftdigonal(int t, int x, int y, int judge) {
        if (model.get_chess(x, y) != '-')
            return false;
        boolean deaful = false;
        // white
        if (t == 0) {
            // up

            if (x - 1 >= 0 && y - 1 >= 1 && model.get_chess(x - 1, y - 1) == 'B') {
                for (int i = 2; x - i >= 0 && y - i >= 1; i++) {

                    if (model.get_chess(x - i, y - i) == 'B')
                        continue;
                    else if (model.get_chess(x - i, y - i) == 'W') {
                        deaful = true;
                        if (judge == 1) {
                            for (int j = 1; j < i; j++) {
                                model.set_position(0, x - i + j, y - i + j);
                            }
                        }
                        if(ComC=='W')
                            current_score += i - 1;
                        break;
                    } else
                        break;
                }
            }
            // down
            if (x + 1 <= 7 && y + 1 <= 8 && model.get_chess(x + 1, y + 1) == 'B') {
                for (int i = 2; x + i <= 7 && y + i <= 8; i++) {

                    if (model.get_chess(x + i, y + i) == 'B')
                        continue;
                    else if (model.get_chess(x + i, y + i) == 'W') {
                        deaful = true;
                        if (judge == 1) {
                            for (int j = 1; j < i; j++) {
                                model.set_position(0, x + i - j, y + i - j);
                            }
                        }
                        if(ComC=='W' )
                            current_score += i - 1;
                        break;
                    } else
                        break;

                }
            }

        }
        // black turn
        if (t == 1) {
            // up
            if (x - 1 >= 0 && y - 1 >= 1 && model.get_chess(x - 1, y - 1) == 'W') {
                for (int i = 2; x - i >= 0 && y - i >= 1; i++) {

                    if (model.get_chess(x - i, y - i) == 'W')
                        continue;
                    else if (model.get_chess(x - i, y - i) == 'B') {
                        deaful = true;
                        if (judge == 1) {
                            for (int j = 1; j < i; j++) {
                                model.set_position(1, x - i + j, y - i + j);
                            }
                        }
                        if(ComC=='B' || ComC=='?')
                            current_score += i - 1;
                        break;
                    } else
                        break;
                }
            }
            // down
            if (x + 1 <= 7 && y + 1 <= 8 && model.get_chess(x + 1, y + 1) == 'W') {
                for (int i = 2; x + i <= 7 && y + i <= 8; i++) {

                    if (model.get_chess(x + i, y + i) == 'W')
                        continue;
                    else if (model.get_chess(x + i, y + i) == 'B') {
                        deaful = true;
                        if (judge == 1) {
                            for (int j = 1; j < i; j++) {
                                model.set_position(1, x + i - j, y + i - j);
                            }
                        }
                        if(ComC=='B' || ComC=='?')
                           current_score += i - 1;
                        break;
                    } else
                        break;

                }
            }

        }
        return deaful;
    }

    /**
     * this method check the position for right diagonal there will be two part for
     * right diagonal first is up, we check the up part, and if there is opposite
     * color we continue until we find the same color, and if we find '-' during the
     * processing we just break because it is not allowed we do the same thing for
     * down side just different direction we also count the current score for
     * computer AI
     * 
     * @param t     color
     * @param x     x position
     * @param y     y position
     * @param judge to finish capture process or not
     * @return whether the position is good or not
     */
    private boolean check_rightdigonal(int t, int x, int y, int judge) {
        if (model.get_chess(x, y) != '-')
            return false;

        // white
        boolean deaful = false;
        if (t == 0) {
            // up
            if (x - 1 >= 0 && y + 1 <= 8 && model.get_chess(x - 1, y + 1) == 'B') {
                for (int i = 2; x - i >= 0 && y + i <= 8; i++) {

                    if (model.get_chess(x - i, y + i) == 'B')
                        continue;
                    else if (model.get_chess(x - i, y + i) == 'W') {
                        deaful = true;
                        if (judge == 1) {

                            for (int j = 1; j < i; j++) {
                                model.set_position(0, x - i + j, y + i - j);
                            }
                        }
                        if(ComC=='W')
                             current_score += i - 1;
                        break;
                    } else
                        break;
                }
            }
            // down
            if (x + 1 <= 7 && y - 1 >= 1 && model.get_chess(x + 1, y - 1) == 'B') {
                for (int i = 2; x + i <= 7 && y - i >= 1; i++) {

                    if (model.get_chess(x + i, y - i) == 'B')
                        continue;
                    else if (model.get_chess(x + i, y - i) == 'W') {
                        deaful = true;
                        if (judge == 1) {
                            for (int j = 1; j < i; j++) {
                                model.set_position(0, x + i - j, y - i + j);
                            }
                        }
                        if(ComC=='W')
                            current_score += i - 1;
                        break;
                    } else
                        break;

                }
            }

        }
        // black turn
        if (t == 1) {
            // up
            if (x - 1 >= 0 && y + 1 <= 8 && model.get_chess(x - 1, y + 1) == 'W') {
                for (int i = 2; x - i >= 0 && y + i <= 8; i++) {

                    if (model.get_chess(x - i, y + i) == 'W')
                        continue;
                    else if (model.get_chess(x - i, y + i) == 'B') {
                        deaful = true;
                        if (judge == 1) {
                            for (int j = 1; j < i; j++) {
                                model.set_position(1, x - i + j, y + i - j);
                            }
                        }
                        if(ComC=='B' || ComC=='?')
                            current_score += i - 1;
                        break;
                    } else
                        break;
                }
            }
            // down
            if (x + 1 <= 7 && y - 1 >= 1 && model.get_chess(x + 1, y - 1) == 'W') {
                for (int i = 2; x + i <= 7 && y - i >= 1; i++) {

                    if (model.get_chess(x + i, y - i) == 'W')
                        continue;
                    else if (model.get_chess(x + i, y - i) == 'B') {
                        deaful = true;
                        if (judge == 1) {
                            for (int j = 1; j < i; j++) {
                                model.set_position(1, x + i - j, y - i + j);
                            }
                        }
                        if(ComC=='B' || ComC=='?')
                            current_score += i - 1;
                        break;
                    } else
                        break;

                }
            }

        }
        return deaful;
    }

    /**
     * this method check the vertical for position there is two part is up and down
     * if it is up, we go through the up part and if we find the opposite color we
     * continue until we find the same color if there is '-' occur we just break,
     * because it is not allowed for the down part we do the same thing just change
     * the direction we also count the current score for computer AI
     * 
     * @param t     color
     * @param x     x position
     * @param y     y position
     * @param judge whether finish the capture or not
     * @return whether this position is good or not
     */
    private boolean check_vertical(int t, int x, int y, int judge) {
        // white

        // if color is white
        boolean deaful = false;
        if (model.get_chess(x, y) != '-')
            return false;

        // white
        if (t == 0) {
            // up
            if (x - 1 >= 0 && model.get_chess(x - 1, y) == 'B') {
                for (int i = x - 2; i >= 0; i--) {

                    if (model.get_chess(i, y) == 'B')
                        continue;
                    else if (model.get_chess(i, y) == 'W') {
                        deaful = true;
                        if (judge == 1) {
                            for (int j = i + 1; j < x; j++)
                                model.set_position(0, j, y);
                        }
                        if(ComC=='W')
                            current_score += x - (i + 1);
                        break;
                    } else
                        break;
                }
            }
            // down
            if (x + 1 <= 7 && model.get_chess(x + 1, y) == 'B') {
                for (int i = x + 2; i <= 7; i++) {

                    if (model.get_chess(i, y) == 'B')
                        continue;
                    else if (model.get_chess(i, y) == 'W') {
                        deaful = true;
                        if (judge == 1) {
                            for (int j = i - 1; j > x; j--) {
                                model.set_position(0, j, y);
                            }
                        }
                        if(ComC=='W')
                              current_score += i - 1 - x;
                        break;
                    } else
                        break;

                }
            }
        }
        // if color is black
        if (t == 1) {
            // up
            if (x - 1 >= 0 && model.get_chess(x - 1, y) == 'W') {
                for (int i = x - 2; i >= 0; i--) {

                    if (model.get_chess(i, y) == 'W')
                        continue;
                    else if (model.get_chess(i, y) == 'B') {
                        // System.out.println("here is"+i+1+" "+x);
                        // System.out.println(judge);
                        deaful = true;
                        if (judge == 1) {

                            for (int j = i + 1; j < x; j++) {
                                // System.out.println("jklp");
                                model.set_position(1, j, y);
                            }
                        }
                        if(ComC=='B' || ComC=='?')
                            current_score += x - (i + 1);
                        break;
                    } else
                        break;
                }
            }
            // down
            if (x + 1 <= 7 && model.get_chess(x + 1, y) == 'W') {
                for (int i = x + 2; i <= 7; i++) {

                    if (model.get_chess(i, y) == 'W')
                        continue;
                    else if (model.get_chess(i, y) == 'B') {
                        deaful = true;
                        if (judge == 1) {
                            for (int j = i - 1; j > x; j--) {
                                model.set_position(1, j, y);
                            }
                        }
                        if(ComC=='B' || ComC=='?')
                            current_score += i - 1 - x;
                        break;
                    } else
                        break;

                }
            }
        }
        return deaful;
    }

    /**
     * this method the horizontal for position there will be two part which is left
     * or right for the left part we go to left side and to see if there are
     * opposite color we continue , until we find same color if there is '-' occurs
     * we just break because it is not allowed for the right part, we do the same
     * thing just change the direction we also count the current score for computer
     * AI
     * 
     * @param t     color
     * @param x     x position
     * @param y     y position
     * @param judge whether finish the capture or not
     * @return whether this position is good or not
     */
    private boolean check_horizontal(int t, int x, int y, int judge) {
        // white

        // if color is white
        boolean deaful = false;
        if (model.get_chess(x, y) != '-')
            return false;

        // white
        if (t == 0) {
            // left
            if (y - 1 >= 1 && model.get_chess(x, y - 1) == 'B') {
                for (int i = y - 2; i >= 1; i--) {

                    if (model.get_chess(x, i) == 'B')
                        continue;
                    else if (model.get_chess(x, i) == 'W') {
                        deaful = true;
                        if (judge == 1) {
                            // System.out.print("abc\n");
                            for (int j = i + 1; j < y; j++) {
                                // System.out.print("abcd\n");
                                model.set_position(0, x, j);
                            }
                        }
                        if(ComC=='W' )
                            current_score += y - (i + 1);
                        break;
                    } else
                        break;
                }
            }
            // right
            if (y + 1 <= 8 && model.get_chess(x, y + 1) == 'B') {
                for (int i = y + 2; i <= 8; i++) {

                    if (model.get_chess(x, i) == 'B')
                        continue;
                    else if (model.get_chess(x, i) == 'W') {
                        deaful = true;
                        if (judge == 1) {
                            for (int j = i - 1; j > y; j--) {
                                model.set_position(0, x, j);
                            }
                        }
                        if(ComC=='W')
                             current_score += i - 1 - y;
                        break;
                    } else
                        break;

                }
            }
        }
        // if color is black
        if (t == 1) {
            // left
            if (y - 1 >= 1 && model.get_chess(x, y - 1) == 'W') {
                for (int i = y - 2; i >= 1; i--) {

                    if (model.get_chess(x, i) == 'W')
                        continue;
                    else if (model.get_chess(x, i) == 'B') {
                        deaful = true;
                        if (judge == 1) {
                            for (int j = i + 1; j < y; j++) {
                                model.set_position(1, x, j);
                            }
                        }
                        if(ComC=='B' || ComC=='?')
                            current_score += y - (i + 1);
                        break;
                    } else
                        break;
                }
            }
            // right
            if (y + 1 <= 8 && model.get_chess(x, y + 1) == 'W') {
                for (int i = y + 2; i <= 8; i++) {

                    if (model.get_chess(x, i) == 'W')
                        continue;
                    else if (model.get_chess(x, i) == 'B') {
                        deaful = true;
                        if (judge == 1) {
                            for (int j = i - 1; j > y; j--) {
                                model.set_position(1, x, j);
                            }
                        }
                        if(ComC=='B' || ComC=='?')
                              current_score += i - 1 - y;
                        break;
                    } else
                        break;

                }
            }
        }
        return deaful;
    }

    /**
     * this method interact with model
     * 
     * @return a string to represent the model
     */

    public String show_image() {
        return model.toString();
    }
}