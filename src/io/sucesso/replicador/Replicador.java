package io.sucesso.replicador;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Set;

import com.google.gson.GsonBuilder;

public class Replicador {

	private static Configuracao config;
	private static List<String> lista = new ArrayList<>();
	private static List<String> filtros = new ArrayList<>();

	public static void main(String[] args) throws Exception {
		config = new GsonBuilder().create().fromJson(new FileReader("config.json"), Configuracao.class);

		for (Entidade entidade : config.getEntidades()) {
			if (entidade.getIgnore()) {
				continue;
			}

			filtros = Arrays.asList(entidade.getFiles().split(";"));
			lerArquivos(config.getPathOrigem());
			for (String arquivoOrigem : lista) {
				String conteudo = lerArquivo(arquivoOrigem);

				Set<String> keys = entidade.getReplaces().keySet();
				int n = keys.size();
				List<String> aList = new ArrayList<>(n);
				for (String x : keys)
					aList.add(x);

				String arquivoDestino = arquivoOrigem;

				for (String key : aList) {
					String replace = entidade.getReplaces().get(key);
					// System.out.println(key+":"+replace);
					var posicao = 0;
					while (conteudo.indexOf(key, posicao) >= 0) {

						var inicio = conteudo.substring(0, conteudo.indexOf(key, posicao));
						var fim = conteudo.substring(conteudo.indexOf(key, posicao) + key.length());

						conteudo = inicio + replace + fim;

						posicao = inicio.length() + replace.length();
					}

					posicao = 0;
					while (arquivoDestino.indexOf(key, posicao) >= 0) {
						var inicio = arquivoDestino.substring(0, arquivoDestino.indexOf(key, posicao));
						var fim = arquivoDestino.substring(arquivoDestino.indexOf(key, posicao) + key.length());

						arquivoDestino = inicio + replace + fim;

						posicao = inicio.length() + replace.length();
					}
				}

				var pathArquivoDestino = arquivoDestino.substring(0, arquivoDestino.lastIndexOf("/") + 1);
				var pathDestino = new File(pathArquivoDestino);
				pathDestino.mkdirs();

				try (var destino = new FileWriter(arquivoDestino);) {
					destino.write(conteudo);
					destino.close();
					System.out.println("criando"+arquivoDestino);
				}

			}
		}
	}

	private static void lerArquivos(String pathname) {
		String arquivos[] = new File(pathname).list();
		for (String arquivo : arquivos) {
			String nome;
			if (pathname.endsWith("/"))
				nome = pathname + arquivo;
			else
				nome = pathname + File.separator + arquivo;
			if (new File(nome).isDirectory()) {
				lerArquivos(nome);
			} else if (new File(nome).isFile()) {

				for (String filtro : filtros) {
					String f = filtro.replaceAll("\\*", "");
					if (filtro.startsWith("*") && filtro.endsWith("*") && arquivo.indexOf(f) >= 0) {
						lista.add(nome);
					} else if (filtro.startsWith("*") && arquivo.endsWith(f)) {
						lista.add(nome);
					} else if (filtro.endsWith("*") && arquivo.startsWith(f)) {
						lista.add(nome);
					}
				}
			}
		}
	}

	private static String lerArquivo(String arquivo) throws Exception {
		var sb = new StringBuilder();
		var in = new BufferedReader(new FileReader(arquivo));
		String linha;
		while ((linha = in.readLine()) != null) {
			sb.append(linha);
			sb.append("\n");
		}
		in.close();
		return sb.toString();
	}
}