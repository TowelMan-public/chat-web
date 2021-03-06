package com.example.demo.security;

import java.util.Collection;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.UserDetails;

import lombok.Data;

/**
 * ユーザー情報を認証回りで使うためのオブジェクト
 * UserDetailsを実装している
 * 
 * @see org.springframework.security.core.userdetails.UserDetails
 */
@Data
public class UserDetailsImp implements UserDetails {
	private static final long serialVersionUID = 6088431978182349464L;
	
	/**
	 * 正確にはuserIdNameのこと<br>
	 * Spring Securityの構成上こうしている
	 */
	private String username;
	private String password;
	private String tokenForServer;
	
	//今回は関係ない
	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		return AuthorityUtils.createAuthorityList("USER");
	}
	@Override
	public boolean isAccountNonExpired() {
		return true;
	}
	@Override
	public boolean isAccountNonLocked() {
		return true;
	}
	@Override
	public boolean isCredentialsNonExpired() {
		return true;
	}
	@Override
	public boolean isEnabled() {
		return true;
	}
}
