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

## ⚡ Performance Metrics

> All metrics below were **measured from a live run** of the application on a Windows 11 machine using OpenJDK 21 (JBR 21.0.9) and the H2 in-memory database (dev profile).

### 🏗️ Build Performance

| Metric | Value |
|---|---|
| **Maven Build Time** (clean package, skip tests) | `~13 seconds` |
| **Deployable JAR Size** | `74.1 MB` (fat JAR with all dependencies) |
| **Java Source Files** | `86 .java files` |
| **Total Lines of Code (Backend)** | `~4,384 lines` |
| **API Controller Classes** | `13 REST Controllers` |
| **Spring Data JPA Repositories** | `14 Repository Interfaces` |
| **Database Tables** | `11 relational tables` |

---

### 🚀 Server Startup Performance

| Metric | Value |
|---|---|
| **Cold Start Time** (Spring Boot + Hibernate DDL + Seed Data) | `~9.7 – 9.9 seconds` |
| **Embedded Web Server** | Apache Tomcat 10.1.17 |
| **JVM** | OpenJDK 21.0.9 (JBR-21.0.9+1-1038.78-jcef) |
| **Spring Context Initialization** | `~2.8 seconds` |
| **HikariCP Connection Pool** | Starts in `~323 ms` |
| **Database Schema Auto-Creation** | H2 DDL auto-generated via Hibernate (`ddl-auto=update`) |
| **Seed Data Insertion** | Transactions, Budgets, Goals, Investments, Debts, Bills, Tax Lines — all on startup |

---

### 📡 API Response Time Benchmarks

> Measured across **25 repeated requests per endpoint** (after JVM warm-up with JWT Bearer authentication). Full raw logs and percentiles are available in [`performance_benchmark_results.json`](./performance_benchmark_results.json).

| Endpoint | Method | Avg Latency | Median (p50) | 95th %ile (p95) | Min / Max | Status |
|---|---|---|---|---|---|---|
| `/api/auth/login` | `POST` | **105.5 ms** | 99.8 ms | 162.7 ms | 88.4 / 162.7 ms | `200 OK` |
| `/api/auth/me` | `GET` | **10.0 ms** | 3.6 ms | 23.2 ms | 2.0 / 28.8 ms | `200 OK` |
| `/api/budgets` | `GET` | **12.7 ms** | 8.5 ms | 30.2 ms | 5.6 / 32.8 ms | `200 OK` |
| `/api/investments` | `GET` | **10.9 ms** | 7.8 ms | 31.6 ms | 5.3 / 36.1 ms | `200 OK` |
| `/api/goals` | `GET` | **8.3 ms** | 7.5 ms | 15.8 ms | 6.2 / 20.0 ms | `200 OK` |
| `/api/bills` | `GET` | **10.4 ms** | 7.2 ms | 32.3 ms | 5.6 / 34.9 ms | `200 OK` |
| `/api/tax/summary` | `GET` | **15.0 ms** | 13.3 ms | 27.9 ms | 9.7 / 35.7 ms | `200 OK` |
| `/api/transactions/summary` | `GET` | **15.1 ms** | 11.7 ms | 34.3 ms | 9.4 / 57.5 ms | `200 OK` |
| `/api/transactions/by-category` | `GET` | **7.7 ms** | 6.5 ms | 8.3 ms | 5.1 / 37.4 ms | `200 OK` |
| `/api/transactions/search?q=Swiggy` | `GET` | **13.5 ms** | 9.4 ms | 31.9 ms | 7.1 / 70.6 ms | `200 OK` |
| `/api/fintech/anomaly/scan` | `GET` | **7.4 ms** | 4.0 ms | 25.7 ms | 2.8 / 26.6 ms | `200 OK` |
| `/api/fintech/ledger/t-accounts` | `GET` | **6.8 ms** | 4.0 ms | 17.6 ms | 2.3 / 17.6 ms | `200 OK` |
| `/api/finance-ops/cash-forecast` | `GET` | **11.7 ms** | 4.7 ms | 26.9 ms | 2.8 / 33.4 ms | `200 OK` |

---

### 💳 SmartMerchant Card Switcher Micro-Benchmark

> Micro-benchmark over **500 live requests** evaluating merchant classification and reward optimization across 10 top vendors (*Swiggy, Zomato, Amazon, Flipkart, Uber, MakeMyTrip, Blinkit, Starbucks, BookMyShow, Croma*):

| Metric | Measured Value | Description |
|---|---|---|
| **Total Iterations** | `500 requests` | Warm JVM, sub-millisecond precision |
| **Execution Duration** | `6.27 seconds` | Completed in single harness run |
| **Engine Throughput** | **`79.71 req/sec`** | Decision & categorization rate |
| **Average Latency** | **`12.43 ms`** | Mean end-to-end response time |
| **Median Latency (p50)** | **`14.63 ms`** | 50th percentile latency |
| **90th Percentile (p90)** | **`18.18 ms`** | 90% of requests finish under this time |
| **95th Percentile (p95)** | **`26.56 ms`** | Tail latency under burst |
| **99th Percentile (p99)** | **`34.36 ms`** | Peak tail latency |
| **Min / Max Latency** | `2.23 ms` / `48.31 ms` | Warm minimum / GC peak |

---

### ⚡ Payment Workflow Speedup & Ingestion Benchmark

> Comparison of **Sequential Writes** (`/api/transactions`) versus **Batch Bulk Ingestion** (`/api/transactions/bulk`) under identical database load:

| Parameter | Sequential Processing | Batch / Bulk Processing | Performance Gain |
|---|---|---|---|
| **Batch Sample Size** | 30 individual transactions | 30 transactions in 1 bulk call | — |
| **Total Elapsed Time** | `333.61 ms` | `52.50 ms` | **6.35x faster** |
| **Effective Latency / Tx** | `10.97 ms` | **`1.75 ms`** | **84.0% latency drop** |
| **Write Throughput** | `89.92 tx/sec` | **`571.43 tx/sec`** | **+535.5% throughput** |
| **Speedup Ratio** | `1.0x` (Baseline) | **`6.35x`** | **6.35x faster ingestion** |

---

### 🧠 Runtime Memory Footprint

| Metric | Value |
|---|---|
| **JVM Working Set (Resident Memory)** | `~200 – 352 MB` |
| **JVM Virtual Memory** | `~8,000 – 10,400 MB` (standard JVM reservation) |
| **HikariCP Connection Pool Size** | Default 10 connections (H2 in-memory) |
| **Spring Cache Type (Dev)** | Simple in-memory cache (`spring.cache.type=simple`) |
| **Redis Cache Type (Prod)** | Redis-ready (`spring.cache.type=redis`) |

---

### 📊 Code Complexity & Architecture

| Category | Count |
|---|---|
| **REST API Endpoints** | `~45+ mapped routes` across 13 controllers |
| **Service Layer Classes** | `15 service classes` |
| **JPA Entity Models** | `11 entity classes` |
| **Security Filters** | JWT Auth Filter + Spring Security 6 filter chain |
| **Background Algorithms** | BCrypt hash (login), Compound interest engine (forecaster), DCF reconciliation, Risk scoring |
| **Export Formats Supported** | PDF (iText7) + CSV (Apache Commons CSV) |
| **Database Indexes** | Email UNIQUE constraint on `users`, PAN UNIQUE on `pan_verifications` |

---

### 🔒 Security Performance

| Feature | Implementation |
|---|---|
| **Password Hashing** | BCrypt (strength 10) — ~150–400 ms intentional slowdown |
| **JWT Token Generation** | HMAC-SHA256 via JJWT 0.12.3 — `< 5 ms` |
| **JWT Validation per Request** | `< 2 ms` (stateless, no DB lookup) |
| **Token Expiry** | 24 hours (`86,400,000 ms`) |
| **Token Blacklist** | In-memory `ConcurrentHashSet` (Redis-upgradeable) |

---

### 🐳 Docker Performance (Production Profile)

| Configuration | Value |
|---|---|
| **Docker Base Image** | `eclipse-temurin:17-jre-alpine` |
| **Container Port** | `8002` |
| **DB** | PostgreSQL 15 (separate container) |
| **Build Command** | `docker-compose up -d --build` |
| **Health Check Endpoint** | `GET /actuator/health` |

---

## 👤 Author & Maintainer
- **Developer:** Ritik Pandey
- **GitHub:** [@RitikPandey68](https://github.com/RitikPandey68)
- **Project:** Personal Finance Tracker & AI FinTech Suite
