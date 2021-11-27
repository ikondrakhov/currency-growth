# Currency Growth Application
___
## Prerequisites
___
1. Create dev.properties in src/main/resources folder
- Add GIF_API_KEY variable with [gif api](https://developers.giphy.com/docs/api#quick-start-guide) key
- Create CURRENCY_API_KEY variable with [open exchange api](https://docs.openexchangerates.org) key

dev.properties example: \
GIF_API_KEY=keytogiphyapi \
CURRENCY_API_KEY=keytoopenexchangeratesapi

2. Docker or Java 11

## How to launch
___

on Windows instead of ./ use .\ \
Execute next commands in cmd or terminal in project root directory to run or build the app

### For gradlew

./gradlew tasks - to list tasks \
./gradlew bootRun - to run as Spring Boot application \
./gradlew bootJar - to build executable jar (generated jar will be in build/libs/)

### For Docker build

1. ./gradlew bootJar
2. docker build -t currency-app:0.0.1 .
3. docker run -d -p 8080:8080 -t currency-app:0.0.1
