package day2;

import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.stream.Collectors;

public class Day2 {
	
	enum Outcome {
		WON(6),
		DRAW(3),
		LOST(0);
		
		int score;
		
		private Outcome(int score) {
			this.score = score;
		}
		
		public int getScore() {
			return score;
		}
	}
	
	enum Shape {
		ROCK(1),
		PAPER(2),
		SCISSOR(3);
		
		int score;
		
		private Shape(int score) {
			this.score = score;
		}
		
		public int getScore() {
			return score;
		}
	}
	
	public static Shape getShape(char shape) {
		if ('A' == shape || 'X' == shape) {
			return Shape.ROCK; 
		} else if ('B' == shape || 'Y' == shape) {
			return Shape.PAPER;
		} else if ('C' == shape || 'Z' == shape){
			return Shape.SCISSOR;
		}
		return null;
	}
	
	public static Outcome getOutcome(char outcome) {
		if ('X' == outcome) {
			return Outcome.LOST; 
		} else if ('Y' == outcome) {
			return Outcome.DRAW;
		} else if ('Z' == outcome){
			return Outcome.WON;
		}
		return null;
	}
	
	public static void main(String[] args) throws IOException, URISyntaxException {
		List<String> lines = Files.lines(Path.of(Day2.class.getResource("input.txt").toURI())).collect(Collectors.toList());
		int sum = 0;
		int sum2 = 0;
		for (String line : lines) {
			Shape opp = getShape(line.charAt(0));
			Shape me = getShape(line.charAt(2));
			sum += getRoundScore(opp, me);
			// Part 2
			// X means LOST, Y means DRAW, and Z means WON
			Outcome outcome = getOutcome(line.charAt(2));
			sum2 += getRoundScore(opp, outcome);
		}
		System.out.println(sum);
		System.out.println("Part2=" + sum2);

	}
	
	public static int getRoundScore(Shape opp, Outcome outcome) {
		if (Shape.ROCK == opp) {
			switch(outcome) {
			case LOST:
				return Shape.SCISSOR.score + outcome.score;
			case DRAW:
				return Shape.ROCK.score + outcome.score;
			case WON:
				return Shape.PAPER.score + outcome.score;
			}
		} else if (Shape.PAPER == opp) {
			switch(outcome) {
			case LOST:
				return Shape.ROCK.score + outcome.score;
			case DRAW:
				return Shape.PAPER.score + outcome.score;
			case WON:
				return Shape.SCISSOR.score + outcome.score;
			}
		} else if (Shape.SCISSOR == opp) {
			switch(outcome) {
			case LOST:
				return Shape.PAPER.score + outcome.score;
			case DRAW:
				return Shape.SCISSOR.score + outcome.score;
			case WON:
				return Shape.ROCK.score + outcome.score;
			}
		}
		return 0;
	}
	
	public static int getRoundScore(Shape opp, Shape me) {
		if (Shape.ROCK == opp) {
			switch(me) {
				case ROCK:
					return me.score + Outcome.DRAW.score;
				case PAPER:
					return me.score + Outcome.WON.score;
				case SCISSOR:
					return me.score + Outcome.LOST.score;
				default:
					System.out.println("Error" + opp + ", " + me);
					return 0;
			}
		} else if (Shape.PAPER == opp) {
			switch(me) {
				case ROCK:
					return me.score + Outcome.LOST.score;
				case PAPER:
					return me.score + Outcome.DRAW.score;
				case SCISSOR:
					return me.score + Outcome.WON.score;
				default:
					System.out.println("Error" + opp + ", " + me);
					return 0;
			}
		} else if (Shape.SCISSOR == opp) {
			switch(me) {
				case ROCK:
					return me.score + Outcome.WON.score;
				case PAPER:
					return me.score + Outcome.LOST.score;
				case SCISSOR:
					return me.score + Outcome.DRAW.score;
				default:
					System.out.println("Error" + opp + ", " + me);
					return 0;
			}
		}
		System.out.println("Error" + opp + ", " + me);
		return 0;
	}
}
