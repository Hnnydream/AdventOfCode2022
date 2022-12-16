package day9;

import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import com.sun.tools.javac.util.Pair;

public class Day9 {
	
	private static final String LEFT = "L";
	private static final String DOWN = "D";
	private static final String RIGHT = "R";
	private static final String UP = "U";

	public static void main(String[] args) throws IOException, URISyntaxException {
		List<String> lines = Files.lines(Path.of(Day9.class.getResource("input.txt").toURI())).collect(Collectors.toList());
		Day9 day9 = new Day9();
		int visitedCount = day9.findTailVisitedCount(lines);
//		int visitedCount = day9.findTailVisitedCount(lines, 2);
		System.out.println("2 knots Tail visited position count=" + visitedCount);
		int visitedCount2 = day9.findTailVisitedCount(lines, 10);
		System.out.println("10 knots Tail visited position count=" + visitedCount2);
	}

	private int findTailVisitedCount(List<String> lines, int size) {
		int count = 0;
		Map<Integer, Set<Integer>> tailMap = new HashMap<>();
		// starting at (0, 0)
		int hx = 0;
		int hy = 0;
		int tx = 0;
		int ty = 0;
		Set<Integer> visitedTYs = new HashSet<Integer>();
		visitedTYs.add(ty);
		tailMap.put(tx, visitedTYs);
		Map<Integer, Pair<Integer, Integer>> knots = new HashMap<>();
		for (int j = 0; j < size; j++) {
			knots.put(j, new Pair<Integer, Integer>(0,0));
		}
		for (String line : lines) {
			String[] args = line.split("\\s+");
			String direction = args[0];
			int steps = Integer.valueOf(args[1]);
			for (int i = 0; i < steps; i++) {
				for (int j = 0; j < size - 1; j++) {
					Pair<Integer, Integer> head = knots.get(j);
					Pair<Integer, Integer> tail = knots.get(j + 1);
					hx = head.fst;
					hy = head.snd;
					tx = tail.fst;
					ty = tail.snd;
					if (j == 0) {
						if (UP.equals(direction)) {
							hy++;
						} else if (RIGHT.equals(direction)) {
							hx++;
						} else if (DOWN.equals(direction)) {
							hy--;
						} else if (LEFT.equals(direction)) {
							hx--;
						}
					}
					if (Math.abs(hy - ty) > 1 && Math.abs(hx - tx) > 1) {
						if (hy > ty) ty++; else ty--;
						if (hx > tx) tx++; else tx--;
					} else if (Math.abs(hy - ty) > 1) {
						if (hy > ty) ty++; else ty--;
						if (Math.abs(hx - tx) == 1) {
							if (hx > tx) tx++; else tx--;
						}
					} else if (Math.abs(hx - tx) > 1) {
						if (hx > tx) tx++; else tx--;
						if (Math.abs(hy - ty) == 1) {
							if (hy > ty) ty++; else ty--;
						}
					}
					knots.put(j, new Pair<>(hx, hy));
					knots.put(j+1, new Pair<>(tx,ty));
				}
				if (tailMap.containsKey(tx)) {
					visitedTYs = tailMap.get(tx);
					visitedTYs.add(ty);
					tailMap.put(tx, visitedTYs);
				} else {
					visitedTYs = new HashSet<Integer>();
					visitedTYs.add(ty);
					tailMap.put(tx, visitedTYs);
				}
			}
		}
		for (Map.Entry<Integer, Set<Integer>> entry : tailMap.entrySet()) {
			Set<Integer> values = entry.getValue();
			count += values.size();
		}
		return count;
	}

	private int findTailVisitedCount(List<String> lines) {
		int count = 0;
		Map<Integer, Set<Integer>> tailMap = new HashMap<>();
		// starting at (0, 0)
		int hx = 0;
		int hy = 0;
		int tx = 0;
		int ty = 0;
		Set<Integer> visitedTYs = new HashSet<Integer>();
		visitedTYs.add(ty);
		tailMap.put(tx, visitedTYs);
		for (String line : lines) {
			String[] args = line.split("\\s+");
			String direction = args[0];
			int steps = Integer.valueOf(args[1]);
			for (int i = 0; i < steps; i++) {
				if (UP.equals(direction)) {
					hy++;
					if (Math.abs(hy - ty) > 1) {
						tx = hx;
						ty++;
					} 
				} else if (RIGHT.equals(direction)) {
					hx++;
					if (Math.abs(hx - tx) > 1) {
						tx++;
						ty = hy;
					}
				} else if (DOWN.equals(direction)) {
					hy--;
					if (Math.abs(hy - ty) > 1) {
						tx = hx;
						ty--;
					}
				} else if (LEFT.equals(direction)) {
					hx--;
					if (Math.abs(hx - tx) > 1) {
						tx--;
						ty = hy;
					}
				}
				if (tailMap.containsKey(tx)) {
					visitedTYs = tailMap.get(tx);
					visitedTYs.add(ty);
					tailMap.put(tx, visitedTYs);
				} else {
					visitedTYs = new HashSet<Integer>();
					visitedTYs.add(ty);
					tailMap.put(tx, visitedTYs);
				}
			}
		}
		for (Map.Entry<Integer, Set<Integer>> entry : tailMap.entrySet()) {
			Set<Integer> values = entry.getValue();
			count += values.size();
		}
		return count;
	}
}
