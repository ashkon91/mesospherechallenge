
public class ElevatorDriver {
	public static void main(String[] args){
		//First input is # of elevators, second is # of floors
		ControlSystem control = new ControlSystem(6, 20);
		control.status();
		control.pickup(3, 10);
		control.step();
		

		
	}
}
