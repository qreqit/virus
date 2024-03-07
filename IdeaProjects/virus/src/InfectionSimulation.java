import java.util.Scanner;

public class InfectionSimulation {
    private static final int HEALTHY = 0;
    private static final int INFECTED = 9;
    private static final int RESISTANT = 4;
    private static int[][] matrix;
    private static int[][] secondMatrix;
    private static int n;

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Введіть розмір матриці (n x n): ");
        n = scanner.nextInt();
        matrix = new int[n][n];
        secondMatrix = new int[n][n];

        System.out.println("Введіть кількість заражених клітин: ");
        int infectedCells = scanner.nextInt();
        System.out.println("Введіть координати заражених клітин (x та y): ");
        for (int i = 0; i < infectedCells; i++) {
            int x = scanner.nextInt() - 1;
            int y = scanner.nextInt() - 1;
            matrix[x][y] = INFECTED;
        }
        for (int iteration = 0; iteration < 20; iteration++) {
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            transfusionMatrix();
            iterationInfection();
            showMatrix(iteration);
        }
    }

    private static void showMatrix(int iteration) {
        System.out.println("\nЧас: " + iteration + "\n");
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                if (matrix[i][j] == HEALTHY)
                    System.out.print("\033[0;32mH "); // Green for healthy cells
                else if (matrix[i][j] <= RESISTANT)
                    System.out.print("\033[0;34mR "); // Blue for resistant cells
                else if (matrix[i][j] <= INFECTED)
                    System.out.print("\033[0;31mI "); // Red for infected cells
            }
            System.out.println("\033[0m"); // Reset color
        }
    }
    private static void transfusionMatrix() {
        for (int i = 0; i < n; i++) {
            System.arraycopy(matrix[i], 0, secondMatrix[i], 0, n);
        }
    }

    private static void iterationInfection() {
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                if (matrix[i][j] > 0)
                    matrix[i][j] -= 1;
                if (testInfection(i, j))
                    infectionOfCells(i, j);
            }
        }
    }

    private static boolean testInfection(int x, int y) {
        return secondMatrix[x][y] >= 5;
    }

    private static void infectionOfCells(int x, int y) {
        for (int i = Math.max(0, x - 1); i <= Math.min(n - 1, x + 1); i++) {
            for (int j = Math.max(0, y - 1); j <= Math.min(n - 1, y + 1); j++) {
                if ((i != x || j != y) && matrix[i][j] == HEALTHY) {
                    if (i == x || j == y) {
                        if (Math.random() < 0.5) {
                            matrix[i][j] = INFECTED;
                        }
                    } else {
                        if (Math.random() < 0.25) {
                            matrix[i][j] = INFECTED;
                        }
                    }
                }
            }
        }
    }
}
