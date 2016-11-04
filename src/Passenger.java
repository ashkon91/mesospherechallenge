
public class Passenger {
	public int dest;
	public boolean riding; 
	public int direction;
	
	public Passenger(int d, int dir){
		dest = d;
		riding = false;
		if(dir > 0)
			direction = 1;
		else
			direction = -1;
	}
	
}
