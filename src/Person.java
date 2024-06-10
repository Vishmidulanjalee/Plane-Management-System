public class Person {
    private static String name;
    private static String surname;
    private static String email;

    public Person(String name, String surname, String email) { //Constructor
        this.name = name;
        this.surname = surname;
        this.email = email;
    }

    // Getters and Setters
    public String getName() {
        return name;
    }


    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    // Method to print Person information
    public static void printPersonInfo() {
        System.out.println("Person Information: " + name + " " + surname + ", Email: " + email);
    }
}
