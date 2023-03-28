package application;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Locale;
import java.util.Scanner;
import java.util.stream.Collectors;

import entities.Sale;

public class Program
{
	// C:\EclipseProjetos\desafio_analise_vendas_01\in.csv
	
	public static void main(String[] args)
	{
		Locale.setDefault(Locale.US);
		Scanner scn = new Scanner(System.in);
		
		System.out.print("Entre o caminho do arquivo: ");
		String caminho = scn.nextLine();
		
		try (BufferedReader bfr = new BufferedReader(new FileReader(caminho)))
		{
			List<Sale> sales = new ArrayList<>();
			
			String linha = bfr.readLine();
			
			while (linha != null)
			{
				String[] campos = linha.split(",");
				sales.add(new Sale(
					Integer.parseInt(campos[0]),     // mês
					Integer.parseInt(campos[1]),     // ano
					campos[2],                       // vendedor
					Integer.parseInt(campos[3]),     // itens
					Double.parseDouble(campos[4]))); // total
				linha = bfr.readLine();
			}
			
			System.out.println();
			System.out.println("Cinco primeiras vendas de 2016 de maior preço médio:");
			
			Comparator<Sale> comp = (s1, s2) -> s1.averagePrice().compareTo(s2.averagePrice());
			
			List<Sale> novaLista = sales.stream()
				.filter(nL -> nL.getYear() == 2016)
				.sorted(comp.reversed())
				.limit(5)
				.collect(Collectors.toList());
			
			novaLista.forEach(System.out::println);				
				
			Double soma = sales.stream()
				.filter(s -> s.getMonth() == 1 || s.getMonth() == 7)
				.filter(s -> s.getSeller().toUpperCase().equals("LOGAN"))
				.map(s -> s.getTotal())
				.reduce(0.0, (x,y) -> x + y);
			
			System.out.println();
			System.out.printf("Valor total vendido pelo vendedor Logan nos meses 1 e 7 = %.2f", soma);			
		}
		catch (IOException e)
		{
			System.out.println("Erro: " + caminho + " (O sistema não pode encontrar o arquivo especificado)");
		}
		finally
		{
			scn.close();
		}
	}
}
