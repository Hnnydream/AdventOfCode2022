package day6;

import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class Day6 {
	
	private static int MARKER_LEN = 4;
	
	private static int MESSAGE_LEN = 14;


	public static void main(String[] args) throws IOException, URISyntaxException {
		List<String> lines = Files.lines(Path.of(Day6.class.getResource("input.txt").toURI())).collect(Collectors.toList());
		Day6 day6 = new Day6();
		int markerPos = day6.getMarkerPos(lines, MARKER_LEN);
		System.out.println("MakrerPos=" + markerPos);
		int messagePos = day6.getMarkerPos(lines, MESSAGE_LEN);
		System.out.println("messagePos=" + messagePos);
	}

	private int getMarkerPos(List<String> lines, int len) {
		for (String input : lines) {
			int size = input.length();
			int i = 0;
			int j = len;
			while (i < size && j < size) {
				String currMarker = input.substring(i, j);
				int dupPos = isMarker(currMarker);
//				System.out.println("Curr Marker=" + currMarker + ", dupPos=" + dupPos);
				if (dupPos == -1) {
					return j;
				} else {
					i += dupPos;
					j += dupPos;
				}
			}
		}
		return 0;
	}
	
	private int isMarker(String signal) {
		Map<Character, Integer> markerSet = new HashMap<Character, Integer>();
		for (int i = 0; i < signal.length(); i++) {
			char c = signal.charAt(i);
			if (markerSet.containsKey(c)) {
				return markerSet.get(c);
			} else {
				markerSet.put(c, i + 1);
			}
		}
		return -1;
	}
}
