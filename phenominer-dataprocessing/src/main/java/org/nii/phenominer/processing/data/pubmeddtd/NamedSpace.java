//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v2.2.4 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2011.12.14 at 03:30:44 PM CET 
//


package org.nii.phenominer.processing.data.pubmeddtd;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlEnumValue;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for named-space.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * <p>
 * <pre>
 * &lt;simpleType name="named-space">
 *   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *     &lt;enumeration value="veryverythinmathspace"/>
 *     &lt;enumeration value="verythinmathspace"/>
 *     &lt;enumeration value="thinmathspace"/>
 *     &lt;enumeration value="mediummathspace"/>
 *     &lt;enumeration value="thickmathspace"/>
 *     &lt;enumeration value="verythickmathspace"/>
 *     &lt;enumeration value="veryverythickmathspace"/>
 *   &lt;/restriction>
 * &lt;/simpleType>
 * </pre>
 * 
 */
@XmlType(name = "named-space", namespace = "http://www.w3.org/1998/Math/MathML")
@XmlEnum
public enum NamedSpace {

    @XmlEnumValue("veryverythinmathspace")
    VERYVERYTHINMATHSPACE("veryverythinmathspace"),
    @XmlEnumValue("verythinmathspace")
    VERYTHINMATHSPACE("verythinmathspace"),
    @XmlEnumValue("thinmathspace")
    THINMATHSPACE("thinmathspace"),
    @XmlEnumValue("mediummathspace")
    MEDIUMMATHSPACE("mediummathspace"),
    @XmlEnumValue("thickmathspace")
    THICKMATHSPACE("thickmathspace"),
    @XmlEnumValue("verythickmathspace")
    VERYTHICKMATHSPACE("verythickmathspace"),
    @XmlEnumValue("veryverythickmathspace")
    VERYVERYTHICKMATHSPACE("veryverythickmathspace");
    private final String value;

    NamedSpace(String v) {
        value = v;
    }

    public String value() {
        return value;
    }

    public static NamedSpace fromValue(String v) {
        for (NamedSpace c: NamedSpace.values()) {
            if (c.value.equals(v)) {
                return c;
            }
        }
        throw new IllegalArgumentException(v);
    }

}
