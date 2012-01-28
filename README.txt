This is a sample application to demonstrate the usage of JAXB API.

See AppMarshaller.java on how to create a XML data file
See AppUnmarshaller.java on how to read XML and create domain objects.

Before running the above main classes make sure to run following maven goal
"mvn jaxb2:xjc"

xjc is a default compiler that comes with JDK 1.6 and above to create java objects
from the XSD provided. In this example I am using maven plugin from codehaus.

*.xjb files are binding files used by xjc compiler for hints on how to create java
objects. You could have things like naming used for attributes and data types conversions
and so on.

Output file copied to target folder, including xml output from marshaling example

LINKS:
Maven Plugin Home Page - http://mojo.codehaus.org/jaxb2-maven-plugin/
XJC - http://docs.oracle.com/cd/E17802_01/webservices/webservices/docs/1.6/jaxb/xjc.html
XJC Binding - http://docs.oracle.com/cd/E17802_01/webservices/webservices/docs/1.5/tutorial/doc/JAXBUsing4.html
