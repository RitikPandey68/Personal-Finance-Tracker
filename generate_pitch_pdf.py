import os
from reportlab.lib.pagesizes import letter
from reportlab.lib import colors
from reportlab.lib.styles import getSampleStyleSheet, ParagraphStyle
from reportlab.platypus import SimpleDocTemplate, Paragraph, Spacer, Table, TableStyle, PageBreak, KeepTogether, HRFlowable

def generate_pdf():
    pdf_path = os.path.abspath("AI_Finance_Controller_Presentation_Guide.pdf")
    doc = SimpleDocTemplate(
        pdf_path,
        pagesize=letter,
        rightMargin=40,
        leftMargin=40,
        topMargin=40,
        bottomMargin=40
    )

    styles = getSampleStyleSheet()
    
    # Custom Palette
    c_primary = colors.HexColor("#1e1e2d")
    c_accent = colors.HexColor("#6366f1")
    c_emerald = colors.HexColor("#10b981")
    c_amber = colors.HexColor("#f59e0b")
    c_dark = colors.HexColor("#0f172a")
    c_light = colors.HexColor("#f8fafc")
    c_text = colors.HexColor("#334155")
    
    # Custom Styles
    title_style = ParagraphStyle(
        'DocTitle',
        parent=styles['Heading1'],
        fontName='Helvetica-Bold',
        fontSize=20,
        leading=24,
        textColor=c_accent,
        spaceAfter=6
    )
    
    subtitle_style = ParagraphStyle(
        'DocSubTitle',
        parent=styles['Normal'],
        fontName='Helvetica-Bold',
        fontSize=12,
        leading=16,
        textColor=c_text,
        spaceAfter=12
    )

    h1_style = ParagraphStyle(
        'SectionH1',
        parent=styles['Heading2'],
        fontName='Helvetica-Bold',
        fontSize=14,
        leading=18,
        textColor=c_dark,
        spaceBefore=14,
        spaceAfter=8
    )

    h2_style = ParagraphStyle(
        'SectionH2',
        parent=styles['Heading3'],
        fontName='Helvetica-Bold',
        fontSize=11,
        leading=15,
        textColor=c_accent,
        spaceBefore=8,
        spaceAfter=4
    )

    body_style = ParagraphStyle(
        'DocBody',
        parent=styles['Normal'],
        fontName='Helvetica',
        fontSize=9.5,
        leading=14,
        textColor=c_text,
        spaceAfter=6
    )

    bullet_style = ParagraphStyle(
        'DocBullet',
        parent=styles['Normal'],
        fontName='Helvetica',
        fontSize=9,
        leading=13,
        textColor=c_text,
        leftIndent=15,
        spaceAfter=4
    )

    script_cue_style = ParagraphStyle(
        'ScriptCue',
        parent=styles['Normal'],
        fontName='Helvetica-Bold',
        fontSize=9.5,
        leading=13,
        textColor=c_amber,
        spaceAfter=3
    )

    script_speech_style = ParagraphStyle(
        'ScriptSpeech',
        parent=styles['Normal'],
        fontName='Helvetica-Oblique',
        fontSize=9.5,
        leading=14,
        textColor=c_dark,
        spaceAfter=6
    )

    callout_style = ParagraphStyle(
        'CalloutText',
        parent=styles['Normal'],
        fontName='Helvetica',
        fontSize=9,
        leading=13,
        textColor=c_dark
    )

    elements = []

    # Title & Header
    elements.append(Paragraph("🏦 FinanceFlow — AI Finance Controller", title_style))
    elements.append(Paragraph("Track 04: 'Run the books and the cash position' | Comprehensive Guide & Video Pitch", subtitle_style))
    elements.append(HRFlowable(width="100%", thickness=1.5, color=c_accent, spaceBefore=4, spaceAfter=12))

    # SECTION 1: EXECUTIVE SUMMARY
    elements.append(Paragraph("1. Executive Summary & The 2026 Core Bottleneck", h1_style))
    p1 = ("In modern financial operations, <b>verification capacity, not generation speed, is the primary bottleneck</b>. "
          "While LLMs can draft text easily, closing the actual finance-ops loop across multi-source ledgers, "
          "resolving settlement discrepancies, and forecasting cash positions requires strict accuracy, deterministic reconciliation, "
          "and an honest exception handling engine. <b>FinanceFlow</b> was built specifically to solve this.")
    elements.append(Paragraph(p1, body_style))
    elements.append(Spacer(1, 6))

    # SECTION 2: TRACK 04 REQUIREMENTS MATRIX
    elements.append(Paragraph("2. Track 04 Alignment Matrix", h1_style))
    
    table_data = [
        [
            Paragraph("<b>Recruiter Requirement (Track 04)</b>", callout_style),
            Paragraph("<b>FinanceFlow Implementation</b>", callout_style),
            Paragraph("<b>Status</b>", callout_style)
        ],
        [
            Paragraph("<b>Multi-source Reconciliation</b><br/>(50+ record synthetic batch)", body_style),
            Paragraph("Automated 3-way matching across Bank Statements, ERP Ledgers, and Payment Gateway records.", body_style),
            Paragraph("<font color='#10b981'><b>100% Ready</b></font>", body_style)
        ],
        [
            Paragraph("<b>Measured Accuracy & Exception List</b><br/>('One cherry-pick proves nothing')", body_style),
            Paragraph("Calculates real match rate (96%), throughput (tx/sec), and outputs detailed categorized exceptions (fee mismatches, unposted invoices).", body_style),
            Paragraph("<font color='#10b981'><b>100% Ready</b></font>", body_style)
        ],
        [
            Paragraph("<b>Settlement Q&A Agent</b>", body_style),
            Paragraph("Conversational AI context engine answering real-time ledger settlement and discrepancy queries.", body_style),
            Paragraph("<font color='#10b981'><b>100% Ready</b></font>", body_style)
        ],
        [
            Paragraph("<b>Forward Cash Forecaster</b>", body_style),
            Paragraph("30/60/90-day cash runway and multi-year compound wealth simulation engine.", body_style),
            Paragraph("<font color='#10b981'><b>100% Ready</b></font>", body_style)
        ],
        [
            Paragraph("<b>Tax-Line Matcher & KYC</b>", body_style),
            Paragraph("Old vs New regime tax deduction classifier + regex PAN card validation.", body_style),
            Paragraph("<font color='#10b981'><b>100% Ready</b></font>", body_style)
        ]
    ]

    t = Table(table_data, colWidths=[150, 310, 70])
    t.setStyle(TableStyle([
        ('BACKGROUND', (0, 0), (-1, 0), colors.HexColor("#e2e8f0")),
        ('GRID', (0, 0), (-1, -1), 0.5, colors.HexColor("#cbd5e1")),
        ('VALIGN', (0, 0), (-1, -1), 'TOP'),
        ('TOPPADDING', (0, 0), (-1, -1), 5),
        ('BOTTOMPADDING', (0, 0), (-1, -1), 5),
    ]))
    elements.append(t)
    elements.append(Spacer(1, 10))

    # SECTION 3: 5-MINUTE VIDEO SCRIPT
    elements.append(Paragraph("3. 🎬 5-Minute Video Demo Script (Minute-by-Minute)", h1_style))
    
    # Minute 1
    elements.append(Paragraph("⏱️ Minute 1 [0:00 – 0:45] — Hook & Problem Statement", h2_style))
    elements.append(Paragraph("🖥️ <b>Visual Cue:</b> Open Dashboard (http://localhost:5500) showing KPI Cards and GitHub Repo.", script_cue_style))
    elements.append(Paragraph('"Hello everyone, I’m Ritik Pandey, and this is my submission for Track 04: AI Finance Controller — Run the books and the cash position. In 2026, builder consensus is clear: generation speed is no longer the bottleneck—verification capacity is. Finance teams still spend countless hours manually reconciling multi-source ledgers, tracking settlement discrepancies, and forecasting forward cash positions. To solve this, I built FinanceFlow—an autonomous AI Finance Controller and Finance-Ops engine that automates multi-source reconciliation across 50+ record batches, reports measured accuracy with an honest exception list, provides a real-time Settlement Q&A Agent, and forecasts dynamic cash positions."', script_speech_style))
    elements.append(Spacer(1, 4))

    # Minute 2
    elements.append(Paragraph("⏱️ Minute 2 [0:45 – 2:00] — Multi-Source Reconciliation & Honest Exception List", h2_style))
    elements.append(Paragraph("🖥️ <b>Visual Cue:</b> Click 'Run 50+ Record Batch Auto-Reconciliation' and point out match rate and exceptions.", script_cue_style))
    elements.append(Paragraph('"Let’s dive straight into the core track requirement: Multi-Source Reconciliation. Here, the engine ingests a synthetic batch of 50+ multi-source transactions across three distinct data pipelines: Bank Statements, ERP Invoices, and Payment Gateway logs. Notice the bar we set for this engine: First, it achieves a measured match rate of 96% with automated 3-way hash matching. Second, more importantly, it does not cherry-pick. It surfaces an Honest Exception List highlighting unresolved records—such as a ₹150 gateway fee mismatch or an unposted ERP invoice. Each exception is categorized with an exact discrepancy reason so human controllers can audit or auto-resolve with one click."', script_speech_style))
    elements.append(Spacer(1, 4))

    # Minute 3
    elements.append(Paragraph("⏱️ Minute 3 [2:00 – 3:00] — Settlement Q&A Agent & Forward Cash Forecaster", h2_style))
    elements.append(Paragraph("🖥️ <b>Visual Cue:</b> Open AI Chat drawer, type settlement query, then show Cash Runway chart.", script_cue_style))
    elements.append(Paragraph('"Next is our Settlement Q&A Agent. Connecting directly to our in-memory financial context service, the AI assistant answers real-time ledger questions such as where our cash bottlenecks are or why specific payouts are delayed. Moving to the Forward Cash Forecaster: instead of relying on static spreadsheets, our forecasting model projects 30, 60, and 90-day cash runway based on recurring debt obligations, verified income streams, and dynamic burn rates, giving leadership a clear visual of their future liquidity."', script_speech_style))
    elements.append(Spacer(1, 4))

    # Minute 4
    elements.append(Paragraph("⏱️ Minute 4 [3:00 – 4:15] — Full FinTech Suite & Architecture", h2_style))
    elements.append(Paragraph("🖥️ <b>Visual Cue:</b> Show Investment Portfolio, Tax Estimator, and backend code in IDE.", script_cue_style))
    elements.append(Paragraph('"Beyond reconciliation, FinanceFlow is a comprehensive FinTech platform featuring Tax-Line Matching for automated deductions under Old vs New tax regimes, an Investment Portfolio Asset Tracker with multi-scenario win/loss recovery projections, and Sandbox PAN & Income Verification. Under the hood, the architecture is enterprise-grade: built on Java 17 and Spring Boot 3.2.1 with strict Spring Security 6 JWT authentication, token blacklisting, PostgreSQL/H2 persistence, and containerized Docker/Kubernetes manifests."', script_speech_style))
    elements.append(Spacer(1, 4))

    # Minute 5
    elements.append(Paragraph("⏱️ Minute 5 [4:15 – 5:00] — Conclusion & Wrap-Up", h2_style))
    elements.append(Paragraph("🖥️ <b>Visual Cue:</b> Show README.md on GitHub, commit log, and active app.", script_cue_style))
    elements.append(Paragraph('"To summarize: FinanceFlow meets and exceeds Track 04’s bar by closing the finance-ops loop with high throughput, measured verification accuracy, conversational settlement intelligence, and honest exception handling. The complete source code, docker-compose configuration, and documentation are available on my GitHub repository. Thank you for your time, and I look forward to discussing this in the next round!"', script_speech_style))
    elements.append(Spacer(1, 8))

    # SECTION 4: INTERVIEW CHEAT SHEET
    elements.append(Paragraph("4. 🧠 Top Technical Q&A (For Evaluators & Interviews)", h1_style))
    
    qa_list = [
        ("Q1: How does your reconciliation engine handle timing and fee discrepancies?",
         "A: It implements a multi-pass heuristic matcher: Pass 1 does strict 3-way hash matching (ID + exact amount). Pass 2 applies a configurable tolerance delta for gateway processing fees (e.g. 2% MDR) and settlement date windows (+/- 48 hours). Records failing both passes are explicitly classified into the Honest Exception List."),
        ("Q2: Why did you prioritize measured accuracy and exception classification over simple matching?",
         "A: In real-world enterprise finance, an engine that blindly marks 100% matches creates audit hazards. An honest exception list with root-cause categorization (AMOUNT_MISMATCH, UNPOSTED_ERP, GATEWAY_TIMING) gives finance controllers full trust and auditability."),
        ("Q3: How is the backend secured and architected?",
         "A: The backend runs Spring Boot 3.2.1 with Spring Security 6. All protected routes require a cryptographically signed HMAC-SHA256 JWT Bearer token with server-side token blacklisting on logout. Data access is managed via Spring Data JPA and Hibernate ORM on PostgreSQL/H2."),
        ("Q4: What is the purpose of the Settlement Q&A Agent?",
         "A: It bridges the gap between raw ledger records and human decision-makers by aggregating real-time ledger states into a conversational context window to answer ad-hoc questions on cash positions and pending settlements.")
    ]

    for q, a in qa_list:
        elements.append(Paragraph(f"<b>{q}</b>", h2_style))
        elements.append(Paragraph(a, body_style))
        elements.append(Spacer(1, 2))

    # Build PDF
    doc.build(elements)
    print(f"PDF successfully generated: {pdf_path}")

if __name__ == "__main__":
    generate_pdf()
