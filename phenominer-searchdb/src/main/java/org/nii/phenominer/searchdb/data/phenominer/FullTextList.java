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
@XmlType(name = "", propOrder = { "id" })
@XmlRootElement(name = "fullTextList")
public class FullTextList {

	@XmlAttribute(name = "source", required = true)
	@XmlJavaTypeAdapter(NormalizedStringAdapter.class)
	protected String source;
	@XmlAttribute(name = "df", required = true)
	@XmlJavaTypeAdapter(NormalizedStringAdapter.class)
	protected String df;
	@XmlAttribute(name = "retmax", required = true)
	@XmlJavaTypeAdapter(NormalizedStringAdapter.class)
	protected String retmax;
	@XmlElement(name = "Id")
	protected List<Id> id;

	/**
	 * Gets the value of the source property.
	 * 
	 * @return possible object is {@link String }
	 * 
	 */
	public String getSource() {
		return source;
	}

	/**
	 * Sets the value of the source property.
	 * 
	 * @param value
	 *            allowed object is {@link String }
	 * 
	 */
	public void setSource(String value) {
		this.source = value;
	}

	/**
	 * Gets the value of the df property.
	 * 
	 * @return possible object is {@link String }
	 * 
	 */
	public String getDf() {
		return df;
	}

	/**
	 * Sets the value of the df property.
	 * 
	 * @param value
	 *            allowed object is {@link String }
	 * 
	 */
	public void setDf(String value) {
		this.df = value;
	}

	/**
	 * Gets the value of the retmax property.
	 * 
	 * @return possible object is {@link String }
	 * 
	 */
	public String getRetmax() {
		return retmax;
	}

	/**
	 * Sets the value of the retmax property.
	 * 
	 * @param value
	 *            allowed object is {@link String }
	 * 
	 */
	public void setRetmax(String value) {
		this.retmax = value;
	}

	/**
	 * Gets the value of the id property.
	 * 
	 * <p>
	 * This accessor method returns a reference to the live list, not a
	 * snapshot. Therefore any modification you make to the returned list will
	 * be present inside the JAXB object. This is why there is not a
	 * <CODE>set</CODE> method for the id property.
	 * 
	 * <p>
	 * For example, to add a new item, do as follows:
	 * 
	 * <pre>
	 * getId().add(newItem);
	 * </pre>
	 * 
	 * 
	 * <p>
	 * Objects of the following type(s) are allowed in the list {@link Id }
	 * 
	 * 
	 */
	public List<Id> getId() {
		if (id == null) {
			id = new ArrayList<Id>();
		}
		return this.id;
	}

}
