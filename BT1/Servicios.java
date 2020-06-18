package BT1;

import java.io.IOException;
import java.util.ArrayList;

import javax.bluetooth.BluetoothStateException;
import javax.bluetooth.DataElement;
import javax.bluetooth.DeviceClass;
import javax.bluetooth.DiscoveryAgent;
import javax.bluetooth.DiscoveryListener;
import javax.bluetooth.LocalDevice;
import javax.bluetooth.RemoteDevice;
import javax.bluetooth.ServiceRecord;
import javax.bluetooth.UUID;

public class Servicios implements DiscoveryListener {
	static int SERVICE_NAME_ATTRID = 0x0100;
	static ArrayList<RemoteDevice> listadisp = new ArrayList<RemoteDevice>(); // Ejercicio 5

	@Override
	public void deviceDiscovered(RemoteDevice rdevice, DeviceClass cod) {
		 // Ejercicio 2
          String dir = rdevice.getBluetoothAddress();
        String nombre = "";
        try {
            nombre = rdevice.getFriendlyName(true);
        } catch ( java.io.IOException e ) { }
        
        System.out.println("Encontrado: " + dir + " - " + nombre );
        listadisp.add(rdevice); // Ejercicio 5
        
    	/* -------------- Ejercicio 3 -----------------------
    	String dir= "94652D2D05B0";
    	String nom = "OnePlus 3T";
    	String nombredevice = "";
    	try {
            nombredevice = rdevice.getFriendlyName(true);
        } catch ( java.io.IOException e ) { 
        	
        }
    	if (nom.equals(nombredevice) || rdevice.getBluetoothAddress().equals(dir)) {
    		System.out.println("Encontrado el dispositivo "+ nom + " " + dir);
    	}
    	// -------------------------------------------------*/
		
	}
	@Override
	public void inquiryCompleted(int arg0) {
		 synchronized(this) {
	            try { this.notifyAll(); } catch ( Exception e ) {}
	        } 
		
	}
	@Override
	public void serviceSearchCompleted(int arg0, int arg1) {
		// TODO Auto-generated method stub
		
	}
	@Override
	/*public void servicesDiscovered(int arg0, ServiceRecord[] arg1) {
		System.out.println("Servicios Encontrados");
		DataElement servicio;
		 String nombreser = "SMS/MMS"; // Ejercicio 7
		for (int i = 0 ; i<arg1.length;i++) {
			servicio = arg1[i].getAttributeValue(SERVICE_NAME_ATTRID);
			// -------------------- Ejercicio 7 ---------------------------
			 if (servicio != null && servicio.equals(nombreser)) {
				System.out.println((String) servicio.getValue());
			} else {
				System.out.println("Servicio no encontrado");
			}
			
			/*if (servicio != null)
                System.out.println((String) servicio.getValue());
            else
                System.out.println("Unnamed service");
			System.out.println(arg1[i].getConnectionURL(ServiceRecord.NOAUTHENTICATE_NOENCRYPT,false));
		}
	
	}
	*/
	public void servicesDiscovered(int arg0, ServiceRecord[] arg1) {
        String nombre;
        DataElement servicio;
        String buscarserv = "SMS/MMS";
        // char[] cad1, cad2; // Ejercicio 7
        //cad2 = buscarserv.toCharArray();
        for (int i = 0; i < arg1.length; i++) {
            servicio = arg1[i].getAttributeValue(SERVICE_NAME_ATTRID);
            /*if (servicio != null) {
                boolean iguales = true;
                nombre = (String) servicio.getValue();
                cad1 = nombre.toCharArray();
                for (int j = 0; j < cad2.length; j++) {
                    if (cad1[j] != cad2[j])
                        iguales = false;
                }
                if (iguales) {
                    System.out.println("Encontrado el servicio "+buscarserv);
                    System.out.println(arg1[i].getConnectionURL(ServiceRecord.NOAUTHENTICATE_NOENCRYPT, false));
                }
            }
            */
            if (servicio != null)
                System.out.println((String) servicio.getValue());
            else
                System.out.println("Unnamed service");
			System.out.println(arg1[i].getConnectionURL(ServiceRecord.NOAUTHENTICATE_NOENCRYPT,false));
		
        }
    }
    public static void main( String args[] ) throws IOException {
        try {
        	// --------------------------- Ejercicio 4 ----------------------
        	Servicios ser = new Servicios();
        		
        	LocalDevice dispositivo = LocalDevice.getLocalDevice();
        		
        	DiscoveryAgent da = dispositivo.getDiscoveryAgent();
        	da.startInquiry( DiscoveryAgent.GIAC, ser );
        	
        		
            System.out.println("Nombre: "+dispositivo.getFriendlyName());
    		System.out.println("Disc. Agent: "+dispositivo.getDiscoveryAgent()); 	
    		System.out.println("Version API: "+dispositivo.getProperty("bluetooth.api.version"));
    		System.out.println("Max. dispositivos: "+dispositivo.getProperty("bluetooth.connected.devices.max"));
    		UUID uuids [] = new UUID[1];
    		uuids [0] = new UUID(0x1002);
    		// -------- Ejercicio 6 --------------------
    		//UUID uuids [] = new UUID[2];
    		//uuids [0] = new UUID(0x1002);
    		//uuids [1] = new UUID(0x1101);
    		// ------------------------------------------
    		int attridset [] = new int [1];
    		attridset[0] = SERVICE_NAME_ATTRID;
    	

    		synchronized(ser) {
                try { ser.wait(); } catch ( Exception e ) {}
            }
            
            
            if (listadisp.size()>0) {
            	for (int i = 0; i<listadisp.size();i++) {
            		RemoteDevice device = listadisp.get(i);
            		System.out.println("Servicios de "+device.getFriendlyName(true)+" - "+ device.getBluetoothAddress() +": " );
            		da.searchServices(attridset, uuids, device, ser);
            	}
            }
            // da.searchServices(attridset, uuids, listadisp.get(0), ser);
   		 synchronized (ser) {
   			 try {
   				 ser.wait();
   			 } catch (Exception e) {};
   		 }

        } catch ( BluetoothStateException e ) {
            System.err.print(e.toString());
        }
    }  

}


