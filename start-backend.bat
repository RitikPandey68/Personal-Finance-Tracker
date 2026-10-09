@echo off
title FinanceFlow Backend Server (Port 8002)
cd /d "%~dp0backend"

echo ===================================================
echo Starting FinanceFlow Backend Server on Port 8002...
echo ===================================================

if exist "target\finance-tracker-1.0.0.jar" (
    java -jar target\finance-tracker-1.0.0.jar
) else if exist "C:\Program Files\JetBrains\IntelliJ IDEA Community Edition 2025.2.3\jbr\bin\java.exe" (
    "C:\Program Files\JetBrains\IntelliJ IDEA Community Edition 2025.2.3\jbr\bin\java.exe" -jar target\finance-tracker-1.0.0.jar
) else (
    "C:\Program Files\JetBrains\IntelliJ IDEA Community Edition 2025.2.3\plugins\maven\lib\maven3\bin\mvn.cmd" spring-boot:run
)
pause
