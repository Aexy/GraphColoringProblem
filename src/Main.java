public class Main {

    public static void main(String[] args) {

        Graph cyclicGraph = getCyclicGraph();


        /**     Visual Representation of the Cyclic Graph:
         *     _____(2)_____
         *   /      |       \
         *  (1)----(5)------(3)
         *  \    /    \     /
         *   \  /      \  /
         *    (0)------(4)
         */

        Solver solver = new Solver(cyclicGraph,4);

        try{
            //Backtracking solution
            solver.solve("solution.txt");
        }catch(Exception e){
            System.out.println("unexpected exception");
            e.printStackTrace();
        }

        try{
            //Alternative solution
            solver.solve2("solution2.txt");
        }catch(Exception e){
            System.out.println("unexpected exception");
            e.printStackTrace();
        }
    }

    private static Graph getCyclicGraph() {
        Graph cyclicGraph = new Graph("Example");
        cyclicGraph.addVertex(0);
        cyclicGraph.addVertex(1);
        cyclicGraph.addVertex(2);
        cyclicGraph.addVertex(3);
        cyclicGraph.addVertex(4);
        cyclicGraph.addVertex(5);

        Vertex v0 = cyclicGraph.getVertex(0);
        Vertex v1 = cyclicGraph.getVertex(1);
        Vertex v2 = cyclicGraph.getVertex(2);
        Vertex v3 = cyclicGraph.getVertex(3);
        Vertex v4 = cyclicGraph.getVertex(4);
        Vertex v5 = cyclicGraph.getVertex(5);

        cyclicGraph.connect(v0, v1);
        cyclicGraph.connect(v1, v2);
        cyclicGraph.connect(v2, v3);
        cyclicGraph.connect(v3, v4);
        cyclicGraph.connect(v4, v0);

        cyclicGraph.connect(v5, v0);
        cyclicGraph.connect(v5, v1);
        cyclicGraph.connect(v5, v3);
        cyclicGraph.connect(v5, v4);
        cyclicGraph.connect(v5, v0);
        return cyclicGraph;
    }
}