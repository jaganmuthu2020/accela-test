package com.accela.service;

import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import com.accela.entity.Address;
import com.accela.entity.Person;
import com.accela.exception.RecordNotFoundException;
import com.accela.repository.AddressRepository;
import com.accela.repository.PersonRepository;

@Service
public class AddressManagementService {

	@Autowired
	private AddressRepository addressRepository;

	@Autowired
	private PersonRepository personRepository;

	@Transactional
	public Address createAddress(String personId, Address address) throws RecordNotFoundException {
		Person persion = null;
		try {
			Optional<Person> personOptionap = personRepository.findById(personId);
			if (personOptionap.isPresent()) {
				persion = personOptionap.get();
			}else {
				throw new RecordNotFoundException("Person Record not found for "+personId);
			}
			address.setPerson(persion);
			return addressRepository.save(address);
		} catch (EmptyResultDataAccessException e) {
			throw new RecordNotFoundException(e.getLocalizedMessage());
		}
	}

	@Transactional
	public Address updateAddress(Address address) throws RecordNotFoundException {
		Person persion = null;
		try {
			Optional<Address> addressOptionap = addressRepository.findById(address.getId());
			if (addressOptionap.isPresent()) {
				persion = addressOptionap.get().getPerson();
			}else {
				throw new RecordNotFoundException("Address Record not found for "+address.getId());
			}
			address.setPerson(persion);
			return addressRepository.save(address);
		} catch (EmptyResultDataAccessException e) {
			throw new RecordNotFoundException(e.getLocalizedMessage());
		}
	}

	@Transactional
	public void deleteAddress(String id) throws RecordNotFoundException {
		try {
			addressRepository.deleteById(id);
		} catch (EmptyResultDataAccessException e) {
			throw new RecordNotFoundException(e.getLocalizedMessage());
		}
	}

}
