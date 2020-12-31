package com.demo.rssfeeds.controller;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

/**
 * This class tests the RSSFeedsController
 */

@SpringBootTest
@AutoConfigureMockMvc
public class RSSFeedsControllerTest {

	@Autowired
	private MockMvc mockMvc;

	@Test
	public void checkSearchMethod_whenSearchIsMissing1() throws Exception {
		mockMvc.perform(get("/rssfeeds?")).andDo(print()).andExpect(status().is4xxClientError());
	}

	@Test
	public void checkSearchMethod_whenSearchIsMissing2() throws Exception {
		mockMvc.perform(get("/rssfeeds")).andDo(print()).andExpect(status().is4xxClientError());
	}

	@Test
	public void checkSearchMethod_whenSearchIsMissing3() throws Exception {
		mockMvc.perform(get("/rssfeeds?search")).andDo(print()).andExpect(status().is4xxClientError());
	}

	@Test
	public void checkSearchMethod_whenSearchIsEmpty() throws Exception {
		mockMvc.perform(get("/rssfeeds?search=   ")).andDo(print()).andExpect(status().isOk());
	}

	@Test
	public void checkCreateMethod_whenParametersAreNotOK1() throws Exception {
		mockMvc.perform(post("/rssfeeds").contentType(MediaType.APPLICATION_JSON).content("{ }")).andDo(print())
				.andExpect(status().is4xxClientError());
	}

	@Test
	public void checkCreateMethod_whenParametersAreNotOK2() throws Exception {
		mockMvc.perform(post("/rssfeeds").contentType(MediaType.APPLICATION_JSON).content("{ \"url\" : \"https://www.foo.com/rss\"}")).andDo(print())
				.andExpect(status().is4xxClientError());
	}

	@Test
	public void checkCreateMethod_whenParametersAreNotOK3() throws Exception {
		mockMvc.perform(post("/rssfeeds").contentType(MediaType.APPLICATION_JSON).content("{ \"refreshRate\":\"15\"}")).andDo(print())
				.andExpect(status().is4xxClientError());
	}

	@Test
	public void checkCreateMethod_whenParametersAreNotOK4() throws Exception {
		mockMvc.perform(
				post("/rssfeeds").contentType(MediaType.APPLICATION_JSON).content("{ \"url\" : \"https://www.foo.com/rss\", \"refreshRate\":\"0\"}"))
				.andDo(print()).andExpect(status().is4xxClientError());
	}

	@Test
	public void checkSearchMethod_whenParametersAreOK() throws Exception {
		mockMvc.perform(
				post("/rssfeeds").contentType(MediaType.APPLICATION_JSON).content("{ \"url\" : \"https://www.foo.com/rss\", \"refreshRate\":\"15\"}"))
				.andDo(print());
		mockMvc.perform(get("/rssfeeds?search=foo.com")).andDo(print()).andExpect(status().is2xxSuccessful())
				.andExpect(content().contentType(MediaType.APPLICATION_JSON))
				.andExpect(jsonPath("$.rssFeeds[0].url").value("https://www.foo.com/rss"))
				.andExpect(jsonPath("$.rssFeeds[0].refreshRate").value("15"));
	}

	@Test
	public void checkFindByIdMethod_whenParametersAreOK() throws Exception {
		mockMvc.perform(get("/rssfeeds/1")).andExpect(status().is2xxSuccessful()).andExpect(content().contentType(MediaType.APPLICATION_JSON))
				.andExpect(jsonPath("$.url").value("https://www.foo.com/rss")).andExpect(jsonPath("$.refreshRate").value("15"));
	}

	@Test
	public void checkFindByIdMethod_whenParametersAreNotOK() throws Exception {
		mockMvc.perform(get("/rssfeeds/-1")).andDo(print()).andExpect(status().is4xxClientError());

	}

	// TO BE CONTINUED

}
