#Cloud of Things - Genius Client

This is a Client for the Deutsche Telekom Cloud of Things service. It is intended for testing and demonstration purposes.

#Usage

*During Development*

1. Clone my GMapsFX Repo `git@github.com:marquies/GMapsFX.git`
2. Build the snapshot with `mvn install`
3. Git Checkout this project
4. Execute `mvn exec:java`

**Note:** If you don't use MacOS you may have to change the path to JavaFX in the pom.xml
````
<systemPath>${java.home}/lib/jfxswt.jar</systemPath>
```
