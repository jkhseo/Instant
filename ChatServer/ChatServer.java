import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.lang.ref.WeakReference;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.logging.Handler;
import java.util.logging.LogRecord;

public class ChatServer {
	public static final int PORT_NUMBER = 2222;
	private static HashMap<String,ArrayList<String>> messageQueue = new HashMap<String,ArrayList<String>>();
	private static ArrayList<ChatServerThread> clients = new ArrayList<ChatServerThread>();
	private static HashMap<String,Integer> clientID = new HashMap<String,Integer>();
	public static void main(String[] args){
		try{
			@SuppressWarnings("resource")
			ServerSocket serverSocket = new ServerSocket(PORT_NUMBER);
			while(true){
				ChatServerThread temp = new ChatServerThread(serverSocket.accept(),clients.size());
				System.out.println("accepted a connection");
				clients.add(temp);
				temp.start();
			}
			
		}catch(IOException e){
			e.printStackTrace();
		}
	}
	private static class ChatServerThread extends Thread {
		private Socket socket;
		private String ID;
		private int position;
		private PrintWriter out;
		private BufferedReader in;
		public ChatServerThread(Socket socket,int position){
			this.socket = socket;
			this.position = position;
		}
		public void writeMessage(String message){
			System.out.println("sent"+message);
			out.println(message);
			out.flush();
		}
		public void run(){
			try{
				System.out.println("Started");
				boolean recievedID = false;
				out = new PrintWriter(socket.getOutputStream(),true);
				in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
				String input = "";
				
				//initial input of ID of socket connection
				input = in.readLine();
				if(input!=null &&input.substring(0,3).equals("ID:")){
					System.out.println("Recieved: "+input);
					ID = input.substring(3).trim();
					if(!clientID.containsKey(ID)){
						clientID.put(ID,position);
					}
					if(messageQueue.containsKey(ID)){
						ArrayList<String> myMessages = messageQueue.get(ID);
						for(String message: myMessages){
							System.out.println("sent"+ message);
							out.println(message);		
						}
						out.flush();
						messageQueue.get(ID).clear();
						recievedID=true;
					}
				}
				else{
					socket.close();
				}
				
				String address;
				Integer clientIndex;
				String message;
				ArrayList<String> messages;
				while(true){
					
					input = in.readLine();
					if(input!=null){
						System.out.println("Recieved: "+input);
						if(input.equals("\\quit")){
							clients.remove(this);
							clientID.remove(ID);
							socket.close();
							break;
						}
						address = input.substring(0,input.indexOf("::"));
						System.out.println(address);
						message = input;
						clientIndex = clientID.get(address);
						if(clientIndex !=null){
							System.out.println("correct");
							clients.get(clientIndex).writeMessage(message);
						}
						else{
							System.out.println("wrong");
							if(!messageQueue.containsKey(ID)){
								
								messages = new ArrayList<String>();
								messages.add(message);
								messageQueue.put(ID,messages);
							}
							else{
								messageQueue.get(ID).add(message);
							}
						}	
					}
				}
				
			}catch(IOException e){
				e.printStackTrace();
			}
		}
	}
}
