# Stars Between


## Running

```
runJvm
runJs
jsBrowserDistribution
```

## Pushing to web

```
aws s3 sync build/distributions/ s3://austinkucera.com/games/stars-between/

./gradlew jsBrowserDistribution && aws s3 sync build/distributions/ s3://austinkucera.com/games/stars-between/
```