

public class drive {

	public static void main(String[] args) {

		int numLanes = 6;
		int maxPatronsPerParty=10;

		Alley a = new Alley( numLanes );
		ControlDesk controlDesk = a.getControlDesk();

		ControlDeskView cdv = new ControlDeskView( controlDesk, maxPatronsPerParty);
		controlDesk.subscribe( cdv );

	}
}
