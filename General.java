
public class General {

	public static int pointYCompare(Point p1, Point p2) {
		if (p1.getY() > p2.getY()) // compare by Y's values
			return 1;
		else if (p1.getY() < p2.getY())
			return -1;
		else {// if Y's are are equal compare by X's values
			if (p1.getX() > p2.getX())
				return 1;
			else if (p1.getX() < p2.getX())
				return -1;
			else
				return 0;
		}
	}

	public static int pointXCompare(Point p1, Point p2) {
		if (p1.getX() > p2.getX()) // compare by X's values
			return 1;
		else if (p1.getX() < p2.getX())
			return -1;
		else {// if X's are are equal compare by Y's values
			if (p1.getY() > p2.getY())
				return 1;
			else if (p1.getY() < p2.getY())
				return -1;
			else
				return 0;
		}
	}

	public static Point findMedian(Point[] arr, int start, int end) {//the median point
		int mid = start + (end - start) / 2;
		return new Point(arr[mid]);

	}

	public static int findMedianSpot(Point[] arr, int start, int end) {// the index of median point
		int mid = start + (end - start) / 2;
		return mid;
	}

	public static Point[] countingSort(Point[] a,int size) {//sorting in O(n)
		Point[] b = new Point[a.length];
		for (int i = 0; i < size; i++)
			b[a[i].getX()] = new Point(a[i].getX(), a[i].getY());
		return b;
	}

	/*public static void main(String args[]){
	Point p1 = new Point(1, 2);
	Point p2 = new Point(0, 2);
	Point p3 = new Point(2, 2);
	Point p4 = new Point(7, 2);
	Point p5 = new Point(3, 2);
	Point p6 = new Point(6, 2);
	Point p7 = new Point(4, 2);
	Point p8 = new Point(5, 2);
	Point p9 = new Point(9, 2);
	Point p10 = new Point(8, 2);
Point[] points={new Point(p1),new Point(p2),new Point(p3),new Point(p4),new Point(p5),new Point(p6),new Point(p7),new Point(p8),new Point(p9),new Point(p10)};points=General.countingSort(points);for(
	int i = 0;i<points.length;i++)

	{
		System.out.println(points[i].toString());
	}
}*/
}