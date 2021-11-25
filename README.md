# Currency Growth Application
___
## Prerequisites
___
- Create dev.properties in src/main/resources folder
- Add GIF_API_KEY variables with [gif api](https://developers.giphy.com/docs/api#quick-start-guide) key
- Create CURRENCY_API_KEY variable with [open exchange api](https://docs.openexchangerates.org) key

dev.properties example:
GIF_API_KEY=keytogiphyapi
CURRENCY_API_KEY=keytoopenexchangeratesapi

## How to launch
___
App uses gradle

./gradlew tasks - to list tasks
./gradlew bootRun - to run as Spring Boot application
./gradlew bootJar - to build executable jar (generated jar will be in build/libs/)