package com.henrique.cursomc.services;

import java.util.Calendar;
import java.util.Date;

import org.springframework.stereotype.Service;

import com.henrique.cursomc.domain.PagamentoComBoleto;

@Service
public class BoletoService {

	public void preencherPagamentoComBoleto(PagamentoComBoleto pagto, Date instante) {
		
		//instancia a data de Calendar
		Calendar cal = Calendar.getInstance();
		//adiciona a data da compra vinda de instante
		cal.setTime(instante);
		//adiciona mais 7 dias da data instante
		cal.add(Calendar.DAY_OF_MONTH, 7);
		//insere a data de vencimento no boleto
		pagto.setDataVencimemto(cal.getTime());
	}

}
