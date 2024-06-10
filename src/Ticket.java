import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

public class Ticket {
    private char row;
    private int seat;
    private int price;
    public Person person;

    public Ticket(char row, int seat, int price, Person person) { //Constructor
        this.row = row;
        this.seat = seat;
        this.price = price;
        this.person=person;

    }

    // Getters and Setters
    public char getRow() {
        return row;
    }

    public void setRow(char row) {
        this.row = row;
    }

    public int getSeat() {
        return seat;
    }

    public void setSeat(int seat) {
        this.seat = seat;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public Person getPerson() {

        return person;
    }

    public void save() {
        String fileName = row + String.valueOf(seat) + ".txt";

        try (PrintWriter writer = new PrintWriter(new FileWriter(fileName))) {
            writer.println("Ticket Information: Row " + row + ", Seat " + seat + ", Price £" + price);

            if (person != null) {
                writer.println("Person Information: " + person.getName() + " " +
                        person.getSurname() + ", Email: " + person.getEmail());
            } else {
                writer.println("No person information available for this ticket.");
            }

            System.out.println("Ticket information saved to " + fileName);
        } catch (IOException e) {
            System.err.println("Error saving ticket information to file: " + e.getMessage());
        }
    }



    // Method to print Ticket information
    public void printTicketInfo() {
        System.out.println("Ticket Information: Row " + row + ", Seat " + seat + ", Price £" + price);

        if (person != null) {
            Person.printPersonInfo();
        } else {
            System.out.println("No person information available for this ticket.");
        }
    }
}

