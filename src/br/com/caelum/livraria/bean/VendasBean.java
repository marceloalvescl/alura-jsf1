package br.com.caelum.livraria.bean;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import org.primefaces.model.chart.Axis;
import org.primefaces.model.chart.AxisType;
import org.primefaces.model.chart.BarChartModel;
import org.primefaces.model.chart.ChartSeries;

import br.com.caelum.livraria.dao.LivroDao;
import br.com.caelum.livraria.dao.VendaDao;
import br.com.caelum.livraria.modelo.Livro;
import br.com.caelum.livraria.modelo.Venda;

@Named
@ViewScoped
public class VendasBean implements Serializable {

	private static final long serialVersionUID = 1L;
	@Inject
	private LivroDao livroDao;
	@Inject
	private VendaDao vendaDao;
	
	public List<Venda> getVendas() {

		

		return null;
	}

	public BarChartModel getVendasModel() {
		BarChartModel model = new BarChartModel();

		ChartSeries vendaSerie = new ChartSeries();
		vendaSerie.setLabel("Vendas 2016");

		List<Venda> vendas = vendaDao.getVendas();
		for (Venda venda : vendas) {
			vendaSerie.set(venda.getLivro().getTitulo(), venda.getQuantidade());
		}

		model.addSeries(vendaSerie);

		model.setAnimate(true);
		model.setTitle("Vendas");
		model.setLegendPosition("ne");
		Axis xAxis = model.getAxis(AxisType.X);
		xAxis.setLabel("Título");

		Axis yAxis = model.getAxis(AxisType.Y);
		yAxis.setLabel("Quantidade");

		return model;
	}

}
