# University Management Portal

A Java-based university management system using JDBC and MySQL.

## Quick Start

### Method 1: VS Code (Easiest)
1. Open project in VS Code
2. Press `F5` to run the GUI application
3. Or use Run menu → Select configuration → Click play button

### Method 2: Double-click Scripts
- **run-gui.bat** - Launch GUI version
- **run-console.bat** - Launch console version
- **build.bat** - Compile the project

### Method 3: Manual Command
```bash
# Set password
export DB_PASSWORD="yuva12@"

# Run GUI
java -cp "bin;lib/mysql-connector-j-8.3.0.jar" com.university.UiMain

# OR Run Console
java -cp "bin;lib/mysql-connector-j-8.3.0.jar" com.university.Main
```

## Prerequisites
- Java 17 or higher
- MySQL 8.0 running on localhost:3306
- MySQL root password: yuva12@

## Database
- Database name: `university_portal`
- Auto-creates database and tables on first run
- No manual setup needed!

## Features
- Manage Students, Faculty, Courses, and Enrollments
- GUI and Console interfaces
- Auto database initialization
- SQL injection protection
- Foreign key relationships
