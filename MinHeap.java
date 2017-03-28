import java.lang.Integer;

public class MinHeap {// building a heap

	private Point[] heapArr;
	private int heapSize;
	

	public MinHeap(Point[] arr, int size) {
		heapSize = size;
		heapArr = arr;
		for (int i = (int) (Math.floor(((heapSize) / 2)) - 1); i >= 0; i--) {
			minHeapify(i);
		}

	}

	public int getSize() {
		return heapSize;
	}

	public void minHeapify(int i) {// the important heapify function
		int smallest;
		int l = left(i);
		int r = right(i);
		if (l < heapSize && General.pointYCompare(heapArr[l], heapArr[i]) < 0)
			smallest = l;
		else
			smallest = i;
		if (r < heapSize && General.pointYCompare(heapArr[r], heapArr[smallest]) < 0)
			smallest = r;
		if (smallest != i) {
			pointSwap(i, smallest);
			minHeapify(smallest);
		}
	}

	public Point min() {// return without delete the min point in the heap
		if (heapSize >= 0)
			return new Point(heapArr[0]);
		else
			throw new RuntimeException("MinHeap is empty!");
	}

	public Point ExtractMin() {// returns and deletes the min point in the heap
		if (heapSize < 0)
			throw new RuntimeException("MinHeap underflow!");
		Point min = new Point(heapArr[0]);
		heapArr[0] = new Point(heapArr[heapSize - 1]);
		heapSize = heapSize - 1;
		minHeapify(0);
		return min;

	}

	public void heapDecreaseKey(int i, Point key) {

		if (General.pointYCompare(key, heapArr[i]) > 0)
			throw new RuntimeException("new point is greater than the current");
		heapArr[i] = new Point(key);
		while (i > 0 && General.pointYCompare(heapArr[parent(i)], heapArr[i]) > 0) {
			pointSwap(i, parent(i));
			i = parent(i);
		}
	}

	public void pointSwap(int i, int j) {
		Point temp = new Point(heapArr[i]);
		heapArr[i] = new Point(heapArr[j]);
		heapArr[j] = new Point(temp);

	}
	public Point[] getMinArr() {
		return heapArr;
	}

	public void insert(Point key) {
		heapSize = heapSize + 1;
		heapArr[heapSize - 1] = new Point(Integer.MAX_VALUE, Integer.MAX_VALUE);
		heapDecreaseKey(heapSize - 1, key);
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
