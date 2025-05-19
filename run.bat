@echo off
setlocal enabledelayedexpansion

:: START
echo '=== SCRIPT START ==='

:: Set project directory
set PROJECT_DIR="%cd%"

:: Navigate to project directory
cd /d "%PROJECT_DIR%"

:: Clean and build project
echo Cleaning project...
call rd /s /q \target 2> null
call mvnw clean
echo Building project...
call mvnw package

:: Run project
echo Starting application...
call mvnw spring-boot:run

:: Handle errors
if %errorlevel% neq 0 (
    echo Error: Failed to start application
    pause
    exit /b 1
)

echo Application started successfully!
echo '=== SCRIPT END ==='
pause