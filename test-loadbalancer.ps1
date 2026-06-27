# test-loadbalancer.ps1
# Faz N requisicoes para um endpoint /instancia atraves do gateway e
# mostra a distribuicao de respostas por hostname, validando que o
# load balancer esta funcionando.
param(
    [string]$Endpoint = "http://localhost:8080/instancia",
    [int]$Requisicoes = 12
)

Write-Host "==> Disparando $Requisicoes requisicoes contra: $Endpoint" -ForegroundColor Cyan
Write-Host ""

$contagem = @{}

for ($i = 1; $i -le $Requisicoes; $i++) {
    try {
        $resp = Invoke-RestMethod -Uri $Endpoint -Method Get -TimeoutSec 10
        $hostname = $resp.hostname
        $servico = $resp.servico
        Write-Host ("[{0:D3}] {1,-22} host={2}" -f $i, $servico, $hostname)
        if ($contagem.ContainsKey($hostname)) {
            $contagem[$hostname] = $contagem[$hostname] + 1
        } else {
            $contagem[$hostname] = 1
        }
    } catch {
        Write-Host ("[{0:D3}] ERRO: {1}" -f $i, $_.Exception.Message) -ForegroundColor Red
    }
    Start-Sleep -Milliseconds 150
}

Write-Host ""
Write-Host "==> Distribuicao de respostas por instancia:" -ForegroundColor Green
$contagem.GetEnumerator() | Sort-Object Value -Descending | ForEach-Object {
    Write-Host ("    {0,-30}  {1,4} req" -f $_.Key, $_.Value)
}

if ($contagem.Count -gt 1) {
    Write-Host ""
    Write-Host "==> Load balancer OK ($($contagem.Count) instancias atenderam)." -ForegroundColor Green
} else {
    Write-Host ""
    Write-Host "==> Apenas 1 instancia atendeu. Verifique se ha replicas suficientes." -ForegroundColor Yellow
}
