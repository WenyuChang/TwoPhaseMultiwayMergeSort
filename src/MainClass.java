import Util.FileOperator;


public class MainClass {
	public static void execute(FileOperator fileOp, int M)
	{
		System.out.println("######## Now Starting The System_Two Phase_Multiway Merge-Sort(M="+M+")... ########");
 		long startCreateFile = System.currentTimeMillis();
		
		try{
			fileOp.File_Split(M);
		}catch(Exception e){
			System.out.println("Splitting File Failed...");
		}
		
		try{
			fileOp.mergeSort(M);
		}catch(Exception e){
			System.out.println("Merge Temp Files Failed...");
		}
		
		long endCreateFile = System.currentTimeMillis();
		System.out.println("######## End The System_Two Phase_Multiway Merge-Sort(M="+M+"). Costs:" + (endCreateFile - startCreateFile) + "ms ########\n\n");
	}
	
	public static void main(String[] args) {
		int ITEM_COUNT = 100000;
		int TMP_FILE_COUNT = 20;
		
		FileOperator fileOp = new FileOperator(ITEM_COUNT);
		
		try{
			fileOp.createFile();
		}catch(Exception e){
			System.out.println("Creating File Failed...");
		}
		
		execute(fileOp, TMP_FILE_COUNT);
		
		//Double Buffering
		execute(fileOp, TMP_FILE_COUNT*2);
	}
}
