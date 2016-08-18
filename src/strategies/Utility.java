package strategies;

import main_components.BitFunctions;
import main_components.Board;
import main_components.Color;

/**
 * \brief
 * Calculates the "utility" of a Board based on different "evaluation functions"
 * @author Rodney Shaghoulian
 */
public class Utility {
	
	public Board board;	///< The Board that we are calculating the "utility" for.
	public int value;	///< The utility value of the Board
	
	/**
	 * Constructor - Initializes variables
	 * @param board		The Board we are calculating the utility for.
	 */
	public Utility(Board board){
		this.board = board;
		this.value = 0;
	}
	
	/**
	 * Calculates utility based on the difference of player scores.
	 */
	public void utilityScoreDiff(){
		if (board.gameEnded)
			gameEndedUtility();
		else
			value = board.blackPlayer.score - board.whitePlayer.score;
	}
	
	/**
	 * Calculates utility based on the number of corners owned by each Player.
	 */
	public void utilityCorners(){
		if (board.gameEnded)
			gameEndedUtility();
		else
			value = board.cornersOwned(Color.BLACK) - board.cornersOwned(Color.WHITE);
	}
	
	/**
	 * Calculates utility based on valid moves remaining for each player. More moves equals greater utility
	 */
	public void utilityValidMoves(){
		if (board.gameEnded)
			gameEndedUtility();
		else
			value = BitFunctions.cardinality(board.blackPlayer.validMoves) - BitFunctions.cardinality(board.whitePlayer.validMoves);
	}
	
	/**
	 * Calculates utility based on valid moves remaining for each player. More moves equals greater utility \n
	 * Also takes into account number of corners owned.
	 */
	public void utilityValidMovesAndCorners(){
		if (board.gameEnded)
			gameEndedUtility();
		else{
			int utilityBlack = BitFunctions.cardinality(board.blackPlayer.validMoves) + 50 * board.cornersOwned(Color.BLACK);
			int utilityWhite = BitFunctions.cardinality(board.whitePlayer.validMoves) + 50 * board.cornersOwned(Color.WHITE);
			value = utilityBlack - utilityWhite;
		}
	}
	
	/**
	 * Calculates utility based on valid moves remaining for each player. More moves equals greater utility \n
	 * Also takes into account number of 'C squares' owned.
	 */
	public void utilityValidMovesAndCSquares(){
		if (board.gameEnded)
			gameEndedUtility();
		else{
			int utilityBlack = BitFunctions.cardinality(board.blackPlayer.validMoves) - 5 * board.cSquaresOwned(Color.BLACK);
			int utilityWhite = BitFunctions.cardinality(board.whitePlayer.validMoves) - 5 * board.cSquaresOwned(Color.WHITE);
			value = utilityBlack - utilityWhite;
		}
	}
	
	/**
	 * Calculates utility based on valid moves remaining for each player. More moves equals greater utility \n
	 * Also takes into account X squares.
	 */
	public void utilityValidMovesAndXSquares(){
		if (board.gameEnded)
			gameEndedUtility();
		else{
			int utilityBlack = BitFunctions.cardinality(board.blackPlayer.validMoves) - 20 * board.xSquaresOwned(Color.BLACK);
			int utilityWhite = BitFunctions.cardinality(board.whitePlayer.validMoves) - 20 * board.xSquaresOwned(Color.WHITE);
			value = utilityBlack - utilityWhite;
		}
	}
	
	/**
	 * Calculates utility based on valid moves remaining for each player. More moves equals greater utility \n
	 * Also takes into account X squares, C squares, and corners.
	 */
	public void utilityComplex(){
		if (board.gameEnded)
			gameEndedUtility();
		else{
			int utilityBlack = BitFunctions.cardinality(board.blackPlayer.validMoves) - 5 * board.cSquaresOwned(Color.BLACK) 
				             - 20 * board.xSquaresOwned(Color.BLACK) + 50 * board.cornersOwned(Color.BLACK);
			int utilityWhite = BitFunctions.cardinality(board.whitePlayer.validMoves) - 5 * board.cSquaresOwned(Color.WHITE) 
					         - 20 * board.xSquaresOwned(Color.WHITE) + 50 * board.cornersOwned(Color.WHITE);
			value = utilityBlack - utilityWhite;
		}
	}
	
	/**
	 * Assigns a utility of 1000 for BLACK win and -1000 for WHITE win.
	 */
	public void gameEndedUtility(){
		if (board.gameEnded){
			if (board.winner == Color.BLACK)
				value = 10000; //pretty arbitrary. Just has to be bigger than Absolute Value of inner nodes
			else if (board.winner == Color.WHITE)
				value = -10000;  //pretty arbitrary. Just has to be bigger than Absolute Value of inner nodes
			else
				value = 0;
		}
	}
}
