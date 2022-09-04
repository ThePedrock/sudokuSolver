package src.sudokuSolver;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class main {
	public static int deep = 0;
	public static void main(String[] args) {
		char[][][] incompleteSudokus = new char[][][] {
			{{'5','3','.','.','7','.','.','.','.'},{'6','.','.','1','9','5','.','.','.'},{'.','9','8','.','.','.','.','6','.'},{'8','.','.','.','6','.','.','.','3'},{'4','.','.','8','.','3','.','.','1'},{'7','.','.','.','2','.','.','.','6'},{'.','6','.','.','.','.','2','8','.'},{'.','.','.','4','1','9','.','.','5'},{'.','.','.','.','8','.','.','7','9'}},
				{{'.','.','9','7','4','8','.','.','.'},{'7','.','.','.','.','.','.','.','.'},{'.','2','.','1','.','9','.','.','.'},{'.','.','7','.','.','.','2','4','.'},{'.','6','4','.','1','.','5','9','.'},{'.','9','8','.','.','.','3','.','.'},{'.','.','.','8','.','3','.','2','.'},{'.','.','.','.','.','.','.','.','6'},{'.','.','.','2','7','5','9','.','.'}},
				{{'.','.','.','2','.','.','.','6','3'},{'3','.','.','.','.','5','4','.','1'},{'.','.','1','.','.','3','9','8','.'},{'.','.','.','.','.','.','.','9','.'},{'.','.','.','5','3','8','.','.','.'},{'.','3','.','.','.','.','.','.','.'},{'.','2','6','3','.','.','5','.','.'},{'5','.','3','7','.','.','.','.','8'},{'4','7','.','.','.','1','.','.','.'}},
				{{'.','.','9','.','.','.','.','.','1'},{'3','.','.','.','2','6','8','.','.'},{'.','.','4','.','.','.','.','.','.'},{'.','9','.','.','8','.','.','.','2'},{'7','1','.','.','.','.','.','5','.'},{'.','4','2','9','.','.','.','.','3'},{'.','.','.','.','4','1','9','.','.'},{'.','.','7','8','.','.','5','.','.'},{'.','.','.','5','.','.','.','.','.'}}
		};

		for (int i=0; i<incompleteSudokus.length; i++) {
			solveSudoku(incompleteSudokus[i]);
		}
	}
    public static int convert(int[] nums) {
    	Integer[] boxedArray = IntStream.of(nums).boxed().toArray(Integer[]::new);
    	Set<Integer> set = IntStream.of(nums).boxed().collect(Collectors.toSet());
    	//or if you need a HashSet specifically
    	HashSet<Integer> hashset = IntStream.of(nums).boxed()
    	    .collect(Collectors.toCollection(HashSet::new));
    	ArrayList<Integer> list = (ArrayList<Integer>) Arrays.stream(nums).boxed().collect(Collectors.toList());
    	List<Integer> list2 = Arrays.stream(nums).boxed().toList();
    	
    	return 0;
    }
    public static void solveSudoku(char[][] board) {
    	Box[][] sudokuBoard = prepareSudoku(board);
		System.out.println("Let's solve...");
    	printSudoku(sudokuBoard);
    	sudokuBoard = tryAndSolveSodoku(sudokuBoard);
    	if (sudokuBoard==null) { System.out.println("Could not find solution."); System.exit(0); }
    	printSudoku(sudokuBoard);
    	char[][] newBoard = sudokuToCharArray(sudokuBoard);
        copyToCharMatrix(newBoard, board);
    }
    public static Box[][] prepareSudoku(char[][] board) {
    	Box[][] sudokuBoard = new Box[board.length][board[0].length];
    	for (int i=0; i<board.length; i++) {
    		for (int j=0; j<board.length; j++) {
    			if (board[i][j]=='.') {
    				sudokuBoard[i][j] = new Box();
    			} else {
    				sudokuBoard[i][j] = new Box(Character.getNumericValue(board[i][j]));
    			}
    		}
    	}
    	return sudokuBoard;
    }
    
    public static int foundNumbers(Box[][] aBoard) {
    	int count = 0;
    	for (int i=0; i<aBoard.length; i++) {
    		for (int j=0; j<aBoard[0].length; j++) {
    			if (aBoard[i][j].isClosed()) {
    				count++;
    			}
    		}
    	}
    	return count;
    }
    public static int countBoxesWithTwoPos(Box[][] aBoard) {
    	int count = 0;
    	for (int i=0; i<aBoard.length; i++) {
    		for (int j=0; j<aBoard[0].length; j++) {
    			if (aBoard[i][j].getValue().size()==2) {
    				count++;
    			}
    		}
    	}
    	return count;   	
    }
    public static void printSudoku(Box[][] myBoard) {
		for (int i=0; i<myBoard.length; i++) {
			System.out.print("|");
			for(int j=0; j<myBoard[0].length; j++) {
				System.out.print(myBoard[i][j].getPrintValue() + "|");
			}
			System.out.println("");
		}
		System.out.println("-------------");
    }
    public static void printSudoku(char[][] myBoard) {
		for (int i=0; i<myBoard.length; i++) {
			System.out.print("|");
			for(int j=0; j<myBoard[0].length; j++) {
				System.out.print(myBoard[i][j] + "|");
			}
			System.out.println("");
		}   	
    }
    public static void copyToCharMatrix(char[][] mine, char[][] received) {
        for (int i=0; i<received.length; i++) {
            for (int j=0; j<received[0].length; j++) {
                received[i][j]=mine[i][j];
            }
        }
    }
    public static char[][] sudokuToCharArray(Box[][] myBoard) {
    	char[][] charSudoku = new char[myBoard.length][myBoard[0].length];
    	for (int i=0; i<myBoard.length; i++) {
    		for (int j=0; j<myBoard[0].length; j++) {
    			if (myBoard[i][j].isClosed()) {
    				charSudoku[i][j] = (char)(myBoard[i][j].getFirstValue()+'0');
    			} else {
    				charSudoku[i][j] = '.';
    			}
    		}
    	}
    	return charSudoku;
    }
    public static boolean isSudokuWrong(Box[][] myBoard) {
    	for (int i=0; i<myBoard.length; i++) {
    		for (int j=0; j<myBoard.length; j++) {
    			if (myBoard[i][j].getValue().isEmpty()) { return true; }
    		}
    	}
    	return false;
    }
    public static boolean isSudokuComplete(Box[][] myBoard) {
    	for (int i=0; i<myBoard.length; i++) {
    		for (int j=0; j<myBoard.length; j++) {
    			if (!myBoard[i][j].isClosed()) { return false; }
    		}
    	}
    	return true;
    }  
    public static Box[][] cloneBoard(Box[][] myBoard) {
    	Box[][] newBoard = new Box[myBoard.length][myBoard[0].length];
    	for (int i=0; i<myBoard.length; i++) {
    		for (int j=0; j<myBoard.length; j++) {
    			newBoard[i][j] = myBoard[i][j].clone();
    		}
    	}
    	return newBoard;
    }
    
    /////// 04/09/2022 ///////
    
    public static Box[][] tryAndSolveSodoku(Box[][] aBoard) {
    	return guessNumberAndTry(cloneBoard(aBoard), new int[] {0,0}, 0);
    }
    public static Box[][] screening(Box[][] aBoard) {
    	int prevCheck = 0;
    	int postCheck = -1;
    	while(prevCheck!=postCheck) {
    		prevCheck=foundNumbers(aBoard);
    		
    		for (int i=0; i<aBoard.length; i++) {
    			for (int j=0; j<aBoard[0].length; j++) {
    				if (aBoard[i][j].isClosed()) {
    					int boxValue = aBoard[i][j].getFirstValue();
    					HashSet<Integer> horiSet = new HashSet<Integer>() {{
    						 add(0); add(1); add(2); add(3); add(4); add(5); add(6); add(7); add(8);
    					}};
    					HashSet<Integer> vertSet = new HashSet<Integer>() {{
    						add(0); add(1); add(2); add(3); add(4); add(5); add(6); add(7); add(8);
    					}};
    					horiSet.remove(i); vertSet.remove(j);
    					Integer[] horizontal = new Integer[horiSet.size()]; horiSet.toArray(horizontal);
    					Integer[] vertical = new Integer[vertSet.size()]; vertSet.toArray(vertical);
    					
    					for (int k=0; k<horizontal.length; k++) {
    						aBoard[horizontal[k]][j].filter(boxValue);
    					}
    					for (int k=0; k<vertical.length; k++) {
    						aBoard[i][vertical[k]].filter(boxValue);
    					}
    					
    					// Quadrant // 
    					horizontal = null; vertical = null;
    					switch(i%3) {
    					case 0:
    						horizontal = new Integer[] {i,i+1,i+2};
    						break;
    					case 1:
    						horizontal = new Integer[] {i-1,i,i+1};
    						break;
    					case 2:
    						horizontal = new Integer[] {i-2,i-1,i};
    						break;
    					}
    					switch(j%3) {
    					case 0:
    						vertical = new Integer[] {j,j+1,j+2};
    						break;
    					case 1:
    						vertical = new Integer[] {j-1,j,j+1};
    						break;
    					case 2:
    						vertical = new Integer[] {j-2,j-1,j};
    						break;
    					}
    					
    					for (int k=0; k<horizontal.length; k++) {
    						for (int l=0; l<vertical.length; l++) {
    							if(i!=horizontal[k] || j!=vertical[l]) { aBoard[horizontal[k]][vertical[l]].filter(boxValue); }
    						}
    					}
    					
    				}
    			}
    		}
    		
    		postCheck=foundNumbers(aBoard);
    	}
    	return aBoard;
    }
    public static int boardStatus(Box[][] aBoard) {
    	if (aBoard==null) { return -1; }
    	if (isSudokuComplete(aBoard)) { return 1; }
    	if (isSudokuWrong(aBoard)) { return -1; }
    	return 0;
    }
    public static int[] nextNumberToGuess(Box[][] aBoard) {
    	int[] result = new int[] {0,0,0};
    	int possibilities = 9;
    	for (int i=0; i<aBoard.length; i++) {
    		for (int j=0; j<aBoard[0].length; j++) {
    			if (aBoard[i][j].getValue().size()>1 && aBoard[i][j].getValue().size()<possibilities) {
    				possibilities = aBoard[i][j].getValue().size();
    				result[0] = i; result[1] = j; result[2] = aBoard[i][j].getValue().iterator().next();
    				if ( possibilities == 2 ) { return result; }
    			}
    		}
    	}
    	return result;
    }
    public static Box[][] guessNumberAndTry(Box[][] aBoard, int[] coords, int guessedNumber) {
    	Box[][] myBoard = cloneBoard(aBoard);
    	if (guessedNumber>0) { myBoard[coords[0]][coords[1]].setValue(guessedNumber); }
    	myBoard = screening(myBoard); 
    	if (isSudokuComplete(myBoard)) { return myBoard; }
    	switch(boardStatus(myBoard)) {
    		case -1:
    			return null;
    		case 0:
    			int[] nextNumber = nextNumberToGuess(myBoard);
    			while (nextNumber[2]!=0) {
    				Box[][] newGuess = guessNumberAndTry(cloneBoard(myBoard), new int[] {nextNumber[0], nextNumber[1]}, nextNumber[2]);
    				switch(boardStatus(newGuess)) {
    					case 0,1:
    						return newGuess;
    					default:
    						myBoard[nextNumber[0]][nextNumber[1]].filter(nextNumber[2]);
    						myBoard = screening(myBoard);
    	    				if (isSudokuComplete(myBoard)) { return myBoard; }
    	    				nextNumber = nextNumberToGuess(myBoard);
    				}
    			}
    			return null;
    		case 1:
    			return myBoard;
    		default:
    			return null;
    	}
    }
}
