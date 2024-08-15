## Command to build
mvn clean package -DskipTests

# Command to run grid
## Start HUB

## MacOS
```
java -cp selenium-server-standalone-3.141.59.jar:selenium-grid-custom-matcher-3.141.59.jar org.openqa.grid.selenium.GridLauncherV3 -role hub -hubConfig hubConfig.json
```

## Windows
```
java -cp "selenium-server-standalone-3.141.59.jar;selenium-grid-custom-matcher-3.141.59.jar" org.openqa.grid.selenium.GridLauncherV3 -role hub -hubConfig hubConfig.json
```

## Start Node 01 - IOS
```
appium server --nodeconfig node_6000_config.json --base-path=/wd/hub -p 6000
```

## Start Node 02 - ANDROID
```
appium server --nodeconfig node_7000_config.json --base-path=/wd/hub -p 7000
```

## Start Node 03 - ANDROID
```
appium server --nodeconfig node_8000_config.json --base-path=/wd/hub -p 8000
```