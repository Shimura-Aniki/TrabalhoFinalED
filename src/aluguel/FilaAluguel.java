package aluguel;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import javax.swing.JOptionPane;

import cliente.ListaLigada;
import tema.ListaTema;

public class FilaAluguel {
	private static String filaAluguel[];
	private String cliente;
	private String tema;
	SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
	ListaTema acessoTema = new ListaTema();
	ListaLigada acessoCliente = new ListaLigada();
	
	public FilaAluguel(int total) {
		filaAluguel = new String[total];
	}

	public void alugarTema() throws ParseException, IOException {
		Calendar cal = Calendar.getInstance();
		cliente = JOptionPane.showInputDialog("Informe o cliente");
		boolean verfUsu = acessoCliente.verificarNome(cliente);
		if(verfUsu) {
			tema = JOptionPane.showInputDialog("Informe o tema que será alugado");
			boolean verfTema = acessoTema.verificarTemaAluguel(tema);
			if(verfTema) {
				int qtiddDias = Integer.parseInt(JOptionPane.showInputDialog("Por quantos dias o tema será alugado:"));
				String dataAlug = formatter.format(cal.getTime());
				String dataDev = calculoData(qtiddDias, dataAlug);		
				boolean verfData = verificarDisponibilidadeTema(tema, dataAlug, dataDev);
				if(verfData) {
					double valorCobrado = calculoValor(qtiddDias, cliente, tema);
					String confirmar = JOptionPane.showInputDialog(("Data de devolução: " + dataDev + "\nO valor total será de: R$" 
					+ valorCobrado + "\nDeseja prosseguir com o aluguel?\nDigite 99 se quiser cancelar"));
					if(confirmar.equals("99")){
						return;
					}
					else {
						String endereco = JOptionPane.showInputDialog("Informar o local da festa");
						salvarAluguel(cliente, tema, dataDev, dataAlug, valorCobrado, endereco);
					}
				} else {
					JOptionPane.showMessageDialog(null, "Tema indisponível nesta data");
					return;
				}
			} else {
				JOptionPane.showMessageDialog(null, "Tema indisponível");
				return;
			}
		} else {
			JOptionPane.showMessageDialog(null, "Cliente não cadastrado!");
			return;
		}
	}

	private String calculoData(int dias, String dataAlug) {
		Calendar cal = Calendar.getInstance();
		try {
			cal.setTime(formatter.parse(dataAlug));
		} catch (ParseException e) {
			e.printStackTrace();
		}
        cal.add(Calendar.DAY_OF_MONTH, dias);  
		String newDate = formatter.format(cal.getTime());  
		return newDate;
	}
	
	private double calculoValor(int qtiddDias, String cliente, String tema) {
		double desconto = acessoCliente.receberDescontoDB(cliente);
		double valorDiaria = acessoTema.receberDiaria(tema);
		double totalSemDesc = valorDiaria * qtiddDias;
		double valorFinal = totalSemDesc - ((totalSemDesc*desconto)/100);
		return valorFinal;
	}

	private void salvarAluguel(String cliente, String tema, String dataDev, String dataAlug, double valorCobrado, String endereco) {
		String valor = String.valueOf(valorCobrado);
		try {
			File f = new File("AluguelDB.csv");
			PrintWriter pw = new PrintWriter(new FileOutputStream(f, true));
			String aluguel = cliente + "§" + tema + "§" + dataAlug + "§" + dataDev + "§" + valor + "§" + endereco;
			pw.append(aluguel);
			pw.append("\n");
			pw.close();
			acessoCliente.addNumeroVezes(cliente);
			JOptionPane.showMessageDialog(null, "Aluguel confirmado!");
			} catch(Exception e){}
	}
	
	private String InserirData() {
		String dataAux[] = new String[3];
		String dataFim = JOptionPane.showInputDialog("Informar quando será realizada a data de entrega do tema\n"
							+ "Padrão dd/MM/aaaa (dia/mês/ano)");
		dataAux = dataFim.split("/");
		boolean diaOk = verificarDia(dataAux[0], dataAux[1], dataAux[2]);
		boolean mesOk = verificarMes(dataAux[1]);
		boolean anoOk = verificarAno(dataAux[0], dataAux[1], dataAux[2]);
		if(diaOk && mesOk && anoOk) {
			return dataFim;
		} else {
			JOptionPane.showMessageDialog(null, "Data inválida");
			return null;
		}
	}

	private boolean verificarMes(String mes) {
		int mesAux = Integer.parseInt(mes);
		boolean ok = false;
		if(mesAux>0 && mesAux<13) {
			ok = true;
			return ok;
		} else {
		return ok;
		}
	}

	private boolean verificarDia(String dia, String mes, String ano) {
		int mesAux = Integer.parseInt(mes);
		int diaAux = Integer.parseInt(dia);
		int anoAux = Integer.parseInt(ano);
		boolean ok = false;
		switch(mesAux){
		case 1:
		case 3:
		case 5:
		case 7:
		case 8:
		case 10:
		case 12:
			if(diaAux>0 && diaAux<32) {
				ok = true;
				return ok;
			}
		case 4:
		case 6:
		case 9:
		case 11:
			if(diaAux>0 && diaAux<31) {
				ok = true;
				return ok;
			}
		case 2:
			if((anoAux % 400 == 0) || ((anoAux % 4 == 0) && (anoAux % 100 != 0))){
				if(diaAux>0 && diaAux<30) {
					ok = true;
					return ok;
				}
			} else {
				if(diaAux>0 && diaAux<29) {
					ok = true;
					return ok;
				}
			}
		}
		return ok;
	}

	private boolean verificarAno(String dia, String mes, String ano) {
		boolean ok = false;
		String dataAtual[] = new String[3];
		int mesAux = Integer.parseInt(mes);
		int diaAux = Integer.parseInt(dia);
		int anoAux = Integer.parseInt(ano);
		Date date = new Date();
		String dataAtualAux = formatter.format(date);
		dataAtual = dataAtualAux.split("/");
		int diaAtual = Integer.parseInt(dataAtual[0]);
		int mesAtual = Integer.parseInt(dataAtual[1]);
		int anoAtual = Integer.parseInt(dataAtual[2]);
		if(anoAux>=anoAtual && mesAux>=mesAtual && diaAux>=diaAtual) {
			ok = true;
		}
		return ok;
	}

	public void agendarTema() throws ParseException, IOException {
		cliente = JOptionPane.showInputDialog("Informe o usuário");
		boolean verfUsu = acessoCliente.verificarNome(cliente);
		if(verfUsu) {
			tema = JOptionPane.showInputDialog("Informe o tema que será alugado");
			boolean verfTema = acessoTema.verificarTemaAluguel(tema);
			if(verfTema) {
				String dataAgenda = InserirData();
				if(dataAgenda != null) {
					int qtiddDias = Integer.parseInt(JOptionPane.showInputDialog("Por quantos dias o tema será alugado:"));
					String dataDev = calculoData(qtiddDias, dataAgenda);		
					boolean verfData = verificarDisponibilidadeTema(tema, dataAgenda, dataDev);
					if(verfData) {
						double valorCobrado = calculoValor(qtiddDias, cliente, tema);
						String confirmar = JOptionPane.showInputDialog(("Data de devolução: " + dataDev + "\nO valor total será de: R$" 
						+ valorCobrado + "\nDeseja prosseguir com o aluguel?\nDigite 99 se quiser cancelar"));
						if(confirmar.equals("99")){
							return;
						}
						else {
							String endereco = JOptionPane.showInputDialog("Informar o local da festa");
							salvarAluguel(cliente, tema, dataDev, dataAgenda, valorCobrado, endereco);
						}
					} else {
						JOptionPane.showMessageDialog(null, "Tema indisponível nesta data");
						return;
					}
				} else {
					return;
				}
			} else {
				JOptionPane.showMessageDialog(null, "Tema indisponível");
				return;
			}
		} else {
			JOptionPane.showMessageDialog(null, "Cliente não cadastrado!");
			return;
		}
	}
	
	public void devolucaoTema() {
		Calendar cal = Calendar.getInstance();
		String tema = JOptionPane.showInputDialog("Informe o tema que foi devolvido");
		String dataDevolucao = formatter.format(cal.getTime());
		StringBuffer sb = new StringBuffer();
		try {
			BufferedReader br = new BufferedReader(new FileReader("AluguelDB.csv"));
			String s = "";
			while((s = br.readLine()) != null){
				String dados[] = new String[6];
				dados = s.split("§");				
				if(dados[1].equalsIgnoreCase(tema) && dados[3].equals(dataDevolucao)) {
					JOptionPane.showMessageDialog(null, "Retirando do registro ativo");				
				} else { 
					sb.append(s);
					sb.append("\n");
				}
				File f = new File("AluguelDB.csv");
				PrintWriter pw = new PrintWriter(new FileOutputStream(f, false));
				pw.print(sb.toString());
				pw.close();
			}
		} catch(Exception e){}
	}
	
	public boolean verificarDisponibilidadeTema(String tema2, String dataAlug, String dataDev) throws ParseException, IOException {
		SimpleDateFormat formato = new SimpleDateFormat("dd/MM/yyyy");
		Date dataInicio = formato.parse(dataAlug);
		Date dataFim = formato.parse(dataDev);
		boolean disponivel = false;
		boolean ok = checarAluguelVazio();
		if(!ok){
			try {
				BufferedReader br = new BufferedReader(new FileReader("AluguelDB.csv"));
				String s = "";
				while((s = br.readLine()) != null){
					String dados[] = new String[5];
					dados = s.split("§");
					Date dataRetirada = formato.parse(dados[2]);
					Date dataDevolucao = formato.parse(dados[3]);
					if(dados[1].equalsIgnoreCase(tema2)) {
						if(dataInicio.compareTo(dataDevolucao)>0){
							disponivel = true;
						} else if(dataFim.compareTo(dataRetirada)<0){
							disponivel = true;
						} else {
							disponivel = false;
						}
					}
				}
				br.close();
				return disponivel;
			} catch(Exception e){}
		} else {
			disponivel = true;	
		}
		return disponivel;
	}

	private boolean checarAluguelVazio() throws IOException {
		try {
			BufferedReader br = new BufferedReader(new FileReader("AluguelDB.csv"));
			String s = "";
			if((s = br.readLine()) == null){
				return true;
			}
		} catch (FileNotFoundException e) {}
		return false;
	}

	public void cancelarReserva() {
		String tema = JOptionPane.showInputDialog("Informe o tema que seria reservado");
		String dataDevolucao = JOptionPane.showInputDialog("Informar data estipulada de devolução\nPadrão dd/MM/aaaa (dia/mês/ano)");
		StringBuffer sb = new StringBuffer();
		try {
			BufferedReader br = new BufferedReader(new FileReader("AluguelDB.csv"));
			String s = "";
			while((s = br.readLine()) != null){
				String dados[] = new String[6];
				dados = s.split("§");				
				if(dados[1].equalsIgnoreCase(tema) && dados[3].equals(dataDevolucao)) {
					JOptionPane.showMessageDialog(null, "Retirando do registro ativo");				
				} else { 
					sb.append(s);
					sb.append("\n");
				}
				File f = new File("AluguelDB.csv");
				PrintWriter pw = new PrintWriter(new FileOutputStream(f, false));
				pw.print(sb.toString());
				pw.close();
			}
		} catch(Exception e){}
	}

	public void verAgenda() {
		try {
			BufferedReader br = new BufferedReader(new FileReader("AluguelDB.csv"));
			String s = "";
			String row = "";			
			while((s = br.readLine()) != null){
				String data[] = new String[6];
				data = s.split("§");
				for(int i = 0; i<6; i++){
					if(i == 2){
						row = row + nomearDB(i) + data[i] + "\n";
					} else if(i == 5){
						row = row + nomearDB(i) + data[i] + "\n" + "\n";
					} else {
						row = row + nomearDB(i) + data[i] + "     \t";
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
			return "Login do usuário = ";
		case 1:
			return "Código do tema = ";
		case 2:
			return "Data de retirada = ";
		case 3:
			return "Data de devolução = ";
		case 4:
			return "Valor total pago = ";
		case 5:
			return "Local da festa = ";

		default:
			return null;
		}
	}
}
