Overview
=============

This is a simple Maven plugin for managing a [Moco] (https://github.com/dreamhead/moco) server. It can be used to consistently
run a Moco server on demand or to automatically launch the server during some part of the Maven lifecycle (such as `integration-test`).

Usage
=============

To get started, add the plugin to your `pom.xml`.

```xml
<plugin>
    <groupId>com.garrettheel</groupId>
    <artifactId>moco-maven-plugin</artifactId>
    <version>0.9.2</version>
    <configuration>
        <port>8081</port>
        <configFile>config.json</configFile>
    </configuration>
</plugin>
```

You may also need to add the Sonatype repository, depending on your environment.

```xml
<pluginRepositories>
    <pluginRepository>
        <id>sonatype</id>
        <url>https://oss.sonatype.org/content/repositories/public/</url>
    </pluginRepository>
</pluginRepositories>
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
