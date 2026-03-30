@echo off
REM Compile the project
echo Compiling Java files...
javac -cp "lib/mysql-connector-j-8.3.0.jar" -d bin src/com/university/*.java src/com/university/model/*.java src/com/university/dao/*.java src/com/university/db/*.java src/com/university/service/*.java src/com/university/ui/*.java
if %errorlevel% equ 0 (
    echo Build successful!
) else (
    echo Build failed!
)
