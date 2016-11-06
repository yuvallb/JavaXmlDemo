# Java XML Demo

This project illustrates how to read XML files in Java.

The are 3 main methods for reading XML files:

1. DOM - reads the entire xml into an object and allows searching it. see: https://www.mkyong.com/java/how-to-read-xml-file-in-java-dom-parser/
2. SAX - stream oriented reading - faster and saves memory. see: https://www.mkyong.com/java/how-to-read-xml-file-in-java-sax-parser/
3. XSLT

All these methods require the XML to be structured correctly.

In the example given here, we take a fourth approach: we read the whole xml into a string, and use Regular Expressions to extract data from it.
This is a les reliable method, but it does not require strict structure and can be faster in some cases.

The data files were downloaded from https://data.gov.il/dataset

