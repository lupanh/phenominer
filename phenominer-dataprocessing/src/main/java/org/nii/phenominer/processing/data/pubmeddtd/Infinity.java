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
 * <p>Java class for infinity.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * <p>
 * <pre>
 * &lt;simpleType name="infinity">
 *   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *     &lt;enumeration value="infinity"/>
 *   &lt;/restriction>
 * &lt;/simpleType>
 * </pre>
 * 
 */
@XmlType(name = "infinity", namespace = "http://www.w3.org/1998/Math/MathML")
@XmlEnum
public enum Infinity {

    @XmlEnumValue("infinity")
    INFINITY("infinity");
    private final String value;

    Infinity(String v) {
        value = v;
    }

    public String value() {
        return value;
    }

    public static Infinity fromValue(String v) {
        for (Infinity c: Infinity.values()) {
            if (c.value.equals(v)) {
                return c;
            }
        }
        throw new IllegalArgumentException(v);
    }

}
