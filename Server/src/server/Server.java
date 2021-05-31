package server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

import thread.ConnectThread;

public class Server {
	public static void main(String[] args) throws IOException {
		int port = 1234;
		if (args.length > 0) {
			port = Integer.parseInt(args[0]);
		}
		
		String dbUrl ="";
		String username = "";
		String password = "";
		
		ServerSocket masterServer = null;
		try {
			masterServer = new ServerSocket(port);
			System.out.println("Master server is listening in port " + String.valueOf(port) + "!!");
			ArrayList<ConnectThread> listConect = new ArrayList<>();
			while (true) {
				Socket socket = masterServer.accept();
				ConnectThread connectThread = new ConnectThread(socket, listConect, dbUrl, username, password);
				connectThread.start();
				listConect.add(connectThread);
			}
		} catch (Exception e) {

		} finally {
			masterServer.close();
		}
	}
}
