package usuario;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.PrintWriter;

import javax.swing.JOptionPane;

public class FilaUsuario {
	private static Usuario filaLogin[];
	private int elementosFila;
	private String removido;
	
	public FilaUsuario(int total) {
		filaLogin = new Usuario[total];
		elementosFila = 0;
	}

	public void VerificarDuplicidade(String login) {
		boolean ok = verificarUsuario(login);
		if(!ok) {
			boolean achou = false;
			if(login.equals("")) {
				JOptionPane.showMessageDialog(null, "Inserir um Usuário válido");
			} else {
				if(elementosFila == 0){
					AddNaFila(new Usuario (login));
				} else {
					for(int i = 0; i < elementosFila; i++) {
						if(filaLogin[i].getLogin().contains(login)){
						achou = true;
							JOptionPane.showMessageDialog(null, "Login semelhante já existe");
							break;
						}
				}
				if (!achou) {
						AddNaFila(new Usuario (login));
					}
				}
			}
		} else {
			JOptionPane.showMessageDialog(null, "Login semelhante já existe");
			return;
		}
	}

	
	public void AddNaFila(Usuario usuario) {
		boolean certo = false;
		String senha = InserirSenha();
		while(certo == false) {
			if(senha != null) {
				if(elementosFila < filaLogin.length){
					filaLogin[elementosFila] = usuario;
					filaLogin[elementosFila].setSenha(senha);
					elementosFila++;
					certo = true;
				} else {
					JOptionPane.showMessageDialog(null, "Não é possível adicionar, pois a fila está cheia");
					certo = true;
				}
			} else {
				senha = InserirSenha();
			}
		}
	}

	private String InserirSenha() {
		String senha = JOptionPane.showInputDialog("Inserir senha");
		String senha2 = JOptionPane.showInputDialog("Inserir senha novamente");
		if(senha.equals(senha2)) {
			return senha;
		} else {
			JOptionPane.showMessageDialog(null, "Senha incorreta");
			return null;
		}
	}

	public void RmvPorLogin(String login) {
		boolean achou = false;
		for (int i = 0; i < elementosFila; i++) {
			if (filaLogin[i].getLogin().contains(login)) {
				achou = true;
				removido = filaLogin[i].toString();
				for(int j = i; j < elementosFila - 1; j++){
					filaLogin[i] = filaLogin[i+1];
				}
				elementosFila--;
				break;
			} 
			JOptionPane.showMessageDialog(null, "Removido:\n" + removido);
		}
		if (!achou) {
			JOptionPane.showMessageDialog(null, "Login não consta na lista");
		}
	}


	public void BuscarCadastro(String login) {
		boolean achou = false;
		for (int i = 0; i < elementosFila; i++) {
			if (filaLogin[i].getLogin().contains(login)) {
				JOptionPane.showMessageDialog(null, "O usuário " + login + " está na posição " + (i + 1));
				achou = true;
				break;
			} 
		}
		if (!achou) {
			JOptionPane.showMessageDialog(null, "Login não consta na lista");
		}
	}

	public String listaFila() {
		String list = "";
		Usuario aux = null;
		for(int i = 0; i < elementosFila; i++) {
			aux = filaLogin[i];
			String aux2 = aux.toString();
			String data[] = new String[2];
			data = aux2.split("§");
			if(data[0] != null) {
				for(int j = 0; j<2; j++) {
					list = list + nomearLista(j) + data[j] + "\n";
				}
			}
		}
		return list;
	}

	private String nomearLista(int i) {
		switch(i){
		case 0:
			return "Login = ";
		case 1:
			return "Senha = ";
		default:
			return null;
		}
	}

	
	public String inserirUsuarioDB() {
		if(elementosFila >= 1){
			try {
				removido = filaLogin[0].toString();
				File f = new File(filaLogin[0].getLogin() + ".csv");
				PrintWriter pw = new PrintWriter(new FileOutputStream(f, true));
				pw.append(removido);
				pw.append("\n");
				pw.close();	
				File f2 = new File("UsuarioDB.csv");
				PrintWriter pw2 = new PrintWriter(new FileOutputStream(f2, true));
				pw2.append(filaLogin[0].getLogin());
				pw2.append("\n");
				pw2.close();
				for(int i = 1; i < elementosFila; i++) {
					filaLogin[i - 1] = filaLogin[i];
				}
				elementosFila--;
			} catch(Exception e){}
			return removido;
		} else {
		return "nulo, pois não há mais cadastros na fila";
		}
	}

	public void exibirUsuarioDB() {
		try {
			BufferedReader br = new BufferedReader(new FileReader("UsuarioDB.csv"));
			String s = "";
			String row = "";
			int contador = 1;
			while((s = br.readLine()) != null){
				if(contador%3 != 0) {
					row = row + s + "          ";
				} else {
					row = row + s + "\n";
				}
				contador++;
			}
			JOptionPane.showMessageDialog(null, row);
			br.close();
		} catch(Exception e){}	
	}

	private String nomearDB(int i) {
		switch(i){
		case 0:
			return "Login = ";
		case 1:
			return "Senha = ";
		default:
			return null;
		}
	}

	public void buscarUsuarioDB(String login) {
		verificarUsuario(login);
		try {
			BufferedReader br = new BufferedReader(new FileReader(login + ".csv"));
			String s = "";
			String row = "";
			while((s = br.readLine()) != null){
				String data[] = new String[2];
				data = s.split("§");
				if(data[0] != null){
					for(int i = 0; i < 2; i++) {
					row = row + nomearDB(i) + data[i] + "\n";
					}
				}
				JOptionPane.showMessageDialog(null, row);
				br.close();
			}
		} catch(Exception e){}
	}
	
	public boolean verificarUsuario(String login) {
		boolean achou = false;
		try {
			BufferedReader br = new BufferedReader(new FileReader("UsuarioDB.csv"));
			String s = "";
			while((s = br.readLine()) != null){
				String data[] = new String[2];
				data = s.split("§");
				if(data[0].equalsIgnoreCase(login)){
					achou = true;
					break;
					}
				}
			if (!achou) {
				JOptionPane.showMessageDialog(null, "Usuário não consta na lista");
			}
			br.close();
		} catch(Exception e){}
		return achou;
	}
	
	public void alterarUsuarioDB(String login) {
		boolean ok = verificarUsuario(login);
		if(ok) {
			String ArrayDados[] = {"Login:","Senha:"};
			int yesNo[] = new int[2];
			String toUpdate[] = new String[2];
			JOptionPane.showMessageDialog(null, "Escolha qual dado quer atualizar");
			for(int i = 0; i<2; i++){
				int temp = Integer.parseInt(JOptionPane.showInputDialog(ArrayDados[i] + "\n0: Não alterar\n1: Alterar"));
				yesNo[i] = verificarYesNo(temp);
			}
		
		JOptionPane.showMessageDialog(null, "Insira os novos dados");
		for(int i = 0; i<2; i++){
			if(yesNo[i] == 1){
				JOptionPane.showMessageDialog(null, ArrayDados[i] + "");
				String tempVal = JOptionPane.showInputDialog("Novo dado:");
				toUpdate[i] = tempVal;
			}
		}
		
			StringBuffer sb = new StringBuffer();
			try {
				BufferedReader br = new BufferedReader(new FileReader(login + ".csv"));
				String s = br.readLine();
					String data[] = new String[2];
					data = s.split("§");
						String row = "";
						for(int i = 0; i<2; i++){
							if(yesNo[i] == 1){
								if(i == 1) {
									row = row + toUpdate[i];
								} else {
									row = row + toUpdate[i] + "§";
								}
							} else {
								if(i == 1) {
									row = row + data[i];
								} else {
									row = row + data[i] + "§";
							}
						}
						}
						sb.append(row);
						sb.append("\n");
					File f = new File(login + ".csv");
					PrintWriter pw = new PrintWriter(new FileOutputStream(f, false));
					pw.print(sb.toString());
					pw.close();
					br.close();
			} catch(Exception e){}
		} else {
			return;
		}
	}
	
	private int verificarYesNo(int temp) {
		switch(temp){
		case 0:
			return 0;
		case 1:
			return 1;
		default:
			JOptionPane.showMessageDialog(null, "Opção inválida, dado não será alterado");
			break;
	}
		return -1;
}

	public void excluirUsuarioDB(String login) {
		verificarUsuario(login);
		File f = new File(login + ".txt");
		if(f.delete()) {
			JOptionPane.showMessageDialog(null, "Excluído o cliente " + login);
		} 
		StringBuffer sb = new StringBuffer();
		try {
			BufferedReader br = new BufferedReader(new FileReader("UsuarioDB.csv"));
			String s = "";
			while((s = br.readLine()) != null){
				if(s.equalsIgnoreCase(login)){
					JOptionPane.showMessageDialog(null, "Retirando do índice");
				} else { 
					sb.append(s);
					sb.append("\n");
				}
				File f2 = new File("UsuarioDB.csv");
				PrintWriter pw = new PrintWriter(new FileOutputStream(f2, false));
				pw.print(sb.toString());
				pw.close();
			}
		} catch(Exception e){}
	
}

	public boolean verificarSenha(String login, String senha) {
		boolean achou = false;
		try {
			BufferedReader br = new BufferedReader(new FileReader(login + ".csv"));
			String s = "";
			while((s = br.readLine()) != null){
				String data[] = new String[2];
				data = s.split("§");
				if(data[1].equalsIgnoreCase(senha)){
					achou = true;
					break;
					}
				}
			if (!achou) {
				JOptionPane.showMessageDialog(null, "Senha incorreta");
			}
			br.close();
		} catch(Exception e){}
		return achou;
	}
}