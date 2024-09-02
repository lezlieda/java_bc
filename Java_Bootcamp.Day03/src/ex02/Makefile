all: clean ex02

ex02:
	@javac -d . *.java
	@jar -cfe ex02.jar ex02.Program ex02
	java -jar ex02.jar --arraySize=11 --threadsCount=10

clean:
	@rm -rf ex02 ex02.jar