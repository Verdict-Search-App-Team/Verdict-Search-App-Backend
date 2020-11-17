# Demo spring backend project, searchable database of Hungarian court decisions

*Compatible only with Java 11. This is a Gradle project.*

## To run:

1.

Start MySQL server and create custom database

2.

Fill out application.properties file with JDBC data relevant to your database created in step 1.

3.

```./gradlew build```

4.

```./gradlew run --args='[ARGUMENT1] [ARGUMENT2] [ARGUMENT3]'```

OR


```/usr/bin/java -jar ./build/libs/opencourtwebapp-0.0.1-SNAPSHOT.jar [ARGUMENT1] [ARGUMENT2] [ARGUMENT3]```

Commandline arguments (may be written in any order, all are optional):

(optional) [ARGUMENT1] is the directory where decision rtf files are to be stored and downloaded, default is /Decisions folder

(optional) [ARGUMENT2] if "read" then the decisions are read from the storage directory specified, default is not to read

(optional) [ARGUMENT3] if "scrape" then is the the program starts to download files from birosag.hu, the site run by Hungarian courts, default is not to start downloading

## API documentation

"/home" returns JSON with the following structure and contents: [NUMBER OF DECISIONS IN DB, [{MOST POPULAR DECISION MAP OBJ 1}, {MOST POPULAR DECISION MAP OBJ 2}, ...]]

"/results?searchedTerm='[searchedTerm]'" returns JSON array of all decision map objects containing the '[searchedTerm]'

"/'[caseNumber]'" returns JSON one decision map object

[Prototype](https://backend.verdict-search.app/ "Verdict Search App Backend API")  is available for testing at https://backend.verdict-search.app/
