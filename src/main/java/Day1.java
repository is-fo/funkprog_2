import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.IntStream;

public class Day1 {
    public static void main(String[] args) {
        int[] inputList;
        try {
            List<String> temp = Files.readAllLines(Path.of("day1"));
            inputList = temp.stream()
                    .mapToInt(Integer::parseInt)
                    .toArray();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        int count = (int) IntStream.range(0, inputList.length - 1)
                .filter(i -> inputList[i] < inputList[i + 1])
                .count();
        System.out.println(count);

        System.out.println("Day2 expected: 1362");

        List<Integer> lcount = new ArrayList<>();
        IntStream.range(0, inputList.length - 2)
                        .forEach(i -> {
                            lcount.add(inputList[i]
                                    + inputList[i + 1]
                                    + inputList[i + 2]);
                        });
        System.out.println(lcount);
        count = (int) IntStream.range(0, lcount.size() - 1)
                        .filter(i -> lcount.get(i) < lcount.get(i + 1))
                .count();
        System.out.println("Actual: " + count);

    }
}
