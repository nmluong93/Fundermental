package Java_Fundermental.Fundermental.sorting;

public class SelectionSort {

	public static int[] sort(int[] unsortedCollection) {
		printOutArray(unsortedCollection);
		if (unsortedCollection == null || unsortedCollection.length == 1 || unsortedCollection.length == 0) {
			return unsortedCollection;
		}
		int minIndex = 0;
		for (int k = 0; k < unsortedCollection.length - 1; k++) {
			minIndex = k;
			for (int i = k + 1; i < unsortedCollection.length; i++) {
				int secondIndex = i;
				if (unsortedCollection[minIndex] > unsortedCollection[secondIndex]) {
					minIndex = secondIndex;
				}
			}
			int temp = unsortedCollection[minIndex];
			unsortedCollection[minIndex] = unsortedCollection[k];
			unsortedCollection[k] = temp;
		}
		System.err.println("After sort");
		printOutArray(unsortedCollection);
		return unsortedCollection;
	}

	private static void printOutArray(int[] un) {
		for (int i = 0; i < un.length; i++) {
			System.out.println(un[i]);
		}
	}

}
