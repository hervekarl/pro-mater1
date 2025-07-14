#!/bin/bash

# Couleurs pour affichage
GREEN="\e[32m"
RESET="\e[0m"

# Liste des microservices
services=("mygateway" "PatientManager" "dosmed" "RHManager" "PharmacyManager" "Authentification" "uploader" "Diagnotic")

# Étape 2 : Démarrage des microservices métiers
echo -e "${GREEN}🚀 Démarrage des microservices métiers...${RESET}"

for service in "${services[@]}"; do
  echo -e "${GREEN}▶️  Lancement de $service...${RESET}"
  cd "$service" || { echo "❌ Dossier $service introuvable."; exit 1; }
  nohup mvn spring-boot:run > "../${service}.log" 2>&1 &
  cd ..
done

echo -e "${GREEN}✅ Tous les services sont lancés. Vérifiez les logs dans les fichiers *.log${RESET}"
