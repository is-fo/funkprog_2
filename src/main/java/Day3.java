import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class Day3 {
    public static void main(String[] args) {
        List<String> input;
        try {
            input = Files.readAllLines(Path.of("inputs/day3mini"));
        } catch (IOException e) {
            return;
        }

        int[] mcb = new int[input.get(0).length()];
        for (int i = 0; i < input.size(); i++) {
            for (int j = 0; j < input.get(i).length(); j++) {
                if (input.get(i).charAt(j) == '1') {
                    mcb[j]++;
                } else {
                    mcb[j]--;
                }
            }
        }

        StringBuilder gamma1 = new StringBuilder(), epsilon1 = new StringBuilder();
        IntStream.range(0, input.get(0).length()).forEach(j -> {
            var partitioned = input.stream()
                    .collect(Collectors.partitioningBy(s -> s.charAt(j) == '1'));

            if (partitioned.get(true).size() > partitioned.get(false).size()) {
                gamma1.append("1");
                epsilon1.append("0");
            } else {
                gamma1.append("0");
                epsilon1.append("1");
            }
        });

        System.out.println("funk: " + Integer.parseInt(gamma1.toString(),
                2) * Integer.parseInt(epsilon1.toString(), 2));


        String gamma = "";
        String epsilon = "";
        for (Integer i : mcb) {
            if (i > 0) {
                gamma += "1";
                epsilon += "0";
            } else {
                gamma += "0";
                epsilon += "1";
            }
        }

        int res =
                Integer.parseInt(gamma, 2)
                * Integer.parseInt(epsilon, 2);

        System.out.println("Part 1: " + res);

        System.out.println("Part 2: " + reduMax(input, 0));
        System.out.println("Expctd: [10111]");
        System.out.println("Part 2: " + reduMin(input, 0));
        System.out.println("Expctd: [01010]");

        System.out.println(Integer.parseInt(reduMax(input, 0).getFirst(),
                2) * Integer.parseInt(reduMin(input, 0).getFirst(), 2));
    }

    public static List<String> reduMax(List<String> input, Integer index) {
        if (input.get(0).length() == index) {
            return input;
        }

        final int finalIndex = index;
        var partioned = input.stream()
                .collect(Collectors.partitioningBy(s -> s.charAt(finalIndex) == '1'));


        if (partioned.get(true).size() >= partioned.get(false).size()) {
            return reduMax(input.stream()
                    .filter(e -> e.charAt(finalIndex) != '0')
                    .toList(), ++index);
        } else {
            return reduMax(input.stream()
                    .filter(e -> e.charAt(finalIndex) != '1')
                    .toList(), ++index);
        }
    }

    public static List<String> reduMin(List<String> input, Integer index) {
        if (input.size() == 1 || input.get(0).length() == index) {
            return input;
        }

        int finalIndex = index;
        var partioned = input.stream()
                .collect(Collectors.partitioningBy(s -> s.charAt(finalIndex) == '1'));


        if (partioned.get(true).size() < partioned.get(false).size()) {
            return reduMin(input.stream()
                    .filter(e -> e.charAt(finalIndex) != '0')
                    .toList(), ++index);
        } else {
            return reduMin(input.stream()
                    .filter(e -> e.charAt(finalIndex) != '1')
                    .toList(), ++index);
        }
    }
}
