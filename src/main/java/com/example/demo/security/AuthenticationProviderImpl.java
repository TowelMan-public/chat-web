package com.example.demo.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationCredentialsNotFoundException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.authentication.dao.AbstractUserDetailsAuthenticationProvider;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import com.example.demo.client.exception.LoginFailureException;
import com.example.demo.sevice.UserDetailsServiceImp;

/**
 * 実際にログイン処理を行うクラス
 * AbstractUserDetailsAuthenticationProviderを継承している
 * 
 * @see org.springframework.security.authentication.dao.AbstractUserDetailsAuthenticationProvider
 */
@Component
public class AuthenticationProviderImpl extends AbstractUserDetailsAuthenticationProvider {
	@Autowired
	UserDetailsServiceImp userDetailsServiceImp;

	private static final String DUMMY_PASSWORD = "DUMMY_PASSWORD"; //認証では使用しないため、値は何でもよい(nullや空文字列はNG)
	
	/**
	 * なにもしない
	 */
	@Override
	protected void additionalAuthenticationChecks(UserDetails userDetails,
			UsernamePasswordAuthenticationToken authentication) throws AuthenticationException {}

	/**
	 * ログイン処理を書くところ
	 */
	@Override
	protected UserDetails retrieveUser(String userIdName, UsernamePasswordAuthenticationToken authentication)	throws AuthenticationException {
	    String password = (String) authentication.getCredentials();
	    
	    //入力ﾁｪｯｸ
        if ("".equals(userIdName) || "".equals(password))  {
            // 例外はSpringSecurityにあったものを適当に使用
            throw new AuthenticationCredentialsNotFoundException("ログイン情報に不備があります。");
        }
        
        //認証及び認証情報の作成
        UserDetailsImp user = new UserDetailsImp();
        try {
	        user.setTokenForServer(
	        		userDetailsServiceImp.loginAndReturnToken(userIdName, password));
	        user.setUsername(userIdName);
	        user.setPassword(DUMMY_PASSWORD);
        }catch(LoginFailureException e){
        	throw new BadCredentialsException(e.getMessage());
        }
        //認証情報を返す
        return user;
	}	
}
