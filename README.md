# AI Content Repurposer

AI-powered platform that converts a YouTube video into multiple content formats including:

- Twitter/X Thread
- LinkedIn Post
- Blog Summary

The application extracts the transcript from a YouTube video, processes it using Google's Gemini AI model, and stores generated content for future access.

---

## Features

### Authentication & Security
- User Registration
- User Login
- JWT Authentication
- Password Encryption using BCrypt
- Protected APIs with Spring Security

### Content Generation
- Extract transcript from YouTube videos
- Generate Twitter/X Threads
- Generate LinkedIn Posts
- Generate Blog Summaries
- AI-powered content generation using Gemini API

### Content History
- Save generated content in MySQL
- View previously generated content
- View detailed content history
- User-specific content access

### Frontend
- User Registration Page
- User Login Page
- Dashboard
- History Panel
- Loading Spinner
- Responsive UI
- Logout Functionality

---

## Tech Stack

### Backend
- Java 21
- Spring Boot
- Spring Security
- Spring Data JPA
- Hibernate
- JWT Authentication
- MySQL
- WebClient

### Frontend
- HTML
- CSS
- JavaScript

### APIs
- Gemini API
- YouTube Transcript API (RapidAPI)

---

## Architecture

```text
Frontend
    |
    v
Spring Boot REST APIs
    |
    +---- JWT Authentication
    |
    +---- YouTube Transcript API
    |
    +---- Gemini API
    |
    v
MySQL Database
```

---

## Database Design

### Users

| Column | Type |
|----------|----------|
| id | Long |
| username | String |
| email | String |
| password | String |
| role | String |
| createdAt | LocalDateTime |

---

### Content History

| Column | Type |
|----------|----------|
| id | Long |
| youtubeUrl | String |
| transcript | LONGTEXT |
| twitterThread | LONGTEXT |
| linkedinPost | LONGTEXT |
| blogSummary | LONGTEXT |
| createdAt | LocalDateTime |
| user_id | FK |

---

## API Endpoints

### Authentication

#### Register

```http
POST /api/v1/auth/signup
```

Request:

```json
{
  "username":"john",
  "email":"john@example.com",
  "password":"password123"
}
```

---

#### Login

```http
POST /api/v1/auth/signin
```

Request:

```json
{
  "username":"john",
  "password":"password123"
}
```

Response:

```text
JWT_TOKEN
```

---

### Generate Content

```http
POST /api/v1/content/generate
```

Headers:

```text
Authorization: Bearer <JWT_TOKEN>
```

Request:

```json
{
  "youtubeUrl":"https://www.youtube.com/watch?v=VIDEO_ID"
}
```

Response:

```json
{
  "transcript":"...",
  "twitterThread":"...",
  "linkedinPost":"...",
  "blogSummary":"..."
}
```

---

### Get Content History

```http
GET /api/v1/content/history
```

Headers:

```text
Authorization: Bearer <JWT_TOKEN>
```

---

### Get Content Details

```http
GET /api/v1/content/history/{id}
```

Headers:

```text
Authorization: Bearer <JWT_TOKEN>
```

---

## Setup Instructions

### 1. Clone Repository

```bash
git clone https://github.com/YOUR_USERNAME/AI_Content_Repurposer.git
```

```bash
cd AI_Content_Repurposer
```

---

### 2. Create Database

```sql
CREATE DATABASE ai_content_repurposer;
```

---

### 3. Create application.properties

Create:

```text
src/main/resources/application.properties
```

Add:

```properties
spring.application.name=AI_Content_Repurposer

spring.datasource.url=jdbc:mysql://localhost:3306/ai_content_repurposer
spring.datasource.username=YOUR_DB_USERNAME
spring.datasource.password=YOUR_DB_PASSWORD

spring.jpa.hibernate.ddl-auto=update
spring.jpa.properties.hibernate.dialect=org.hibernate.dialect.MySQLDialect

jwt.secret=YOUR_SECRET_KEY
jwt.expiration=86400000

gemini.api.key=YOUR_GEMINI_API_KEY

transcript.api.key=YOUR_RAPIDAPI_KEY
transcript.api.host=youtube-transcripts.p.rapidapi.com
```

---

### 4. Run Application

```bash
mvn spring-boot:run
```

Application runs at:

```text
http://localhost:8080
```

---

## Project Screenshots

### Login Page

<img width="1843" height="881" alt="Screenshot 2026-06-13 152341" src="https://github.com/user-attachments/assets/9fdd43db-bdbd-4763-9982-b9aecd1706d8" />

---

### Register Page

<img width="1860" height="914" alt="Screenshot 2026-06-13 152325" src="https://github.com/user-attachments/assets/79bbcac0-b32f-4de5-afd0-3c9c2c8722cb" />

---

### Dashboard

<img width="1831" height="874" alt="Screenshot 2026-06-13 152356" src="https://github.com/user-attachments/assets/3a6f2613-9522-438d-83bf-500c3217139e" />



---

### History Panel

<img width="1871" height="907" alt="Screenshot 2026-06-13 152458" src="https://github.com/user-attachments/assets/56ba3108-0f69-4041-98f8-ce65b23a4776" />


---

## Future Improvements

- Export generated content as PDF
- Content Templates
- User Profile Management
- OAuth Login (Google/GitHub)
- Docker Support
- Cloud Deployment
- AI Tone Customization
- Content Scheduling

---

## Author

- Developed as a full-stack AI project using Spring Boot, JWT Authentication, MySQL, and Gemini AI.
- Made By Mritunjay28 
- Used AI To Polish Frontend .
