package day3;

import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class Day3 {
	public static void main(String[] args) throws IOException, URISyntaxException {
		List<String> lines = Files.lines(Path.of(Day3.class.getResource("input.txt").toURI())).collect(Collectors.toList());
		findCommonCharInEachHalfLine(lines);
		findCommonCharInEvery3Lines(lines);
	}
	
	private static void findCommonCharInEvery3Lines(List<String> lines) {
		int sum = 0;
		Set<Character> charSet = new HashSet<Character>();
		Set<Character> commonCharSet = new HashSet<Character> ();
		for (int i = 0; i < lines.size(); i++) {
			String line = lines.get(i);
			if (i % 3 == 0) {
				// first line of 3 line group
				charSet = new HashSet<Character>();
				for (int j = 0; j < line.length(); j++) {
					charSet.add(line.charAt(j));
				}
			} else if (i % 3 == 1) {
				// second line
				commonCharSet = new HashSet<Character>();
				for (int j = 0; j < line.length(); j++) {
					char c = line.charAt(j);
					if (charSet.contains(c)) {						
						commonCharSet.add(c);
					}
				}
			} else {
				// third line
				for (int j = 0; j < line.length(); j++) {
					char c = line.charAt(j);
					if (commonCharSet.contains(c)) {
						sum += getCharValue(c);
						break;
					}
				}
			}
		}
		System.out.println("sum of common char every 3 lines= " + sum);
	}

	private static void findCommonCharInEachHalfLine(List<String> lines) {
		int sum = 0;
		for (String line : lines) {
			int size = line.length();
			int halfPoint = size / 2;
			String s1 = line.substring(0, halfPoint);
			String s2 = line.substring(halfPoint);
//			System.out.print("s1=" + s1 + ", size=" + s1.length() + ", s2=" + s2 + ", size=" + s2.length() + ", ");
			Set<Character> charSet = new HashSet<Character>();
			for (int i = 0; i < size; i++) {
				char c = line.charAt(i);
				if (i < halfPoint) {
					charSet.add(c);
				} else {
					if (charSet.contains(c)) {
						int value = getCharValue(c);
//						System.out.println("match char=" + c + ", value=" + value);
						sum += value;
						break;
					}
				}
			}
		}
		System.out.println(sum);
	}

	private static int getCharValue(char c) {
		int offset = 38;
		if (c <= 'z' && c >= 'a') {
			offset = 96;
		}
		int value = c - offset;
		return value;
	}
}
