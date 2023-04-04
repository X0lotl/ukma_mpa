import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

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
    configurations = generateConfigurations(K, N);
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

  private static List<List<Integer>> generateConfigurations(int K, int N) {
    List<List<Integer>> configurations = new ArrayList<>();

    List<Integer> current = new ArrayList<>();
    for (int i = 0; i < N; i++) {
      current.add(1);
    }

    while (true) {
      configurations.add(new ArrayList<>(current));

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

    return configurations;
  }

  private static double calculateReliability(List<Integer> configuration, List<Element> elements, int k1, int k2, int k3) {
    double R1 = 1.0;
    double R2 = 1.0;
    double R3 = 1.0;

    for (int i = 0; i < k1; i++) {
      int elementType = configuration.get(i);
      R1 *= (1 - elements.get(elementType - 1).Qkz);
    }

    for (int i = k1; i < k1 + k2; i++) {
      int elementType = configuration.get(i);
      R2 *= (1 - elements.get(elementType - 1).Qoj);
    }

    for (int i = k1 + k2; i < k1 + k2 + k3; i++) {
      int elementType = configuration.get(i);
      R3 *= (1 - elements.get(elementType - 1).Qoj);
    }

    return R1 * R2 * R3;
  }

}