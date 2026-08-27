import os
from reportlab.lib.pagesizes import letter
from reportlab.lib import colors
from reportlab.lib.styles import getSampleStyleSheet, ParagraphStyle
from reportlab.platypus import (
    SimpleDocTemplate, Paragraph, Spacer, Table, TableStyle, PageBreak, KeepTogether, HRFlowable
)

def generate_pdf():
    pdf_path = os.path.abspath("AI_Finance_Controller_Presentation_Guide.pdf")
    doc = SimpleDocTemplate(
        pdf_path,
        pagesize=letter,
        rightMargin=36,
        leftMargin=36,
        topMargin=36,
        bottomMargin=36
    )

    styles = getSampleStyleSheet()
    
    # Custom Colors
    c_primary = colors.HexColor("#1e1e2d")
    c_accent = colors.HexColor("#4f46e5")
    c_accent_dark = colors.HexColor("#3730a3")
    c_emerald = colors.HexColor("#059669")
    c_amber = colors.HexColor("#d97706")
    c_rose = colors.HexColor("#e11d48")
    c_dark = colors.HexColor("#0f172a")
    c_text = colors.HexColor("#334155")
    c_bg_light = colors.HexColor("#f1f5f9")
    
    # Typography Styles
    title_style = ParagraphStyle(
        'DocTitle',
        parent=styles['Heading1'],
        fontName='Helvetica-Bold',
        fontSize=18,
        leading=22,
        textColor=c_accent,
        spaceAfter=4
    )
    
    subtitle_style = ParagraphStyle(
        'DocSubTitle',
        parent=styles['Normal'],
        fontName='Helvetica-Bold',
        fontSize=10.5,
        leading=14,
        textColor=c_text,
        spaceAfter=8
    )

    h1_style = ParagraphStyle(
        'SectionH1',
        parent=styles['Heading2'],
        fontName='Helvetica-Bold',
        fontSize=12.5,
        leading=16,
        textColor=c_dark,
        spaceBefore=10,
        spaceAfter=6
    )

    h2_style = ParagraphStyle(
        'SectionH2',
        parent=styles['Heading3'],
        fontName='Helvetica-Bold',
        fontSize=10,
        leading=14,
        textColor=c_accent,
        spaceBefore=6,
        spaceAfter=3
    )

    body_style = ParagraphStyle(
        'DocBody',
        parent=styles['Normal'],
        fontName='Helvetica',
        fontSize=8.5,
        leading=12.5,
        textColor=c_text,
        spaceAfter=4
    )

    feature_title_style = ParagraphStyle(
        'FeatureTitle',
        parent=styles['Normal'],
        fontName='Helvetica-Bold',
        fontSize=9,
        leading=13,
        textColor=c_accent_dark,
        spaceBefore=4,
        spaceAfter=2
    )

    script_cue_style = ParagraphStyle(
        'ScriptCue',
        parent=styles['Normal'],
        fontName='Helvetica-Bold',
        fontSize=8.5,
        leading=12,
        textColor=c_amber,
        spaceAfter=2
    )

    script_speech_style = ParagraphStyle(
        'ScriptSpeech',
        parent=styles['Normal'],
        fontName='Helvetica-Oblique',
        fontSize=8.5,
        leading=12.5,
        textColor=c_dark,
        spaceAfter=5
    )

    callout_style = ParagraphStyle(
        'CalloutText',
        parent=styles['Normal'],
        fontName='Helvetica-Bold',
        fontSize=8.5,
        leading=12,
        textColor=c_dark
    )

    elements = []

    # Title & Header
    elements.append(Paragraph("🏦 FinanceFlow — AI Finance Controller & FinTech Platform", title_style))
    elements.append(Paragraph("Track 04: 'Run the books and the cash position' | Complete Feature Blueprint & 5-Min Pitch Guide", subtitle_style))
    elements.append(HRFlowable(width="100%", thickness=1.5, color=c_accent, spaceBefore=2, spaceAfter=8))

    # SECTION 1: COMPLETE FEATURE-BY-FEATURE BREAKDOWN
    elements.append(Paragraph("1. Complete Features Breakdown (Har Ek Feature Ki Detail)", h1_style))
    elements.append(Paragraph("Neeche project ke <b>saare 11 core modules aur unke features</b> ko step-by-step explain kiya gaya hai taaki aap video ya interview me har ek feature ko confidence ke sath demonstrate kar sakein:", body_style))
    elements.append(Spacer(1, 4))

    features = [
        ("1. 📊 Executive Dashboard & AI Health Score (0–100)",
         "• <b>Live KPI Cards:</b> Real-time Net Worth, Monthly Inflow, Total Expenses, aur Total Savings ka aggregate view.<br/>"
         "• <b>AI Financial Health Score:</b> Algorithm jo Debt-to-Income (DTI), Emergency Fund Buffer, Savings Rate aur Spending Discipline ko measure karke 0-100 scoring provide karta hai.<br/>"
         "• <b>Interactive Chart.js Visualizations:</b> 6-Month Income vs Expense trends, Category-wise Expense distribution (Doughnut), aur dynamic cash inflow graphs."),

        ("2. 💸 Transactions Manager & Smart Categorization",
         "• <b>Full CRUD Operations:</b> Income aur Expense transactions ko add, edit, delete aur list karne ki facility.<br/>"
         "• <b>Auto-Categorization:</b> Food, Housing/Rent, Utilities, Shopping, Salary, Investments, etc.<br/>"
         "• <b>Multi-Field Filter:</b> Date range, category, payment mode (UPI, Net Banking, Credit Card, Cash) aur search keyword se instantly transactions filter hote hain."),

        ("3. 🎯 Category Budgeting & Overspending Alert Engine",
         "• <b>Category-wise Monthly Limits:</b> Har category ke liye monthly spending target set kiya jata hai.<br/>"
         "• <b>3-Tier Visual Alerts:</b> Safe (Green) &rarr; Warning at 80% limit (Yellow) &rarr; Overbudget Breach (Red alert).<br/>"
         "• <b>Dynamic Remaining Balance:</b> Real-time balance update hota hai jo user ko overspend karne se prevent karta hai."),

        ("4. 📈 Investment Portfolio & Asset Tracker",
         "• <b>Multi-Asset Support:</b> Stocks, Mutual Funds, Cryptocurrencies, Gold, aur Fixed Deposits (FDs).<br/>"
         "• <b>Performance Analytics:</b> Invested Amount, Current Market Value, Absolute Return (&amp;#8377;), aur Percentage CAGR Return (%).<br/>"
         "• <b>Visual Asset Allocation:</b> Portfolio diversification ko visual doughnut chart me dikhata hai."),

        ("5. 🎯 Target Financial Goals Simulator",
         "• <b>Goal Tracking:</b> Emergency Fund, House Downpayment, Vacation, etc. ke target set karna.<br/>"
         "• <b>Visual Progress:</b> Target Date, Target Amount, Current Saved Amount aur Percentage completion timeline."),

        ("6. 💳 Debt Payoff & Bill Reminders Management",
         "• <b>Debt Payoff Engine:</b> Loans, Credit Cards aur EMIs ka interest rate aur minimum monthly payment track karna.<br/>"
         "• <b>Upcoming Bill Alerts:</b> Electricity, Rent, WiFi, aur Subscriptions ke due date reminders with 'Paid/Pending' status toggle."),

        ("7. 📋 Tax Planning & Deductions Matcher",
         "• <b>Old vs New Regime Estimator:</b> Tax slabs aur comparative savings calculation.<br/>"
         "• <b>Tax-Line Matcher:</b> 80C, 80D, HRA aur standard deductions ka automatic deduction mapping."),

        ("8. 🤖 AI Financial Context Advisor (Chatbot)",
         "• <b>Conversational Context Engine:</b> LLM / rule-based AI jo ledger data se connect hoke natural language me jawab deta hai.<br/>"
         "• <b>Domain Tools:</b> Budget Tool, Expense Tool, Investment Tool, aur Health Score Explanation Tool.<br/>"
         "• <b>Sample Prompts:</b> 'Mera is month spending kaisa hai?', 'Where am I overspending?', 'Can I afford buying X?'"),

        ("9. 🏦 Autonomous Finance-Ops & Multi-Source Reconciliation (Track 04 Core)",
         "• <b>50+ Record Synthetic Batch Engine:</b> Bank Statements, ERP Invoices aur Payment Gateways ka 3-way multi-source matching.<br/>"
         "• <b>2-Pass Heuristic Matching:</b> Strict hash matching + tolerance delta for 2% gateway processing fees &amp; +/- 48h settlement windows.<br/>"
         "• <b>Honest Exception List:</b> Unmatched transactions ko root-cause categorization ke sath report karta hai (AMOUNT_MISMATCH, UNPOSTED_ERP, GATEWAY_FEE_DISCREPANCY).<br/>"
         "• <b>Measured Accuracy &amp; Throughput:</b> Real match rate (96%) aur tx/sec throughput compute karta hai."),

        ("10. 🔮 Advanced FinTech Add-ons (Recovery, Risk &amp; PAN)",
         "• <b>Win/Loss Recovery Simulator:</b> Portfolio loss recovery timeline multi-return scenarios par project karta hai (8%, 12%, 15% CAGR).<br/>"
         "• <b>Risk Profiling Assessment:</b> 7-question evaluation assessing Conservative, Moderate, ya Aggressive profile.<br/>"
         "• <b>2040 Wealth Forecaster:</b> Compound wealth simulation modeling net worth milestones up to year 2040.<br/>"
         "• <b>Sandbox PAN &amp; Income Verification:</b> Regex verification ([A-Z]{5}[0-9]{4}[A-Z]{1}) with verified security badge."),

        ("11. 🛡️ Security & Enterprise Architecture",
         "• <b>Spring Security 6 &amp; JWT:</b> Cryptographic HMAC-SHA256 JWT tokens with server-side token blacklist on logout.<br/>"
         "• <b>PostgreSQL 15 &amp; H2 Database:</b> schema.sql with complete foreign keys &amp; referential integrity.<br/>"
         "• <b>Docker &amp; Kubernetes Ready:</b> Containerized multi-service setup (docker-compose.yml, k8s manifests).")
    ]

    for title, desc in features:
        elements.append(Paragraph(title, feature_title_style))
        elements.append(Paragraph(desc, body_style))
        elements.append(Spacer(1, 2))

    elements.append(Spacer(1, 6))

    # SECTION 2: 5-MINUTE VIDEO DEMO SCRIPT
    elements.append(Paragraph("2. 🎬 5-Minute Video Pitch Script (Minute-by-Minute Guide)", h1_style))
    elements.append(Paragraph("Video record karte waqt aap screen par feature open karein aur neeche di gayi speech ko smoothly follow karein:", body_style))
    elements.append(Spacer(1, 4))

    # Minute 1
    elements.append(Paragraph("⏱️ Minute 1 [0:00 – 0:45] — Introduction & The Verification Bottleneck", h2_style))
    elements.append(Paragraph("🖥️ <b>Screen Action:</b> Show live Glassmorphic Dashboard (http://localhost:5500) & GitHub Repository.", script_cue_style))
    elements.append(Paragraph('"Hello everyone, I’m Ritik Pandey, and this is my submission for Track 04: AI Finance Controller — Run the books and the cash position. In 2026, the builder consensus is clear: generation speed is no longer the bottleneck—verification capacity is. Finance teams still spend countless manual hours reconciling multi-source ledgers, tracking settlement discrepancies, and forecasting forward cash positions. To solve this, I built FinanceFlow—an autonomous AI Finance Controller that automates multi-source reconciliation across 50+ record batches, reports measured accuracy with an honest exception list, provides a real-time Settlement Q&A Agent, and forecasts dynamic cash runway."', script_speech_style))
    elements.append(Spacer(1, 2))

    # Minute 2
    elements.append(Paragraph("⏱️ Minute 2 [0:45 – 2:00] — Multi-Source Reconciliation & Honest Exception List", h2_style))
    elements.append(Paragraph("🖥️ <b>Screen Action:</b> Open Finance Ops tab, click 'Run 50+ Record Batch Auto-Reconciliation', highlight 96% Match Rate & Exceptions.", script_cue_style))
    elements.append(Paragraph('"Let’s dive straight into the core track requirement: Multi-Source Reconciliation. Here, our engine ingests a synthetic batch of 50+ transactions across three independent pipelines: Bank Statements, ERP Ledgers, and Payment Gateway records. Notice the high bar we set: First, it achieves a measured match rate of 96% with automated 3-way matching. Second, it doesn’t cherry-pick. It surfaces an Honest Exception List highlighting unresolved records—such as a ₹150 gateway fee mismatch or an unposted ERP invoice. Each exception is categorized with an exact discrepancy reason for auditability."', script_speech_style))
    elements.append(Spacer(1, 2))

    # Minute 3
    elements.append(Paragraph("⏱️ Minute 3 [2:00 – 3:00] — Settlement Q&A Agent & Forward Cash Forecaster", h2_style))
    elements.append(Paragraph("🖥️ <b>Screen Action:</b> Open AI Chat drawer, ask settlement question, then show Cash Runway chart.", script_cue_style))
    elements.append(Paragraph('"Next is our Settlement Q&A Agent. Connecting directly to our in-memory financial context service, the AI assistant answers real-time ledger questions like: Where are our cash bottlenecks? and Why are specific vendor payouts delayed? Moving to the Forward Cash Forecaster: instead of static spreadsheets, our model projects 30, 60, and 90-day cash runway based on recurring debt obligations, verified income streams, and dynamic burn rates, giving leadership complete visibility over liquidity."', script_speech_style))
    elements.append(Spacer(1, 2))

    # Minute 4
    elements.append(Paragraph("⏱️ Minute 4 [3:00 – 4:15] — Core FinTech Modules (Budgets, Portfolio, Tax, PAN)", h2_style))
    elements.append(Paragraph("🖥️ <b>Screen Action:</b> Quick tour of Transactions, Category Budgeting, Investment Portfolio, Tax Estimator, and backend code in IDE.", script_cue_style))
    elements.append(Paragraph('"Beyond reconciliation, FinanceFlow provides a complete FinTech ecosystem: A Category Budgeting Engine with near-breach visual alerts, an Investment Asset Tracker with win/loss recovery projections, Tax-Line Matching for Old vs New regime deductions, and Sandbox PAN verification. On the backend, it is built with Java 17, Spring Boot 3.2.1, strict Spring Security 6 JWT authentication, token blacklisting on logout, PostgreSQL persistence, and Docker/Kubernetes containerization."', script_speech_style))
    elements.append(Spacer(1, 2))

    # Minute 5
    elements.append(Paragraph("⏱️ Minute 5 [4:15 – 5:00] — Summary & Conclusion", h2_style))
    elements.append(Paragraph("🖥️ <b>Screen Action:</b> Show README.md on GitHub, clean commit history, and wrap up.", script_cue_style))
    elements.append(Paragraph('"To summarize: FinanceFlow meets and exceeds Track 04’s bar by closing the finance-ops loop with high throughput, measured verification accuracy, conversational settlement intelligence, and honest exception handling. The complete source code and documentation are available on my GitHub repository. Thank you for your time, and I look forward to discussing this in the next round!"', script_speech_style))
    elements.append(Spacer(1, 6))

    # SECTION 3: TOP TECHNICAL INTERVIEW Q&A
    elements.append(Paragraph("3. 🧠 Top Interview Questions & How to Answer Them", h1_style))
    
    qa_list = [
        ("Q1: How does your reconciliation engine handle timing and fee discrepancies?",
         "A: We implement a 2-pass heuristic matcher: Pass 1 does strict 3-way hash matching (Transaction ID + exact amount). Pass 2 applies a tolerance delta for gateway processing fees (e.g. 2% MDR) and settlement date windows (+/- 48 hours). Records failing both passes are explicitly classified into the Honest Exception List with root-cause tags."),
        ("Q2: Why did you prioritize measured accuracy and exception classification over simple matching?",
         "A: In real enterprise finance, blindly marking 100% matches creates severe compliance and audit risks. An honest exception list with categorized discrepancies (AMOUNT_MISMATCH, UNPOSTED_ERP, GATEWAY_TIMING) provides genuine auditability for finance controllers."),
        ("Q3: How is the backend secured?",
         "A: The backend runs Spring Boot 3.2.1 with Spring Security 6. All protected routes require a cryptographically signed HMAC-SHA256 JWT Bearer token. On logout, active tokens are stored in a thread-safe token blacklist to prevent replay attacks."),
        ("Q4: What is the role of the Settlement Q&A Agent?",
         "A: It aggregates live ledger states into a structured context window, enabling non-technical stakeholders to ask ad-hoc questions on ledger health, settlement delays, and cash runway.")
    ]

    for q, a in qa_list:
        elements.append(Paragraph(f"<b>{q}</b>", feature_title_style))
        elements.append(Paragraph(a, body_style))
        elements.append(Spacer(1, 2))

    # Build PDF
    doc.build(elements)
    print(f"PDF successfully updated: {pdf_path}")

if __name__ == "__main__":
    generate_pdf()
