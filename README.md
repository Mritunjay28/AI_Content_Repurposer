# 🚀 AI Content Repurposer

An AI-powered full-stack web application that transforms a YouTube video into multiple content formats using Google's Gemini AI.

Simply paste a YouTube video URL, and the application automatically extracts the transcript, generates high-quality content, and saves it for future access.

## 🌐 Live Demo

**🔗 https://mrituai.netlify.app/**

---

## ✨ Features

### 🔐 Authentication & Security

- User Registration & Login
- JWT Authentication
- BCrypt Password Encryption
- Spring Security Protected APIs

### 🤖 AI Content Generation

- Extract YouTube video transcripts
- Generate Twitter/X Threads
- Generate LinkedIn Posts
- Generate Blog Summaries
- AI-powered content generation using Gemini API

### 📚 Content History

- Store generated content in MySQL
- View previous generations
- Detailed content history
- User-specific content access

### 💻 Frontend

- Responsive UI
- Login & Registration Pages
- Dashboard
- History Panel
- Loading Indicators
- Logout Functionality

---

# 🛠 Tech Stack

## Backend

- Java 21
- Spring Boot
- Spring Security
- Spring Data JPA
- Hibernate
- JWT Authentication
- MySQL
- WebClient

## Frontend

- HTML
- CSS
- JavaScript

## AI & APIs

- Google Gemini API
- YouTube Transcript API (RapidAPI)

---

# 🏗 Architecture

```text
                 Frontend
                     │
                     ▼
        Spring Boot REST APIs
                     │
      ┌──────────────┼──────────────┐
      │              │              │
      ▼              ▼              ▼
 JWT Authentication Gemini API YouTube Transcript API
                     │
                     ▼
              MySQL Database
```

---

# 🗄 Database Design

## Users

| Column | Type |
|---------|------|
| id | Long |
| username | String |
| email | String |
| password | String |
| role | String |
| createdAt | LocalDateTime |

---

## Content History

| Column | Type |
|---------|------|
| id | Long |
| youtubeUrl | String |
| transcript | LONGTEXT |
| twitterThread | LONGTEXT |
| linkedinPost | LONGTEXT |
| blogSummary | LONGTEXT |
| createdAt | LocalDateTime |
| user_id | Foreign Key |

---

# 📡 REST API Endpoints

## Authentication

### Register

```http
POST /api/v1/auth/signup
```

Request

```json
{
  "username": "john",
  "email": "john@example.com",
  "password": "password123"
}
```

---

### Login

```http
POST /api/v1/auth/signin
```

Request

```json
{
  "username": "john",
  "password": "password123"
}
```

Response

```text
JWT_TOKEN
```

---

## Generate Content

```http
POST /api/v1/content/generate
```

Header

```text
Authorization: Bearer <JWT_TOKEN>
```

Request

```json
{
  "youtubeUrl": "https://www.youtube.com/watch?v=VIDEO_ID"
}
```

Response

```json
{
  "transcript": "...",
  "twitterThread": "...",
  "linkedinPost": "...",
  "blogSummary": "..."
}
```

---

## Get Content History

```http
GET /api/v1/content/history
```

Header

```text
Authorization: Bearer <JWT_TOKEN>
```

---

## Get Content Details

```http
GET /api/v1/content/history/{id}
```

Header

```text
Authorization: Bearer <JWT_TOKEN>
```

---

# ⚙️ Getting Started

## 1. Clone the Repository

```bash
git clone https://github.com/YOUR_USERNAME/AI_Content_Repurposer.git
cd AI_Content_Repurposer
```

---

## 2. Create Database

```sql
CREATE DATABASE ai_content_repurposer;
```

---

## 3. Run the Application

```bash
mvn spring-boot:run
```

The backend will start at

```
http://localhost:8080
```

---

# 📸 Project Screenshots

## Login Page



---

## Register Page


---

## Dashboard


---

## History Panel


---

# 🚀 Future Improvements

- Export generated content as PDF
- Multiple writing styles & templates
- AI tone customization
- User profile management
- OAuth Login (Google/GitHub)
- Docker support
- Cloud deployment
- Content scheduling
- Multi-language support

---

# 👨‍💻 Author

Developed by **Mritunjay28**

### Built With

- Spring Boot
- Spring Security
- JWT Authentication
- MySQL
- Google Gemini AI
- HTML, CSS & JavaScript

---

⭐ If you found this project useful, consider giving it a star!
USED AI to Polish Frontend