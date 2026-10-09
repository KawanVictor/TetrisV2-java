@echo off
rem Compila e executa o Tetris (requer JDK 17+ no PATH)
cd /d "%~dp0"
if not exist bin mkdir bin
dir /s /b src\*.java > bin\sources.txt
javac -encoding UTF-8 -d bin -cp "lib\*" @bin\sources.txt
if errorlevel 1 exit /b 1
java -cp "bin;lib\*" Main
