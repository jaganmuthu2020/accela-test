package com.accela.controller;

import java.util.List;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.accela.entity.Address;
import com.accela.entity.Person;
import com.accela.exception.RecordNotFoundException;
import com.accela.service.AddressManagementService;
import com.accela.service.PersonManagementService;

@RestController
@RequestMapping("/api/v1")
public class PersonManagementController {

	private static final Logger log = LoggerFactory.getLogger(PersonManagementController.class);

	@Autowired
	private PersonManagementService personService;

	@Autowired
	private AddressManagementService addressService;

	@PostMapping("/person")
	public ResponseEntity<Person> addPerson(@Valid @RequestBody Person person) {
		return ResponseEntity.ok().body(personService.saveOrUpdatePerson(person));
	}

	@PutMapping("/person")
	public ResponseEntity<Person> updatePerson(@Valid @RequestBody Person person) {
		return ResponseEntity.ok().body(personService.saveOrUpdatePerson(person));
	}

	@DeleteMapping("/person/{id}")
	public void deletePerson(@Valid @PathVariable String id) throws RecordNotFoundException {
		log.debug("Person ID for delete:"+id);
		personService.deletePerson(id);
	}

	@GetMapping("/persons")
	public ResponseEntity<List<Person>> getPersons() {
		return ResponseEntity.ok(personService.getPlayers());
	}

	@GetMapping("/personsCount")
	public ResponseEntity<Long> getPersonsCount() {
		return ResponseEntity.ok(personService.getPlayerCount());
	}

	@PostMapping("/address/{personId}")
	public ResponseEntity<Address> addAddress(@Valid @PathVariable String personId, @Valid @RequestBody Address address)
			throws RecordNotFoundException {
		return ResponseEntity.ok(addressService.createAddress(personId, address));
	}

	@PutMapping("/address")
	public ResponseEntity<Address> updateAddress(@Valid @RequestBody Address address) throws RecordNotFoundException {
		return ResponseEntity.ok(addressService.updateAddress(address));
	}

	@DeleteMapping("/address/{id}")
	public void deeleteAddress(@Valid @PathVariable String id) throws RecordNotFoundException {
		addressService.deleteAddress(id);
	}
}
