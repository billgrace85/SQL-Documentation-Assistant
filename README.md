# SQL Documentation Assistant (SQL-DA)

A **Java console application** developed as a university project to demonstrate database schema exploration and documentation workflows using sample data.

> **Project scope:** This is an educational prototype. Database connections are **simulated** (no live JDBC connection), and the AI assistant is a **demonstration**, not an integrated AI model.

## Features

- Interactive console dashboard and menu navigation
- Simulated database connection setup and input validation
- Schema explorer for three sample tables: `customers`, `orders`, and `products`
- Generate and view structured documentation for sample tables
- Add comments to documentation
- Export documentation to text files
- Demonstration AI assistant

## Technology

- Java (standard library)
- Object-oriented programming
- IntelliJ IDEA (original development environment)

## Getting started

Install a Java Development Kit (JDK), then from the repository root run:

```bash
javac src/*.java
java -cp src Main
```

On Windows PowerShell, the same commands can be used if `javac` and `java` are on your PATH.

## How to use

1. Run the application and select **1** to set up a simulated database connection.
2. Select **2** to explore sample tables.
3. Select **3** to generate documentation.
4. Select **4** to view documentation and add comments.
5. Select **5** to export documentation as a `.txt` file.
6. Select **6** to try the demonstration AI assistant.
7. Select **0** to exit.

The sample schema is hard-coded for demonstration purposes; entering connection information does not connect to a real database.

## Source files

| File | Purpose |
| --- | --- |
| `Main.java` | Console menus and user interactions |
| `DbCon.java` | Simulated database connection configuration |
| `Table.java` | Sample table model |
| `Column.java` | Column metadata model |
| `Documentationservice.java` | Documentation generation, comments and export |

## Project report

The [`docs/Project_Report.docx`](docs/Project_Report.docx) includes the project requirements, proposed designs, wireframes, flowcharts, use cases, class diagrams, and evaluation. Some features described in the design report are **proposed functionality**, not implemented in this console prototype.

## Potential improvements

- Implement real database connectivity using JDBC
- Extract schema metadata dynamically
- Add automated tests and a build tool
- Create a graphical user interface
- Integrate an actual AI service with appropriate safeguards

## Academic context

Developed as a Java university coursework project. The code and documentation are included for learning and portfolio demonstration.
