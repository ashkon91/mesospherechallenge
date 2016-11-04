import java.util.ArrayList;

public class ControlSystem implements ElevatorControlSystem{
	
	ArrayList<Elevator> elevators  = new ArrayList<>(); //Elevators in the system
	
	 //Represents floors
	ArrayList<ArrayList<Passenger>> waitingPassengers;
	public final static int ELEVATOR_MAX = 16;
	
	public ControlSystem(int numOfElevators, int floors){
		if(numOfElevators > ELEVATOR_MAX) numOfElevators = ELEVATOR_MAX;
		buildElevators(numOfElevators);
		buildFloors(floors);
		
	}
	
	



	private void buildFloors(int floors) {
		waitingPassengers = new ArrayList<>(floors);
		for(int x = 0; x < floors; x++){
			ArrayList<Passenger> tempFloor = new ArrayList<>();
			waitingPassengers.add(tempFloor);
		}
		
	}





	private void buildElevators(int numOfElevators) {
		for(int x = 0; x < numOfElevators; x++){
			Elevator newElev = new Elevator(x,x);
			elevators.add(newElev);
		}
		
	}
	@Override
	public void status() {
		for(Elevator e : elevators){
			System.out.println("Elevator ID: " + ( e.elevatorId) + " Currently on Floor: " + (e.currFloor) + " Going to Floor: " + (e.goalFloor) + " WITH # OF PASSENGERS: " + e.passengers.size() + " IN USE: " + e.inUse() + " DIRECTION: " + e.direction);			
		}
	}

	@Override
	//An update modifies the ID/Floor #/Goal # of the specified elevator
	public void update(int elevId, int floorNum, int goalFloor) {
		this.elevators.get(elevId).currFloor = floorNum;
		this.elevators.get(elevId).goalFloor = goalFloor;
		
	}

	@Override
	public void pickup(int floorId, int goalFloor) {
		//No need for elevator if they are already at their destination floor
		if(floorId == goalFloor) return;
		int direction;
		if(floorId > goalFloor) 
			direction = -1;
		else
			direction = 1;
		Passenger p = new Passenger(goalFloor, direction);
		waitingPassengers.get(floorId).add(p);
		
	}

	@Override
	public void step() {
		System.out.println("STEP");
		for(int x = 0; x < elevators.size(); x++){
			
			if(loadPassengers(elevators.get(x).currFloor, elevators.get(x).elevatorId)){
				continue;
			}
			else{
				elevators.get(x).elevatorStep(elevators.get(x).direction);
			}
		}
		
		for(int x = 0; x < waitingPassengers.size(); x++){
			ArrayList<Passenger> temp = waitingPassengers.get(x);
			if(!temp.isEmpty()){
				Elevator closestE = new Elevator(-1, Integer.MAX_VALUE);
				for(int a = 0; a < elevators.size(); a++){
					if(elevators.get(a).direction == 0){
						int distance = Math.abs(elevators.get(a).currFloor - x);
						int compDist = Math.abs(closestE.currFloor - x);
						if( distance < compDist){
							closestE = elevators.get(a);
							for(Passenger p : temp){
								p.setInc(closestE.elevatorId);
							}
							System.out.println("NEW CLOSEST: " + elevators.get(a).elevatorId);
						}
					}
				}
				waitingPassengers.set(x, temp);
				if(closestE.elevatorId == -1) continue;
				elevators.get(closestE.elevatorId).setGoalFloor(x);
				//elevators.set(closestE.elevatorId, closestE);
			}
		}
		
	}
	
	public boolean loadPassengers(int currFloor, int elevId){
		
		int initFloorSize = waitingPassengers.get(currFloor).size();
		
		Elevator e = elevators.get(elevId); //Current Elevator
		
		//Track who is staying and leaving
		ArrayList<Passenger> leavingPas = new ArrayList<>();
		ArrayList<Passenger> stayingPas = new ArrayList<>();
		
		//Go through all passengers waiting on the floor
		for(int x = 0; x < waitingPassengers.get(currFloor).size(); x++){
			
			Passenger p = waitingPassengers.get(currFloor).get(x);
			//Checks if heading in same direction
			if(p.direction == e.direction || e.direction == 0){
				leavingPas.add(p);
				e.direction = p.direction;
				if(p.getInc() != elevId){
					elevators.get(p.getInc()).deschedule();
				}
			}
			else{
				stayingPas.add(p);
			}
		}
		
		waitingPassengers.set(currFloor, stayingPas);
		
		for(int x = 0; x < leavingPas.size(); x++){
			e.addRider(leavingPas.get(x));
		}
		
		elevators.set(elevId, e);
		
		if(initFloorSize !=   waitingPassengers.get(currFloor).size()) 
			return true;
		else
			return false;
		
	}

}
