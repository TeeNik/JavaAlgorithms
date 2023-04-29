package Baseball;

import edu.princeton.cs.algs4.FlowEdge;
import edu.princeton.cs.algs4.FlowNetwork;
import edu.princeton.cs.algs4.FordFulkerson;
import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdOut;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;

public class BaseballElimination {

    private final static int INDEX = 0;
    private final static int WINS = 1;
    private final static int LOSSES = 2;
    private final static int REMAINING = 3;

    private final int numOfTeams;
    private final HashMap<String, Integer[]> teams;
    private final HashMap<Integer, String> teamIndexToName;
    private final int[][] against;
    private ArrayList<String> subset;
    private final int[] wins;


    // create a baseball division from given filename in format specified below
    public BaseballElimination(String filename) {
        teams = new HashMap<>();
        teamIndexToName = new HashMap<>();

        In in = new In(filename);
        numOfTeams = in.readInt();
        against = new int[numOfTeams][numOfTeams];
        wins = new int[numOfTeams];

        int i = 0;
        while (!in.isEmpty()) {
            String teamName = in.readString();
            Integer[] teamInfo = new Integer[4];
            teamInfo[INDEX] = i;
            teamInfo[WINS] = in.readInt();
            teamInfo[LOSSES] = in.readInt();
            teamInfo[REMAINING] = in.readInt();
            teams.put(teamName, teamInfo);
            teamIndexToName.put(i, teamName);

            for (int j = 0; j < numOfTeams; ++j) {
                against[i][j] = in.readInt();
            }
            ++i;
        }
    }

    // number of teams
    public int numberOfTeams() {

        return numOfTeams;
    }

    // all teams
    public Iterable<String> teams() {
        return teams.keySet();
    }

    // number of wins for given team
    public int wins(String team) {
        checkTeam(team);
        return teams.get(team)[WINS];
    }

    // number of losses for given team
    public int losses(String team) {
        checkTeam(team);
        return teams.get(team)[LOSSES];
    }

    // number of remaining games for given team
    public int remaining(String team) {
        checkTeam(team);
        return teams.get(team)[REMAINING];
    }

    // number of remaining games between team1 and team2
    public  int against(String team1, String team2) {
        checkTeam(team1);
        checkTeam(team2);
        return against[teams.get(team1)[INDEX]][teams.get(team2)[INDEX]];
    }

    private void checkTeam(String team) {
        if (team == null || !teams.containsKey(team))
        {
            throw new IllegalArgumentException();
        }
    }

    // is given team eliminated?
    public boolean isEliminated(String team) {
        checkTeam(team);

        subset = new ArrayList<>();

        //trivial elimination
        Iterator<String> teamNames = teams().iterator();
        while (teamNames.hasNext()) {
            String current = teamNames.next();
            if (wins(team) + remaining(team) < wins(current)) {
                subset.add(current);
                return true;
            }
        }
        return flowCheck(team);
    }

    private boolean flowCheck(String team) {
        //initialize flow network with source [size - 2] and sink [size - 1]
        final int games = numOfTeams * (numOfTeams - 1) / 2;
        final int size = 2 + numOfTeams + games;
        FlowNetwork network = new FlowNetwork(size);

        int s = games + numOfTeams;
        int t = games + numOfTeams + 1;
        int capacity = 0;
        int vertex = 0;

        //add edges for source to game combination vertices to teams
        for (int i = 0; i < numOfTeams; ++i) {
            network.addEdge(new FlowEdge(games + i, t, remaining(team) + wins(team) - wins(teamIndexToName.get(i))));
            for (int j = i + 1; j < numOfTeams; ++j) {
                network.addEdge(new FlowEdge(vertex, games + i, Double.POSITIVE_INFINITY));
                network.addEdge(new FlowEdge(vertex, games + j, Double.POSITIVE_INFINITY));
                network.addEdge(new FlowEdge(s, vertex, against[i][j]));
                ++vertex;
                capacity += against[i][j];
            }
        }

        FordFulkerson ff = new FordFulkerson(network, s, t);
        if (capacity == ff.value()) {
            return false;
        }
        else {
            int index = 0;
            for (int v = games; v < games + numOfTeams; ++v) {
                if (ff.inCut(v)) {
                    subset.add(teamIndexToName.get(index));
                }
                ++index;
            }
            return true;
        }
    }

    // subset R of teams that eliminates given team; null if not eliminated
    public Iterable<String> certificateOfElimination(String team) {
        checkTeam(team);

        if (isEliminated(team)) {
            return subset;
        }
        return null;
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
