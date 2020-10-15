import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

/**
 * This class collects all of the test methods for our controller.
 * 
 * In eclipse, running it should run it under JUnit. Running the Reversi class
 * (since it is our main class) will actually run the program. If you're not
 * using eclipse, you'll need to run this under JUnit 5.
 * 
 * @author Wenkai Zheng
 *
 */
class ReversiTest {
	@Test
	/**
	 * this method runs the check_input and i give all invalid position for testing
	 * 
	 * @throws InvalidInputException if the input is not valid
	 */
	void testinput() throws InvalidInputException, InvalidMoveException {

		ReversiModel mo = new ReversiModel();
		ReversiController co = new ReversiController(mo);
		assertThrows(InvalidInputException.class, () -> {
			co.check_input("oog");
		});
		assertThrows(InvalidInputException.class, () -> {
			co.check_input("o");
		});
		assertThrows(InvalidInputException.class, () -> {
			co.check_input("a0");
		});
		assertThrows(InvalidInputException.class, () -> {
			co.check_input("a9");
		});
		assertThrows(InvalidInputException.class, () -> {
			co.check_input("p1");
		});
		assertThrows(InvalidInputException.class, () -> {
			co.check_input("'1");
		});
		assertThrows(InvalidMoveException.class, () -> {
			co.check_input("c1");
		});

	}

	@Test
	/**
	 * this method test humanTurn i give the both valid and invalid position to test
	 * the return value and score
	 */
	void testhumanTurn() {
		ReversiModel mo = new ReversiModel();
		ReversiController co = new ReversiController(mo);
		assertEquals(co.humanTurn(4, 3), 0);
		assertEquals(co.humanTurn(1, 1), 1);

		assertEquals(co.white_score(), 4);
		assertEquals(co.black_score(), 1);

	}

	@Test
	/**
	 * this method test computerTurn i give the both valid and invalid position to
	 * test the return value and score
	 */
	void testComputerTurn() {
		ReversiModel mo = new ReversiModel();
		ReversiController co = new ReversiController(mo);
		assertEquals(co.computerTurn(), 0);
		assertEquals(co.black_score(), 4);
		assertEquals(co.white_score(), 1);

	}

	@Test
	/**
	 * this method testing the score is as excepted
	 * 
	 * @throws InvalidInputException
	 */
	void testwhitescore() throws InvalidInputException, InvalidMoveException {
		ReversiModel mo = new ReversiModel();
		ReversiController co = new ReversiController(mo);
		co.check_input("c5");
		assertEquals(co.white_score(), 4);
	}

	@Test
	/**
	 * this method testing the score is as excepted
	 * 
	 * @throws InvalidInputException
	 */
	void testblackscore() {
		ReversiModel mo = new ReversiModel();
		ReversiController co = new ReversiController(mo);
		co.computerTurn();
		assertEquals(co.black_score(), 4);
	}

	@Test
	/**
	 * this method is using for testing if there is some way for placing chess
	 */
	void testAllpossible() {
		ReversiModel mo = new ReversiModel();
		ReversiController co = new ReversiController(mo);
		assertEquals(co.checkAllPossible(0), 0);
	}

	@Test
	/**
	 * this method is testing the place where computer will go and compare the
	 * message for making sure it is right
	 * 
	 * @throws InvalidInputException
	 */
	void testComputerDone() throws InvalidInputException, InvalidMoveException {
		ReversiModel mo = new ReversiModel();
		ReversiController co = new ReversiController(mo);
		co.check_input("c5");
		co.computerTurn();
		assertEquals(co.getComputerDone(), "The computer put chess in c4");
	}

	@Test
	/**
	 * this method the whole game i put my own chess and then i push computer AI to
	 * play until the game over, i will see who will won the game at last also when
	 * the game end , there should be not possible move for both computer AI and
	 * player
	 * 
	 * @throws InvalidInputException
	 */

	void testWholegame() throws InvalidInputException, InvalidMoveException {
		
		ReversiModel mo = new ReversiModel();
		ReversiController co = new ReversiController(mo);
		assertThrows(InvalidMoveException.class, () -> {co.check_input("e5");});
		assertThrows(InvalidMoveException.class, () -> {co.check_input("f3");});
		co.check_input("c5");
		co.computerTurn();
		co.check_input("f3");
		co.computerTurn();
		co.check_input("c3");
		co.computerTurn();
		co.check_input("a3");
		co.computerTurn();
		co.check_input("d6");
		co.computerTurn();
		co.check_input("e3");
		co.computerTurn();
		co.check_input("c7");
		co.computerTurn();
		co.check_input("b7");
		co.computerTurn();
		assertEquals(co.checkAllPossible(0), 0);
		co.check_input("d3");
		co.computerTurn();
		co.check_input("e1");
		co.computerTurn();
		co.check_input("c1");
		co.computerTurn();
		co.check_input("a2");
		co.computerTurn();
		co.check_input("f5");
		co.computerTurn();
		assertEquals(co.checkAllPossible(0), 0);
		co.check_input("f1");
		co.computerTurn();
		assertEquals(co.checkAllPossible(0), 0);
		co.check_input("d7");
		co.computerTurn();
		assertEquals(co.checkAllPossible(0), 0);
		co.check_input("e8");
		co.computerTurn();
		assertEquals(co.checkAllPossible(0), 0);
		co.check_input("b8");
		co.computerTurn();
		assertEquals(co.checkAllPossible(0), 0);
		co.check_input("h5");
		co.computerTurn();
		assertEquals(co.checkAllPossible(0), 0);
		co.check_input("f6");
		co.computerTurn();
		assertEquals(co.checkAllPossible(0), 0);
		co.check_input("d1");
		co.computerTurn();
		assertEquals(co.checkAllPossible(0), 0);
		co.check_input("h8");
		co.computerTurn();
		co.check_input("a7");
		co.computerTurn();
		co.check_input("b3");
		co.computerTurn();
		co.check_input("a6");
		co.computerTurn();
		co.check_input("g5");
		co.computerTurn();
		co.check_input("g2");
		co.computerTurn();
		co.check_input("g6");
		co.computerTurn();
		co.check_input("e7");
		co.computerTurn();
		co.check_input("h1");
		co.computerTurn();
		co.check_input("g8");
		co.computerTurn();
		assertEquals(co.show_image().charAt(8), 'B');
		assertEquals(co.show_image().charAt(16), 'W');
		assertEquals(co.checkAllPossible(0), 1);
		assertEquals(co.computerTurn(), 1);

	}

	@Test
	/**
	 * this method the whole game i put my own chess and then i push computer AI to
	 * play until the game over, i will see who will won the game at last also when
	 * the game end , there should be not possible move for both computer AI and
	 * player
	 * 
	 * @throws InvalidInputException
	 */
	void testWholegame2() throws InvalidInputException, InvalidMoveException {
		
		ReversiModel mo = new ReversiModel();
		mo.set_position(1, 0, 2);
		mo.set_position(1, 0, 3);
		mo.set_position(1, 0, 4);
		mo.set_position(1, 0, 5);
		mo.set_position(1, 0, 6);
		mo.set_position(1, 0, 7);
		mo.set_position(1, 0, 8);
		
		ReversiController co = new ReversiController(mo);
		assertThrows(InvalidMoveException.class, () -> {co.check_input("a1");});
		//System.out.println(co.show_image());

		// assertThrows(InvalidMoveException.class, () -> {co.check_input("b4");});
     
	}
	@Test
	/**
	 * this method the whole game i put my own chess and then i push computer AI to
	 * play until the game over, i will see who will won the game at last also when
	 * the game end , there should be not possible move for both computer AI and
	 * player
	 * 
	 * @throws InvalidInputException
	 */
	void testWholegame3() throws InvalidInputException, InvalidMoveException {
		ReversiModel mo = new ReversiModel();
		mo.set_position(1, 4, 7);
		mo.set_position(1, 3, 8);
		
		
		
		ReversiController co = new ReversiController(mo);
		//System.out.println(co.show_image());
		assertThrows(InvalidMoveException.class, () -> {co.check_input("f6");});
		//System.out.println(co.show_image());

		// assertThrows(InvalidMoveException.class, () -> {co.check_input("b4");});

	}
	@Test
	/**
	 * this method the whole game i put my own chess and then i push computer AI to
	 * play until the game over, i will see who will won the game at last also when
	 * the game end , there should be not possible move for both computer AI and
	 * player
	 * 
	 * @throws InvalidInputException
	 */
	void testWholegame4() throws InvalidInputException, InvalidMoveException {
		ReversiModel mo = new ReversiModel();
		mo.set_position(0, 5, 3);
		mo.set_position(1, 1, 7);
		mo.set_position(0, 0, 8);
		
		
		
		
		ReversiController co = new ReversiController(mo);
		//System.out.println(co.show_image());
		co.check_input("f3");
		//System.out.println(co.show_image());
		assertEquals(co.computerTurn(),1);

		// assertThrows(InvalidMoveException.class, () -> {co.check_input("b4");});

	}
	@Test
	/**
	 * this method the whole game i put my own chess and then i push computer AI to
	 * play until the game over, i will see who will won the game at last also when
	 * the game end , there should be not possible move for both computer AI and
	 * player
	 * 
	 * @throws InvalidInputException
	 */
	void testWholegame5() throws InvalidInputException, InvalidMoveException {
		ReversiModel mo = new ReversiModel();
		mo.set_position(0, 0, 1);
		mo.set_position(1, 1, 2);
		mo.set_position(1, 3, 4);
		
		
		
		
		ReversiController co = new ReversiController(mo);
		//System.out.println(co.show_image());
		co.check_input("c3");
		//System.out.println(co.show_image());
		//assertEquals(co.computerTurn(),1);

		// assertThrows(InvalidMoveException.class, () -> {co.check_input("b4");});

	}
	@Test
	/**
	 * this method the whole game i put my own chess and then i push computer AI to
	 * play until the game over, i will see who will won the game at last also when
	 * the game end , there should be not possible move for both computer AI and
	 * player
	 * 
	 * @throws InvalidInputException
	 */
	void testWholegame6() throws InvalidInputException, InvalidMoveException {
		ReversiModel mo = new ReversiModel();
		mo.set_position(0, 7, 4);
		mo.set_position(1, 6, 4);
		//mo.set_position(1, 3, 4);
		
		
		
		
		ReversiController co = new ReversiController(mo);
		//System.out.println(co.show_image());
		co.check_input("d6");
		//System.out.println(co.show_image());
		//assertEquals(co.computerTurn(),1);

		// assertThrows(InvalidMoveException.class, () -> {co.check_input("b4");});

	}
	@Test
	/**
	 * this method the whole game i put my own chess and then i push computer AI to
	 * play until the game over, i will see who will won the game at last also when
	 * the game end , there should be not possible move for both computer AI and
	 * player
	 * 
	 * @throws InvalidInputException
	 */
	void testWholegame7() throws InvalidInputException, InvalidMoveException {
		ReversiModel mo = new ReversiModel();
		mo.set_position(0, 4, 1);
		mo.set_position(1, 4, 2);
		//mo.set_position(1, 3, 4);
		
		
		
		
		ReversiController co = new ReversiController(mo);
		
		co.check_input("c5");
		
		//assertEquals(co.computerTurn(),1);

		// assertThrows(InvalidMoveException.class, () -> {co.check_input("b4");});

	}
}
