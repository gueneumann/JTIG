JTIG
====

Java Tree Insertion Grammar


### Building the project (via [Maven](http://maven.apache.org/))

1. Installing 3rd party JARs into local maven repository  
There is actually one 3rd party JAR ([morphadorner](http://morphadorner.northwestern.edu/)), which isn't available in the global Maven repository. 
To add it, run:   
`mvn install:install-file -Dfile=lib/morphadorner.jar -DgroupId=edu.northwestern.at -DartifactId=Morphadorner -Dversion=2.0 -Dpackaging=jar`  
See Also: [Maven HowTo](http://maven.apache.org/guides/mini/guide-3rd-party-jars-local.html)

2. Build package via maven
`mvn package`

### Copyright
TODO
