package com.example.demo.form.insert;

import javax.validation.constraints.NotBlank;

import com.example.demo.configurer.RegexpMessage;

import lombok.Data;

@Data
public class InsertUserForm {
	@NotBlank(message=RegexpMessage.EMPTY)
	private String userIdName;
}
