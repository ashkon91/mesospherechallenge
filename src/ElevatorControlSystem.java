
public interface ElevatorControlSystem {
	public void status();
	
	public void update(int elevId, int floorNum, int goalFloor);
	
	public void pickup(int floorId, int goalFloor);
	
	public void step();
}
