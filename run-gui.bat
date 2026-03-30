@echo off
REM University Management Portal - Quick Run GUI
echo Starting University Management Portal (GUI)...
set DB_PASSWORD=yuva12@
java -cp "bin;lib/mysql-connector-j-8.3.0.jar" com.university.UiMain
