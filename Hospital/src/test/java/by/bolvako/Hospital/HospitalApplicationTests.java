package by.bolvako.Hospital;

import by.bolvako.Hospital.model.*;
import by.bolvako.Hospital.repository.DoctorRepository;
import by.bolvako.Hospital.repository.PatientRepository;
import by.bolvako.Hospital.repository.ReceptionRepository;
import by.bolvako.Hospital.repository.UserRepository;
import by.bolvako.Hospital.security.jwt.JwtTokenProvider;
import org.hamcrest.Matchers;
import org.junit.Assert;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
class HospitalApplicationTests {
	@MockBean
	private DoctorRepository doctorRepository;

	@MockBean
	private PatientRepository patientRepository;
	@MockBean
	private UserRepository userRepository;
	@MockBean
	private ReceptionRepository receptionRepository;

	@Autowired
	JwtTokenProvider jwtTokenProvider;
	@Autowired
	WebApplicationContext webApplicationContext;


	@Test
	public void testGetUsersRepository() {
		List<User> users = Arrays.asList(
				new User(),
				new User()
		);
		when(userRepository.findAll()).thenReturn(users);

		Assert.assertEquals(userRepository.findAll(), users);
	}

		    @Test
	    public void testGetPatientsRepository() {
				List<Patient> patients = Arrays.asList(
						new Patient(),
						new Patient()
				);
				when(patientRepository.findAll()).thenReturn(patients);

				Assert.assertEquals(patientRepository.findAll(), patients);
			}

	@Test
	public void testGetDoctorsRepository() {
		List<Doctor> doctors = Arrays.asList(
				new Doctor(),
				new Doctor()
		);
		when(doctorRepository.findAll()).thenReturn(doctors);

		Assert.assertEquals(doctorRepository.findAll(), doctors);
	}
	@Test
	public void testGetReceptionRepository() throws Exception {
		List<Reception> receptions = Arrays.asList(
				new Reception(),
				new Reception()
		);
		when(receptionRepository.findAll()).thenReturn(receptions);

		Assert.assertEquals(receptionRepository.findAll(), receptions);
	}
}
