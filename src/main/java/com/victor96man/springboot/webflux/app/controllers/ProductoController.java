package com.victor96man.springboot.webflux.app.controllers;

import java.time.Duration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.thymeleaf.spring5.context.webflux.ReactiveDataDriverContextVariable;

import com.victor96man.springboot.webflux.app.models.dao.IProductoDao;
import com.victor96man.springboot.webflux.app.models.documents.Producto;

import reactor.core.publisher.Flux;

@Controller
public class ProductoController {

	@Autowired
	private IProductoDao dao;
	
	
	@GetMapping({"/listar", "/"})
	private String listar(Model model) {
		
		Flux<Producto> productos = dao.findAll().map(producto -> {
			producto.setNombre(producto.getNombre().toUpperCase());
			return producto;
		});
		
		productos.subscribe();	
		
		model.addAttribute("productos",productos);
		model.addAttribute("titulo", "Listado de Productos");
		return "listar";
	}
	
	@GetMapping("/listar-datadriver")
	private String listarDataDriver(Model model) {
		
		Flux<Producto> productos = dao.findAll().map(producto -> {
			producto.setNombre(producto.getNombre().toUpperCase());
			return producto;
		}).delayElements(Duration.ofSeconds(1));
		
		productos.subscribe();	
		
		model.addAttribute("productos", new ReactiveDataDriverContextVariable(productos,1));
		model.addAttribute("titulo", "Listado de Productos");
		return "listar";
	}
}
