import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;

public class GPIO {
	public static void pinMode(Integer pin, String s) {
		// built using
		// http://project-bonsai.org/accessing-beaglebone-black-gpio-from-java-part-2-using-java/
		// timing this function provided the following delay as the longest
		// response
		// real 0m0.811s
		// user 0m0.645s
		// sys 0m0.154s
		if (s == "out" || s == "in") {
			FileOutputStream out = null;
			try {
				// open the export file, and write to it the gpio number you
				// want to export
				out = new FileOutputStream("/sys/class/gpio/export");
				PrintWriter pw = new PrintWriter(out);
				pw.print(pin);
				pw.close();

				// now access to the just created gpio folder, and print the
				// direction, in/out, default is in
				out = new FileOutputStream("/sys/class/gpio/gpio" + pin
						+ "/direction");
				pw = new PrintWriter(out);
				pw.print(s);
				pw.close();

			} catch (Exception e) {
				throw new RuntimeException(e);
			} finally {
				// remember to close the stream, to release precious BBB
				// resources
				try {
					out.close();
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
	}

	public static Boolean isAvailable(Integer pin) {
		// timing this function provided the following delay as the longest
		// response
		// real 0m1.042s
		// user 0m0.809s
		// sys 0m0.197s
		//
		Boolean result = false;
		String s;
		Runtime run = Runtime.getRuntime();
		Process p = null;
		String cmd = "cat /sys/kernel/debug/pinctrl/44e10800.pinmux/pinmux-pins | grep ";
		cmd = cmd + pin.toString();
		//
		try {
			// open or run executable command
			p = run.exec(cmd);

			// read the results and check condition to change to true if
			// available
			BufferedReader br = new BufferedReader(new InputStreamReader(
					p.getInputStream()));
			while ((s = br.readLine()) != null) {
				s = br.readLine();
				if (s.contains(pin.toString())) {
					if (s.contains("UNCLAIMED")) {
						result = true;
					}
				}
			}

			p.getErrorStream();
			p.waitFor();

		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("ERROR.RUNNING.CMD");
		} finally {
			// remember to close the stream, to release precious BBB resources
			p.destroy();
		}
		return result;
	}

	public static void digitalWrite(Integer pin, Boolean b) {
		// built using
		// http://project-bonsai.org/accessing-beaglebone-black-gpio-from-java-part-2-using-java/
		// timing this function provided the following delay as the longest

		FileOutputStream out = null;
		try {

			out = new FileOutputStream("/sys/class/gpio/gpio" + pin + "/value");
			PrintWriter pw = new PrintWriter(out);
			pw = new PrintWriter(out);
			pw.print(b); // 1=HIGH, 0=LOW
			pw.close();

		} catch (Exception e) {
			throw new RuntimeException(e);
		} finally {
			// remember to close the stream, to release precious BBB
			// resources
			try {
				out.close();
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

	public static Integer digitalRead(Integer pin) {
		FileInputStream in = null;
		int result = 0;
		try {
			in = new FileInputStream("/sys/class/gpio/gpio" + pin + "/value");
			result = in.read() - 48; // this is probably wrong, char to 0/1
		} catch (Exception e) {
			throw new RuntimeException(e);
		} finally {
			// remember to close the stream, to release precious BBB
			// resources
			try {
				in.close();
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

		return result;
	}

	public static void unexportPin(Integer pin) {
		FileOutputStream out = null;
		try {
			// open the export file, and write to it the gpio number you
			// want to export
			out = new FileOutputStream("/sys/class/gpio/unexport");
			PrintWriter pw = new PrintWriter(out);
			pw.print(pin);
			pw.close();
		} catch (Exception e) {
			throw new RuntimeException(e);
		} finally {
			// remember to close the stream, to release precious BBB
			// resources
			try {
				out.close();
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	
}
