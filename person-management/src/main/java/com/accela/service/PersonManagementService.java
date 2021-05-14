package com.accela.service;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import com.accela.entity.Person;
import com.accela.exception.RecordNotFoundException;
import com.accela.repository.PersonRepository;

@Service
public class PersonManagementService {

	@Autowired
	private PersonRepository repository;

	public Person saveOrUpdatePerson(Person entity) {
		return repository.save(entity);
	}

	@Transactional
	public void deletePerson(String id) throws RecordNotFoundException {
		try {
			repository.deleteById(id);
		} catch (EmptyResultDataAccessException e) {
			throw new RecordNotFoundException(e.getLocalizedMessage());
		}
	}

	public List<Person> getPlayers() {
		return repository.findAll();
	}

	public long getPlayerCount() {
		return repository.count();
	}
}
