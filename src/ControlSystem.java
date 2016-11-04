import java.util.ArrayList;

public class ControlSystem implements ElevatorControlSystem{
	
	ArrayList<Elevator> elevators  = new ArrayList<>(); //Elevators in the system
	
	 //Represents floors
	ArrayList<ArrayList<Passenger>> waitingPassengers;
	public final static int ELEVATOR_MAX = 16;
	
	//Requires a # of elevators and floors
	public ControlSystem(int numOfElevators, int floors){
		if(numOfElevators > ELEVATOR_MAX) numOfElevators = ELEVATOR_MAX;
		buildElevators(numOfElevators);
		buildFloors(floors);
		
	}
	
	//Builds the floors based on the int input
	private void buildFloors(int floors) {
		waitingPassengers = new ArrayList<>(floors);
		for(int x = 0; x < floors; x++){
			ArrayList<Passenger> tempFloor = new ArrayList<>();
			waitingPassengers.add(tempFloor);
		}
		
	}

	//Builds elevators based on the int input
	private void buildElevators(int numOfElevators) {
		for(int x = 0; x < numOfElevators; x++){
			Elevator newElev = new Elevator(x,x);
			elevators.add(newElev);
		}
		
	}
	
	//Returns the status of all elevators
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
		System.out.println("Passenger request at floor: " + floorId + " to go to floor: " + goalFloor);
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
	//Step function for elevator system
	public void step() {
		//Checks if the elevators have a passenger to unload/load for the current floor
		for(int x = 0; x < elevators.size(); x++){
			
			if(loadPassengers(elevators.get(x).currFloor, elevators.get(x).elevatorId)){
				continue;
			}
			//Elevator moves if no passengers to unload
			else{
				elevators.get(x).elevatorStep(elevators.get(x).direction);
			}
		}
		
		//Greedy solution which sets the closest elevator to go to the floor with passengers
		for(int x = 0; x < waitingPassengers.size(); x++){
			
			ArrayList<Passenger> temp = waitingPassengers.get(x);
			//Checks each floor for waiting passengers
			if(!temp.isEmpty()){
				Elevator closestE = new Elevator(-1, Integer.MAX_VALUE);
				//Finds the closest elevator
				for(int a = 0; a < elevators.size(); a++){
					if(elevators.get(a).direction == 0){
						int distance = Math.abs(elevators.get(a).currFloor - x);
						int compDist = Math.abs(closestE.currFloor - x);
						if( distance < compDist){
							closestE = elevators.get(a);
							//Sets the passengers incoming elevator to that of the closest elevator
							for(Passenger p : temp){
								//Deschedule for new closest
								if(p.getInc() != -1){
									if(closestE.isEmpty()){
									}
								}
								
								p.setInc(closestE.elevatorId);
							}
						}
					}
				}
				
				//Sets passengers with new elevator values 
				waitingPassengers.set(x, temp);

				if(closestE.elevatorId == -1) continue;
				elevators.get(closestE.elevatorId).setGoalFloor(x);
			}
		}
		
	}
	
	//Loads the passengers on and off the elevator
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
				//If picked up from a different elevator than originally scheduled deschedule original elevator
				if(p.getInc() != elevId){
					if(p.getInc() == -1) continue;
					elevators.get(p.getInc()).deschedule();
				}
			}
			//Keep passengers on the floor for opposite direction
			else{
				stayingPas.add(p);
			}
		}
		
		//Sets the passengers for the current floor
		waitingPassengers.set(currFloor, stayingPas);
		
		//Adds riders to the elevator
		for(int x = 0; x < leavingPas.size(); x++){
			e.addRider(leavingPas.get(x));
		}
		
		elevators.set(elevId, e);
		
		//Returns bool if passengers were leaving for the floor or got on at the floor
		if(initFloorSize !=   waitingPassengers.get(currFloor).size()) 
			return true;
		else
			return false;
		
	}

}
