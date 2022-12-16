package day4;

import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.stream.Collectors;

public class Day4 {
	private int r1 = 0;
	private int r2 = 0;
	private int s1 = 0;
	private int s2 = 0;

	public static void main(String[] args) throws IOException, URISyntaxException {
		List<String> lines = Files.lines(Path.of(Day4.class.getResource("input.txt").toURI())).collect(Collectors.toList());
		Day4 day4 = new Day4();
		int count = day4.findFullyContain(lines);
		System.out.println("Fully contianed count=" + count);
		int count2 = day4.findOverlap(lines);
		System.out.println("Overlap count =" + count2);
	}
	
	private int findOverlap(List<String> lines) {
		int count = 0;
		for (String line : lines) {
			setupData(line);
			if (r1 == s1 || r1 == s2 || r2 == s1 || r2 == s2) {
				count++;
			} else {
				if (r1 > s1 && r1 < s2) {
					count ++;
				} else if (r1 < s1 && r2 > s1) {
					count ++;
				}
			}
		}
		return count;
	}

	private int findFullyContain(List<String> lines) {
		int count = 0;
		for (String line : lines) {
			setupData(line);
			if (r1 == s1 || r2 == s2) {
				count++;
			} else if (r1 > s1 && r2 < s2) {
				count++;
			} else if (r1 < s1 && r2 > s2) {
				count++;
			}
			//System.out.println(r1 + "-" + r2 + "," + s1 + "-" + s2 + ", count=" + count);
		}
		return count;
	}

	private void setupData(String line) {
		String[] pairs = line.split(",");
		String pair1 = pairs[0];
		String pair2 = pairs[1];
		 r1 = Integer.valueOf(pair1.split("-")[0]);
		 r2 = Integer.valueOf(pair1.split("-")[1]);
		 s1 = Integer.valueOf(pair2.split("-")[0]);
		 s2 = Integer.valueOf(pair2.split("-")[1]);
		int tmp = 0;
		if (r1 > r2) {
			tmp = r1;
			r1 = r2;
			r2 = tmp;
		}
		if (s1 > s2) {
			tmp = s1;
			s1 = s2;
			s2 = tmp;
		}
	}
}
