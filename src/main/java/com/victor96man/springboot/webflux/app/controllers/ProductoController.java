package com.victor96man.springboot.webflux.app.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.victor96man.springboot.webflux.app.models.dao.IProductoDao;
import com.victor96man.springboot.webflux.app.models.documents.Producto;

import reactor.core.publisher.Flux;

@Controller
public class ProductoController {

	@Autowired
	private IProductoDao dao;
	
	
	@GetMapping({"/listar", "/"})
	private String listar(Model model) {
		
		Flux<Producto> productos = dao.findAll();
		
		model.addAttribute("productos",productos);
		model.addAttribute("titulo", "Listado de Productos");
		return "listar";
	}
}
