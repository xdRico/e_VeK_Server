package de.ehealth.evek.entity;

import java.util.List;
import java.util.Optional;

import de.ehealth.evek.type.Id;
import de.ehealth.evek.type.Reference;
import de.ehealth.evek.type.UserRole;

public record User(
		Id<User> id,
		String lastName,
		String firstName,
		Reference<Adress> adress,
		String userName,
		String password,
		Reference<ServiceProvider> serviceProvider,
		UserRole role
		) {

	public static sealed interface Command permits Create, Update, Delete, UpdateRole {		
	}
	
	public static record Create(
			String lastName,
			String firstName,
			Reference<Adress> adress,
			String userName,
			String password,
			Reference<ServiceProvider> serviceProvider,
			UserRole role) implements Command{	
	}
	
	public static record Delete(Id<User> id) implements Command{	
	}
	
	public static record Update(
			Id<User> id, 
			String lastName,
			String firstName,
			Reference<Adress> adress,
			String userName,
			String password,
			Reference<ServiceProvider> serviceProvider
			) implements Command{
	}
	
	public static record UpdateRole(UserRole role) implements Command{	
	}
	
	public static record Filter(Optional<String> lastName, Optional<String> firstName, 
			Optional<Reference<Adress>> adress, Optional<String> userName,
			Optional<Reference<ServiceProvider>> serviceProvider, Optional<UserRole> role) {
	}

	public static interface Operations {
		User process(Command cmd) throws Exception;

		List<User> getUser(Filter filter);

		ServiceProvider getUser(Id<User> id);
	}

	public User updateWith(
			String newLastName,
			String newFirstName,
			Reference<Adress> newAdress,
			Reference<ServiceProvider> newServiceProvider,
			UserRole newRole) {
		return new User(this.id, newLastName, newFirstName, newAdress, 
				this.userName, this.password, newServiceProvider, newRole);
	}
	
	public User updateLogin(
			String newUserName,
			String newPassword) {
		return new User(this.id, this.lastName, this.firstName, this.adress, 
				newUserName, newPassword, this.serviceProvider, this.role);
	}
	
	
	
	public String toString() {
		return String.format(
				"User[id=%s, lastName=%s, firstName=%s, "
				+ "adress=%s, userName=%s, "
				+ "password=*****, serviceProvider=%s, "
				+ "role=%s]", 
				id,
				lastName,
				firstName,
				adress.toString(),
				userName,
				password,
				serviceProvider.toString(),
				role.toString());
	}
}
