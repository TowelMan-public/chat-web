package com.example.demo.form.insert;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import com.example.demo.configurer.RegexpMessage;
import com.example.demo.configurer.SizeConfig;

import lombok.Data;

@Data
public class InsertTalkForm {
	@Size(max=SizeConfig.CONTENT_TEXT_MAX_SIZE, message=RegexpMessage.CONTENT_TEXT_MAX_SIZE)
	@NotBlank(message=RegexpMessage.EMPTY)
	private String talkContent;
}
