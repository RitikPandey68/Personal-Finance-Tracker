@echo off
title FinanceFlow Frontend Dashboard (Port 5500)
cd /d "%~dp0frontend"

echo =========================================================
echo Starting FinanceFlow Frontend on http://localhost:5500...
echo =========================================================

"C:\Users\pande\AppData\Local\Programs\Python\Python313\python.exe" -m http.server 5500
pause
