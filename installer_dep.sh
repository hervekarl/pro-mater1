# Liste des microservices
configs=("myconfig" "mygateway" "myDiscorveryServer")
services=("PatientManager" "dosmed" "RHManager" "PharmacyManager" "Authentification" "Diagnotic")
all_services=("${configs[@]}" "${services[@]}")

# Étape 0 : Installation des dépendances (mvn install)
echo -e "${GREEN}📦 Installation des dépendances pour tous les services...${RESET}"
for ser in "${all_services[@]}"; do
  echo -e "${GREEN}▶️  Installation de $ser...${RESET}"
  cd "$ser" || { echo "❌ Dossier $ser introuvable."; exit 1; }
  mvn install -DskipTests
  cd ..
done
