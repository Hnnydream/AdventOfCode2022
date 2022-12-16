package day7;

import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class Day7 {
	

	class File {
		String name;
		boolean isDir;
		int size;
		File parent;
		Map<String, File> path;
	}
	
	private static final String CMD_INDICATOR = "$";
	
	private static final String CMD_CD = "cd";
	
	private static final String CMD_LS = "ls";
	
	private static final String DIR = "dir";

	private static final String ROOT = "/";
	
	private static final String CMD_BACK_CD = "..";
	
	private static final int MAX = 100000;
	
	private static final int DISK_SPACE = 70000000;
	
	private static final int SPACE_NEEDED = 30000000;
	
	private File root;
	
	public static void main(String[] args) throws IOException, URISyntaxException {
		List<String> lines = Files.lines(Path.of(Day7.class.getResource("input.txt").toURI())).collect(Collectors.toList());
		Day7 day7 = new Day7();
		day7.setupData(lines);
		int size = day7.calcSize(day7.root);
		System.out.println("Total Size=" + size);
		int limitedSize = day7.calcLimitedSize(day7.root, MAX);
		System.out.println("Total size of at most " + MAX + "=" + limitedSize);
		int neededSize = SPACE_NEEDED - (DISK_SPACE - day7.root.size); 
		int deletedSize = day7.findDelSize(day7.root, neededSize, day7.root.size);
		System.out.println("Total deleted size=" + deletedSize + ", needed size=" + neededSize);
	}

	private int findDelSize(File currRoot, int spaceNeeded, int currMin) {
		if (currRoot.size < spaceNeeded) {
			return currMin;
		} else {
			if (currRoot.isDir) {
				Map<String, File> pathMap = currRoot.path;
				for (Map.Entry<String, File> entry : pathMap.entrySet()) {
					File file = entry.getValue();
					if (file.size > spaceNeeded && file.size < currMin) {
						return Math.min(findDelSize(file, spaceNeeded, file.size), file.size);
					}
				}
			}
		}
		return Math.min(currRoot.size, currMin);
	}

	private int calcLimitedSize(File currRoot, int max) {
		int size = 0;
		if (currRoot.size <= max) {
			size += currRoot.size;
		}
		if (currRoot.isDir) {
			Map<String, File> pathMap = currRoot.path;
			for (Map.Entry<String, File> entry : pathMap.entrySet()) {
				File file = entry.getValue();
				if (file.isDir) {
					size += calcLimitedSize(file, MAX);
				}
			}
		}
		return size;
	}

	private int calcSize(File currRoot) {
		if (currRoot.isDir) {
			int size = 0;
			Map<String, File> pathMap = currRoot.path;
			for (Map.Entry<String, File> entry : pathMap.entrySet()) {
				File file = entry.getValue();
				if (file.isDir) {
					size += calcSize(file);
				} else {
					size += file.size;
				}
			}
			currRoot.size = size;
			return size;
		} else {
			return currRoot.size;
		}
	}

	private void setupData(List<String> lines) {
		File currDir = root;
		String currCMD = "";
		for (String line : lines) {
			if (line.startsWith(CMD_INDICATOR)) {
				String cmd = line.substring(CMD_INDICATOR.length()).trim();
				if (cmd.startsWith(CMD_CD)) {
					currCMD = CMD_CD;
					String path = cmd.substring(CMD_CD.length()).trim();
					if (ROOT.equals(path)) {
						if (root == null) {
							root = createFile(null, ROOT, true, 0);
						}
						currDir = root;
					} else if (CMD_BACK_CD.equals(path)) {
						currDir = currDir.parent;
					} else {
						Map<String, File> pathMap = currDir.path;
						if (pathMap.containsKey(path)) {
							currDir = pathMap.get(path);
						} else {
							System.out.println("Incorrect Path entried, path" + path + ", currDir=" + currDir.name);
						}
					}
				} else if (cmd.startsWith(CMD_LS)) {
					currCMD = CMD_LS;
				}
			} else {
				if (CMD_LS.equals(currCMD)) {
					if (line.startsWith(DIR)) {
						String filename = line.substring(DIR.length()).trim();
						File file = createFile(currDir, filename, true, 0);
						currDir.path.put(filename, file);
					} else {
						String[] args = line.split("\\s+");
						File file = createFile(currDir, args[1], false, Integer.valueOf(args[0]));
						currDir.path.put(file.name, file);
					}
				}
			}
		}
	}

	private File createFile(File currDir, String filename, boolean isDir, int size) {
		File file = new File();
		file.name = filename;
		file.isDir = isDir;
		file.size = size;
		file.parent = currDir;
		if (isDir) {
			file.path = new HashMap<String, Day7.File>();
		}
		return file;
	}
}
