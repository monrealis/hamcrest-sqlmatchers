sqlmatchers
===
[![Build Status](https://travis-ci.org/monrealis/hamcrest-sqlmatchers.svg?branch=master)](https://travis-ci.org/monrealis/hamcrest-sqlmatchers)
[![Maven Central](https://maven-badges.herokuapp.com/maven-central/eu.vytenis.hamcrest/sqlmatchers/badge.svg)](http://search.maven.org/#artifactdetails%7Ceu.vytenis.hamcrest%7Csqlmatchers%7C0.4%7Cjar)
[![License](http://img.shields.io/:license-apache-blue.svg)](http://www.apache.org/licenses/LICENSE-2.0.html)

This library contains Hamcrest matchers to validate SQL statements.

It is expected to be used in unit tests to validate correctness of generated SQL.

Usage
===

Dependency can be downloaded from central Maven repository.

```xml
<dependency>
	<groupId>eu.vytenis.hamcrest</groupId>
	<artifactId>sqlmatchers</artifactId>
	<version>0.4</version>
</dependency>
```

If you are missing org.gibello:zql-parser

```xml
		<dependency>
			<groupId>eu.vytenis.hamcrest</groupId>
			<artifactId>sqlmatchers</artifactId>
			<version>0.4</version>
			<classifier>jar-with-dependencies</classifier>
			<exclusions>
				<exclusion>
					<groupId>org.gibello</groupId>
					<artifactId>zql-parser</artifactId>
				</exclusion>
			</exclusions>
		</dependency>
```

```java
assertThat("select * from dual", isSelect());
```
