package day10;

import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.stream.Collectors;

public class Day10 {
	
	public static void main(String[] args) throws IOException, URISyntaxException {
		List<String> lines = Files.lines(Path.of(Day10.class.getResource("input.txt").toURI())).collect(Collectors.toList());
		Day10 day10 = new Day10();
		int signalStrengths = day10.findSumofSixSignalStrengths(lines);
		System.out.println("Sum of these six signal sgrengths=" + signalStrengths);
		day10.drawSprite(lines);
	}
	
	private void drawSprite(List<String> lines) {
		int cycle = 0;
		int x = 1;
		for (String line : lines) {
			if ("noop".equals(line)) {
				cycle = drawPixel(cycle, x);
			} else if (line.startsWith("addx")) {
				String[] args = line.split("\\s+");
				int value = Integer.valueOf(args[1]);
				for (int i = 0; i < 2; i++) {
					cycle = drawPixel(cycle, x);
				}
				x += value;
			}
		}
	}

	private int drawPixel(int cycle, int x) {
		if (Math.abs(cycle % 40 - x) <= 1) {
			System.out.print("#");
		} else {
			System.out.print(".");
		}
		cycle++;
		if (cycle == 40 || cycle == 80 || cycle == 120 || cycle == 160 || cycle == 200 || cycle == 240) {
			System.out.println();
		}
		return cycle;
	}

	private int findSumofSixSignalStrengths(List<String> lines) {
		int sum = 0;
		int cycle = 0;
		int x = 1;
		for (String line : lines) {
			if ("noop".equals(line)) {
				cycle++;
				sum = updateCycle(sum, cycle, x);
			} else if (line.startsWith("addx")) {
				String[] args = line.split("\\s+");
				int value = Integer.valueOf(args[1]);
				for (int i = 0; i < 2; i++) {
					cycle++;
					sum = updateCycle(sum, cycle, x);
				}
				x += value;
			}
		}
		return sum;
	}

	private int updateCycle(int sum, int cycle, int x) {
		if (cycle == 20 || cycle == 60 || cycle == 100 || cycle == 140 || cycle == 180 || cycle == 220) {
			sum += x * cycle;
		}
		return sum;
	}
}
