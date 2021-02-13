package br.com.caelum.library.bean;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import org.primefaces.model.chart.Axis;
import org.primefaces.model.chart.AxisType;
import org.primefaces.model.chart.BarChartModel;
import org.primefaces.model.chart.ChartSeries;

import br.com.caelum.library.dao.DAO;
import br.com.caelum.library.model.Book;
import br.com.caelum.library.model.Sale;

@ManagedBean
@ViewScoped
public class SalesBean {
	
	
	public BarChartModel getSalesModel() {

	    BarChartModel model = new BarChartModel();

	    ChartSeries saleSerie = new ChartSeries();
	    saleSerie.setLabel("Sales 2016");

	    List<Sale> sales = getSales(1234);

	    for (Sale sale: sales) {
	        saleSerie.set(sale.getBook().getTitle(), sale.getQuantity());
	    }

	    model.addSeries(saleSerie);

	    ChartSeries saleSerie2015 = new ChartSeries();
	    saleSerie2015.setLabel("Vendas 2015");

	    sales = getSales(4321);

	    for (Sale sale : sales) {
	        saleSerie2015.set(sale.getBook().getTitle(), sale.getQuantity());
	    }

	    model.addSeries(saleSerie2015);
	    
	    model.setTitle("Sales");
	    model.setLegendPosition("ne");
	    
	    Axis xAxis = model.getAxis(AxisType.X);
	    xAxis.setLabel("Title");
	    
	    Axis yAxis = model.getAxis(AxisType.Y);
	    yAxis.setLabel("Quantity");
	    

	    return model;
	}
	
	
	
	
	public List<Sale> getSales(long seed){
		
		List<Book> books = new DAO<Book>(Book.class).listAll();
		List<Sale> sales = new ArrayList<Sale>();
		
		Random random = new Random(seed);
		
		for(Book book : books) {
			Integer quantity = random.nextInt(500);
			
			sales.add(new Sale(book, quantity));
		
		}
		
		return sales;
		
	}
	
}
