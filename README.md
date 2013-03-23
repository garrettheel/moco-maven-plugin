Overview
=============

This is a simple Maven plugin for managing a [Moco] (https://github.com/dreamhead/moco) server. It can be used to simplify manual running of the server or to automatically launch the server during some part of the maven lifecycle (such as `integration-test`).

Usage
=============

To get started, add the plugin to your `pom.xml`.

```xml
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

## Running manually

Starting the server manually is easy! Simply run the following command:

```
mvn com.garrettheel:moco-maven-plugin:run
```

This will run the the server indefinitely until the process is terminated.

## Running during the maven lifecycle

You can also configure maven to start and stop the Moco server during the build lifecycle. For example, the following configuration would support using the server for integration testing:

```xml
<plugin>
    <groupId>com.garrettheel</groupId>
    <!-- ... -->
    <configuration>
        <port>8081</port>
        <configFile>config.json</configFile>
    </configuration>
    <executions>
        <execution>
            <id>start-moco</id>
            <phase>pre-integration-test</phase>
            <goals>
                <goal>start</goal>
            </goals>
        </execution>
        <execution>
            <id>stop-moco</id>
            <phase>post-integration-test</phase>
            <goals>
                <goal>stop</goal>
            </goals>
        </execution>
    </executions>
</plugin>
```
