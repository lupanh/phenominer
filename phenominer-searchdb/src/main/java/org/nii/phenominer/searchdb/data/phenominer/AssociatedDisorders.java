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
@XmlType(name = "", propOrder = { "disorder" })
@XmlRootElement(name = "associatedDisorders")
public class AssociatedDisorders {

	@XmlAttribute(name = "source", required = true)
	@XmlJavaTypeAdapter(NormalizedStringAdapter.class)
	protected String source;
	@XmlAttribute(name = "min_supp", required = true)
	@XmlJavaTypeAdapter(NormalizedStringAdapter.class)
	protected String minSupp;
	@XmlAttribute(name = "min_conf", required = true)
	@XmlJavaTypeAdapter(NormalizedStringAdapter.class)
	protected String minConf;
	@XmlAttribute(name = "df", required = true)
	@XmlJavaTypeAdapter(NormalizedStringAdapter.class)
	protected String df;
	@XmlElement(required = true)
	protected List<Disorder> disorder;

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
	 * Gets the value of the minSupp property.
	 * 
	 * @return possible object is {@link String }
	 * 
	 */
	public String getMinSupp() {
		return minSupp;
	}

	/**
	 * Sets the value of the minSupp property.
	 * 
	 * @param value
	 *            allowed object is {@link String }
	 * 
	 */
	public void setMinSupp(String value) {
		this.minSupp = value;
	}

	/**
	 * Gets the value of the minConf property.
	 * 
	 * @return possible object is {@link String }
	 * 
	 */
	public String getMinConf() {
		return minConf;
	}

	/**
	 * Sets the value of the minConf property.
	 * 
	 * @param value
	 *            allowed object is {@link String }
	 * 
	 */
	public void setMinConf(String value) {
		this.minConf = value;
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
	 * Gets the value of the disorder property.
	 * 
	 * <p>
	 * This accessor method returns a reference to the live list, not a
	 * snapshot. Therefore any modification you make to the returned list will
	 * be present inside the JAXB object. This is why there is not a
	 * <CODE>set</CODE> method for the disorder property.
	 * 
	 * <p>
	 * For example, to add a new item, do as follows:
	 * 
	 * <pre>
	 * getDisorder().add(newItem);
	 * </pre>
	 * 
	 * 
	 * <p>
	 * Objects of the following type(s) are allowed in the list {@link Disorder }
	 * 
	 * 
	 */
	public List<Disorder> getDisorder() {
		if (disorder == null) {
			disorder = new ArrayList<Disorder>();
		}
		return this.disorder;
	}
	
	public void setDisorder(List<Disorder> disorder) {
		this.disorder = disorder;
	}

}
