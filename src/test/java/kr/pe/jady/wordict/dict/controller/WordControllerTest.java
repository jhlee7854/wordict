package kr.pe.jady.wordict.dict.controller;

import kr.pe.jady.wordict.config.spring.web.WebConfig;
import kr.pe.jady.wordict.config.tiles.TilesConfig;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.servlet.view.json.MappingJackson2JsonView;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {WebConfig.class, TilesConfig.class})
@WebAppConfiguration
public class WordControllerTest {

	private MockMvc mockMvc;
	@InjectMocks
	private WordController controller;
	
	@Before
	public void setUp() {
		MockitoAnnotations.initMocks(this);
		mockMvc = MockMvcBuilders.standaloneSetup(controller).build();
	}

	@Test
	public void testMoveToSearch() throws Exception {
		mockMvc.perform(get("/dict/word/search.do"))
			.andExpect(status().isOk())
			.andExpect(view().name("dict/word/search"));
	}

	@Test
	public void testGetWords() throws Exception {
		MockMvc flashMockMvc = MockMvcBuilders.standaloneSetup(controller).setSingleView(new MappingJackson2JsonView()).build();
		flashMockMvc.perform(get("/dict/word/words.json"))
	        .andExpect(status().isOk())
			.andDo(MockMvcResultHandlers.print())
			.andExpect(jsonPath("$").exists())
			.andExpect(jsonPath("$.wordList").isArray())
			.andExpect(jsonPath("$.wordList[0].korean").value("단어"));
	}
}
