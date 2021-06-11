package server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.HashMap;

import thread.ConnectThread;

public class Server {
	public static void main(String[] args) throws IOException {
		int port = 7899;
		if (args.length > 0) {
			port = Integer.parseInt(args[0]);
		}

		String dbHost = "studenthoangthuc701.mssql.somee.com";
		int dbPort = 1433;
		String dbDatabase = "studenthoangthuc701";
		
		String dbUrl = "jdbc:sqlserver://" + dbHost + ":" + dbPort + ";DatabaseName=" + dbDatabase;
		String username = "hoangthuc701_SQLLogin_1";
		String password = "hfnwvp7pny";
		
		ArrayList<String> listUserOnline = new ArrayList<>();
		HashMap<String, ArrayList<String>> messages = new HashMap<String, ArrayList<String>>(); 

		ServerSocket masterServer = null;
		try {
			masterServer = new ServerSocket(port);
			System.out.println("Master server is listening in port " + String.valueOf(port) + "!!");
			ArrayList<ConnectThread> listConect = new ArrayList<>();
			while (true) {
				Socket socket = masterServer.accept();
				ConnectThread connectThread = new ConnectThread(socket, listConect, dbUrl, username, password, listUserOnline, messages);
				connectThread.start();
				listConect.add(connectThread);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			masterServer.close();
		}
	}
}
