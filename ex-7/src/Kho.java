import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.*;

class Element {
  int type;
  double Qkz;
  double Qoj;

  Element(int type, double Qkz, double Qoj) {
    this.type = type;
    this.Qkz = Qkz;
    this.Qoj = Qoj;
  }
}

public class Kho {

  public static void main(String[] args) throws FileNotFoundException {
    File inputFile = new File("Kho_test_7.txt");
    Scanner scanner = new Scanner(inputFile);

    int k1 = scanner.nextInt();
    int k2 = scanner.nextInt();
    int k3 = scanner.nextInt();
    int N = k1 + k2 + k3;

    int K = scanner.nextInt();

    List<Element> elements = new ArrayList<>();

    for (int i = 0; i < K; i++) {
      int type = scanner.nextInt();
      double Qkz = scanner.nextDouble();
      double Qoj = scanner.nextDouble();
      elements.add(new Element(type, Qkz, Qoj));
    }

    scanner.close();

    List<List<Integer>> configurations = new ArrayList<>();
    configurations = generateConfigurations(K, N, k1, k2, k3);
    double maxReliability = 0.0;
    List<Integer> bestConfiguration = new ArrayList<>();
    for (int i = 0; i < configurations.size(); i++) {
      List<Integer> configuration = configurations.get(i);
      double reliability = calculateReliability(configuration, elements, k1, k2, k3);
      if (reliability > maxReliability) {
        maxReliability = reliability;
        bestConfiguration = configuration;
      }
    }

    System.out.println("Задана структура: " + k1 + " " + k2 + " " + k3);
    System.out.println("Кількість різнотипних елементів: " + K);
    System.out.println("Кількість різних конфігурацій: " + configurations.size());
    System.out.println("Максимальна надійність: " + maxReliability);
    System.out.println("Конфігурація з максимальною надійністю: ");
    for (int i = 0; i < bestConfiguration.size(); i++) {
      System.out.print(bestConfiguration.get(i) + " ");
      if (i == k1 - 1 || i == k1 + k2 - 1) {
        System.out.println();
      }
    }
    System.out.println();

    PrintWriter outputFile = new PrintWriter("Kho_rez_7.txt");
    outputFile.println("Задана структура: " + k1 + " " + k2 + " " + k3);
    outputFile.println("Кількість різнотипних елементів: " + K);
    outputFile.println("Кількість різних конфігурацій: " + configurations.size());
    outputFile.println("Максимальна надійність: " + maxReliability);
    outputFile.println("Конфігурація з максимальною надійністю: ");
    for (int i = 0; i < bestConfiguration.size(); i++) {
      outputFile.print(bestConfiguration.get(i) + " ");
      if (i == k1 - 1 || i == k1 + k2 - 1) {
        outputFile.println();
      }
    }
    outputFile.close();
    }

  private static List<List<Integer>> generateConfigurations(int K, int N, int k1, int k2, int k3) {
    Set<List<Integer>> uniqueConfigurations = new HashSet<>();

    List<Integer> current = new ArrayList<>();
    for (int i = 0; i < N; i++) {
      current.add(1);
    }

    while (true) {

      if (uniqueConfigurations.size() % 100 == 0) {
        System.out.println(uniqueConfigurations.size());
      }

      List<Integer> sortedCurrent = new ArrayList<>(current);
      sortedCurrent.sort(null);
      if (!uniqueConfigurations.contains(sortedCurrent)) {
        uniqueConfigurations.add(sortedCurrent);
      }

      int index = N - 1;
      while (index >= 0 && current.get(index) == K) {
        index--;
      }

      if (index < 0) {
        break;
      }

      current.set(index, current.get(index) + 1);
      for (int i = index + 1; i < N; i++) {
        current.set(i, 1);
      }
    }

    return new ArrayList<>(uniqueConfigurations);
  }


  public static long getConfigurationHash(List<Integer> configuration, int k1, int k2, int k3) {
    int mul1 = 1, mul2 = 1, mul3 = 1;
    int sum1 = 1, sum2 = 1, sum3 = 1;
    int powSum1 = 1, powSum2 = 1, powSum3 = 1;
    int i = 0;

    for (; i < k1; i++) {
      mul1 *= configuration.get(i) + 1;
      sum1 += configuration.get(i) + 1;
      powSum1 *= ((configuration.get(i) + 1) * (configuration.get(i) + 1));
    }

    for (; i < k1 + k2; i++) {
      mul2 *= configuration.get(i) + 1;
      sum2 += configuration.get(i) + 1;
      powSum2 *= ((configuration.get(i) + 1) * (configuration.get(i) + 1));
    }

    for (; i < k1 + k2 + k3; i++) {
      mul3 *= configuration.get(i) + 1;
      sum3 += configuration.get(i) + 1;
      powSum3 *= ((configuration.get(i) + 1) * (configuration.get(i) + 1));
    }

    long doubleHash = cantor((mul1 * sum1) + powSum1 + mul1, (mul2 * sum2) + powSum2 + mul2);
    long tripleHash = cantor(doubleHash, (mul3 * sum3) + powSum3 + mul3);

    return tripleHash;
  }

  public static long cantor(long a, long b) {
    return ((a + b) * (a + b + 1) + b) / 2;
  }

  private static double calculateReliability(List<Integer> configuration, List<Element> elements, int k1, int k2, int k3) {
    double p1 = 1.0;
    int l = 0;

    for (int i = 0; i < k1; i++) {
      int elementType = configuration.get(l++);
      p1 *= 1 - elements.get(elementType - 1).Qkz;
    }

    double p2 = 1.0;

    for (int i = 0; i < k2; i++) {
      double product = 1.0;
      for (int j = 0; j < k3; j++) {
        if (l < configuration.size()) {
          int elementType = configuration.get(l++);
          product *= elements.get(elementType - 1).Qoj;
        }
      }
      p2 *= 1 - product;
    }

    return p1 * (1 - p2);
  }


}