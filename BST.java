public class BST {
	protected BSN root;
	public BST() {
		root = null;
	}

	public BST(Point[] points,int length){
		insertMediansToTheTree(points,  0, length);
	}
	public void insert(Point toAdd) {
		if (root == null){
			root = new BSN(toAdd);}
		else
			root.insert(toAdd);
	}
	
	public BSN remove(Point toRemove) {
		return root.remove(toRemove);
	}
	public int size() {
		if (root == null)
			return 0;
		else
			return root.size();
	}

	public boolean isEmpty() {
		return root == null;
	}

	public int height() {
		if (isEmpty())
			return -1;
		else
			return root.height();
	}
	private void insertMediansToTheTree(Point[] points,int start, int end){
		if((!((Math.abs(end-start))<=1))){//term for finding the medians
			insert(General.findMedian(points, start, end));
			int spot=General.findMedianSpot(points, start, end);
			insertMediansToTheTree(points,  spot, end);
			insertMediansToTheTree(points,  start, spot);
		}
		else if (start==0&&end==1)
			insert(General.findMedian(points, start, end));
	}
	
}