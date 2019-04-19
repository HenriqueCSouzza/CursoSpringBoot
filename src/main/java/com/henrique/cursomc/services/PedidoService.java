package com.henrique.cursomc.services;

import java.util.Date;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.henrique.cursomc.domain.ItemPedido;
import com.henrique.cursomc.domain.PagamentoComBoleto;
import com.henrique.cursomc.domain.Pedido;
import com.henrique.cursomc.domain.enums.EstadoPagamento;
import com.henrique.cursomc.repositories.ItemPedidoRepository;
import com.henrique.cursomc.repositories.PagamentoRepository;
import com.henrique.cursomc.repositories.PedidoRepository;
import com.henrique.cursomc.services.exception.ObjectNotFoundException;

@Transactional
@Service
public class PedidoService {

	@Autowired
	private PedidoRepository repo;
	@Autowired
	private BoletoService boletoService;
	@Autowired
	private PagamentoRepository pagamentoRepository;
	@Autowired
	private ProdutoService produtoService;
	@Autowired
	private ItemPedidoRepository itemPedidoRepository;

	public Pedido find(Integer id) {
		Optional<Pedido> obj = repo.findById(id);
		return obj.orElseThrow(
				() -> new ObjectNotFoundException("Objeto não encontrado! " + "id:" + id + ", Tipo: " + Pedido.class));

	}

	public Pedido insert(Pedido obj) {
		// TODO Auto-generated method stub
		obj.setId(null);
		obj.setInstante(new Date());
		obj.getPagamento().setEstadoPagamento(EstadoPagamento.PENDENTE);
		obj.getPagamento().setPedido(obj);
		// se a instancia for de pagamentoComBoleto
		if (obj.getPagamento() instanceof PagamentoComBoleto) {
			PagamentoComBoleto pagto = (PagamentoComBoleto) obj.getPagamento();
			boletoService.preencherPagamentoComBoleto(pagto, obj.getInstante());
		}
		obj = repo.save(obj);
		// salva o pagamento e tipo de pagamento
		pagamentoRepository.save(obj.getPagamento());
		// associando os itens de pedido
		for (ItemPedido ip : obj.getItens()) {
			ip.setDesconto(0.0);
			ip.setPreco(produtoService.find(ip.getProduto().getId()).getPreco());
			ip.setPedido(obj);
		}
		itemPedidoRepository.saveAll(obj.getItens());
		return obj;

	}

}
