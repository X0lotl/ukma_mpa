import java.util.Arrays;
import java.util.Scanner;

public class Kho {
  public static class SquareMatrix {
    protected int N;
    protected int[][] _matrix;

    public SquareMatrix(int N, int[] firstRow) {
      this.N = N;
      this._matrix = new int[N][N];
      this._matrix[0] = firstRow;
    }

    public String toString() {
      StringBuilder stringBuilder = new StringBuilder();
      stringBuilder.append("{\n");

      for (int i = 0; i < N; i++) {
        stringBuilder.append("[ ");
        for (int j = 0; j < N; j++) {
          stringBuilder.append(this._matrix[i][j]);
          stringBuilder.append(" ");
        }
        stringBuilder.append("],\n");
      }

      stringBuilder.append("}\n");

      return stringBuilder.toString();
    }

    public boolean isValid() {
      for (int i = 0; i < N; i++) {
        int[] row = Arrays.copyOf(_matrix[i], N);
        Arrays.sort(row);
        for (int j = 0; j < N - 1; j++) {
          if (row[j] != 0 && row[j] == row[j + 1]) {
            return false;
          }
        }

        int[] col = new int[N];

        for (int j = 0; j < N; j++) {
          col[j] = _matrix[j][i];
        }

        if (col[0] == 1) {
          for (int j = 0; j < N - 1; j++){
            if (col[j] != 0 && col[j + 1] != 0 && col[j + 1] - col[j] != 1) {
              return false;
            }
          }
        }

        Arrays.sort(col);
        for (int j = 0; j < N - 1; j++) {
          if (col[j] != 0 && col[j] == col[j + 1]) {
            return false;
          }
        }
      }

      return true;
    }

  }

  public static boolean fillMatrix(SquareMatrix matrix, int x, int y, int value) {
    matrix._matrix[x][y] = value;

    if (matrix.isValid()) {
      if (x + 1 == matrix.N && y + 1 == matrix.N) {

        return true;
      }

      if (y + 1 == matrix.N) {
        y = 0;
        x++;
      } else {
        y++;
      }

      for (int i = 1; i <= matrix.N; i++) {
        if (fillMatrix(matrix, x, y, i)) {
          return true;
        }
      }
      return false;
    } else {
      matrix._matrix[x][y] = 0;
      return false;
    }
  }

  public static void solve(SquareMatrix matrix) {
    for (int i = 1; i <= matrix.N; i++) {
      if (fillMatrix(matrix,1,0, i)) {
        return;
      }
    }
  }


  public static void main(String[] args) {
    Scanner scanner = new Scanner(System.in);
    System.out.println("Enter square size from 3 to 10");
    int N = scanner.nextInt();

    if (N < 3 || N > 10) {
      System.out.println("Incorrect input");
      return;
    }

    int firstRow[] = new int[N];
    System.out.println("Enter first column elements");
    for (int i = 0; i < N; i++) {
      try {
        firstRow[i] = scanner.nextInt();
      } catch (Exception e) {
        System.err.println(e);
        return;
      }
    }

    SquareMatrix squareMatrix = new SquareMatrix(N, firstRow);


    System.out.println(squareMatrix);

    solve(squareMatrix);

    System.out.println(squareMatrix);

  }
}
