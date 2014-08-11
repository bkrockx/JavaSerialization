import java.io.*;
import java.util.*;

public class SerializationDemo22 {
	public static void main(String[] args) throws IOException, ClassNotFoundException{
		
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		File directory = Myclass.getDirectory();
		
		while(true){
			
			System.out.println("Welcome To Employee Management System");
			System.out.println("Enter your choice");
			
			//Below code lines shows options to the user using the file "config.properties"
			//prop object has been created to write to the file config.properties.
			Properties prop = new Properties();
			OutputStream output  = null;
			
			try{
				output = new FileOutputStream("config.properties");
				
				prop.setProperty("Enter_1","show employee");
				prop.setProperty("Enter_2","Create new employee");
				prop.setProperty("Enter_3","Delete employee");
				prop.setProperty("Enter_4","quit");
				
				prop.store(output,null);
			
			}catch(IOException e){
				e.printStackTrace();
			}
			
			//property object has been created to retrieve values for each key and show the options to the user
			Properties property = new Properties();
			InputStream input = null;
			
			try{
				input = new FileInputStream("config.properties");
				
				property.load(input);
				
				System.out.println(property.getProperty("Enter_1"));
				System.out.println(property.getProperty("Enter_2"));
				System.out.println(property.getProperty("Enter_3"));
				System.out.println(property.getProperty("Enter_4"));
				
			}catch(IOException e){
				e.printStackTrace();
			}
			
			//choice is being taken from the user
			int choice = 0;
			try{
				String str = br.readLine();
				int ch = Integer.parseInt(str);
				choice = ch;
			}
			catch(NumberFormatException e){
				System.out.println("Please enter a valid digit");
				try {
				    Thread.sleep(700);
				} catch ( java.lang.InterruptedException ie) {
				    System.out.println(ie);
				}
				continue;
			}
			
			//ArrayList has been created to store the employees name and department information
			ArrayList<Myclass> object1 = new ArrayList<Myclass>();
			
			//choice 2 to add the employees 
			if(choice==2){
				try{
					String str1;
					String str2;
					
					System.out.println("Enter number of employees you want to add");
					Scanner in1 = new Scanner(System.in);
					int num = in1.nextInt();
						
					for(int i=0;i<num;i++){
						str1 = br.readLine();
						str2 = br.readLine();
						object1.add(new Myclass(str1,str2));
					}
							
					FileOutputStream fos = new FileOutputStream(directory);
					ObjectOutputStream oos = new ObjectOutputStream(fos);
					
					oos.writeObject(object1);
					oos.flush();
					oos.close();
					System.out.println("Press Y to continue");
					String s1 = br.readLine();
					if(s1.equalsIgnoreCase("Y")){
						continue;
					}
				}
				catch(Exception e){
					System.out.println("exception");
					e.printStackTrace();
				}
				}
			//choice 1 to show the employees details
			else if(choice==1){
				try{
					FileInputStream  fis = new FileInputStream(directory);
					ObjectInputStream ois = new ObjectInputStream(fis);
					ArrayList object2 = (ArrayList)ois.readObject();
					ois.close();
					System.out.println("employee details "+" "+object2);
						
					System.out.println("Press Y to continue");
					String s2 = br.readLine();
					if(s2.equalsIgnoreCase("Y")){
						continue;
					}
				}
				catch(Exception e){
					e.printStackTrace();
				}
				
			}
			//choice 3 to delete the employees
			else if(choice==3){
				System.out.println("Enter name and department of employee you want to delete");
				try {
					
					String str3;
					String str4;
					BufferedReader br1 = new BufferedReader(new InputStreamReader(System.in));
					str3 = br1.readLine();
					str4 = br1.readLine();
						
					FileInputStream  Fs = new FileInputStream(directory);
					ObjectInputStream Os = new ObjectInputStream(Fs);
					ArrayList<Myclass> object3 = (ArrayList<Myclass>)Os.readObject();
						
					Iterator itr = object3.iterator();
					
		            //code to delete the employee corresponding to user entry
					while(itr.hasNext()) {
						  Myclass element = (Myclass) itr.next();
						  if(element.getName().equals(str3) && element.getDepartment().equals(str4))
						  {
						      itr.remove();
						      break;
						  }
					}
					
					//serialization is done after delete operation has been performed
					FileOutputStream fs = new FileOutputStream(directory);
					ObjectOutputStream os = new ObjectOutputStream(fs);
					os.writeObject(object3);
					os.flush();
					os.close();
						
					System.out.println("Press Y to continue");
					String s = br.readLine();
					if(s.equalsIgnoreCase("Y")){
						continue;
					}
						
				} 
				catch (FileNotFoundException e) {
					e.printStackTrace();
				}
					
			}
			//exit the system
			else{
				System.exit(0);
			}
			
			
				
		}
	}
}


 class Myclass implements Serializable{
		String name;
		String dname;
		public Myclass(String name,String dname){
			this.name = name;
			this.dname = dname;
		}
		
		public boolean equals(Object obj){
			if(obj instanceof Myclass){
				Myclass ss = (Myclass)obj;
				return (ss.name.equals(this.name) && ss.dname.equals(this.dname));
			}
			else{
				return false;
			}
		}
		
		public int hashCode(){
			int hashcode = 0;
			hashcode = hashcode + (name!=null?name.hashCode():0);
			return hashcode; 
		}
		
		public String getName() {
	        return name;
	    }
	 
	    public void setName(String name) {
	        this.name = name;
	    }
	    
	    public String getDepartment() {
	        return dname;
	    }
	 
	    public void setDepartment(String dname) {
	        this.dname = dname;
	    }
		public String toString(){
			return "name = "+" "+name+","+" "+"department = "+" "+dname;
		}
		
		public static File getDirectory() throws IOException{
			File Dir = new File("MyFile");
			if(!Dir.exists()){
				Dir.createNewFile();
			}
			return Dir;
		}
	}


