#!/bin/bash
echo "🛑 Arrêt des services Spring Boot..."
pkill -f 'spring-boot:run'
echo "✅ Tous les services ont été arrêtés."
