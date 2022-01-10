package pl.com.britenet.javastepone.service.user;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import pl.com.britenet.javastepone.repository.UserRepository;
import pl.com.britenet.javastepone.repository.model.user.MyUserDetails;

@Service
public class MyUserDetailsService implements UserDetailsService {

    private final UserRepository userRepository;

    public MyUserDetailsService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
        var user = userRepository.findByUsername(s);

        if (user.isEmpty()) {
            throw new UsernameNotFoundException("Could not find user");
        }

        return new MyUserDetails(user.orElseThrow());
    }
}
