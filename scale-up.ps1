# scale-up.ps1
# Sobe o ambiente Pizzaria com multiplas instancias dos microsservicos.
#
# Numero de instancias por microsservico (parametros opcionais).
param(
    [int]$Pizzaria = 2,
    [int]$Estoque  = 2,
    [int]$Entregas = 3,
    [switch]$Rebuild
)

Write-Host "==> Subindo ambiente Pizzaria com:" -ForegroundColor Cyan
Write-Host "    pizzaria-service = $Pizzaria instancias" -ForegroundColor Yellow
Write-Host "    ms-estoque       = $Estoque instancias" -ForegroundColor Yellow
Write-Host "    ms-entregas      = $Entregas instancias (>= 3 por requisito do enunciado)" -ForegroundColor Yellow
Write-Host ""

if ($Rebuild) {
    Write-Host "==> Rebuild forcado das imagens..." -ForegroundColor Cyan
    docker compose build
    if ($LASTEXITCODE -ne 0) { exit $LASTEXITCODE }
}

docker compose up -d `
    --scale pizzaria-service=$Pizzaria `
    --scale ms-estoque=$Estoque `
    --scale ms-entregas=$Entregas

if ($LASTEXITCODE -ne 0) {
    Write-Host "==> Falha ao subir ambiente." -ForegroundColor Red
    exit $LASTEXITCODE
}

Write-Host ""
Write-Host "==> Ambiente no ar. Use os seguintes pontos de acesso:" -ForegroundColor Green
Write-Host "    Eureka Dashboard : http://localhost:8761"
Write-Host "    API Gateway      : http://localhost:8080"
Write-Host "    RabbitMQ UI      : http://localhost:15672 (guest/guest)"
Write-Host ""
Write-Host "==> Containers ativos:" -ForegroundColor Cyan
docker compose ps
