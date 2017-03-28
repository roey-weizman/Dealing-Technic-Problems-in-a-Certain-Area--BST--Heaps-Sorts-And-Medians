public class BSN {
	protected Point data;
	protected BSN left;
	protected BSN right;
	protected int smalllSons;// how many nodes are smaller than you
	protected int sumOfY;// sum of y's of you and your sons

	public BSN(Point data) {
		this.data = new Point(data);
		left = null;
		right = null;
		sumOfY = data.getY();
		smalllSons = 1;
	}

	public BSN(Point data, BSN left, BSN right) {
		this.data = new Point(data);
		this.left = left;
		this.right = right;
		sumOfY = data.getY();
		smalllSons = 1;

	}

	public void insert(Point toAdd) {
		if (General.pointXCompare(toAdd, (Point) (this.data)) > 0) {
			if (right == null)
				right = new BSN(toAdd);
			else
				right.insert(toAdd);
		}
		if (General.pointXCompare(toAdd, (Point) (this.data)) < 0) {
			this.smalllSons = this.smalllSons + 1;
			this.sumOfY=this.sumOfY+toAdd.getY();
			if (left == null)
				left = new BSN(toAdd);
			else
				left.insert(toAdd);
		}
	}

	public BSN remove(Point toRemove) {
		if (General.pointXCompare(data, toRemove)>0) {
			if (left != null){
				left = ((BSN) left).remove(toRemove);
				this.smalllSons--;
				if(left!=null)
					this.sumOfY=((BSN)left).sumOfY+this.data.getY();
				else
					this.sumOfY=this.data.getY();
			}
		} else if (General.pointXCompare(data, toRemove)<0) {
			if (right != null)
				right = ((BSN) right).remove(toRemove);
		} else {// need to remove the data in this node
			if (left == null || right == null) { // the base cases...
				if (left == null)
					return right;
				else
					return left;
			} else { // this node has two children
				this.sumOfY=this.sumOfY-this.data.getY();
				data = ((BSN) right).findMin().data;
				right = ((BSN) right).remove(data);
				this.sumOfY=this.sumOfY+this.data.getY();
			}
		
		}
		return this;
	}
	
	public BSN findMin(){
		BSN currNode = this;
		while (currNode.left != null){
		currNode = currNode.left;
		}
		return currNode;
		}

	public int size() {
		int res = 1;
		if (left != null)
			res = res + left.size();
		if (right != null)
			res = res + right.size();
		return res;
	}

	public int height() {
		int res = 0;
		if (left != null)
			res = left.height() + 1;
		if (right != null)
			res = Math.max(res, right.height() + 1);
		return res;
	}

}
