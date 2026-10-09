@echo off
rem Compila e executa o Tetris (requer JDK 17+; o Maven e baixado pelo wrapper)
cd /d "%~dp0"
call "%~dp0mvnw.cmd" -q compile exec:java
