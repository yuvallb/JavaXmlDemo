# Java XML Demo

This project illustrates how to read XML files in Java.

The are 4 main methods for reading XML files:

1. DOM - reads the entire xml into an object and allows searching it. see: https://www.mkyong.com/java/how-to-read-xml-file-in-java-dom-parser/
2. SAX - stream oriented reading - faster and saves memory. see: https://www.mkyong.com/java/how-to-read-xml-file-in-java-sax-parser/
3. XSL based methods, for example XSL Transformation or XPath. 
4. Text based method, for example using Regular Expressions. This is a less reliable method , but can be faster to implement and/or to run in some cases.

In this example app we parse two XML files: A dataset of all active cellular antennas in Israel, and a dataset of cities in Israel.
We show two alternative ways to parse the first xml: using SAX and Regular Expressions.
The second xml is parsed using Regular Expression only.

The data files were downloaded from https://data.gov.il/dataset

