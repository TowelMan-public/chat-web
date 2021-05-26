package com.example.demo.form.update;

import javax.validation.constraints.NotBlank;

import com.example.demo.configurer.RegexpMessage;

import lombok.Data;

@Data
public class UpdateNameForm {
	@NotBlank(message=RegexpMessage.EMPTY)
	private String name;
	
	public UpdateNameForm(String name) {
		this.name = name;
	}
	
	public UpdateNameForm() {}
}
