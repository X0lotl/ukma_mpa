import java.io.File;
import java.io.FileNotFoundException;
import java.util.Arrays;
import java.util.Scanner;

public class Kho {

  private static class Symplex {
    private final int[][] table;
    private int _m;
    int[] _C;
    int[][] _constants;

    public Symplex(int m, int[] C, int[][] constants) {
      this._m = m;
      this.table = new int[m + 1][(2 * m) + 1];
      this._C = C;
      this._constants = constants;
      if (m >= 0) System.arraycopy(C, 0, this.table[0], 0, m);

      for (int i = 0; i < m; i++) {
        for (int j = 0; j < m + 1; j++) {
          if (j == m) {
            this.table[i + 1][(2 * m)] = constants[i][j];
          } else {
            this.table[i + 1][j] = constants[i][j];
          }
        }
        this.table[i + 1][m + i] = 1;
      }
    }

    public int[][] getTable() {
      return this.table;
    }


    public void solve() {
      int m = this._m;
      int maxC = 0;
      int indexMaxC = 0;

      for (int i = 0; i < m; i++) {
        if (maxC < this._C[i]) {
          maxC = this._C[i];
          indexMaxC = i;
        }
      }

      int minRow= this.table[1][(2*m)]/this.table[1][indexMaxC];
      int minRowIndex = 1;

      for (int i = 1; i < m + 1; i++) {
        int tempMinRow = this.table[i][2*m]/this.table[1][indexMaxC];
        if (minRow < tempMinRow) {
          minRow = tempMinRow;
          minRowIndex = i;
        }
      }

      int tempX = this.table[minRowIndex][indexMaxC];

      for (int i = 0; i < this.table[minRowIndex].length; i++) {
        this.table[minRowIndex][i] = this.table[minRowIndex][i] / tempX;
      }

      System.out.println(this);

      for (int i = 0; i < m + 1; i++) {
        if (i != minRowIndex) {
          int coef = this.table[i][indexMaxC];
          for (int j = 0; j < this.table[i].length; j++) {
            int temp = this.table[i][j] - ( coef  * this.table[minRowIndex][j]);
            this.table[i][j] = temp;
          }
        }
      }

      StringBuilder stringBuilder = new StringBuilder();
      stringBuilder.append("Відповідь: ( ");
      int sum = 0;
      int finalX;
      for (int i = 0; i < m; i++) {
        stringBuilder.append("x");
        stringBuilder.append(i);
        stringBuilder.append("=");
        finalX = this.table[i+1][m*2];
        stringBuilder.append(finalX);
        sum += (this._C[i] * finalX);
        if (i != m - 1){
          stringBuilder.append(", ");
        }
      }
      stringBuilder.append(" ) = ");
      stringBuilder.append(sum);

      System.out.println(stringBuilder.toString());
    }

    @Override
    public String toString() {
      StringBuilder stringBuilder = new StringBuilder();
      stringBuilder.append("{ \n");

      for (int[] row : this.table) {
        stringBuilder.append(Arrays.toString(row));
        stringBuilder.append("\n");
      }
      stringBuilder.append("}");
      return stringBuilder.toString();
    }
  }

  public static void main(String[] args) throws FileNotFoundException {
    Scanner scanner = new Scanner(new File("Kho.txt"));

    int m = scanner.nextInt();


    int[] C = new int[m];
    for (int i = 0; i < m; i++) {
      C[i] = scanner.nextInt();
    }

    int[][] constants = new int[m][m + 1];

    for (int i = 0; i < m; i++) {
      for (int j = 0; j < m + 1; j++) {
        constants[i][j] = scanner.nextInt();
      }
    }

    Symplex symplex = new Symplex(m, C, constants);
    symplex.solve();
  }
}