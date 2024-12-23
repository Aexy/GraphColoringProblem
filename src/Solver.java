import java.io.*;
import java.util.*;

public class Solver {

    private int maxColours;
    private Map<Vertex, Integer> mappedColor;
    private BufferedWriter writer;
    public Graph graph;

    //Alternative solution without backtracking
    public List<Integer> availableColors = new ArrayList<>();

    public Solver(Graph graph, int maxColours) {
        this.graph = graph;
        this.maxColours = maxColours;
        this.mappedColor = new HashMap<>();
        initAvailableColors(maxColours);
    }

    public void solve(String output) throws IOException {
        BufferedWriter writer = new BufferedWriter(new FileWriter(output));
        boolean result = solve(0, new ArrayList<>(graph.vertices));
        if (result) {
            writeSolution(writer);
            writer.close();
        } else {
            writer.write("No solution found");
            writer.close();
        }
    }

    private boolean solve(int vIndex, List<Vertex> vertices) {
        if (vIndex == vertices.size()) {
            return true;
        }
        Vertex curr = vertices.get(vIndex);
        for (int c = 1; c <= maxColours; c++) {
            if (canColor(curr, c)) {
                mappedColor.put(curr, c);
                //if the coloring is a solution in the given branch, proceed to the next node
                if (solve(vIndex+1, vertices)) {
                    return true;
                }
                //backtrack to remove mapping
                mappedColor.remove(curr);
            }
        }
        return false; //kill the current branch
    }

    private boolean canColor(Vertex vertex, int color) {
        for (Vertex neighbor : vertex.neighbors) {
            if (mappedColor.getOrDefault(neighbor, -1) == color) {
                return false; //neighbor has the same color
            }
        }
        return true;
    }

    private void writeSolution(BufferedWriter writer) throws IOException {
        System.out.println("___Solution 1:___");
        for (Map.Entry<Vertex, Integer> entry : mappedColor.entrySet()) {
            writer.write("[Vertex: " + entry.getKey().key + " , Color:" + entry.getValue() + "]\n");
            System.out.println("[Vertex: " + entry.getKey().key + " , Color:" + entry.getValue() + "]");
        }
    }


    /**
     * DISCLAIMER:
     * The code snippet below was an attempt to use an alternative method instead of backtracking.
     * It works for only simple graphs but fails for the cyclic graph example.
     * It contains almost all the random thoughts that I had while trying to come up with
     * an algorithm that does not brute force the options.
     *
     * Ideas for later:
     * 1- Importance ranking according to number of edges:
     *    = The vertex with the least connections gets assigned a color last
     *    = Constraint based preprocessing
     * 2- Isolating root:
     *    =
     */
    public void solve2(String output) throws IOException {
        writer = new BufferedWriter(new FileWriter(output));
        mappedColor.clear();
        if(solve2()){
            writeSolution();
            writer.close();
        }else{
            writer.close();
            System.out.println("No solution found");
        }
    }

    //alternative solution without backtracking (fails for cyclic graphs)
    private boolean solve2() throws IOException {
        List<Vertex> vertices = new ArrayList<>(graph.vertices);
        for (Vertex referenceVertex : vertices) {
            if(mappedColor.containsKey(referenceVertex)){
                continue;
            }
            //number of available colors
            List<Integer> unusedColours = getUnusedColors(referenceVertex);
            //vertices to be colored
            List<Vertex> toColor = new ArrayList<>(referenceVertex.neighbors);

            //color the reference with the first available color
            mappedColor.put(referenceVertex, unusedColours.getFirst());
            unusedColours.remove(unusedColours.getFirst());

            //not enough colors available
            if (toColor.stream().filter(v -> !mappedColor.containsKey(v)).toList().size() > unusedColours.size()) {
                return false;
            }
            int index = 0;
            for (Vertex vert : toColor) {
                if (mappedColor.containsKey(vert)) {
                    continue;
                }
                List<Integer> available = new ArrayList<>(unusedColours);
                int temp = available.get(index);
                List<Vertex> neighbors = vert.neighbors;
                neighbors.stream().filter(v -> mappedColor.containsKey(v)).forEach(present-> available.remove(mappedColor.get(present)));

                List<Vertex> overLap = neighbors.stream()
                                                .filter(toColor::contains)
                                                .filter(v-> !mappedColor.containsKey(v)).toList();
                if(overLap.isEmpty()){
                    mappedColor.put(vert, temp);
                    continue;
                }

                if(!colorNeighbours(overLap,temp, available)){
                    return false;
                }
            }
        }
        return true;
    }

    private boolean colorNeighbours(List<Vertex> overLap, int temp, List<Integer> available) {
        for(Vertex v : overLap){
            mappedColor.put(v, temp);
            temp++;
            if(temp> available.size()){
                return false;
            }
            temp = available.get(temp);
        }
        return true;
    }

    private void initAvailableColors(int maxColours) {
        for(int i = 1; i <= maxColours+1; i++){
            availableColors.add(i);
        }
    }

    private List<Integer> getUnusedColors(Vertex vertex) {
        List<Integer> unused = new ArrayList<>(availableColors);
        for(Vertex neighbour: vertex.neighbors){
            if(mappedColor.containsKey(neighbour)){
                unused.remove(mappedColor.get(neighbour));
            }
        }
        return unused;
    }

    private void writeSolution() throws IOException {
        System.out.println("___Solution 2:___");
        for(Map.Entry<Vertex, Integer> entry : mappedColor.entrySet()){
            writer.write(mappedString(entry.getKey(), entry.getValue()) + "\n");
            System.out.println("[Vertex: " + entry.getKey().key + ", Color: " + entry.getValue() + "]");
        }
    }

    private String mappedString(Vertex vertex, int value) {
        StringBuilder sb = new StringBuilder();
        sb.append("[Vertex: ").append(vertex.key).append(" , Color:").append(value).append("]");
        return sb.toString();
    }

}
