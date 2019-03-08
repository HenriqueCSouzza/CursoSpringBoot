package com.henrique.cursomc;

import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.henrique.cursomc.domain.Categoria;
import com.henrique.cursomc.domain.Cidade;
import com.henrique.cursomc.domain.Cliente;
import com.henrique.cursomc.domain.Endereco;
import com.henrique.cursomc.domain.Estado;
import com.henrique.cursomc.domain.Produto;
import com.henrique.cursomc.domain.enums.TipoCliente;
import com.henrique.cursomc.repositories.CategoriaRepository;
import com.henrique.cursomc.repositories.CidadeRepository;
import com.henrique.cursomc.repositories.ClienteRepository;
import com.henrique.cursomc.repositories.EnderecoRepository;
import com.henrique.cursomc.repositories.EstadoRepository;
import com.henrique.cursomc.repositories.ProdutoRepository;

@SpringBootApplication
public class CursomcApplication implements CommandLineRunner {

	@Autowired
	private CategoriaRepository categoriaRepository;
	@Autowired
	private ProdutoRepository produtoRepository;
	@Autowired
	private EstadoRepository estadoRepository;
	@Autowired
	private CidadeRepository cidadeRepository;
	@Autowired
	private ClienteRepository clienteRepository;
	@Autowired
	private EnderecoRepository enderecoRepository;

	
	public static void main(String[] args) {
		SpringApplication.run(CursomcApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {

		Categoria cat1 = new Categoria(null, "informática");
		Categoria cat2 = new Categoria(null, "escritório");

		Produto p1 = new Produto(null, "Computador", 2000.00);
		Produto p2 = new Produto(null, "Impressora", 800.00);
		Produto p3 = new Produto(null, "Mouse", 80.00);

		cat1.getProdutos().addAll(Arrays.asList(p1, p2, p3));
		cat2.getProdutos().addAll(Arrays.asList(p2));

		p1.getCategorias().addAll(Arrays.asList(cat1));
		p2.getCategorias().addAll(Arrays.asList(cat1, cat2));
		p3.getCategorias().addAll(Arrays.asList(cat1));

		categoriaRepository.saveAll(Arrays.asList(cat1, cat2));
		produtoRepository.saveAll(Arrays.asList(p1, p2, p3));

		Estado est1 = new Estado(null, "Minas Gerais");
		Estado est2 = new Estado(null, "São Paulo");

		Cidade c1 = new Cidade(null, "Uberlândia", est1);
		Cidade c2 = new Cidade(null, "São Paulo", est2);
		Cidade c3 = new Cidade(null, "Campinas", est2);

		est1.getCidades().addAll(Arrays.asList(c1));
		est2.getCidades().addAll(Arrays.asList(c2, c3));

		estadoRepository.saveAll(Arrays.asList(est1, est2));
		cidadeRepository.saveAll(Arrays.asList(c1, c2, c3));

		Cliente cli1 = new Cliente(null, "Maria Silva", "maria@gmail.com", "36589852321", TipoCliente.PESSOAFISICA);
		cli1.getTelefones().addAll(Arrays.asList("1125117360", "11952777777"));

		Cliente cli2 = new Cliente(null, "Henrique Carvalho", "henrique@gmail.com", "15489784502", TipoCliente.PESSOAFISICA);
		cli1.getTelefones().addAll(Arrays.asList("1125117360", "11952765279"));
		
		Endereco en = new Endereco(null, "Clarice Bueno de Miranda", "356", "casa", "São Miguel Paulista", "08042-100",
				cli2, c2);
		
		Endereco en2 = new Endereco(null, "Rua Flores", "300", "Apto 303", "Jardim", "38220-834", cli1, c1);

		cli1.getEnderecos().addAll(Arrays.asList(en, en2));

		clienteRepository.saveAll(Arrays.asList(cli1,cli2));
		enderecoRepository.saveAll(Arrays.asList(en, en2));


	}

}
