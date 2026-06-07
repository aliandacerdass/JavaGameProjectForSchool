#!/bin/bash
# Script'in bulundugu dizine git (Finder'dan tiklandiginda dogru calismasi icin)
cd "$(dirname "$0")"

clear
echo "============================================="
echo "       slimeSlayer Derleniyor ve Baslatiliyor "
echo "============================================="
echo ""

# Derleme Islemi
javac -d bin -sourcepath src src/AnaGiris.java

# Derleme basariliysa oyunu calistir
if [ $? -eq 0 ]; then
    echo "[OK] Derleme basarili. Oyun baslatiliyor..."
    java -cp bin AnaGiris
else
    echo "[HATA] Derleme sirasinda bir sorun olustu!"
    echo "Terminali kapatmak icin herhangi bir tusa basin..."
    read -n 1
fi
