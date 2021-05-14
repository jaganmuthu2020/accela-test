package com.accela.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.core.MethodParameter;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.validation.BeanPropertyBindingResult;
import org.springframework.web.bind.MethodArgumentNotValidException;

import com.accela.entity.Address;
import com.accela.entity.Person;
import com.accela.exception.RecordNotFoundException;
import com.accela.service.AddressManagementService;
import com.accela.service.PersonManagementService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;

@SpringBootTest
@AutoConfigureMockMvc
class PersonManagementControllerTest {

	private final static String PERSON_SERVICE_URL = "/api/v1/person";
	private final static String PERSONS_SERVICE_URL = "/api/v1/persons";
	private final static String PERSONS_COUNT_SERVICE_URL = "/api/v1/personsCount";
	private final static String ADDRESS_SERVICE_URL = "/api/v1/address";

	@Autowired
	private MockMvc mockMvc;

	@MockBean
	private PersonManagementService personService;

	@MockBean
	private AddressManagementService addressService;

	private static final String PERSON_ID = "JM005";
	private static final String FIRST_NAME = "Jaganathan";
	private static final String LAST_NAME = "Muthusamy";

	private static final String ID = "D4001";
	private static final String STREET = "Ringsend";
	private static final String CITY = "Dublin";
	private static final String STATE = "Leinster";
	private static final String POSTAL_CODE = "D04";

	private Person buildPerson() {
		return new Person(PERSON_ID, FIRST_NAME, LAST_NAME);
	}

	private Address buildAddress() {
		return new Address(ID, STREET, CITY, STATE, POSTAL_CODE, buildPerson());
	}

	@Test
	void testAddPerson() throws JsonProcessingException, Exception {
		Person person = buildPerson();
		ObjectWriter objectWriter = new ObjectMapper().writer().withDefaultPrettyPrinter();

		when(personService.saveOrUpdatePerson(any())).thenReturn(person);

		/*
		 * mockMvc.perform(post(PERSON_SERVICE_URL).contentType(MediaType.
		 * APPLICATION_JSON_VALUE))
		 * .andExpect(status().isOk()).andExpect(content().json(objectWriter.
		 * writeValueAsString(person)));
		 */

		MvcResult mvcResult = mockMvc.perform(post(PERSON_SERVICE_URL).contentType(MediaType.APPLICATION_JSON_VALUE)
				.content(objectWriter.writeValueAsString(person))).andReturn();
		int status = mvcResult.getResponse().getStatus();
		assertEquals(200, status);
	}

	@Test
	void testUpdatePerson() throws JsonProcessingException, Exception {
		Person person = buildPerson();
		ObjectWriter objectWriter = new ObjectMapper().writer().withDefaultPrettyPrinter();
		when(personService.saveOrUpdatePerson(any())).thenReturn(person);
		MvcResult mvcResult = mockMvc.perform(put(PERSON_SERVICE_URL).contentType(MediaType.APPLICATION_JSON_VALUE)
				.content(objectWriter.writeValueAsString(person))).andReturn();
		int status = mvcResult.getResponse().getStatus();
		assertEquals(200, status);
	}

	@Test
	void testDeletePerson() throws Exception {
		StringBuffer url = new StringBuffer(PERSON_SERVICE_URL).append("/1");
		doNothing().when(personService).deletePerson(any());
		MvcResult mvcResult = mockMvc.perform(delete(url.toString())).andReturn();
		int status = mvcResult.getResponse().getStatus();
		assertEquals(200, status);
	}

	@Test
	void testDeletePersonException() throws Exception {
		StringBuffer url = new StringBuffer(PERSON_SERVICE_URL).append("/1");
		doThrow(new RecordNotFoundException("Entity not exists")).when(personService).deletePerson(any());
		mockMvc.perform(delete(url.toString())).andExpect(status().isNotFound());
	}

	@Test
	void testGetPersons() throws JsonProcessingException, Exception {
		List<Person> persons = Arrays.asList(buildPerson());
		ObjectWriter objectWriter = new ObjectMapper().writer().withDefaultPrettyPrinter();

		when(personService.getPlayers()).thenReturn(persons);

		mockMvc.perform(get(PERSONS_SERVICE_URL).contentType(MediaType.APPLICATION_JSON_VALUE))
				.andExpect(status().isOk()).andExpect(content().json(objectWriter.writeValueAsString(persons)));

	}

	@Test
	void testGetPersonsCount() throws JsonProcessingException, Exception {
		when(personService.getPlayerCount()).thenReturn(2l);
		ObjectWriter objectWriter = new ObjectMapper().writer().withDefaultPrettyPrinter();
		mockMvc.perform(get(PERSONS_COUNT_SERVICE_URL).contentType(MediaType.APPLICATION_JSON_VALUE))
				.andExpect(status().isOk()).andExpect(content().json(objectWriter.writeValueAsString(2)));
	}

	@Test
	void testAddAddress() throws JsonProcessingException, Exception {
		StringBuffer url = new StringBuffer(ADDRESS_SERVICE_URL).append("/JM005");
		Address address = buildAddress();
		ObjectWriter objectWriter = new ObjectMapper().writer().withDefaultPrettyPrinter();
		when(addressService.createAddress(any(), any())).thenReturn(address);
		MvcResult mvcResult = mockMvc.perform(post(url.toString()).contentType(MediaType.APPLICATION_JSON_VALUE)
				.content(objectWriter.writeValueAsString(address))).andReturn();
		int status = mvcResult.getResponse().getStatus();
		assertEquals(200, status);
	}

	@Test
	void testUpdateAddress() throws JsonProcessingException, Exception {
		// StringBuffer url = new StringBuffer(ADDRESS_SERVICE_URL).append("/JM005");
		Address address = buildAddress();
		ObjectWriter objectWriter = new ObjectMapper().writer().withDefaultPrettyPrinter();
		when(addressService.updateAddress(any())).thenReturn(address);
		MvcResult mvcResult = mockMvc.perform(put(ADDRESS_SERVICE_URL).contentType(MediaType.APPLICATION_JSON_VALUE)
				.content(objectWriter.writeValueAsString(address))).andReturn();
		int status = mvcResult.getResponse().getStatus();
		assertEquals(200, status);
	}

	@Test
	void testDeeleteAddress() throws Exception {
		StringBuffer url = new StringBuffer(ADDRESS_SERVICE_URL).append("/1");
		doNothing().when(addressService).deleteAddress(any());
		MvcResult mvcResult = mockMvc.perform(delete(url.toString())).andReturn();
		int status = mvcResult.getResponse().getStatus();
		assertEquals(200, status);
	}

	@Test
	void testDeeleteAddressException() throws Exception {
		StringBuffer url = new StringBuffer(ADDRESS_SERVICE_URL).append("/1");
		doThrow(new RecordNotFoundException("Entity not exists")).when(addressService).deleteAddress(any());
		MvcResult mvcResult = mockMvc.perform(delete(url.toString())).andReturn();
		int status = mvcResult.getResponse().getStatus();
		assertEquals(404, status);
	}
}
