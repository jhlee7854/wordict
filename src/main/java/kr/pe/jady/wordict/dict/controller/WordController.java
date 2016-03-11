package kr.pe.jady.wordict.dict.controller;

import kr.pe.jady.wordict.domain.model.Word;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.ArrayList;
import java.util.List;

@Controller
public class WordController {

	@RequestMapping(value = "/dict/word/search", method = {RequestMethod.GET})
	public String moveToSearch() {
		return "dict/word/search";
	}

	@RequestMapping(value = "/dict/word/words", method = {RequestMethod.GET})
	public List<Word> getWords() {
		List<Word> list = new ArrayList<>();
		list.add(new Word("단어", "word", "단어", "word", "분리하여 자립적으로 쓸 수 있는 말이나 이에 준하는 말. 또는 그 말의 뒤에 붙어서 문법적 기능을 나타내는 말."));
		list.add(new Word("축약", "abbreviation", "축약", "abbr", "줄여서 간략하게 함."));
		return list;
	}
	
}
