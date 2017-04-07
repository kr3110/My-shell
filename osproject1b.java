import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.InputStreamReader;
import java.util.Scanner;

public class osproject1b {
	final static String os = System.getProperty("os.name");;
	public static class process {

		private ProcessBuilder pb;
		private Process process;
		
		//passing an array of string to process to the constructor
		process(String pr[]) {
		
			//no idea how to handle multiple arguments and put them in 
			//processBuilder();
			pb = new ProcessBuilder(pr[0], pr[1]);
			Exec();
		}
		//passing a string containing the process to run
		process(String pr) {
			pb = new ProcessBuilder(pr);
			Exec();
		}

		void Exec() {
			try {
				//start the process
				process = pb.start();
				InputStreamReader isr = new InputStreamReader(process.getInputStream());
				InputStreamReader esr = new InputStreamReader(process.getErrorStream());
				String line;
				//output input stream of the process
				BufferedReader br = new BufferedReader(isr);
				while ((line = br.readLine()) != null) {
					System.out.println(line);
				}
				//output error Stream of the process
				br = new BufferedReader(esr);
				while ((line = br.readLine()) != null) {
					System.out.println(line);
				}
				br.close();
			} catch (Exception e) {
				System.out.println("Command Couldn't execute");

			}
		}
	}

	public static class command extends Thread {
		public String line;
		public Scanner rm;
		command(String i,Scanner r){
			line=i;
			rm=r;
		}
		@Override
		public void run() {
			// TODO Auto-generated method stub
			if (!(line.trim().isEmpty())) {
				//split the command 
				String Process[] = line.split(" ");
				
				if (Process[0].equalsIgnoreCase("clr")) {
					try {
						if (os.contains("Windows")) {
							new process("clr");
						} else {
							new process("clear");
						}
					} catch (Exception e) {
						//
					}
				} else if (Process[0].equalsIgnoreCase("echo")) {
					for (int i = 1; i < Process.length; i++)
						System.out.print(Process[i] + " ");
					System.out.println();
				} else if (Process[0].equalsIgnoreCase("exit")) {
					System.exit(1);
				} else if (Process[0].equalsIgnoreCase("help")) {
					while (rm.hasNextLine()) {
						System.out.println(rm.nextLine());
					}
				} else if (Process[0].equalsIgnoreCase("pause")) {
					System.console().readPassword("");

				} else {
					process pr=null;
					if(Process.length>1)
						//send all array including the command
						 pr = new process(Process);
					else 
						//send only the command
						pr=new process(Process[0]);
				}
		}
		
	}
		}

	public static void main(String args[]) {
		File fl = new File("./readme.txt");
		Scanner rm = null;
		//check if readme if available, if not quit.
		try {
			rm = new Scanner(fl);
		} catch (FileNotFoundException e1) {
			System.err.println("File is not available. Program will exit");
			System.exit(0);
		}
		
		
		
		Scanner s = new Scanner(System.in);
		String line = "";
	
		
		
		
		while (true) {
			System.out.print("reke3636> ");
			line = s.nextLine();
			if (!(line.trim().isEmpty())) {
				//split the command 
				String Process[] = line.split(";");
				Thread thread[]=new Thread[Process.length];
				for(int i=0;i<thread.length;i++)
				{
					command c;
					thread[i] =new Thread(new command(Process[i],rm));
				}
				for(int i=0;i<thread.length;i++)
				{
					thread[i].run();
				}
			}
		}
	}
	}
	
