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
