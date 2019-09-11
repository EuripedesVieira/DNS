package br.edu.ifg.servidor;

import br.edu.ifg.face.Constant;
import br.edu.ifg.face.InterfaceDNS;
import br.edu.ifg.model.TabelaUsuarios;

import java.rmi.registry.Registry;
import java.rmi.registry.LocateRegistry;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;

public class DNS implements InterfaceDNS {
	
	TabelaUsuarios tabelaUsuarios = new TabelaUsuarios();

	public DNS() {}
	
	public static void main(String args[]) throws RemoteException {

		try {

			DNS dns = new DNS();
			InterfaceDNS interfDNS = (InterfaceDNS) UnicastRemoteObject.exportObject(dns, 0);
			Registry registry = LocateRegistry.createRegistry(Constant.RMI_PORT); 
			registry.bind(Constant.RMI_ID, interfDNS);

			System.out.println("Servidor rodando!");
			
		} catch (Exception e) {
			System.err.println("Server exception: " + e.toString());
			e.printStackTrace();
		}
	}

	
	public boolean autentica(String nick, String ip) throws RemoteException {
		tabelaUsuarios.adicionarUsuario(nick, ip);
		System.out.println("Tabela de usuarios online: " +tabelaUsuarios.toString());
		return true;
	}

	public ArrayList<String> obterListaUsuariosOnline() throws RemoteException {
		return tabelaUsuarios.criaListaNick();

	}

	public String obterIP(String nick) throws RemoteException {
		return tabelaUsuarios.ipUsuario(nick);
	}
}