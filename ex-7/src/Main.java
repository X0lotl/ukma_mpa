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

public class Main{

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

    List<List<Integer>> configurations = new ArrayList<>();
    generateConfigurations(configurations, new ArrayList<>(), K, N);

    double maxReliability = 0.0;
    List<Integer> bestConfiguration = new ArrayList<>();

    for (List<Integer> configuration : configurations) {
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
    System.out.print("Конфігурація з максимальною надійністю: ");
    for (int i : bestConfiguration) {
      System.out.print(i + " ");
    }

    PrintWriter outputFile = new PrintWriter("output.txt");
    outputFile.println("Задана структура: " + k1 + " " + k2 + " " + k3);
    outputFile.println("Кількість різнотипних елементів: " + K);
    outputFile.println("Кількість різних конфігурацій: " + configurations.size());
    outputFile.println("Максимальна надійність: " + maxReliability);
    outputFile.print("Конфігурація з максимальною надійністю: ");
    for (int i : bestConfiguration) {
      outputFile.print(i + " ");
    }
    outputFile.close();
  }

  private static void generateConfigurations(List<List<Integer>> configurations, List<Integer> current, int K, int N) {
    if (current.size() == N) {
      configurations.add(new ArrayList<>(current));
      return;
    }

    for (int i = 1; i <= K; i++) {
      current.add(i);
      generateConfigurations(configurations, current, K, N);
      current.remove(current.size() - 1);
    }
  }
}
