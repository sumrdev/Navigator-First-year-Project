package marp;

import java.util.ArrayList;
import java.util.List;
import javafx.scene.canvas.GraphicsContext;

public class Way {
    List<Node> nodes = new ArrayList<Node>();
    
    Way(List<Node> nodes){
        this.nodes = nodes;
    }

    void addNode(Node node){
        nodes.add(node);
    }

    void draw( GraphicsContext gc){
        for(int i = 0; i < nodes.size() - 1; i++){
            Node n1 = nodes.get(i);
            Node n2 = nodes.get(i+1);
            gc.strokeLine(n1.lon, n1.lat, n2.lon, n2.lat);
        }
    }
}
