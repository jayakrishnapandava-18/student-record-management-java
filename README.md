# Student Record Management System

**Domain:** Java Programming | **Type:** Console prototype | **JDK:** 17+

## Features
- Add, list, search, update and delete student records
- Automatic grade calculation
- Input validation and duplicate-ID prevention
- Save/load records using `students.csv`

## Run in VS Code
1. Install JDK 17+ and the Java extensions in VS Code.
2. Open this project folder.
3. Open Terminal in the project folder and run:
   ```bash
   javac -d out src/Main.java
   java -cp out Main
   ```

The CSV file is created in the current working directory when a record is saved. This is a learning prototype, not a production system.

## Viva explanation
This application uses a `Student` class to represent each student and a `LinkedHashMap` to store records by unique ID. Methods implement CRUD operations. Input validation prevents invalid marks, and file handling keeps records between program runs.

## Suggested GitHub repository
`student-record-management-java`
