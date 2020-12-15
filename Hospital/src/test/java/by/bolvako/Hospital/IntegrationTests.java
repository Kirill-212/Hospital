package by.bolvako.Hospital;
import by.bolvako.Hospital.model.Role;
import by.bolvako.Hospital.security.jwt.JwtTokenProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
@SpringBootTest
public class IntegrationTests {

    @Autowired
    WebApplicationContext webApplicationContext;

    @Autowired
    JwtTokenProvider jwtTokenProvider;
    private MockMvc mockMvc;

    protected void setUp() {
        mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
    }



    @Test
    void testSetJwtTokenProviderDOCTOR() throws Exception {
        setUp();
        List<Role> roles = new ArrayList<>();
        roles.add(new Role("ROLE_DOCTOR"));
        String token = jwtTokenProvider.createToken("1bolvako@mail.ru", roles);
        Authentication authentication = jwtTokenProvider.getAuthentication(token);
        SecurityContextHolder.getContext().setAuthentication(authentication);

        mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/doctor/doctor/19").header("Authorization", "Bearer_" + token))
                .andExpect(status().isOk());
    }

    @Test
    void testSetJwtTokenProviderADMIN() throws Exception {
        setUp();
        List<Role> roles = new ArrayList<>();
        roles.add(new Role("ROLE_ADMIN"));
        String token = jwtTokenProvider.createToken("bolvako@mail.ru", roles);
        Authentication authentication = jwtTokenProvider.getAuthentication(token);
        SecurityContextHolder.getContext().setAuthentication(authentication);

        mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/admin/admin/0").header("Authorization", "Bearer_" + token))
                .andExpect(status().isOk());
    }
    @Test
    void testSetJwtTokenProviderPATIENT() throws Exception {
        setUp();
        List<Role> roles = new ArrayList<>();
        roles.add(new Role("ROLE_USER"));
        String token = jwtTokenProvider.createToken("2bolvako@mail.ru", roles);
        Authentication authentication = jwtTokenProvider.getAuthentication(token);
        SecurityContextHolder.getContext().setAuthentication(authentication);

        mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/users/patient/24").header("Authorization", "Bearer_" + token))
                .andExpect(status().isOk());
    }
}