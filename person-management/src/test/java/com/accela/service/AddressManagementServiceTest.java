package com.accela.service;

import static org.junit.jupiter.api.Assertions.fail;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.when;

import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.dao.EmptyResultDataAccessException;

import com.accela.entity.Address;
import com.accela.entity.Person;
import com.accela.exception.RecordNotFoundException;
import com.accela.repository.AddressRepository;
import com.accela.repository.PersonRepository;

@SpringBootTest
class AddressManagementServiceTest {

	@MockBean
	private PersonRepository personRepository;

	@MockBean
	private AddressRepository addressRepository;

	@Autowired
	private AddressManagementService addressService;

	private static final String PERSON_ID = "1";
	private static final String FIRST_NAME = "Jaganathan";
	private static final String LAST_NAME = "Muthusamy";

	private static final String ID = "1";
	private static final String STREET = "Ringsend";
	private static final String CITY = "Dublin";
	private static final String STATE = "Leinster";
	private static final String POSTAL_CODE = "D04";

	private Person buildPerson() {
		return new Person(PERSON_ID, FIRST_NAME, LAST_NAME, null);
	}

	private Address buildAddress() {
		return new Address(ID, STREET, CITY, STATE, POSTAL_CODE, null);
	}

	@Test
	void testCreateAddress() {
		Person person = buildPerson();
		Address address = buildAddress();
		address.setPerson(person);
		when(personRepository.findById(any())).thenReturn(Optional.of(person));
		when(addressRepository.save(any())).thenReturn(address);
		addressService.createAddress(person.getId(), address);
	}
	
	@Test
	void testCreateAddressEmptyPerson() {
		Person person = buildPerson();
		Address address = buildAddress();
		address.setPerson(person);
		when(personRepository.findById(any())).thenReturn(Optional.empty());
		when(addressRepository.save(any())).thenReturn(address);
		try {
			addressService.createAddress(person.getId(), address);
			fail("My method didn't throw when I expected it to");
		} catch (RecordNotFoundException expectedException) {
			//Do nothing
		}
	}
	
	@Test
	void testCreateAddressException() {
		Person person = buildPerson();
		Address address = buildAddress();
		address.setPerson(person);
		doThrow(new EmptyResultDataAccessException("", 0)).when(personRepository).findById(any());
		when(addressRepository.save(any())).thenReturn(address);
		try {
			addressService.createAddress(person.getId(), address);
			fail("My method didn't throw when I expected it to");
		} catch (RecordNotFoundException expectedException) {
			//Do nothing
		}
	}

	@Test
	void testUpdateAddress() {
		Person person = buildPerson();
		Address address = buildAddress();
		address.setPerson(person);
		when(addressRepository.findById(any())).thenReturn(Optional.of(address));
		when(addressRepository.save(any())).thenReturn(address);
		addressService.updateAddress(address);
	}
	
	@Test
	void testUpdateAddressEmptyAddress() {
		Person person = buildPerson();
		Address address = buildAddress();
		address.setPerson(person);
		when(addressRepository.findById(any())).thenReturn(Optional.empty());
		when(addressRepository.save(any())).thenReturn(address);
		try {
			addressService.updateAddress(address);
			fail("My method didn't throw when I expected it to");
		} catch (RecordNotFoundException expectedException) {
			//Do nothing
		}
	}
	
	@Test
	void testUpdateAddressException() {
		Person person = buildPerson();
		Address address = buildAddress();
		address.setPerson(person);
		doThrow(new EmptyResultDataAccessException("Entity not exists", 0)).when(addressRepository).findById(any());
		when(addressRepository.save(any())).thenReturn(address);
		try {
			addressService.updateAddress(address);
			fail("My method didn't throw when I expected it to");
		} catch (RecordNotFoundException expectedException) {
			//Do nothing
		}
	}
	
	@Test
	void testDeleteAddress() {
		doNothing().when(addressRepository).deleteById(any());
		addressService.deleteAddress("1");
	}

	@Test
	void testDeleteAddressException() {
		doThrow(new EmptyResultDataAccessException("Entity not exists", 0)).when(addressRepository).deleteById(any());
		try {
			addressService.deleteAddress("0");
			fail("My method didn't throw when I expected it to");
		} catch (RecordNotFoundException expectedException) {
			//Do nothing
		}
	}

}
