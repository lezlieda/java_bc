all: clean ex00

ex00:
	javac -d . *.java
	jar -cfe ex00.jar ex00.Program ex00
	java -jar ex00.jar

clean:
	rm -rf ex00 ex00.jar result.txt