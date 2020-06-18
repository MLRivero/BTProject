package Chat;

import java.io.*;
import javax.microedition.io.*;

import chatUI.ChatWindow;

import javax.bluetooth.*;
public class rfcommclient {
 
 public void startclient() {
   try { 
	   ChatWindow window = new ChatWindow();
	   window.setVisible(true);
	   String url = "btspp://localhost:" + new UUID(0x1101).toString() + ";name=richat";
        StreamConnection con = 
            (StreamConnection) Connector.open(url);
        DataInputStream dis = new DataInputStream(con.openInputStream());
        DataOutputStream dos = new DataOutputStream(con.openOutputStream());
        window.setOut("Conexión establecida");
        OutputStream os = con.openOutputStream();
        InputStream is = con.openInputStream();
        InputStreamReader isr = new InputStreamReader(System.in);
        BufferedReader bufReader = new BufferedReader(isr);
        RemoteDevice dev = RemoteDevice.getRemoteDevice(con);

     /*  if (dev !=null) {
         File f = new File("test.xml");
         InputStream sis = new FileInputStream("test.xml");
         OutputStream oo = new FileOutputStream(f);
         byte buf[] = new byte[1024];
         int len;
         while ((len=sis.read(buf))>0)
          oo.write(buf,0,len);
         sis.close();
        }  */
        
     if (con !=null) {
      while (true) {   
         
     System.out.println("Server Found:" 
         +dev.getBluetoothAddress()+"\r\n"+"Put your string"+"\r\n");
        String str = bufReader.readLine();
        os.write( str.getBytes());
       //reciever string
        byte buffer[] = new byte[1024];
        int bytes_read = is.read( buffer );
        String received = new String(buffer, 0, bytes_read);
        System.out.println("client: " + received
         + "from:"+dev.getBluetoothAddress()); 
      } 
        } 
        }
  catch(Exception e){}  
 }
 
 
    public static void main( String args[] ) {
       try{
      LocalDevice local = LocalDevice.getLocalDevice();
      System.out.println("Address:"+local.getBluetoothAddress()
        +"+n"+local.getFriendlyName());
       }
       catch (Exception e) {System.err.print(e.toString());}
     try {        
     rfcommclient ss = new rfcommclient();
        while(true){
     ss.startclient();
        }
        } catch ( Exception e ) {
            System.err.print(e.toString());
        }
    }
}//main
