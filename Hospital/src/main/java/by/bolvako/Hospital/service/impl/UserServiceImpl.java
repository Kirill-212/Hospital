package by.bolvako.Hospital.service.impl;

import by.bolvako.Hospital.model.Role;
import by.bolvako.Hospital.model.Status;
import by.bolvako.Hospital.model.User;
import by.bolvako.Hospital.repository.RoleRepository;
import by.bolvako.Hospital.repository.UserRepository;
import by.bolvako.Hospital.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Properties;

@Service
@Slf4j
public class UserServiceImpl implements UserService {
    private static final org.slf4j.Logger log =
            org.slf4j.LoggerFactory.getLogger(UserServiceImpl.class);
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final BCryptPasswordEncoder passwordEncoder;

    @Autowired
    public UserServiceImpl(UserRepository userRepository, RoleRepository roleRepository, BCryptPasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public User register(User user) {
        Role roleUser = roleRepository.findByName("ROLE_USER");
        List<Role> userRoles = new ArrayList<>();
        userRoles.add(roleUser);

        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setRoles(userRoles);
        user.setStatus(Status.NOT_ACTIVE);

        User registeredUser = userRepository.save(user);

        log.info("IN register - user: {} successfully registered", registeredUser);

        return registeredUser;
    }



    @Override
    public List<User> getAll() {
        List<User> result = userRepository.findAll();
        log.info("IN getAll - {} users found", result.size());
        return result;
    }

    @Override
    public Page<User> getPage(Pageable pageable) {
        return userRepository.findAll(pageable);
    }

    @Override
    public List<User> GetByFandL(String f, String l) {
        List<User> users=userRepository.findByFirstNameAndLastName(f,l);
        if(users.size()>0){
            log.info("IN GetByFandL - {} users found", users);
            return  users;
        }
        return null;
    }

    @Override
    public User findByEmail(String username) {
        User result = userRepository.findByEmail(username);
        log.info("IN findByUsername - user: {} found by username: {}", result, username);
        return result;
    }
    @Override
    public Long getRoleForId(Long id){
        User result1 = userRepository.findById(id).get();
        Long IdRole=result1.getRoles().get(0).getId();
        log.info("IN getRoleForId - user: {} found byIdRole: {}", IdRole);
        return IdRole;
    }
    @Override
    public User findById(Long id) {
        User result = userRepository.findById(id).orElse(null);
        if (result == null) {
            log.info("IN findById - no user found by id: {}", id);
            return null;
        }

        log.info("IN findById - user: {} ", result);
        return result;
    }

    @Override
    public void delete(Long id) {
        userRepository.deleteById(id);
        log.info("IN delete - user  successfully deleted");
    }
}

