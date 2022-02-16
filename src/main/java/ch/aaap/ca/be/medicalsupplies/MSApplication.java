package ch.aaap.ca.be.medicalsupplies;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import ch.aaap.ca.be.medicalsupplies.data.CSVUtil;
import ch.aaap.ca.be.medicalsupplies.data.MSGenericNameRow;
import ch.aaap.ca.be.medicalsupplies.data.MSProductIdentity;
import ch.aaap.ca.be.medicalsupplies.data.MSProductRow;
import ch.aaap.ca.be.medicalsupplies.model.GenericName;
import ch.aaap.ca.be.medicalsupplies.model.LicenceHolder;
import ch.aaap.ca.be.medicalsupplies.model.Producer;
import ch.aaap.ca.be.medicalsupplies.model.Product;

public class MSApplication {

    private final Set<MSGenericNameRow> genericNames;
    private final Set<MSProductRow> registry;
    private final List<Product> productRegistry;

    public MSApplication() {
        genericNames = CSVUtil.getGenericNames();
        registry = CSVUtil.getRegistry();
        productRegistry = (List<Product>) createModel(genericNames, registry);
        
    }

    public static void main(String[] args) {
        MSApplication main = new MSApplication();

        System.err.println("generic names count: " + main.genericNames.size());
        System.err.println("registry count: " + main.registry.size());

        System.err.println("1st of generic name list: " + main.genericNames.iterator().next());
        System.err.println("1st of registry list: " + main.registry.iterator().next());
        
    }

    /**
     * Create a model / data structure that combines the input sets.
     * 
     * @param genericNameRows
     * @param productRows
     * @return
     */
    public Object createModel(Set<MSGenericNameRow> genericNameRows, Set<MSProductRow> productRows) {
    	List<GenericName> genericNames = new ArrayList<>();
    	List<Product> productRegistry = new ArrayList<>();
    	
    	for(MSGenericNameRow row : genericNameRows) {
    		GenericName genericName = new GenericName();
    		genericName.setId(row.getId());
    		genericName.setName(row.getName());
    		genericName.setCategory1(row.getCategory1());
    		genericName.setCategory2(row.getCategory2());
    		genericName.setCategory3(row.getCategory3());
    		genericName.setCategory4(row.getCategory4());
    		
    		genericNames.add(genericName);
    	}
    	
    	for (MSProductRow row : productRows) {
			Producer producer = new Producer(row.getProducerId(), row.getProducerName(), 
					row.getProducerAddress());
			
			LicenceHolder licenceHolder = new LicenceHolder(row.getLicenseHolderId(),
					row.getLicenseHolderName(), row.getLicenseHolderAddress());
			
			Product product = new Product();
			product.setId(row.getId());
			product.setName(row.getName());
			product.setProducer(producer);
			product.setLicenceHolder(licenceHolder);
			
			GenericName genericName = null;
			for(GenericName name : genericNames) {
				if(name.getName().equals(row.getGenericName()) && 
						row.getPrimaryCategory().matches(name.getCategory1() + "|" + name.getCategory2() + "|" 
								+ name.getCategory3() + "|" + name.getCategory4())) {
					genericName = name;
					break;
				}
			}
			
			if(genericName != null) {
				product.setGenericName(genericName);
			}else {
				GenericName newGenericName = new GenericName();
				newGenericName.setName(row.getGenericName());
				newGenericName.setCategory1(row.getPrimaryCategory());
				newGenericName.setCategory2("");
				newGenericName.setCategory3("");
				newGenericName.setCategory4("");
				product.setGenericName(newGenericName);
			}
			productRegistry.add(product);
		}
        
    	return productRegistry;
        
    }

    /* MS Generic Names */
    /**
     * Method find the number of unique generic names.
     * 
     * @return
     */
    public Object numberOfUniqueGenericNames() {
    	Set<String> uniqueGenericNames = new HashSet<>();
    	
    	for(MSGenericNameRow row : genericNames) {
    		uniqueGenericNames.add(row.getName());
    	}

		return uniqueGenericNames.size();
    }

    /**
     * Method finds the number of generic names which are duplicated.
     * 
     * @return
     */
    public Object numberOfDuplicateGenericNames() {
    	List<MSGenericNameRow> genericNamesList = new ArrayList<>(genericNames);
    	int number = 0;
    	
    	for(int i = 0; i < genericNamesList.size(); i++) {
    		MSGenericNameRow rowI = genericNamesList.get(i);
    		for(int j = i + 1; j < genericNamesList.size(); j++) {
    			MSGenericNameRow rowJ = genericNamesList.get(j);
    			if(rowI.getName().equals(rowJ.getName())) {
    				number++;
    				continue;
    			}
    		}
    		
    	}
    	
    	return number;
    }

    /* MS Products */
    /**
     * Method finds the number of products which have a generic name which can be
     * determined.
     * 
     * @return
     */
    public Object numberOfMSProductsWithGenericName() {
    	Set<String> uniqueGenericNames = new HashSet<>();
    	int number = 0;
    	
    	for(MSGenericNameRow row : genericNames) {
    		uniqueGenericNames.add(row.getName());
    	}
    	
    	for(Product product : productRegistry) {
    		if(uniqueGenericNames.contains(product.getGenericName().getName())) {
    			number++;
    		}
    	}
    	
    	return number;
    }

    /**
     * Method finds the number of products which have a generic name which can NOT
     * be determined.
     * 
     * @return
     */
    public Object numberOfMSProductsWithoutGenericName() {
    	int number = productRegistry.size() - (int)numberOfMSProductsWithGenericName();
    	
    	return number;
    }

    /**
     * Method finds the name of the company which is both the producer and license holder for the
     * most number of products.
     * 
     * @return
     */
    public Object nameOfCompanyWhichIsProducerAndLicenseHolderForMostNumberOfMSProducts() {
        List<String> companiesSameProducerAndLicenceHolder = new ArrayList<>();
        
        for(Product product : productRegistry) {
        	if(product.getProducer().getName().equals(product.getLicenceHolder().getName())) {
        		companiesSameProducerAndLicenceHolder.add(product.getProducer().getName());
        	}
        }
        
        String maxCompanyName = "";
        int max = 0;
        for(String companyName : companiesSameProducerAndLicenceHolder) {
        	int frequency = Collections.frequency(companiesSameProducerAndLicenceHolder, companyName);
        	if(frequency > max) {
        		max = frequency;
        		maxCompanyName = companyName;
        	}
        }
        
        return maxCompanyName;
    }

    /**
     * Method finds the number of products whose producer name starts with
     * <i>companyName</i>.
     * 
     * @param companyName
     * @return
     */
    public Object numberOfMSProductsByProducerName(String companyName) {
        int number = 0;
        
        for(Product product : productRegistry) {
        	if(product.getProducer().getName().toLowerCase().startsWith(companyName.toLowerCase())) {
        		number++;
        	} 
        }
        
        return number;
    }

    /**
     * Method finds the products whose generic name has the category of interest.
     * 
     * @param category
     * @return
     */
    public Set<MSProductIdentity> findMSProductsWithGenericNameCategory(String category) {
    	Set<MSProductIdentity> productsWithCategory = new HashSet<>();
    	
		for (Product product : productRegistry) { 
			String genericName = product.getGenericName().getName();
			for(MSGenericNameRow genericNameRow : genericNames) {
				if(genericNameRow.getName().equals(genericName)) {
					if(genericNameRow.getCategory1().equals(category) || genericNameRow.getCategory2().equals(category) 
							|| genericNameRow.getCategory3().equals(category) || genericNameRow.getCategory4().equals(category)) {
						productsWithCategory.add(product); 
						break; 
					} 
				} 
			} 
		}
    	
    	return productsWithCategory;
    }
}
