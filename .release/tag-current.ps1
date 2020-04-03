if (($args -match " -h") -or ($args -match " --help")) {
  npx standard-version -h
  return
}

Write-Output "Tagging current release..."

npx standard-version --config "$PSScriptRoot/.versionrc.json" --skip.bump --skip.changelog --skip.commit $args

if ($LASTEXITCODE) {
  Write-Error "Something wrong with standard-version"
}
else {
  Write-Output "All good! you can push the generated tag."
}
