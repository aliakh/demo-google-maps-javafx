About
=====
1) Get [Google Maps API key](https://developers.google.com/maps/documentation/embed/get-api-key).

2) Run with VM arguments:
```
--module-path C:\Java\javafx-sdk-13.0.1\lib --add-modules=javafx.base,javafx.web
```

How to build and run with installed maven
=========================================

```
mvn install
mvn exec:java
```

### Java version
```
$ java --version

openjdk 11.0.7 2020-04-14
OpenJDK Runtime Environment AdoptOpenJDK (build 11.0.7+10)
OpenJDK 64-Bit Server VM AdoptOpenJDK (build 11.0.7+10, mixed mode)
```

### Maven version
```
$ mvn --version

Apache Maven 3.6.3 (cecedd343002696d0abb50b32b541b8a6ba2883f)
Maven home: ~/.sdkman/candidates/maven/current
Java version: 11.0.7, vendor: AdoptOpenJDK, runtime: /home/viktor/.sdkman/candidates/java/11.0.7.hs-adpt
Default locale: en_US, platform encoding: UTF-8
OS name: "linux", version: "5.4.0-33-generic", arch: "amd64", family: "unix"
```