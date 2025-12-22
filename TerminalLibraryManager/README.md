# TerminalLibraryManager

TerminalLibraryManager is a **console-based library management app** written in Java.  
It allows users to view, search, and manage books with basic authentication and user roles.

---

## Features

**User:**
- Login with username and password
- View list of books
- Search books by title or author

**Admin:**
- Add new books
- Edit existing books
- Delete books

> All data is stored **in memory** and will be lost when the program exits.

---

## Predefined Users

| Role  | Username | Password  |
|-------|----------|-----------|
| ADMIN | admin    | admin123  |
| USER  | user     | user123   |

---

## Limitations

- No persistent storage – data is lost after exit  
- Console-based interface only  
- No input validation for special characters  

---

## Future Improvements

- Add persistent storage (file or database)  
- Improve input validation and error handling  
- Add unit tests  
- Enhance user interface (possible GUI)  
- Support multiple user sessions  
- Add categories, ratings, or borrowing system  

---

## How to Run

1. Clone the repository:
```bash
git clone https://github.com/KamilCiemiega/Java-studies-homeworks.git
