package chongxiang.utilities;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import org.testng.Assert;

public class Test_UnZip {

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception {
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void test()throws Exception {
		try {
			
			String s_File = System.getProperty("user.dir") + "/Accounts.txt.zip";
			String s_Destination = System.getProperty("user.dir");
			String s_Password = "";
			
			Utilities.UnZipFile(s_File, s_Destination, s_Password);
			
			Assert.assertTrue(true);
			
		}catch(Exception e) {
			Assert.assertTrue(false);
		}

	}

}
