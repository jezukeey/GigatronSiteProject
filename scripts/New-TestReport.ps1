param(
    [string]$ReportsDirectory = (Join-Path $PSScriptRoot '..\target\surefire-reports'),
    [string]$OutputFile = (Join-Path $PSScriptRoot '..\target\test-report.html')
)

$ErrorActionPreference = 'Stop'

# Koristimo samo postojeće JUnit XML rezultate; skripta nikada ne pokreće testove.
$reports = @(Get-ChildItem -LiteralPath $ReportsDirectory -Filter 'TEST-*.xml' -File)
if ($reports.Count -eq 0) { throw 'Nema JUnit XML rezultata. Prvo pokreni Maven testove.' }

$rows = New-Object System.Collections.Generic.List[string]
$total = 0
$failures = 0
$errors = 0
$skipped = 0
foreach ($report in $reports) {
    # XML reader ne dozvoljava spoljne entitete ili učitavanje udaljenih resursa.
    $settings = New-Object System.Xml.XmlReaderSettings
    $settings.DtdProcessing = [System.Xml.DtdProcessing]::Prohibit
    $settings.XmlResolver = $null
    $reader = [System.Xml.XmlReader]::Create($report.FullName, $settings)
    try {
        $xml = New-Object System.Xml.XmlDocument
        $xml.XmlResolver = $null
        $xml.Load($reader)
    } finally { $reader.Dispose() }
    $suite = $xml.DocumentElement
    $total += [int]$suite.GetAttribute('tests')
    $failures += [int]$suite.GetAttribute('failures')
    $errors += [int]$suite.GetAttribute('errors')
    $skipped += [int]$suite.GetAttribute('skipped')
    foreach ($case in $suite.SelectNodes('testcase')) {
        # Rezultat određujemo iz XML čvorova; preskočen test nije prolaz.
        $status = 'PASS'
        if ($case.SelectSingleNode('failure')) { $status = 'FAIL' }
        elseif ($case.SelectSingleNode('error')) { $status = 'ERROR' }
        elseif ($case.SelectSingleNode('skipped')) { $status = 'SKIPPED' }
        # HTML kodiramo i prikazujemo samo nazive i vremena, bez logova ili podataka naloga.
        $class = [System.Net.WebUtility]::HtmlEncode($case.GetAttribute('classname'))
        $name = [System.Net.WebUtility]::HtmlEncode($case.GetAttribute('name'))
        $time = [System.Net.WebUtility]::HtmlEncode($case.GetAttribute('time'))
        $rows.Add("<tr><td>$class</td><td>$name</td><td class='$status'>$status</td><td>$time s</td></tr>")
    }
}
$passed = $total - $failures - $errors - $skipped
$date = [System.Net.WebUtility]::HtmlEncode((Get-Date).ToString('yyyy-MM-dd HH:mm:ss zzz'))
$body = $rows -join "`n"
$html = @"
<!doctype html>
<html lang='sr'><meta charset='utf-8'><title>Gigatron — rezultati testova</title>
<style>
body{font:16px system-ui,sans-serif;max-width:1200px;margin:40px auto;padding:0 24px;background:#f6f7f9;color:#20252d}
h1{border-left:7px solid #ffcf00;padding-left:16px}table{width:100%;border-collapse:collapse;background:white}
th,td{padding:12px;text-align:left;border-bottom:1px solid #e1e4e8}th{background:#20252d;color:white}
.PASS{color:#18713e;font-weight:bold}.FAIL,.ERROR{color:#b52323;font-weight:bold}.SKIPPED{color:#946400}
.summary{background:white;padding:20px;margin:24px 0;border-radius:8px}p{line-height:1.6}
</style>
<h1>Gigatron: rezultati testova</h1>
<p>Izveštaj napravljen: $date. Izvor: lokalni Maven JUnit XML rezultati.</p>
<div class='summary'>Ukupno: <b>$total</b> · Prošlo: <b>$passed</b> · Failures: <b>$failures</b> · Errors: <b>$errors</b> · Skipped: <b>$skipped</b></div>
<p>Izveštaj obuhvata samo izvršene testove. Ručni checkout i WSPay nisu automatski potvrđeni ovim izveštajem.
Pre novog skupa koristiti Maven clean da se rezultati različitih pokretanja ne pomešaju.</p>
<table><thead><tr><th>Klasa</th><th>Scenario</th><th>Rezultat</th><th>Trajanje</th></tr></thead><tbody>$body</tbody></table>
</html>
"@
# Izveštaj ostaje u target direktorijumu, koji Git ignoriše.
$resolvedOutput = [System.IO.Path]::GetFullPath($OutputFile)
[System.IO.Directory]::CreateDirectory([System.IO.Path]::GetDirectoryName($resolvedOutput)) | Out-Null
[System.IO.File]::WriteAllText($resolvedOutput, $html, (New-Object System.Text.UTF8Encoding($false)))
Write-Output "Izveštaj: $resolvedOutput"
