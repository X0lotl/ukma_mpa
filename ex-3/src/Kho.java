import java.util.Random;
import java.util.Scanner;

public class Kho {
  public static void main(String[] args) {
    Scanner scanner = new Scanner(System.in);

    System.out.print("Enter array length: ");
    int arrayLength = scanner.nextInt();

    int[] array = generateRandomIntArray(arrayLength);

    System.out.print("Your random array: ");
    outputArrayToConsole(array);

    System.out.println("\nSorting:");
    mergeSort(array, 0, arrayLength - 1);

    System.out.print("\nYour sorted array: ");
    outputArrayToConsole(array);
  }

  public static void outputArrayToConsole(int[] array) {
    StringBuilder stringBuilder = new StringBuilder();
    stringBuilder.append("[ ");
    for (int i : array) {
      stringBuilder.append(i).append(" ");
    }
    stringBuilder.append("]");
    System.out.println(stringBuilder);
  }

  public static int[] generateRandomIntArray(int arrayLength) {
    int[] array = new int[arrayLength];
    Random random = new Random();
    for (int i = 0; i < arrayLength - 1; i++) {
      array[i] = random.nextInt(arrayLength * 2) + 1;
    }

    return array;
  }

  public static void mergeSort(int[] array, int leftIndex, int rightIndex) {
    if (leftIndex < rightIndex) {
      int middleIndex = (leftIndex + rightIndex) / 2;

      //Split array to left and right
      mergeSort(array, leftIndex, middleIndex);
      mergeSort(array, middleIndex + 1, rightIndex);

      merge(array, leftIndex, middleIndex, rightIndex);

      outputArrayToConsole(array);
    }
  }

  public static int[] copyLeftPart(int[] array, int leftIndex, int leftPartLength) {
    int[] leftPartOfArray = new int[leftPartLength];
    System.arraycopy(array, leftIndex, leftPartOfArray, 0, leftPartLength);

    return leftPartOfArray;
  }

  public static int[] copyRightPart(int[] array, int middleIndex, int rightPartLength) {
    int[] rightPartOfArray = new int[rightPartLength];
    System.arraycopy(array, middleIndex + 1, rightPartOfArray, 0, rightPartLength);
    return rightPartOfArray;
  }

  public static void merge(int[] array, int leftIndex, int middleIndex, int rightIndex) {
    int leftPartLength = middleIndex - leftIndex + 1;
    int rightPartLength = rightIndex - middleIndex;

    int[] leftPartOfArray = copyLeftPart(array, leftIndex, leftPartLength);
    int[] rightPartOfArray = copyRightPart(array, middleIndex, rightPartLength);

    int i = 0, j = 0, k = leftIndex;
    while (i < leftPartLength && j < rightPartLength) {
      if (leftPartOfArray[i] <= rightPartOfArray[j]) {
        array[k] = leftPartOfArray[i];
        i++;
      } else {
        array[k] = rightPartOfArray[j];
        j++;
      }
      k++;
    }

    while (i < leftPartLength) {
      array[k] = leftPartOfArray[i];
      i++;
      k++;
    }

    while (j < rightPartLength) {
      array[k] = rightPartOfArray[j];
      j++;
      k++;
    }
  }
}
