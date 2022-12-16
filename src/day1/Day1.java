package day1;

import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.stream.Collectors;

public class Day1 {
	
	public static void main(String[] args) throws IOException, URISyntaxException {
		List<String> lines = Files.lines(Path.of(Day1.class.getResource("input.txt").toURI())).collect(Collectors.toList());
		int maxCal = 0;
		int secondCal = 0;
		int thirdCal = 0;
		int sumCal = 0;
		int count = 0;
		for (String line : lines) {
			if (line == null || line.length() == 0) {
				count++;
				String answer = "count=" + count + ", sum=" + sumCal + ", Max=" + maxCal + ", second=" + secondCal + ", third=" + thirdCal + ", total=" + (maxCal + secondCal + thirdCal);
				System.out.println(answer);
				if (maxCal == 0) {
					maxCal = sumCal;
				} else if (secondCal == 0) {
					if (sumCal > maxCal) {
						maxCal = sumCal;
					} else {
						secondCal = sumCal;
					}
				} else if (thirdCal == 0) {
					if (sumCal > maxCal) {
						maxCal = sumCal;
					} else if (sumCal > secondCal) {
						thirdCal = secondCal;
						secondCal = sumCal;
					} else {
						thirdCal = sumCal;
					}
				} else {
					if (sumCal > maxCal) {
						thirdCal = secondCal;
						secondCal = maxCal;
						maxCal = sumCal;
					} else if (sumCal > secondCal) {
						thirdCal = secondCal;
						secondCal = sumCal;
					} else if (sumCal > thirdCal){
						thirdCal = sumCal;
					}
				}
				sumCal = 0;
			} else {
				sumCal += Integer.parseInt(line);
			}
		}
		String answer = "Max=" + maxCal + ", second=" + secondCal + ", third=" + thirdCal + ", total=" + (maxCal + secondCal + thirdCal);
		System.out.print(answer);
	}
}
