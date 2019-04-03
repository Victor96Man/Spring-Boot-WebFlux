package com.victor96man.springboot.webflux.app.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.victor96man.springboot.webflux.app.models.dao.IProductoDao;
import com.victor96man.springboot.webflux.app.models.documents.Producto;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/api/productos")
public class ProductoRestController {

	@Autowired
	private IProductoDao dao;
	
	@GetMapping
	public Flux<Producto> index(){
		Flux<Producto> productos = dao.findAll().map(producto -> {
			producto.setNombre(producto.getNombre().toUpperCase());
			return producto;
		}).doOnNext(prod -> System.out.println(prod.getNombre()));
		
		return productos;
	}

	@GetMapping("/{id}")
	public Mono<Producto> show(@PathVariable String id){
		Mono<Producto> producto = dao.findById(id);
		return producto;
	}
	
}
