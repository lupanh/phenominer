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
 *         &lt;element ref="{}subj-group" maxOccurs="unbounded" minOccurs="0"/>
 *         &lt;element ref="{}series-title" maxOccurs="unbounded" minOccurs="0"/>
 *         &lt;element ref="{}series-text" minOccurs="0"/>
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
    "subjGroup",
    "seriesTitle",
    "seriesText"
})
@XmlRootElement(name = "article-categories")
public class ArticleCategories {

    @XmlElement(name = "subj-group")
    protected List<SubjGroup> subjGroup;
    @XmlElement(name = "series-title")
    protected List<SeriesTitle> seriesTitle;
    @XmlElement(name = "series-text")
    protected SeriesText seriesText;

    /**
     * Gets the value of the subjGroup property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the subjGroup property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getSubjGroup().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link SubjGroup }
     * 
     * 
     */
    public List<SubjGroup> getSubjGroup() {
        if (subjGroup == null) {
            subjGroup = new ArrayList<SubjGroup>();
        }
        return this.subjGroup;
    }

    /**
     * Gets the value of the seriesTitle property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the seriesTitle property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getSeriesTitle().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link SeriesTitle }
     * 
     * 
     */
    public List<SeriesTitle> getSeriesTitle() {
        if (seriesTitle == null) {
            seriesTitle = new ArrayList<SeriesTitle>();
        }
        return this.seriesTitle;
    }

    /**
     * Gets the value of the seriesText property.
     * 
     * @return
     *     possible object is
     *     {@link SeriesText }
     *     
     */
    public SeriesText getSeriesText() {
        return seriesText;
    }

    /**
     * Sets the value of the seriesText property.
     * 
     * @param value
     *     allowed object is
     *     {@link SeriesText }
     *     
     */
    public void setSeriesText(SeriesText value) {
        this.seriesText = value;
    }

}
