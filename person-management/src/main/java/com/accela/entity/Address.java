package com.accela.entity;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonProperty.Access;

@Entity
public class Address {

	@Id
	@NotBlank(message = "Address Id is mandatory")
	@Size(min = 1, max = 10)
	private String id;

	@NotBlank(message = "Street is mandatory")
	@Size(min = 5, max = 30)
	private String street;

	@NotBlank(message = "City is mandatory")
	@Size(min = 5, max = 30)
	private String city;

	@NotBlank(message = "State is mandatory")
	@Size(min = 5, max = 30)
	private String state;

	@NotBlank(message = "Postal is mandatory")
	@Size(min = 2, max = 10)
	private String postalCode;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "person_id")
	@JsonProperty(access = Access.READ_ONLY)
	private Person person;

	public Address() {
		super();
	}

	public Address(String id, String street, String city, String state, String postalCode, Person person) {
		super();
		this.id = id;
		this.street = street;
		this.city = city;
		this.state = state;
		this.postalCode = postalCode;
		this.person = person;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getStreet() {
		return street;
	}

	public void setStreet(String street) {
		this.street = street;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public String getPostalCode() {
		return postalCode;
	}

	public void setPostalCode(String postalCode) {
		this.postalCode = postalCode;
	}

	public Person getPerson() {
		return person;
	}

	public void setPerson(Person person) {
		this.person = person;
	}

	@Override
	public String toString() {
		return "Address [id=" + id + ", street=" + street + ", city=" + city + ", state=" + state + ", postalCode="
				+ postalCode + ", person=" + person + "]";
	}

}
