# pobi-java
Migration of c++ pobi project to java

## Changes
Here are list of changes which are handled differently in the java implementation:
- getInfo methods are just toString().
- managers name does not describe well what it exactly does, now it's just services.
- all functions that returns float should return double instead