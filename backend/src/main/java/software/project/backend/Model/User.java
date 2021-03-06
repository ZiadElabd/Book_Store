package software.project.backend.Model;

public class User implements Imodel{
	private String userName;
	private String firstName;
	private String lastName;
	private String password;
	private String address;
	private String email;
	private int role;
	
	public User()
	{
		
	}
	


	public String getFirstName() {
		return this.firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return this.lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}
	
	public String getUserName() {
		return this.userName;
	}

	public void setUserName(String user) {
		this.userName = user;
	}
	
	public String getPassword() {
		return this.password;
	}

	public void setPassword(String pass) {
		this.password = pass;
	}
	
	public String getEmail() {
		return this.email;
	}

	public void setEmail(String p) {
		this.email = p;
	}
	
	public String getAddress() {
		return this.address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public int getRole() {
		return role;
	}

	public void setRole(int role) {
		this.role = role;
	}
}
