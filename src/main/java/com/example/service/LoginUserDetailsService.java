package com.example.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

import com.example.domein.LoginUser;
import com.example.domein.LoginUserDetails;
import com.example.form.InsertUserForm;
import com.example.repository.LoginUserRepository;

@Service
public class LoginUserDetailsService implements UserDetailsService {

	@Autowired
	private LoginUserRepository loginUserRepository;

	public LoginUserDetailsService(LoginUserRepository loginUserRepository) {
		this.loginUserRepository = loginUserRepository;
	}

	@Autowired
	private JdbcTemplate jdbcTemplate;

	@Autowired
	private NamedParameterJdbcTemplate template;

	private static final RowMapper<LoginUser> USER_ROW_MAPPER = new BeanPropertyRowMapper<>(LoginUser.class);

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		try {
			System.out.println("loadUserByUserNameにきたよ");
			System.out.println("username = " + username);

			LoginUser user = loginUserRepository.findByName(username);
			String password = user.getPassword();

			System.out.println("ゆーざーのパスワード" + password);

//			Collection<GrantedAuthority> authorities = new ArrayList<>();
//			authorities.add(new SimpleGrantedAuthority(user.getAuthority().toString()));
			System.out.println("ABC");
			LoginUser loginUser = new LoginUser();
			loginUser.setName(username);
			loginUser.setPassword(password);
			System.out.println("DEF");
			return new LoginUserDetails(loginUser);
		} catch (Exception e) {
			System.out.println("loadUserByUserNameのcatchにきたよ");
			throw new UsernameNotFoundException("user not found.", e);
		}
	}

}

//	@Override
//	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
//
//		try {
//            String sql = "SELECT * FROM user WHERE name = ?";
//            Map<String, Object> map = jdbcTemplate.queryForMap(sql, username);
//            String password = (String)map.get("password");
//            Collection<GrantedAuthority> authorities = new ArrayList<>();
//            authorities.add(new SimpleGrantedAuthority((String)map.get("authority")));
//            return new UserDetailsImpl(username, password, authorities);
//        } catch (Exception e) {
//            throw new UsernameNotFoundException("user not found.", e);
//        }
//		
////		System.out.println(" email = "+email);
////		User user = userRepository.findByMail(email);
////		if(user == null) {
////			throw new UsernameNotFoundException("そのEmailは登録されていません");
////		}
//		
//		return null;
//	}

//以下は以前書いたコード
//import java.util.Optional;
//
//import org.springframework.security.core.userdetails.UserDetails;
//import org.springframework.security.core.userdetails.UserDetailsService;
//import org.springframework.security.core.userdetails.UsernameNotFoundException;
//import org.springframework.stereotype.Service;
//
//import com.example.domein.LoginUser;
//import com.example.domein.LoginUserDetails;
//import com.example.repository.LoginUserRepository;
//
//@Service
//public class LoginUserDetailsService implements UserDetailsService {
//
//	private final LoginUserRepository loginUserRepository;
//
//	public LoginUserDetailsService(LoginUserRepository loginUserRepository) {
//		this.loginUserRepository = loginUserRepository;
//	}
//
//	@Override
//	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
//		Optional<LoginUser> userOptinal = loginUserRepository.findByUsername(username);
//		return userOptinal.map(user -> new LoginUserDetails(user))
//				.orElseThrow(() -> new UsernameNotFoundException("not found"));
//
//	}
//
//}
