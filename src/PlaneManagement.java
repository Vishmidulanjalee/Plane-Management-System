import java.util.InputMismatchException;
import java.util.Scanner;


public class PlaneManagement {
    private static final int Rows = 4; // Initialize the rows of the seating.
    private static final int[] SeatsPerRow = {14, 12, 12, 14}; // Initialize the seats per row in the plane.
    private static final int Available = 0; //  Initialize an available seat in seatStatus array.
    private static final int Sold = 1; //  Initialize a sold seat in seatStatus array.

    private static Ticket[] ticketsArray = new Ticket[0]; // This is an array to store the sold tickets.
    private static int[][] seatStatus; // 2D array to represent rows of the plane and seats per the row.
    static Scanner scanner = new Scanner(System.in); //This is the scanner object


    /**
     * This is the main method
     * @param args
     */
    public static void main(String[] args) {
        System.out.println("\n____Welcome to the Plane Management application____");
        initializeSeats(); // calling the initializeSeats method
        showMenu(); // calling the showmenu method
        scanner.close();
    }

    /**
     * This method is for initialize the seating plan according to the number of the rows and seats
     */
    private static void initializeSeats() {
        seatStatus = new int[Rows][]; // Declaring a 2D array for represent seat status
        for (int i = 0; i < Rows; i++) {
            seatStatus[i] = new int[SeatsPerRow[i]];
        }
    }



    /**
     * This is the showMenu method for show the menu options to the user.
     */
    private static void showMenu() {
        int choice;
        do {
            System.out.println("\n********************************************");
            System.out.println("*               Menu Options               *");
            System.out.println("********************************************");
            System.out.println("\n1). Buy a seat");
            System.out.println("2). Cancel a seat");
            System.out.println("3). Find the first available seat");
            System.out.println("4). Show seating plan");
            System.out.println("5). Print ticket information and total sales");
            System.out.println("6). Search Ticket");
            System.out.println("0). Exit");
            System.out.println("********************************************");
            System.out.print("Please select an option: ");

            try {
                choice = scanner.nextInt();

                switch (choice) {
                    case 1 -> buySeat();
                    case 2 -> cancelSeat();
                    case 3 -> findFirstAvailable();
                    case 4 -> showSeatingPlan();
                    case 5 -> printTicketInfo();
                    case 6 -> searchTicket();
                    case 0 -> System.out.println("Exiting the program. Thank you!");
                    default -> System.out.println("Invalid choice. Please try again.");
                }
            } catch (InputMismatchException e) {
                System.out.println("Invalid input. Please enter a valid choice (a number).");
                scanner.nextLine(); // This is the invalid input to avoid an infinite loop
                choice = -1; // Set a default value to continue the loop
            }
        } while (choice != 0);
    }

    /**
     * This method is for buy seat in the plane.
     */
    private static void buySeat() {
        System.out.println("\n----- Buy A Seat ---- ");
        Scanner scanner = new Scanner(System.in);
        try {

            System.out.print("Enter the row letter (A, B, C, D): ");
            char rowLetter = scanner.next().toUpperCase().charAt(0); // Assigned first Uppercase input to the variable rowLetter type char.
            // References:https://stackoverflow.com/questions/8000826/is-it-possible-to-get-only-the-first-character-of-a-string#:~:text=Use%20ld.,first%20char%20of%20the%20String%20.

            if (rowLetter < 'A' || rowLetter > 'D') {
                System.out.println("Invalid row letter. Please enter a row between A-D.");
                buySeat();
                return;

            }
            int row = rowLetter - 'A'; // Convert A to the numeric value.
            // References: https://www.programiz.com/c-programming/examples/ASCII-value-character#:~:text=For%20example%2C%20the%20ASCII%20value,of%20characters%20in%20C%20programming.

            System.out.print("Enter the seat number: ");
            int seatNumber = scanner.nextInt();

            if (seatStatus[row][seatNumber - 1] == Sold) {
                System.out.println("Seat already sold. Please choose another seat.");
                buySeat();
            }

            if (seatNumber < 1 || seatNumber > SeatsPerRow[row]) {
                System.out.println("Invalid seat number. Please try again.");
                buySeat();
                return;
            }

            int seatPrice;
            if (seatNumber <= 5) {
                seatPrice = 200;
            } else if (seatNumber <= 9) {
                seatPrice = 150;
            } else {
                seatPrice = 180;
            }

            System.out.print("Enter the name: ");
            String name = scanner.next();
            System.out.print("Enter the surname: ");
            String surname = scanner.next();
            String email;
            do {
                System.out.print("Enter the email: ");
                email = scanner.next();
                if (!isValidEmailAddress(email)) {
                    System.out.println("Invalid email. Please enter a valid email address.");
                }
            } while (!isValidEmailAddress(email));
            Person person = new Person(name, surname, email);

            Ticket ticket = new Ticket(rowLetter, seatNumber, seatPrice, person);
            ticketsArray = addToTicketsArray(ticketsArray, ticket);
            ticket.printTicketInfo();

            seatStatus[row][seatNumber - 1] = Sold; // Updating the status of the  seat to 'Sold' in the seating arrangement.

            System.out.println("Seat " + rowLetter + seatNumber + " purchased successfully for $" + seatPrice + ".");
            showSeatingPlan();
            ticket.save();
        } catch (Exception e){
            System.out.println("Invalid seat number.Please Try again.");
            scanner.nextLine(); // Clear the invalid input
            buySeat();
        }
    }

    /**
     * This method is for validate the email entered by the user.
     * @param email The email address that user input
     * @return true if the email address is valid
     */
    private static boolean isValidEmailAddress(String email) {   //References: https://stackoverflow.com/questions/624581/what-is-the-best-java-email-address-validation-method
        String ePattern = "^[a-zA-Z0-9.!#$%&'*+/=?^_`{|}~-]+@((\\[[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\])|(([a-zA-Z\\-0-9]+\\.)+[a-zA-Z]{2,}))$";
        java.util.regex.Pattern p = java.util.regex.Pattern.compile(ePattern);
        java.util.regex.Matcher m = p.matcher(email);
        return m.matches();
    }

    /**
     * This method is for add to the purchased ticket to the ticket array
     * @param array represent the array to be added the ticket
     * @param element represent the element to be added to the array
     * @return
     */
    private static Ticket[] addToTicketsArray(Ticket[] array, Ticket element) {
        Ticket[] newTicketArray = new Ticket[array.length + 1];
        System.arraycopy(array, 0, newTicketArray, 0, array.length);
        newTicketArray[array.length] = element;
        return newTicketArray;

    }



    /**
     * This method is for cancel a seat in the plane.
     */
    private static void cancelSeat() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("\n---- Cancel A seat ----");
        try {
            System.out.print("Enter the row letter (A, B, C, D): ");
            char rowLetter = scanner.next().toUpperCase().charAt(0);

            if (rowLetter < 'A' || rowLetter > 'D') {
                System.out.println("Invalid row letter. Please enter a row between A-D.");
                cancelSeat();
                return;
            }

            int row = rowLetter - 'A';

            System.out.print("Enter the seat number: ");
            int seatNumber = scanner.nextInt();

            if (seatNumber < 1 || seatNumber > SeatsPerRow[row]) {
                System.out.println("Invalid seat number. Please try again.");
                cancelSeat();
                return;
            }

            if (seatStatus[row][seatNumber - 1] == Available) {
                System.out.println("Seat is not sold. Nothing to cancel.");
            } else {
                ticketsArray = removeTicketFromArray(ticketsArray, rowLetter, seatNumber); // Find the ticket in the ticketsArray and remove it

                seatStatus[row][seatNumber - 1] = Available; // Update the seat status to available

                System.out.println("Ticket for seat " + rowLetter + seatNumber + " canceled successfully.");
                showSeatingPlan(); // Update the seating plan after canceling a ticket and show it.
            }
        }catch (Exception e){
        System.out.println("Invalid seat number.Please try again");
        scanner.nextLine();
        cancelSeat();
        }
    }

    /**
     *This method is for remove the cancelled ticket from the array of Ticket.
     * @param array represent the array of tickets
     * @param row represent the row to remove from the array.
     * @param seat represent the seat number to remove from the array.
     * @return the modified array after remove the cancelled ticket.
     */
    private static Ticket[] removeTicketFromArray(Ticket[] array, char row, int seat) {
        for (int i = 0; i < array.length; i++) {
            if (array[i].getRow() == row && array[i].getSeat() == seat) {
                array = removeElement(array, i);
                break;
            }
        }
        return array;
    }

    /**
     * This method is used to remove an element at a specified index from an array of Ticket objects.
     * @param array  represent the array from which an element needs to be removed.
     * @param index representing the index of the element to be removed.
     * @return the new array without removed index.
     */
    private static Ticket[] removeElement(Ticket[] array, int index) {
        Ticket[] newTicketArray = new Ticket[array.length - 1];
        System.arraycopy(array, 0, newTicketArray, 0, index);
        System.arraycopy(array, index + 1, newTicketArray, index, array.length - index - 1);
        return newTicketArray;
    }



    /**
     * This method is for find the first available seat in the plane system.
     */
    private static void findFirstAvailable() {
        for (int i = 0; i < Rows; i++) {
            for (int j = 0; j < SeatsPerRow[i]; j++) { // iterate over all the seats
                if (seatStatus[i][j] == Available) {
                    char row = (char) ('A' + i); //get the row letter
                    int seatNumber = j + 1;
                    System.out.println("The first available seat is: " + row + seatNumber);
                    return;
                }
            }
        }
        System.out.println("No available seats found.");
    }


    /**
     * This method is for show the seating plan of the plane management system.
     */
    private static void showSeatingPlan() {
        System.out.println("\nSeating Plan of the Plane:");
        System.out.print("   ");

        System.out.println();

        char row = 'A';

        for (int i = 0; i < Rows; i++) {
            System.out.print(row++ + " ");

            for (int j = 0; j < SeatsPerRow[i]; j++) {
                char seat = (seatStatus[i][j] == Available) ? 'O' : 'X';
                System.out.print(" " + seat);
            }

            System.out.println();
        }
    }


    /**
     * This method is for print the ticket information that was sold and calculate the total sales
     */
    private static void printTicketInfo() {
        System.out.println("Ticket Information:");

        int totalSales = 0;
        for (Ticket ticket : ticketsArray) { // iterate over each Ticket objects in the ticket array
            System.out.println("Ticket: " + ticket.getRow() + ticket.getSeat() + " - Price: £" + ticket.getPrice());
            totalSales += ticket.getPrice();
        }

        System.out.println("Total Sales: $" + totalSales);
    }



    /**
     * This is the method for search ticket information and person information
     */
    private static void searchTicket() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("\n---- Search Ticket ----");

        try {
            System.out.print("Enter the row letter (A, B, C, D): ");
            char rowLetter = scanner.next().toUpperCase().charAt(0); // The input will take as a string and charAt(0) will only get the first character of the user input.
            // References:https://stackoverflow.com/questions/8000826/is-it-possible-to-get-only-the-first-character-of-a-string#:~:text=Use%20ld.,first%20char%20of%20the%20String%20.

            if (rowLetter < 'A' || rowLetter > 'D') { // if the row letter is not A,B,C or D program prompt the method again with the error message.
                System.out.println("Invalid row letter. Please try again.");
                searchTicket();
                return;
            }

            int row = rowLetter - 'A'; // This is for convert "A" for get the numeric value.

            System.out.print("Enter the seat number: ");
            int seatNumber = scanner.nextInt();

            if (seatNumber < 1 || seatNumber > SeatsPerRow[row]) {
                System.out.println("Invalid seat number. Please try again.");
                searchTicket();
                return;
            }

            if (seatStatus[row][seatNumber - 1] == Available) {
                System.out.println("This seat is available.");
            } else {
                Ticket ticket = findTicket(rowLetter, seatNumber); // Find the ticket in the ticketsArray
                if (ticket != null) {
                    ticket.printTicketInfo();
                } else {
                    System.out.println("No information available for this seat.");
                }
            }
        } catch (InputMismatchException e) {
            System.out.println("Invalid input. Please enter a valid row letter and seat number.");
            scanner.nextLine();// Consume the invalid input to avoid an infinite loop
            searchTicket();
        }
    }


    /**
     * This is the method for find the ticket in plane.
     * @param row represent the row Letter
     * @param seat represent the seat number
     * @return Ticket info if the entered row and seat number matched otherwise return null if no ticket match with the input.
     */
    private static Ticket findTicket(char row, int seat) {
        for (Ticket ticket : ticketsArray) { // for each loop for all the Ticket objects in ticketsArray.
            if (ticket.getRow() == row && ticket.getSeat() == seat) { //if the entered row and seat number is match reture the ticket info.
                return ticket;
            }
        }
        return null; // Ticket not found
    }


}

