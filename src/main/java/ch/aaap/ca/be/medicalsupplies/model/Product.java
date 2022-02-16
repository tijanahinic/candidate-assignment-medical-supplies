package ch.aaap.ca.be.medicalsupplies.model;

import ch.aaap.ca.be.medicalsupplies.data.MSProductIdentity;

public class Product implements MSProductIdentity{
	
	private String id;
	private String name;
	
	private GenericName genericName;
	
	private Producer producer;
	
	private LicenceHolder licenceHolder;
	
	public Product() {}

	public Product(String id, String name, GenericName genericName, Producer producer,
			LicenceHolder licenceHolder) {
		super();
		this.id = id;
		this.name = name;
		this.genericName = genericName;
		this.producer = producer;
		this.licenceHolder = licenceHolder;
	}

	@Override
	public String getID() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public GenericName getGenericName() {
		return genericName;
	}

	public void setGenericName(GenericName genericName) {
		this.genericName = genericName;
	}

	public Producer getProducer() {
		return producer;
	}

	public void setProducer(Producer producer) {
		this.producer = producer;
	}

	public LicenceHolder getLicenceHolder() {
		return licenceHolder;
	}

	public void setLicenceHolder(LicenceHolder licenceHolder) {
		this.licenceHolder = licenceHolder;
	}

	@Override
	public String toString() {
		return "Product [id=" + id + ", name=" + name + ", genericName=" + genericName + ", producer=" + producer + ", licenceHolder=" + licenceHolder + "]";
	}

		
}
