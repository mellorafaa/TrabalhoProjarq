#!/usr/bin/env bash
# scale-up.sh
# Sobe o ambiente Pizzaria com multiplas instancias dos microsservicos.
set -e

PIZZARIA="${PIZZARIA:-2}"
ESTOQUE="${ESTOQUE:-2}"
ENTREGAS="${ENTREGAS:-3}"
REBUILD="${REBUILD:-0}"

echo "==> Subindo ambiente Pizzaria com:"
echo "    pizzaria-service = $PIZZARIA instancias"
echo "    ms-estoque       = $ESTOQUE instancias"
echo "    ms-entregas      = $ENTREGAS instancias (>= 3 por requisito do enunciado)"
echo ""

if [ "$REBUILD" = "1" ]; then
    echo "==> Rebuild forcado das imagens..."
    docker compose build
fi

docker compose up -d \
    --scale pizzaria-service="$PIZZARIA" \
    --scale ms-estoque="$ESTOQUE" \
    --scale ms-entregas="$ENTREGAS"

echo ""
echo "==> Ambiente no ar. Pontos de acesso:"
echo "    Eureka Dashboard : http://localhost:8761"
echo "    API Gateway      : http://localhost:8080"
echo "    RabbitMQ UI      : http://localhost:15672 (guest/guest)"
echo ""
docker compose ps
