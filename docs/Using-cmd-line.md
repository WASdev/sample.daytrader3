## Building and running the sample using the command line

### Clone Git Repo
:pushpin: [Switch to Eclipse example](/docs/Using-WDT.md/#clone-git-repo)

```bash

$ git clone https://github.com/WASdev/sample.daytrader3.git
$ cd sample.daytrader3

```

### Building the sample
:pushpin: [Switch to Eclipse example](/docs/Using-WDT.md/#building-the-sample-in-eclipse)

This sample can be built using either [Gradle](#gradle-commands) or [Maven](#apache-maven-commands).

###### [Gradle](http://gradle.org/) commands

```bash
$ gradle build
```
###### [Apache Maven](http://maven.apache.org/) commands

```bash
$ mvn install
```


The built ear file is copied into the apps directory of the server configuration located in the daytrader3-ee6-wlpcfg directory:

```text
daytrader3-ee6-wlpcfg
 +- servers
     +- daytrader3-ee6-wlpcfg                   <-- specific server configuration
        +- server.xml                          <-- server configuration
        +- apps                                <- directory for applications
           +- daytrader3-ee6.ear                <- sample application
        +- logs                                <- created by running the server locally
        +- workarea                            <- created by running the server locally
```

### Running the application locally
:pushpin: [Switch to Eclipse example](/docs/Using-WDT.md/#running-the-application-locally)

Pre-requisite: [Download WAS Liberty](/docs/Downloading-WAS-Liberty.md)

Use the following to start the server and run the application:

```bash
$ export WLP_USER_DIR=/path/to/sample.daytrader3/daytrader3-ee6-wlpcfg
$ /path/to/wlp/bin/installUtility install daytrader3_Sample
$ /path/to/wlp/bin/server start daytrader3_Sample

1.  Confirm web browser opens on "http://localhost:9083/daytrader" 
2.  In the web browser, Click on the configuration tab.
3.  Click on '(Re)-create  DayTrader Database Tables and Indexes' to create the database.
4.  Click on '(Re)-populate  DayTrader Database' to populate the database.
5.  Restart the server-> .  Now the application will be ready for use.
$ /path/to/wlp/bin/server stop daytrader3_Sample
$ /path/to/wlp/bin/server start daytrader3_Sample
```

* `start` runs the server in the background. Look in the logs directory for console.log to see what's going on, e.g.
* `stop` stop the server in the background. Look in the logs directory for console.log to see what's going on, e.g.
* `run` runs the server in the foreground.

```bash
$ tail -f ${WLP_USER_DIR}/servers/daytrader3_Sample/logs/console.log
```

