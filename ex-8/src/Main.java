public class Main {
  private static char[] alphabet = {'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k', 'l', 'm', 'n', 'o', 'p', 'q', 'r', 's', 't', 'u', 'v', 'w', 'x', 'y', 'z'};

  public static void main(String[] args) {
    for (int i = 0; i < alphabet.length; i++) {
      System.out.println(i+1 + " : " + alphabet[i]);
    }
  }
}