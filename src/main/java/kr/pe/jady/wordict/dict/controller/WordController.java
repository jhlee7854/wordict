package kr.pe.jady.wordict.dict.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class WordController {

	@RequestMapping(value = "/dict/word/search.do", method = {RequestMethod.GET})
	public String moveToSearch() {
		return "dict/word/search";
	}
	
}
