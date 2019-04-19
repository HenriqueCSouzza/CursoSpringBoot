package com.henrique.cursomc.domain;

import java.util.Date;

import javax.persistence.Entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonTypeName;
import com.henrique.cursomc.domain.enums.EstadoPagamento;
@Entity
@JsonTypeName("pagamentoComBoleto")
public class PagamentoComBoleto extends Pagamento{
	private static final long serialVersionUID = 1L;
	
	@JsonFormat(pattern="dd/MM/yyyy")
	private Date dataPagamento;
	
	@JsonFormat(pattern="dd/MM/yyyy")
	private Date dataVencimemto;
	
	public PagamentoComBoleto() {
	}
	public PagamentoComBoleto(Integer id, EstadoPagamento estadoPagamento, Pedido pedido,Date dataPagamento,Date dataVencimemto) {
		super(id, estadoPagamento, pedido);
		this.dataPagamento=dataPagamento;
		this.dataVencimemto=dataVencimemto;
	}
	public Date getDataPagamento() {
		return dataPagamento;
	}
	public void setDataPagamento(Date dataPagamento) {
		this.dataPagamento = dataPagamento;
	}
	public Date getDataVencimemto() {
		return dataVencimemto;
	}
	public void setDataVencimemto(Date dataVencimemto) {
		this.dataVencimemto = dataVencimemto;
	}
	

	
	
}
