package com.example.demo.form.update;

import javax.validation.constraints.AssertTrue;
import javax.validation.constraints.NotBlank;

import com.example.demo.configurer.RegexpMessage;

import lombok.Data;

@Data
public class UpdatePasswordForm {
	@NotBlank(message=RegexpMessage.EMPTY)
	private String password;
	@NotBlank(message=RegexpMessage.EMPTY)
	private String oneMorePassword;
	
	@AssertTrue(message = "2つのパスワードが合致しません。もう一度お確かめください")
	public boolean isNotMatchesPassword() {
		return (password == null || oneMorePassword == null) || (password.equals(oneMorePassword));
	}
}