import java.util.Scanner;

public class Main {
  public static int modifiedLevenshteinDistance(String str1, String str2) {
    int len1 = str1.length();
    int len2 = str2.length();
    int[][] dp = new int[len1 + 1][len2 + 1];

    for (int i = 0; i <= len1; i++) {
      for (int j = 0; j <= len2; j++) {
        if (i == 0) {
          dp[i][j] = j == 0 ? 0 : dp[i][j - 1] + (int) (str2.charAt(j - 1) - 'a' + 1);
        } else if (j == 0) {
          dp[i][j] = dp[i - 1][j] + (int) (str1.charAt(i - 1) - 'a' + 1);
        } else {
          int cost = (int) Math.abs(str1.charAt(i - 1) - str2.charAt(j - 1));
          dp[i][j] = Math.min(Math.min(dp[i - 1][j] + (int) (str1.charAt(i - 1) - 'a' + 1),
            dp[i][j - 1] + (int) (str2.charAt(j - 1) - 'a' + 1)), dp[i - 1][j - 1] + cost);
        }
      }
    }

    return dp[len1][len2];
  }


  public static void main(String[] args) {
    Scanner scanner = new Scanner(System.in);

    System.out.println("Введіть перший рядок:");
    String A = scanner.nextLine();
    System.out.println("Введіть другий рядок:");
    String B = scanner.nextLine();

    System.out.println("Модифікована відастань Лавенштейна для радків A: '" + A + "', B: '" + B + "' = "  + modifiedLevenshteinDistance(A, B));
  }
}