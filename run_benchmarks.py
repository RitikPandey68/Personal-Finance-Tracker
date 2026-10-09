"""
FinanceFlow & Payment Workflow Performance Benchmark Runner
Executes comprehensive, high-resolution micro-benchmarks and end-to-end API benchmarks,
recording raw timings, throughput, percentiles (p50, p90, p95, p99), speedup calculations,
and dumps performance_benchmark_results.json and performance_benchmark_run.log.
"""

import urllib.request
import urllib.error
import json
import time
import statistics
import os
import sys
import platform

BASE_URL = "http://localhost:8002"
OUTPUT_JSON = "performance_benchmark_results.json"
OUTPUT_LOG = "performance_benchmark_run.log"

if sys.stdout.encoding != 'utf-8':
    try:
        sys.stdout.reconfigure(encoding='utf-8')
        sys.stderr.reconfigure(encoding='utf-8')
    except Exception:
        pass

def log(msg, file_handle=None):
    try:
        print(msg)
    except Exception:
        try:
            print(msg.encode('ascii', 'replace').decode('ascii'))
        except Exception:
            pass
    if file_handle:
        try:
            file_handle.write(msg + "\n")
            file_handle.flush()
        except Exception:
            pass

def make_request(url, method="GET", payload=None, token=None):
    headers = {"Content-Type": "application/json"}
    if token:
        headers["Authorization"] = f"Bearer {token}"
    data = json.dumps(payload).encode("utf-8") if payload is not None else None
    req = urllib.request.Request(url, data=data, headers=headers, method=method)
    
    start_ns = time.perf_counter_ns()
    try:
        with urllib.request.urlopen(req, timeout=15) as resp:
            content = resp.read().decode("utf-8")
            elapsed_ms = (time.perf_counter_ns() - start_ns) / 1_000_000.0
            return {
                "statusCode": resp.status,
                "elapsedMs": elapsed_ms,
                "data": json.loads(content) if content else {},
                "error": None
            }
    except urllib.error.HTTPError as e:
        elapsed_ms = (time.perf_counter_ns() - start_ns) / 1_000_000.0
        err_msg = e.read().decode("utf-8") if e.fp else str(e)
        return {
            "statusCode": e.code,
            "elapsedMs": elapsed_ms,
            "data": None,
            "error": err_msg
        }
    except Exception as e:
        elapsed_ms = (time.perf_counter_ns() - start_ns) / 1_000_000.0
        return {
            "statusCode": 0,
            "elapsedMs": elapsed_ms,
            "data": None,
            "error": str(e)
        }

def compute_stats(samples):
    if not samples:
        return {
            "count": 0,
            "minMs": 0.0,
            "maxMs": 0.0,
            "avgMs": 0.0,
            "medianMs": 0.0,
            "p90Ms": 0.0,
            "p95Ms": 0.0,
            "p99Ms": 0.0,
            "stdDevMs": 0.0
        }
    s = sorted(samples)
    n = len(s)
    p50_idx = int(n * 0.50)
    p90_idx = min(int(n * 0.90), n - 1)
    p95_idx = min(int(n * 0.95), n - 1)
    p99_idx = min(int(n * 0.99), n - 1)
    
    return {
        "count": n,
        "minMs": round(s[0], 2),
        "maxMs": round(s[-1], 2),
        "avgMs": round(statistics.mean(s), 2),
        "medianMs": round(s[p50_idx], 2),
        "p90Ms": round(s[p90_idx], 2),
        "p95Ms": round(s[p95_idx], 2),
        "p99Ms": round(s[p99_idx], 2),
        "stdDevMs": round(statistics.stdev(s) if n > 1 else 0.0, 2)
    }

def main():
    log_file = open(OUTPUT_LOG, "w", encoding="utf-8")
    log("=" * 80, log_file)
    log(">> FINANCEFLOW LIVE PERFORMANCE BENCHMARK HARNESS", log_file)
    log(f"Target Base URL: {BASE_URL}", log_file)
    log(f"Timestamp: {time.strftime('%Y-%m-%d %H:%M:%S')}", log_file)
    log(f"OS Platform: {platform.system()} {platform.release()} ({platform.machine()})", log_file)
    log(f"Python Version: {platform.python_version()}", log_file)
    log("=" * 80, log_file)

    benchmark_output = {
        "metadata": {
            "timestamp": time.strftime('%Y-%m-%dT%H:%M:%S%z'),
            "targetServer": BASE_URL,
            "platform": f"{platform.system()} {platform.release()}",
            "arch": platform.machine(),
            "pythonVersion": platform.python_version()
        },
        "benchmarks": {}
    }

    # Step 1: Authentication & JWT Acquisition
    log("\n[1/5] Authenticating & Measuring Login Latency...", log_file)
    login_times = []
    auth_token = None
    
    for i in range(10):
        res = make_request(
            f"{BASE_URL}/api/auth/login",
            method="POST",
            payload={"email": "ritik@financeflow.com", "password": "SecurePass123!"}
        )
        if res["statusCode"] == 200:
            login_times.append(res["elapsedMs"])
            if not auth_token:
                auth_token = res["data"].get("token")
        else:
            log(f"  Login iteration {i+1} failed: {res['statusCode']} - {res['error']}", log_file)
    
    if not auth_token:
        log("FATAL: Could not obtain JWT token. Exiting.", log_file)
        sys.exit(1)
        
    auth_stats = compute_stats(login_times)
    benchmark_output["benchmarks"]["authLogin"] = {
        "description": "User Login (BCrypt Cost Factor 10 Verification + JWT Generation)",
        "stats": auth_stats,
        "firstRequestColdMs": round(login_times[0], 2),
        "warmupAvgMs": round(statistics.mean(login_times[1:]), 2) if len(login_times) > 1 else auth_stats["avgMs"],
        "rawSamples": [round(x, 2) for x in login_times]
    }
    log(f"  [OK] Login Avg: {auth_stats['avgMs']} ms (Min: {auth_stats['minMs']} ms, Max: {auth_stats['maxMs']} ms, p95: {auth_stats['p95Ms']} ms)", log_file)
    log(f"  [OK] JWT Token acquired successfully", log_file)

    # Step 2: SmartMerchant Micro-Benchmark
    log("\n[2/5] Running SmartMerchant Card Switcher Micro-Benchmark (500 iterations)...", log_file)
    merchants = [
        ("Swiggy", 850.0),
        ("Zomato", 420.0),
        ("Amazon", 3499.0),
        ("Flipkart", 1899.0),
        ("Uber", 350.0),
        ("MakeMyTrip", 7800.0),
        ("Blinkit", 620.0),
        ("Starbucks", 450.0),
        ("BookMyShow", 900.0),
        ("Croma", 15400.0)
    ]
    
    # Warmup
    for m, amt in merchants[:5]:
        make_request(f"{BASE_URL}/api/fintech/card-switcher/recommend", "POST", {"merchant": m, "amount": amt}, auth_token)

    smart_merchant_times = []
    smart_merchant_records = []
    start_bench_time = time.perf_counter()
    
    TOTAL_SMART_ITERATIONS = 500
    for i in range(TOTAL_SMART_ITERATIONS):
        m, amt = merchants[i % len(merchants)]
        res = make_request(
            f"{BASE_URL}/api/fintech/card-switcher/recommend",
            method="POST",
            payload={"merchant": m, "amount": amt},
            token=auth_token
        )
        if res["statusCode"] == 200:
            smart_merchant_times.append(res["elapsedMs"])
            if i < 5:
                smart_merchant_records.append(res["data"])
    
    total_smart_duration = time.perf_counter() - start_bench_time
    smart_stats = compute_stats(smart_merchant_times)
    smart_throughput = round(len(smart_merchant_times) / total_smart_duration, 2)
    
    benchmark_output["benchmarks"]["smartMerchantMicroBenchmark"] = {
        "description": "SmartMerchant Cashback/Reward Rule Engine & Categorization",
        "totalIterations": len(smart_merchant_times),
        "totalDurationSeconds": round(total_smart_duration, 3),
        "throughputRequestsPerSecond": smart_throughput,
        "stats": smart_stats,
        "sampleOutputs": smart_merchant_records
    }
    log(f"  [OK] SmartMerchant Completed: {len(smart_merchant_times)} iterations in {total_smart_duration:.2f}s", log_file)
    log(f"  [OK] Throughput: {smart_throughput} requests/sec", log_file)
    log(f"  [OK] Latency: Avg={smart_stats['avgMs']} ms, p50={smart_stats['medianMs']} ms, p90={smart_stats['p90Ms']} ms, p99={smart_stats['p99Ms']} ms", log_file)

    # Step 3: Payment Workflow & Write Performance: Sequential vs Bulk Speedup
    log("\n[3/5] Benchmarking Payment Workflow & Transaction Engine Write Performance...", log_file)
    TX_COUNT = 30
    
    # Test A: Sequential Individual Transaction Writes
    log(f"  -> Testing Sequential Payment Workflow ({TX_COUNT} individual POST /api/transactions)...", log_file)
    seq_times = []
    seq_start = time.perf_counter()
    for i in range(TX_COUNT):
        tx_body = {
            "user": {"id": 1},
            "type": "EXPENSE",
            "amount": 250.0 + (i * 10),
            "description": f"Benchmark Tx Sequential #{i+1}",
            "category": "Food & Dining",
            "account": "HDFC Salary Account",
            "transactionDate": "2026-08-25",
            "recurring": "NONE",
            "currency": "INR"
        }
        res = make_request(f"{BASE_URL}/api/transactions", method="POST", payload=tx_body, token=auth_token)
        if res["statusCode"] in (200, 201):
            seq_times.append(res["elapsedMs"])
        else:
            log(f"     Sequential write {i+1} failed: {res['statusCode']} - {res['error']}", log_file)
            
    seq_total_duration_ms = (time.perf_counter() - seq_start) * 1000.0
    seq_stats = compute_stats(seq_times)
    seq_throughput = round(len(seq_times) / (seq_total_duration_ms / 1000.0), 2) if seq_total_duration_ms > 0 else 0
    log(f"     Sequential Total Time: {seq_total_duration_ms:.2f} ms | Avg per tx: {seq_stats['avgMs']} ms | Throughput: {seq_throughput} tx/s", log_file)

    # Test B: Bulk / Batch Payment Ingestion
    log(f"  -> Testing Batch / Bulk Payment Ingestion ({TX_COUNT} tx in 1 batch POST /api/transactions/bulk)...", log_file)
    bulk_payload = [
        {
            "user": {"id": 1},
            "type": "EXPENSE",
            "amount": 300.0 + (i * 15),
            "description": f"Benchmark Tx Bulk #{i+1}",
            "category": "Shopping",
            "account": "ICICI Credit Card",
            "transactionDate": "2026-08-25",
            "recurring": "NONE",
            "currency": "INR"
        }
        for i in range(TX_COUNT)
    ]
    bulk_samples = []
    for round_idx in range(5):
        bulk_res = make_request(f"{BASE_URL}/api/transactions/bulk", method="POST", payload=bulk_payload, token=auth_token)
        if bulk_res["statusCode"] in (200, 201):
            bulk_samples.append(bulk_res["elapsedMs"])
        else:
            log(f"     Bulk batch round {round_idx+1} failed: {bulk_res['statusCode']} - {bulk_res['error']}", log_file)
            
    bulk_stats = compute_stats(bulk_samples)
    bulk_avg_total_ms = bulk_stats["avgMs"] if bulk_stats["avgMs"] > 0 else 1.0
    bulk_effective_per_tx_ms = round(bulk_avg_total_ms / TX_COUNT, 3)
    bulk_throughput = round(TX_COUNT / (bulk_avg_total_ms / 1000.0), 2)
    log(f"     Bulk Batch Total Time: {bulk_avg_total_ms:.2f} ms | Effective per tx: {bulk_effective_per_tx_ms} ms | Throughput: {bulk_throughput} tx/s", log_file)

    # Calculate Speedup
    speedup_ratio = round(seq_total_duration_ms / bulk_avg_total_ms, 2)
    latency_reduction_pct = round(((seq_stats['avgMs'] - bulk_effective_per_tx_ms) / max(seq_stats['avgMs'], 0.001)) * 100.0, 1)
    throughput_increase_pct = round(((bulk_throughput - seq_throughput) / max(seq_throughput, 0.001)) * 100.0, 1)

    log(f"  [OK] Payment Workflow Speedup Ratio: {speedup_ratio}x faster", log_file)
    log(f"  [OK] Per-Transaction Latency Reduction: {latency_reduction_pct}%", log_file)
    log(f"  [OK] Throughput Increase: +{throughput_increase_pct}%", log_file)

    benchmark_output["benchmarks"]["paymentWorkflow"] = {
        "description": "Payment & Transaction Ingestion Engine Performance Comparison",
        "sampleSize": TX_COUNT,
        "sequentialProcessing": {
            "totalDurationMs": round(seq_total_duration_ms, 2),
            "stats": seq_stats,
            "throughputTxPerSec": seq_throughput
        },
        "bulkBatchProcessing": {
            "totalBatchDurationMs": bulk_stats["avgMs"],
            "effectivePerTxMs": bulk_effective_per_tx_ms,
            "stats": bulk_stats,
            "throughputTxPerSec": bulk_throughput
        },
        "performanceGain": {
            "speedupMultiplier": f"{speedup_ratio}x",
            "latencyReductionPercentage": f"{latency_reduction_pct}%",
            "throughputIncreasePercentage": f"+{throughput_increase_pct}%"
        }
    }

    # Step 4: Core API Response Time Latency Suite
    log("\n[4/5] Measuring Core Endpoints Latency (25 repeated iterations each)...", log_file)
    endpoints = [
        {"name": "Auth Me (Session)", "url": f"{BASE_URL}/api/auth/me", "method": "GET"},
        {"name": "Budgets (Category Allocation)", "url": f"{BASE_URL}/api/budgets", "method": "GET"},
        {"name": "Investments (Portfolio Assets)", "url": f"{BASE_URL}/api/investments", "method": "GET"},
        {"name": "Goals (Wealth Targets)", "url": f"{BASE_URL}/api/goals", "method": "GET"},
        {"name": "Bills (Reminders)", "url": f"{BASE_URL}/api/bills", "method": "GET"},
        {"name": "Tax Summary (Deductions)", "url": f"{BASE_URL}/api/tax/summary", "method": "GET"},
        {"name": "Transactions Summary (KPI Aggregates)", "url": f"{BASE_URL}/api/transactions/summary", "method": "GET"},
        {"name": "Expenses By Category", "url": f"{BASE_URL}/api/transactions/by-category", "method": "GET"},
        {"name": "Transaction Search (Indexed query)", "url": f"{BASE_URL}/api/transactions/search?q=Swiggy", "method": "GET"},
        {"name": "Anomaly Detector (Scan)", "url": f"{BASE_URL}/api/fintech/anomaly/scan", "method": "GET"},
        {"name": "General Ledger T-Accounts", "url": f"{BASE_URL}/api/fintech/ledger/t-accounts", "method": "GET"},
        {"name": "Reconciliation Summary", "url": f"{BASE_URL}/api/finance-ops/reconciliation/summary", "method": "GET"},
        {"name": "Cash Forecast", "url": f"{BASE_URL}/api/finance-ops/cash-forecast", "method": "GET"},
    ]

    api_results = {}
    ITERATIONS_PER_ENDPOINT = 25
    for ep in endpoints:
        times = []
        for _ in range(ITERATIONS_PER_ENDPOINT):
            res = make_request(ep["url"], method=ep["method"], token=auth_token)
            if res["statusCode"] == 200:
                times.append(res["elapsedMs"])
        stats = compute_stats(times)
        api_results[ep["name"]] = {
            "url": ep["url"].replace(BASE_URL, ""),
            "method": ep["method"],
            "stats": stats
        }
        log(f"  [OK] {ep['name']:<35} | Avg: {stats['avgMs']:>5.1f} ms | p50: {stats['medianMs']:>5.1f} ms | p95: {stats['p95Ms']:>5.1f} ms | Min/Max: {stats['minMs']:.1f}/{stats['maxMs']:.1f} ms", log_file)

    benchmark_output["benchmarks"]["coreApiEndpoints"] = api_results

    # Step 5: Save JSON & Conclude
    log("\n[5/5] Writing Raw Results to Artifacts...", log_file)
    with open(OUTPUT_JSON, "w", encoding="utf-8") as f:
        json.dump(benchmark_output, f, indent=2)
    log(f"  [OK] Successfully generated: {OUTPUT_JSON}", log_file)
    log(f"  [OK] Successfully written log: {OUTPUT_LOG}", log_file)

    log("\n" + "=" * 80, log_file)
    log(">> BENCHMARK RUN COMPLETED SUCCESSFULLY", log_file)
    log("=" * 80, log_file)
    log_file.close()

if __name__ == "__main__":
    main()
