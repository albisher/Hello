import java.io.FileOutputStream;
import java.io.PrintWriter;

public class CMDLine {
	public static Boolean setTime(String sDate) {
		// need to check time format is it 
		// yyyymmdd hh:mm:ssAM
		Boolean result = true;
		String[] cmd = {"~", "-c", "date +%Y%m%d%T%p", "-s ","\"", sDate, "\"" };
		//
		try {
			runCMD(cmd);
		} catch (Exception e) {
			result = false;
			e.printStackTrace();
			System.out.println("ERROR.RUNNING.CMD in setTime");
		}
		return result;
	}
	
//	public static Boolean runCMD(String cmd) {
//		Boolean result = true;
//		Runtime run = Runtime.getRuntime();
//		Process p = null;
//		//
//		try {
//			// open or run executable command
//			p = run.exec(cmd);
//			
//			p.getErrorStream();
//			p.waitFor();
//
//		} catch (Exception e) {
//			result = false;
//			e.printStackTrace();
//			System.out.println("ERROR.RUNNING.CMD");
//		} finally {
//			// remember to close the stream, to release precious BBB resources
//			p.destroy();
//		}
//		return result;
//	}
	
	public static Boolean runCMD(String[] cmd){
		Boolean result = true;
		
		Process p = null;
		try {
			// open the export file, and write to it the gpio number you
			// want to export
			p = Runtime.getRuntime().exec(cmd);

		} catch (Exception e) {
			result = false;
			throw new RuntimeException(e);
		} finally {
			// remember to close the stream, to release precious BBB
			// resources
			try {
				p.destroy();
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	
		
		return result;
	}
	
}
