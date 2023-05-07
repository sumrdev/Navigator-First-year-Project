package marp.datastructures;

import java.io.Serializable;
import java.sql.Time;
import java.util.*;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import marp.mapelements.Road;
import marp.mapelements.RoadNode;
import marp.utilities.MathFunctions;

public class Digraph implements Serializable {
    boolean notInCar;
    ArrayList<Road> roads;
    HashMap<Long, Road> roadsMap;
    HashMap<Long, RoadNode> nodes;
    ArrayList<ArrayList<Edge>> connectedComponents;
    ArrayList<Edge> navigation;
    HashSet<RoadNode> closedSet;
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
        createTextDescriptionFromNavigation();
    }

    private float getWeight(Edge edge, boolean notInCar){
        if(notInCar && !edge.isWalkable()) return Float.MAX_VALUE;
        else if(!notInCar && !edge.isDriveable()) return Float.MAX_VALUE;
        else if(notInCar) return (float) MathFunctions.distanceInMeters(nodes.get(edge.start).getX(), nodes.get(edge.start).getY(), nodes.get(edge.end).getX(), nodes.get(edge.end).getY());
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
    public List<String> aStar(RoadNode start, RoadNode end){
        return aStar(start, end, walking);
    }
    public List<String> aStar(RoadNode start, RoadNode end, boolean walking){
        Time startTime = new Time(System.currentTimeMillis());
        averageSpeedCount = 0;
        averageSpeed = 0;
        closedSet = new HashSet<>();
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
                Time endTime = new Time(System.currentTimeMillis());
                System.out.println("Ran astar with : " + this.nodes.size() + " nodes in " + (endTime.getTime() - startTime.getTime())/1000 + " s");
                return createTextDescriptionFromNavigation();
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
        String info = "No path found";
        System.out.println(info);

        return new ArrayList<>();
    }

    private float getWeight(Edge edge, boolean walking){
        if(walking && !edge.isWalkable()) return Float.MAX_VALUE;
        else if(!walking && !edge.isDriveable()) return Float.MAX_VALUE;
        else if(walking) return (float) MathFunctions.distanceInMeters(nodes.get(edge.start).getX(), nodes.get(edge.start).getY(), nodes.get(edge.end).getX(), nodes.get(edge.end).getY());
        else return (float) MathFunctions.distanceInMeters(nodes.get(edge.start).getX(), nodes.get(edge.start).getY(), nodes.get(edge.end).getX(), nodes.get(edge.end).getY())/roadsMap.get(edge.road).getSpeed();
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

    public List<String> createTextDescriptionFromNavigation(){
        List<String> result= new ArrayList<>();
        String previousRoad = "";
        int distanceSinceLastRoad = 0;

        for (int i = 0; i < navigation.size(); i++) {
            Edge edge = navigation.get(i);
            if(!previousRoad.equals(roadsMap.get(edge.road).getName())){
                if(!previousRoad.equals("")){
                    String turnInformation;
                    switch (getTurnInformation(navigation.get(i-1), navigation.get(i))) {
                        case 0:
                            turnInformation = "Continue straight onto ";
                            break;
                        case 1:
                            turnInformation = "Turn right on ";
                            break;
                        case 2:
                            turnInformation = "Turn left on ";
                            break;
                        case 3:
                            turnInformation = "Turn around on ";
                            break;
                        default:
                            turnInformation = "Continue straight onto ";
                            break;
                    }
                    String direction = turnInformation + roadsMap.get(edge.road).getName() + " after " + distanceSinceLastRoad + " meters";
                    result.add(direction);
                } else {
                    String direction = "Start on " + roadsMap.get(edge.road).getName();
                    result.add(direction);
                }
                previousRoad = roadsMap.get(edge.road).getName();
                distanceSinceLastRoad = 0;
            }
            distanceSinceLastRoad += MathFunctions.distanceInMeters(nodes.get(edge.start).getX(), nodes.get(edge.start).getY(), nodes.get(edge.end).getX(), nodes.get(edge.end).getY());
        }
        return result;
    }

    public int getTurnInformation(Edge e1, Edge e2){
        float x1 = nodes.get(e1.start).getX();
        float y1 = nodes.get(e1.start).getY();
        float x2 = nodes.get(e1.end).getX();
        float y2 = nodes.get(e1.end).getY();
        float x3 = nodes.get(e2.end).getX();
        float y3 = nodes.get(e2.end).getY();
        float x4 = nodes.get(e2.start).getX();
        float y4 = nodes.get(e2.start).getY();

        int angle = MathFunctions.getAngleBetweenTwoLines(x1, y1, x2, y2, x3, y3, x4, y4 );
        if(angle > 0 && angle < 180) return 1;
        else if(angle < 0 && angle > -180) return 2;
        else if(angle == 180 || angle == -180) return 3;
        else return 0;
    }
    public void draw(GraphicsContext gc) {
        drawNavigation(gc);
    }
    public void drawNavigation(GraphicsContext gc){
        gc.setStroke(Color.rgb(192, 48, 48));
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

    public void clearNavigation() {
        navigation.clear();
    }

    public float getDistance() {
        float distance = 0;
        for (Edge edge : navigation) {
            distance = (float) (distance + MathFunctions.distanceInMeters((float) (nodes.get(edge.start).getX()*0.56), nodes.get(edge.start).getY(), (float) (nodes.get(edge.end).getX()*0.56), nodes.get(edge.end).getY()));
            System.out.println("The distance of the edge is " + MathFunctions.distanceInMeters((float) (nodes.get(edge.start).getX()*0.56), nodes.get(edge.start).getY(), (float) (nodes.get(edge.end).getX()*0.56), nodes.get(edge.end).getY()));
        }
        return Math.round((distance/1000.0));
    }

    public int getTravelTime(int transportationMethod) {
        double travelTime = 0;
        for (Edge edge : navigation) {
            if (transportationMethod == 0) {
                if (getWeight(edge, false) < Float.MAX_VALUE) {
                    travelTime = travelTime + (getWeight(edge, false));
                }
            }
            else if (transportationMethod == 1) {
                travelTime = travelTime + ((getWeight(edge, true)/5000)*60);
                System.out.println(getWeight(edge, true));
            } else if (transportationMethod == 2) {
                travelTime = travelTime + ((getWeight(edge, true)/20000)*60);
            }
        }
        return (int) travelTime;
    }
}
