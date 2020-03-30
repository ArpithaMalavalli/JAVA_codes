package imt2018057;

import java.util.ArrayList;

import animation.BBox;
import animation.Point;
import animation.SceneObject;

public class TestObject extends SceneObject implements BBox
{
  
private ArrayList<Point>outLine = new ArrayList<Point>(0);
	private Point curr_p;
	private Point des;
	private int size;
	private String obj_name;
	private static int obj_id=1;


	public TestObject(){
		size = 10;
		obj_name = ("SceneObj-"+Integer.toString(obj_id));
		obj_id++;

		curr_p = new Point(0,0);
		des = new Point(0,0);

		outLine.add(new Point(0, 0));
        outLine.add(new Point(0, 10));
        outLine.add(new Point(10, 10));
        outLine.add(new Point(10, 0));
	}

	@Override
	public String getObjName() {
		return obj_name;
	}

	@Override
	public Point getPosition(){
		return curr_p;
	}

	@Override
	public void setPosition(int x, int y){
		curr_p.setPos(x,y);
	}

	public void setDestPosition(int x, int y) {
		des.setPos(x,y);
	}

	public Point getMinPt(){
		return curr_p;
	}

	public Point getMaxPt(){
		Point temp = new Point(0,0);
		temp.setPos(curr_p.getX()+size,curr_p.getY()+size);
		return temp;
	}

	public boolean intersects(BBox b){
		Point min_p = getMinPt();
		Point max_p = getMaxPt();
		Point b_min = b.getMinPt();
		Point b_max = b.getMaxPt();

		if((b_min.getX()-min_p.getX())*(b_min.getX()-max_p.getX())<0){
			if((b_min.getY()-min_p.getY())*(b_min.getY()-max_p.getY())<0){return true; }
		}
		if((b_max.getX()-min_p.getX())*(b_max.getX()-max_p.getX())<0){
			if((b_max.getY()-min_p.getY())*(b_max.getY()-max_p.getY())<0){return true; }
		}
		return false;
	}
	
	
	@Override
	public BBox getBBox() {
		BBox temp = this;
		return temp;
	}
	

	@Override
	protected ArrayList<Point> getOutline() {
		return outLine;
	}

	@Override
	protected void updatePos(int deltaT){
		//int ds = deltaT/100*2;
		int ds = 10;

		if(des.getX()!=curr_p.getX()){
			double tanO = Math.abs((des.getY()-curr_p.getY())/(des.getX()-curr_p.getX()));
		 	double tan2O = tanO*tanO;
		 	int chg_x,chg_y;
		 	chg_x=(int)(ds/Math.sqrt(1+tan2O));
		 	chg_y=(int)(chg_x*tanO);

		 	if(curr_p.getX()<des.getX()){ 
		 		curr_p.setPos(curr_p.getX()+chg_x,curr_p.getY());
		 		if(curr_p.getX()>des.getX()){curr_p.setPos(des.getX(),curr_p.getY()); }
		 	}
		 	else if(curr_p.getX()>des.getX()){ 
		 		curr_p.setPos(curr_p.getX()-chg_x,curr_p.getY());
		 		if(curr_p.getX()<des.getX()){curr_p.setPos(des.getX(),curr_p.getY()); }
		 	}

		 	if(curr_p.getY()<des.getY()){ 
		 		curr_p.setPos(curr_p.getX(),curr_p.getY()+chg_y);
		 		if(curr_p.getY()>des.getY()){curr_p.setPos(curr_p.getX(),des.getY()); }
		 	}
		 	else if(curr_p.getY()>des.getY()){ 
		 		curr_p.setPos(curr_p.getX(),curr_p.getY()-chg_y);
		 		if(curr_p.getY()<des.getY()){curr_p.setPos(curr_p.getX(),des.getY()); }
		 	}
		}
		else{
		 	if(curr_p.getY()<des.getY()){ 
		 		curr_p.setPos(curr_p.getX(),curr_p.getY()+ds);
		 		if(curr_p.getY()>des.getY()){curr_p.setPos(curr_p.getX(),des.getY()); }
		 	}
		 	else if(curr_p.getY()>des.getY()){ 
		 		curr_p.setPos(curr_p.getX(),curr_p.getY()-ds);
		 		if(curr_p.getY()<des.getY()){curr_p.setPos(curr_p.getX(),des.getY()); }
		 	}
		}
	}
		
	@Override
	public String toString(){
        return "[( " + curr_p.getX() + " ," + curr_p.getY() + " ), ( " + curr_p.getX()+size + " , " + curr_p.getY()+size + " )]";
    }

}