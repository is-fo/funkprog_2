import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Day4 {
    private static List<List<Integer>> flatboards;
    public static void main(String[] args) {
        List<Integer> loket = null;
        flatboards = new ArrayList<>();
        List<Integer> currentBoard = new ArrayList<>();

        try (BufferedReader br = new BufferedReader(new FileReader("inputs/day4.txt"))) {
            loket = Stream.of(br.readLine().split(","))
                    .map(Integer::parseInt)
                    .toList();
            System.out.println("Numbers: " + loket);

            String line;
            while ((line = br.readLine()) != null) {
                if (line.isBlank()) {
                    if (!currentBoard.isEmpty()) {
                        flatboards.add(new ArrayList<>(currentBoard));
                        currentBoard.clear();
                    }
                    continue;
                }

                List<Integer> row = Arrays.stream(line.trim().split("\\s+"))
                        .map(Integer::parseInt)
                        .collect(Collectors.toList());
                currentBoard.addAll(row);
            }

            if (!currentBoard.isEmpty()) {
                flatboards.add(new ArrayList<>(currentBoard));
            }
        } catch (IOException e) {
            System.out.println("IO Error: " + e.getMessage());
        }
        assert loket != null;
        int draw = 0;
        List<Integer> drawn = new ArrayList<>();
        for (int i = 0; i < 5; i++) {
            drawn.add(loket.get(i));
            draw = i;
        }
        while (draw < loket.size()) {
            for (int i = 0; i < flatboards.size(); i++) {
                List<Integer> flatboard = flatboards.get(i);
                if (bingoVertical(flatboard, drawn, flatboard.size(), i)
                        || bingoHorizontal(flatboard, drawn, flatboard.size(), i)) {
                    flatboards.remove(i--);
                }
            }
            drawn.add(loket.get(draw++));
        }
    }

    private static boolean bingoHorizontal(List<Integer> row, List<Integer> drawn, int size, int board) {
        for (int i = 0; i < row.size(); i += 5) {
            List<Integer> smallRow = new ArrayList<>();
            for (int j = 0; j < 5; j++) {
                smallRow.add(row.get(j + i));
            }
            if (bingo(smallRow, drawn, board)) {
                return true;
            }
        }
        return false;
    }

    private static boolean bingoVertical(List<Integer> row, List<Integer> drawn, Integer size, int board) {
        for (int col = 0; col < 5; col++) {
            List<Integer> smallrow = new ArrayList<>();

            for (int i = col; i < row.size(); i += 5) {
                smallrow.add(row.get(i));
            }
            if (smallrow.size() != 5) {
                return false;
            }

            if (bingo(smallrow, drawn, board)) {
                return true;
            }
        }
        return false;
    }
    private static boolean bingo(List<Integer> smallrow, List<Integer> drawn, int board) {
        for (Integer i : smallrow) {
            boolean thisnum = false;
            for (Integer d : drawn) {
                if (Objects.equals(i, d)) {
                    thisnum = true;
                    break;
                }
            }
            if (!thisnum) {
                return false;
            }
        }

        List<Integer> result = new ArrayList<>();
        for (Integer i : flatboards.get(board)) {
            boolean found = false;
            for (Integer b : smallrow) {
                if (i.equals(b)) {
                    found = true;
                    break;
                }
            }
            for (Integer d : drawn) {
                if (i.equals(d)) {
                    found = true;
                    break;
                }
            }
            if (!found) {
                result.add(i);
            }
        }
        System.out.println(result.stream()
                .mapToInt(Integer::intValue)
                .sum() * drawn.getLast());
        return true;
    }

}
