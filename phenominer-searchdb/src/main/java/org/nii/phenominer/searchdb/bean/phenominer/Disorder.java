package org.nii.phenominer.searchdb.bean.phenominer;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;
import javax.xml.bind.annotation.adapters.NormalizedStringAdapter;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

/**
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = { "name", "omimId" })
@XmlRootElement(name = "disorder")
public class Disorder {

	@XmlAttribute(name = "supp", required = true)
	@XmlJavaTypeAdapter(NormalizedStringAdapter.class)
	protected String supp;
	@XmlAttribute(name = "conf", required = true)
	@XmlJavaTypeAdapter(NormalizedStringAdapter.class)
	protected String conf;
	@XmlAttribute(name = "lift", required = true)
	@XmlJavaTypeAdapter(NormalizedStringAdapter.class)
	protected String lift;
	@XmlAttribute(name = "pval", required = true)
	@XmlJavaTypeAdapter(NormalizedStringAdapter.class)
	protected String pval;
	@XmlElement(required = true)
	protected String name;
	@XmlElement(name = "omim_id", required = true)
	protected String omimId;

	/**
	 * Gets the value of the supp property.
	 * 
	 * @return possible object is {@link String }
	 * 
	 */
	public String getSupp() {
		return supp;
	}

	/**
	 * Sets the value of the supp property.
	 * 
	 * @param value
	 *            allowed object is {@link String }
	 * 
	 */
	public void setSupp(String value) {
		this.supp = value;
	}

	/**
	 * Gets the value of the conf property.
	 * 
	 * @return possible object is {@link String }
	 * 
	 */
	public String getConf() {
		return conf;
	}

	/**
	 * Sets the value of the conf property.
	 * 
	 * @param value
	 *            allowed object is {@link String }
	 * 
	 */
	public void setConf(String value) {
		this.conf = value;
	}

	/**
	 * Gets the value of the lift property.
	 * 
	 * @return possible object is {@link String }
	 * 
	 */
	public String getLift() {
		return lift;
	}

	/**
	 * Sets the value of the lift property.
	 * 
	 * @param value
	 *            allowed object is {@link String }
	 * 
	 */
	public void setLift(String value) {
		this.lift = value;
	}

	/**
	 * Gets the value of the pval property.
	 * 
	 * @return possible object is {@link String }
	 * 
	 */
	public String getPval() {
		return pval;
	}

	/**
	 * Sets the value of the pval property.
	 * 
	 * @param value
	 *            allowed object is {@link String }
	 * 
	 */
	public void setPval(String value) {
		this.pval = value;
	}

	/**
	 * Gets the value of the name property.
	 * 
	 * @return possible object is {@link String }
	 * 
	 */
	public String getName() {
		return name;
	}

	/**
	 * Sets the value of the name property.
	 * 
	 * @param value
	 *            allowed object is {@link String }
	 * 
	 */
	public void setName(String value) {
		this.name = value;
	}

	/**
	 * Gets the value of the omimId property.
	 * 
	 * @return possible object is {@link String }
	 * 
	 */
	public String getOmimId() {
		return omimId;
	}

	/**
	 * Sets the value of the omimId property.
	 * 
	 * @param value
	 *            allowed object is {@link String }
	 * 
	 */
	public void setOmimId(String value) {
		this.omimId = value;
	}

}
