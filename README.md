# UrlAnalyzer for ImmobilienScout

Compile:
```
./mvnw package
```

Execute WAR file standalone
```
java -jar target/urlanalyzer-0.0.1-SNAPSHOT.war
```

Otherwise the WAR file can be deployed to any capable application server.

**Note:** The application server need to support Java 8.

### Assumptions made on the requirements

- HTML Version: 
The current solution checks the `DOC_TYPE` in order to determine the used html version.

- Internal and External Links:
The current solution detects internal links by assuming either having the TLD inside the
`href` attribute or starting with `/`. All other links are deemed as external.

- Login Form:
The current solution assumes that there is a login form if there is a `input` field with
type `password` existing in the parsed html.