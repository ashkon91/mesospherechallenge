import java.util.ArrayList;

public class Elevator {
	public int elevatorId;
	public int currFloor;
	public int goalFloor;
	public int direction; //-1 = down , 0 = idle, 1 = up;
	
	public ArrayList<Passenger> passengers = new ArrayList<>();
	
	public Elevator(int id, int currFloor){
		this.elevatorId = id;
		this.currFloor = currFloor;
		this.direction = 0;
		this.goalFloor = -1;
	}
	
	public void addRider(Passenger p){
		if(direction == 1 && p.dest > goalFloor) 
			goalFloor = p.dest;
		else if( direction == -1 && p.dest < goalFloor ) 
			goalFloor = p.dest;
		
		passengers.add(p);
	}
	
	public void changeDirection(int dir){
		if(dir > -2 && dir < 2)
			this.direction = dir;
		else
			System.out.println("Invalid durection for Elevator: " + this.elevatorId);
	}
	
	public void elevatorStep(int dir){
		if(this.hasRiderForCurrDestination())
			return;
		currFloor += dir;

	}
	
	public boolean inUse(){
		if(passengers.isEmpty() && direction == 0)
			return false;
		else
			return true;
	}
	
	public boolean isEmpty(){
		if(passengers.isEmpty()){
			direction = 0;
			goalFloor = -1;
			return true;
		}
		else 
			return false;
	}
	
	public void deschedule(){
		direction = 0;
		goalFloor = -1;
	}
	
	//Checks for someone leaving and stops elevator for them
	public boolean hasRiderForCurrDestination(){
		int initSize = passengers.size();
		
		ArrayList<Passenger> staying = new ArrayList<>();
		ArrayList<Passenger> leaving = new ArrayList<>();
		
		for(int x =0 ; x< passengers.size(); x++){
			if(this.currFloor == passengers.get(x).dest){
				leaving.add(passengers.get(x));
			}
			else{
				staying.add(passengers.get(x));
			}
		}
		passengers = staying;
		this.isEmpty();
		return passengers.size() == initSize ? false : true;
		
	}

	public void setGoalFloor(int x) {
		this.goalFloor = x;
		int calc = x - currFloor;
		if( calc > 0){
			direction = 1;
		}
		else if( calc < 0){
			direction = -1;
		}
		else{
			direction = 0;
		}
			
			
		
	}
}
