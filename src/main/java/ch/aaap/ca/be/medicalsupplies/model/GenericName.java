package ch.aaap.ca.be.medicalsupplies.model;

public class GenericName {
	
	private Long id;
	private String name;
	private String category1;
	private String category2;
	private String category3;
	private String category4;
	
	public GenericName() {}
	
	public GenericName(Long id, String name, String category1, String category2, String category3, String category4) {
		super();
		this.id = id;
		this.name = name;
		this.category1 = category1;
		this.category2 = category2;
		this.category3 = category3;
		this.category4 = category4;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getCategory1() {
		return category1;
	}

	public void setCategory1(String category1) {
		this.category1 = category1;
	}

	public String getCategory2() {
		return category2;
	}

	public void setCategory2(String category2) {
		this.category2 = category2;
	}

	public String getCategory3() {
		return category3;
	}

	public void setCategory3(String category3) {
		this.category3 = category3;
	}

	public String getCategory4() {
		return category4;
	}

	public void setCategory4(String category4) {
		this.category4 = category4;
	}

	@Override
	public String toString() {
		return "GenericName [id=" + id + ", name=" + name + ", category1=" + category1 + ", category2=" + category2
				+ ", category3=" + category3 + ", category4=" + category4 + "]";
	}

	
	
	
	
}
