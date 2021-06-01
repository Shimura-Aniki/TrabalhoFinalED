package tema;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.PrintWriter;

import javax.swing.JOptionPane;

public class ListaTema {
	private static NodoTema inicio;
	Tema tema= null;
	
	public ListaTema(){
		inicio = null;
	}
	
	public static void addTema(Tema tema) {
		NodoTema no = new NodoTema(tema); 
		if(inicio == null) {
			inicio = no;
			no.anterior = null;
			no.seguinte = null;
		} else {
			NodoTema aux = localizaFim(inicio);
			aux.seguinte = no;
			no.anterior = aux;
			no.seguinte = null;	
		}
	}

	private static NodoTema localizaFim(NodoTema inicio2) {
		if(inicio2.seguinte != null){
			return localizaFim(inicio2.seguinte);
		}
		return inicio2;
	}

	public Tema rmvTema(int pos2) {
		Tema removido = null;
		int i = 1;
		NodoTema aux = inicio;
		if(inicio == null){
			JOptionPane.showMessageDialog(null, "Não há cadastros na lista");
		}
		if(pos2 == 1){
			removido = rmvInicio();
			return removido;
		} else {
			while(aux.seguinte != null){
				aux = aux.seguinte;
				i++;
			}
			if(pos2 > i || pos2 <= 0){
				JOptionPane.showMessageDialog(null, "Posição inválida");
				return null;
			} else if (pos2 == i){
				removido = rmvFim(i);
				return removido;
			}else {
				aux = localizarPosicao(inicio, pos2);
				NodoTema aux2 = localizarPosicao(inicio, pos2-1);
			removido = aux.tema;
			aux2.seguinte = aux.seguinte;
			aux.anterior = null;
			return removido;
			}
		}
	}

	private NodoTema localizarPosicao(NodoTema inicio2, int pos2) {
		if(pos2 > 1) {
			return localizarPosicao(inicio2.seguinte, pos2-1);
		}
		return inicio2;
	}

	private Tema rmvInicio() {
		Tema removido = null;
		removido = inicio.tema;
		inicio = inicio.seguinte;
		if(inicio !=null) {
			inicio.anterior = null;
		}
		return removido;
	}
	
	private Tema rmvFim(int i) {
		Tema removido = null;
		NodoTema aux = localizarPosicao(inicio, i);
		NodoTema aux2 = aux.anterior;
		removido = aux.tema;
		aux.anterior = null;
		aux2.seguinte = null;
		return removido;
	}

	public void dadosTema(int pos2) {
		Tema listado = null;
		int i = 1;
		NodoTema aux = inicio;
		if(inicio == null){
			JOptionPane.showMessageDialog(null, "Não há cadastros na lista");
		}
		if(pos2 == 1){
			listado = aux.tema;
			mostrarListado(listado);
		} else {
			while(aux.seguinte != null){
				aux = aux.seguinte;
				i++;
			}
			if(pos2 > i || pos2 <= 0){
				JOptionPane.showMessageDialog(null, "Posição inválida");
			} else if (pos2 == i){
				listado = aux.tema;
				mostrarListado(listado);
			} else {
				aux = localizarPosicao(inicio, pos2);
				listado = aux.tema;
				mostrarListado(listado);
			}
		}
	}

	private void mostrarListado(Tema listado) {
		String list = "";
		String aux = listado.toString();
		String data[] = new String[3];
		data = aux.split("§");
		if(data[0] != null) {
			for(int i = 0; i<3; i++) {
				list = list + nomearLista(i) + data[i] + "\n";
			}
		}
		JOptionPane.showMessageDialog(null, list);
	}

	public String listarTema() {
		NodoTema aux = inicio;
		String list = "";
		while(aux != null){
			String aux2 = aux.tema.toString();
			String data[] = new String[3];
			data = aux2.split("§");
			if(data[0] != null) {
				for(int i = 0; i<3; i++) {
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
			return "Tipo = ";
		case 1:
			return "Cenario = ";
		case 2:
			return "Valor da diária = ";
		default:
			return null;
		}
	}

		public void menuEditarTema(int pos2) {
		String tipo, cenario; 
		int valorDiaria;
		int opcao = 0;
		while(opcao != 99){
			opcao = Integer.parseInt(JOptionPane.showInputDialog("Escolha qual item deseja editar:\n1 - Editar tipo\n2 - Editar cenário\n3 - Editar quantidade"
					+"máxima de pessoas\n99 - Sair"));
			switch(opcao){
			case 1:
				tipo = JOptionPane.showInputDialog("Inserir novo tipo de festa");
				edicaoTipo(pos2, tipo);
				return;
			case 2:
				cenario = JOptionPane.showInputDialog("Inserir novo cenário");
				edicaoCenario(pos2, cenario);
				return;
			case 3:
				valorDiaria = Integer.parseInt(JOptionPane.showInputDialog("Inserir nova quantidade máxima de pessoas"));
				edicaoValorD(pos2, valorDiaria);
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

	private void edicaoTipo(int pos2, String tipo) {
		String tipoEditado;
		int i = 1;
		NodoTema aux = inicio;
		if(inicio == null){
			JOptionPane.showMessageDialog(null, "Não há cadastros na lista");
		}
		if(pos2 == 1){
			tipoEditado = aux.tema.setTipo(tipo); 
			JOptionPane.showMessageDialog(null, "Cadastro atualizado, nome = " + tipoEditado);
		} else {
			while(aux.seguinte != null){
				aux = aux.seguinte;
				i++;
			}
			if(pos2 > i || pos2 <= 0){
				JOptionPane.showMessageDialog(null, "Posição inválida");
			} else if (pos2 == 1){
				tipoEditado = aux.tema.setTipo(tipo); 
				JOptionPane.showMessageDialog(null, "Cadastro atualizado, nome = " + tipoEditado);
			} else {
				aux = inicio;
				aux = localizarPosicao(inicio, pos2);
				tipoEditado = aux.tema.setTipo(tipo); 
				JOptionPane.showMessageDialog(null, "Cadastro atualizado, nome = " + tipoEditado);
			}
		}
	}
	
	private void edicaoCenario(int pos2, String cenario) {
		String cenarioEditado;
		int i = 1;
		NodoTema aux = inicio;
		if(inicio == null){
			JOptionPane.showMessageDialog(null, "Não há cadastros na lista");
		}
		if(pos2 == 1){
			cenarioEditado = aux.tema.setCenario(cenario); 
			JOptionPane.showMessageDialog(null, "Cadastro atualizado, sobrenome = " + cenarioEditado);
		} else {
			while(aux.seguinte != null){
				aux = aux.seguinte;
				i++;
			}
			if(pos2 > i || pos2 <= 0){
				JOptionPane.showMessageDialog(null, "Posição inválida");
			} else if (pos2 == 1){
				cenarioEditado = aux.tema.setCenario(cenario); 
				JOptionPane.showMessageDialog(null, "Cadastro atualizado, sobrenome = " + cenarioEditado);
			} else {
				aux = localizarPosicao(inicio, pos2);
				cenarioEditado = aux.tema.setCenario(cenario); 
				JOptionPane.showMessageDialog(null, "Cadastro atualizado, sobrenome = " + cenarioEditado);
			}
		}
	}

	private void edicaoValorD(int pos2, int valorDiaria) {
		int ValorDEditado;
		int i = 1;
		NodoTema aux = inicio;
		if(inicio == null){
			JOptionPane.showMessageDialog(null, "Não há cadastros na lista");
		}
		if(pos2 == 1){
			ValorDEditado = aux.tema.setValorDiaria(valorDiaria); 
			JOptionPane.showMessageDialog(null, "Cadastro atualizado, documento = " + ValorDEditado);
		} else {
			while(aux.seguinte != null){
				aux = aux.seguinte;
				i++;
			}
			if(pos2 > i || pos2 <= 0){
				JOptionPane.showMessageDialog(null, "Posição inválida");
			} else if (pos2 == 1){
				ValorDEditado = aux.tema.setValorDiaria(valorDiaria); 
				JOptionPane.showMessageDialog(null, "Cadastro atualizado, documento = " + ValorDEditado);
			} else {
				aux = localizarPosicao(inicio, pos2);
				ValorDEditado = aux.tema.setValorDiaria(valorDiaria);
				JOptionPane.showMessageDialog(null, "Cadastro atualizado, documento = " + ValorDEditado);
			}
		}
	}

	public void verificarDuplicidadeTema(String nomeArq) {
		boolean achou = false;
		if(nomeArq.equals("")) {
			JOptionPane.showMessageDialog(null, "Inserir um nome válido\nTema não salvo, retornando a tela anterior");
		} else {
			try {
				BufferedReader br = new BufferedReader(new FileReader("TemaDB.csv"));
				String s = "";
				while((s = br.readLine()) != null){
					String data[] = new String[3];
					data = s.split("§");
					if(data[0].equalsIgnoreCase(nomeArq)){
						achou = true;
						JOptionPane.showMessageDialog(null, "Login semelhante já existe");
						break;
						}
					}
				if (!achou) {
					inserirTemaDB(nomeArq);
				}
				br.close();
			} catch(Exception e){}
		}
	}

	
	public String inserirTemaDB(String nomeArq) {
		String removido = null;
		if(inicio == null){
			JOptionPane.showMessageDialog(null, "Lista vazia");
		} else {
			try {
				removido = inicio.tema.toString();
				File f = new File(nomeArq + ".csv");
				PrintWriter pw = new PrintWriter(new FileOutputStream(f, true));
				pw.append(removido);
				pw.append("\n");
				pw.close();
				File f2 = new File("TemaDB.csv");
				PrintWriter pw2 = new PrintWriter(new FileOutputStream(f2, true));
				pw2.append(nomeArq);
				pw2.append("\n");
				pw2.close();
				JOptionPane.showMessageDialog(null, "Tema salvo na Base de Dados");
				inicio = inicio.seguinte;
			} catch(Exception e){}
		}
		return removido;
	}

	public void exibirTemaDB() {
		try {
			BufferedReader br = new BufferedReader(new FileReader("TemaDB.csv"));
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

	public void buscarTemaDB(String nome) {
		verificarNome(nome);
		try {
			BufferedReader br = new BufferedReader(new FileReader(nome + ".csv"));
			String s = "";
			String row = "";
			while((s = br.readLine()) != null){
				String data[] = new String[3];
				data = s.split("§");
				if(data[0] != null){
					for(int i = 0; i<3; i++){
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
			return "Tipo = ";
		case 1:
			return "Cenario = ";
		case 2:
			return "Valor da diária = ";
		default:
			return null;
		}
	}
	
	public void atualizarTemaDB(String nome) {
		boolean ok = verificarNome(nome);
		if(ok) {
			String ArrayDados[] = {"Tipo","Cenário:","Valor da diária:"};
			int yesNo[] = new int[3];
			String toUpdate[] = new String[3];
			
			JOptionPane.showMessageDialog(null, "Escolha qual dado quer atualizar");
			for(int i = 0; i<3; i++){
				int temp = Integer.parseInt(JOptionPane.showInputDialog(ArrayDados[i] + "\n0: Não alterar\n1: Alterar"));
				yesNo[i] = verificarYesNo(temp);
			}
			
			JOptionPane.showMessageDialog(null, "Insira os novos dados");
			for(int i = 0; i<3; i++){
				JOptionPane.showMessageDialog(null, ArrayDados[i] + "");
				if(yesNo[i] == 1){
					JOptionPane.showMessageDialog(null, ArrayDados[i] + "");
					String tempVal = JOptionPane.showInputDialog("Novo dado:");
					toUpdate[i] = tempVal;
			}
				StringBuffer sb = new StringBuffer();
				try {
					BufferedReader br = new BufferedReader(new FileReader(nome + ".csv"));
					String s = br.readLine();
					String data[] = new String[3];
					data = s.split("§");
					String row = "";
					for(int j = 0; j<3; j++){
						if(yesNo[i] == 1){
							if(j == 2) {
								row = row + toUpdate[i];
							} else {
								row = row + toUpdate[i] + "§";
							}
						} else {
							if(j == 2) {
								row = row + data[i];
							} else {
								row = row + data[i] + "§";
							}
						}
					}
					
					sb.append(row);
					sb.append("\n");
					File f = new File(nome + ".csv");
					PrintWriter pw = new PrintWriter(new FileOutputStream(f, false));
					pw.print(sb.toString());
					pw.close();
					br.close();
				} catch(Exception e){}
			}
	} else {
		return;
	}
}
	
	private boolean verificarNome(String nome) {
		boolean achou = false;
		try {
			BufferedReader br = new BufferedReader(new FileReader("TemaDB.csv"));
			String s = "";
			while((s = br.readLine()) != null){
				String data[] = new String[3];
				data = s.split("§");
				if(data[0].equalsIgnoreCase(nome)){
					achou = true;
					break;
					}
				}
			if (!achou) {
				JOptionPane.showMessageDialog(null, "Tema não consta na lista");
			}
			br.close();
		} catch(Exception e){}
		return achou;
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

	public void excluirTemaDB() {
		String nomeArq = JOptionPane.showInputDialog("Inserir nome do arquivo");
		verificarNome(nomeArq);
		File f = new File(nomeArq + ".csv");
		if(f.delete()) {
			JOptionPane.showMessageDialog(null, "Excluído o tema " + nomeArq);
		} else {
			JOptionPane.showMessageDialog(null, "Tema não encontrado");
		}	
		StringBuffer sb = new StringBuffer();
		try {
			BufferedReader br = new BufferedReader(new FileReader("TemaDB.csv"));
			String s = "";
			while((s = br.readLine()) != null){
				if(s.equalsIgnoreCase(nomeArq)){
					JOptionPane.showMessageDialog(null, "Retirando do índice");
				} else { 
					sb.append(s);
					sb.append("\n");
				}
				File f2 = new File("TemaDB.csv");
				PrintWriter pw = new PrintWriter(new FileOutputStream(f2, false));
				pw.print(sb.toString());
				pw.close();
			}
		} catch(Exception e){}
}

	public boolean verificarTemaAluguel(String tema2) {
		boolean achou = false;
		try {
			BufferedReader br = new BufferedReader(new FileReader("TemaDB.csv"));
			String s = "";
			while((s = br.readLine()) != null){
				if(s.equalsIgnoreCase(tema2)){
					achou = true;
					break;
					}
				}
			if (!achou) {
				JOptionPane.showMessageDialog(null, "Tema não consta na lista");
			}
			br.close();
		} catch(Exception e){}
		return achou;
	}

	public boolean verificarDisponibilidadeTema(String tema2) {
		boolean disponivel = false;
		try {
			BufferedReader br = new BufferedReader(new FileReader(tema2 + ".csv"));
			String s = "";
			while((s = br.readLine()) != null){
				String dados[] = new String[5];
				dados = s.split("§");
				if(dados[3].equals(null)) {
					JOptionPane.showMessageDialog(null, "Tema disponível");
					disponivel = true;
				} else {
					JOptionPane.showMessageDialog(null, "Tema indisponível, de " + dados[3] + " à " + dados[4]);
				}
				}
			br.close();
		} catch(Exception e){}
		return disponivel;
	}

	public double receberDiaria(String tema2) {
		double valor = 0;
		try {
			BufferedReader br = new BufferedReader(new FileReader(tema2 + ".csv"));
			String s = br.readLine();
			String dados[] = new String[3];
			dados = s.split("§");
			valor = Double.parseDouble(dados[2]);
			br.close();
		} catch(Exception e){}
		return valor;
	}
}
