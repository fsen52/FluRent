package com.flurent.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import com.flurent.domain.enums.RoleType;

import lombok.Data;

@Data

@Entity
@Table(name="tbl_role")
public class Role {
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Integer id;
	
	@Enumerated(EnumType.STRING)
	@Column(length=20)
	private RoleType name;

	@Override
	public String toString() {
		return  ""+name+"";
	}

	
	
	
}
