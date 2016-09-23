import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.Scanner;
/**
 * 
 * @author Nhu Luong
 *
 */
public class ReservationSystem {
	private static String input;
	private static Plane plane;
	private static String name;
	private static ArrayList<String> names;
	private static String group;
	private static String service;
	private static String preference;
	private static Scanner in;
	private static PrintStream output;
	/**
	 * 
	 * @param args
	 * @throws FileNotFoundException
	 */
	public static void main(String[] args) throws FileNotFoundException {
		plane = new Plane();
		Scanner load;
		try {
			load = new Scanner(new File(args[0]));//if it exists			
			while(load.hasNextLine()){
				String sentence = load.nextLine();
				if(Character.isDigit(sentence.charAt(0))){
					String[] info = sentence.trim().split(", ");
					String seat = info[0];
					String type = info[1];
					String name = "";
					String group = "";
					if(info.length == 3){ //individual
						name = info[2];
					}
					else{ //group
						group = info[2];
						name = info[3];
					}
					plane.load(seat, type, name, group);
	 			}
			}
			plane.printManifest("first");
			plane.printManifest("economy");
		} catch (FileNotFoundException e) {
			output = new PrintStream(new File("CL34.txt"));
			System.out.println("CL34 is being created.");
		}
		in = new Scanner(System.in);
		while (in.hasNextLine()) {
			input = in.nextLine();
			if (input.matches("[PGCAMQpgcamq]")) {
				System.out.println(input);
				process();
			} else {
				System.out.println("Invalid input.Please try again");
			}
		}

	}

	public static void process() throws FileNotFoundException {
		if (input.matches("[Pp]")) {
			name = prompt("Name: ");
			service = prompt("Service Class: ");
			preference = prompt("Seat Preference: ");
			if (name.length() > 0 || service.length() > 0 || preference.length() > 0) {
				boolean success = plane.addPassenger(name, service, preference);
				int time = 0;
				while (success != true && time < 2) {
					System.out.println("We cannot find a seat that you prefer.");
					preference = prompt("Seat Preference: ");
					time++;
					success = plane.addPassenger(name, service, preference);
				}
				if (time == 2) {
					System.out.println("Request Fail.");
				}
			}

		} else if (input.matches("[Gg]")) {
			names = new ArrayList<String>();
			group = prompt("Group Name: ");
			prompt("Names: ");
			System.out.println(names.toString());
			service = prompt("Service Class: ");
			if (group.length() > 0 || names.size() > 0 || service.length() > 0) {
				boolean success = plane.addGroup(group, names, service);
				if (!success) {
					System.out.println("Request Fail.");
				}

			}

		} else if (input.matches("[Cc]")) {
			service = prompt("Cancel [I]ndividual or [G]roup?");
			if (service.equals("I")) {
				name = prompt("Name: ");
				plane.cancelIndividual(name);
			} else if (service.equals("G")) {
				group = prompt("Group Name: ");
				plane.cancelGroup(group);
			}
		} else if (input.matches("[Aa]")) {
			service = prompt("Service: ");
			plane.printAvailable(service);
		} else if (input.matches("[Mm]")) {
			service = prompt("Service: ");
			plane.printManifest(service);
		} else {
			plane.quit(output);
		}

	}

	public static String prompt(String word) {
		System.out.print(word);
		String result = "";
		String temp = in.nextLine();
		System.out.println(temp);
		if (word.equals("Names: ")) {
			if (temp.matches("[a-zA-Z, ]+")) {
				while (temp.indexOf(',') > -1) {
					int start = 0;
					int pos = temp.indexOf(',');
					String name = temp.substring(start, pos).trim();
					start = pos + 1;
					names.add(name);
					temp = temp.substring(start).trim();
				}
				names.add(temp.trim());
				return "";
			} else {
				prompt(word);
			}
		} else if (word.contains("Group")) {
			if (temp.matches("[a-zA-Z0-9]++")) {
				group = temp;
				return group;
			} else {
				prompt(word);
			}
		} else if (word.equals("Name: ")) {

			if (temp.matches("[a-zA-Z\\s ]+")) {
				name = temp;
				return name;
			} else {
				prompt(word);
			}
		} else if (word.contains("Service")) {
			if (temp.contains("irst") || temp.contains("conomy")) {
				service = temp;
				return service;
			} else {
				prompt(word);
			}
		} else if (word.contains("eat")) {
			if (temp.equals("W") || temp.equals("A") || temp.equals("C")) {
				preference = temp;
				return preference;
			} else {
				prompt(word);
			}
		} else if (word.contains("Cancel")) {
			if (temp.equals("I") || temp.equals("G")) {
				service = temp;
				return service;
			} else {
				prompt(word);
			}
		}

		return result;
	}
}
