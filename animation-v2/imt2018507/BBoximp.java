//IMT2018507
package imt2018507;
import animation.*;
//BBox is the implentation of BBox interface in animation
public class BBoximp implements BBox
{

 public BBoximp(Point a,Point b)
 {
  minimum=a;
  maximum=b;
 }
 
 public boolean intersects(BBox b)
 {
  Point pminimum,pmaximum;
  
  pminimum=b.getMinPt();
  pmaximum=b.getMaxPt();
  if(maximum.getX()<pminimum.getX())
   return false;

  else if(minimum.getY()>pmaximum.getY())
   return false;

  else if(pminimum.getY()>maximum.getY())
   return false;

  else if(pmaximum.getX()<minimum.getX())
   return false;

  else 
   return true;
 }
 private Point minimum;
 private Point maximum;

 public Point getMinPt()
 {
  return minimum;
 }
 public Point getMaxPt()
 {
  return maximum;
 }
}

  
