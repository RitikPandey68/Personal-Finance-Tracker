-- ===================================================
-- FinanceFlow Enterprise Relational Schema (PostgreSQL / H2)
-- ===================================================

CREATE TABLE IF NOT EXISTS users (
    id BIGSERIAL PRIMARY KEY,
    full_name VARCHAR(100) NOT NULL,
    email VARCHAR(150) UNIQUE NOT NULL,
    password VARCHAR(255) NOT NULL,
    role VARCHAR(50) DEFAULT 'ROLE_USER',
    phone VARCHAR(50),
    profile_image VARCHAR(255),
    currency VARCHAR(20) DEFAULT 'INR',
    enabled BOOLEAN DEFAULT TRUE,
    email_verified BOOLEAN DEFAULT FALSE,
    monthly_income DOUBLE PRECISION DEFAULT 90000.0,
    pan_number VARCHAR(10),
    pan_verified BOOLEAN DEFAULT FALSE,
    two_factor_enabled BOOLEAN DEFAULT FALSE,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    last_login TIMESTAMP
);

CREATE TABLE IF NOT EXISTS transactions (
    id BIGSERIAL PRIMARY KEY,
    user_id BIGINT,
    type VARCHAR(20) NOT NULL,
    amount DOUBLE PRECISION NOT NULL,
    description VARCHAR(255) NOT NULL,
    category VARCHAR(100) NOT NULL,
    account VARCHAR(100),
    transaction_date DATE NOT NULL,
    recurring VARCHAR(50) DEFAULT 'NONE',
    currency VARCHAR(20) DEFAULT 'INR',
    tags VARCHAR(255),
    notes VARCHAR(255),
    tax_section VARCHAR(20),
    tax_deductible BOOLEAN DEFAULT FALSE,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE IF NOT EXISTS budgets (
    id BIGSERIAL PRIMARY KEY,
    category VARCHAR(100) NOT NULL,
    icon VARCHAR(50),
    color VARCHAR(20),
    budgeted_amount DOUBLE PRECISION NOT NULL,
    period_month VARCHAR(20) NOT NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE IF NOT EXISTS goals (
    id BIGSERIAL PRIMARY KEY,
    title VARCHAR(150) NOT NULL,
    emoji VARCHAR(20) DEFAULT '🎯',
    target_amount DOUBLE PRECISION NOT NULL,
    current_amount DOUBLE PRECISION DEFAULT 0.0,
    deadline VARCHAR(50),
    category VARCHAR(50),
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE IF NOT EXISTS investments (
    id BIGSERIAL PRIMARY KEY,
    ticker VARCHAR(30) NOT NULL,
    name VARCHAR(150) NOT NULL,
    asset_type VARCHAR(50) NOT NULL,
    quantity DOUBLE PRECISION NOT NULL,
    buy_price DOUBLE PRECISION NOT NULL,
    current_price DOUBLE PRECISION NOT NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE IF NOT EXISTS debts (
    id BIGSERIAL PRIMARY KEY,
    name VARCHAR(150) NOT NULL,
    total_amount DOUBLE PRECISION NOT NULL,
    remaining_balance DOUBLE PRECISION NOT NULL,
    interest_rate DOUBLE PRECISION NOT NULL,
    monthly_emi DOUBLE PRECISION NOT NULL,
    due_date VARCHAR(50),
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE IF NOT EXISTS bills (
    id BIGSERIAL PRIMARY KEY,
    name VARCHAR(150) NOT NULL,
    amount DOUBLE PRECISION NOT NULL,
    due_date VARCHAR(50) NOT NULL,
    category VARCHAR(50) NOT NULL,
    frequency VARCHAR(30) DEFAULT 'monthly',
    is_paid BOOLEAN DEFAULT FALSE,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE IF NOT EXISTS tax_lines (
    id BIGSERIAL PRIMARY KEY,
    description VARCHAR(255) NOT NULL,
    section VARCHAR(50) NOT NULL,
    amount DOUBLE PRECISION NOT NULL,
    proof_doc VARCHAR(100),
    financial_year VARCHAR(20) DEFAULT 'FY 2024-25',
    verified BOOLEAN DEFAULT TRUE,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE IF NOT EXISTS income_sources (
    id BIGSERIAL PRIMARY KEY,
    name VARCHAR(150) NOT NULL,
    category VARCHAR(50) NOT NULL,
    monthly_amount DOUBLE PRECISION NOT NULL,
    proof_doc VARCHAR(100),
    verified BOOLEAN DEFAULT TRUE,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE IF NOT EXISTS reconciliation_records (
    id BIGSERIAL PRIMARY KEY,
    internal_id VARCHAR(50) NOT NULL,
    description VARCHAR(255) NOT NULL,
    bank_ref VARCHAR(100),
    gateway_ref VARCHAR(100),
    amount DOUBLE PRECISION NOT NULL,
    bank_amount DOUBLE PRECISION NOT NULL,
    delta DOUBLE PRECISION DEFAULT 0.0,
    status VARCHAR(50) NOT NULL,
    reason VARCHAR(255),
    is_exception BOOLEAN DEFAULT FALSE,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE IF NOT EXISTS audit_logs (
    id BIGSERIAL PRIMARY KEY,
    action VARCHAR(100) NOT NULL,
    entity_name VARCHAR(100) NOT NULL,
    entity_id BIGINT,
    performed_by VARCHAR(100) DEFAULT 'SYSTEM',
    details TEXT,
    ip_address VARCHAR(50),
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);
