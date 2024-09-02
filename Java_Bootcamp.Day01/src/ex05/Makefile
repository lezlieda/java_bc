all: clean ex05

ex05:
	javac -d . *.java
	jar -cfe ex05.jar ex05.Program ex05
	java -jar ex05.jar --profile=dev

clean:
	rm -rf ex05 ex05.jar