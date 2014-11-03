package org.nii.phenominer.searchdb.bean.phenominer;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;
import javax.xml.bind.annotation.adapters.NormalizedStringAdapter;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "")
@XmlRootElement(name = "Link")
public class Link {

	@XmlAttribute(name = "text", required = true)
	@XmlJavaTypeAdapter(NormalizedStringAdapter.class)
	protected String text;
	@XmlAttribute(name = "ontology", required = true)
	@XmlJavaTypeAdapter(NormalizedStringAdapter.class)
	protected String ontology;
	@XmlAttribute(name = "ID", required = true)
	@XmlJavaTypeAdapter(NormalizedStringAdapter.class)
	protected String id;
	@XmlAttribute(name = "evidence", required = true)
	@XmlJavaTypeAdapter(NormalizedStringAdapter.class)
	protected String evidence;

	/**
	 * Gets the value of the text property.
	 * 
	 * @return possible object is {@link String }
	 * 
	 */
	public String getText() {
		return text;
	}

	/**
	 * Sets the value of the text property.
	 * 
	 * @param value
	 *            allowed object is {@link String }
	 * 
	 */
	public void setText(String value) {
		this.text = value;
	}

	/**
	 * Gets the value of the ontology property.
	 * 
	 * @return possible object is {@link String }
	 * 
	 */
	public String getOntology() {
		return ontology;
	}

	/**
	 * Sets the value of the ontology property.
	 * 
	 * @param value
	 *            allowed object is {@link String }
	 * 
	 */
	public void setOntology(String value) {
		this.ontology = value;
	}

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
	 * Gets the value of the evidence property.
	 * 
	 * @return possible object is {@link String }
	 * 
	 */
	public String getEvidence() {
		return evidence;
	}

	/**
	 * Sets the value of the evidence property.
	 * 
	 * @param value
	 *            allowed object is {@link String }
	 * 
	 */
	public void setEvidence(String value) {
		this.evidence = value;
	}

}
