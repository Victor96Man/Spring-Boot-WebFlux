package com.victor96man.springboot.webflux.app;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.mongodb.core.ReactiveMongoTemplate;

import com.victor96man.springboot.webflux.app.models.dao.IProductoDao;
import com.victor96man.springboot.webflux.app.models.documents.Producto;

import reactor.core.publisher.Flux;

@SpringBootApplication
public class SpringBootWebFluxApplication implements CommandLineRunner{
	
	@Autowired
	private IProductoDao dao;
	
	@Autowired
	private ReactiveMongoTemplate mongoTemplate;

	public static void main(String[] args) {
		SpringApplication.run(SpringBootWebFluxApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		
		mongoTemplate.dropCollection("productos").subscribe();
		
		Flux.just( new Producto("TV", 459.99),
				new Producto("DVD", 158.99),
				new Producto("Sony", 308.99),
				new Producto("Altavoces", 48.99),
				new Producto("Pantalla LCD", 78.99),
				new Producto("Portatil Asus", 599.99),
				new Producto("Xiomi note 7", 658.99)
				)
		.flatMap(producto -> {
			producto.setCreateAt(new Date());
			return dao.save(producto);
			})
		.subscribe();
		
	}

}
