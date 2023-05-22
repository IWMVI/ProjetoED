package control;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;

import javax.swing.JOptionPane;

import model.Aluno;

public class InserirAluno implements IInserirAluno{
	@Override
	public void manterAluno(String pathData, String arquivo, String aluno, String ra, String curso, String periodo, int ciclo) throws Exception {
		Aluno novo = new Aluno(aluno, ra, curso, periodo, ciclo);
		
		try {
			inserir(pathData, arquivo, novo);
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}
	
	private void inserir(String pathData, String nomeArquivo, Aluno aluno) throws Exception {
		File dir = new File(pathData);
		String conteudo = aluno.getAluno()+";"+aluno.getRa()+";"+aluno.getCurso()+";"+aluno.getPeriodo()+";"+aluno.getCiclo()+"\n";
		if(!dir.exists()) {
			dir.mkdirs();
		}
		File arquivo = new File(pathData, nomeArquivo);
		boolean exists = false;
		boolean control = false;
		if(arquivo.exists()) {
			exists = true;
		}
		
		FileWriter escrita = new FileWriter(arquivo, exists);
		PrintWriter print = new PrintWriter(escrita);
		if(!alunoExiste(arquivo, aluno)) {
			print.write(conteudo);
			print.flush();
			JOptionPane.showMessageDialog(null, "Aluno gravado com sucesso.");
		}
		print.close();
		escrita.close();
	}
	
//====================[ Verifica se um aluno já existe na lista ]===================//
	private boolean alunoExiste(File arquivo, Aluno aluno) throws IOException {
		FileInputStream fluxo = new FileInputStream(arquivo);
		InputStreamReader leitor = new InputStreamReader(fluxo);
		BufferedReader buffer = new BufferedReader(leitor);
		String linha = buffer.readLine();
		while(linha!=null) {
			String[] linhaSeparada = linha.split(";");
			if(aluno.getRa().contains(linhaSeparada[1])) {
				JOptionPane.showMessageDialog(null, "O aluno especificado já existe no sistema.");
				return true;
			}
			linha = buffer.readLine();
		}
		buffer.close();
		leitor.close();
		fluxo.close();
		return false;
	}
}
