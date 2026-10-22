New-Item -ItemType Directory -Force out | Out-Null
javac -d out (Get-ChildItem -Recurse src -Filter *.java).FullName
if ($LASTEXITCODE -eq 0) { java -cp "out;res" com.F99.Main }