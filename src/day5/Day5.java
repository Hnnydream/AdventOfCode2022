package day5;

import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Stack;
import java.util.stream.Collectors;

public class Day5 {
	
	private static String MOVE = "move";
	
	private static String FROM = "from";
	
	private static String TO = "to";
	
	private Map<Integer,Stack<Character>> ships;
	
	private int totalStackCount = 0;
	
	private List<String> opQueue = new ArrayList<String>();
	
	public static void main(String[] args) throws IOException, URISyntaxException {
		List<String> lines = Files.lines(Path.of(Day5.class.getResource("input.txt").toURI())).collect(Collectors.toList());
		Day5 day5 = new Day5();
		day5.setupData(lines);
		// Part 1
//		boolean updatedMover = false;
		// Part 2
		boolean updatedMover = true;
		day5.doOperations(updatedMover);
		String topCrates = day5.populateTopCrates();
		System.out.println("Top crates=" + topCrates);
	}

	private String populateTopCrates() {
		String topCrates = "";
		for (int i = 1; i <= totalStackCount; i++) {
			Stack<Character> stack = ships.get(i);
			topCrates = topCrates + stack.peek();
		}
		return topCrates;
	}

	private void doOperations(boolean updatedMover) {
		for (String op : opQueue) {
			int fromIndex = op.indexOf(FROM);
			int toIndex = op.indexOf(TO);
			int movingCount = Integer.valueOf(op.substring(MOVE.length(), fromIndex).trim());
			int fromStackIndex = Integer.valueOf(op.substring(fromIndex + FROM.length(), toIndex).trim());
			int toStackIndex = Integer.valueOf(op.substring(toIndex + TO.length()).trim());
			Stack<Character> fromStack = ships.get(fromStackIndex);
			Stack<Character> toStack = ships.get(toStackIndex);
			if (updatedMover) {
				Stack<Character> retainOrderStack = new Stack<Character>();
				for (int i = 0; i < movingCount; i++) {
					char movingCrate = fromStack.pop();
					ships.put(fromStackIndex, fromStack);
					retainOrderStack.add(movingCrate);
				}
				while (!retainOrderStack.isEmpty()) {
					char crate = retainOrderStack.pop();
					toStack.add(crate);
				}
				ships.put(toStackIndex, toStack);
			} else {
				for (int i = 0; i < movingCount; i++) {
					char movingCrate = fromStack.pop();
					ships.put(fromStackIndex, fromStack);
					toStack.add(movingCrate);
					ships.put(toStackIndex, toStack);
				}
			}
		}
	}

	private void setupData(List<String> lines) {
		ships = new HashMap<Integer, Stack<Character>>();
		Stack<String> tmpStacks = new Stack<String>();
		for (String line : lines) {
			if (line.startsWith("[")) {
				tmpStacks.add(line);
			} else if (line.startsWith(MOVE)) {
				opQueue.add(line);
			} else {
				String[] stacks = line.split("\\s+");
				for (int i = 1; i < stacks.length; i++) {
					ships.put(Integer.valueOf(stacks[i]), new Stack<Character>());
					totalStackCount = i;
				}
			}
		}
		if (!tmpStacks.isEmpty()) {
			int index = 1;
			while (!tmpStacks.isEmpty()) {
				String stackLvl = tmpStacks.pop();
				int size = stackLvl.length();
				int stackIndex = 0;
				for (int i = 0; i < size; i = i + 4) {
					char crate = stackLvl.charAt(index + i);
					stackIndex++;
					if (crate != ' ') {
						Stack<Character> stack = ships.get(stackIndex);
						stack.add(crate);
						ships.put(stackIndex, stack);
					}
				}
			}
		}
	}
}
