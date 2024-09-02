all: clean ex03

ex03:
	@javac -d . *.java
	@jar -cfe ex03.jar ex03.Program ex03
	java -jar ex03.jar --threadsCount=3

clean:
	@rm -rf ex03 ex03.jar *.jpg *.png *.jpeg *.pdf