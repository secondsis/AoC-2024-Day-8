import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Scanner;
import java.io.File;
import java.util.*;
import java.awt.Point;

public class Part2 {
    // Number of antinodes: Sum + previous number * 2
    // n antennas: number of n-1 antinodes + (n-1)*2

    public static int numberOfAntinodes(int n, int currIndex, int sum) {
        if(currIndex == n) {
            return sum;
        }

        return numberOfAntinodes(n, currIndex + 1, sum + (currIndex)*2);
    }

    public static void main(String[] args) throws IOException{
        Scanner scan = new Scanner(new File("input.txt"));
        //System.out.println(numberOfAntinodes(5, 1, 0));
        HashMap<Character, ArrayList<Antenna>> antennaGroups = new HashMap<>();

        String[] content = Files.readString(Paths.get("input.txt")).split("\n");
        char[][] contentChar = new char[content.length][content[0].length()];

        for (int i = 0; i < content.length; i++) {
            contentChar[i] = content[i].toCharArray();
        }

        int height = 0;
        int width = 0;
        
        for (; scan.hasNext(); height++) {
            String line = scan.nextLine();
            char[] lineChar = line.toCharArray();

            for (width = 0; width < lineChar.length; width++) {
                char c = lineChar[width];
                if(c == '.') {
                    continue;
                    // ignore
                }

                ArrayList<Antenna> list = antennaGroups.getOrDefault(c, new ArrayList<>());
                list.add(new Antenna(c, new Point(width, height)));

                antennaGroups.putIfAbsent(c, list); //might be unneeded
            }

        }
        boolean[][] antinodes = new boolean[height][width];

        System.out.println(antennaGroups);
        // need to check the position from every antenna to another antenna
        for(ArrayList<Antenna> antennas : antennaGroups.values()) {
            for(Antenna antennaOuter : antennas) {
                for(Antenna antennaInner : antennas) {
                    if(antennaOuter == antennaInner) continue;

                    Point change1 = new Point(antennaOuter.getPosition().x - antennaInner.getPosition().x, antennaOuter.getPosition().y - antennaInner.getPosition().y);
                    Point change2 = new Point(antennaInner.getPosition().x - antennaOuter.getPosition().x, antennaInner.getPosition().y - antennaOuter.getPosition().y);
                    antinodes[antennaOuter.getPosition().y][antennaOuter.getPosition().x] = true;
                    antinodes[antennaInner.getPosition().y][antennaInner.getPosition().x] = true;
                    
                    // Different antennas, find the antinodes on both sides
                    Point antinode1 = new Point(antennaOuter.getPosition().x - change1.x, antennaOuter.getPosition().y - change1.y);
                    Point antinode2 = new Point(antennaInner.getPosition().x - change2.x, antennaInner.getPosition().y - change2.y);

                    while(true) {
                        if((antinode1.x < 0 || antinode1.x >= width || antinode1.y < 0 || antinode1.y >= height)
                        && (antinode2.x < 0 || antinode2.x >= width || antinode2.y < 0 || antinode2.y >= height)) {
                            System.out.println("break");
                            break;
                        }

                        if(!(antinode1.x < 0 || antinode1.x >= width || antinode1.y < 0 || antinode1.y >= height)) {
                            antinodes[antinode1.y][antinode1.x] = true;
                            contentChar[antinode1.y][antinode1.x] = '#';
                        }
                        

                        if(!(antinode2.x < 0 || antinode2.x >= width || antinode2.y < 0 || antinode2.y >= height)) {
                            antinodes[antinode2.y][antinode2.x] = true;
                            contentChar[antinode2.y][antinode2.x] = '#';
                        }

                        antinode1.setLocation(antinode1.x - change1.x, antinode1.y - change1.y);
                        antinode2.setLocation(antinode2.x - change2.x, antinode2.y - change2.y);
                    }
                }
            }
        }

        for(char[] chars : contentChar) {
            String charsStr = new String(chars);
            System.out.println(charsStr);
        }
        int total = 0;

        for(boolean[] b : antinodes) {
            for(boolean v : b) {
                if(v) total++;
            }
        }

        System.out.println(total);
    }
}