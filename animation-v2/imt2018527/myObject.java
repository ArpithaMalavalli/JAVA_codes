package imt2018527;

import java.util.ArrayList;
import java.util.Random;

import animation.BBox;
import animation.Point;
import animation.Scene;
import animation.SceneObject;

public class myObject extends SceneObject {

    static {
        myObject.newId = 0;
    }

    //constructor
    public myObject() {
        this.id = new Integer(myObject.newId++);
        this.speed = 20;
        this.position = new Point(0, 0);
        this.destination = new Point(0, 0);
        this.bBox = new myBBox(this);
        this.name = id.toString();

        directions = new ArrayList<Point>();
        directions.add(new Point(speed, 0)); directions.add(new Point(0, speed)); 
        directions.add(new Point(-1*speed, 0)); directions.add(new Point(0, -1*speed));
        directions.add(new Point(0, 0));

        this.points = new ArrayList<Point>();

    }

    public String getObjName() {
        return name;
    }

    @Override
    public String toString() {
        return name;
    }

    public Point getPosition() {
        return position;
    }

    public void setPosition(int x, int y) {
        position.setPos(x, y);
        ((myBBox) bBox).setBoxPos(x, y);
    }

    public void setDestPosition(int x, int y) {
        destination = new Point(x, y);
    }

    public BBox getBBox() {
        return bBox;
    }

    protected ArrayList<Point> getOutline() {        
        points.add(bBox.getMinPt());
        points.add(new Point(bBox.getMinPt().getX()+((myBBox) bBox).getWidth(), bBox.getMinPt().getY()));
        points.add(bBox.getMaxPt());
        points.add(new Point(bBox.getMaxPt().getX()-((myBBox) bBox).getWidth(), bBox.getMaxPt().getY()));
        return points;
    }

    protected void updatePos(int deltaT) {
        if ((destination.getX()>=position.getX()- speed) && 
            (destination.getX()<=position.getX()+speed) &&
            (destination.getY()>=position.getY()-speed) &&
            (destination.getY()<=position.getY()+speed)) {
                name = "reached" + id;
                return;
        }

        Random rand = new Random();

        Point expectedPosition = new Point(0, 0); //the next expected position
        Point move = new Point(0, 0);
        boolean safeMove = false;

        //try right-left vs up-down
        ArrayList<Integer> directionIndex = new ArrayList<Integer>();
        int a = (rand.nextInt(1000))%2;
        if (a==0) {                //try right-left first   
            directionIndex.add(0);
            directionIndex.add(2);
            directionIndex.add(1);
            directionIndex.add(3);
        }
        else {                     //try up-down first
            directionIndex.add(1);
            directionIndex.add(3);
            directionIndex.add(0);
            directionIndex.add(2);
        }
        directionIndex.add(4);

        if (a==0) {     //right-left
            if (position.getX()<destination.getX()) {
                move = directions.get(directionIndex.get(0));
                expectedPosition.setPos(position.getX()+move.getX(), position.getY()+move.getY());
                if (safe(expectedPosition)) {
                    safeMove = true;
                    position.setPos(expectedPosition.getX(), expectedPosition.getY());
                    return;
                }
            }
            if (position.getX()>destination.getX() && (safeMove==false)) {
                move = directions.get(directionIndex.get(1));
                expectedPosition.setPos(position.getX()+move.getX(), position.getY()+move.getY());
                if (safe(expectedPosition)) {
                    safeMove = true;
                    position.setPos(expectedPosition.getX(), expectedPosition.getY());
                    return;
                }
            }
            if (position.getY()<destination.getY() || (safeMove==false)) {
                move = directions.get(directionIndex.get(2));
                expectedPosition.setPos(position.getX()+move.getX(), position.getY()+move.getY());
                if (safe(expectedPosition)) {
                    safeMove = true;
                    position.setPos(expectedPosition.getX(), expectedPosition.getY());
                    return;
                }
            }
            if (position.getY()>destination.getY() && (safeMove==false)) {
                move = directions.get(directionIndex.get(3));
                expectedPosition.setPos(position.getX()+move.getX(), position.getY()+move.getY());
                if (safe(expectedPosition)) {
                    safeMove = true;
                    position.setPos(expectedPosition.getX(), expectedPosition.getY());
                    return;
                }
            }
            else {
                expectedPosition.setPos(position.getX(), position.getY());
            }
        }
        else {        //up-down
            if (position.getY()<destination.getY()) {
                move = directions.get(directionIndex.get(0));
                expectedPosition.setPos(position.getX()+move.getX(), position.getY()+move.getY());
                if (safe(expectedPosition)) {
                    safeMove = true;
                    position.setPos(expectedPosition.getX(), expectedPosition.getY());
                    return;
                }
            }
            else if (position.getY()>destination.getY() && (safeMove==false)) {
                move = directions.get(directionIndex.get(1));
                expectedPosition.setPos(position.getX()+move.getX(), position.getY()+move.getY());
                if (safe(expectedPosition)) {
                    safeMove = true;
                    position.setPos(expectedPosition.getX(), expectedPosition.getY());
                    return;
                }
            }
            else if (position.getX()<destination.getX() || (safeMove==false)) {
                move = directions.get(directionIndex.get(2));
                expectedPosition.setPos(position.getX()+move.getX(), position.getY()+move.getY());
                if (safe(expectedPosition)) {
                    safeMove = true;
                    position.setPos(expectedPosition.getX(), expectedPosition.getY());
                    return;
                }
            } 
            else if (position.getX()>destination.getX() && (safeMove==false)) {
                move = directions.get(directionIndex.get(3));
                expectedPosition.setPos(position.getX()+move.getX(), position.getY()+move.getY());
                if (safe(expectedPosition)) {
                    safeMove = true;
                    position.setPos(expectedPosition.getX(), expectedPosition.getY());
                    return;
                }
            }
            else {
                expectedPosition.setPos(position.getX(), position.getY());
            }
        }       
    }

    public boolean safe(Point position) {
        ArrayList<SceneObject> obstacles = Scene.getScene().getObstacles();
        ArrayList<SceneObject> actors = Scene.getScene().getActors();

        myBBox expectedBBox = new myBBox(position, new Point(position.getX()+ ((myBBox) bBox).getWidth() + 1, position.getY() + ((myBBox) bBox).getWidth() + 1));
        
        for (SceneObject obstacle: obstacles) {
            if (expectedBBox.intersects(obstacle.getBBox())) {
                return false;
            }
            
        }

        for (SceneObject actor: actors) {
            if (!this.equals(actor)) {
                if (expectedBBox.intersects(actor.getBBox())) {
                    return false;
                }                        
            }
        }
        return true;
    }

    
    private static int newId;
    private Integer id;    
    private String name;
    private Point position;
    private Point destination;
    private int speed;
    private BBox bBox;
    private ArrayList<Point> points; 

    private ArrayList<Point> directions;
}