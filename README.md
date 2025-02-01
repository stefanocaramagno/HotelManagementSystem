# Hotel Management System

**Hello everyone!**

## 🌍 Context

This repository hosts a comprehensive full-stack web application designed to implement a hotel management system. <br>
The project leverages modern web development technologies to create a feature-rich, responsive, and user-friendly experience. 

## 🎯 Objectives

- **Request a Booking**: Allow customers to request a room booking with details like dates and room type.  
- **Manage Booking Requests**: Enable receptionists to accept or reject booking requests efficiently.  
- **Make a Payment**: Allow customers to complete payments securely using various methods.  
- **View Booking Details**: Let customers view booking details, including dates, room type, and services.  
- **Request a Booking Modification**: Allow customers to request changes to booking details.  
- **Modify Booking Details**: Enable receptionists to update bookings as requested by customers.  
- **Cancel a Booking**: Allow customers to cancel bookings based on hotel policies.  
- **Delete a Booking**: Enable receptionists to delete bookings for internal purposes.  
- **Digital Check-in**: Allow customers to check in digitally for faster arrivals.  
- **Digital Check-out**: Enable customers to check out digitally to simplify departures.  
- **Assign Staff Tasks**: Let managers assign tasks like room cleaning to staff.  
- **Manage Room Status**: Allow staff to update room status, such as cleaned or in maintenance.  

## 📂 Contents

- **Frontend**: HTML, CSS, Tailwind CSS, JavaScript for the user interface.
- **Backend**: Java, Spring Boot for server-side logic.
- **Testing**: JUnit for automated testing.
- **Database**: MySQL for data storage and management.
- **Version Control**: Git for version control.
- **Hosting**: GitHub for repository hosting.

## 🛠 Configuration

### Prerequisites

Ensure you have the following tools installed on your system before proceeding:

- **Java Development Kit (JDK)**: Version 17 or later.
- **Apache Maven**: For project build and dependency management.
- **MySQL**: For database setup and management.
- **Git**: To clone the repository.
- **IDE**: Any modern IDE like Visual Studio Code or preferred text editor.

### Installation Steps

1. **Clone the Repository**
   
   Open a terminal and execute the following commands:

   ```sh
   git clone https://github.com/yourusername/Hotel_Management_System.git
   cd Hotel_Management_System
   ```

2. **Start MySQL**

   Open the XAMPP Control Panel and start the **MySQL** service.

3. **Install Dependencies**

   Use Maven to download and install all required dependencies:

   ```sh
   mvn clean install
   ```

### Running the Application

1. **Start the Server**

   Run the following command to start the Spring Boot application:

   ```sh
   mvn spring-boot:run
   ```

2. **Access the Application**

   Once the server is running, open your web browser and navigate to:

   ```sh
   http://localhost:8080
   ```

   This will take you to the homepage of the Hotel Management System.

### Testing the Application

To run the project's test suite, use the following command:

```sh
mvn test
```

## ⚖️ License

© **Stefano Caramagno, Alessia Fichera, Mario Anthony Guerrera**

**Personal and Educational Use Only**  
All content in this repository is provided for personal and educational purposes only. <br>
Unauthorized actions without explicit permission from the author are prohibited, including but not limited to:

- **Commercial Use**: Using any part of the content for commercial purposes.
- **Distribution**: Sharing or distributing the content to third parties.
- **Modification**: Altering, transforming, or building upon the content.
- **Resale**: Selling or licensing the content or any derivatives.

For permissions beyond the scope of this license, please contact the author.

**Disclaimer**  
The content is provided "*as is*" without warranty of any kind, express or implied. <br>
The author shall not be liable for any claims, damages, or other liabilities arising from its use.