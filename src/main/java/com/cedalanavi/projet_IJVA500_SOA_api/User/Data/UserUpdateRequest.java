package com.cedalanavi.projet_IJVA500_SOA_api.User.Data;

import com.fasterxml.jackson.annotation.JsonIgnore;

public class UserUpdateRequest {
	
	@JsonIgnore(value = false)
	public String password;
}