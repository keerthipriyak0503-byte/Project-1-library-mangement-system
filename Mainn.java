import java.util.Scanner;

// Parent class
class Person {
    protected String name;
    protected String collegeId;

    Person(String name, String collegeId) {
        this.name = name;
        this.collegeId = collegeId;
    }

    public void displayDetails() {
        System.out.println("Name: " + name);
        System.out.println("College ID: " + collegeId);
    }
}

// Student class inherits Person
class Student extends Person {
    private String department;

    Student(String name, String collegeId, String department) {
        super(name, collegeId);
        this.department = department;
    }

    public String getDepartment() {
        return department;
    }

    // Method overriding
    @Override
    public void displayDetails() {
        System.out.println("Student Name: " + name);
        System.out.println("College ID: " + collegeId);
        System.out.println("Department: " + department);
    }
}

// Book class
class Book {
    private int bookId;
    private String title;
    private String category;
    private boolean available;
    private String borrowedBy;
    private String dueDate;

    Book(int bookId, String title, String category) {
        this.bookId = bookId;
        this.title = title;
        this.category = category;
        this.available = true;
        this.borrowedBy = "";
        this.dueDate = "";
    }

    public int getBookId() {
        return bookId;
    }

    public String getTitle() {
        return title;
    }

    public String getCategory() {
        return category;
    }

    public boolean isAvailable() {
        return available;
    }

    public String getBorrowedBy() {
        return borrowedBy;
    }

    public String getDueDate() {
        return dueDate;
    }

    public void borrowBook(String studentId, String date) {
        available = false;
        borrowedBy = studentId;
        dueDate = date;
    }

    public void returnBook() {
        available = true;
        borrowedBy = "";
        dueDate = "";
    }

    public void displayBook() {
        System.out.println("Book ID: " + bookId);
        System.out.println("Title: " + title);
        System.out.println("Category: " + category);
        System.out.println("Status: "
                + (available ? "Available" : "Issued"));
        System.out.println("-------------------------");
    }
}

// Library class
class Library {
    private Book[] books = {
        new Book(101, "Java Programming", "CSE"),
        new Book(102, "Data Structures", "CSE"),
        new Book(103, "Digital Logic", "ECE"),
        new Book(104, "Discrete Mathematics", "Mathematics"),
        new Book(105, "Computer Networks", "CSE"),
        new Book(106, "Artificial Intelligence", "CSE")
    };

    private Student[] students = {
        new Student("Rahul", "KLH101", "CSE"),
        new Student("Ananya", "KLH102", "ECE"),
        new Student("Keerthi", "KLH103", "CSE"),
        new Student("Arjun", "KLH104", "Mathematics")
    };

    private Scanner sc;

    Library(Scanner sc) {
        this.sc = sc;
    }

    // Verify registered student
    public Student verifyStudent(String id) {
        for (int i = 0; i < students.length; i++) {
            if (students[i].collegeId.equalsIgnoreCase(id)) {
                return students[i];
            }
        }
        return null;
    }

    // Display all books
    public void displayBooks() {
        for (int i = 0; i < books.length; i++) {
            books[i].displayBook();
        }
    }

    // Search a book
    public void searchBook() {
        System.out.print("Enter book title: ");
        String title = sc.nextLine();

        boolean found = false;

        for (int i = 0; i < books.length; i++) {
            if (books[i].getTitle().equalsIgnoreCase(title)) {
                books[i].displayBook();
                found = true;
            }
        }

        if (!found) {
            System.out.println("Book not found.");
        }
    }

    // Borrow a book
    public void borrowBook(Student student) {
        System.out.print("Enter book ID: ");
        int id = sc.nextInt();
        sc.nextLine();

        for (int i = 0; i < books.length; i++) {
            if (books[i].getBookId() == id) {

                if (books[i].isAvailable()) {
                    System.out.print("Enter due date (DD/MM/YYYY): ");
                    String date = sc.nextLine();

                    books[i].borrowBook(student.collegeId, date);

                    System.out.println("Book borrowed successfully!");
                    System.out.println("Book: " + books[i].getTitle());
                    System.out.println("Due Date: " + date);
                } else {
                    System.out.println("Book is already issued.");
                }

                return;
            }
        }

        System.out.println("Invalid book ID.");
    }

    // Return a book and calculate fine
    public void returnBook(Student student) {
        System.out.print("Enter book ID: ");
        int id = sc.nextInt();

        for (int i = 0; i < books.length; i++) {
            if (books[i].getBookId() == id) {

                if (!books[i].isAvailable()
                        && books[i].getBorrowedBy()
                        .equalsIgnoreCase(student.collegeId)) {

                    System.out.print("Enter days late (0 if on time): ");
                    int daysLate = sc.nextInt();

                    if (daysLate < 0) {
                        System.out.println("Invalid number of days.");
                        return;
                    }

                    double fine = daysLate * 10.0;

                    books[i].returnBook();

                    System.out.println("Book returned successfully!");
                    System.out.printf("Fine: Rs. %.2f%n", fine);

                } else {
                    System.out.println(
                        "Book is available or belongs to another student."
                    );
                }

                return;
            }
        }

        System.out.println("Invalid book ID.");
    }

    // Show only the current student's borrowed books
    public void myBooks(Student student) {
        boolean found = false;

        for (int i = 0; i < books.length; i++) {
            if (books[i].getBorrowedBy()
                    .equalsIgnoreCase(student.collegeId)) {

                System.out.println("Book: " + books[i].getTitle());
                System.out.println("Due Date: " + books[i].getDueDate());
                System.out.println("-------------------------");

                found = true;
            }
        }

        if (!found) {
            System.out.println("You have no borrowed books.");
        }
    }

    // Recommend available books by department
    public void recommendBooks(Student student) {
        boolean found = false;

        System.out.println("Recommendations for "
                + student.getDepartment() + ":");

        for (int i = 0; i < books.length; i++) {
            if (books[i].getCategory().equalsIgnoreCase(
                    student.getDepartment())
                    && books[i].isAvailable()) {

                System.out.println(books[i].getTitle());
                found = true;
            }
        }

        if (!found) {
            System.out.println("No recommendations available.");
        }
    }
}

// Main class
public class Mainn {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        Library library = new Library(sc);

        System.out.println("================================");
        System.out.println(" UNIVERSITY LIBRARY SYSTEM");
        System.out.println("================================");

        System.out.print("Enter College ID: ");
        String id = sc.nextLine();

        Student student = library.verifyStudent(id);

        if (student == null) {
            System.out.println("Invalid College ID. Access denied.");
        } else {
            System.out.println("\nStudent verified successfully!");
            student.displayDetails();

            int choice = 0;

            while (choice != 7) {
                System.out.println("\n===== LIBRARY MENU =====");
                System.out.println("1. View All Books");
                System.out.println("2. Search Book");
                System.out.println("3. Borrow Book");
                System.out.println("4. Return Book");
                System.out.println("5. My Borrowed Books");
                System.out.println("6. Recommend Books");
                System.out.println("7. Exit");

                System.out.print("Enter choice: ");
                choice = sc.nextInt();
                sc.nextLine();

                switch (choice) {
                    case 1:
                        library.displayBooks();
                        break;

                    case 2:
                        library.searchBook();
                        break;

                    case 3:
                        library.borrowBook(student);
                        break;

                    case 4:
                        library.returnBook(student);
                        break;

                    case 5:
                        library.myBooks(student);
                        break;

                    case 6:
                        library.recommendBooks(student);
                        break;

                    case 7:
                        System.out.println("Thank you for using the library!");
                        break;

                    default:
                        System.out.println("Invalid choice.");
                }
            }
        }

        sc.close();
    }
}