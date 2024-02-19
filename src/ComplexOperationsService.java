import java.util.Arrays;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * validatePasswordStrength: Тестируйте различные пароли, чтобы проверить силу пароля.
 * Используйте @CsvSource для тестирования паролей с различными комбинациями символов.
 *
 * processText: Проверьте тексты на наличие запрещенных слов.
 * Используйте @ValueSource с различными фразами.
 *
 * sumOfNumbersInString: Тестируйте строки с числами, разделенными запятыми, включая некорректные строки.
 * @CsvSource может использоваться для предоставления различных строк.
 *
 * validateUrlFormat: Используйте @ValueSource для проверки различных форматов URL, включая невалидные.
 *
 * checkEmailListConsistency: Создайте тесты для проверки списков электронных адресов на наличие невалидных адресов.
 * @MethodSource может использоваться для генерации и предоставления списков адресов.
 *
 * checkStringPalindrome: Проверьте различные строки на то, являются ли они палиндромами.
 * Используйте @ValueSource для предоставления строк.
 *
 * validateIdentificationNumber: Тестируйте различные идентификационные номера на соответствие формату.
 * @ValueSource подходит для этой задачи.
 *
 * checkSumOfListAgainstThreshold: Проверьте, что сумма чисел в списке превышает заданный порог.
 * @MethodSource может использоваться для генерации тестовых списков чисел и порогов.
 *
 * validateIPAddress: Тестируйте различные строки на соответствие формату IPv4.
 * Используйте @ValueSource для предоставления IP адресов.
 *
 * ensureNoDuplicateEntries: Проверьте списки на наличие дубликатов.
 * @MethodSource подходит для предоставления различных списков строк.
 */
public class ComplexOperationsService {

    /**
     * - понять что делает метод
     * - понять какие у него поведения
     * - понять пограничные значения
     * - написать параметризованный тест с использованием пограничных значений
     */
    public boolean validatePasswordStrength(String password) {
        // Password must contain at least 8 characters, including a digit, a lowercase letter, an uppercase letter, and a special character
        String regex = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[!@#&()–[{}]:;',?/*~$^+=<>]).{8,}$";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(password);
        if (!matcher.matches()) {
            throw new IllegalArgumentException("Password does not meet the strength requirements.");
        }return true;
    }

    public boolean processText(String text) {
        // Text must not contain any forbidden words: "error", "fail", "wrong"
        List<String> forbiddenWords = Arrays.asList("error", "fail", "wrong");
        boolean containsForbiddenWord = forbiddenWords.stream().anyMatch(text::contains);
        if (containsForbiddenWord) {
            throw new IllegalArgumentException("Text contains forbidden words.");
        }return true;
    }

    public void sumOfNumbersInString(String numbers) {
        // The string must contain numbers separated by commas, e.g., "1,2,3"
        List<Double> parsedNumbers;
        try {
            parsedNumbers = Arrays.stream(numbers.split(","))
                    .map(Double::parseDouble)
                    .toList();
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("String contains non-numeric values.");
        }

        double sum = parsedNumbers.stream().mapToDouble(Double::doubleValue).sum();
        System.out.println("Sum: " + sum);
    }

    public void validateUrlFormat(String url) {
        // URL must start with http or https and contain a domain name
        String regex = "^(http|https)://[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,6}.*$";
        if (!url.matches(regex)) {
            throw new IllegalArgumentException("URL format is invalid.");
        }
    }

    public boolean checkEmailListConsistency(List<String> emails) {
        // All emails in the list must be valid
        String regex = "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,6}$";
        Pattern pattern = Pattern.compile(regex);
        boolean allEmailsValid = emails.stream().allMatch(email -> pattern.matcher(email).matches());
        if (!allEmailsValid) {
            throw new IllegalArgumentException("Email list contains invalid emails.");
        }return true;
    }

    public boolean checkStringPalindrome(String input) {
        String sanitized = input.replaceAll("\\s+", "").toLowerCase();
        String reversed = new StringBuilder(sanitized).reverse().toString();
        if (!sanitized.equals(reversed)) {
            throw new IllegalArgumentException("String is not a palindrome.");
        }return true;
    }

    public boolean validateIdentificationNumber(String id) {
        // ID должен быть в формате: две буквы, за которыми следуют 6 цифр (например, AB123456)
        if (!id.matches("^[A-Za-z]{2}\\d{6}$")) {
            throw new IllegalArgumentException("Identification number format is invalid.");
        }return true;
    }

    public boolean checkSumOfListAgainstThreshold(List<Integer> numbers, int threshold) {
        int sum = numbers.stream().mapToInt(Integer::intValue).sum();
        if (sum <= threshold) {
            throw new IllegalArgumentException("Sum of the list is not above the threshold.");
        }return true;
    }

    public boolean validateIPAddress(String ipAddress) {
        // IP адрес должен соответствовать формату IPv4
        if (!ipAddress.matches("^([0-9]{1,3}\\.){3}[0-9]{1,3}$")) {
            throw new IllegalArgumentException("IP address format is invalid.");
        }return true;
    }

    public boolean ensureNoDuplicateEntries(List<String> entries) {
        long uniqueCount = entries.stream().distinct().count();
        if (uniqueCount < entries.size()) {
            throw new IllegalArgumentException("List contains duplicate entries.");
        }return true;
    }
}