package de.ehealth.evek.entity;

import java.util.List;
import java.util.Optional;

import de.ehealth.evek.type.Id;
import de.ehealth.evek.type.Reference;

public record ServiceProvider(
		Id<ServiceProvider> id, 
		String name,
		String type,
		Boolean isHealthcareProvider,
		Boolean isTransportProvider,
		Reference<Adress> adress,
		String contactInfo) {

	public static sealed interface Command permits Create, Delete, Move, Update, UpdateService {
	}

	public static record Create(String name, String  type, 
			Adress adress, String contactInfo) implements Command {
	}

	public static record Delete(Id<ServiceProvider> id) implements Command {
	}
	
	public static record Move(Id<ServiceProvider> id, 
			Reference<Adress> adress) implements Command {
	}

	public static record Update(Id<ServiceProvider> id,
			String name, String type, String contactInfo) implements Command {
	}
	
	public static record UpdateService(Boolean providesHealthcare, 
			Boolean providesTransport) implements Command{
	}

	public static record Filter(Optional<Reference<Adress>> adress, 
			Optional<String> type, Optional<String> name) {
	}

	public static interface Operations {
		ServiceProvider process(Command cmd) throws Exception;

		List<ServiceProvider> getSrviceProvider(Filter filter);

		ServiceProvider getServiceProvider(Id<ServiceProvider> id);
	}

	public ServiceProvider updateWith(String newName, 
			String newType, Boolean isHealthcareProvider,
			Boolean isTransportProvider, Reference<Adress> newAdress, 
			String newContactInfo) {
		return new ServiceProvider(this.id, newName, newType, isHealthcareProvider, 
				isTransportProvider, newAdress, newContactInfo);
	}
	
	public String toString() {
		return String.format(
				"ServiceProvider[id=%s, name=%s, type=%s, "
				+ "isHealthcarePeovider=%s, isTransportProvider=%s, "
				+ "adress=%s, contactInfo=%s]", 
				id, name, type, isHealthcareProvider.toString(),
				isTransportProvider.toString(), adress.toString(), contactInfo);
	}
}