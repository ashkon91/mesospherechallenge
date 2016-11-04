
public class ElevatorDriver {
	public static void main(String[] args){
		ControlSystem control = new ControlSystem(6, 20);
		control.status();
		control.pickup(3, 10);
		control.pickup(7, 11);
		control.pickup(8, 12);
		control.pickup(9, 13);
		control.pickup(10, 14);
		control.pickup(11, 15);
		control.pickup(12, 11);
		control.pickup(1, 11);
		control.step();
		control.status();
		control.step();
		control.status();
		control.step();
		control.status();
		control.step();
		control.status();
		control.step();
		control.status();
		control.step();
		control.status();
		control.step();
		control.status();
		control.step();
		control.status();	
		control.step();
		control.status();
		control.step();
		control.pickup(12, 3);
		control.pickup(9, 13);
		control.pickup(10, 14);
		control.pickup(11, 15);
		control.pickup(12, 11);
		control.status();
		control.step();
		control.status();
		control.step();
		control.status();
		control.step();
		control.status();
		control.step();
		control.status();
		control.step();
		control.status();
		control.step();
		control.status();
		control.step();
		control.status();
		control.step();
		control.status();
		control.step();
		control.status();
		control.step();
		control.status();
		

		
	}
}
