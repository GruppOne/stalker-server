# TODO handle unclean git status

if (-not (Test-Path ".versionrc.json")) {
  throw "you need to be in the repository root folder to run this script"
}

if (($args -join "  ") -notmatch "--release-as") {
  throw "you should declare an explicit target version with the --release-as parameter"
}

# TODO convert both scripts to a module...

Write-Output "Preparing current release..."

npx standard-version --skip.tag $args

if ($LASTEXITCODE) {
  Write-Error "Something wrong with standard-version"
}
else {
  $tagRelease = Join-Path $PSScriptRoot "tag-current-ps1"
  Write-Output "when ready, run $tagRelease"
}
