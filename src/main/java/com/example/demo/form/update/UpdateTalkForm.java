package com.example.demo.form.update;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import com.example.demo.configurer.RegexpMessage;
import com.example.demo.configurer.SizeConfig;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UpdateTalkForm {
	@Size(max=SizeConfig.CONTENT_TEXT_MAX_SIZE, message=RegexpMessage.CONTENT_TEXT_MAX_SIZE)
	@NotBlank(message=RegexpMessage.EMPTY)
	private String talkContent;
}