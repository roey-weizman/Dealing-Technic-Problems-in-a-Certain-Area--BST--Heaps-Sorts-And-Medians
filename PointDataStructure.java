
public class PointDataStructure implements PDT {
	private Point[] mainArr;
	private int mainSize;
	private MaxHeap lowMedian;
	private MinHeap largeMedian;
	private BST myTree;
	private Point theMedian;
	private int initialLength;//for remove median
	private int avereagreRenainder;//for average

	//////////////// DON'T DELETE THIS CONSTRUCTOR ////////////////
	public PointDataStructure(Point[] points, Point initialYMedianPoint) {
		mainArr = new Point[(int) (points.length + Math.ceil(10 * ((Math.log(points.length)) / Math.log(2))))];// size	 of array with	 spare
		mainSize = points.length;
		initialLength=points.length;
		avereagreRenainder=0;
		for (int i = 0; i < points.length; i++)// initialize the field
			mainArr[i] = new Point(points[i]);
		theMedian = initialYMedianPoint;
		boolean ans = true;
		for (int j = 0; j < mainSize - 1 && ans; j++) {// checking if its// project a
			if (General.pointXCompare(mainArr[j], mainArr[j + 1]) > 0)
				ans = false;
		}
		if (!ans) {// if not sorting it by point X's values
			mainArr = General.countingSort(mainArr,mainSize);
		}
		Point[] lowerThanMedian = new Point[(int) Math
				.ceil(((mainArr.length) / 2) + 5*((Math.log(mainSize)) / Math.log(2)))];// array
																						// for
																						// max
																						// heap
		Point[] largerThanMedian = new Point[(int) Math
				.ceil(((mainArr.length) / 2) + 5*((Math.log(mainSize)) / Math.log(2)))];// array
																						// for
																						// min
																						// heap
		int countLow = 0;
		int countLarge = 0;
		for (int i = 0,k=0,j=0; i < mainSize; i++) {// check who is greater or smaller than the median and divide it the corresponding array
			if(theMedian.equals(mainArr[i])){}
			else if (General.pointYCompare(mainArr[i], theMedian) > 0) {
				largerThanMedian[k] = new Point(mainArr[i]);
				countLarge++;k++;
			} // counting num of points
			else if (General.pointYCompare(mainArr[i], theMedian) < 0) {
				lowerThanMedian[j] = new Point(mainArr[i]);
				countLow++;j++;
			}
		}
		lowMedian = new MaxHeap(lowerThanMedian, countLow );
		largeMedian = new MinHeap(largerThanMedian, countLarge );// creating
																	// the heaps
		if (largeMedian.getSize() < lowMedian.getSize())
			largeMedian.insert(theMedian);
		else if (largeMedian.getSize() == lowMedian.getSize())
			lowMedian.insert(theMedian);
		myTree = new BST(mainArr, mainSize);// creating the tree
		// TODO
	}

	@Override
	public void addPoint(Point point) {
		Point med = new Point(theMedian);
		mainArr[mainSize] = new Point(point);//insert point to the big array
		mainSize++;
		myTree.insert(point);// insert point to the tree
		//cases to sort the new point
		if (point.getY() < theMedian.getY()) {//case 1
			lowMedian.insert(point);
			med = new Point(ExtractMedian());
			if (lowMedian.getSize() == largeMedian.getSize()) {///updating the median according to heaps proportions
				largeMedian.insert(med);
			} else if (lowMedian.getSize() > largeMedian.getSize()) {
				largeMedian.insert(med);
				theMedian=new Point(lowMedian.max());//update median
			}
		} else if (point.getY() > theMedian.getY()) {//case 2
			largeMedian.insert(point);
			med = new Point(ExtractMedian());
			if (lowMedian.getSize() == largeMedian.getSize()) {
				lowMedian.insert(med);
				theMedian=new Point(largeMedian.min());
			} else if (lowMedian.getSize() > largeMedian.getSize()) {
				largeMedian.insert(med);
				theMedian = new Point(lowMedian.max());//update median
			}
		}
	}

	@Override//to be checked
	public Point[] getPointsInRange(int XLeft, int XRight) {
		int start=searchIndex(mainArr, XLeft);int counter=0;
		Point[] xRangepoints=new Point[XRight-XLeft+1];
		int i=start; boolean isLoopOver=false; int k=0;
		while(i<initialLength&&!isLoopOver&&i!=-1){
			while(mainArr[i]==null)
				i++;
			if((mainArr[i].getX()<=XRight)&&i<initialLength){
				xRangepoints[k]=new Point(mainArr[i]);
				i++;k++; counter++;
			} else isLoopOver=true;
		}
		int j=initialLength;
		while(j<mainSize){
			while(mainArr[j]==null)
				j++;
			if((mainArr[j].getX()<=XRight && mainArr[j].getX()>=XLeft)&&(j<mainSize)){
				xRangepoints[k]=new Point(mainArr[i]);	
				i=i+1;k=k+1;counter++;
			}
			j=j+1;
		}
		Point[]finalArr= new Point[counter];
		for(int p=0;p<finalArr.length;p++){
			finalArr[p]=new Point(xRangepoints[p]);
		}
		return finalArr;
	
		
		
		
		// TODO Auto-generated method stub
		
		
	} 

	@Override
	public int numOfPointsInRange(int XLeft, int XRight) {
		int sumA=nodeSumSearch(myTree.root,XLeft,0);
		int sumB=nodeSumSearch(myTree.root,XRight,0);
		if(searchByX(myTree.root, XLeft)==true)
			return (sumB-sumA)+1;
		else return (sumB-sumA);
	}

	public int nodeSumSearch(BSN vertix,int xToSearch,int sum){
		if(vertix!=null){
			if(vertix.data.getX()>xToSearch)
				return nodeSumSearch(vertix.left, xToSearch, sum);
			else if(vertix.data.getX()<xToSearch)
				return nodeSumSearch(vertix.right, xToSearch, sum+vertix.smalllSons);
			else return(sum+vertix.smalllSons);
		}
		else return sum;
			
	}
	@Override
	public double averageHeightInRange(int XLeft, int XRight) {
		int SumA=YsumSearch(myTree.root,XLeft,0,true); int sumB=YsumSearch(myTree.root,XRight,0,false);
		double finalSum=sumB-SumA+avereagreRenainder;
		double finalNum=numOfPointsInRange(XLeft, XRight);
		if(finalNum==0)
			return 0;
		else return (double)(finalSum/finalNum);
	}

	public int YsumSearch(BSN vertix, int xToSearch, int sum, boolean ans) {
		if (vertix != null) {
			if (vertix.data.getX() > xToSearch)
				return YsumSearch(vertix.left, xToSearch, sum,ans);
			else if (vertix.data.getX() < xToSearch)
				return YsumSearch(vertix.right, xToSearch, sum + vertix.sumOfY,ans);
			else {
				if(ans)
					avereagreRenainder=vertix.data.getY();
				return (sum + vertix.sumOfY);
			}
		} else
			return sum;

	}
	@Override
	public void removeMedianPoint() {
		String s=whereIsMedian();
		if(s.equals("large")){//the median is in the minheap
			Point med=new Point(ExtractMedian());
			theMedian=lowMedian.max();
			myTree.remove(med);//remove the median from the tree
			///remove median from the big array
			int index=searchIndex(mainArr, med.getX());///need to be checked
			if(index==med.getX())//check till the n'th spot
				mainArr[index]=null;
			else for(int i=initialLength;i<mainSize;i++){//if not found search beyond
				if(mainArr[i].getX()==med.getX()){
					mainArr[i]=null;
					break;}
			
			}
			
		}
		else if(s.equals("low")){//Symmetrically
			Point med=new Point(ExtractMedian());
			theMedian=largeMedian.min();
			myTree.remove(med);
			int index=searchIndex(mainArr, med.getX());///need to be checked
			if(index==med.getX())
				mainArr[index]=null;
			else for(int i=initialLength;i<mainSize;i++){
				if(mainArr[i].getX()==med.getX()){
					mainArr[i]=null;
					break;}
			
			}
		}
		
			
		
		
		// TODO Auto-generated method stub

	}

	@Override
	public Point[] getMedianPoints(int k) {
		int z=k;
		Point[] medians=new Point[k]; int i=0;//index for medians array
		Point[]low=new Point[k];   MaxHeap low2=new MaxHeap(low, 0);//create an empty max heap
		Point[]large=new Point[k]; MinHeap large2=new MinHeap(large, 0);//create an empty min heap
		int indexOfLow2=0;//index to find the sons in the original array
		int indexOfLarge2=0;
		large2.insert(new Point (largeMedian.min()));
		low2.insert(new Point(lowMedian.max()));
		if(whereIsMedian().equals("large")){
			while (((low2.getSize() != 0 || large2.getSize() != 0)) && k > 0) {
				if (k > 0 && large2.getSize() != 0) {
					while ((low2.getSize() == large2.getSize())&&large2.getSize() != 0&&i<z) {
						medians[i] = new Point(large2.ExtractMin());
						i++;	k--;
						if ((MinHeap.left(indexOfLarge2) < largeMedian.getSize())&&k>0) {
							large2.insert((largeMedian.getMinArr())[MinHeap.left(indexOfLarge2)]);
						}
						if (MinHeap.right(indexOfLarge2) < largeMedian.getSize()&&k>0) {
							large2.insert((largeMedian.getMinArr())[MinHeap.right(indexOfLarge2)]);
						}
						indexOfLarge2++;
					}
				}
				if (k > 0 && low2.getSize() != 0) {
					while (low2.getSize() < large2.getSize()&&low2.getSize() != 0&&i<z) {//change shivion
						medians[i] = new Point(low2.ExtractMax());
						i++;	k--;
						if (MaxHeap.left(indexOfLow2) < lowMedian.getSize()&&k>0) {
							low2.insert((lowMedian.getMaxArr())[MaxHeap.left(indexOfLow2)]);
						}
						if (MaxHeap.right(indexOfLow2) < lowMedian.getSize()&&k>0) {
							low2.insert((lowMedian.getMaxArr())[MaxHeap.right(indexOfLow2)]);
						}
						indexOfLow2++;
					}
				}
			}
		}
		else{ /*if(whereIsMedian().equals("low"))*/    
			boolean ans=false;
			while (((low2.getSize() != 0 || large2.getSize() != 0)) && k > 0) {
				if (k > 0 && (low2.getSize() != 0)) {
					while (((ans&&low2.getSize() > large2.getSize())||(!ans&&low2.getSize() >= large2.getSize()))&&low2.getSize() != 0&&i<z) {
						ans=true;
						medians[i] = new Point(low2.ExtractMax());	i++;k--;
						if (MaxHeap.left(indexOfLow2) < lowMedian.getSize() && k > 0) {
							low2.insert((lowMedian.getMaxArr())[MaxHeap.left(indexOfLow2)]);
						}
						if (MaxHeap.right(indexOfLow2) < lowMedian.getSize() && k > 0) {
							low2.insert((lowMedian.getMaxArr())[MaxHeap.right(indexOfLow2)]);
						}
						indexOfLow2++;
					}
				}
				if(k>0&&large2.getSize()!=0){
					while (low2.getSize() == large2.getSize()&&large2.getSize() != 0&&i<z) {
					medians[i]=new Point(large2.ExtractMin()); i++; k--;
					if(MinHeap.left(indexOfLarge2)<largeMedian.getSize()&&k>0){
						large2.insert((largeMedian.getMinArr())[MinHeap.left(indexOfLarge2)]);
					}
					if(MinHeap.right(indexOfLarge2)<largeMedian.getSize()&&k>0){
						large2.insert((largeMedian.getMinArr())[MinHeap.right(indexOfLarge2)]);
					}
					indexOfLarge2++;
				}
				}
			}
		}
		int cnt=0;
		for(int j=0;j<medians.length;j++){
			if(medians[j]!=null)
				cnt++;
			else break;
		}
		Point[] finalMedians=new Point[cnt];
		for(int p=0;p<cnt;p++){
			finalMedians[p]=new Point(medians[p]);
		}
		// TODO Auto-generated method stub
		return finalMedians;
	}

	@Override
	public Point[] getAllPoints() {
		Point[] res=new Point[largeMedian.getSize()+lowMedian.getSize()];
		for(int i=0,k=0;i<mainSize;i++){
			if(mainArr[i]!=null){
				res[k]=new Point(mainArr[i]); k++;
			}
		}
		
		// TODO Auto-generated method stub
		return res;
	}

	// TODO: add members, methods, etc.
	private Point ExtractMedian() {
		Point med = new Point(theMedian);// just for initializing
		if (whereIsMedian().equals("large"))
			med = new Point(largeMedian.ExtractMin());
		else if (whereIsMedian().equals("low"))
			med = new Point(lowMedian.ExtractMax());
		return med;

	}

	private String whereIsMedian() {
		if (largeMedian.min().equals(theMedian))
			return "large";
		else if (lowMedian.max().equals(theMedian))
			return "low";
		else
			throw new RuntimeException("The median wasnt found!");
	}
	
	
	/////****there are bugs in this function for sure****///////
	private int searchIndex(Point[] arr,int chosen){
		int low=0; int high=initialLength-1;
		int result=-1; //the minimum index that his value higher or equals to chosen.
		int mid=(int)Math.floor((high+low+1)/2); //middle index
		while(((result!=-1&&arr[result].getX()!=chosen)||(result==-1))&&high>=low){
			if(arr[mid].getX()>chosen){
				result=mid;
				high=mid-1;
				mid=(int)Math.floor((high+low+1)/2);
			}
			else if(arr[mid].getX()<chosen){
				low=mid+1;
				mid=(int)Math.floor((high+low+1)/2);
			} else result=mid;
		}
		return result;
	}
	public boolean searchByX(BSN vertix,int value){
		if(vertix==null)
			return false;
		else if(vertix.data.getX()>value)
			return searchByX(vertix.left, value);
		else if (vertix.data.getX()<value)
			return searchByX(vertix.right, value);
		else return true;
	}
}


