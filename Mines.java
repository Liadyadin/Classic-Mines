package mines;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Mines {
	public OneMines[][] board;
	int width, height, numMines;
	static Random rand = new Random();

	public Mines(int height, int width, int numMines) { // constructor for Mines class
		this.height = height;
		this.width = width;
		this.numMines = numMines;
		board = new OneMines[height][width]; // new board of play
		for (int i = 0; i < board.length; i++) // initializing of OneMines values (in the inner class)
			for (int j = 0; j < board[i].length; j++)
				board[i][j] = new OneMines(i, j);
		for (int i = 0; i < numMines; i++) { //for random mines in the board
			int x = rand.nextInt(height);
			int y = rand.nextInt(width);
			board[x][y].bomb = true;
		}
		for (int i = 0; i < board.length; i++) // initializing array list of the neighbors
			for (int j = 0; j < board[i].length; j++)
				board[i][j].neighb = board[i][j].neigbors(i, j);
	}

	public class OneMines { // inner class for one place in the array
		List<OneMines> neighb = new ArrayList<>(); // Array list for all the neighbors
		private int i, j;
		boolean bomb = false; // if there is bomb
		boolean openS = false; // if the place is open
		 boolean flag = false; // if there is in this place a flag
		private boolean showAll = false; // if we called setShowAll method

		public OneMines(int i, int j) { // constructor of one place
			this.i = i;
			this.j = j;
		}

		public int Mine_Num() { // method for count how many bombs there is near the place(of all the neighbors)
			int cnt = 0;
			for (OneMines element : neighb) {
				if (element.i >= 0 && element.j >= 0 && element.i < height && element.j < width)
					if (board[element.i][element.j].bomb)
						cnt++;
			}

			return cnt;
		}

		public List<OneMines> neigbors(int i, int j) { // creating the ArrayList of neighbors
			if (j - 1 >= 0 && i - 1 >= 0)
				neighb.add(board[i - 1][j - 1]);
			if (j - 1 >= 0 && i == this.i)
				neighb.add(board[i][j - 1]);
			if (j - 1 >= 0 && i + 1 < height)
				neighb.add(board[i + 1][j - 1]);
			if (j + 1 < width && i - 1 >= 0)
				neighb.add(board[i - 1][j + 1]);
			if (j + 1 < width && i == this.i)
				neighb.add(board[i][j + 1]);
			if (j + 1 < width && i + 1 < height)
				neighb.add(board[i + 1][j + 1]);
			if (j == this.j && i - 1 >= 0)
				neighb.add(board[i - 1][j]);
			if (j == this.j && i + 1 < height)
				neighb.add(board[i + 1][j]);
			return neighb;

		}

		public String toString() {
			return get(i, j); // calling get for per place
		}

	} // end of inner class

	public boolean addMine(int i, int j) { // for add a mine

		if (board[i][j].bomb)
			return true;
		board[i][j].bomb = true;
		numMines++;
		return false;
	}

	public boolean open(int i, int j) { // for open this place - recursive method
		List<OneMines> n1 = new ArrayList<>();
		n1 = board[i][j].neighb;
		if (board[i][j].bomb)
			return false;
		board[i][j].openS = true;
		if (board[i][j].Mine_Num() == 0) {
			for (OneMines element : n1) {
				if (!element.openS) {
					open(element.i, element.j);
				}
			}
		}
		return true;

	}

	public void toggleFlag(int x, int y) { // put a flag in the correlate place or remove flag
		if (board[x][y].flag)
			board[x][y].flag = false;
		else
			board[x][y].flag = true;
	}

	public boolean isDone() { // return true if all the places that not mines is open
		for (int i = 0; i < board.length; i++)
			for (int j = 0; j < board[i].length; j++)
				if (!board[i][j].bomb)
					if (!board[i][j].openS)
						return false;
		return true;
	}

	public String get(int i, int j) { // return string of one place
		String s = "";
		if (board[i][j].openS || board[i][j].showAll) { // if showAll is true also we need this condition
			if (board[i][j].bomb)
				return "X";
			else if (board[i][j].Mine_Num() == 0)
				return " ";
			else
				return s + board[i][j].Mine_Num();
		} else if (board[i][j].flag)
			return "F";
		else
			return ".";

	}

	public void setShowAll(boolean showAll) { // detrmine the value of showAll
		for (int i = 0; i < board.length; i++)
			for (int j = 0; j < board[i].length; j++)
				board[i][j].showAll = showAll;

	}

	public String toString() { // tostring of all the board
		String s = "";
		for (int i = 0; i < board.length; i++) {
			for (int j = 0; j < board[i].length; j++) {
				s += board[i][j].toString();
			}
			s += "\n";
		}
		return s;
	}
}
