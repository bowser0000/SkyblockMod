package me.Danker.features;

import com.google.common.primitives.Ints;
import me.Danker.config.ModConfig;

import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.IntStream;

public class CoordsOptimizer {

    // https://www.baeldung.com/java-ant-colony-optimization

    private final static Pattern numberedPattern = Pattern.compile("\\d+");
    private final ArrayList<CrystalHollowWaypoints.Waypoint> waypoints;

    private final double c = 1.0;
    private double alpha;
    private double beta;
    private final double evaporation = 0.5;
    private final double Q = 500;
    private final double antFactor = 0.8;
    private final double randomFactor = 0.01;

    private final int maxIterations = 5000;

    private final int numberOfCities;
    private final int numberOfAnts;
    private final double[][] graph;
    private final double[][] trails;
    private final List<Ant> ants = new ArrayList<>();
    private final Random random = new Random();
    private final double[] probabilities;

    private int currentIndex;

    private int[] bestTourOrder;
    private double bestTourLength;

    public CoordsOptimizer(ArrayList<CrystalHollowWaypoints.Waypoint> waypoints) {
        this.waypoints = waypoints;
        graph = getAdjacency();
        numberOfCities = graph.length;
        numberOfAnts = (int) (numberOfCities * antFactor);
        alpha = ModConfig.coordAlpha;
        beta = ModConfig.coordBeta;

        trails = new double[numberOfCities][numberOfCities];
        probabilities = new double[numberOfCities];
        for (int i = 0; i < numberOfAnts; i++) {
            ants.add(new Ant(numberOfCities));
        }
    }

    public static ArrayList<CrystalHollowWaypoints.Waypoint> getNumbered(ArrayList<CrystalHollowWaypoints.Waypoint> waypoints) {
        ArrayList<CrystalHollowWaypoints.Waypoint> numbered = new ArrayList<>();

        for (CrystalHollowWaypoints.Waypoint waypoint : waypoints) {
            String location = waypoint.location.replaceAll("\\D", "");
            Matcher matcher = numberedPattern.matcher(location);
            if (matcher.matches()) numbered.add(new CrystalHollowWaypoints.Waypoint(location, waypoint.pos));
        }

        numbered.sort(Comparator.comparingInt(w -> Integer.parseInt(w.location)));
        return numbered;
    }

    public int[] solve() {
        setupAnts();
        clearTrails();

        for (int i = 0; i < maxIterations; i++) {
            moveAnts();
            updateTrails();
            updateBest();
        }

        System.out.println("Best path: " + Arrays.toString(bestTourOrder));
        return bestTourOrder.clone();
    }

    private void setupAnts() {
        for (int i = 0; i < numberOfAnts; i++) {
            for (Ant ant : ants) {
                ant.clear();
                ant.visitCity(-1, random.nextInt(numberOfCities));
            }
        }
        currentIndex = 0;
    }

    private void moveAnts() {
        for (int i = currentIndex; i < numberOfCities - 1; i++) {
            for (Ant ant : ants) {
                ant.visitCity(currentIndex, selectNextCity(ant));
            }
            currentIndex++;
        }
    }

    private int selectNextCity(Ant ant) {
        int t = random.nextInt(numberOfCities - currentIndex);
        if (random.nextDouble() < randomFactor) {
            OptionalInt cityIndex = IntStream.range(0, numberOfCities)
                    .filter(i -> i == t && !ant.visited(i))
                    .findFirst();
            if (cityIndex.isPresent()) return cityIndex.getAsInt();
        }
        calculateProbabilities(ant);

        double r = random.nextDouble();
        double total = 0;
        for (int i = 0; i < numberOfCities; i++) {
            total += probabilities[i];
            if (total >= r) return i;
        }

        throw new RuntimeException("There are no other cities");
    }

    public void calculateProbabilities(Ant ant) {
        int i = ant.trail[currentIndex];
        double pheromone = 0.0;
        for (int l = 0; l < numberOfCities; l++) {
            if (!ant.visited(l)) {
                pheromone += Math.pow(trails[i][l], alpha) * Math.pow(1.0 / graph[i][l], beta);
            }
        }

        for (int j = 0; j < numberOfCities; j++) {
            if (ant.visited(j)) {
                probabilities[j] = 0.0;
            } else {
                double numerator = Math.pow(trails[i][j], alpha) * Math.pow(1.0 / graph[i][j], beta);
                probabilities[j] = numerator / pheromone;
            }
        }
    }

    private void updateTrails() {
        for (int i = 0; i < numberOfCities; i++) {
            for (int j = 0; j < numberOfCities; j++) {
                trails[i][j] *= evaporation;
            }
        }

        for (Ant a : ants) {
            double contribution = Q / a.trailLength(graph);
            for (int i = 0; i < numberOfCities - 1; i++) {
                trails[a.trail[i]][a.trail[i + 1]] += contribution;
            }
            trails[a.trail[numberOfCities - 1]][a.trail[0]] += contribution;
        }
    }

    private void updateBest() {
        if (bestTourOrder == null) {
            bestTourOrder = ants.get(0).trail;
            bestTourLength = ants.get(0).trailLength(graph);
        }

        for (Ant a : ants) {
            if (a.trailLength(graph) < bestTourLength) {
                bestTourLength = a.trailLength(graph);
                bestTourOrder = a.trail.clone();
            }
        }
    }

    private void clearTrails() {
        for (int i = 0; i < numberOfCities; i++) {
            for (int j = 0; j < numberOfCities; j++) {
                trails[i][j] = c;
            }
        }
    }

    public ArrayList<CrystalHollowWaypoints.Waypoint> getOptimizedPath() {
        ArrayList<CrystalHollowWaypoints.Waypoint> sorted = new ArrayList<>();
        List<Integer> bestPath = Ints.asList(bestTourOrder);
        Collections.reverse(bestPath);

        int offset = 0;
        for (int i = 0; i < bestPath.size(); i++) {
            if (bestPath.get(i) == 0) {
                offset = i;
                break;
            }
        }

        for (int i = 0; i < bestPath.size(); i++) {
            int index = (i + offset) % bestPath.size();
            sorted.add(new CrystalHollowWaypoints.Waypoint("" + (i + 1), waypoints.get(bestPath.get(index)).pos));
        }

        return sorted;
    }

    private static double getDistance(CrystalHollowWaypoints.Waypoint waypoint1, CrystalHollowWaypoints.Waypoint waypoint2) {
        return Math.sqrt(waypoint1.pos.distanceSq(waypoint2.pos));
    }

    private double[][] getAdjacency() {
        int length = waypoints.size();
        double[][] adj = new double[length][length];

        for (int i = 0; i < length; i++) {
            for (int j = 0; j < length; j++) {
                double distance = getDistance(waypoints.get(i), waypoints.get(j));
                adj[i][j] = distance;
                adj[j][i] = distance;
            }
        }

        return adj;
    }

    public static double getLength(ArrayList<CrystalHollowWaypoints.Waypoint> waypoints) {
        if (waypoints.size() == 0) return 0;

        double length = 0;
        for (int i = 0; i < waypoints.size() - 1; i++) {
            length += getDistance(waypoints.get(i), waypoints.get(i + 1));
        }
        length += getDistance(waypoints.get(waypoints.size() - 1), waypoints.get(0));

        return length;
    }


    private static class Ant {

        public int trailSize;
        public int[] trail;
        public boolean[] visited;

        public Ant(int tourSize) {
            this.trailSize = tourSize;
            this.trail = new int[tourSize];
            this.visited = new boolean[tourSize];
        }

        public void visitCity(int currentIndex, int city) {
            trail[currentIndex + 1] = city;
            visited[city] = true;
        }

        public boolean visited(int i) {
            return visited[i];
        }

        public double trailLength(double[][] graph) {
            double length = graph[trail[trailSize - 1]][trail[0]];
            for (int i = 0; i < trailSize - 1; i++) {
                length += graph[trail[i]][trail[i + 1]];
            }
            return length;
        }

        public void clear() {
            for (int i = 0; i < trailSize; i++) {
                visited[i] = false;
            }
        }

    }

}
