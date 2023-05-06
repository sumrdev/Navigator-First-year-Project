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
    HashSet<RoadNode> closedSet = new HashSet<>();
    int averageSpeedCount;
    float averageSpeed;

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

    public void setAverageSpeed(int speed){
        float temp = averageSpeed*averageSpeedCount;
        temp+=speed*10;
        this.averageSpeedCount++;
        this.averageSpeed = temp/averageSpeedCount;
        this.averageSpeed = 70;
    }

    public void aStar(RoadNode start, RoadNode end, boolean walking){
        averageSpeedCount = 0;
        averageSpeed = 0;
        HashMap<RoadNode, RoadNode> cameFrom = new HashMap<>();
        HashMap<RoadNode, Float> gScore = new HashMap<>();
        HashMap<RoadNode, Float> fScore = new HashMap<>();
        PriorityQueue<RoadNode> openSetQueue = new PriorityQueue<>(new Comparator<RoadNode>() {
            @Override
            public int compare(RoadNode o1, RoadNode o2) {
                return fScore.get(o1).compareTo(fScore.get(o2));
            }
        });
        gScore.put(start, 0f);   
        fScore.put(start, gScore.get(start) + (float) MathFunctions.distanceInMeters(start.getX(), start.getY(), end.getX(), end.getY()));
        
        openSetQueue.add(start);
        while(!openSetQueue.isEmpty()){
            RoadNode current = openSetQueue.poll();
            if(current.getID() == end.getID()){
                reconstructPath(cameFrom, current);
                return;
            }
            closedSet.add(current);
            for (Edge edge : current.getEdges()) {
                if(!closedSet.contains(nodes.get(edge.end))){
                    setAverageSpeed(roadsMap.get(edge.road).getSpeed());
                    float tentativeGScore = gScore.get(current) + getWeight(edge, walking);
                    if(gScore.get(nodes.get(edge.end)) != null && tentativeGScore >= gScore.get(nodes.get(edge.end))) continue;
                    cameFrom.put(nodes.get(edge.end), current);
                    gScore.put(nodes.get(edge.end), tentativeGScore);
                    fScore.put(nodes.get(edge.end), gScore.get(nodes.get(edge.end)) + getHScore(nodes.get(edge.end), end, walking));
                    if(!openSetQueue.contains(nodes.get(edge.end))) openSetQueue.add(nodes.get(edge.end));
                }
            }
        }
        System.out.println("No path found");
    }

    private float getHScore(RoadNode start, RoadNode end, boolean walking){
        if(walking) return (float) MathFunctions.distanceInMeters(start.getX(), start.getY(), end.getX(), end.getY());
        else return (float) MathFunctions.distanceInMeters(start.getX(), start.getY(), end.getX(), end.getY())/averageSpeed;
    }

    private void reconstructPath(HashMap<RoadNode, RoadNode> cameFrom, RoadNode current){
        navigation = new ArrayList<>();
        while(cameFrom.containsKey(current)){
            for (Edge edge : current.getEdges()) {
                if(edge.end == cameFrom.get(current).getID()){
                    navigation.add(edge);
                    break;
                }
            }
            current = cameFrom.get(current);
        }
    }

    public void draw(GraphicsContext gc) {
        drawNavigation(gc);
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
