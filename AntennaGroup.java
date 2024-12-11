import java.util.*;

public class AntennaGroup {
    private ArrayList<Antenna> group;
    private char frequency;

    public AntennaGroup(ArrayList<Antenna> group, char freq) {
        this.group = group;
        this.frequency = freq;
    }

    public void addAntenna(Antenna antenna) {
        this.group.add(antenna);
    }

    public ArrayList<Antenna> getGroup() {
        return this.group;
    }

    public char getFrequency() {
        return this.frequency;
    }
}