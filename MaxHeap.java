import java.lang.Math;

public class MaxHeap {// building a heap

	private Point[] heapArr;
	private int heapSize; 

	public MaxHeap(Point[] arr, int size) {
		heapSize = size;
		heapArr = arr;
		for (int i = (int) (Math.floor(((heapSize) / 2)) - 1); i >= 0; i--) {
			maxHeapify(i);
		}

	}

	public Point[] getMaxArr() {
		return heapArr;
	}
	public int getSize() {
		return heapSize;
	}

	public void maxHeapify(int i) {// the important heapify function
		int largest;
		int l = left(i);
		int r = right(i);
		if (l < heapSize && General.pointYCompare(heapArr[l], heapArr[i]) > 0)
			largest = l;
		else
			largest = i;
		if (r < heapSize && General.pointYCompare(heapArr[r], heapArr[largest]) > 0)
			largest = r;
		if (largest != i) {
			pointSwap(i, largest);
			maxHeapify(largest);
		}
	}

	public Point max() {// return without delete the max point in the heap
		if (heapSize >= 0)
			return new Point(heapArr[0]);
		else
			throw new RuntimeException("MaxHeap is empty!");
	}

	public Point ExtractMax() {// returns and deletes the max point in the heap
		if (heapSize < 0)
			throw new RuntimeException("MaxHeap underflow!");
		Point max = new Point(heapArr[0]);
		heapArr[0] = new Point(heapArr[heapSize - 1]);
		heapSize = heapSize - 1;
		maxHeapify(0);
		return max;

	}

	public void heapIncreaseKey(int i, Point key) {

		if (General.pointYCompare(key, heapArr[i]) < 0)
			throw new RuntimeException("new point is smaller than the current");
		heapArr[i] = new Point(key);
		while (i > 0 && General.pointYCompare(heapArr[parent(i)], heapArr[i]) < 0) {
			pointSwap(i, parent(i));
			i = parent(i);
		}
	}

	public void pointSwap(int i, int j) {
		Point temp = new Point(heapArr[i]);
		heapArr[i] = new Point(heapArr[j]);
		heapArr[j] = new Point(temp);

	}

	public void insert(Point key) {
		heapSize = heapSize + 1;
		heapArr[heapSize - 1] = new Point(-1, -1);
		heapIncreaseKey(heapSize - 1, key);
	}

	public static int parent(int i) {
		return ((int) Math.floor(((i - 1) / 2)));
	}

	public static int left(int i) {
		return (2 * i) + 1;
	}

	public static int right(int i) {
		return (2 * i) + 2;
	}
	 public Point leftPointer(int i){
		if(heapArr[(2 * i) + 1]==null)
			return null;
		else return new Point(heapArr[(2 * i) + 1]);
	 }
	 public Point rightPointer(int i){
		 if(heapArr[(2 * i) + 2]==null)
				return null;
			else return new Point(heapArr[(2 * i) + 2]);
	 }
}
