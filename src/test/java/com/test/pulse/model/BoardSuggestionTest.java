package com.test.pulse.model;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import com.test.pulse.mapper.BoardSuggestionMapper;
import com.test.pulse.model.boardsuggestion.BoardSuggestionDTO;

@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@ContextConfiguration(locations={
		"file:src/main/webapp/WEB-INF/spring/root-context.xml",
		"file:src/main/webapp/WEB-INF/spring/appServlet/servlet-context.xml",
		"file:src/main/webapp/WEB-INF/spring/security-context.xml"
})

public class BoardSuggestionTest {

	@Autowired
	private BoardSuggestionMapper mapper;

	@Test
	public void testListSuggestion() {
		
		List<BoardSuggestionDTO> slist = mapper.suggestionList();
		
		assertNotNull(mapper);
		assertEquals(2, slist.size());
		
		
	}
	
}
