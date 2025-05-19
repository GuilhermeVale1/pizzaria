package com.sistema.pizzaria.controllers;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sistema.pizzaria.dtos.PizzaRecordDto;
import com.sistema.pizzaria.models.PizzaModel;
import com.sistema.pizzaria.repositories.PizzaRepository;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;

@RestController
public class PizzaController {

	@Autowired
	PizzaRepository pizzaRepository;

	@PostMapping(value = "/pizzas", consumes = { "multipart/form-data" })
	public ResponseEntity<PizzaModel> savePizza(@RequestParam("pizza") String pizzaJson,
			@RequestParam("image") MultipartFile imageFile) throws IOException {

		ObjectMapper objectMapper = new ObjectMapper();
		@Valid
		PizzaRecordDto pizzaRecordDto = objectMapper.readValue(pizzaJson, PizzaRecordDto.class);

		String fileName = UUID.randomUUID() + "_" + imageFile.getOriginalFilename();
		Path imagePath = Paths.get("imagens", fileName);
		Files.createDirectories(imagePath.getParent());
		Files.write(imagePath, imageFile.getBytes());

		var pizzaModel = new PizzaModel();
		BeanUtils.copyProperties(pizzaRecordDto, pizzaModel);
		pizzaModel.setImage(fileName);

		return ResponseEntity.status(HttpStatus.CREATED).body(pizzaRepository.save(pizzaModel));

	}

	@GetMapping("/pizzas")
	public ResponseEntity<List<PizzaModel>> getAllPizzas(HttpServletRequest request) {

		List<PizzaModel> pizzaListModel = pizzaRepository.findAll();

		if (!pizzaListModel.isEmpty()) {
			for (PizzaModel pizza : pizzaListModel) {
				UUID uuid = pizza.getId();
				pizza.add(linkTo(methodOn(PizzaController.class).getOnePizza(uuid, request)).withSelfRel());

				String imagemUrl = ServletUriComponentsBuilder.fromRequestUri(request)
						.replacePath("/imagens/" + pizza.getImage()).build().toUriString();

				pizza.setImage(imagemUrl);
			}
		}
		return ResponseEntity.status(HttpStatus.OK).body(pizzaListModel);
	}

	@GetMapping("/pizzas/{id}")
	public ResponseEntity<Object> getOnePizza(@PathVariable(value = "id") UUID id, HttpServletRequest request) {

		Optional<PizzaModel> pizzaOptional = pizzaRepository.findById(id);

		if (pizzaOptional.isEmpty()) {
			return notFound();

		}
		PizzaModel pizza = pizzaOptional.get();

		String imagemUrl = ServletUriComponentsBuilder.fromRequestUri(request)
				.replacePath("/imagens/" + pizza.getImage()).build().toUriString();

		pizza.setImage(imagemUrl);

		pizza.add(linkTo(methodOn(PizzaController.class).getAllPizzas(request)).withRel("Pizzas List"));

		return ResponseEntity.status(HttpStatus.OK).body(pizza);

	}

	@PutMapping("/pizzas/{id}")
	public ResponseEntity<Object> updatePizza(@PathVariable(value = "id") UUID id,
			@RequestBody @Valid PizzaRecordDto pizzaRecordDto) {

		Optional<PizzaModel> pizza = pizzaRepository.findById(id);

		if (pizza.isEmpty()) {
			return notFound();
		}

		var pizzaGet = pizza.get();

		BeanUtils.copyProperties(pizzaRecordDto, pizzaGet);

		return ResponseEntity.status(HttpStatus.OK).body(pizzaRepository.save(pizzaGet));
	}

	@DeleteMapping("/pizzas/{id}")
	public ResponseEntity<Object> deletePizza(@PathVariable(value = "id") UUID id) {

		Optional<PizzaModel> pizza = pizzaRepository.findById(id);

		if (pizza.isEmpty()) {
			return notFound();
		}

		var pizzaGet = pizza.get();

		pizzaRepository.delete(pizzaGet);

		return ResponseEntity.status(HttpStatus.OK).body("Pizza deletada");
	}

	public ResponseEntity<Object> notFound() {
		return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Product not found");
	}

}
