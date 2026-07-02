# 🎰 Blackjack App

A modern web-based Blackjack game built with **Spring Boot**, **MySQL**, and **Thymeleaf**. Play against the dealer in real-time with a responsive web interface.

## ✨ Features

- 🎮 **Interactive Blackjack Game** - Play against an AI dealer
- 👤 **User Authentication** - Secure login and registration system
- 💾 **Game History** - Track all your games and statistics
- 🏦 **Admin Dashboard** - Manage users and game statistics
- 📱 **Responsive Design** - Works on desktop and mobile devices
- 🔐 **Spring Security** - Secure user sessions and password encryption

## 🛠️ Tech Stack

- **Backend**: Spring Boot 4.1.0
- **Language**: Java 21
- **Database**: MySQL 8.0
- **Template Engine**: Thymeleaf
- **ORM**: JPA/Hibernate
- **Build Tool**: Maven
- **Security**: Spring Security
- **UI**: HTML5, CSS3, JavaScript

## 📋 Prerequisites

- Java 21 or higher
- Maven 3.6+
- MySQL 8.0+
- Git

## 🚀 Quick Start

### 1. Clone the Repository
```bash
git clone https://github.com/changyu2hao/blackjack-app.git
cd blackjack-app
```

### 2. Configure Local Database
Create a MySQL database and update `src/main/resources/application.properties`:
```properties
spring.datasource.url=jdbc:mysql://localhost:3306/blackjack_db?useSSL=false&serverTimezone=UTC
spring.datasource.username=root
spring.datasource.password=your_password
```

### 3. Run the Application
```bash
mvn spring-boot:run
```

The application will start at `http://localhost:8080`

## 🌐 Default Login Credentials

- **Username**: `admin`
- **Password**: `changeme`

## 📁 Project Structure

```
blackjack-app/
├── src/main/java/com/example/blackjack/
│   ├── BlackjackAppApplication.java      # Entry point
│   ├── controller/                        # REST controllers
│   ├── entity/                           # JPA entities
│   ├── game/                             # Game logic
│   ├── repository/                       # Data access layer
│   ├── security/                         # Security configuration
│   └── service/                          # Business logic
├── src/main/resources/
│   ├── application.properties            # Development config
│   ├── application-prod.properties       # Production config
│   ├── templates/                        # Thymeleaf templates
│   └── static/                           # CSS, JS, images
├── pom.xml                               # Maven dependencies
└── Procfile                              # Railway deployment config
```

## 🎮 How to Play

1. **Register or Login** - Create an account or use the default admin account
2. **Start a Game** - Click "Play Game" to start a new game
3. **Make Decisions** - Hit, Stand, or Double Down based on your strategy
4. **Check History** - View all your past games and statistics
5. **View Dashboard** - Admin users can access game analytics

## 🚢 Deployment to Railway

This project is configured for easy deployment to [Railway](https://railway.app):

1. Push code to GitHub
2. Create a new project on Railway and connect your GitHub repo
3. Add MySQL database service
4. Set environment variables:
   ```
   DATABASE_URL=jdbc:mysql://host:port/database
   DB_USERNAME=your_username
   DB_PASSWORD=your_password
   ADMIN_PASSWORD=your_admin_password
   ```
5. Deploy!

See [RAILWAY_DEPLOYMENT.md](./RAILWAY_DEPLOYMENT.md) for detailed instructions.

## 📝 Configuration Files

### `application.properties` (Development)
- Local MySQL connection
- SQL debugging enabled
- Development-specific settings

### `application-prod.properties` (Production)
- Uses environment variables
- Optimized for Railway deployment
- Security-focused configuration

## 🔧 Available Endpoints

| Endpoint | Method | Description |
|----------|--------|-------------|
| `/` | GET | Home page |
| `/login` | GET, POST | User login |
| `/register` | GET, POST | User registration |
| `/game` | GET | Game interface |
| `/history` | GET | Game history |
| `/dashboard` | GET | Admin dashboard (admin only) |
| `/api/game/action` | POST | Game actions (Hit, Stand, etc.) |

## 🧪 Building

```bash
# Clean build
mvn clean package

# Run built JAR
java -jar target/blackjack-app-0.0.1-SNAPSHOT.jar
```

## 📜 License

This project is open source and available under the MIT License.

## 👨‍💻 Author

- **Repository**: [changyu2hao/blackjack-app](https://github.com/changyu2hao/blackjack-app)

## 🐛 Issues & Support

If you encounter any issues, please:
1. Check the application logs
2. Verify MySQL connection settings
3. Open an issue on GitHub

## 🎯 Future Enhancements

- [ ] Multiplayer support
- [ ] Betting system with wallet
- [ ] Leaderboard
- [ ] Game statistics and analytics
- [ ] Mobile app
- [ ] Real-time notifications

---

**Enjoy your game! 🎰**
