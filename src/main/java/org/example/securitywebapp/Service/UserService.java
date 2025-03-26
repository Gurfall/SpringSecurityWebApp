package org.example.securitywebapp.Service;


import org.example.securitywebapp.Model.ERole;
import org.example.securitywebapp.Model.Role;
import org.example.securitywebapp.Model.User;
import org.example.securitywebapp.Repositories.RoleRepository;
import org.example.securitywebapp.Repositories.UserRepository;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class UserService implements UserDetailsService {
    private final UserRepository userRepository;
    private final BCryptPasswordEncoder passwordEncoder;
    private final RoleRepository roleRepository;

    public UserService(UserRepository userRepository, RoleRepository roleRepository) {
        this.userRepository = userRepository;
        this.passwordEncoder = new BCryptPasswordEncoder();
        this.roleRepository = roleRepository;
    }
    public User findByUsername(String username) {
        return userRepository.findByUsername(username);
    }
    public void deleteUser(int id) {
        userRepository.deleteById(id);
    }
    public void clearUsers() {
        userRepository.deleteAll();
    }
    public void updateUser(User user) {
        Set<Role> roles = new HashSet<>();
        roles.add(roleRepository.findRoleByName(ERole.ROLE_USER));
        if(user.getRoles() != roles) {
            roles.add(roleRepository.findRoleByName(ERole.ROLE_ADMIN));
        }
        user.setRoles(roles);
        userRepository.save(user);

    }
    public void createUser(User user) {
        String encodedPassword = passwordEncoder.encode(user.getPassword());
        user.setPassword(encodedPassword);

        Set<Role> roles = new HashSet<>();
        roles.add(roleRepository.findRoleByName(ERole.ROLE_USER));
        user.setRoles(roles);
        userRepository.save(user);
    }
    public List<User> findAllUsers() {
        return userRepository.findAll();
    }
    public User findUserById(int id) {
        return userRepository.findById(id).orElse(null);
    }
    @Override
    @Transactional
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = findByUsername(username);
        if (user == null) {
            throw new UsernameNotFoundException(username + " not found");
        } else
            return new org.springframework.security.core.userdetails.User(user.getUsername(), user.getPassword(), mapRolesToAuthority(user.getRoles()));
    }
    private Collection<? extends GrantedAuthority> mapRolesToAuthority(Collection<Role> roles) {
        return roles.stream()
                .map(role -> new SimpleGrantedAuthority(role.getRole()))
                .collect(Collectors.toList());
    }
}

