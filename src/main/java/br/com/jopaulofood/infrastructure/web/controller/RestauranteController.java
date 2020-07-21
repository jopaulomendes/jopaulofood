package br.com.jopaulofood.infrastructure.web.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import br.com.jopaulofood.application.service.RestauranteService;
import br.com.jopaulofood.application.service.ValidationException;
import br.com.jopaulofood.domain.cliente.Cliente;
import br.com.jopaulofood.domain.restaurante.CategoriaRestauranteRepository;
import br.com.jopaulofood.domain.restaurante.Restaurante;
import br.com.jopaulofood.domain.restaurante.RestauranteRepository;
import br.com.jopaulofood.util.SecurityUtils;

@Controller
@RequestMapping(path = "/restaurante")
public class RestauranteController {
	
	@Autowired
	private RestauranteService restauranteService;
	
	@Autowired
	private RestauranteRepository restauranteRepository;
	
	@Autowired
	private CategoriaRestauranteRepository categoriaRestauranteRepository;

	@GetMapping(path = "/home")
	public String home() {
		return "restaurante-home";
	}
	
	@GetMapping(path = "/edit")
	public String edit(Model model) {
		Integer restauranteId = SecurityUtils.loggedRestaurante().getId();
		Restaurante restaurante = restauranteRepository.findById(restauranteId).orElseThrow();
		model.addAttribute("restaurante", restaurante);
		ControllerHelper.setEditMode(model, true);
		ControllerHelper.addCategoriaToRequest(categoriaRestauranteRepository, model);
		return "restaurante-cadastro";
	}
	
	@PostMapping(path = "/save")
	public String save(@ModelAttribute("restaurante") @Valid Restaurante restaurante, Errors errors, Model model) {		
		if (!errors.hasErrors()) {
			try {
				restauranteService.saveRestaurante(restaurante);
				model.addAttribute("msg", "Restaurante salvo com sucesso!");
			} catch (ValidationException e) {
				errors.rejectValue("email", null, e.getMessage());
			}
		}
		ControllerHelper.setEditMode(model, true);
		ControllerHelper.addCategoriaToRequest(categoriaRestauranteRepository, model);
		return "restaurante-cadastro";
	}
}
