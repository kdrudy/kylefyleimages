# Kyle Fyle Images
Simple image display web server using http://sparkjava.com/

You specify a directory of images anywhere on your computer that will then be served up and displayed.  It will recursively read into directories. It will ignore hidden files starting with a period but will try to treat everything else as if it were an image.

To build the jar run the command:
```
mvn clean compile assembly:single
```

To run the jar use the command:
```
java -jar kylefyleimages-1.0-jar-with-dependencies.jar "<directory with images you want to display>"
```
