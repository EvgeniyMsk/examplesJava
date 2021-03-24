import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.PriorityQueue;
import java.util.Scanner;
 
 
public class Main {
    public static void main(String[] args) throws IOException{
        new Main().run("input.txt", "output.txt");
    }
 
    public static class Cell implements Comparable<Cell> {
        static int count;
        int x;
        int y;
        int z;
        int path;
        char c;
 
        Cell(int z, int x, int y, char c, int path) {
            this.z = z;
            this.x = x;
            this.y = y;
            this.c = c;
            this.path = path;
            Cell.count++;
        }
 
        public int compareTo(Cell o) {
            return (path - o.path > 0 ? 1 : 0);
        }
    }
 
    private PrintWriter printWriter;
    private Scanner scanner;
    private int startX, startY, startZ, finishX, finishY, finishZ;
    private Cell[][][] matrix;
    private int h, m, n;
    private PriorityQueue<Cell> priorityQueue = new PriorityQueue<Cell>();
 
 
    void run(String input, String output) throws IOException
    {
        readData(input, output);
        check(matrix[startZ][startX][startY]);
        doWave();
        printWriter.println(matrix[finishZ][finishX][finishY].path * 5);
        printWriter.close();
 
    }
 
    private void readData(String input, String output) throws IOException
    {
        scanner = new Scanner(new File(input));
        printWriter = new PrintWriter(new File(output));
        h = scanner.nextInt();
        n = scanner.nextInt();
        m = scanner.nextInt();
        matrix = new Cell[h][m][n];
        for (int z = 0; z < h; z++) {
            for (int i = 0; i < n; i++) {
                String line = scanner.next();
                    for (int j = 0; j < m; j++) {
                    switch (line.charAt(j)) {
                        case '1': {
                            matrix[z][j][i] = new Cell(z, j, i, '1', 0);
                            startX = j;
                            startY = i;
                            startZ = z;
                            break;
                        }
                        case '.': {
                            matrix[z][j][i] = new Cell(z, j, i, '.', 0);
                            break;
                        }
                        case 'o': {
                            matrix[z][j][i] = new Cell(z, j, i, 'o', -1);
                            break;
                        }
                        case '2': {
                            matrix[z][j][i] = new Cell(z, j, i, '2', 0);
                            finishX = j;
                            finishY = i;
                            finishZ = z;
                            break;
                        }
                    }
                }
 
            }
        }
 
    }
 
    private void check(Cell cell)
    {
        int x = cell.x;
        int y = cell.y;
        int z = cell.z;
 
        if ((x + 1 < m) && (matrix[z][x + 1][y].path == 0) && ((matrix[z][x + 1][y].c == '.') || (matrix[z][x + 1][y].c == '2'))) {
            matrix[z][x + 1][y].path = cell.path + 1;
            priorityQueue.add(matrix[z][x + 1][y]);
        }
        if ((x - 1 >= 0) && (matrix[z][x - 1][y].path == 0) && ((matrix[z][x - 1][y].c == '.') || (matrix[z][x - 1][y].c == '2'))) {
            matrix[z][x - 1][y].path = cell.path + 1;
            priorityQueue.add(matrix[z][x - 1][y]);
        }
        if ((y + 1 < n) && (matrix[z][x][y + 1].path == 0) && ((matrix[z][x][y + 1].c == '.') || (matrix[z][x][y + 1].c == '2'))) {
            matrix[z][x][y + 1].path = cell.path + 1;
            priorityQueue.add(matrix[z][x][y + 1]);
        }
        if ((y - 1 >= 0) && (matrix[z][x][y - 1].path == 0) && ((matrix[z][x][y - 1].c == '.') || (matrix[z][x][y - 1].c == '2'))) {
            matrix[z][x][y - 1].path = cell.path + 1;
            priorityQueue.add(matrix[z][x][y - 1]);
        }
        if ((z + 1 < h) && (matrix[z + 1][x][y].path == 0) && ((matrix[z + 1][x][y].c == '.') || (matrix[z + 1][x][y].c == '2'))) {
            matrix[z + 1][x][y].path = cell.path + 1;
            priorityQueue.add(matrix[z + 1][x][y]);
        }
    }
 
    private void doWave(){
        check(matrix[startZ][startX][startY]);
        while (priorityQueue.iterator().hasNext())
        {
            check(priorityQueue.iterator().next());
            priorityQueue.poll();
        }
    }
 
}