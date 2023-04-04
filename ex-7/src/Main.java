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

public class Main {

  public static void main(String[] args) throws FileNotFoundException {
    File inputFile = new File("input.txt");
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

    List<Integer> bestConfiguration = new ArrayList<>();
    int[] configurationCounter = new int[1];
    double[] maxReliability = new double[1];
    int[] bestConfigurationIndex = new int[1];

    generateConfigurations(bestConfiguration, new ArrayList<>(), elements, K, N, k1, k2, k3, maxReliability, configurationCounter, bestConfigurationIndex, 0);

    System.out.println("Задана структура: " + k1 + " " + k2 + " " + k3);
    System.out.println("Кількість різнотипних елементів: " + K);
    System.out.println("Кількість різних конфігурацій: " + configurationCounter[0]);
    System.out.println("Максимальна надійність: " + maxReliability[0]);
    System.out.println("Конфігурація з максимальною надійністю (номер конфігурації): " + bestConfigurationIndex[0]);

    PrintWriter outputFile = new PrintWriter("output.txt");
    outputFile.println("Задана структура: " + k1 + " " + k2 + " " + k3);
    outputFile.println("Кількість різнотипних елементів: " + K);
    outputFile.println("Кількість різних конфігурацій: " + configurationCounter[0]);
    outputFile.println("Максимальна надійність: " + maxReliability[0]);
    outputFile.println("Конфігурація з максимальною надійністю (номер конфігурації): " + bestConfigurationIndex[0]);
    outputFile.close();
  }

  private static void generateConfigurations(List<Integer> bestConfiguration, List<Integer> current, List<Element> elements, int K, int N, int k1, int k2, int k3, double[] maxReliability, int[] configurationCounter, int[] bestConfigurationIndex, int currentConfigurationIndex) {
    if (current.size() == N) {
      configurationCounter[0]++;
      double reliability = calculateReliability(current, elements, k1, k2, k3);
      if (reliability > maxReliability[0]) {
        maxReliability[0] = reliability;
        bestConfigurationIndex[0] = currentConfigurationIndex;
        bestConfiguration.clear();
        bestConfiguration.addAll(current);
      }
      return;
    }

    for (int i = 1; i <= K; i++) {
      current.add(i);
      generateConfigurations(bestConfiguration, current, elements, K, N, k1, k2, k3, maxReliability, configurationCounter, bestConfigurationIndex, currentConfigurationIndex + 1);
      current.remove(current.size() - 1);
    }
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

