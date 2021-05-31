package com.example.demo.form.select;

import javax.validation.constraints.NotNull;

import com.example.demo.configurer.RegexpMessage;

import lombok.Data;

@Data
public class LoadTalkForm {
	@NotNull(message=RegexpMessage.EMPTY)
	private Integer startIndex;
}
