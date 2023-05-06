package marp.datastructures;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.PriorityQueue;
import java.util.Stack;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import marp.mapelements.Road;
import marp.mapelements.RoadNode;
import marp.utilities.MathFunctions;

public class Digraph implements Serializable {
    ArrayList<Road> roads;
    HashMap<Long, Road> roadsMap;
    HashMap<Long, RoadNode> nodes;
    ArrayList<ArrayList<Edge>> connectedComponents;
    ArrayList<Edge> navigation;

    public Digraph(ArrayList<Road> roads, HashMap<Long, RoadNode> nodes) {
        this.nodes = nodes;
        this.roadsMap = new HashMap<>();
        this.roads = roads;
        for (Road road : roads) {
            roadsMap.put(road.getID(), road);
            for (int i = 0; i < road.getNodeSize()-1; i++) {
                RoadNode node1 = road.getNode(i);
                RoadNode node2 = road.getNode(i + 1);
                node1.addEdge(
                    new Edge(node1.getID(), node2.getID(), road.getID(), road.isDriveable(), road.isWalkable())
                );
                if(!road.isOneWay()) node2.addEdge(
                    new Edge(node2.getID(), node1.getID(), road.getID(), road.isDriveable(), road.isWalkable())
                );
            }
        }
        connectedComponents = categorizeEdgesOnConnectedComponents();
        ArrayList<RoadNode> roadNodesAsArray = new ArrayList<>(nodes.values());
        aStar(roadNodesAsArray.get(100), roadNodesAsArray.get(1000), true);
    }

    private float getWeight(Edge edge, boolean walking){
        if(walking && !edge.isWalkable()) return Float.MAX_VALUE;
        else if(!walking && !edge.isDriveable()) return Float.MAX_VALUE;
        else if(walking) return (float) MathFunctions.distanceInMeters(nodes.get(edge.start).getX(), nodes.get(edge.start).getY(), nodes.get(edge.end).getX(), nodes.get(edge.end).getY());
        else return (float) MathFunctions.distanceInMeters(nodes.get(edge.start).getX(), nodes.get(edge.start).getY(), nodes.get(edge.end).getX(), nodes.get(edge.end).getY())/roadsMap.get(edge.road).getSpeed();
    }

    private ArrayList<ArrayList<Edge>> categorizeEdgesOnConnectedComponents(){
        ArrayList<ArrayList<Edge>> edges = new ArrayList<>();
        HashSet<Long> visited = new HashSet<>();
        for (RoadNode node : nodes.values()) {
            if(!visited.contains(node.getID())){
                ArrayList<Edge> component = new ArrayList<>();
                Stack<Long> stack = new Stack<>();
                stack.push(node.getID());
                visited.add(node.getID());
                while(!stack.isEmpty()){
                    RoadNode currentNode = nodes.get(stack.pop());
                    for (Edge edge : currentNode.getEdges()) {
                        if(!visited.contains(edge.end)){
                            visited.add(edge.end);
                            stack.push(edge.end);
                        }
                        component.add(edge);
                    }
                }
                edges.add(component);
            }
        }
        return edges;
    }

    public void aStar(RoadNode start, RoadNode end, boolean walkable){
        HashSet<Long> closedSet = new HashSet<>();
        HashMap<Long, Long> cameFrom = new HashMap<>();
        HashMap<Long, Float> gScore = new HashMap<>();
        HashMap<Long, Float> fScore = new HashMap<>();
        PriorityQueue<RoadNode> openSet = new PriorityQueue<>(new Comparator<RoadNode>(){
            @Override
            public int compare(RoadNode o1, RoadNode o2) {
                return Float.compare(fScore.get(o1.getID()), fScore.get(o2.getID()));
            }
        } );
        for (RoadNode node : nodes.values()) {
            gScore.put(node.getID(), Float.MAX_VALUE);
            fScore.put(node.getID(), Float.MAX_VALUE);
        }
        gScore.put(start.getID(), 0f);
        fScore.put(start.getID(), (float) MathFunctions.distanceInMeters(start.getX(), start.getY(), end.getX(), end.getY()));
        openSet.add(start);
        while(!openSet.isEmpty()){
            RoadNode current = openSet.poll();
            if(current.getID() == end.getID()){
                navigation = reconstructPath(cameFrom, current.getID());
                return;
            }
            closedSet.add(current.getID());
            for (Edge edge : current.getEdges()) {
                if(!closedSet.contains(edge.end)){
                    float tentativeGScore = gScore.get(current.getID()) + getWeight(edge, walkable);
                    if(tentativeGScore < gScore.get(edge.end)){
                        cameFrom.put(edge.end, current.getID());
                        gScore.put(edge.end, tentativeGScore);
                        fScore.put(edge.end, tentativeGScore + (float) MathFunctions.distanceInMeters(nodes.get(edge.end).getX(), nodes.get(edge.end).getY(), end.getX(), end.getY()));
                        if(!openSet.contains(nodes.get(edge.end))) openSet.add(nodes.get(edge.end));
                    }
                }
            }
        }
    }

    private ArrayList<Edge> reconstructPath(HashMap<Long, Long> cameFrom, long id) {
        ArrayList<Edge> path = new ArrayList<>();
        Stack<Long> stack = new Stack<>();
        stack.push(id);
        while(cameFrom.containsKey(id)){
            id = cameFrom.get(id);
            stack.push(id);
        }
        while(!stack.isEmpty()){
            long id1 = stack.pop();
            long id2 = stack.isEmpty() ? -1 : stack.peek();
            for (Edge edge : nodes.get(id1).getEdges()) {
                if(edge.end == id2){
                    path.add(edge);
                    break;
                }
            }
        }
        return path;
    }

    public void draw(GraphicsContext gc) {
        // drawNavigation(gc);
    }

    public void drawNavigation(GraphicsContext gc){
        gc.setStroke(Color.RED);
        for (Edge edge : navigation) {
            gc.strokeLine(nodes.get(edge.start).getX(), nodes.get(edge.start).getY(), nodes.get(edge.end).getX(), nodes.get(edge.end).getY());
        }
    }

    public void drawConnectedComponents(GraphicsContext gc){
        for (ArrayList<Edge> arrayList : connectedComponents) {
            gc.setStroke(Color.rgb((int)(Math.random()*255), (int)(Math.random()*255), (int)(Math.random()*255)));
            for (Edge edge : arrayList) {
                gc.strokeLine(nodes.get(edge.start).getX(), nodes.get(edge.start).getY(), nodes.get(edge.end).getX(), nodes.get(edge.end).getY());
            }
        }
    }
}
