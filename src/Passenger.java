/**
 * The Passenger class contains passengers' information
 * 
 * @author Nhu Luong
 *
 */
public class Passenger {
	private String name;
	private String group;
	private Seat seat;
	private String service;

	/**
	 * Constructor
	 * 
	 * @param n
	 *            the name of the passenger
	 * @param s
	 *            the service class the passenger chose
	 */
	public Passenger(String n, String s) {
		name = n;
		group = null;
		service = s;
	}

	/**
	 * Retrieve the name of the passenger
	 * 
	 * @return the name of the passenger
	 */
	public String getName() {
		return name;
	}

	/**
	 * Retrieve a passenger's group name
	 * 
	 * @return a passenger's group name if has one
	 */
	public String getGroup() {
		return group;
	}
	/**
	 * Set a new group name for a passenger
	 * @param group the new group name
	 */
	public void setGroup(String group) {
		this.group = group;
	}
	/**
	 * Retrieve the seat information 
	 * @return the passenger's seat information
	 */
	public Seat getSeat() {
		return seat;
	}
	/**
	 * Set a new seat to a passenger
	 * @param seat a new seat for the passenger
	 */
	public void setSeat(Seat seat) {
		this.seat = seat;
	}
}
