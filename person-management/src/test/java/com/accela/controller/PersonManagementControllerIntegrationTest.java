package com.accela.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.web.client.RestTemplate;

import com.accela.entity.Person;

@SpringBootTest
public class PersonManagementControllerIntegrationTest {

	private static final String GET_PERSONS_URL = "http://localhost:8888/accela-person/api/v1/persons";
	private static final String PERSON_URL = "http://localhost:8888/accela-person/api/v1/person";

	RestTemplate restTemplate = null;

	@BeforeEach
	public void init() {
		restTemplate = new RestTemplate();
	}

	private static final String PERSON_ID = "JM115";
	private static final String FIRST_NAME = "Jaganathan";
	private static final String LAST_NAME = "Muthusamy";

	private Person buildPerson() {
		return new Person(PERSON_ID, FIRST_NAME, LAST_NAME);
	}

	@Disabled
	@SuppressWarnings("unchecked")
	@Test
	public void testGetPersons() {
		List<Person> persons = restTemplate.getForObject(GET_PERSONS_URL, List.class);
		assertNotNull(persons);
		assertEquals(2, persons.size());
	}

	@Disabled
	@Test
	public void testSavePersons() {
		Person person = buildPerson();
		Person personResponse = restTemplate.postForObject(PERSON_URL, person, Person.class);
		assertNotNull(personResponse);
		assertEquals(personResponse.getId(), personResponse.getId());
	}
}
