package com.example.demo.form.insert;

import javax.validation.constraints.AssertTrue;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import com.example.demo.configurer.RegexpMessage;
import com.example.demo.configurer.SizeConfig;

import lombok.Data;

@Data
public class SignupForm {
	@Size(max=SizeConfig.NAME_MAX_SIZE, message=RegexpMessage.NAME_MAX_SIZE)
	@NotBlank(message=RegexpMessage.EMPTY)
	private String userName;
	@Size(max=SizeConfig.NAME_MAX_SIZE, message=RegexpMessage.NAME_MAX_SIZE)
	@NotBlank(message=RegexpMessage.EMPTY)
	private String userIdName;
	@Size(max=SizeConfig.NAME_MAX_SIZE, message=RegexpMessage.NAME_MAX_SIZE)
	@NotBlank(message=RegexpMessage.EMPTY)
	private String password;
	@Size(max=SizeConfig.NAME_MAX_SIZE, message=RegexpMessage.NAME_MAX_SIZE)
	@NotBlank(message=RegexpMessage.EMPTY)
	private String oneMorePassword;
	
	@AssertTrue(message = "2つのパスワードが合致しません。もう一度お確かめください")
	public boolean isNotMatchesPassword() {
		return (password == null || oneMorePassword == null) || (password.equals(oneMorePassword));
	}
}
