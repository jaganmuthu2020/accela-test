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

/**
 * Rest-controller class to receive the inputs from rest client.
 * 
 */
@RestController
@RequestMapping("/api/v1")
public class PersonManagementController {

	private static final Logger log = LoggerFactory.getLogger(PersonManagementController.class);

	@Autowired
	private PersonManagementService personService;

	@Autowired
	private AddressManagementService addressService;

	/**
	 * This method used to store person object to table. We can ignore address(its
	 * read-only) object in JSON input request body so we can pass only person
	 * object with address
	 * 
	 * @param person
	 * @return
	 */
	@PostMapping("/person")
	public ResponseEntity<Person> addPerson(@Valid @RequestBody Person person) {
		return ResponseEntity.ok().body(personService.saveOrUpdatePerson(person));
	}

	/**
	 * This method used to update person object to table. We can ignore address(its
	 * read-only) object in JSON input request body so we can pass only person
	 * object with address
	 * 
	 * @param person
	 * @return
	 */
	@PutMapping("/person")
	public ResponseEntity<Person> updatePerson(@Valid @RequestBody Person person) {
		return ResponseEntity.ok().body(personService.saveOrUpdatePerson(person));
	}

	/**
	 * This method used to delete the person object from table based on the person
	 * id input.
	 * 
	 * @param person
	 * @return
	 */
	@DeleteMapping("/person/{id}")
	public void deletePerson(@Valid @PathVariable String id) throws RecordNotFoundException {
		log.debug("Person ID for delete:" + id);
		personService.deletePerson(id);
	}

	/**
	 * This method used to list the complete person objects from table including
	 * address lists.
	 * 
	 * @return
	 */
	@GetMapping("/persons")
	public ResponseEntity<List<Person>> getPersons() {
		return ResponseEntity.ok(personService.getPlayers());
	}

	/**
	 * This used to get the total count of person objects in table.
	 * 
	 * @return
	 */
	@GetMapping("/personsCount")
	public ResponseEntity<Long> getPersonsCount() {
		return ResponseEntity.ok(personService.getPlayerCount());
	}

	/**
	 * This method used to save the address object for the given person id in path
	 * variable. We can ignore the person(its read-only) object in request body
	 * JSON.
	 * 
	 * @param personId
	 * @param address
	 * @return
	 * @throws RecordNotFoundException
	 */
	@PostMapping("/address/{personId}")
	public ResponseEntity<Address> addAddress(@Valid @PathVariable String personId, @Valid @RequestBody Address address)
			throws RecordNotFoundException {
		return ResponseEntity.ok(addressService.createAddress(personId, address));
	}

	/**
	 * This method used to update the address object for the given request JSON
	 * Body. We can ignore the person(its read-only) object in request body JSON.
	 * 
	 * @param address
	 * @return
	 * @throws RecordNotFoundException
	 */
	@PutMapping("/address")
	public ResponseEntity<Address> updateAddress(@Valid @RequestBody Address address) throws RecordNotFoundException {
		return ResponseEntity.ok(addressService.updateAddress(address));
	}

	/**
	 * This method used to delete the address object for the given address id in
	 * path variable.
	 * 
	 * @param id
	 * @throws RecordNotFoundException
	 */
	@DeleteMapping("/address/{id}")
	public void deeleteAddress(@Valid @PathVariable String id) throws RecordNotFoundException {
		addressService.deleteAddress(id);
	}
}
