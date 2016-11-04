
public class ElevatorDriver {
	public static void main(String[] args){
		ControlSystem control = new ControlSystem(16, 20);
		control.status();
		control.pickup(3, 10);
		control.pickup(7, 11);
		control.pickup(8, 12);
		control.pickup(9, 13);
		control.pickup(10, 14);
		control.pickup(11, 15);
		control.pickup(12, 11);
		control.pickup(14, 1);
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
