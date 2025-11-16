@echo off
title GPA Calculator Launcher
echo ========================================
echo    GPA Calculator - Starting...
echo ========================================
echo.

REM Run the JavaFX application using Maven
call mvnw.cmd javafx:run

REM Keep window open if there's an error
if errorlevel 1 (
    echo.
    echo ========================================
    echo    Error occurred while running app
    echo ========================================
    pause
)
