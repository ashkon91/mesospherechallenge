
public class Passenger {
	public int dest;
	public boolean riding; 
	public int direction;
	private int incElevator;
	
	public Passenger(int d, int dir){
		dest = d;
		riding = false;
		if(dir > 0)
			direction = 1;
		else
			direction = -1;
	}
	
	public void setInc(int elev){
		this.incElevator = elev;
	}
	
	public int getInc(){
		return incElevator;
	}
	
}
