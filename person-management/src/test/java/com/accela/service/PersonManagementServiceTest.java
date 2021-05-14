package com.accela.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.when;

import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.dao.EmptyResultDataAccessException;

import com.accela.entity.Person;
import com.accela.exception.RecordNotFoundException;
import com.accela.repository.PersonRepository;

@SpringBootTest
class PersonManagementServiceTest {

	@MockBean
	private PersonRepository personRepository;

	@Autowired
	private PersonManagementService perManagementService;

	private static final String PERSON_ID = "1";
	private static final String FIRST_NAME = "Jaganathan";
	private static final String LAST_NAME = "Muthusamy";

	private Person buildPerson() {
		return new Person(PERSON_ID, FIRST_NAME, LAST_NAME, null);
	}

	@Test
	void testSaveOrUpdateAddress() {
		Person person = buildPerson();
		when(personRepository.save(any())).thenReturn(person);
		Person actual = perManagementService.saveOrUpdatePerson(person);
		assertEquals(person, actual);
	}

	@Test
	void testDeletePerson() {
		doNothing().when(personRepository).deleteById(any());
		perManagementService.deletePerson("1");
	}

	@Test
	void testDeletePersonException() {
		doThrow(new EmptyResultDataAccessException("Entity not exists", 0)).when(personRepository).deleteById(any());
		try {
			perManagementService.deletePerson("0");
			fail("My method didn't throw when I expected it to");
		} catch (RecordNotFoundException expectedException) {
			//Do nothing
		}
	}

	@Test
	void testGetPlayers() {
		List<Person> persons = Arrays.asList(buildPerson());
		when(personRepository.findAll()).thenReturn(persons);
		List<Person> findAll = perManagementService.getPlayers();
		assertEquals(persons, findAll);
	}

	@Test
	void testGetPlayerCount() {
		long expected = 2;
		when(personRepository.count()).thenReturn(2l);
		long actualCount = perManagementService.getPlayerCount();
		assertEquals(expected, actualCount);
	}

}
