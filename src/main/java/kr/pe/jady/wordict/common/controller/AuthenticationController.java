package kr.pe.jady.wordict.common.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class AuthenticationController {
	
	@RequestMapping(value = "/signin", method = RequestMethod.GET)
	public String moveToSignin() {
		return "/signin";
	}
	
}
