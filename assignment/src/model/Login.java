package model;

import bean.User;

public class Login {

	public static Boolean validate(User user) {
		System.out.println("model");
		String userName=user.getUserName();
		String password=user.getPassword();
		//change here to access dao :)
		if(userName.equals("admin") && password.equals("admin")){
			return true;
		}
		//upto here :)
		return false;
	}

}
