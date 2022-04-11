package eu.michalszyba.adrwaybill.controller;

import eu.michalszyba.adrwaybill.model.Company;
import eu.michalszyba.adrwaybill.service.CompanyService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
class CompanyControllerTest {

    @MockBean
    private CompanyService companyService;

    private MockMvc mockMvc;

    @Autowired
    private WebApplicationContext context;

    @BeforeEach
    public void setUp() {
        mockMvc = MockMvcBuilders
                .webAppContextSetup(context)
                .apply(springSecurity())
                .build();
    }

    @WithMockUser(value = "spring")
    @Test
    void getListCompany_shouldReturnViewPrefilledData() throws Exception {
        Company company = new Company();
        company.setId(1L);
        company.setCompanyName("company Name");
        company.setCountry("Poland");

        when(companyService.getAllCompany()).thenReturn(List.of(company));

        mockMvc.perform(MockMvcRequestBuilders.get("/admin/company/list"))
                .andExpect(status().isOk())
                .andExpect(view().name("company/company-list"))
                .andExpect(model().attributeExists("companies"))
                .andExpect(model().attribute("companies", List.of(company)));
    }

    @WithMockUser(value = "spring")
    @Test
    void getCompanyById_shouldReturnSuccess() throws Exception {
        Company company = new Company();
        company.setId(1L);
        company.setCompanyName("company Name");
        company.setCountry("Poland");

        when(companyService.getById(company.getId())).thenReturn(company);

        mockMvc.perform(MockMvcRequestBuilders.get("/admin/company/edit/1"))
                .andExpect(status().isOk())
                .andExpect(view().name("company/company-details"))
                .andExpect(model().attributeExists("company"))
                .andExpect(model().attribute("company", company));
    }

    @WithMockUser(value = "spring")
    @Test
    void getCompanyIdIsNull_shouldReturn3xx() throws Exception {
        when(companyService.getById(2L)).thenReturn(null);

        mockMvc.perform(MockMvcRequestBuilders.get("/admin/company/edit/2"))
                .andExpect(status().is3xxRedirection());
    }
}