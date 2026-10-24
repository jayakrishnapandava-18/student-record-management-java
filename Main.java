import java.io.*;
import java.util.*;

/** Beginner-friendly Java console prototype: Student Record Management System. */
public class Main {
    private static final String FILE = "students.csv";
    private static final Map<Integer, Student> students = new LinkedHashMap<>();
    private static final Scanner in = new Scanner(System.in);

    static class Student {
        int id;
        String name, department;
        double marks;
        Student(int id, String name, String department, double marks) {
            this.id=id; this.name=name; this.department=department; this.marks=marks;
        }
        String grade() {
            if (marks >= 90) return "A+";
            if (marks >= 80) return "A";
            if (marks >= 70) return "B";
            if (marks >= 60) return "C";
            if (marks >= 50) return "D";
            return "F";
        }
        public String toString() {
            return String.format("ID: %d | Name: %s | Department: %s | Marks: %.1f | Grade: %s",
                    id, name, department, marks, grade());
        }
    }

    public static void main(String[] args) {
        load();
        System.out.println("\\n=== STUDENT RECORD MANAGEMENT SYSTEM ===");
        while (true) {
            System.out.println("\\n1 Add  2 View all  3 Search  4 Update  5 Delete  0 Exit");
            int choice = integer("Choose: ");
            switch (choice) {
                case 1 -> add();
                case 2 -> view();
                case 3 -> search();
                case 4 -> update();
                case 5 -> delete();
                case 0 -> { save(); System.out.println("Saved. Goodbye!"); return; }
                default -> System.out.println("Choose a valid menu option.");
            }
        }
    }
    static void add() {
        int id = integer("Student ID: ");
        if (students.containsKey(id)) { System.out.println("That ID already exists."); return; }
        String name = nonEmpty("Name: ");
        String dept = nonEmpty("Department: ");
        double marks = number("Marks (0-100): ", 0, 100);
        students.put(id, new Student(id, name, dept, marks)); save();
        System.out.println("Student added.");
    }
    static void view() {
        if (students.isEmpty()) { System.out.println("No records yet."); return; }
        students.values().forEach(System.out::println);
        System.out.println("Total: " + students.size());
    }
    static void search() {
        Student s = students.get(integer("ID to search: "));
        System.out.println(s == null ? "Student not found." : s);
    }
    static void update() {
        Student s = students.get(integer("ID to update: "));
        if (s == null) { System.out.println("Student not found."); return; }
        s.name = nonEmpty("New name: ");
        s.department = nonEmpty("New department: ");
        s.marks = number("New marks (0-100): ", 0, 100);
        save(); System.out.println("Student updated.");
    }
    static void delete() {
        Student s = students.remove(integer("ID to delete: "));
        if (s == null) System.out.println("Student not found.");
        else { save(); System.out.println("Student deleted."); }
    }
    static int integer(String prompt) {
        while (true) try { System.out.print(prompt); return Integer.parseInt(in.nextLine().trim()); }
        catch (NumberFormatException e) { System.out.println("Enter a whole number."); }
    }
    static double number(String prompt, double min, double max) {
        while (true) try {
            System.out.print(prompt); double v = Double.parseDouble(in.nextLine().trim());
            if (v >= min && v <= max) return v;
            System.out.println("Enter a value from " + min + " to " + max + ".");
        } catch (NumberFormatException e) { System.out.println("Enter a valid number."); }
    }
    static String nonEmpty(String prompt) {
        while (true) { System.out.print(prompt); String s=in.nextLine().trim(); if (!s.isEmpty()) return s; System.out.println("Cannot be empty."); }
    }
    static void load() {
        File f = new File(FILE); if (!f.exists()) return;
        try (BufferedReader r = new BufferedReader(new FileReader(f))) {
            String line;
            while ((line=r.readLine()) != null) {
                String[] p=line.split(",", -1); if (p.length != 4) continue;
                try { students.put(Integer.parseInt(p[0]), new Student(Integer.parseInt(p[0]), p[1], p[2], Double.parseDouble(p[3]))); }
                catch (NumberFormatException ignored) { System.out.println("Skipping invalid saved row."); }
            }
        } catch (IOException e) { System.out.println("Could not load records: " + e.getMessage()); }
    }
    static void save() {
        try (PrintWriter w = new PrintWriter(new FileWriter(FILE))) {
            for (Student s : students.values())
                w.println(s.id + "," + s.name.replace(",", " ") + "," + s.department.replace(",", " ") + "," + s.marks);
        } catch (IOException e) { System.out.println("Could not save records: " + e.getMessage()); }
    }
}
