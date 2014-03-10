import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.util.Scanner;


public class Database {

	String filePath;
	Scanner fileReader;


	public Database()
	{

	}

	/*
	 * Initialises the database
	 * @param path String The file path of the database
	 * @return boolean Returns true if the database file could be found and false if not
	 */
	public boolean initDatabase(String path)
	{

		filePath = path;

		try 
		{
			fileReader = new Scanner( new File(filePath));
		} 
		catch (IOException e)
		{
			e.printStackTrace();
			return false;
		}

		return true;

	}

	// http://stackoverflow.com/questions/453018/number-of-lines-in-a-file-in-java
	private int countFileLines(String filename) throws IOException {
		InputStream is = new BufferedInputStream(new FileInputStream(filename));
		try {
			byte[] c = new byte[1024];
			int count = 0;
			int readChars = 0;
			boolean empty = true;
			while ((readChars = is.read(c)) != -1) {
				empty = false;
				for (int i = 0; i < readChars; ++i) {
					if (c[i] == '\n') {
						++count;
					}
				}
			}
			return (count == 0 && !empty) ? 1 : count;
		} 
		catch (IOException e)
		{
			e.printStackTrace();
			return -1;
		}
		finally 
		{
			is.close();
		}
	}



	public int countForms(String moduleCode){
		try
		{
			System.out.println("starts getAllForms method");
			int totalLineCount = countFileLines("forms.txt");
			int numForms = 0;
			for( int i=0; i<totalLineCount; i++ )
			{
				String line = fileReader.nextLine();
				if ( line.contains(moduleCode) == true ){
					numForms++;
				}
			}
			fileReader.close();
			System.out.println("not crashed yet...");
			return numForms;
		}
		catch (IOException e)
		{
			e.printStackTrace();
			return -1;
		}
	}
	
	public String[][] getAllForms(String moduleCode)
	{
		try
		{
			int numForms = countForms(moduleCode);
			System.out.println( numForms );
			String[][] moduleForms = new String[numForms][7];
			for( int i=0; i<countFileLines("forms.txt"); i++ )
			{
				String line = fileReader.nextLine();
				System.out.println("nothing");
				if ( line.contains(moduleCode)){
					String[] parts = line.split(",");
					parts[7] = parts[7].substring(0, parts[7].length()-1);
					System.out.println( parts[7]);
					//[0] moduleCode; [1] paperName; [2] ELODeadline; [3] EMDeadline; 
					//[4] MLDeadline; [5] IMDeadline; [6] EM_ID; [7] IM_ID
					moduleForms[i][0] = parts[0];
					moduleForms[i][1] = parts[1];
					moduleForms[i][2] = parts[2];
					moduleForms[i][3] = parts[3];
					moduleForms[i][4] = parts[4];
					moduleForms[i][5] = parts[5];
					moduleForms[i][6] = parts[6];
					moduleForms[i][7] = parts[7];
				}
			}
			fileReader.close();
			System.out.println("forms table saved");
			return moduleForms;
		}
		catch (IOException e)
		{
			e.printStackTrace();
			return null;
		}
	}
	




	/*
	 * Resets the file reader so that the file can be read again
	 * (I couldn't get the normal reset function on the Scanner to work)
	 */
	public void resetFileReader()
	{
		fileReader.close();
		try 
		{
			fileReader = new Scanner( new File(filePath));

		} 
		catch (IOException e)
		{
			e.printStackTrace();
		}
	}

	/*
	 * Closes all open I/O objects
	 * MUST be called when database is finished being used
	 */
	public void close()
	{
		fileReader.close();
	}

	public static void main(String[] args)
	{
		/*Database d = new Database();
		d.initDatabase("forms.txt");
		d.insertAssessmentPaper("cm2304", "cw1", "ELODate1", "EMDeadline1", "MLDeadline1", "IMDeadline1", "1001001", "2002002");
		d.insertAssessmentPaper("cm2304", "cw2", "ELODate2", "EMDeadline2", "MLDeadline2", "IMDeadline2", "1001001", "2002002");
		d.insertAssessmentPaper("cm2304", "cw3", "ELODate3", "EMDeadline3", "MLDeadline3", "IMDeadline3", "1001001", "2002002");
		d.insertAssessmentPaper("cm2304", "cw4", "ELODate4", "EMDeadline4", "MLDeadline4", "IMDeadline4", "1001001", "2002002");
		
		d.insertStaff("Simon", "Titcomb", "LOLNOPE@nah.com", "2345678986", "nahmate", "TitS");
		d.insertStaff("Aly", "Sheriff", "kingtut@pharoahpower.com", "11111", "egyptrules", "Tut");
		d.insertStaff("Mez", "Gangbanger", "kingofrape@ianwatkins.com", "5678987", "kids", "Kids101");
		
		String a = d.getStaffFirstName("staffID", "TitS");
		String b = "";
		System.out.println(a);
		System.out.println(b);
		String mez = d.getStaffEmail("first name", "mez");
		System.out.println(mez);
		System.out.println(d.deleteStaff("staffID","Tut"));
		
		d.close();

		System.out.println("Completed.");*/
		
		/*Database d = new Database();
		d.initDatabase("users.txt");

		System.out.println("Started.");
		
		String[][] a = d.getAllStaff();
		for(int i=0; i < a.length; i++)
		{
			for(int j=0; j < 6; j++)
			{
				System.out.print(a[i][j]+" ");
			}
			
			System.out.println(" ");
		}

		System.out.println("Completed.");
		
		d.close();*/
	

	}

}
