package org.nii.phenominer.searchdb.data.phenominer;

import javax.xml.bind.annotation.XmlRegistry;

/**
 * This object contains factory methods for each Java content interface and Java
 * element interface generated in this package.
 * <p>
 * An ObjectFactory allows you to programatically construct new instances of the
 * Java representation for XML content. The Java representation of XML content
 * can consist of schema derived interfaces and classes representing the binding
 * of schema type definitions, element declarations and model groups. Factory
 * methods for each of these are provided in this class.
 * 
 */
@XmlRegistry
public class ObjectFactory {

	/**
	 * Create a new ObjectFactory that can be used to create new instances of
	 * schema derived classes for package: dustin.examples
	 * 
	 */
	public ObjectFactory() {
	}

	/**
	 * Create an instance of {@link AbstractList }
	 * 
	 */
	public AbstractList createAbstractList() {
		return new AbstractList();
	}

	/**
	 * Create an instance of {@link Id }
	 * 
	 */
	public Id createId() {
		return new Id();
	}

	/**
	 * Create an instance of {@link AnnotationCollection }
	 * 
	 */
	public AnnotationCollection createAnnotationCollection() {
		return new AnnotationCollection();
	}

	/**
	 * Create an instance of {@link Term }
	 * 
	 */
	public Term createTerm() {
		return new Term();
	}

	/**
	 * Create an instance of {@link Link }
	 * 
	 */
	public Link createLink() {
		return new Link();
	}

	/**
	 * Create an instance of {@link Disorder }
	 * 
	 */
	public Disorder createDisorder() {
		return new Disorder();
	}

	/**
	 * Create an instance of {@link AssociatedDisorders }
	 * 
	 */
	public AssociatedDisorders createAssociatedDisorders() {
		return new AssociatedDisorders();
	}

	/**
	 * Create an instance of {@link QualifierList }
	 * 
	 */
	public QualifierList createQualifierList() {
		return new QualifierList();
	}

	/**
	 * Create an instance of {@link FullTextList }
	 * 
	 */
	public FullTextList createFullTextList() {
		return new FullTextList();
	}

}
