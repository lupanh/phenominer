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
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElements;
import javax.xml.bind.annotation.XmlID;
import javax.xml.bind.annotation.XmlIDREF;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;
import javax.xml.bind.annotation.adapters.CollapsedStringAdapter;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;


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
 *         &lt;element ref="{}label" minOccurs="0"/>
 *         &lt;element ref="{}title" minOccurs="0"/>
 *         &lt;element ref="{}term-head" minOccurs="0"/>
 *         &lt;element ref="{}def-head" minOccurs="0"/>
 *         &lt;choice maxOccurs="unbounded" minOccurs="0">
 *           &lt;element ref="{}def-item"/>
 *           &lt;element ref="{}x"/>
 *         &lt;/choice>
 *         &lt;element ref="{}def-list" maxOccurs="unbounded" minOccurs="0"/>
 *       &lt;/sequence>
 *       &lt;attribute name="id" type="{http://www.w3.org/2001/XMLSchema}ID" />
 *       &lt;attribute name="list-type" type="{http://www.w3.org/2001/XMLSchema}anySimpleType" />
 *       &lt;attribute name="prefix-word" type="{http://www.w3.org/2001/XMLSchema}anySimpleType" />
 *       &lt;attribute name="list-content" type="{http://www.w3.org/2001/XMLSchema}anySimpleType" />
 *       &lt;attribute name="specific-use" type="{http://www.w3.org/2001/XMLSchema}anySimpleType" />
 *       &lt;attribute name="continued-from" type="{http://www.w3.org/2001/XMLSchema}IDREF" />
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = {
    "label",
    "title",
    "termHead",
    "defHead",
    "defItemOrX",
    "defList"
})
@XmlRootElement(name = "def-list")
public class DefList {

    protected Label label;
    protected Title title;
    @XmlElement(name = "term-head")
    protected TermHead termHead;
    @XmlElement(name = "def-head")
    protected DefHead defHead;
    @XmlElements({
        @XmlElement(name = "def-item", type = DefItem.class),
        @XmlElement(name = "x", type = X.class)
    })
    protected List<Object> defItemOrX;
    @XmlElement(name = "def-list")
    protected List<DefList> defList;
    @XmlAttribute(name = "id")
    @XmlJavaTypeAdapter(CollapsedStringAdapter.class)
    @XmlID
    @XmlSchemaType(name = "ID")
    protected String id;
    @XmlAttribute(name = "list-type")
    @XmlSchemaType(name = "anySimpleType")
    protected String listType;
    @XmlAttribute(name = "prefix-word")
    @XmlSchemaType(name = "anySimpleType")
    protected String prefixWord;
    @XmlAttribute(name = "list-content")
    @XmlSchemaType(name = "anySimpleType")
    protected String listContent;
    @XmlAttribute(name = "specific-use")
    @XmlSchemaType(name = "anySimpleType")
    protected String specificUse;
    @XmlAttribute(name = "continued-from")
    @XmlIDREF
    @XmlSchemaType(name = "IDREF")
    protected Object continuedFrom;

    /**
     * Gets the value of the label property.
     * 
     * @return
     *     possible object is
     *     {@link Label }
     *     
     */
    public Label getLabel() {
        return label;
    }

    /**
     * Sets the value of the label property.
     * 
     * @param value
     *     allowed object is
     *     {@link Label }
     *     
     */
    public void setLabel(Label value) {
        this.label = value;
    }

    /**
     * Gets the value of the title property.
     * 
     * @return
     *     possible object is
     *     {@link Title }
     *     
     */
    public Title getTitle() {
        return title;
    }

    /**
     * Sets the value of the title property.
     * 
     * @param value
     *     allowed object is
     *     {@link Title }
     *     
     */
    public void setTitle(Title value) {
        this.title = value;
    }

    /**
     * Gets the value of the termHead property.
     * 
     * @return
     *     possible object is
     *     {@link TermHead }
     *     
     */
    public TermHead getTermHead() {
        return termHead;
    }

    /**
     * Sets the value of the termHead property.
     * 
     * @param value
     *     allowed object is
     *     {@link TermHead }
     *     
     */
    public void setTermHead(TermHead value) {
        this.termHead = value;
    }

    /**
     * Gets the value of the defHead property.
     * 
     * @return
     *     possible object is
     *     {@link DefHead }
     *     
     */
    public DefHead getDefHead() {
        return defHead;
    }

    /**
     * Sets the value of the defHead property.
     * 
     * @param value
     *     allowed object is
     *     {@link DefHead }
     *     
     */
    public void setDefHead(DefHead value) {
        this.defHead = value;
    }

    /**
     * Gets the value of the defItemOrX property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the defItemOrX property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getDefItemOrX().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link DefItem }
     * {@link X }
     * 
     * 
     */
    public List<Object> getDefItemOrX() {
        if (defItemOrX == null) {
            defItemOrX = new ArrayList<Object>();
        }
        return this.defItemOrX;
    }

    /**
     * Gets the value of the defList property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the defList property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getDefList().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link DefList }
     * 
     * 
     */
    public List<DefList> getDefList() {
        if (defList == null) {
            defList = new ArrayList<DefList>();
        }
        return this.defList;
    }

    /**
     * Gets the value of the id property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getId() {
        return id;
    }

    /**
     * Sets the value of the id property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setId(String value) {
        this.id = value;
    }

    /**
     * Gets the value of the listType property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getListType() {
        return listType;
    }

    /**
     * Sets the value of the listType property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setListType(String value) {
        this.listType = value;
    }

    /**
     * Gets the value of the prefixWord property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getPrefixWord() {
        return prefixWord;
    }

    /**
     * Sets the value of the prefixWord property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setPrefixWord(String value) {
        this.prefixWord = value;
    }

    /**
     * Gets the value of the listContent property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getListContent() {
        return listContent;
    }

    /**
     * Sets the value of the listContent property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setListContent(String value) {
        this.listContent = value;
    }

    /**
     * Gets the value of the specificUse property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getSpecificUse() {
        return specificUse;
    }

    /**
     * Sets the value of the specificUse property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setSpecificUse(String value) {
        this.specificUse = value;
    }

    /**
     * Gets the value of the continuedFrom property.
     * 
     * @return
     *     possible object is
     *     {@link Object }
     *     
     */
    public Object getContinuedFrom() {
        return continuedFrom;
    }

    /**
     * Sets the value of the continuedFrom property.
     * 
     * @param value
     *     allowed object is
     *     {@link Object }
     *     
     */
    public void setContinuedFrom(Object value) {
        this.continuedFrom = value;
    }

}
