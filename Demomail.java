import java.util.Properties;
import javax.mail.*;
import java.util.*;
import java.lang.Object;
import javax.mail.search.FlagTerm;
import javax.mail.Folder;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.NoSuchProviderException;
import javax.mail.Session;
import javax.mail.Store;
import java.io.*;
import javax.xml.bind.DatatypeConverter;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.io.DataOutputStream;

public class Demomail {

   public static void check(String host, String storeType, String user,
      String password)
   {
      try {

      //create properties field
      Properties properties = new Properties();

      properties.put("mail.imap.host", host);
      properties.put("mail.imap.port", "993");
      properties.put("mail.imap.starttls.enable", "true");
      Session emailSession = Session.getDefaultInstance(properties);

      //create the POP3 store object and connect with the pop server
      Store store = emailSession.getStore("imaps");

      store.connect(host, user, password);
	  
	  //create the folder object and open it
      Folder emailFolder = store.getFolder("INBOX");
      emailFolder.open(Folder.READ_WRITE);
      // retrieve the messages from the folder in an array and print it
     // Message[] messages = emailFolder.getMessages();
      Message[] messages =emailFolder.search(new FlagTerm(new Flags(Flags.Flag.SEEN), false));
      System.out.println("messages.length---" + messages.length);

      int  n = messages.length;

     

      // for (int i = n; i >0; i--) {
          //int k=i-1;
         // messages.setFlag(Flags.Flag.SEEN, true);
         //emailFolder.setFlags(messages, new Flags(Flags.Flag.SEEN), true);
           // }
      
      for (int i = 0; i <n; i++) {
          
         Message message = messages[i];
         message.setFlag(Flags.Flag.SEEN, true);
               /*Multipart multipart = (Multipart) message.getContent();
                for (int x = 0; x < multipart.getCount(); x++) { 
                BodyPart bodyPart = multipart.getBodyPart(x);
                System.out.println("Text: " + bodyPart.getContent());}*/
         System.out.println("---------------------------------");
               //System.out.println("Email Number " + (i + 1));
         String t=message.getSubject();
         System.out.println(t);
         System.out.println("Subject: " + message.getReceivedDate());
         System.out.println("From: " + message.getFrom()[0]);
         Multipart multipart = (Multipart) message.getContent();
              //System.out.println("body.length---" + multipart.getCount());
      for (int x = 0; x < multipart.getCount()-1; x++) {
     
         BodyPart bodyPart = multipart.getBodyPart(x);
         System.out.println("Text: " + bodyPart.getContent());}
               //String s="build";
            if(t.contains("build"))
                 { System.out.println("build is triggered");
                 try {
                 URL url = new URL ("http://18.224.96.63:8085/job/Mail/buildWithParameters"); // Jenkins URL localhost:8080, job named 'test'
                 String user1 = "karty333"; // username
                 String pass = "11870bfda52aa9bb5a011cc9bb0778a81a"; // password or API token
                 String authStr = user1 +":"+  pass;
                 String encoding = DatatypeConverter.printBase64Binary(authStr.getBytes("utf-8"));
                 HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                 connection.setRequestMethod("POST");
                 connection.setDoOutput(true);
                 connection.setRequestProperty("Authorization", "Basic " + encoding);
                 String urlParams="decider=yes";
                 byte[] postData = urlParams.getBytes("utf-8");
                 try(DataOutputStream wr = new DataOutputStream(connection.getOutputStream())) {
                 wr.write(postData);
                      }

                 InputStream content = connection.getInputStream();
                 BufferedReader in   =
                 new BufferedReader (new InputStreamReader (content));
                 String line;
                 while ((line = in.readLine()) != null) {
                 System.out.println(line);
                   }
                   } catch(Exception e) {
	             e.printStackTrace();
                     }break;
                     }

                 else
                  {
                  System.out.println("build is not triggered");

                  }   
                     //System.out.println("Subject: " + message.getSubject());
                     //System.out.println("From: " + message.getFrom()[0]);
                    //System.out.println("Text: " + message.getContent().toString());

                  } 

                     /* for (Message message : messages) {

                     message.setFlag(Flags.Flag.SEEN, true);

                     }*/
                    //close the store and folder objects
                  emailFolder.close(false);
                  store.close();

                  } catch (NoSuchProviderException e) {
                  e.printStackTrace();
                  } catch (MessagingException e) {
                  e.printStackTrace();
                  } catch (Exception e) {
                  e.printStackTrace();
                  }
                  }

                  public static void main(String[] args) {

                  String host = "imap-mail.outlook.com";// change accordingly
                  String mailStoreType = "imaps";
	          String username = "vishnuskct@outlook.com";// change accordingly
                  String password = "wqfnfrlfnklyvpsw";// change accordingly
                  check(host, mailStoreType, username, password);
                  }
                  }
