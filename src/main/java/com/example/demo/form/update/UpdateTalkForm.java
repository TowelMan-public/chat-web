package com.example.demo.form.update;

import javax.validation.constraints.NotBlank;

import com.example.demo.configurer.RegexpMessage;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UpdateTalkForm {
	@NotBlank(message=RegexpMessage.EMPTY)
	private String talkContent;
}