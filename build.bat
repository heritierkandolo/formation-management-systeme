@echo off
echo Building Formation Management System...
call mvn -pl core clean package spring-boot:repackage
if %errorlevel% equ 0 (
    echo  Build completed successfully
    echo.
    echo To run: run.bat
) else (
    echo Build failed
)
pause
