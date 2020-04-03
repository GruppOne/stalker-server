if (($args -match " -h") -or ($args -match " --help")) {
  npx standard-version -h
  return
}

Write-Output "Preparing current release..."

npx standard-version --config "$PSScriptRoot/.versionrc.json" --skip.tag $args

if ($LASTEXITCODE) {
  Write-Error "Something wrong with standard-version"
}
else {
  $tagRelease = Join-Path $PSScriptRoot "tag-current-ps1"
  Write-Output "when ready, run $tagRelease"
}
