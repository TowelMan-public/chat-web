package com.example.demo.form.update;

import javax.validation.constraints.NotBlank;

import com.example.demo.configurer.RegexpMessage;

import lombok.Data;

@Data
public class UpdateIdNameForm {
	@NotBlank(message=RegexpMessage.EMPTY)
	private String idName;
	
	public UpdateIdNameForm(String idName) {
		this.idName = idName;
	}
	
	public UpdateIdNameForm() {}
}
