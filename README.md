# 🎰 Blackjack App

A modern web-based Blackjack game built with **Spring Boot**, **MySQL**, and **Thymeleaf**. Play against the dealer through a responsive web interface with support for standard Blackjack actions, including **Hit**, **Stand**, **Split**, and **Double Down**.

🎮 **[Play Now!](https://web-production-02b9e2.up.railway.app/)** ← Click here to play online

## ✨ Features

- 🎮 **Interactive Blackjack Game** - Play against an automated dealer
- 🃏 **Hit and Stand** - Draw additional cards or end your turn
- ✂️ **Split Hands** - Split matching starting cards into two separate hands
- 💰 **Double Down** - Double the current hand's bet, receive one card, and automatically stand
- 👤 **User Authentication** - Secure login and registration system
- 💾 **Game History** - Track previous games, bets, scores, results, and balances
- 🪙 **Virtual Chip Balance** - Place bets and manage a virtual account balance
- 🏦 **Dashboard** - Access the game, account balance, and game history
- 📱 **Responsive Design** - Works on desktop and mobile devices
- 🔐 **Spring Security** - Secure user sessions and password encryption

## 🎯 Blackjack Rules Implemented

### Hit

The player receives one additional card. If the total score exceeds 21, the hand loses.

### Stand

The player ends the current hand. The dealer then draws cards until reaching at least 17.

### Split

Split is available when:

- The player has exactly two cards
- The two cards have the same Blackjack value
- The hand has not already been split
- The player has enough balance for an additional equal bet

After splitting:

- The original cards are separated into two hands
- Each hand receives one additional card
- The player completes the first hand before playing the second hand
- Both hands are evaluated separately against the dealer
- Each hand has its own bet and game-history record

Ten-value cards such as `10`, `J`, `Q`, and `K` may be split together because they share the same Blackjack value.

### Double Down

Double Down is available when the active hand contains exactly two cards and the player has enough balance.

After choosing Double Down:

- The active hand's bet is doubled
- The player receives exactly one additional card
- The active hand automatically stands
- In a split game, play continues with the next hand when applicable

## 🛠️ Tech Stack

- **Backend**: Spring Boot 4.1.0
- **Language**: Java 21
- **Database**: MySQL 8.0
- **Template Engine**: Thymeleaf
- **ORM**: JPA/Hibernate
- **Build Tool**: Maven
- **Security**: Spring Security
- **UI**: HTML5, CSS3, JavaScript
- **Deployment**: Railway

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

### 2. Configure the Local Database

Create a MySQL database and update:

```text
src/main/resources/application.properties
```

Example configuration:

```properties
spring.datasource.url=jdbc:mysql://localhost:3306/blackjack_db?useSSL=false&serverTimezone=UTC
spring.datasource.username=root
spring.datasource.password=your_password
```

### 3. Run the Application

```bash
mvn spring-boot:run
```

The application will start at:

```text
http://localhost:8080
```

## 🌐 Default Login Credentials

- **Username**: `admin`
- **Password**: `changeme`

For a production deployment, change the default password through an environment variable or secure configuration.

## 📁 Project Structure

```text
blackjack-app/
├── src/main/java/com/example/blackjack/
│   ├── BlackjackAppApplication.java      # Application entry point
│   ├── controller/                       # MVC controllers
│   ├── entity/                           # JPA entities
│   ├── game/                             # Blackjack game logic
│   ├── repository/                       # Data access layer
│   ├── security/                         # Spring Security configuration
│   └── service/                          # Business logic
├── src/main/resources/
│   ├── application.properties            # Development configuration
│   ├── application-prod.properties       # Production configuration
│   ├── templates/                        # Thymeleaf templates
│   └── static/                           # CSS, JavaScript, and images
├── pom.xml                               # Maven dependencies
└── Procfile                              # Railway deployment configuration
```

## 🎮 How to Play

1. **Register or Log In**  
   Create an account or use the default admin account.

2. **Place a Bet**  
   Enter a virtual-chip bet that does not exceed your current balance.

3. **Start the Game**  
   The player and dealer each receive two cards.

4. **Choose an Action**
   - **Hit** to receive another card
   - **Stand** to finish the current hand
   - **Split** when the initial cards have the same Blackjack value
   - **Double Down** to double the active hand's bet and receive one final card

5. **Complete Split Hands**  
   When a hand is split, play the first hand and then the second hand.

6. **View the Result**  
   Each hand is compared with the dealer's hand.

7. **Check Game History**  
   Review previous bets, results, player scores, dealer scores, and account balances.

## 🚢 Deployment to Railway

This project is configured for deployment to [Railway](https://railway.app).

1. Push the project to GitHub
2. Create a new Railway project
3. Connect the GitHub repository
4. Add a MySQL database service
5. Configure the required environment variables
6. Deploy the application

Example environment variables:

```text
DATABASE_URL=jdbc:mysql://host:port/database
DB_USERNAME=your_username
DB_PASSWORD=your_password
ADMIN_PASSWORD=your_admin_password
```

See [RAILWAY_DEPLOYMENT.md](./RAILWAY_DEPLOYMENT.md) for detailed instructions.

## 📝 Configuration Files

### `application.properties`

Used for local development:

- Local MySQL connection
- Development database settings
- Local application configuration

### `application-prod.properties`

Used for production:

- Railway environment variables
- Production database connection
- Deployment-specific configuration
- Security-focused settings

## 🔧 Available Endpoints

| Endpoint | Method | Description |
|----------|--------|-------------|
| `/` | GET | Home page |
| `/login` | GET, POST | User login |
| `/register` | GET, POST | User registration |
| `/dashboard` | GET | User dashboard |
| `/game` | GET | Blackjack game interface |
| `/game/start` | POST | Start a new game |
| `/game/hit` | POST | Hit the active hand |
| `/game/stand` | POST | Stand the active hand |
| `/game/split` | POST | Split the initial player hand |
| `/game/double-down` | POST | Double the active hand's bet and draw one card |
| `/game/new` | POST | Clear the completed game and start again |
| `/history` | GET | View game history |
| `/logout` | POST | Log out of the application |

## 🧪 Building and Testing

Run the tests:

```bash
mvn test
```

Create a clean build:

```bash
mvn clean package
```

Run the packaged JAR:

```bash
java -jar target/blackjack-app-0.0.1-SNAPSHOT.jar
```

## 📜 License

This project is open source and available under the MIT License.

## 👨‍💻 Author

- **GitHub Repository**: [changyu2hao/blackjack-app](https://github.com/changyu2hao/blackjack-app)

## 🐛 Issues and Support

When reporting an issue:

1. Check the application logs
2. Confirm that MySQL is running
3. Verify the database credentials
4. Confirm that all required environment variables are configured
5. Open an issue in the GitHub repository

## 🎯 Future Enhancements

- [x] Double Down
- [x] Split Hands
- [x] Virtual-chip betting system
- [x] Game history
- [ ] Split aces special rules
- [ ] Re-splitting
- [ ] Insurance
- [ ] Multiplayer support
- [ ] Leaderboard
- [ ] Advanced game statistics and analytics
- [ ] Mobile application
- [ ] Real-time notifications

---

**Enjoy the game! 🎰**