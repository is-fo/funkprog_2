import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class Day2 {
    public static void main(String[] args) {
        List<String> input;
        try {
            input = Files.readAllLines(Path.of("day2"));

        } catch (IOException e) {
            return;
        }

        Map<Character, Integer> map = input.stream()
                .collect(Collectors.groupingBy(s -> s.charAt(0),
                        Collectors.summingInt(s -> Character.getNumericValue(s.charAt(s.length() - 1)))));

        System.out.println(
                map.get('f') * (map.get('d') - map.get('u'))
        );

        int aim = 0, forward = 0, depth = 0;

        for (String s : input) {
            int last = Character.getNumericValue(s.charAt(s.length() - 1));
            if (s.charAt(0) == 'f') {
                forward += last;
                depth += last * aim;
            } else if (s.charAt(0) == 'u') {
                aim -= last;
            } else {
                aim += last;
            }
        }
        System.out.println(forward * depth);
    }
}
