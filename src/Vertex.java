import java.util.*;

public class Vertex {
    public List<Vertex> neighbors;
    public int key;

    public Vertex(int key){
        this.key = key;
        this.neighbors = new ArrayList<>();
    }

    public void connect(Vertex to){
        this.neighbors.add(to);
    }

    public void connectAll(Vertex...others){
        this.neighbors.addAll(Arrays.stream(others).toList());
    }

    public String neighborsToString() {
        StringBuilder sb = new StringBuilder();
        if(this.neighbors.isEmpty()){
            sb.append("empty");
            return sb.toString();
        }
        for(Vertex v : neighbors){
            sb.append(v.key).append("-");
        }
        return sb.toString();
    }


    @Override
    public String toString() {
        return "Vertex" +
                " "+ key +
                " , neighbors=[ " + neighborsToString() + " ]";
    }
}
