# Liste des microservices
configs=("myconfig" "myDiscorveryServer")


# Étape 1 : Démarrage des services de configuration
echo -e "${GREEN}🚀 Démarrage des services de configuration...${RESET}"

for conf in "${configs[@]}"; do
  echo -e "${GREEN}▶️  Lancement de $conf...${RESET}"
  cd "$conf" || { echo "❌ Dossier $conf introuvable."; exit 1; }
  nohup mvn spring-boot:run > "../${conf}.log" 2>&1 &
  cd ..
done