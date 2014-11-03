package org.nii.phenominer.searchdb.bean.phenominer;

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
@XmlType(name = "", propOrder = { "term" })
@XmlRootElement(name = "annotationCollection")
public class AnnotationCollection {
	@XmlAttribute(name = "ID", required = true)
	@XmlJavaTypeAdapter(NormalizedStringAdapter.class)
	protected String id;

	@XmlAttribute(name = "ASSIGNED_BY", required = true)
	@XmlJavaTypeAdapter(NormalizedStringAdapter.class)
	protected String assignby;

	@XmlElement(name = "Term")
	protected List<Term> term;

	/**
	 * Gets the value of the term property.
	 * 
	 * <p>
	 * This accessor method returns a reference to the live list, not a
	 * snapshot. Therefore any modification you make to the returned list will
	 * be present inside the JAXB object. This is why there is not a
	 * <CODE>set</CODE> method for the term property.
	 * 
	 * <p>
	 * For example, to add a new item, do as follows:
	 * 
	 * <pre>
	 * getTerm().add(newItem);
	 * </pre>
	 * 
	 * 
	 * <p>
	 * Objects of the following type(s) are allowed in the list {@link Term }
	 * 
	 * 
	 */
	public List<Term> getTerm() {
		if (term == null) {
			term = new ArrayList<Term>();
		}
		return this.term;
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
	 * Gets the value of the assignby property.
	 * 
	 * @return possible object is {@link String }
	 * 
	 */
	public String getAssignBy() {
		return assignby;
	}

	/**
	 * Sets the value of the assignby property.
	 * 
	 * @param value
	 *            allowed object is {@link String }
	 * 
	 */
	public void setAssignBy(String value) {
		this.assignby = value;
	}

}
