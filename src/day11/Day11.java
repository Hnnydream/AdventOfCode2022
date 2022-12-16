package day11;

import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.Queue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.stream.Collectors;

public class Day11 {
	
	private static final String BY = "by";
	private static final String IF_FALSE = "If false:";
	private static final String IF_TRUE = "If true:";
	private static final String TEST = "Test:";
	private static final String OPERATION = "Operation:";
	private static final String STARTING_ITEMS = "Starting items:";
	private static final String MONKEY = "Monkey";

	enum OPS {
		ADD("+"), 
		MULTIPLY("*"), 
		SQUARE("^");
		
		String symbol;
		
		private OPS(String symbol) {
			this.symbol = symbol;
		}
	}
	
	class Monkey {
		int index;
		Queue<Integer> items;
		OPS op;
		int opValue;
		int testValue;
		int testTrueMonkeyId;
		int testFalseMonkeyId;
		int inspectedCount;
		
		public void addItem(Integer item) {
			this.items.add(item);
		}
		
		public int doOpeartion(Integer worryLvl) {
			int newLvl = worryLvl;
			switch (op) {
				case ADD:
					newLvl =  worryLvl + opValue;
					break;
				case MULTIPLY:
					newLvl = worryLvl * opValue;
					break;
				case SQUARE:
					newLvl = worryLvl * worryLvl;
					break;
			}
			return newLvl / 3;
		}
		
		public int getThrowToMonkey(Integer worryLvl) {
			if (worryLvl % testValue == 0) {
				return testTrueMonkeyId;
			} else {
				return testFalseMonkeyId;
			}
		}
	}
	
	public static void main(String[] args) throws IOException, URISyntaxException {
		List<String> lines = Files.lines(Path.of(Day11.class.getResource("input.txt").toURI())).collect(Collectors.toList());
		Day11 day11 = new Day11();
		List<Monkey> monkeys = day11.setupData(lines);
		int level = findLevelOfMonkeyBusiness(monkeys, 20);
		System.out.println("The level of monkey business after 20 rounds=" + level);
	}

	private static int findLevelOfMonkeyBusiness(List<Monkey> monkeys, int round) {
		for (int i = 0; i < round; i++) {
			for (int j = 0; j < monkeys.size(); j++) {
				Monkey monkey = monkeys.get(j);
				Queue<Integer> items = monkey.items;
				while (!items.isEmpty()) {
					Integer worryLvl = items.poll();
					monkey.inspectedCount += 1;
					int newWorryLvl = monkey.doOpeartion(worryLvl);
					int throwIndex = monkey.getThrowToMonkey(newWorryLvl);
					Monkey throwToMonkey = monkeys.get(throwIndex);
					throwToMonkey.items.add(newWorryLvl);
				}
			}
		}
		int max = 0;
		int max2 = 0;
		for (Monkey monkey : monkeys) {
			int count = monkey.inspectedCount;
			System.out.println("Monkey " + monkey.index + " inspected " + count + " times.");
			if (count > max) {
				max2 = max;
				max = count;
			} else if (count > max2) {
				max2 = count;
			}
		}
		return max * max2;
	}

	private List<Monkey> setupData(List<String> lines) {
		List<Monkey> monkeys = new ArrayList<>();
		Monkey monkey = null;
		for (String line : lines) {
			line = line.trim();
			if (line.startsWith(MONKEY)) {
				monkey = new Monkey();
				int num = Integer.valueOf(line.substring(MONKEY.length()).replace(":", "").trim());
				monkey.index = num;
				monkeys.add(monkey);
			} else if (line.startsWith(STARTING_ITEMS)) {
				String itemListStr = line.substring(STARTING_ITEMS.length());
				String[] itemArr = itemListStr.split(",");
				Queue<Integer> items = new LinkedBlockingQueue<>();
				for (int i = 0; i < itemArr.length; i++) {
					String itemStr = itemArr[i].trim();
					int worryLvl = Integer.valueOf(itemStr);
					items.add(worryLvl);
				}
				monkey.items = items;
			} else if (line.startsWith(OPERATION)) {
				if (line.contains(OPS.ADD.symbol)) {
					monkey.op = OPS.ADD;
					int opValue = Integer.valueOf(line.substring(line.indexOf(OPS.ADD.symbol) + OPS.ADD.symbol.length()).trim());
					monkey.opValue = opValue;
				} else if (line.contains(OPS.MULTIPLY.symbol)) {
					String value = line.substring(line.indexOf(OPS.MULTIPLY.symbol) + OPS.MULTIPLY.symbol.length()).trim();
					if ("old".equalsIgnoreCase(value)) {
						monkey.op = OPS.SQUARE;
					} else {
						monkey.op = OPS.MULTIPLY;
						int opValue = Integer.valueOf(value);
						monkey.opValue = opValue;
					}
				}
			} else if (line.startsWith(TEST)) {
				String valueStr = line.substring(line.indexOf(BY) + BY.length()).trim();
				int value = Integer.valueOf(valueStr);
				monkey.testValue = value;
			} else if (line.startsWith(IF_TRUE) || line.startsWith(IF_FALSE)) {
				String valueStr = line.substring(line.indexOf(MONKEY.toLowerCase()) + MONKEY.length()).trim();
				int index = Integer.valueOf(valueStr);
				if (line.startsWith(IF_TRUE)) {
					monkey.testTrueMonkeyId = index;
				} else {
					monkey.testFalseMonkeyId = index;
				}
			}
					
		}
		return monkeys;
	}
}
