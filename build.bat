@echo off
echo 🔨 Building Formation Management System...
call mvn clean install -DskipTests
if %errorlevel% equ 0 (
    echo ✅ Build completed successfully
    echo.
    echo 🚀 To run: run.bat
    echo 🌐 REST API: http://localhost:8080/formation/api/formations
    echo 🌐 H2 Console: http://localhost:8080/formation/h2-console
) else (
    echo ❌ Build failed
)
pause
