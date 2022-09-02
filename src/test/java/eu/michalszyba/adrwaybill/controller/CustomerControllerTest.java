package eu.michalszyba.adrwaybill.controller;

import eu.michalszyba.adrwaybill.model.Customer;
import eu.michalszyba.adrwaybill.service.CustomerService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.util.List;

import static org.mockito.Mockito.when;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(SpringExtension.class)
@SpringBootTest
class CustomerControllerTest {

    @MockBean
    private CustomerService customerService;

    private MockMvc mockMvc;

    @Autowired
    private WebApplicationContext context;

    @BeforeEach
    public void setup() {
        mockMvc = MockMvcBuilders
                .webAppContextSetup(context)
                .apply(springSecurity())
                .build();
    }

    @WithMockUser(value = "spring")
    @Test
    void getListCustomers_shouldReturnViewWithPrefilledData() throws Exception {

        Customer customer = new Customer();
        customer.setId(1L);
        customer.setCustomerName("customer Name");

        when(customerService.getAllCustomers()).thenReturn(List.of(customer));

        this.mockMvc.perform(MockMvcRequestBuilders.get("/customer/list"))
                .andExpect(status().isOk())
                .andExpect(view().name("customer/customer-list"))
                .andExpect(MockMvcResultMatchers.model().attributeExists("customers"))
                .andExpect(model().attribute("customers", List.of(customer)));
    }

    @WithMockUser(value = "spring")
    @Test
    void getCustomerById_shouldReturnSuccess() throws Exception {
        Customer customer = new Customer();
        customer.setId(1L);
        customer.setCustomerName("customer Name");

        when(customerService.getByIdForCurrentUser(customer.getId())).thenReturn(customer);

        mockMvc.perform(MockMvcRequestBuilders.get("/customer/edit/1"))
                .andExpect(status().isOk());

    }
}