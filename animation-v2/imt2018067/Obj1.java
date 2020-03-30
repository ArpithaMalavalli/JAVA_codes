package imt2018067;
import java.util.ArrayList;
import java.util.Random;
// import java.lang.*;
import animation.*;

public class Obj1 extends SceneObject
{

    public Obj1(){
        super();
        position = new Point(-1,-1);
        destination = new Point(-1,-1);
        boundary = new RectangularBBox();
    }
    public Obj1(Point initial){
        super();
        position = new Point(initial);
        boundary = new RectangularBBox();
    }
    public Obj1(Point initial,Point dest){
        super();
        position = new Point(initial);
        destination = new Point(dest);
        boundary = new RectangularBBox();
    }
    public String getObjName(){
        return "Rectangle";
    }
    public Point getPosition(){
        return position;
    }
    public void setPosition(int x,int y){
        position.setPos(x,y);
    }
    public void setDestPosition(int x,int y){
        destination.setPos(x,y);
    }
    public BBox getBBox(){
        return boundary;
    }
    class RectangularBBox implements BBox{
        RectangularBBox(){
            minPoint = new Point(position);
            maxPoint = new Point(position.getX()+breadth,position.getY()+length);
        }
        public Point getMinPt(){
            minPoint.setPos(position.getX(), position.getY());
            return minPoint;
        }
        public Point getMaxPt(){
            maxPoint.setPos(position.getX()+breadth,position.getY()+length);
            return maxPoint;
        }
        public boolean intersects(BBox b)
        {
            // Point min = getMinPt(),max = getMaxPt();
            for(Point min : getOutline()){
                if(b.getMinPt().getX() <= min.getX() && b.getMinPt().getY() <= min.getY() && b.getMaxPt().getX() >= min.getX() && b.getMaxPt().getY() >= min.getY())
                    return true;
            }
            // else if(min.getX() <= b.getMaxPt().getX() && min.getY() <= b.getMaxPt().getY() && b.getMaxPt().getX() <= max.getX() && b.getMaxPt().getY() <= max.getY())
            //     return true;
            // else if(b.getMinPt().getX() <= min.getX() && b.getMinPt().getY() <= min.getY() && min.getX() <= b.getMaxPt().getX() && min.getY() <= b.getMaxPt().getY())
            //     return true;
            // else if(b.getMinPt().getX() <= max.getX() && b.getMinPt().getY() <= max.getY() && max.getX() <= b.getMaxPt().getX() && max.getY() <= b.getMaxPt().getY())
            //     return true;
            return false;
        }
        Point minPoint,maxPoint;
    }
    public ArrayList<Point> getOutline(){
        ArrayList<Point> outline = new ArrayList<>();
        outline.add(new Point(position));
        outline.add(new Point(position.getX()+breadth,position.getY()));
        outline.add(new Point(position.getX()+breadth,position.getY()+length));
        outline.add(new Point(position.getX(),position.getY()+length));
        return outline;
    }
    public void updatePos(int deltaT){
        ArrayList<SceneObject> actorArrray = Scene.getScene().getActors();
        ArrayList<SceneObject> obstracleArrray = Scene.getScene().getObstacles();
        ArrayList<Integer> moves = new ArrayList<>();
        Random rand = new Random();
        // if(destination.getY() > position.getY())
        //     moves.add(1); //1 for moving up
        // else if(destination.getY() < position.getY())
        //     moves.add(2); //2 for moving down
        // if(destination.getX() > position.getX())
        //     moves.add(3); //3 moving right
        // else if(destination.getX() < position.getY())
        //     moves.add(4); //4 moving left
        // for(Integer i = 1 ; i <= 4 ; i++){
        //     if(!moves.contains(i))
        //         moves.add(i);
        // }
        if(((position.getX()-destination.getX()>=0 && position.getX()-destination.getX() <= breadth) || 
            (destination.getX()-position.getY() >= 0 && destination.getX()-position.getY() <= breadth)) &&
             ((position.getY()-destination.getY() >= 0 && position.getY()-destination.getY() <= length) || 
             (destination.getY()-position.getY() >= 0 && destination.getY()-position.getY() <= length)))
        {
            System.out.println("reached");
            return;
        }
        if(rand.nextInt(2)==0)
        {
            if(destination.getY() > position.getY() && (moveFail==-1 || moveFail==1))
                moves.add(1); //1 for moving up
            else if(destination.getY() < position.getY() && (moveFail==-1 || moveFail==2))
                moves.add(2); //2 for moving down
            if(destination.getX() > position.getX() && (moveFail==-1 || moveFail==3))
                moves.add(3); //3 moving right
            else if(destination.getX() < position.getX() && (moveFail==-1 || moveFail==4))
                moves.add(4); //4 moving left
        }
        else
        {
            if(destination.getX() > position.getX() && (moveFail==-1 || moveFail==3))
                moves.add(3); //3 moving right
            else if(destination.getX() < position.getX() && (moveFail==-1 || moveFail==4))
                moves.add(4); //4 moving left
            if(destination.getY() > position.getY() && (moveFail==-1 || moveFail==1))
                moves.add(1); //1 for moving up
            else if(destination.getY() < position.getY() && (moveFail==-1 || moveFail==2))
                moves.add(2); //2 for moving down
        }
        if(counterMove != -1){
            if(rand.nextInt(15) == 1){
                System.out.println("occur");
                if(counterMove > 2)
                    counterMove += 2;
                else
                counterMove -= 2;
            }
            moves.add(counterMove);
        }
        for(Integer i = 1 ; i <= 4 ; i++){
           if(!moves.contains(i))
               moves.add(i);
        }
        
        for(Integer i : moves)
            System.out.print(i + " ");
        System.out.println();            
        // for(Integer i = 1 ; i <= 4 ; i++){
        //    if(!moves.contains(i))
        //        moves.add(i);
        // }
        moves.add(0); //In worst case don't move the object all;
        for(Integer move: moves){
            if(move == 1) {
                position.setPos(position.getX(), position.getY()+5*deltaT/500);
                if(validMove(actorArrray, obstracleArrray)){
                    if(moveFail == 1){
                        moveFail = -1;
                        // extraMove(counterMove)
                        position.setPos(position.getX(), position.getY()-2*deltaT/500);
                        counterMove = -1;
                    }
                    break;
                }                
                position.setPos(position.getX(), position.getY()-5*deltaT/500);
                moveFail = 1;
                if(counterMove == -1)
                    counterMove = rand.nextInt(2)+3;
            }
            else if(move == 2) {
                position.setPos(position.getX(), position.getY()-5*deltaT/500);
                if(validMove(actorArrray, obstracleArrray)){
                    if(moveFail == 2){
                        moveFail = -1;
                        counterMove = -1;
                        position.setPos(position.getX(), position.getY()+2*deltaT/500);
                    }
                    break;
                }                
                position.setPos(position.getX(), position.getY()+5*deltaT/500);
                moveFail = 2;
                if(counterMove == -1)
                    counterMove = rand.nextInt(2)+3;            
            }
            else if(move == 3) {
                position.setPos(position.getX()+5*deltaT/500, position.getY());
                if(validMove(actorArrray, obstracleArrray)){
                    if(moveFail == 3){
                        moveFail = -1;
                        counterMove = -1;
                        position.setPos(position.getX()-2*deltaT/500, position.getY());
                    }
                    break;
                }
                position.setPos(position.getX()-5*deltaT/500, position.getY());
                moveFail = 3;
                if(counterMove == -1)
                    counterMove = rand.nextInt(2)+1;
            }
            else if(move == 4) {
                position.setPos(position.getX()-5*deltaT/500, position.getY());
                if(validMove(actorArrray, obstracleArrray)){
                    if(moveFail == 4){
                        moveFail = -1;
                        counterMove = -1;
                        position.setPos(position.getX()+2*deltaT/500, position.getY());
                    }
                    break;
                }
                position.setPos(position.getX()+5*deltaT/500, position.getY());
                moveFail = 4;
                if(counterMove == -1)
                    counterMove = rand.nextInt(2)+1;
                System.out.println(counterMove);
            }
            if(position.getY() < 0)
            {
                position.setPos(position.getX(), 0);
                counterMove = 1;
            }
            if(position.getX() < 0)
            {
                position.setPos(0, position.getY());
                counterMove = 3;
            }
        }
    }
    public boolean validMove(ArrayList<SceneObject> actorArrray,ArrayList<SceneObject> obstracleArrray){
        for(SceneObject samp : actorArrray){
            for(SceneObject obs: obstracleArrray){
                if(samp.getBBox().intersects(obs.getBBox())){
                    return false;
                }
            }
        }
        for(SceneObject samp1 : actorArrray){
            for(SceneObject samp2 : actorArrray){
                if(!samp1.equals(samp2)){
                    if(samp1.getBBox().intersects(samp2.getBBox()))
                        return false;
                }
            }
        }
        return true;
    }
    Point position,destination;
    RectangularBBox boundary;
    final int breadth = 20;
    final int length = 25;
    int moveFail = -1,counterMove = -1;
}