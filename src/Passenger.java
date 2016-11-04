
public class Passenger {
	public int dest;
	public boolean riding; 
	public int direction;
	private int incElevator;
	
	//Passenger constructor
	public Passenger(int d, int dir){
		dest = d;
		riding = false;
		if(dir > 0)
			direction = 1;
		else
			direction = -1;
		incElevator = -1;
	}
	//Sets the incoming elevator for the passenger
	public void setInc(int elev){
		this.incElevator = elev;
	}
	
	//getter for incoming elevator
	public int getInc(){
		return incElevator;
	}
	
}
