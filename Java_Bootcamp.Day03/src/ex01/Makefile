all: clean ex01

ex01:
	@javac -d . *.java
	@jar -cfe ex01.jar ex01.Program ex01
	java -jar ex01.jar --count=15

clean:
	@rm -rf ex01 ex01.jar