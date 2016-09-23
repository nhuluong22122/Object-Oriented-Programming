import java.util.ArrayList;

/**
 * The Seat class performs algorithms to find appropriate seats for the user
 * 
 * @author Nhu Luong
 *
 */
public class Seat {
	private String actualSeat;
	private String service;
	private int serviceSize;
	private int startRow;
	private int posArray;
	private Passenger[] array;
	private int row;

	/**
	 * Constructor
	 * 
	 * @param a
	 *            the array depending on the service class
	 * @param service
	 *            the service class
	 */
	public Seat(Passenger[] a, String service) {
		array = a;
		actualSeat = null;
		this.service = service;
		if (service.contains("irst")) {
			serviceSize = 4;
			startRow = 1;
		} else {
			serviceSize = 6;
			startRow = 10;
		}
	}

	/**
	 * Find a seat for the user's preference
	 * 
	 * @param preference
	 *            the passenger's preferred seat
	 * @return
	 */
	public int findSeat(String preference) {
		if (service.contains("irst")) {
			for (int i = 0; i < array.length; i++) {
				if (array[i] == null) {
					if (preference.equals("W")) {
						if (i % serviceSize == 0 || i % serviceSize == 3) {
							return i;
						}
					} else if (preference.equals("A")) {// User picked A
						if (i % serviceSize == 1 || i % serviceSize == 2) {
							return i;
						}
					}
				}
			}
		} else { // If it is economy
			for (int i = 0; i < array.length; i++) {
				if (array[i] == null) {
					if (preference.equals("W")) {
						if (i % serviceSize == 0 || i % serviceSize == 5)
							return i;
					} else if (preference.equals("C")) {
						if (i % serviceSize == 1 || i % serviceSize == 4)
							return i;
					} else if (preference.equals("A")) {// User picked A
						if (i % serviceSize == 2 || i % serviceSize == 3)
							return i;
					}
				}
			}
		}
		return 200;
	}

	/**
	 * Find adjacent seats for a group of passengers
	 * 
	 * @param size
	 *            the size of a group
	 * @return an array list that stores all the seat information
	 */
	public ArrayList<Seat> findAdjacent(int size) {
		// 1. Find the row with the largest number of adjacent seats
		ArrayList<Seat> result = new ArrayList<Seat>();
		int startR = 1;
		int largestRow = findLargestRow(startR, size);
		if (findAvailableSeat(largestRow) > size) { // If there are enough
													// available seat in that
													// row
			int pos = (largestRow - 1) * serviceSize;
			while (result.size() != size) {
				Seat s = new Seat(this.array, this.service);
				s.posArray = pos;
				s.convertSeat(pos);
				result.add(s);
				pos++;
			}
		} else // Place some here and find the next largest
		{
			int pos = (largestRow - 1) * serviceSize; // start at the
														// different
														// row's
														// position
			int count = 0;
			int seats = findAvailableSeat(largestRow);
			if (seats == 0) {
				return new ArrayList<Seat>();
			}
			while (count < seats) { // reach the end
				if (array[pos] == null) {
					Seat s = new Seat(this.array, this.service);
					s.posArray = pos;
					s.convertSeat(pos);
					result.add(s);
					count++;
				}
				pos++;
			}
			largestRow++;
			while (result.size() < size) {
				if (!isFull(result)) {
					largestRow = findLargestRow(largestRow, size); // second
																	// largest
																	// row

					pos = (largestRow - 1) * serviceSize; // start at the
															// different
															// row's
															// position

					count = 0;
					seats = findAvailableSeat(largestRow);
					while (result.size() < size && count < seats) { // reach
																	// the
																	// end
						if (array[pos] == null) {
							Seat s = new Seat(this.array, this.service);
							s.posArray = pos;
							s.convertSeat(pos);
							result.add(s);
							count++;
						}
						pos++;
					}
					largestRow++;
				} else {
					return new ArrayList<Seat>();
				}
			}

		}
		return result;

	}

	/**
	 * Helper class to find available seats in a specified row
	 * 
	 * @param row
	 *            row that needs to find available seats
	 * @return the number of available seats in that row
	 */
	public int findAvailableSeat(int row) {
		int num = (row - 1) * serviceSize;
		int count = 0;
		for (int i = num; i < serviceSize * row; i++) {
			if (array[i] == null) {
				count++;
			}
		}
		return count;

	}

	/**
	 * A helper method to find the row with the most available seats
	 * 
	 * @param start
	 *            the row that want to perform the search on
	 * @param size
	 *            the desired amount of seat
	 * @return the row that contains the most seats
	 */
	public int findLargestRow(int start, int size) {// Trying to make this
													// recursive
		int num = (start - 1) * serviceSize;
		int largest = 0;
		int count = 0;
		for (int i = num; i < array.length; i++) {
			if (array.length == 8 && i % serviceSize == 3) { // first class and
																// reach the end
																// of the row
				count = 0; // Count restart for every row
			} else if (array.length == 120 && i % serviceSize == 5) {// economy
																		// class
																		// and
																		// reach
																		// the
																		// end
																		// of
																		// the
																		// row
				count = 0;
			}
			if (array[i] == null) { // If there is empty seat
				count++; // Increase count
				if (count > largest || count == size) { // If count is largest
					largest = getRow(i);
					break;
				}
			}
		}
		return largest;
	}

	/**
	 * Convert position in an array to the seat's number on the plane
	 * @param i the position in the service class array
	 * @return the seat number listed on the airplane
	 */
	public String convertSeat(int i) {
		actualSeat = "";
		if (i % serviceSize == 0) {
			row = i / serviceSize + startRow;
			actualSeat = new Integer(row).toString() + "A";
		} else if (i % serviceSize == 1) {
			row = i / serviceSize + startRow;
			actualSeat = new Integer(row).toString() + "B";
		} else if (i % serviceSize == 2) {
			row = i / serviceSize + startRow;
			actualSeat = new Integer(row).toString() + "C";
		} else if (i % serviceSize == 3) {
			row = i / serviceSize + startRow;
			actualSeat = new Integer(row).toString() + "D";
		} else if (i % serviceSize == 4) {
			row = i / serviceSize + startRow;
			actualSeat = new Integer(row).toString() + "E";
		} else if (i % serviceSize == 5) {
			row = i / serviceSize + startRow;
			actualSeat = new Integer(row).toString() + "F";
		}

		return actualSeat;
	}
	/**
	 * Convert a given seat number to the position in an array
	 * @param seat the given seat on the airplane
	 * @return the position in the service class array
	 */
	public int convertArray(String seat) {
		actualSeat = seat;
		int result = 0;
		if (service.contains("irst")) {
			row = Integer.parseInt(seat.substring(0, 1));
			result = (row - startRow) * serviceSize;
			String column = seat.substring(1);
			if (column.equals("A")) {
				result = result + 0;
			} else if (column.equals("B")) {
				result = result + 1;
			} else if (column.equals("C")) {
				result = result + 2;
			} else if (column.equals("D")) {
				result = result + 3;
			}

		} else if (service.contains("conomy")) {
			row = Integer.parseInt(seat.substring(0, 2));
			result = (row - startRow) * serviceSize;
			String column = seat.substring(2);
			if (column.equals("A")) {
				result = result + 0;
			} else if (column.equals("B")) {
				result = result + 1;
			} else if (column.equals("C")) {
				result = result + 2;
			} else if (column.equals("D")) {
				result = result + 3;
			} else if (column.equals("E")) {
				result = result + 4;
			} else if (column.equals("F")) {
				result = result + 5;
			}
		}
		return result;

	}
	/**
	 * Return the seat number on the airplane
	 * @return the seat number on the airplane
	 */
	public String getActualSeat() {
		return actualSeat;
	}
	/**
	 * Calculate the row of a seat
	 * @param i position of the service class array
	 * @return the row 
	 */
	public int getRow(int i) {
		int result = i / serviceSize + 1;
		return result;
	}
	/**
	 * Return the position of the seat in the array
	 * @return the position of the seat in the array
	 */
	public int getPos() {
		return posArray;
	}
	/**
	 * Check if the number of seats found exceeds the capacity of the class service
	 * @param s the ArrayList that contains all the seats' information
	 * @return true if the service class do not have enough seats, false if it have enough seats
	 */
	public boolean isFull(ArrayList<Seat> s) {
		int count = 0;
		for (int i = 0; i < array.length; i++) {
			if (array[i] != null) {
				count++;
			}
		}
		if (s.size() + count >= array.length) {
			return true;
		} else {
			return false;
		}
	}

}
