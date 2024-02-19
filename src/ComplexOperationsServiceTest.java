import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.ValueSource;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;

class ComplexOperationsServiceTest {
    private final ComplexOperationsService service = new ComplexOperationsService();

    @ParameterizedTest
    @CsvSource({
            "weak, false",
            "strongPassword1@, true",
            "noDigit!, false",
            "NoSpecialChar1, false"
    })
    void validatePasswordStrength(String password, boolean isValid) {
        if (!isValid){
            assertThrows(IllegalArgumentException.class, ()->service.validatePasswordStrength(password));
        }else {
            Assertions.assertTrue(service.validatePasswordStrength(password));
        }
    }

    @ParameterizedTest
    @ValueSource(strings = {"This is correct", "This text contains error", "fail is not good", "wrong way"})
    void processText(String text) {
        if (text.contains("error") || text.contains("fail") || text.contains("wrong")) {
            assertThrows(IllegalArgumentException.class, () -> service.processText(text));
        } else {
            assertTrue(service.processText(text));
        }
    }

    @ParameterizedTest
    @CsvSource({"1,2,3", "one,two,three", "10,,20", "5.5,6.5"})
    void sumOfNumbersInString(String numbers) {
        if (numbers.matches(".*[a-zA-Z]+.*") || numbers.contains(",,")) {
            assertThrows(IllegalArgumentException.class, () -> service.sumOfNumbersInString(numbers));
        } else {
            assertDoesNotThrow(()-> service.sumOfNumbersInString(numbers));
        }
    }

    @ParameterizedTest
    @ValueSource(strings = {"http://valid.com", "https://valid.com", "ftp://invalid.com", "justtext"})
    void validateUrlFormat(String url) {
        if (!url.startsWith("http")) {
            assertThrows(IllegalArgumentException.class, () -> service.validateUrlFormat(url));
        } else {
            assertDoesNotThrow(() -> service.validateUrlFormat(url));
        }
    }

    public static Stream<Arguments> emailListProvider() {
        return Stream.of(
                Arguments.of(Arrays.asList("valid@email.com", "invalid-email"), false),
                Arguments.of(Arrays.asList("valid@email.com", "also.valid@email.com"), true)
        );
    }

    @ParameterizedTest
    @MethodSource("emailListProvider")
    void checkEmailListConsistency(List<String> emails, boolean isValid) {
        if (!isValid) {
            assertThrows(IllegalArgumentException.class, () -> service.checkEmailListConsistency(emails));

        } else {
            assertDoesNotThrow(()->service.checkEmailListConsistency(emails));
        }
    }

    @ParameterizedTest
    @ValueSource(strings = {"level", "A man a plan a canal Panama", "radar", "hello"})
    void checkStringPalindrome(String input) {
        String sanitized = input.replaceAll("\\s+", "").toLowerCase();
        String reversed = new StringBuilder(sanitized).reverse().toString();
        if (!sanitized.equals(reversed)) {
            assertThrows(IllegalArgumentException.class, () -> service.checkStringPalindrome(input));
        } else {
            assertDoesNotThrow(() -> service.checkStringPalindrome(input));
        }
    }

    @ParameterizedTest
    @ValueSource(strings = {"AB123456", "CD987654", "XY123456", "invalidID", "12345678"})
    void validateIdentificationNumber(String id) {
        if (!id.matches("^[A-Za-z]{2}\\d{6}$")) {
            assertThrows(IllegalArgumentException.class, () -> service.validateIdentificationNumber(id));
        } else {
            assertDoesNotThrow(() -> service.validateIdentificationNumber(id));
        }
    }

    @ParameterizedTest
    @MethodSource("generateTestCasesForSumOfListAgainstThreshold")
    void checkSumOfListAgainstThreshold(List<Integer> numbers, int threshold) {
        int sum = numbers.stream().mapToInt(Integer::intValue).sum();
        if (sum <= threshold) {
            assertThrows(IllegalArgumentException.class, () -> service.checkSumOfListAgainstThreshold(numbers, threshold));
        } else {
            assertDoesNotThrow(() -> service.checkSumOfListAgainstThreshold(numbers, threshold));
        }
    }

    private static Stream<Arguments> generateTestCasesForSumOfListAgainstThreshold() {
        return Stream.of(
                Arguments.of(Arrays.asList(1, 2, 3), 5),
                Arguments.of(Arrays.asList(10, 20, 30), 50),
                Arguments.of(Arrays.asList(5, 5, 5), 14),
                Arguments.of(Arrays.asList(1, 2, 3), 6)
        );
    }

    @ParameterizedTest
    @ValueSource(strings = {"192.168.0.1", "10.0.0.255", "invalidIP", "256.256.256.256"})
    void validateIPAddress(String ipAddress) {
        if (!ipAddress.matches("^([0-9]{1,3}\\.){3}[0-9]{1,3}$")) {
            assertThrows(IllegalArgumentException.class, () -> service.validateIPAddress(ipAddress));
        } else {
            assertDoesNotThrow(() -> service.validateIPAddress(ipAddress));
        }
    }

    @ParameterizedTest
    @MethodSource("generateTestCasesForEnsureNoDuplicateEntries")
    void ensureNoDuplicateEntries(List<String> entries) {
        long uniqueCount = entries.stream().distinct().count();
        if (uniqueCount < entries.size()) {
            assertThrows(IllegalArgumentException.class, () -> service.ensureNoDuplicateEntries(entries));
        } else {
            assertDoesNotThrow(() -> service.ensureNoDuplicateEntries(entries));
        }
    }

    private static Stream<Arguments> generateTestCasesForEnsureNoDuplicateEntries() {
        return Stream.of(
                Arguments.of(Arrays.asList("one", "two", "three")),
                Arguments.of(Arrays.asList("apple", "banana", "apple", "orange")),
                Arguments.of(Collections.singletonList("singleEntry")),
                Arguments.of(Arrays.asList("1", "2", "3", "2"))
        );
    }

}