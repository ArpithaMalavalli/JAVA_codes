//IMT2018507
package imt2018507;

import animation.*;
import java.util.ArrayList;
//SceneObjectext is the derived class of SceneObject class from animation

public class SceneObjectext extends SceneObject
{

 public SceneObjectext()
 {
  objectvalue+=1;
  objectname="H"+(objectvalue);
  position=new Point(0,0);
  destination=new Point(0,0);
 }
 public void setPosition(int x, int y)
 {
  position.setPos(x,y);
 }

 public void setDestPosition(int x, int y)
 {
  destination.setPos(x,y);
 }


 public String getObjName()
 {
  return objectname;
 }

 public Point getPosition()
 {
  return position;
 }
 
 public ArrayList<Point> getOutline()
 {  // it returns an arraylist with the 4 border points of the object
  ArrayList<Point> out=new ArrayList<>(4);
  out.add(new Point(position));
  out.add(new Point(position.getX()+10,position.getY()));
  out.add(new Point(position.getX()+10,position.getY()+5));
  out.add(new Point(position.getX(),position.getY()+5));

  return out;
 }
 
 public BBox getBBox() //it returns BBox with max and min points
 {
  return new BBoximp(new Point(position),new Point(position.getX()+10,position.getY()+5));
 }
 @Override
 public void updatePos(int deltaT) 
 {
 if(Math.abs(destination.getX()-position.getX())<Math.abs(20) && Math.abs(destination.getY()-position.getY())<Math.abs(20))
   Scene.getScene().getActors().remove(this);
 if (position.getX()<0 || position.getY()<0)
   Scene.getScene().getActors().remove(this);  
  Scene s;
  s=Scene.getScene();
  float dx,dy;
  if(destination.getX() == position.getX()){
    dx = 0;
    dy=20;
    if(destination.getY()<position.getY())
     dy*=-1;

  }
  else{
  float tan=(destination.getY()-(float)(position.getY()))/(destination.getX()-(float)(position.getX()));

  if(tan>1)
  {
   dy=20;
   if(destination.getY()<position.getY())
    dy*=-1;
   dx=dy/tan;
  }
  else
  {
   dx=20;
   if(destination.getX()<position.getX())
    dx*=-1;
   dy=dx*tan;
  }}

  boolean colide=false;
  BBoximp k;
  Point pnt1;
  Point pnt2;
  ArrayList<SceneObject> lst = new ArrayList<SceneObject>();
  lst.addAll(s.getObstacles());
  lst.addAll(s.getActors());

  for(SceneObject sc : lst)
  {
   if(sc.getBBox().intersects( k=new BBoximp(pnt1= new Point(getPosition().getX()+(int)dx,getPosition().getY()+(int)dy) ,pnt2= new Point(getPosition().getX()+(int)dx+10,getPosition().getY()+(int)dy+5)) ) )
    colide=true;
  }
  while(colide)
  {
  for(SceneObject sc : s.getObstacles())
  {
   if(sc.getBBox().intersects( k=new BBoximp(pnt1= new Point(getPosition().getX()+(int)dx,getPosition().getY()+(int)dy) ,pnt2= new Point(getPosition().getX()+(int)dx+10,getPosition().getY()+(int)dy+5)) ) )
   {
    colide=true;
    break;
   }
   else
   {
    colide=false;
   }
  }
  if(way==0)
   dx=-dx;     
  else if(way==1)
  {
   dx=-dx;
   dy=-dy;
  }

  else if(way==2)
  {
   dx=0;
   dy=0;
   break;
  }
  way+=1;
 }
  	

    setPosition(getPosition().getX()+(int)dx,getPosition().getY()+(int)dy);
    way=0;
    if(destination.getX() == position.getX()){
      dx = 0;
      dy=20;
      if(destination.getY()<position.getY())
       dy*=-1;
  
    }
    else{
    float tan=(destination.getY()-(float)(position.getY()))/(destination.getX()-(float)(position.getX()));
  
    if(tan>1)
    {
     dy=20;
     if(destination.getY()<position.getY())
      dy*=-1;
     dx=dy/tan;
    }
    else
    {
     dx=20;
     if(destination.getX()<position.getX())
      dx*=-1;
     dy=dx*tan;
    }}

  
 }
 private static int objectvalue=1;
 private static int way=0;
 private String objectname;
 private Point position;
 private Point destination;
 
}
