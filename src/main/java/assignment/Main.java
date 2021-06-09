/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package assignment;

import java.io.*;
import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Main
 About: 
 * @author Leroy Siyafa 219323151
 * Date Modified: 
 */
public class Main {
    private FileReader fr;
    private BufferedReader br;
    private BufferedWriter bw;
    private FileWriter fw;
    private ObjectInputStream input;
    private Customer temp;
    private ArrayList items = new ArrayList();
    private ArrayList customers = new ArrayList();
    private ArrayList suppliers = new ArrayList();
    private int[] customerAge = new int[6];
    private int can = 0;
    private int cannotRent = 0;
    
    //arrays to store the data of the customers objects
    private String[] IDs = new String[6];
    private String[] name = new String[6];
    private String[] surname = new String[6];
    private String[] address = new String[6];
    private String[] birth = new String[6];
    private Double[] credit = new Double[6];
    private Boolean[] canRent = new Boolean[6];
    
    //arrays to store the data of the suppliers objects
    private String[] supplierIDs = new String[5];
    private String[] supplierNames = new String[5];
    private String[] supplierProductTypes = new String[5];
    private String[] supplierProductDescription = new String[5];
    
    //method to open the file for reading
    public void openFile(){
        try{
            input = new ObjectInputStream(new FileInputStream("stakeholder.ser"));
        }
        catch(IOException ioe)
        
        {
            System.out.println("error opening ser file: " + ioe.getMessage());
        }
    }
    
    
    public void closeFiles(){
        try{
         input.close();
        }
        catch(IOException ioe)
        {
            System.out.println(ioe);
        }
    }
    
    //reading the objects in the file and adding them to an arraylist called items 
    public void readFile() throws ClassNotFoundException{
        try{
            for(int i = 0; i < 11; i++){
                items.add(input.readObject());
            }
            
        }catch(IOException ioe){
            System.out.println("Error reading from ser file "+ ioe);
        }
    }
    
    
    //re-allocating the different objects in the ArrayList to two different ArrayLists => customers and suppliers
    public void allocate(){
        for (int i=0; i < items.size(); i ++){
            if (items.get(i) instanceof Customer){
                customers.add(items.get(i));
            }else{
                suppliers.add(items.get(i));
            }
        }
    }
    
    //method to sort the objects in customers arrayList
    //using a custom comparator to sort the arraylist
    public void sortId(){
        Collections.sort(customers, Comparator.comparing(Customer::getStHolderId));

    }
    
    //method to sort the objects in the suppliers arrayList
    //using a customer comparator to sort the arrayList
    public void sortName(){
        Collections.sort(suppliers, Comparator.comparing(Supplier::getName));
    }
    
    //method to determine the age of the customer
    public void determineAge(){
        String yr;
        int age = 0;
        int birthYear;
        int month;
        int day;
        
        //looping through the list to get the date of birth of every customer
        for(int i = 0; i < 6; i++){
            Customer c = (Customer) customers.get(i);
            String str = c.getDateOfBirth();
            
            String [] arr = str.split("-");
            //parse string to int
            birthYear = Integer.parseInt(arr[0]);
            month = Integer.parseInt(arr[1]);
            day = Integer.parseInt(arr[2]);
            
            //checking if the customer was born on a date later than today
            if(day > 8 && month > 6){
                age = ((2021 - birthYear) -1);
                String name = c.getFirstName();
                //System.out.println(name + "'s age is " + age);
                customerAge[i] = age;
            }else{
                age = (2021 - birthYear);
                String name = c.getFirstName();
                //System.out.println(name + "'s age is " + age);
                customerAge[i] = age;
            }
            
            //calling the store objects method so that every time we loop through the array, we can store the data of each object
            storeObjects(i);
            //calling method to calculate number of customers that can rent and those that cannot. 
            canRent(i);
        }
    }
    
    //method to store the objects' data in supplier arrayList
    public void storeStoreSupplierObjects(){
        for (int i = 0; i < 5; i ++){
            //calling method to store suppliers objects' data in different arrays
            createSupplierArrays(i);
        }
    }
    //method to create arrays that will store the obect's data
    public void storeObjects(int i){
        
        Customer c = (Customer) customers.get(i);
            
            
            //getting the user Id's and putting them in an array 
            String a = c.getStHolderId();
            IDs[i] = a;
            
            //getting the customer names and putting them in an array 
            String b = c.getFirstName();
            name[i] = b;
            
            //getting the customer surnames and putting them in an array 
            String d = c.getSurName();
            surname[i] = d;
            
            //getting the customer addresses and putting them in an array 
            String e = c.getAddress();
            address[i] = e;
            
            //getting the customer date of births and putting them in an array 
            String f = c.getDateOfBirth();
            birth[i] = f;
            
            //getting the customer credit and putting them in an array 
            Double g = c.getCredit();
            credit[i] = g;
            
            //getting the customer canRent value and putting them in an array 
            Boolean h = c.getCanRent();
            canRent[i] = h;
            
            //System.out.println(name[i]);
    }
    
    //method to create arrays that will store the obects' in supplier arrayList's data
    public void createSupplierArrays(int i){
        Supplier s = (Supplier) suppliers.get(i);
        
            //getting the supplier Id's and putting them in an array 
            String a = s.getStHolderId();
            supplierIDs[i] = a;
            
            //getting the supplier names and putting them in an array 
            String b = s.getName();
            supplierNames[i] = b;
            
            //getting the supplier prod types and putting them in an array 
            String c = s.getProductType();
            supplierProductTypes[i] = c;
            
            //getting the supplier Description and putting them in an array 
            String d = s.getProductDescription();
            supplierProductDescription[i] = d;
            
            //System.out.println(supplierIDs[i] + " " +  supplierNames[i] + " " +  supplierProductTypes[i] + " " + supplierProductDescription[i]);
    }
    
    
    public void reFormat(){
        for(int i = 0; i < 6; i++){
           Customer c = (Customer) customers.get(i);
           String str = c.getDateOfBirth();
           
           String [] arr = str.split("-");
           
           String temp = arr[0];
           arr[0] = arr[2];
           arr[2] = temp;
           
           //switch statement to change the number to the month
           
           int num = Integer.parseInt(arr[1]);
           String[] months = {"Jan", "Feb", "Mar","Apr", "May", "Jun", "Jul", "Aug", "Sep", "Oct", "Nov", "Dec"};
           arr[1] = months[num-1];
           String joined = String.join(" ", arr);
          
           c.setDateOfBirth(joined);
        }
    }
    
    //method to determine the customers who can rent and those that cannot rent
    public void canRent(int i){
        Customer c = (Customer) customers.get(i);
        Boolean bool = c.getCanRent();
        
        if(bool == true){
            can++;
        }else{
           cannotRent++;
        }
        
    }
    
    
    //method to print the value of the objects in Customers ArrayList
    public void customersFile(){    
        
        
        
        String output = "========================== Customers ============================\n" +
                            "Id       Name        Surname        Date of Birth         Age\n"
                          + "================================================================\n" +
                  IDs[0]+"     " + name[0] + "        " + surname[0] + "       " + birth[0] + "            " + customerAge[0] + "\n" +
                  IDs[1]+"     " + name[1] + "        " + surname[1] + "       " + birth[1] + "            " + customerAge[1] + "\n" +
                  IDs[2]+"     " + name[2] + "        " + surname[2] + "       " + birth[2] + "            " + customerAge[2] + "\n" +
                  IDs[3]+"     " + name[3] + "        " + surname[3] + "       " + birth[3] + "            " + customerAge[3] + "\n" +
                  IDs[4]+"     " + name[4] + "        " + surname[4] + "       " + birth[4] + "            " + customerAge[4] + "\n" +
                  IDs[5]+"     " + name[5] + "        " + surname[5] + "       " + birth[5] + "            " + customerAge[5] + "\n" + "\n" +
                  "Number of customers who can rent:  "  + can + " \n" +
                  "Number of customers who cannot rent:  " + cannotRent;
        
        try {
            //creating new file
            File file = new File("CustomerOutFile.txt");
            
            //checking whether our file exists
            if(!file.exists()){
                file.createNewFile(); 
            }
            
            PrintWriter pw = new PrintWriter(file); 
            pw.println(output);
            pw.close();
            System.out.println("done"); 
        }
        catch (IOException ex) {
                //catch block
                ex.printStackTrace();
            }
            
        
    }
    
    //method to print the values of the objects in Suppiers arrayList
    public void suppliersFile(){
        String output = "========================== Customers ============================\n" +
                            "Id       Name        prod Type        Description"  + "\n"    
                          + "================================================================\n" +
                  supplierIDs[0]+"     " + supplierNames[0] + "        " + supplierProductTypes[0] + "       " + supplierProductDescription[0] + "\n" +
                  supplierIDs[1]+"     " + supplierNames[1] + "        " + supplierProductTypes[1] + "       " + supplierProductDescription[1] + "\n" +
                  supplierIDs[2]+"     " + supplierNames[2] + "        " + supplierProductTypes[2] + "       " + supplierProductDescription[2] + "\n" +
                  supplierIDs[3]+"     " + supplierNames[3] + "        " + supplierProductTypes[3] + "       " + supplierProductDescription[3] + "\n";
        
        try {
            //creating new file
            File file = new File("SupplierOutFile.txt");
            
            //checking whether our file exists
            if(!file.exists()){
                file.createNewFile(); 
            }
            
            PrintWriter pw = new PrintWriter(file); 
            pw.println(output);
            pw.close();
            System.out.println("done"); 
        }
        catch (IOException ex) {
            //catch block
            ex.printStackTrace();
        }
    }
    
    public static void main(String[] args) throws IOException, ClassNotFoundException{
        
        Main  myobj = new Main();
        //open the file
        myobj.openFile();
        //read the file
        myobj.readFile();
        //close the file
        myobj.closeFiles();
        //re-allocate the objects in the file to two different arrayLists (customers and suppliers respectively)
        myobj.allocate();
        //sorting the items in the array by the stakeholderId's 
        myobj.sortId();
        //sorting the objects of the suppiers ID by their first Name
        myobj.sortName();
        //method to determine the age of the customer
        myobj.determineAge();
        //method to re-format the 
        myobj.reFormat();
        //store supplier objects
        myobj.storeStoreSupplierObjects();
        //write to customer out file
        myobj.customersFile();
        //write to the Supplier out file
        myobj.suppliersFile();
        
    }
}


        
