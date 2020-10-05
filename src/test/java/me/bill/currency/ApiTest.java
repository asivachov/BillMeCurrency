package me.bill.currency;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class ApiTest {

    @Autowired
    private MockMvc mvc;

    private static final String GET_CURRENCY_PATH_MASK = "/api/getCurrency?code=%s";

    @Test
    public void testForEmptyCode() throws Exception {
        mvc.perform(MockMvcRequestBuilders.get(formatRequestPath("")).accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void testForMultipleSpacesCode() throws Exception {
        mvc.perform(MockMvcRequestBuilders.get(formatRequestPath("              ")).accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void testForCodeContainingNumber() throws Exception {
        mvc.perform(MockMvcRequestBuilders.get(formatRequestPath("RU1")).accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void testForCodeWithWrongLength() throws Exception {
        mvc.perform(MockMvcRequestBuilders.get(formatRequestPath("RU")).accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void testForUsd() throws Exception {
        mvc.perform(MockMvcRequestBuilders.get(formatRequestPath("USD")).accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.currency").value("United States dollar"));
    }

    @Test
    public void testForRubUpperCase() throws Exception {
        mvc.perform(MockMvcRequestBuilders.get(formatRequestPath("RUB")).accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.currency").value("Russian ruble"));
    }

    @Test
    public void testForRubLowerCase() throws Exception {
        mvc.perform(MockMvcRequestBuilders.get(formatRequestPath("rub")).accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.currency").value("Russian ruble"));
    }


    @Test
    public void testForUnexistedCurrencyCode() throws Exception {
        mvc.perform(MockMvcRequestBuilders.get(formatRequestPath("ZZU")).accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }

    private String formatRequestPath(String code) {
        return String.format(GET_CURRENCY_PATH_MASK, code);
    }
}