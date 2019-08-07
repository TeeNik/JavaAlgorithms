import edu.princeton.cs.algs4.Bag;
import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdOut;

import java.util.HashMap;

public class BaseballElimination {

    private int numOfTeams;
    private HashMap<String, Integer[]> teams;
    private HashMap<Integer, String> teamNumbers;
    private int[][] against;
    private Bag<String> subset;
    private int[] wins;
    private boolean checked;

    public BaseballElimination(String filename) {
        readData(filename);
    }

    private void readData(String filename){
        teams = new HashMap<String, Integer[]>();
        teamNumbers = new HashMap<Integer, String>();
        In in = new In(filename);
        numOfTeams = in.readInt();
        against = new int[numOfTeams][numOfTeams];
        wins = new int[numOfTeams];

        int i = 0;
        while (!in.isEmpty()) {
            // read team name, number, wins, losses, and remaining to hashmap
            String team = in.readString();
            Integer[] games = new Integer[4];
            games[0] = i;
            games[1] = in.readInt();
            wins[i] = games[1];
            games[2] = in.readInt();
            games[3] = in.readInt();
            teams.put(team, games);
            teamNumbers.put(games[0], team);

            // add current team's future matches to matrix
            for (int j = 0; j < numOfTeams; j++) {
                against[i][j] = in.readInt();
            }
            i++;
        }
    }

    public int numberOfTeams(){
        return numOfTeams;
    }

    public Iterable<String> teams() {
        return teams.keySet();
    }

    public int wins(String team) {
        checkTeam(team);
        return teams.get(team)[1];
    }

    public int losses(String team) {
        checkTeam(team);
        return teams.get(team)[2];
    }

    public int remaining(String team) {
        checkTeam(team);
        return teams.get(team)[3];
    }

    public int against(String team1, String team2) {
        checkTeam(team1);
        checkTeam(team2);
        return against[teams.get(team1)[0]][teams.get(team2)[0]];
    }

    public  boolean isEliminated(String team) {
        
    }

    public Iterable<String> certificateOfElimination(String team) {

    }

    private void checkTeam(String team) {
        if (!teams.containsKey(team)) throw new java.lang.IllegalArgumentException();
    }

    public static void main(String[] args) {
        BaseballElimination division = new BaseballElimination(args[0]);
        for (String team : division.teams()) {
            if (division.isEliminated(team)) {
                StdOut.print(team + " is eliminated by the subset R = { ");
                for (String t : division.certificateOfElimination(team)) {
                    StdOut.print(t + " ");
                }
                StdOut.println("}");
            }
            else {
                StdOut.println(team + " is not eliminated");
            }
        }
    }

}
