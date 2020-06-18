package Chat;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;

import javax.bluetooth.LocalDevice;
import javax.bluetooth.RemoteDevice;
import javax.bluetooth.UUID;
import javax.microedition.io.Connector;
import javax.microedition.io.StreamConnection;

import chatUI.ChatWindow;

public class cliente {
	public void startclient() {
		   try { 
			  
			  
			   String url = "btspp://localhost:" + new UUID(0x1101).toString() + ";name=richat";
		        StreamConnection con = 
		            (StreamConnection) Connector.open(url);
		        OutputStream output = con.openOutputStream();
		        InputStream input = con.openInputStream();
		        InputStreamReader teclado = new InputStreamReader(System.in);
		        BufferedReader buflec = new BufferedReader(teclado);
		        RemoteDevice dev = RemoteDevice.getRemoteDevice(con);

		        
		     if (con !=null) {
		      while (true) {   
		         
		     System.out.println("Servidor encontrado "  +dev.getBluetoothAddress()+"\r\n"+"Escriba su mensaje"+"\r\n");
		        String str = buflec.readLine();
		        output.write( str.getBytes());
		        byte buffer[] = new byte[1024];
		        int leido = input.read( buffer );
		        String datos = new String(buffer, 0, leido);
		        System.out.println("Cliente: " + datos
		         + "De:"+dev.getBluetoothAddress()); 
		      } 
		        } 
		        }
		  catch(Exception e){}  
		 }
		 
		 
		    public static void main( String args[] ) {
		       try{
		      LocalDevice device = LocalDevice.getLocalDevice();
		      System.out.println("Direccion" + ":"+device.getBluetoothAddress()
		        +"+n"+device.getFriendlyName());
		       }
		       catch (Exception e) {System.err.print(e.toString());}
		     try {        
		     rfcommclient rfcomm = new rfcommclient();
		        while(true){
		     rfcomm.startclient();
		        }
		        } catch ( Exception e ) {
		            System.err.print(e.toString());
		        }
		    }
}
