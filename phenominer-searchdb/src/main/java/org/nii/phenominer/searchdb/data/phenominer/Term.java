package org.nii.phenominer.searchdb.data.phenominer;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;
import javax.xml.bind.annotation.adapters.NormalizedStringAdapter;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = { "qualifierList", "link", "tree", "associatedDisorders",
		"fullTextList", "abstractList" })
@XmlRootElement(name = "Term")
public class Term {

	@XmlAttribute(name = "ID", required = true)
	@XmlJavaTypeAdapter(NormalizedStringAdapter.class)
	protected String id;
	@XmlAttribute(name = "KEY", required = true)
	@XmlJavaTypeAdapter(NormalizedStringAdapter.class)
	protected String key;
	@XmlAttribute(name = "EVIDENCE", required = true)
	@XmlJavaTypeAdapter(NormalizedStringAdapter.class)
	protected String evidence;
	@XmlAttribute(name = "DATE", required = true)
	@XmlJavaTypeAdapter(NormalizedStringAdapter.class)
	protected String date;
	@XmlElement(required = true)
	protected QualifierList qualifierList;
	@XmlElement(name = "Link")
	protected List<Link> link;
	@XmlElement(name = "Tree", required = true)
	protected String tree;
	protected AssociatedDisorders associatedDisorders;
	@XmlElement(required = true)
	protected FullTextList fullTextList;
	@XmlElement(required = true)
	protected AbstractList abstractList;

	/**
	 * Gets the value of the id property.
	 * 
	 * @return possible object is {@link String }
	 * 
	 */
	public String getID() {
		return id;
	}

	/**
	 * Sets the value of the id property.
	 * 
	 * @param value
	 *            allowed object is {@link String }
	 * 
	 */
	public void setID(String value) {
		this.id = value;
	}

	/**
	 * Gets the value of the key property.
	 * 
	 * @return possible object is {@link String }
	 * 
	 */
	public String getKEY() {
		return key;
	}

	/**
	 * Sets the value of the key property.
	 * 
	 * @param value
	 *            allowed object is {@link String }
	 * 
	 */
	public void setKEY(String value) {
		this.key = value;
	}

	/**
	 * Gets the value of the evidence property.
	 * 
	 * @return possible object is {@link String }
	 * 
	 */
	public String getEVIDENCE() {
		return evidence;
	}

	/**
	 * Sets the value of the evidence property.
	 * 
	 * @param value
	 *            allowed object is {@link String }
	 * 
	 */
	public void setEVIDENCE(String value) {
		this.evidence = value;
	}

	/**
	 * Gets the value of the date property.
	 * 
	 * @return possible object is {@link String }
	 * 
	 */
	public String getDATE() {
		return date;
	}

	/**
	 * Sets the value of the date property.
	 * 
	 * @param value
	 *            allowed object is {@link String }
	 * 
	 */
	public void setDATE(String value) {
		this.date = value;
	}

	/**
	 * Gets the value of the qualifierList property.
	 * 
	 * @return possible object is {@link QualifierList }
	 * 
	 */
	public QualifierList getQualifierList() {
		return qualifierList;
	}

	/**
	 * Sets the value of the qualifierList property.
	 * 
	 * @param value
	 *            allowed object is {@link QualifierList }
	 * 
	 */
	public void setQualifierList(QualifierList value) {
		this.qualifierList = value;
	}

	/**
	 * Gets the value of the link property.
	 * 
	 * <p>
	 * This accessor method returns a reference to the live list, not a
	 * snapshot. Therefore any modification you make to the returned list will
	 * be present inside the JAXB object. This is why there is not a
	 * <CODE>set</CODE> method for the link property.
	 * 
	 * <p>
	 * For example, to add a new item, do as follows:
	 * 
	 * <pre>
	 * getLink().add(newItem);
	 * </pre>
	 * 
	 * 
	 * <p>
	 * Objects of the following type(s) are allowed in the list {@link Link }
	 * 
	 * 
	 */
	public List<Link> getLink() {
		if (link == null) {
			link = new ArrayList<Link>();
		}
		return this.link;
	}

	/**
	 * Gets the value of the tree property.
	 * 
	 * @return possible object is {@link String }
	 * 
	 */
	public String getTree() {
		return tree;
	}

	/**
	 * Sets the value of the tree property.
	 * 
	 * @param value
	 *            allowed object is {@link String }
	 * 
	 */
	public void setTree(String value) {
		this.tree = value;
	}

	/**
	 * Gets the value of the associatedDisorders property.
	 * 
	 * @return possible object is {@link AssociatedDisorders }
	 * 
	 */
	public AssociatedDisorders getAssociatedDisorders() {
		return associatedDisorders;
	}

	/**
	 * Sets the value of the associatedDisorders property.
	 * 
	 * @param value
	 *            allowed object is {@link AssociatedDisorders }
	 * 
	 */
	public void setAssociatedDisorders(AssociatedDisorders value) {
		this.associatedDisorders = value;
	}

	/**
	 * Gets the value of the fullTextList property.
	 * 
	 * @return possible object is {@link FullTextList }
	 * 
	 */
	public FullTextList getFullTextList() {
		return fullTextList;
	}

	/**
	 * Sets the value of the fullTextList property.
	 * 
	 * @param value
	 *            allowed object is {@link FullTextList }
	 * 
	 */
	public void setFullTextList(FullTextList value) {
		this.fullTextList = value;
	}

	/**
	 * Gets the value of the abstractList property.
	 * 
	 * @return possible object is {@link AbstractList }
	 * 
	 */
	public AbstractList getAbstractList() {
		return abstractList;
	}

	/**
	 * Sets the value of the abstractList property.
	 * 
	 * @param value
	 *            allowed object is {@link AbstractList }
	 * 
	 */
	public void setAbstractList(AbstractList value) {
		this.abstractList = value;
	}

}
