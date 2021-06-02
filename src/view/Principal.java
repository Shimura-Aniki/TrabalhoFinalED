/*
Henrique A. Chaves RA 1110481912011
Isis Mazur Rakauskas RA 1110482113025
Leandro Shimura Barea RA 1110482013020
Wanderson Rodrigues da Silva RA 1110482013012
 */

package view;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.ParseException;

import javax.swing.JOptionPane;

import aluguel.FilaAluguel;
import cliente.Cliente;
import cliente.ListaLigada;
import tema.ListaTema;
import tema.Tema;
import usuario.FilaUsuario;

public class Principal {

	public static void main(String[] args) throws Exception {
		verficarArquivos();
		int op = 0;
		String senha, login;
		while(op != 99){
			op = Integer.parseInt(JOptionPane.showInputDialog("Informar quem est� acessando o sistema:\n1 - Funcion�rio\n2 - Gerente"
					+ "\n99 - Sair"));
			switch(op){
			case 1:
				login = JOptionPane.showInputDialog("Informar login");
				acessoFuncionario(login);
				break;
			case 2:
				senha = JOptionPane.showInputDialog("Informar senha");
				acessoAdmin(senha);
				break;
			case 99:
				JOptionPane.showMessageDialog(null, "Saindo...");
				return;
			default:
				JOptionPane.showMessageDialog(null, "Escolha uma das op��es na lista");
				break;
			}
		}
	}

	private static void acessoFuncionario(String login) throws ParseException, IOException {
		FilaUsuario usuario = new FilaUsuario(1);
		boolean ok = usuario.verificarUsuario(login);
		if(ok) {
			String senha = JOptionPane.showInputDialog("Informar senha");
			boolean verf = usuario.verificarSenha(login, senha);
			if(verf) {
				menuFuncionario();
			} else {
				return;
			} 
		} else {
			return;
		}
	}

	public static boolean verificarSenhaAdmin(String pw) {
		try {
			BufferedReader br = new BufferedReader(new FileReader("Admin.txt"));
			String s = br.readLine();
			if(s.equals(pw)) {
				return true;
			} else {
				JOptionPane.showMessageDialog(null, "Senha incorreta");
				return false;
			}
		} catch(Exception e){}
		return false;
	}

	private static void acessoAdmin(String senha) throws ParseException, IOException {
		boolean ok = verificarSenhaAdmin(senha);
		if(ok){
			menuAdmin();
		} else {
			JOptionPane.showMessageDialog(null, "Senha incorreta");
		}
	}

	public static void trocarSenhaAdmin(String senha) {
		boolean ok = verificarSenhaAdmin(senha);
		if(ok) {
			String novaSenha = JOptionPane.showInputDialog("Inserir nova senha\nEsta senha � case sensitive");
			File f = new File("Admin.txt");
			PrintWriter pw;
			try {
				pw = new PrintWriter(new FileOutputStream(f, false));
				pw.append(novaSenha);
				pw.close();
			} catch (FileNotFoundException e) {}
		} else {
			return;
		}		
	}

	private static void menuFuncionario() throws ParseException, IOException {
		int op = 0;
		while(op != 99){
			op = Integer.parseInt(JOptionPane.showInputDialog("1 - Manter Cliente\n2 - Manter Tema\n3 - Agenda/Aluguel\n"
					+ "4 - Base de dados de clientes\n5 - Base de dados de temas\n99 - Sair"));
			switch(op){
			case 1:
				menuFuncionarioCliente();
				break;
			case 2:
				menuTema();
				break;
			case 3:
				menuAluguel();
				break;
			case 4:
				menuDBClienteRestrito();
				break;
			case 5:
				menuDBTema();
				break;
			case 99:
				JOptionPane.showMessageDialog(null, "Voltando a tela inicial...");
				return;
			default:
				JOptionPane.showMessageDialog(null, "Escolha uma das op��es na lista");
				break;
			}
		}
	}

	private static void menuFuncionarioCliente() {
		ListaLigada lista = new ListaLigada();
		int op = 0;
		String nome, sobrenome, documento, endereco, telefone, codigo; 
		int numeroVezes, pos;
		while(op != 99){
			op = Integer.parseInt(JOptionPane.showInputDialog("1 - Adicionar novo cliente\n2 - Editar cliente\n3 - Excluir cliente\n"
					+ "4 - Consultar dados de cliente\n5 - Listar clientes\n6 - Verificar se cliente pode receber desconto\n"
					+ "7 - Salvar primeiro item da lista na base de dados\n99 - Sair"));
			switch(op){
			case 1:
				nome = JOptionPane.showInputDialog("Inserir primeiro nome");
				sobrenome = JOptionPane.showInputDialog("Inserir sobrenome"); 
				documento = JOptionPane.showInputDialog("Inserir RG");
				endereco = JOptionPane.showInputDialog("Inserir endere�o (rua, CEP)");
				telefone = JOptionPane.showInputDialog("Inserir telefone de contato (incluindo DDD");
				numeroVezes = Integer.parseInt(JOptionPane.showInputDialog("Informe quantas vezes o cliente j� alugou aqui"));
				lista.addCliente(new Cliente(nome, sobrenome, documento, endereco, telefone, numeroVezes));
				break;
			case 2:
				pos = Integer.parseInt(JOptionPane.showInputDialog("Informar posi��o do cadastro que ser� editado"));
				lista.menuEditar(pos);
				break;
			case 3:
				pos = Integer.parseInt(JOptionPane.showInputDialog("Informar a posi��o do cadastro que ser� exclu�da"));
				lista.rmvCliente(pos);
				break;
			case 4:
				pos = Integer.parseInt(JOptionPane.showInputDialog("Informar posi��o do cadastro que ser� consultado"));
				lista.dadosClienteRestrito(pos);
				break;
			case 5: 
				JOptionPane.showMessageDialog(null, lista.listarClientesRestrito());
				break;
			case 6:
				pos = Integer.parseInt(JOptionPane.showInputDialog("Informar posi��o do cliente que ser� consultado"));
				lista.calculoDesconto(pos);
				break;
			case 7:
				codigo = JOptionPane.showInputDialog("Inserir c�digo que ser� utilizado para nomear o cliente");
				lista.verificarDuplicidade(codigo);
				break;
			case 99:
				JOptionPane.showMessageDialog(null, "Voltando ao menu inicial...");
				return;
			default:
				JOptionPane.showMessageDialog(null, "Escolha uma das op��es na lista");
				break;
			}
		}
	}

	private static void menuDBClienteRestrito() {
		ListaLigada lista = new ListaLigada();
		int op = 0;
		String nome;
		while(op != 99){
			op = Integer.parseInt(JOptionPane.showInputDialog("1 - Visualizar lista de clientes\n2 - Visualizar dados de cliente espec�fico na base de dados"
					+ "\n3 - Editar dado de cliente na base de dados\n4 - Verificar se cliente na base de dados tem desconto"
					+ "\n99 - Sair"));
			switch(op){
			case 1:
				JOptionPane.showMessageDialog(null, "Clientes na base de dados:\n(para visualizar os dados, use a op��o 2)");	
				lista.exibirDB();
				break;
			case 2:
				nome = JOptionPane.showInputDialog("Inserir c�digo do cliente que ser� buscado");
				lista.buscarClienteDBRestrito(nome);
				break;
			case 3:
				nome = JOptionPane.showInputDialog("Inserir c�digo do cliente cujos dados ser�o alterados");
				lista.atualizarDBRestrito(nome);
				break;
			case 4:
				nome = JOptionPane.showInputDialog("Inserir c�digo do cliente que ser� verificado o desconto");
				lista.exibirDescontoDB(nome);
				break;
			case 99:
				JOptionPane.showMessageDialog(null, "Voltando ao menu inicial...");
				return;
			default:
				JOptionPane.showMessageDialog(null, "Escolha uma das op��es na lista");
				break;
			}
		}
	}

	private static void menuTema() {
		ListaTema lista = new ListaTema();
		int op = 0;
		String tipo = "", cenario, codigo; 
		int valorD, pos2, numTipo;

		while(op != 99){
			op = Integer.parseInt(JOptionPane.showInputDialog("1 - Adicionar novo tema\n2 - Editar tema\n3 - Excluir tema\n"
					+ "4 - Consultar dados dos temas\n5 - Listar temas\n6 - Salvar primeiro item da lista na base de dados\n99 - Sair"));
			switch(op){
			case 1:
				numTipo = Integer.parseInt(JOptionPane.showInputDialog("Escolha o tipo de festa \n1 - Festa de casamento\n2 - Festa de 15 anos"
						+ "\n3 - Festa de anivers�rio\n4 - Festa empresarial\n5 - outros"));
				tipo = tipoFesta(numTipo);
				cenario = JOptionPane.showInputDialog("Informe o cen�rio desejado");
				valorD =  Integer.parseInt(JOptionPane.showInputDialog("Informe o valor da di�ria"));
				ListaTema.addTema(new Tema (tipo, cenario, valorD));
				break;
			case 2:
				pos2 = Integer.parseInt(JOptionPane.showInputDialog("Informar posi��o do cadastro que ser� editado"));
				lista.menuEditarTema(pos2);
				break;
			case 3:
				pos2 = Integer.parseInt(JOptionPane.showInputDialog("Informar a posi��o do cadastro que ser� exclu�da"));
				lista.rmvTema(pos2);
				break;
			case 4:
				pos2 = Integer.parseInt(JOptionPane.showInputDialog("Informar posi��o do cadastro que ser� consultado"));
				lista.dadosTema(pos2);
				break;
			case 5: 
				JOptionPane.showMessageDialog(null, lista.listarTema());
				break;
			case 6:
				codigo = JOptionPane.showInputDialog("Inserir c�digo que ser� utilizado para nomear o cliente");
				lista.verificarDuplicidadeTema(codigo);
				break;
			case 99:
				JOptionPane.showMessageDialog(null, "Voltando ao menu inicial...");
				return;
			default:
				JOptionPane.showMessageDialog(null, "Escolha uma das op��es na lista");
				break;
			}
		}
	}

	private static String tipoFesta(int opcao) {
		switch(opcao){
		case 1:
			return "Festa de casamento";
		case 2:
			return "Festa de 15 anos";
		case 3:
			return "Festa de anivers�rio";
		case 4:
			return "Festa empresarial";
		case 5:
			String outros = JOptionPane.showInputDialog("Informe o tipo de festa");
			return outros;
		default:
			JOptionPane.showMessageDialog(null, "Op��o inv�lida, informa��o n�o ser� adicionada (incluir em 'editar')");
			break;
	}
		return null;
	}

	private static void menuAluguel() throws ParseException, IOException {
		FilaAluguel aluguel = new FilaAluguel(5);
		int op = 0;
		while (op != 99) {
			op = Integer.parseInt(JOptionPane.showInputDialog("1 - Verificar disponibilidade de tema\2 - Alugar tema, retirada hoje\n3 - Agendar data de retirada do tema\n"
					+ "4 - Devolu��o de tema\n5 - Cancelar reserva\n6 - Verificar alugueis e reservas ativas\n99 - Sair"));
			switch(op){
			case 1:
				String nomeTema = JOptionPane.showInputDialog("Informar qual tema ser� buscado");
				String dataInicio = JOptionPane.showInputDialog("Informar qual a data do come�o da loca��o\nPadr�o dd/mm/aaaa (dia/m�s/ano)");
				String dataFim = JOptionPane.showInputDialog("Informar qual a data de devolu��o do tema\nPadr�o dd/mm/aaaa (dia/m�s/ano)");
				boolean ok = aluguel.verificarDisponibilidadeTema(nomeTema, dataInicio, dataFim);
				if(ok) {
					JOptionPane.showMessageDialog(null, "Tema dispon�vel");
				} else {
					JOptionPane.showMessageDialog(null, "Tema indispon�vel");
				}
				break;
			case 2:
                aluguel.alugarTema();
                break;
			case 3:
				aluguel.agendarTema();
				break;
			case 4:
				aluguel.devolucaoTema();
				break;
			case 5:
				aluguel.cancelarReserva();
				break;
			case 6:
				aluguel.verAgenda();
				break;
			case 99:
				JOptionPane.showMessageDialog(null, "Retornando ao menu inicial...");
				return;
			default:
				JOptionPane.showMessageDialog(null,"Escolha uma op��o v�lida");
			}
		}
	}

	private static void menuDBTema() {
		ListaTema lista = new ListaTema();
		int op = 0;
		String nome;
		while(op != 99){
			op = Integer.parseInt(JOptionPane.showInputDialog("1 - Visualizar lista de temas\n2 - Visualizar dados de temas na base de dados"
					+ "\n3 - Editar dado de temas na base de dados\n4 - Excluir cliente da base de dados"
					+ "\n99 - Sair"));
			switch(op){
			case 1:
				JOptionPane.showMessageDialog(null, "Clientes na base de dados:\n(para visualizar os dados, use a op��o 2)\n");	
				lista.exibirTemaDB();
				break;
			case 2:
				nome = JOptionPane.showInputDialog("Inserir nome do tema que ser� buscado");
				lista.buscarTemaDB(nome);
				break;
			case 3:
				nome = JOptionPane.showInputDialog("Inserir nome do tema cujos dados ser�o alterados");
				lista.atualizarTemaDB(nome);
				break;
			case 4:
				lista.excluirTemaDB();
				break;
			case 99:
				JOptionPane.showMessageDialog(null, "Voltando ao menu inicial...");
				return;
			default:
				JOptionPane.showMessageDialog(null, "Escolha uma das op��es na lista");
				break;
			}
		}
	}

	private static void menuAdmin() throws ParseException, IOException {
		int op = 0;
		while(op != 99){
			op = Integer.parseInt(JOptionPane.showInputDialog("1 - Manter Cliente\n2 - Manter Tema\n3 - Agenda/Aluguel\n"
					+ "4 - Base de dados de clientes\n5 - Base de dados de temas\n6 - Manter usu�rio\n99 - Sair"));
			switch(op){
			case 1:
				menuAdminCliente();
				break;
			case 2:
				menuTema();
				break;
			case 3:
				menuAluguel();
				break;
			case 4:
				menuDBCliente();
				break;
			case 5:
				menuDBTema();
				break;
			case 6:
				menuUsuario();
				break;
			case 99:
				JOptionPane.showMessageDialog(null, "Voltando a tela inicial...");
				return;
			default:
				JOptionPane.showMessageDialog(null, "Escolha uma das op��es na lista");
				break;
			}
		}
	}


	private static void menuAdminCliente() {
		ListaLigada lista = new ListaLigada();
		int op = 0;
		String nome, sobrenome, documento, endereco, telefone, codigo;
		int numeroVezes, pos;
		while(op != 99){
			op = Integer.parseInt(JOptionPane.showInputDialog("1 - Adicionar novo cliente\n2 - Editar cliente\n3 - Excluir cliente\n"
					+ "4 - Consultar dados de cliente\n5 - Listar clientes\n6 - Verificar se cliente pode receber desconto\n"
					+ "7 - Incluir hist�rico\n8 - Ver hist�rico\n9 - Salvar primeiro item da lista na base de dados\n"
					+ "99 - Sair"));
			switch(op){
			case 1:
				nome = JOptionPane.showInputDialog("Inserir primeiro nome");
				sobrenome = JOptionPane.showInputDialog("Inserir sobrenome"); 
				documento = JOptionPane.showInputDialog("Inserir RG");
				endereco = JOptionPane.showInputDialog("Inserir endere�o (rua, CEP)");
				telefone = JOptionPane.showInputDialog("Inserir telefone de contato (incluindo DDD");
				numeroVezes = Integer.parseInt(JOptionPane.showInputDialog("Informe quantas vezes o cliente j� alugou aqui"));
				lista.addCliente(new Cliente(nome, sobrenome, documento, endereco, telefone, numeroVezes));
				break;
			case 2:
				pos = Integer.parseInt(JOptionPane.showInputDialog("Informar posi��o do cadastro que ser� editado"));
				lista.menuEditar(pos);
				break;
			case 3:
				pos = Integer.parseInt(JOptionPane.showInputDialog("Informar a pois��o do cadastro que ser� exclu�da"));
				lista.rmvCliente(pos);
				break;
			case 4:
				pos = Integer.parseInt(JOptionPane.showInputDialog("Informar posi��o do cadastro que ser� consultado"));
				lista.dadosCliente(pos);
				break;
			case 5: 
				JOptionPane.showMessageDialog(null, lista.listarClientes());
				break;
			case 6:
				pos = Integer.parseInt(JOptionPane.showInputDialog("Informar posi��o do cliente que ser� consultado"));
				lista.calculoDesconto(pos);
				break;
			case 7:
				pos = Integer.parseInt(JOptionPane.showInputDialog("Informar posi��o do cliente que ser� inclu�do hist�rico"));
				lista.incluirHistorico(pos);
				break;
			case 8:
				pos = Integer.parseInt(JOptionPane.showInputDialog("Informar posi��o do cliente que ser� consultado"));
				lista.verHistorico(pos);
				break;
			case 9:
				codigo = JOptionPane.showInputDialog("Inserir c�digo que ser� utilizado para nomear o cliente");
				lista.verificarDuplicidade(codigo);
				break;
			case 99:
				JOptionPane.showMessageDialog(null, "Voltando ao menu inicial...");
				return;
			default:
				JOptionPane.showMessageDialog(null, "Escolha uma das op��es na lista");
				break;
			}
		}
	}

	private static void menuDBCliente() {
		ListaLigada lista = new ListaLigada();
		int op = 0;
		String nome;
		while(op != 99){
			op = Integer.parseInt(JOptionPane.showInputDialog("1 - Visualizar lista de clientes\n2 - Visualizar dados de cliente espec�fico na base de dados"
					+ "\n3 - Editar dado de cliente na base de dados\n4 - Verificar se cliente na base de dados tem desconto\n5 - Excluir cliente da base de dados"
					+ "\n99 - Sair"));
			switch(op){
			case 1:
				JOptionPane.showMessageDialog(null, "Clientes na base de dados:\n(para visualizar os dados, use a op��o 2)\n");	
				lista.exibirDB();
				break;
			case 2:
				nome = JOptionPane.showInputDialog("Inserir c�digo do cliente que ser� buscado");
				lista.buscarClienteDB(nome);
				break;
			case 3:
				nome = JOptionPane.showInputDialog("Inserir c�digo do cliente cujos dados ser�o alterados");
				lista.atualizarDB(nome);
				break;
			case 4:
				nome = JOptionPane.showInputDialog("Inserir c�digo do cliente que ser� verificado o desconto");
				lista.exibirDescontoDB(nome);
				break;
			case 5:
				lista.excluirClienteDB();
				break;
			case 99:
				JOptionPane.showMessageDialog(null, "Voltando ao menu inicial...");
				return;
			default:
				JOptionPane.showMessageDialog(null, "Escolha uma das op��es na lista");
				break;
			}
		}
	}

	private static void menuUsuario() {
		FilaUsuario usuario = new FilaUsuario(5);
		int op = 0;
		String login, senha;
		while (op != 99) {
			op = Integer.parseInt(JOptionPane.showInputDialog("1 - Adicionar usu�rio na fila\n2 - Remover usu�rio pelo login\n"
					+ "3 - Pesquisar usu�rio pelo login na fila\n4 - Listar usu�rios na fila\n5 - Salvar primeiro usu�rio na base de dados\n"
					+ "6 - Listar usu�rios na base de dados\n7 - Buscar usu�rio na base de dados\n8 - Alterar login e/ou senha\n"
					+ "9 - Excluir usu�rio da base de dados\n10 - Alterar senha do Administrador\n99 - Sair"));
			switch(op){
			case 1:
				login = JOptionPane.showInputDialog("Inserir novo login\nEste login � case sensitive");
				usuario.VerificarDuplicidade(login);
				break;
			case 2:
				login = JOptionPane.showInputDialog("Inserir o login do usu�rio que ser� exlu�do");
				usuario.RmvPorLogin(login);
				break;
			case 3:
				login = JOptionPane.showInputDialog("Inserir o login que ser� buscado");
				usuario.BuscarCadastro(login);
				break;
			case 4:
				JOptionPane.showMessageDialog(null, "Os usu�rios a serem cadastrados na fila s�o:\n" + usuario.listaFila());
				break;
			case 5: 
				JOptionPane.showMessageDialog(null, "Inserido na base dados o usu�rio\n" + usuario.inserirUsuarioDB());
				break;
			case 6: 
				JOptionPane.showMessageDialog(null, "Usu�rios na base de dados:\n");	
				usuario.exibirUsuarioDB();
				break;
			case 7: 
				login = JOptionPane.showInputDialog("Inserir o login que ser� buscado");
				usuario.buscarUsuarioDB(login);
				break;
			case 8: 
				login = JOptionPane.showInputDialog("Inserir o login que ser� alterado");
				usuario.alterarUsuarioDB(login);
				break;
			case 9: 
				login = JOptionPane.showInputDialog("Inserir o login que ser� exclu�do");
				usuario.excluirUsuarioDB(login);
				break;
			case 10:
				senha = JOptionPane.showInputDialog("Inserir senha atual do administrador\nEsta senha � case sensitive");
				trocarSenhaAdmin(senha);
			case 99:
				JOptionPane.showMessageDialog(null, "Retornando ao menu inicial...");
				return;
			default:
				JOptionPane.showMessageDialog(null,"Escolha uma op��o v�lida");
			}
		}
	}
	
	private static void verficarArquivos() {
		File cliente = new File("ClienteDB.csv");
		File usuario = new File("UsuarioDB.csv");
		File tema = new File("TemaDB.csv");
		File aluguel = new File("AluguelDB.csv");
		try {
			cliente.createNewFile();
			usuario.createNewFile();
			tema.createNewFile();
			aluguel.createNewFile();
		} catch (IOException e) {}		
	}
}

