package cliente;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.PrintWriter;

import javax.swing.*;

import cliente.Nodo;
import tema.Tema;
import cliente.Cliente;

public class ListaLigada {

	private Nodo inicio;
	Cliente cliente = null;
	
	public ListaLigada(){
		inicio = null;
	}
	
	public void addCliente(Cliente cliente) {
		Nodo no = new Nodo(cliente); 
		if(inicio == null) {
			no = new Nodo(cliente);
			inicio = no;
		} else {
			Nodo aux = localizaFim(inicio);
			aux.seguinte = no;
		}
	}

	private Nodo localizaFim(Nodo inicio2) {
		if(inicio2.seguinte != null){
			return localizaFim(inicio2.seguinte);
		}
		return inicio2;
	}

	public Cliente rmvCliente(int pos) {
		Cliente removido = null;
		int i = 1;
		Nodo aux = inicio;
		if(inicio == null){
			JOptionPane.showMessageDialog(null, "Não há cadastros na lista");
		}
		if(pos == 1){
			removido = rmvInicio();
			return removido;
		} else {
			while(aux.seguinte != null){
				aux = aux.seguinte;
				i++;
			}
			if(pos > i || pos <= 0){
				JOptionPane.showMessageDialog(null, "Posição inválida");
				return null;
			} else if (pos == i){
				removido = rmvFim(i);
				return removido;
			}else {
				aux = localizarPosicao(inicio, pos);
				Nodo aux2 = localizarPosicao(inicio, pos-1);
			removido = aux.cliente;
			aux2.seguinte = aux.seguinte;
			return removido;
			}
		}
	}

	private Nodo localizarPosicao(Nodo inicio2, int pos) {
		if(pos > 1) {
			return localizarPosicao(inicio2.seguinte, pos-1);
		}
		return inicio2;
	}

	private Cliente rmvInicio() {
		Cliente removido = null;
		removido = inicio.cliente;
		inicio = inicio.seguinte;
		return removido;
	}
	
	private Cliente rmvFim(int i) {
		Cliente removido = null;
		Nodo aux = localizarPosicao(inicio, i);
		removido = aux.seguinte.cliente;
		aux.seguinte = null;
		return removido;
	}

	public void dadosCliente(int pos) {
		Cliente listado = null;
		int i = 1;
		Nodo aux = inicio;
		if(inicio == null){
			JOptionPane.showMessageDialog(null, "Não há cadastros na lista");
		}
		if(pos == 1){
			listado = aux.cliente;
			mostrarListado(listado);
		} else {
			while(aux.seguinte != null){
				aux = aux.seguinte;
				i++;
			}
			if(pos > i || pos <= 0){
				JOptionPane.showMessageDialog(null, "Posição inválida");
			} else if (pos == i){
				listado = aux.cliente;
				mostrarListado(listado);
			} else {
				aux = localizarPosicao(inicio, pos);
				listado = aux.cliente;
				mostrarListado(listado);
			}
		}
	}
	
	public void dadosClienteRestrito(int pos) {
		Cliente listado = null;
		int i = 1;
		Nodo aux = inicio;
		if(inicio == null){
			JOptionPane.showMessageDialog(null, "Não há cadastros na lista");
		}
		if(pos == 1){
			listado = aux.cliente;
			mostrarListadoRestrito(listado);
		} else {
			while(aux.seguinte != null){
				aux = aux.seguinte;
				i++;
			}
			if(pos > i || pos <= 0){
				JOptionPane.showMessageDialog(null, "Posição inválida");
			} else if (pos == i){
				listado = aux.cliente;
				mostrarListadoRestrito(listado);
			} else {
				aux = localizarPosicao(inicio, pos);
				listado = aux.cliente;
				mostrarListadoRestrito(listado);
			}
		}
	}
	
	private void mostrarListado(Cliente listado) {
		String list = "";
		String aux = listado.toString();
		String data[] = new String[7];
		data = aux.split("§");
		if(data[0] != null) {
			for(int i = 0; i<7; i++) {
				list = list + nomearLista(i) + data[i] + "\n";
			}
		}
		JOptionPane.showMessageDialog(null, list);
	}
	
	private void mostrarListadoRestrito(Cliente listado) {
		String list = "";
		String aux = listado.toString();
		String data[] = new String[7];
		data = aux.split("§");
		if(data[0] != null) {
			for(int i = 0; i<5; i++) {
				list = list + nomearLista(i) + data[i] + "\n";
			}
		}
		JOptionPane.showMessageDialog(null, list);
	}

	public String listarClientes() {
		Nodo aux = inicio;
		String list = "";
		while(aux != null){
			String aux2 = aux.cliente.toString();
			String data[] = new String[7];
			data = aux2.split("§");
			if(data[0] != null) {
				for(int i = 0; i<7; i++) {
					list = list + nomearLista(i) + data[i] + "\n";
				}
			}
			aux = aux.seguinte;
		}
		return list;
	}
	
	public Object listarClientesRestrito() {
		Nodo aux = inicio;
		String list = "";
		while(aux != null){
			String aux2 = aux.cliente.toString();
			String data[] = new String[7];
			data = aux2.split("§");
			if(data[0] != null) {
				for(int i = 0; i<5; i++) {
					list = list + nomearLista(i) + data[i] + "\n";
				}
			}
			aux = aux.seguinte;
		}
		return list;
	}

	private String nomearLista(int i) {
		switch(i){
		case 0:
			return "Nome = ";
		case 1:
			return "Sobrenome = ";
		case 2:
			return "Documento = ";
		case 3:
			return "Endereço = ";
		case 4:
			return "Telefone = ";
		case 5:
			return "Número de vezes que alugou = ";
		case 6:
			return "Histórico = ";
		default:
			return null;
		}
	}

	public void calculoDesconto(int pos) {
		int desconto;
		int i = 1;
		Nodo aux = inicio;
		if(inicio == null){
			JOptionPane.showMessageDialog(null, "Não há cadastros na lista");
		}
		if(pos == 1){
			desconto = aux.cliente.getNumeroVezes(); 
			aplicarDesconto(desconto);
		} else {
			while(aux.seguinte != null){
				aux = aux.seguinte;
				i++;
			}
			if(pos > i || pos <= 0){
				JOptionPane.showMessageDialog(null, "Posição inválida");
			} else if (pos == 1){
				desconto = aux.cliente.getNumeroVezes(); 
				aplicarDesconto(desconto);
			} else {
				aux = localizarPosicao(inicio, pos);
				desconto = aux.cliente.getNumeroVezes(); 
				aplicarDesconto(desconto);
			}
		}
	}

	private void aplicarDesconto(int desconto) {
		if(desconto < 2){
			JOptionPane.showMessageDialog(null, "Nenhum desconto será aplicado");
		} else if(desconto >= 2 && desconto < 3){
			JOptionPane.showMessageDialog(null, "Será aplicado 2% de desconto");
		} else if(desconto >= 3 && desconto < 4){
			JOptionPane.showMessageDialog(null, "Será aplicado 5% de desconto");
		} else if(desconto >= 4 && desconto < 5){
			JOptionPane.showMessageDialog(null, "Será aplicado 10% de desconto");
		} else 
			JOptionPane.showMessageDialog(null, "Será aplicado 15% de desconto");
	}

	public void menuEditar(int pos) {
		String nome, sobrenome, documento, endereco, telefone; 
		int numeroVezes;
		int opcao = 0;
		while(opcao != 99){
			opcao = Integer.parseInt(JOptionPane.showInputDialog("Escolha qual item deseja editar:\n1 - Editar nome\n2 - Editar sobrenome\n3 - Editar documento\n"
					+ "4 - Editar endereço\n5 - Editar telefone\n6 - Editar quantas vezes cliente já alugou\n99 - Sair"));
			switch(opcao){
			case 1:
				nome = JOptionPane.showInputDialog("Inserir novo primeiro nome");
				edicaoNome(pos, nome);
				return;
			case 2:
				sobrenome = JOptionPane.showInputDialog("Inserir novo sobrenome");
				edicaoSobrenome(pos, sobrenome);
				return;
			case 3:
				documento = JOptionPane.showInputDialog("Inserir novo RG");
				edicaoDocumento(pos, documento);
				return;
			case 4:
				endereco = JOptionPane.showInputDialog("Inserir novo endereço (rua, CEP)");
				edicaoEndereco(pos, endereco);
				return;
			case 5: 
				telefone = JOptionPane.showInputDialog("Inserir novo telefone de contato (incluindo DDD");
				edicaoTelefone(pos, telefone);
				return;
			case 6:
				numeroVezes = Integer.parseInt(JOptionPane.showInputDialog("Informe quantas vezes o cliente já alugou aqui"));
				edicaoNumeroVezes(pos, numeroVezes);
				return;
			case 99:
				JOptionPane.showMessageDialog(null, "Saindo...");
				return;
			default:
				JOptionPane.showMessageDialog(null, "Escolha uma das opções na lista");
				break;
			}
		}
	}

	private void edicaoNome(int pos, String nome) {
		String nomeEditado;
		int i = 1;
		Nodo aux = inicio;
		if(inicio == null){
			JOptionPane.showMessageDialog(null, "Não há cadastros na lista");
		}
		if(pos == 1){
			nomeEditado = aux.cliente.setNome(nome); 
			JOptionPane.showMessageDialog(null, "Cadastro atualizado, nome = " + nomeEditado);
		} else {
			while(aux.seguinte != null){
				aux = aux.seguinte;
				i++;
			}
			if(pos > i || pos <= 0){
				JOptionPane.showMessageDialog(null, "Posição inválida");
			} else if (pos == 1){
				nomeEditado = aux.cliente.setNome(nome); 
				JOptionPane.showMessageDialog(null, "Cadastro atualizado, nome = " + nomeEditado);
			} else {
				aux = inicio;
				aux = localizarPosicao(inicio, pos);
				nomeEditado = aux.cliente.setNome(nome); 
				JOptionPane.showMessageDialog(null, "Cadastro atualizado, nome = " + nomeEditado);
			}
		}
	}
	
	private void edicaoSobrenome(int pos, String sobrenome) {
		String sobrenomeEditado;
		int i = 1;
		Nodo aux = inicio;
		if(inicio == null){
			JOptionPane.showMessageDialog(null, "Não há cadastros na lista");
		}
		if(pos == 1){
			sobrenomeEditado = aux.cliente.setSobrenome(sobrenome); 
			JOptionPane.showMessageDialog(null, "Cadastro atualizado, sobrenome = " + sobrenomeEditado);
		} else {
			while(aux.seguinte != null){
				aux = aux.seguinte;
				i++;
			}
			if(pos > i || pos <= 0){
				JOptionPane.showMessageDialog(null, "Posição inválida");
			} else if (pos == 1){
				sobrenomeEditado = aux.cliente.setSobrenome(sobrenome); 
				JOptionPane.showMessageDialog(null, "Cadastro atualizado, sobrenome = " + sobrenomeEditado);
			} else {
				aux = localizarPosicao(inicio, pos);
				sobrenomeEditado = aux.cliente.setSobrenome(sobrenome); 
				JOptionPane.showMessageDialog(null, "Cadastro atualizado, sobrenome = " + sobrenomeEditado);
			}
		}
	}

	private void edicaoDocumento(int pos, String documento) {
		String documentoEditado;
		int i = 1;
		Nodo aux = inicio;
		if(inicio == null){
			JOptionPane.showMessageDialog(null, "Não há cadastros na lista");
		}
		if(pos == 1){
			documentoEditado = aux.cliente.setDocumento(documento); 
			JOptionPane.showMessageDialog(null, "Cadastro atualizado, documento = " + documentoEditado);
		} else {
			while(aux.seguinte != null){
				aux = aux.seguinte;
				i++;
			}
			if(pos > i || pos <= 0){
				JOptionPane.showMessageDialog(null, "Posição inválida");
			} else if (pos == 1){
				documentoEditado = aux.cliente.setDocumento(documento); 
				JOptionPane.showMessageDialog(null, "Cadastro atualizado, documento = " + documentoEditado);
			} else {
				aux = localizarPosicao(inicio, pos);
				documentoEditado = aux.cliente.setDocumento(documento); 
				JOptionPane.showMessageDialog(null, "Cadastro atualizado, documento = " + documentoEditado);
			}
		}
	}

	private void edicaoEndereco(int pos, String endereco) {
		String enderecoEditado;
		int i = 1;
		Nodo aux = inicio;
		if(inicio == null){
			JOptionPane.showMessageDialog(null, "Não há cadastros na lista");
		}
		if(pos == 1){
			enderecoEditado = aux.cliente.setEndereco(endereco); 
			JOptionPane.showMessageDialog(null, "Cadastro atualizado, endereço = " + enderecoEditado);
		} else {
			while(aux.seguinte != null){
				aux = aux.seguinte;
				i++;
			}
			if(pos > i || pos <= 0){
				JOptionPane.showMessageDialog(null, "Posição inválida");
			} else if (pos == 1){
				enderecoEditado = aux.cliente.setEndereco(endereco);  
				JOptionPane.showMessageDialog(null, "Cadastro atualizado, endereço = " + enderecoEditado);
			} else {
				aux = localizarPosicao(inicio, pos);
				enderecoEditado = aux.cliente.setEndereco(endereco); 
				JOptionPane.showMessageDialog(null, "Cadastro atualizado, endereço = " + enderecoEditado);
			}
		}
	}
	
	private void edicaoTelefone(int pos, String telefone) {
		String telefoneEditado;
		int i = 1;
		Nodo aux = inicio;
		if(inicio == null){
			JOptionPane.showMessageDialog(null, "Não há cadastros na lista");
		}
		if(pos == 1){
			telefoneEditado = aux.cliente.setTelefone(telefone); 
			JOptionPane.showMessageDialog(null, "Cadastro atualizado, telefone = " + telefoneEditado);
		} else {
			while(aux.seguinte != null){
				aux = aux.seguinte;
				i++;
			}
			if(pos > i || pos <= 0){
				JOptionPane.showMessageDialog(null, "Posição inválida");
			} else if (pos == 1){
				telefoneEditado = aux.cliente.setTelefone(telefone); 
				JOptionPane.showMessageDialog(null, "Cadastro atualizado, telefone = " + telefoneEditado);
			} else {
				aux = localizarPosicao(inicio, pos);
				telefoneEditado = aux.cliente.setTelefone(telefone); 
				JOptionPane.showMessageDialog(null, "Cadastro atualizado, telefone = " + telefoneEditado);
			}
		}
	}
	
	private void edicaoNumeroVezes(int pos, int numeroVezes) {
		int numeroVezesEditado;
		int i = 1;
		Nodo aux = inicio;
		if(inicio == null){
			JOptionPane.showMessageDialog(null, "Não há cadastros na lista");
		}
		if(pos == 1){
			numeroVezesEditado = aux.cliente.setNumeroVezes(numeroVezes); 
			JOptionPane.showMessageDialog(null, "Cadastro atualizado, número de vezes = " + numeroVezesEditado);
		} else {
			while(aux.seguinte != null){
				aux = aux.seguinte;
				i++;
			}
			if(pos > i || pos <= 0){
				JOptionPane.showMessageDialog(null, "Posição inválida");
			} else if (pos == 1){
				numeroVezesEditado = aux.cliente.setNumeroVezes(numeroVezes); 
				JOptionPane.showMessageDialog(null, "Cadastro atualizado, número de vezes = " + numeroVezesEditado);
			} else {
				aux = localizarPosicao(inicio, pos);
				numeroVezesEditado = aux.cliente.setNumeroVezes(numeroVezes); 
				JOptionPane.showMessageDialog(null, "Cadastro atualizado, número de vezes = " + numeroVezesEditado);
			}
		}
	}

	public void incluirHistorico(int pos) {
		String historico = JOptionPane.showInputDialog("Incluir histórico");
		int i = 1;
		Nodo aux = inicio;
		if(inicio == null){
			JOptionPane.showMessageDialog(null, "Não há cadastros na lista");
		}
		if(pos == 1){
			aux.cliente.setHistoricoCliente(historico); 
			JOptionPane.showMessageDialog(null, "Histórico incluído");
		} else {
			while(aux.seguinte != null){
				aux = aux.seguinte;
				i++;
			}
			if(pos > i || pos <= 0){
				JOptionPane.showMessageDialog(null, "Posição inválida");
			} else if (pos == 1){
				aux.cliente.setHistoricoCliente(historico); 
				JOptionPane.showMessageDialog(null, "Histórico incluído");
			} else {
				aux = localizarPosicao(inicio, pos);
				aux.cliente.setHistoricoCliente(historico); 
				JOptionPane.showMessageDialog(null, "Histórico incluído");
			}
		}
	}

	public void verHistorico(int pos) {
		String pesquisado;
		int i = 1;
		Nodo aux = inicio;
		if(inicio == null){
			JOptionPane.showMessageDialog(null, "Não há cadastros na lista");
		}
		if(pos == 1){
			pesquisado = String.valueOf(aux.cliente.getHistoricoCliente()); 
			JOptionPane.showMessageDialog(null, pesquisado);
		} else {
			while(aux.seguinte != null){
				aux = aux.seguinte;
				i++;
			}
			if(pos > i || pos <= 0){
				JOptionPane.showMessageDialog(null, "Posição inválida");
			} else if (pos == 1){
				pesquisado = String.valueOf(aux.cliente.getHistoricoCliente()); 
				JOptionPane.showMessageDialog(null, pesquisado);
			} else {
				aux = inicio;
				aux = localizarPosicao(inicio, pos);
				pesquisado = String.valueOf(aux.cliente.getHistoricoCliente()); 
				JOptionPane.showMessageDialog(null, pesquisado);
			}
		}
	}
	
	public void verificarDuplicidade(String nomeArq) {
		boolean achou = false;
		if(nomeArq.equals("")) {
			JOptionPane.showMessageDialog(null, "Inserir um Login válido\nCliente não salvo, retornando a tela anterior");
		} else {
			try {
				BufferedReader br = new BufferedReader(new FileReader("ClienteDB.csv"));
				String s = "";
				while((s = br.readLine()) != null){
					String data[] = new String[7];
					data = s.split("§");
					if(data[0].equalsIgnoreCase(nomeArq)){
						achou = true;
						JOptionPane.showMessageDialog(null, "Login semelhante já existe");
						break;
						}
					}
				if (!achou) {
					inserirDB(nomeArq);
				}
				br.close();
			} catch(Exception e){}
		}
	}

	public String inserirDB(String nomeArq) {
		String removido = null;
		if(inicio == null){
			JOptionPane.showMessageDialog(null, "Lista vazia");
		} else {
			try {
				removido = inicio.cliente.toString();
				File f = new File(nomeArq + ".csv");
				PrintWriter pw = new PrintWriter(new FileOutputStream(f, true));
				pw.append(removido);
				pw.append("\n");
				pw.close();
				inicio = inicio.seguinte;
				File f2 = new File("ClienteDB.csv");
				PrintWriter pw2 = new PrintWriter(new FileOutputStream(f2, true));
				pw2.append(nomeArq);
				pw2.append("\n");
				pw2.close();
				JOptionPane.showMessageDialog(null, "Cleinte salvo na Base de Dados");
			} catch(Exception e){}
		}
		return removido;
	}

	public void exibirDB() { 
		try {
			BufferedReader br = new BufferedReader(new FileReader("ClienteDB.csv"));
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
	
	public void buscarClienteDB(String nomeArq) {
		verificarNome(nomeArq);
		try {
			BufferedReader br = new BufferedReader(new FileReader(nomeArq + ".csv"));
			String s = "";
			String row = "";
			while((s = br.readLine()) != null){
				String data[] = new String[7];
				data = s.split("§");
				if(data[0] != null){
					for(int i = 0; i<7; i++){
						row = row + nomearDB(i) + data[i] + "\n";
					}
				}
			}
			JOptionPane.showMessageDialog(null, row);
			br.close();
		} catch(Exception e){}
	}

	private String nomearDB(int i) {
		switch(i){
		case 0:
			return "Nome = ";
		case 1:
			return "Sobrenome = ";
		case 2:
			return "Documento = ";
		case 3:
			return "Endereço = ";
		case 4:
			return "Telefone = ";
		case 5:
			return "Número de vezes que alugou = ";
		case 6:
			return "Histórico = ";
		default:
			return null;
		}
	}
	
	public void buscarClienteDBRestrito(String nomeArq) {
		verificarNome(nomeArq);
		try {
			BufferedReader br = new BufferedReader(new FileReader(nomeArq + ".csv"));
			String s = "";
			String row = "";
			while((s = br.readLine()) != null){
				String data[] = new String[6];
				data = s.split("§");
				if(data[0] != null){
					for(int i = 0; i<6; i++){
						row = row + nomearDB(i) + data[i] + "\n";
					}
				}
			}
			JOptionPane.showMessageDialog(null, row);
			br.close();
		} catch(Exception e){}
	}
	
	public void atualizarDB(String nomeArq) {
		boolean ok = verificarNome(nomeArq);
		if(ok) {
			String ArrayDados[] = {"Nome:","Sobrenome:","Documento:","Endereço:","Telefone:","Número de vezes que alugou:","Histórico:"};
			int yesNo[] = new int[7];
			String toUpdate[] = new String[7];
			
			JOptionPane.showMessageDialog(null, "Escolha qual dado quer atualizar");
			for(int i = 0; i<7; i++){
				int temp = Integer.parseInt(JOptionPane.showInputDialog(ArrayDados[i] + "\n0: Não alterar\n1: Alterar"));
				yesNo[i] = verificarYesNo(temp);
			}
			
			JOptionPane.showMessageDialog(null, "Insira os novos dados");
			for(int i = 0; i<7; i++){
				if(yesNo[i] == 1){
					JOptionPane.showMessageDialog(null, ArrayDados[i] + "");
					String tempVal = JOptionPane.showInputDialog("Novo dado:");
					toUpdate[i] = tempVal;
				} 
			}
				StringBuffer sb = new StringBuffer();
				try {
					BufferedReader br = new BufferedReader(new FileReader(nomeArq + ".csv"));
					String s = br.readLine();
					String data[] = new String[7];
					data = s.split("§");
					String row = "";
					for(int i = 0; i<7; i++){
						if(yesNo[i] == 1){
							if(i == 6) {
								if(data[6].equals("null")) {
									row = row + toUpdate[i];
								} else {
									row = row + data[i] + ", " + toUpdate[i];
								}
							} else {
								row = row + toUpdate[i] + "§";
							}
						} else {
							if(i == 6) {
								row = row + data[i];
							} else {
								row = row + data[i] + "§";
							}
						}
					}
					sb.append(row);
					sb.append("\n");
					File f = new File(nomeArq + ".csv");
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

	public boolean verificarNome(String nome) {
		boolean achou = false;
		try {
			BufferedReader br = new BufferedReader(new FileReader("ClienteDB.csv"));
			String s = "";
			while((s = br.readLine()) != null){
				String data[] = new String[7];
				data = s.split("§");
				if(data[0].equalsIgnoreCase(nome)){
					achou = true;
					break;
					}
				}
			if (!achou) {
				JOptionPane.showMessageDialog(null, "Login não consta na lista");
			}
			br.close();
		} catch(Exception e){}
		return achou;
	}

	public void atualizarDBRestrito(String nomeArq) {
		boolean ok = verificarNome(nomeArq);
		if(ok) {
			String ArrayDados[] = {"Nome:","Sobrenome:","Documento:","Endereço:","Telefone:","Número de vezes que alugou:","Histórico:"};
			int yesNo[] = new int[7];
			String toUpdate[] = new String[7];
			JOptionPane.showMessageDialog(null, "Escolha qual dado quer atualizar");
			for(int i = 0; i<7; i++){
				if(i == 6) {
					yesNo[i] = 0;
				} else {
					int temp = Integer.parseInt(JOptionPane.showInputDialog(ArrayDados[i] + " (0: Não alterar/1: Alterar)"));
					yesNo[i] = temp;
				}
			}
		
			JOptionPane.showMessageDialog(null, "Insira os novos dados");
			for(int i = 0; i<7; i++){
				JOptionPane.showMessageDialog(null, ArrayDados[i] + "");
				if(yesNo[i] == 1){
					String tempVal = JOptionPane.showInputDialog("Novo dado:");
					toUpdate[i] = tempVal;
				} else {
					JOptionPane.showMessageDialog(null, "Não será alterado");
				}
			}
				StringBuffer sb = new StringBuffer();
				try {
					BufferedReader br = new BufferedReader(new FileReader(nomeArq + ".csv"));
					String s = br.readLine();
					String data[] = new String[7];
					data = s.split("§");
					String row = "";
					for(int i = 0; i<7; i++){
						if(yesNo[i] == 1){
							if(i == 6) {
								row = row + toUpdate[i];
							} else {
								row = row + toUpdate[i] + "§";
							}
						} else {
							if(i == 6) {
								row = row + data[i];
							} else {
								row = row + data[i] + "§";
							}
						}
					}
					sb.append(row);
					sb.append("\n");
					File f = new File(nomeArq + ".csv");
					PrintWriter pw = new PrintWriter(new FileOutputStream(f, false));
					pw.print(sb.toString());
					pw.close();
					br.close();
			} catch(Exception e){}
		} else {
			return;
		}
	}

	public void exibirDescontoDB(String nomeArq) {
		boolean ok = verificarNome(nomeArq);
		if(ok) {
			try {
				BufferedReader br = new BufferedReader(new FileReader(nomeArq + ".csv"));
				String s = br.readLine();
				String data[] = new String[7];
				data = s.split("§");
				int desconto = Integer.parseInt(data[5]);
				aplicarDesconto(desconto);
				br.close();
			} catch(Exception e){}
		} else {
			return;
		}
	}

	public void excluirClienteDB() {
		String nomeArq = JOptionPane.showInputDialog("Inserir nome do arquivo");
		verificarNome(nomeArq);
		File f = new File(nomeArq + ".csv");
		if(f.delete()) {
			JOptionPane.showMessageDialog(null, "Excluído o cliente " + nomeArq);
		} else {
			JOptionPane.showMessageDialog(null, "Login não encontrado");
		}	
		StringBuffer sb = new StringBuffer();
		try {
			BufferedReader br = new BufferedReader(new FileReader("ClienteDB.csv"));
			String s = "";
			while((s = br.readLine()) != null){
				if(s.equalsIgnoreCase(nomeArq)){
					JOptionPane.showMessageDialog(null, "Retirando do índice");
				} else { 
					sb.append(s);
					sb.append("\n");
				}
				File f2 = new File("ClienteDB.csv");
				PrintWriter pw = new PrintWriter(new FileOutputStream(f2, false));
				pw.print(sb.toString());
				pw.close();
			}
		} catch(Exception e){}
}

	public double receberDescontoDB(String nomeArq) {
		double descTotal = 0;
		try {
			BufferedReader br = new BufferedReader(new FileReader(nomeArq + ".csv"));
			String s = br.readLine();
			String data[] = new String[7];
			data = s.split("§");
			int desconto = Integer.parseInt(data[5]);
			descTotal = aplicarDesconto2(desconto);
			br.close();
			return descTotal;
		} catch(Exception e){}
		return descTotal;
	}
	
	private double aplicarDesconto2(int desconto) {
		if(desconto < 2){
			return 0;
		} else if(desconto >= 2 && desconto < 3){
			return 2;
		} else if(desconto >= 3 && desconto < 4){
			return 5;
		} else if(desconto >= 4 && desconto < 5){
			return 10;
		} else 
			return 15;
	}

	public void addNumeroVezes(String nomeArq) {
		StringBuffer sb = new StringBuffer();
		try {
			BufferedReader br = new BufferedReader(new FileReader(nomeArq + ".csv"));
			String s = br.readLine();
			String data[] = new String[7];
			data = s.split("§");
			int numVezes = Integer.parseInt(data[5]) + 1;
			String row = "";
			for(int i = 0; i<7; i++){
				if(i == 5){
					row = row + numVezes + "§";
				} else if (i == 6) {
						row = row + data[i];
				} else {
						row = row + data[i] + "§";
				}
			}
			sb.append(row);
			sb.append("\n");
			File f = new File(nomeArq + ".csv");
			PrintWriter pw = new PrintWriter(new FileOutputStream(f, false));
			pw.print(sb.toString());
			pw.close();
			br.close();
	} catch(Exception e){}
	}
}

