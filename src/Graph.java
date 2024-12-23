import com.sun.source.tree.ReturnTree;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class Graph {
    public String name;
    public List<Vertex> vertices;
    public Set<Integer> keys;


    public Graph(String name){
        this.name = name;
        this.vertices = new ArrayList<Vertex>();
        this.keys = new HashSet<>();
    }

    public void addVertex(int key){
        if(this.keys.contains(key)){
            return;
        }
        this.vertices.add(new Vertex(key));
        this.keys.add(key);
    }

    public void connect(Vertex from, Vertex to){
        if(this.vertices.contains(from) && this.vertices.contains(to)){
            from.connect(to);
            to.connect(from);
        }
    }

    public void removeVertex(Vertex v){
        for(Vertex v1 : v.neighbors){
            v1.neighbors.remove(v);
        }
        this.vertices.remove(v);
        System.out.println("the vertex with the key: "+ v.key + " is no more contained. Contained: " +this.vertices.contains(v));
    }

    public Vertex getVertex(int key){
        for(Vertex v : this.vertices){
            if(v.key == key){
                return v;
            }
        }
        return null;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Graph: "+ this.name + "\n");
        if(this.vertices.isEmpty()){
            sb.append(" Vertices: EMPTY\n");
            return sb.toString();
        }
        sb.append("Vertices: { " + "\n");
        for(Vertex v : this.vertices){
            sb.append( "vertex (" +v.key+")");
            sb.append(v.neighborsToString());
            sb.append(", " + "\n");
        }
        sb.append("} ");
        return sb.toString();
    }
}
