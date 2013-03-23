# Overview

This is a simple Maven plugin for managing a Moco server.

# Usage

First, add the plugin to your `pom.xml`.

```
<plugin>
    <groupId>moco-maven-plugin</groupId>
    <artifactId>moco-maven-plugin</artifactId>
    <version>1.0-SNAPSHOT</version>
    <configuration>
        <port>8081</port>
        <configFile>config.json</configFile>
        <stopPort>8082</stopPort> <!-- Optional - only if using stop -->
    </configuration>
</plugin>
```

You can then execute the start or stop goals like so,
* `mvn moco-maven-plugin:moco-maven-plugin:start`
* `mvn moco-maven-plugin:moco-maven-plugin:stop`
