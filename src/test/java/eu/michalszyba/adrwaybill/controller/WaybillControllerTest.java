package eu.michalszyba.adrwaybill.controller;

import eu.michalszyba.adrwaybill.model.Company;
import eu.michalszyba.adrwaybill.model.Customer;
import eu.michalszyba.adrwaybill.service.CompanyService;
import eu.michalszyba.adrwaybill.service.CustomerService;
import eu.michalszyba.adrwaybill.service.WaybillService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
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
class WaybillControllerTest {

    @MockBean
    private CompanyService companyService;

    @MockBean
    private CustomerService customerService;

    @MockBean
    private WaybillService waybillService;

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

    @WithMockUser
    @Test
    void populateCustomer() throws Exception {
        Customer customer = new Customer();
        customer.setId(1L);
        customer.setCustomerName("customer Name");
        customer.setCountry("San Escobar");

        when(customerService.getAllCustomers()).thenReturn(List.of(customer));

        mockMvc.perform(MockMvcRequestBuilders.get("/waybill/add"))
                .andExpect(status().isOk())
                .andExpect(model().attributeExists("customers"))
                .andExpect(model().attribute("customers", List.of(customer)));
    }

    @WithMockUser
    @Test
    void populateCompany() throws Exception {
        Company company = new Company();
        company.setId(1L);
        company.setCompanyName("company Name");
        company.setCountry("Poland");

        when(companyService.getAllCompany()).thenReturn(List.of(company));

        mockMvc.perform(MockMvcRequestBuilders.get("/waybill/add"))
                .andExpect(status().isOk())
                .andExpect(model().attributeExists("companies"))
                .andExpect(model().attribute("companies", List.of(company)));
    }
}