package com.example.demo.form.update;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import com.example.demo.configurer.RegexpMessage;
import com.example.demo.configurer.SizeConfig;

import lombok.Data;

@Data
public class UpdateNameForm {
	@Size(max=SizeConfig.NAME_MAX_SIZE, message=RegexpMessage.NAME_MAX_SIZE)
	@NotBlank(message=RegexpMessage.EMPTY)
	private String name;
	
	public UpdateNameForm(String name) {
		this.name = name;
	}
	
	public UpdateNameForm() {}
}
