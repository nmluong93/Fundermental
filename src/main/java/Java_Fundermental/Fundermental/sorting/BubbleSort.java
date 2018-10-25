package Java_Fundermental.Fundermental.sorting;

public class BubbleSort {

	public static int[] bubleSort(int[] arr) {
		if (arr == null) {
			return null;
		}
		int length = arr.length;
		for (int i = 0; i < length; i++) {
			for (int j = 0; j < length - i - 1; j++) {
				if (arr[j] > arr[j + 1]) {
					int temp = arr[j + 1];
					arr[j + 1] = arr[j];
					arr[j] = temp;
				}
			}
		}
		return arr;
	}

	public static void bubleSortRecursive(int[] arr, int length) {
		if (length == 1) {
			return;
		}
		for (int i = 0; i < length; i++) {
			if (arr[i] > arr[i + 1]) {
				int temp = arr[i + 1];
				arr[i + 1] = arr[i];
				arr[i] = temp;
			}
		}
		bubleSortRecursive(arr, length - 1);
	}
}
