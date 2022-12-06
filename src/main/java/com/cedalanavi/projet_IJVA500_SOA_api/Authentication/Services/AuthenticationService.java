package com.cedalanavi.projet_IJVA500_SOA_api.Authentication.Services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.client.RestTemplate;

import com.cedalanavi.projet_IJVA500_SOA_api.Authentication.Data.AuthCredentialsUpdateRequest;
import com.cedalanavi.projet_IJVA500_SOA_api.Authentication.Data.AuthenticationRequest;
import com.cedalanavi.projet_IJVA500_SOA_api.Authentication.Data.AuthenticationResource;
import com.cedalanavi.projet_IJVA500_SOA_api.User.Data.CreateUserRequest;

@Service
public class AuthenticationService {

	@Value("${authentication.service.url}")
	String authServiceUrl;
	
	@Value("${user.service.url}")
	String userServiceUrl;

	@Autowired
    @Qualifier("myRestTemplate")
	RestTemplate restTemplate;

	public void register(@RequestBody AuthenticationRequest authenticationRequest) {
		HttpEntity<AuthenticationRequest> authCreateRequest = new HttpEntity<AuthenticationRequest>(authenticationRequest);
		CreateUserRequest response = restTemplate.exchange(authServiceUrl + "/register", HttpMethod.POST, authCreateRequest, CreateUserRequest.class).getBody();
		
		if (response != null) {
			HttpEntity<CreateUserRequest> userCreateRequest = new HttpEntity<CreateUserRequest>(response);
			restTemplate.exchange(userServiceUrl + "/create", HttpMethod.POST, userCreateRequest, Void.class);
		}
	}
	
	public AuthenticationResource signin(AuthenticationRequest authRequest) {
		HttpEntity<AuthenticationRequest> request = new HttpEntity<AuthenticationRequest>(authRequest);
		return restTemplate.exchange(authServiceUrl + "/signin", HttpMethod.POST, request, AuthenticationResource.class).getBody();
	}
	
	public void updateUserCredentials(AuthCredentialsUpdateRequest userCredentialsUpdateRequest) {
		HttpEntity<AuthCredentialsUpdateRequest> request = new HttpEntity<AuthCredentialsUpdateRequest>(userCredentialsUpdateRequest);
		restTemplate.exchange(authServiceUrl + "/updateCredentials", HttpMethod.PUT, request, void.class);
	}

}
