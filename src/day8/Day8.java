package day8;

import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.stream.Collectors;

public class Day8 {
	
	class View {
		int leftViewDist;
		int topViewDist;
		int rightViewDist;
		int bottomViewDist;
		
		public int getScenicScore() {
			return leftViewDist * topViewDist * rightViewDist * bottomViewDist;
		}
		
		@Override
		public String toString() {
			return "[" + leftViewDist + ", " + topViewDist + ", " + rightViewDist + ", " + bottomViewDist + "]";
		}
	}
	
	public static void main(String[] args) throws IOException, URISyntaxException {
		List<String> lines = Files.lines(Path.of(Day8.class.getResource("input.txt").toURI())).collect(Collectors.toList());
		Day8 day8 = new Day8();
		int[][] grid = setupData(lines);
		int count = countAllVisibleTrees(grid);
		System.out.println("All visible tree count=" + count);
		int bestScore = day8.findBestScenicScore(grid);
		System.out.println("Best Scenic Score=" + bestScore);
	}

	private  int findBestScenicScore(int[][] grid) {
		int viewLeft = 0;
		int viewTop = 0;
		int viewRight = 0;
		int viewBottom = 0;
		View[][] viewGrid = new View[grid.length][grid[0].length];
		for (int i = 0; i < grid.length; i++) {
			int colSize = grid[i].length;
			for (int j = 0; j < colSize; j++) {
				int tree = grid[i][j];
				View view = viewGrid[i][j];
				if (view == null) view = new View();
				if (j == 0) {
					view.leftViewDist = 0;
				} else {
					int row = i;
					int col = j - 1;
					while (col >= 0) {
						int currTree = grid[row][col];
						if (currTree < tree) {
							viewLeft++;
							col--;
						} else {
							viewLeft++;
							break;
						}
					}
					view.leftViewDist = viewLeft;
					viewLeft = 0;
				}
				if (i == 0) {
					view.topViewDist = 0;
				} else {
					int row = i - 1;
					int col = j;
					while (row >= 0) {
						int currTree = grid[row][col];
						if (currTree < tree) {
							viewTop++;
							row--;
						} else {
							viewTop++;
							break;
						}
					}
					view.topViewDist = viewTop;
					viewTop = 0;
				}
				if (i == grid.length - 1) {
					view.rightViewDist = 0;
				} else {
					int row = i;
					int col = j + 1;
					while (col < grid[i].length) {
						int currTree = grid[row][col];
						if (currTree < tree) {
							viewRight++;
							col++;
						} else {
							viewRight++;
							break;
						}
					}
					view.rightViewDist = viewRight;
					viewRight = 0;
				}
				if (j == grid.length - 1) {
					view.bottomViewDist = 0;
				} else {
					int row = i + 1;
					int col = j;
					while (row < grid.length) {
						int currTree = grid[row][col];
						if (currTree < tree) {
							viewBottom++;
							row++;
						} else {
							viewBottom++;
							break;
						}
					}
					view.bottomViewDist = viewBottom;
					viewBottom = 0;
				}
				viewGrid[i][j] = view;
//				System.out.println("{i,j}=" + i + ", " + j + "; " + view);
			}
			viewLeft = 0;
			viewTop = 0;
			viewRight = 0;
			viewBottom = 0;
		}
		View best = null;
		int x = 0;
		int y = 0;
		int bestScore = 0;
		for (int i = 0; i < viewGrid.length; i++) {
			for (int j = 0; j < viewGrid[i].length; j++) {
				int score = viewGrid[i][j].getScenicScore();
				if (score > bestScore) {
					best = viewGrid[i][j];
					bestScore = best.getScenicScore();
					x = i;
					y = j;
				}
			}
		}
		System.out.println("(i,j)=" + x + ", " + y + ", value=" + grid[x][y] + ". " + best);
		return bestScore;
	}

	private static int countAllVisibleTrees(int[][] grid) {
		int count = 0;
		int highestLeft = 0;
		int highestTop = 0;
		int highestRight = 0;
		int highestBottom = 0;
		boolean[][] visible = new boolean[grid.length][grid[0].length];
		for (int i = 0; i < grid.length; i++) {
			int colSize = grid[i].length;
			for (int j = 0; j < colSize; j++) {
				// left to right (left visible check)
				int h1 = grid[i][j];
				if (h1 > highestLeft || j == 0) {
					highestLeft = h1;
					visible[i][j] = true;
				}
				// top to bottom (top visible check)
				int h2 = grid[j][i];
				if (h2 > highestTop || i == 0) {
					highestTop = h2;
					visible[j][i] = true;
				}
				// right to left (right visible check)
				int col3 = colSize - j - 1;
				int h3 = grid[i][col3];
				if (h3 > highestRight || col3 == colSize - 1) {
					highestRight = h3;
					visible[i][col3] = true;
				}
				// bottom to top (bottom visible check)
				int row4 = grid.length - j - 1;
				int h4 = grid[row4][i];
				if (h4 > highestBottom || i == grid.length - 1) {
					highestBottom = h4;
					visible[row4][i] = true;
				}
			}
			highestLeft = 0;
			highestTop = 0;
			highestRight = 0;
			highestBottom = 0;
		}
		for (int i = 0; i < visible.length; i++) {
			for (int j = 0; j < visible[i].length; j++) {
				if (visible[i][j]) count++;
			}
		}
		return count;
	}

	private static int[][] setupData(List<String> lines) {
		int[][] grid = null;
		int count = 0;
		for (String line : lines) {
			int size = line.length();
			if (grid == null) grid = new int[size][size];
			for (int i = 0; i < size; i++) {
				grid[count][i] = Integer.valueOf("" + line.charAt(i));
			}
			count++;
		}
		return grid;
	}
}
