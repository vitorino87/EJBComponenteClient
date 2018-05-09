/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ejbcomponenteclient;


import com.tutorialspoint.stateless.LibrarySessionBeanRemote;
import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.List;
import java.util.Properties;
import javax.naming.InitialContext;
import javax.naming.NamingException;
/**
 *
 * @author tiago.lucas
 */
public class EJBComponenteClient {

    /**
     * @param args the command line arguments
     */
    @SuppressWarnings("CallToPrintStackTrace")
    BufferedReader brConsoleReader = null;        
        Properties props;
        InitialContext ctx;
        {
            props = new Properties();
            try{
                props.load(new FileInputStream("jndi.properties"));
            }catch(IOException ex){
                ex.printStackTrace();
            }
            try{
                ctx = new InitialContext(props);
            }catch(NamingException ex){
                ex.printStackTrace();
            }
            brConsoleReader = new BufferedReader(new InputStreamReader(System.in));
        }
    
    public static void main(String[] args) {
        EJBComponenteClient ejbTester = new EJBComponenteClient();
        ejbTester.testStatelessEjb();
    }
    
    private void showGUI(){
        System.out.println("**************************************");
        System.out.println("Welcome to Book Store");
        System.out.println("**************************************");
        System.out.println("Options \n1. Add Book\n2. Exit\nEnter Choice: ");        
    }
    
    private void testStatelessEjb() {
        try{
            int choice = 1;
            LibrarySessionBeanRemote libraryBean = (LibrarySessionBeanRemote)ctx.lookup("LibrarySessionBean/remote");
            while (choice !=2){
                String bookName;
                showGUI();
                String strChoice = brConsoleReader.readLine();
                choice = Integer.parseInt(strChoice);
                if(choice == 1){
                    System.out.println("Enter book name: ");
                    bookName = brConsoleReader.readLine();
                    /*Book book = new Book();
                    book.setName(bookName);
                    libraryBean.addBook(book);
                    */
                    libraryBean.addBook(bookName);
                }else if(choice == 2){
                    break;
                }
            }
            //List<String> booksList = libraryBean.getBooks();
            List<String> booksList = libraryBean.getBooks();
            System.out.println("Book(s) entered so far: "+booksList.size());
            for(int i=0;i<booksList.size();++i){
                System.out.println((i+1)+". "+booksList.get(i));
            }
            /*int i = 0; for(Book book: booksList){
                System.out.println((i+1)+". "+book.getName());
                i++;
            }*/
            /*LibrarySessionBeanRemote libraryBean1 = (LibrarySessionBeanRemote)ctx.lookup("LibrarySessionBean/remote");
            List<String>booksList1 = libraryBean1.getBooks();
            System.out.println("***Using second lookup to get library stateless object***");
            System.out.println("Book(s) entered so far: "+booksList1.size());
            for(int i = 0;i<booksList1.size();++i){
                System.out.println((i+1)+". "+booksList1.get(i));
            }*/
        }catch(Exception e){
            System.out.println(e.getMessage());
            //e.printStackTrace();
        }finally{
            try{
                if(brConsoleReader != null){
                    brConsoleReader.close();
                }
            }catch(IOException ex){
                System.out.println(ex.getMessage());
            }
        }
    }
    
}
