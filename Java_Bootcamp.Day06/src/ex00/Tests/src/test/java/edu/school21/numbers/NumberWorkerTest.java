package edu.school21.numbers;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;
import org.junit.jupiter.params.provider.ValueSource;

import static edu.school21.numbers.NumberWorker.isPrime;
import static edu.school21.numbers.NumberWorker.sumOfDigits;
import static org.junit.jupiter.api.Assertions.*;

class NumberWorkerTest {

    @ParameterizedTest(name = "isPrimeForPrimes")
    @ValueSource(ints = {3, 11, 13, 101, 2269, 5419, 13267, 19927, 22447, 23497, 24571, 25117, 26227, 27361, 33391, 35317})
    void isPrimeForPrimes(int number) {
        assertTrue(isPrime(number));
    }

    @ParameterizedTest(name = "isPrimeForNotPrimes")
    @ValueSource(ints = {4, 15, 18, 100, 23456, 33456, 2344554, 79675200})
    void isPrimeForNotPrimes(int number) {
        assertFalse(isPrime(number));
    }

    @ParameterizedTest(name = "isPrimeForIncorrectNumbers")
    @ValueSource(ints = {-123, -10, 0, 1})
    void isPrimeForIncorrectNumbers(int number) {
        assertThrows(IllegalNumberException.class, () -> isPrime(number));
    }

    @ParameterizedTest(name = "digitsSum")
    @CsvFileSource(resources = "/data.csv")
    void digitsSum(int number, int sum) {
        assertEquals(sumOfDigits(number), sum);
    }

}