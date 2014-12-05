//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v2.2.4 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2011.12.14 at 03:30:44 PM CET 
//


package org.nii.phenominer.processing.data.pubmeddtd;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for anonymous complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType>
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element ref="{}award-group" maxOccurs="unbounded" minOccurs="0"/>
 *         &lt;element ref="{}funding-statement" maxOccurs="unbounded" minOccurs="0"/>
 *         &lt;element ref="{}open-access" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = {
    "awardGroup",
    "fundingStatement",
    "openAccess"
})
@XmlRootElement(name = "funding-group")
public class FundingGroup {

    @XmlElement(name = "award-group")
    protected List<AwardGroup> awardGroup;
    @XmlElement(name = "funding-statement")
    protected List<FundingStatement> fundingStatement;
    @XmlElement(name = "open-access")
    protected OpenAccess openAccess;

    /**
     * Gets the value of the awardGroup property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the awardGroup property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getAwardGroup().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link AwardGroup }
     * 
     * 
     */
    public List<AwardGroup> getAwardGroup() {
        if (awardGroup == null) {
            awardGroup = new ArrayList<AwardGroup>();
        }
        return this.awardGroup;
    }

    /**
     * Gets the value of the fundingStatement property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the fundingStatement property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getFundingStatement().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link FundingStatement }
     * 
     * 
     */
    public List<FundingStatement> getFundingStatement() {
        if (fundingStatement == null) {
            fundingStatement = new ArrayList<FundingStatement>();
        }
        return this.fundingStatement;
    }

    /**
     * Gets the value of the openAccess property.
     * 
     * @return
     *     possible object is
     *     {@link OpenAccess }
     *     
     */
    public OpenAccess getOpenAccess() {
        return openAccess;
    }

    /**
     * Sets the value of the openAccess property.
     * 
     * @param value
     *     allowed object is
     *     {@link OpenAccess }
     *     
     */
    public void setOpenAccess(OpenAccess value) {
        this.openAccess = value;
    }

}