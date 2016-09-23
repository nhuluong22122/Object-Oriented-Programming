import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintStream;
import java.util.ArrayList;

/**
 * The Plane class allows processes users input and process it using data
 * structures and algorithms
 * 
 * @author Nhu Luong
 *
 */
public class Plane {
	private Passenger[] first;
	private Passenger[] economy;

	/**
	 * Constructor for the Airplane
	 */
	public Plane() {
		// Constructor
		first = new Passenger[8];
		economy = new Passenger[120];
	}

	/**
	 * Add a passenger to the airplane
	 * 
	 * @param name
	 *            name of the passenger
	 * @param service
	 *            the service class the passenger chooses
	 * @param preference
	 *            the seat preference that the passenger chooses
	 * @return true if added successfully, false if the airplane is full
	 */
	public boolean addPassenger(String name, String service, String preference) {
		// TODO Auto-generated method stub
		Passenger p = new Passenger(name, service);
		Seat s;
		int i = 200;
		if (service.contains("irst")) {
			if (isFull(first)) {
				return false;
			}
			s = new Seat(first, service);
			i = s.findSeat(preference);
		} else {
			if (isFull(economy)) {
				return false;
			}
			s = new Seat(economy, service);
			i = s.findSeat(preference);
		}
		if (i != 200) { // When the seat is valid
			s.convertSeat(i); // Convert seat
			if (s.getActualSeat().length() > 0) { // If there is a seat
				p.setSeat(s); // Set the seat to that passenger
				if (service.contains("irst")) {
					first[i] = p;
				} else if (service.contains("conomy")) {
					economy[i] = p;
				}
				System.out.println(s.getActualSeat());// Print out the seat
														// number
				return true;
			} else {
				return false;
				// There is no more
				// seat in that
				// preference
			}

		}
		return true;

	}

	/**
	 * 
	 * @param group
	 * @param names
	 * @param service
	 * @return
	 */
	public boolean addGroup(String group, ArrayList<String> names, String service) {
		System.out.println("Group: " + group);
		if (service.contains("irst")) {
			Seat s = new Seat(first, service);
			ArrayList<Seat> seats = s.findAdjacent(names.size());
			if (seats.size() == 0) {
				System.out.println("We cannot accomodate everyone.");
				return false;
			}
			{
				for (int i = 0; i < seats.size(); i++) {
					Passenger p = new Passenger(names.get(i), service);
					p.setSeat(seats.get(i));
					p.setGroup(group);
					first[(seats.get(i)).getPos()] = p;
					System.out.println(p.getName() + ": " + p.getSeat().getActualSeat());
				}
			}
			return true;
		} else { // economy seat
			Seat s = new Seat(economy, service);
			ArrayList<Seat> seats = s.findAdjacent(names.size());
			if (seats.size() == 0) {
				System.out.println("We cannot accomodate everyone.");
				return false;
			}
			for (int i = 0; i < seats.size(); i++) {
				Passenger p = new Passenger(names.get(i), service);
				p.setSeat(seats.get(i));
				p.setGroup(group);
				economy[(seats.get(i)).getPos()] = p;
				System.out.println(p.getName() + ": " + p.getSeat().getActualSeat());
			}
		}
		return true;
	}

	/**
	 * Print the available seats on the airplane
	 * 
	 * @param service
	 *            the service class that the user wants to print
	 */
	public void printAvailable(String service) {
		// TODO Auto-generated method stub
		if (service.contains("irst")) {
			int start = 1;
			boolean newRow = true;
			for (int i = 0; i < first.length; i++) {
				if (first[i] == null) {
					Seat s = new Seat(first, service);
					String seat = s.convertSeat(i);
					int row = Integer.parseInt(seat.substring(0, 1));
					if (start == row && newRow == true) {
						System.out.print(row + ": ");
						newRow = false;
					}
					if ((i + 1) % 4 == 3 && first[i + 1] != null) {
						System.out.print(seat.substring(1, 2));
						newRow = true;
						start++;
					} else if (seat.charAt(1) == 'D') {
						System.out.print(seat.substring(1, 2));
						newRow = true;
						start++;
					} else if (start == row && newRow == false) {
						System.out.print(seat.substring(1, 2) + ", ");
					}
					if (row > start) {
						start = row;
						newRow = true;
					}
				}
				if (i % 4 == 3) {
					System.out.println();
				}
			}
		} else {
			int start = 10;
			boolean newRow = true;
			for (int i = 0; i < economy.length; i++) {
				if (economy[i] == null) {
					Seat s = new Seat(economy, service);
					String seat = s.convertSeat(i);
					int row = Integer.parseInt(seat.substring(0, 2));
					if (start == row && newRow == true) {
						System.out.print(row + ": ");
						newRow = false;
					}
					if ((i + 1) % 6 == 5 && economy[i + 1] != null) {
						System.out.print(seat.substring(2, 3));
						newRow = true;
						start++;
					} else if (seat.charAt(2) == 'F') {
						System.out.print(seat.substring(2, 3));
						newRow = true;
						start++;
					} else if (start == row && newRow == false) {
						System.out.print(seat.substring(2, 3) + ", ");
					}
					if (row > start) {
						start = row;
						newRow = true;
					}
				}
				if (i % 6 == 5) {
					System.out.println();
				}
			}
		}
	}

	/**
	 * Print the occupied seat with its passenger's name
	 * 
	 * @param service
	 *            the service the user wants to print
	 */
	public void printManifest(String service) {
		// TODO Auto-generated method stub
		System.out.println(service);
		if (service.contains("irst")) {
			for (int i = 0; i < first.length; i++) {
				if (first[i] != null) {
					System.out.println(first[i].getSeat().getActualSeat() + ": " + first[i].getName());
				}
			}
		} else {
			for (int i = 0; i < economy.length; i++) {
				if (economy[i] != null) {
					System.out.println(economy[i].getSeat().getActualSeat() + ": " + economy[i].getName());
				}
			}
		}
	}

	/**
	 * Check if the selected array is full
	 * @param array the array of first or economy class
	 * @return true is the service class is full, false if it is not
	 */
	public boolean isFull(Passenger[] array) {
		for (int i = 0; i < array.length; i++) {
			if (array[i] == null) {
				return false;
			}
		}
		return true;
	}
	/**
	 * Cancel a passenger's reservation
	 * @param name the name of the passenger
	 */
	public void cancelIndividual(String name) {
		for (int i = 0; i < first.length; i++) {
			if (first[i] != null && (first[i].getName()).equals(name)) {
				first[i] = null;
			}
		}
		for (int j = 0; j < economy.length; j++) {
			if (economy[j] != null && (economy[j].getName()).equals(name)) {
				economy[j] = null;
			}
		}
	}
	/**
	 * Cancel a group's reservation
	 * @param group the name of the group
	 */
	public void cancelGroup(String group) {
		for (int i = 0; i < first.length; i++) {
			if (first[i] != null && first[i].getGroup() != null && first[i].getGroup().equals(group)) {
				first[i] = null;
			}
		}
		for (int j = 0; j < economy.length; j++) {
			if (economy[j] != null && economy[j].getGroup() != null && economy[j].getGroup().equals(group)) {
				economy[j] = null;
			}
		}

	}
	/**
	 * Quit the reservation system and save current reservation seat
	 * @param output the file that the system wants to override and store information
	 */
	public void quit(PrintStream output) {
		System.setOut(output);
		System.out.println("First 1-2, Left: A-B, Right: C-D; Economy 10-29, Left: A-C, Right: D-F");
		for (int i = 0; i < first.length; i++) {
			if (first[i] != null) {
				String type = "";
				if (first[i].getGroup() == null) {
					type = "I";
					System.out.println(first[i].getSeat().getActualSeat() + ", " + type + ", " + first[i].getName());
				} else {
					type = "G";
					System.out.println(first[i].getSeat().getActualSeat() + ", " + type + ", " + first[i].getGroup()
							+ ", " + first[i].getName());
				}
			}
		}
		for (int j = 0; j < economy.length; j++) {
			if (economy[j] != null) {
				String type = "";
				if (economy[j].getGroup() == null) {
					type = "I";
					System.out
							.println(economy[j].getSeat().getActualSeat() + ", " + type + ", " + economy[j].getName());
				} else {
					type = "G";
					System.out.println(economy[j].getSeat().getActualSeat() + ", " + type + ", " + economy[j].getGroup()
							+ ", " + economy[j].getName());
				}
			}
		}
	}
	/**
	 * Load the reservation information from a file
	 * @param seat a passenger's seat
	 * @param type individual or group
	 * @param name a passenger's name
	 * @param group if a passenger has a group name
	 */
	public void load(String seat, String type, String name, String group) {
		if (Character.isDigit(seat.charAt(1)) == true) { // Economy
			Passenger p = new Passenger(name, "economy");
			Seat s = new Seat(economy, "economy");
			int i = s.convertArray(seat);
			p.setSeat(s);
			if (type.equals("I")) {
				economy[i] = p;
			} else {
				p.setGroup(group);
				economy[i] = p;
			}
		} else if (Character.isDigit(seat.charAt(1)) == false) { // First
			Passenger p = new Passenger(name, "first");
			Seat s = new Seat(first, "first");
			int i = s.convertArray(seat);
			p.setSeat(s);
			if (type.equals("I")) {
				first[i] = p;
			} else {
				p.setGroup(group);
				first[i] = p;
			}
		}
	}
	
}
