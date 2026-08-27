# 💰 FinanceFlow — Personal Finance Tracker & AI FinTech Platform

[![Spring Boot 3.2.1](https://img.shields.io/badge/Spring_Boot-3.2.1-6DB33F?style=for-the-badge&logo=springboot&logoColor=white)](https://spring.io/projects/spring-boot)
[![Java 17](https://img.shields.io/badge/Java-17-ED8B00?style=for-the-badge&logo=openjdk&logoColor=white)](https://www.oracle.com/java/)
[![PostgreSQL](https://img.shields.io/badge/PostgreSQL-15-316192?style=for-the-badge&logo=postgresql&logoColor=white)](https://www.postgresql.org/)
[![Chart.js](https://img.shields.io/badge/Chart.js-4.4.1-FF6384?style=for-the-badge&logo=chartdotjs&logoColor=white)](https://www.chartjs.org/)
[![Docker](https://img.shields.io/badge/Docker-Ready-2496ED?style=for-the-badge&logo=docker&logoColor=white)](https://www.docker.com/)

**FinanceFlow** is a modern, full-stack Personal Finance Management & AI FinTech Analytics Platform. Built with **Spring Boot 3 (REST API, Spring Security 6, JPA/Hibernate)** and an interactive **Glassmorphic Vanilla HTML5/CSS3/JavaScript & Chart.js** frontend, it provides end-to-end wealth management, automated budgeting, investment portfolio tracking, tax planning, and AI-driven financial health guidance.

---

## 🌟 Key Features & Functional Modules

### 1. 📊 Financial Dashboard & Health Score Engine
- **Live KPI Cards:** Instant visibility of Net Worth, Total Monthly Inflow, Monthly Expenses, and Total Savings.
- **AI Financial Health Score (0–100):** Real-time algorithm assessing Debt-to-Income (DTI) ratio, emergency fund coverage, savings rate, and expense discipline.
- **Dynamic Charts:** Powered by Chart.js 4.4.1 — Monthly cash flow comparison (Income vs Expense bar charts), Expense distribution (Doughnut charts), and 6-Month asset growth trends.

### 2. 💸 Transactions Manager & Smart Categorization
- Add, edit, filter, and delete Income and Expense records.
- Categorization across *Food & Dining, Housing & Rent, Shopping, Utilities, Investments, Salary, Freelance*, and more.
- Filter transactions by date range, category, payment method (UPI, Net Banking, Credit Card), and search query.

### 3. 🎯 Category Budgeting & Overspending Alerts
- Set monthly budget limits per category.
- Visual progress bars with dynamic color shifts (Green $\rightarrow$ Yellow warning at 80% $\rightarrow$ Red alert when exceeded).
- Real-time remaining budget calculations to prevent overspending.

### 4. 📈 Investment Portfolio & Asset Tracker
- Multi-asset tracking: **Stocks, Mutual Funds, Crypto, Gold, and Fixed Deposits (FDs)**.
- Live calculation of Invested Value, Current Market Value, Absolute Return (₹), and Percentage Return (%).
- Visual asset distribution breakdown.

### 5. 💳 Debt & Bill Management
- **Debt Payoff Engine:** Track loans, credit card balances, interest rates, and minimum monthly payments.
- **Upcoming Bill Reminders:** Track due dates for electricity, rent, subscriptions, and credit card bills with status toggles (*Paid / Pending*).

### 6. 🤖 AI Financial Advisor & Conversational Context Engine
- Built-in conversational AI assistant (`ChatController`, `ChatService`, `FinancialContextService`).
- Understands user's real-time financial context and provides actionable advice for questions like:
  - *"Mera is month spending kaisa hai?"*
  - *"Where am I spending the most?"*
  - *"Can I afford buying a new laptop this month?"*
  - *"How can I improve my financial health score?"*

### 7. 🔮 Advanced FinTech Add-on Suite
- **📈 Investment Win/Loss Recovery Simulator:** Projections for recovering portfolio drawdowns across multiple CAGR return scenarios (8%, 12%, 15%).
- **🧠 Risk Profiling Assessment:** 7-question psychometric evaluation categorizing investors into `CONSERVATIVE`, `MODERATE`, or `AGGRESSIVE` with recommended asset allocation models.
- **🔮 Future Wealth & Security Forecaster:** Compound wealth projection modeling net worth milestones up to the year 2040.
- **🪪 PAN & Income Verification:** Sandbox PAN card regex verification (`[A-Z]{5}[0-9]{4}[A-Z]{1}`) with verified badges and multi-source verified income streams.
- **📑 Financial Statements Export:** Instant export of comprehensive financial summaries and transaction logs in PDF and CSV formats.

---

## 🛠️ Technology Stack

| Layer | Technology |
|---|---|
| **Backend Framework** | Java 17, Spring Boot 3.2.1 |
| **Security** | Spring Security 6, JJWT (io.jsonwebtoken 0.12.3), BCrypt Password Encoder |
| **Data Persistence** | Spring Data JPA, Hibernate ORM |
| **Databases** | H2 In-Memory Database (Development) / PostgreSQL 15 (Production) |
| **Caching** | Spring Boot Cache (Redis-ready) |
| **Frontend** | Vanilla HTML5, Modern Glassmorphic CSS3, JavaScript (ES6+) |
| **Data Visualization** | Chart.js 4.4.1 (UMD) |
| **Icons & Typography** | FontAwesome 6.5.0, Google Fonts (Inter & Space Grotesk) |
| **DevOps & Container** | Docker, Docker Compose, Maven |

---

## 🏗️ Architecture & Database Design

The relational schema is configured in [`schema.sql`](file:///c:/Users/pande/OneDrive%20-%20MSFT/Desktop/Project%20for%20Job%20Apply/02-personal-finance-tracker/backend/src/main/resources/schema.sql) with full referential integrity:

```
                                  ┌───────────────┐
                                  │     USERS     │
                                  └───────┬───────┘
                                          │ 1:N
        ┌───────────────────┬─────────────┼─────────────┬───────────────────┐
        ▼                   ▼             ▼             ▼                   ▼
┌──────────────┐    ┌──────────────┐┌───────────┐┌──────────────┐   ┌───────────────┐
│ TRANSACTIONS │    │   BUDGETS    ││   GOALS   ││ INVESTMENTS  │   │  AUDIT_LOGS   │
└──────────────┘    └──────────────┘└───────────┘└──────────────┘   └───────────────┘
        │                   │
        ▼                   ▼
┌──────────────┐    ┌──────────────┐
│  CATEGORIES  │    │ BILL_REMINDR │
└──────────────┘    └──────────────┘
```

---

## 🔌 API Endpoints Summary

### Authentication & Authorization (`/api/auth`)
| Method | Endpoint | Description | Access |
|---|---|---|---|
| `POST` | `/api/auth/login` | Email & password login, returns JWT Bearer token | Public |
| `POST` | `/api/auth/register` | Register new user account with default profile | Public |
| `POST` | `/api/auth/logout` | Revokes JWT token and adds to server blacklist | Bearer |
| `GET` | `/api/auth/me` | Fetches active user session and profile | Bearer |
| `POST` | `/api/auth/oauth2/callback` | OAuth2 account sign-in / registration routing | Public |
| `POST` | `/api/auth/oauth2/google-token` | Google Cloud Identity ID Token verification endpoint | Public |
| `POST` | `/api/auth/mobile/send-otp` | Generates & dispatches 6-digit mobile SMS OTP | Public |
| `POST` | `/api/auth/mobile/verify-otp` | Validates mobile OTP and signs in user | Public |

### Core Financial Services
| Method | Endpoint | Description |
|---|---|---|
| `GET / POST` | `/api/transactions` | Fetch all transactions / Create new transaction |
| `GET / POST` | `/api/budgets` | Retrieve category budgets / Set monthly limit |
| `GET / POST` | `/api/investments` | Retrieve portfolio / Add investment asset |
| `GET / POST` | `/api/goals` | List financial goals / Create new target goal |
| `GET / POST` | `/api/bills` | List bills & reminders / Schedule recurring bill |
| `POST` | `/api/ai/chat` | Send prompt to AI Financial Context Advisor |
| `GET` | `/api/fintech/health-score` | Compute comprehensive 0-100 Financial Health score |
| `POST` | `/api/fintech/verify-pan` | Validate PAN card format and issue verification badge |

---

## 🚀 Getting Started Locally

### Prerequisites
- **Java Development Kit (JDK 17+)**
- **Apache Maven 3.8+**
- **Python 3.x** (or any static HTTP server for frontend)
- **Docker & Docker Compose** (Optional, for containerized run)

---

### Step 1: Clone the Repository
```bash
git clone https://github.com/RitikPandey68/Personal-Finance-Tracker.git
cd Personal-Finance-Tracker
```

### Step 2: Run Backend (Spring Boot)
```bash
cd backend
mvn clean package -DskipTests
java -jar target/finance-tracker-1.0.0.jar
```
*Backend API server will start on:* `http://localhost:8002`  
*H2 Database Console:* `http://localhost:8002/h2-console` (JDBC URL: `jdbc:h2:mem:financedb`, User: `sa`, Password: *(blank)*)

---

### Step 3: Run Frontend Dashboard
In a separate terminal:
```bash
cd frontend
python -m http.server 5500
```
*Open your browser and navigate to:* **`http://localhost:5500`**

---

### Step 4: Default Sign-In Credentials

To explore the dashboard immediately with pre-loaded mock financial data:
- **Email:** `ritik@financeflow.com`
- **Password:** `SecurePass123!`

---

## 🐳 Docker Deployment (One-Click)

To launch the entire platform with PostgreSQL and backend containerized:
```bash
docker-compose up -d --build
```
- **Web App:** `http://localhost:5500`
- **API Server:** `http://localhost:8002`
- **PostgreSQL Database:** `localhost:5432` (DB: `financedb`, User: `finance_user`, Pass: `finance_secret_2026`)

---

## 👤 Author & Maintainer
- **Developer:** Ritik Pandey
- **GitHub:** [@RitikPandey68](https://github.com/RitikPandey68)
- **Project:** Personal Finance Tracker & AI FinTech Suite
