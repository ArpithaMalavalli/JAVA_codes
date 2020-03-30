package imt2018527;

import animation.Point;
import animation.BBox;

public class myBBox implements BBox{

    public myBBox(Point minPt, Point maxPt) {
        this.minPt = new Point(minPt);
        this.maxPt = new Point(maxPt);
        width = 8;
    }

    public myBBox(myObject ownerObject) {
        this.ownerObject = ownerObject;
        this.minPt = new Point(0, 0);
        this.maxPt = new Point(0, 0);
        width = 8;
    }

    public myBBox(Point position) {

    }

    public boolean intersects(BBox b) {
        boolean xRange = ((minPt.getX() >= b.getMinPt().getX()) && (minPt.getX() <= b.getMaxPt().getX())) 
                            || ((maxPt.getX() >= b.getMinPt().getX()) && (maxPt.getX() <= b.getMaxPt().getX())) ;
        
        boolean yRange = ((minPt.getY() >= b.getMinPt().getY()) && (minPt.getY() <= b.getMaxPt().getY())) 
                            || ((maxPt.getY() >= b.getMinPt().getY()) && (maxPt.getY() <= b.getMaxPt().getY())) ;
                
        return (xRange && yRange);
    }
    
    
    public Point getMinPt() {
        minPt.setPos(ownerObject.getPosition().getX(), ownerObject.getPosition().getY());
        return minPt;
    }
    
    public Point getMaxPt() {
        maxPt.setPos(ownerObject.getPosition().getX() + width, ownerObject.getPosition().getY() + width);
        return maxPt;
    }

    public void setBoxPos(int x, int y) {
        minPt.setPos(x, y);
        maxPt.setPos(x, y);
    }

    public int getWidth() {
        return width;
    }

    public myObject getOwnerObject() {
        return ownerObject;
    }

    private Point minPt;
    private Point maxPt;
    private myObject ownerObject;

    private int width;
}