$ErrorActionPreference = 'Stop'
$RepositoryUrl = 'https://github.com/NAVEENKUMAR-T-GIT-28/Java-capstone-project.git'
$TargetBranch = 'Java-capstone-project'

if (-not (Get-Command git -ErrorAction SilentlyContinue)) {
  throw 'Git is not installed. Install Git for Windows, then rerun this script.'
}
if (-not (Test-Path '.git')) {
  git init -b $TargetBranch
  if ($LASTEXITCODE -ne 0) { throw 'git init failed.' }
} else {
  $current = (git branch --show-current).Trim()
  if ($current -ne $TargetBranch) {
    git switch -c $TargetBranch
    if ($LASTEXITCODE -ne 0) { throw "Could not create/switch to $TargetBranch. Check whether the branch already exists." }
  }
}
$remotes = git remote
if ($remotes -contains 'origin') {
  git remote set-url origin $RepositoryUrl
} else {
  git remote add origin $RepositoryUrl
}
git add -A
if ($LASTEXITCODE -ne 0) { throw 'git add failed.' }
git diff --cached --quiet
if ($LASTEXITCODE -eq 1) {
  git commit -m 'Implement Smart Clinic Management System capstone rubric'
  if ($LASTEXITCODE -ne 0) { throw 'git commit failed. Configure git user.name and user.email, then rerun.' }
} elseif ($LASTEXITCODE -ne 0) {
  throw 'Could not check staged changes.'
} else {
  Write-Host 'No new staged changes to commit.'
}
git push -u origin $TargetBranch
if ($LASTEXITCODE -ne 0) { throw 'Push failed. Authenticate to GitHub and retry; this script does not force-push or push main.' }
Write-Host "Published branch $TargetBranch to $RepositoryUrl"
