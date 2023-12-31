package com.flurent.domain;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import lombok.*;

@Data
@Entity
@Table(name="tbl_cmessage")
public class ContactMessage implements Serializable {/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Size(min=1, max=50, message="Your name '${validatedValue}' must be between {min} and {max} chars long")
	@NotNull(message="Please provide your name")
	@Column(length = 50, nullable = false)
	private String name;

	
	@Size(min=5, max=50, message="Your subject '${validatedValue}' must be between {min} and {max} chars long")
	@NotNull(message="Please provide message subject")
	@Column(length = 50, nullable = false)
	private String subject;

	@Size(min=20, max=200, message="Your message '${validatedValue}' must be between {min} and {max} chars long")
	@NotNull(message="Please provide message body")
	@Column(length = 200, nullable = false)
	private String body;
	
	
	@Email
	@Column(length=50, nullable=false)
	private String email;


}
