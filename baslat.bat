@echo off
rem Script'in bulundugu dizine git
cd /d "%~dp0"

cls
echo =============================================
echo        slimeSlayer Derleniyor ve Baslatiliyor 
echo =============================================
echo.

rem Derleme Islemi
javac -d bin -sourcepath src src/AnaGiris.java

rem Derleme basariliysa oyunu calistir
if %errorlevel% equ 0 (
    echo [OK] Derleme basarili. Oyun baslatiliyor...
    java -cp bin AnaGiris
) else (
    echo [HATA] Derleme sirasinda bir sorun olustu!
    pause
)
