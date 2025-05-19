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
import com.sistema.pizzaria.dtos.BebidaRecordDto;
import com.sistema.pizzaria.models.BebidasModel;
import com.sistema.pizzaria.repositories.BebidaRepository;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;

@RestController

public class BebidaController {

	@Autowired
	BebidaRepository bebidaRepository;

	@PostMapping("/bebidas")
	public ResponseEntity<BebidasModel> saveBebida(@RequestParam("bebida") String bebidaJson,
			@RequestParam("image") MultipartFile imageFile) throws IOException {

		ObjectMapper objectMapper = new ObjectMapper();
		@Valid
		BebidaRecordDto bebidaDto = objectMapper.readValue(bebidaJson, BebidaRecordDto.class);

		
		String fileName = UUID.randomUUID() + "_" + imageFile.getOriginalFilename();
		Path imagePath = Paths.get("imagens", fileName);
		Files.createDirectories(imagePath.getParent());
		Files.write(imagePath, imageFile.getBytes());


		var bebidaModel = new BebidasModel();
		BeanUtils.copyProperties(bebidaDto, bebidaModel);
		bebidaModel.setImage(fileName);

		return ResponseEntity.status(HttpStatus.CREATED).body(bebidaRepository.save(bebidaModel));
	}

	@GetMapping("/bebidas")

	public ResponseEntity<List<BebidasModel>> getAll(HttpServletRequest request) {
		List<BebidasModel> bebidas = bebidaRepository.findAll();

		if (!bebidas.isEmpty()) {
			for (BebidasModel bebida : bebidas) {
				UUID id = bebida.getId();
				bebida.add(linkTo(methodOn(BebidaController.class).getOne(id, request)).withRel("Bebidas List"));

				String imagemUrl = ServletUriComponentsBuilder.fromRequestUri(request)
						.replacePath("/imagens/" + bebida.getImage()).toUriString();

				bebida.setImage(imagemUrl);
			}

		}

		return ResponseEntity.status(HttpStatus.OK).body(bebidas);

	}

	@GetMapping("/bebidas/{id}")
	public ResponseEntity<Object> getOne(@PathVariable(value = "id") UUID id, HttpServletRequest request) {

		Optional<BebidasModel> bebidaOptional = bebidaRepository.findById(id);
		if (bebidaOptional.isEmpty()) {
			return notFound();
		}
		BebidasModel bebida = bebidaOptional.get();
		bebida.add(linkTo(methodOn(BebidaController.class).getAll(request)).withRel("Bebidas List"));

		return ResponseEntity.status(HttpStatus.OK).body(bebida);

	}

	@PutMapping("/bebidas/{id}")
	public ResponseEntity<Object> updateBebida(@RequestBody @Valid BebidaRecordDto bebidaRecordDto,
			@PathVariable(value = "id") UUID id) {
		Optional<BebidasModel> bebida = bebidaRepository.findById(id);
		if (bebida.isEmpty()) {
			return notFound();
		}
		var bebidaGet = bebida.get();

		BeanUtils.copyProperties(bebidaRecordDto, bebidaGet);

		return ResponseEntity.status(HttpStatus.OK).body(bebidaRepository.save(bebidaGet));

	}

	@DeleteMapping("/bebidas/{id}")
	public ResponseEntity<Object> deleteBebida(@PathVariable(value = "id") UUID id) {

		Optional<BebidasModel> bebida = bebidaRepository.findById(id);
		if (bebida.isEmpty()) {
			return notFound();
		}

		bebidaRepository.delete(bebida.get());

		return ResponseEntity.status(HttpStatus.OK).body("Bebida deletada com sucesso");

	}

	public ResponseEntity<Object> notFound() {
		return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Product not found");
	}

}
