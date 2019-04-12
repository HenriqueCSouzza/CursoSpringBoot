package com.henrique.cursomc;

import java.text.SimpleDateFormat;
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
import com.henrique.cursomc.domain.ItemPedido;
import com.henrique.cursomc.domain.Pagamento;
import com.henrique.cursomc.domain.PagamentoComBoleto;
import com.henrique.cursomc.domain.PagamentoComCartao;
import com.henrique.cursomc.domain.Pedido;
import com.henrique.cursomc.domain.Produto;
import com.henrique.cursomc.domain.enums.EstadoPagamento;
import com.henrique.cursomc.domain.enums.TipoCliente;
import com.henrique.cursomc.repositories.CategoriaRepository;
import com.henrique.cursomc.repositories.CidadeRepository;
import com.henrique.cursomc.repositories.ClienteRepository;
import com.henrique.cursomc.repositories.EnderecoRepository;
import com.henrique.cursomc.repositories.EstadoRepository;
import com.henrique.cursomc.repositories.ItemPedidoRepository;
import com.henrique.cursomc.repositories.PagamentoRepository;
import com.henrique.cursomc.repositories.PedidoRepository;
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
	@Autowired
	private PedidoRepository pedidoRepository;
	@Autowired
	private PagamentoRepository pagamentoRepository;
	@Autowired
	private ItemPedidoRepository itemPedidoRepository;

	public static void main(String[] args) {
		SpringApplication.run(CursomcApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {

		Categoria cat1 = new Categoria(null, "informática");
		Categoria cat2 = new Categoria(null, "escritório");
		Categoria cat3 = new Categoria(null, "Cama mesa e banho");
		Categoria cat4 = new Categoria(null, "Eletrônicos");
		Categoria cat5 = new Categoria(null, "Jardinagem");
		Categoria cat6 = new Categoria(null, "Decoração");
		Categoria cat7 = new Categoria(null, "Perfumaria");

		Produto p1 = new Produto(null, "Computador", 2000.00);
		Produto p2 = new Produto(null, "Impressora", 800.00);
		Produto p3 = new Produto(null, "Mouse", 80.00);

		cat1.getProdutos().addAll(Arrays.asList(p1, p2, p3));
		cat2.getProdutos().addAll(Arrays.asList(p2));

		p1.getCategorias().addAll(Arrays.asList(cat1));
		p2.getCategorias().addAll(Arrays.asList(cat1, cat2));
		p3.getCategorias().addAll(Arrays.asList(cat1));

		categoriaRepository.saveAll(Arrays.asList(cat1, cat2,cat3,cat4,cat5,cat6,cat7));
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

		Cliente cli2 = new Cliente(null, "Henrique Carvalho", "henrique@gmail.com", "15489784502",
				TipoCliente.PESSOAFISICA);
		cli2.getTelefones().addAll(Arrays.asList("1125117360", "11952765279"));

		Endereco en = new Endereco(null, "Clarice Bueno de Miranda", "356", "casa", "São Miguel Paulista", "08042-100",
				cli2, c2);

		Endereco en2 = new Endereco(null, "Rua Flores", "300", "Apto 303", "Jardim", "38220-834", cli1, c1);

		cli1.getEnderecos().addAll(Arrays.asList(en, en2));

		clienteRepository.saveAll(Arrays.asList(cli1, cli2));
		enderecoRepository.saveAll(Arrays.asList(en, en2));

		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm");

		Pedido ped1 = new Pedido(null, sdf.parse("30/09/2017 10:32"), cli1, en);
		Pedido ped2 = new Pedido(null, sdf.parse("10/10/2017 19:25"), cli2, en2);

		Pagamento pgto = new PagamentoComCartao(null, EstadoPagamento.QUITADO, ped1, 5);
		ped1.setPagamento(pgto);
		Pagamento pgto2 = new PagamentoComBoleto(null, EstadoPagamento.PENDENTE, ped2, sdf.parse("20/10/2017 00:00"),
				null);
		ped2.setPagamento(pgto2);

		cli1.getPedidos().addAll(Arrays.asList(ped1));
		cli2.getPedidos().addAll(Arrays.asList(ped2));

		pedidoRepository.saveAll(Arrays.asList(ped1, ped2));
		pagamentoRepository.saveAll(Arrays.asList(pgto, pgto2));

		ItemPedido ip1 = new ItemPedido(ped1, p1, 0.00, 1, 2000.00);
		ItemPedido ip2 = new ItemPedido(ped1, p3, 0.00, 2, 80.00);
		ItemPedido ip3 = new ItemPedido(ped2, p2, 0.00, 1, 800.00);

		ped1.getItens().addAll(Arrays.asList(ip1, ip2));
		ped2.getItens().addAll(Arrays.asList(ip3));

		p1.getItens().addAll(Arrays.asList(ip1));
		p2.getItens().addAll(Arrays.asList(ip3));
		p3.getItens().addAll(Arrays.asList(ip2));

		itemPedidoRepository.saveAll(Arrays.asList(ip1, ip2, ip3));

	}

}
