package com.ncr.tpvbuddy.util;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import org.joda.time.DateTime;
import org.opensaml.Configuration;
import org.opensaml.DefaultBootstrap;
import org.opensaml.common.SAMLObjectBuilder;
import org.opensaml.common.SAMLVersion;
import org.opensaml.saml2.core.*;
import org.opensaml.saml2.core.impl.AssertionMarshaller;
import org.opensaml.xml.ConfigurationException;
import org.opensaml.xml.XMLObjectBuilder;
import org.opensaml.xml.XMLObjectBuilderFactory;
import org.opensaml.xml.io.MarshallingException;
import org.opensaml.xml.schema.XSString;
import org.opensaml.xml.signature.Signature;
import org.opensaml.xml.util.XMLHelper;
import org.w3c.dom.Element;


public class SAMLWriter{
    public static void main(String[] args) {
        try {
            SAMLInputContainer input = new SAMLInputContainer();
            input.strIssuer = "http://synesty.com";
            input.strNameID = "UserJohnSmith";
            input.strNameQualifier = "My Website";
            input.sessionId = "abcdedf1234567";

            Map<String,String> customAttributes = new HashMap<String, String>();
            customAttributes.put("FirstName", "John");
            customAttributes.put("LastName", "Smith");
            customAttributes.put("Email", "john.smith@yahoo.com");
            customAttributes.put("PhoneNumber", "76373898998");
            customAttributes.put("Locality", "USA");
            customAttributes.put("Username", "John.Smith");

            input.attributes = customAttributes;

            Assertion assertion = SAMLWriter.buildDefaultAssertion(input);
            AssertionMarshaller marshaller = new AssertionMarshaller();
            Element plaintextElement = marshaller.marshall(assertion);
            String originalAssertionString = XMLHelper.nodeToString(plaintextElement);

            System.out.println("Assertion String: " + originalAssertionString);

            // TODO: now you can also add encryption....

        } catch (MarshallingException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

    }

    private static XMLObjectBuilderFactory builderFactory;

    public static XMLObjectBuilderFactory getSAMLBuilder() throws ConfigurationException{

        if(builderFactory == null){
            // OpenSAML 2.3
            DefaultBootstrap.bootstrap();
            builderFactory = Configuration.getBuilderFactory();
        }

        return builderFactory;
    }

    @SuppressWarnings("rawtypes")
    public static Attribute buildStringAttribute(String name, String value, XMLObjectBuilderFactory builderFactory) throws ConfigurationException{
        SAMLObjectBuilder attrBuilder = (SAMLObjectBuilder) getSAMLBuilder().getBuilder(Attribute.DEFAULT_ELEMENT_NAME);
        Attribute attrFirstName = (Attribute) attrBuilder.buildObject();
        attrFirstName.setName(name);

        // Set custom Attributes
        XMLObjectBuilder stringBuilder = getSAMLBuilder().getBuilder(XSString.TYPE_NAME);
        XSString attrValueFirstName = (XSString) stringBuilder.buildObject(AttributeValue.DEFAULT_ELEMENT_NAME, XSString.TYPE_NAME);
        attrValueFirstName.setValue(value);

        attrFirstName.getAttributeValues().add(attrValueFirstName);
        return attrFirstName;
    }

    /**
     * Helper method which includes some basic SAML fields which are part of almost every SAML Assertion.
     */
    @SuppressWarnings("rawtypes")
    public static Assertion buildDefaultAssertion(SAMLInputContainer input){
        try {
            // Create the NameIdentifier
            SAMLObjectBuilder nameIdBuilder = (SAMLObjectBuilder) SAMLWriter.getSAMLBuilder().getBuilder(NameID.DEFAULT_ELEMENT_NAME);
            NameID nameId = (NameID) nameIdBuilder.buildObject();
            nameId.setValue(input.getStrNameID());
            nameId.setNameQualifier(input.getStrNameQualifier());
            nameId.setFormat(NameID.UNSPECIFIED);

            // Create the SubjectConfirmation

            SAMLObjectBuilder confirmationMethodBuilder = (SAMLObjectBuilder)  SAMLWriter.getSAMLBuilder().getBuilder(SubjectConfirmationData.DEFAULT_ELEMENT_NAME);
            SubjectConfirmationData confirmationMethod = (SubjectConfirmationData) confirmationMethodBuilder.buildObject();
            DateTime now = new DateTime();
            confirmationMethod.setNotBefore(now);
            confirmationMethod.setNotOnOrAfter(now.plusMinutes(2));

            SAMLObjectBuilder subjectConfirmationBuilder = (SAMLObjectBuilder) SAMLWriter.getSAMLBuilder().getBuilder(SubjectConfirmation.DEFAULT_ELEMENT_NAME);
            SubjectConfirmation subjectConfirmation = (SubjectConfirmation) subjectConfirmationBuilder.buildObject();
            subjectConfirmation.setSubjectConfirmationData(confirmationMethod);

            // Create the Subject
            SAMLObjectBuilder subjectBuilder = (SAMLObjectBuilder) SAMLWriter.getSAMLBuilder().getBuilder(Subject.DEFAULT_ELEMENT_NAME);
            Subject subject = (Subject) subjectBuilder.buildObject();

            subject.setNameID(nameId);
            subject.getSubjectConfirmations().add(subjectConfirmation);

            // Testing starts
            SAMLObjectBuilder signatureBuilder = (SAMLObjectBuilder) SAMLWriter.getSAMLBuilder().getBuilder(Signature.DEFAULT_ELEMENT_NAME);
            Signature signature = (Signature) signatureBuilder.buildObject();

            SAMLObjectBuilder eaB = (SAMLObjectBuilder) SAMLWriter.getSAMLBuilder().getBuilder(EncryptedAssertion.DEFAULT_ELEMENT_NAME);
            EncryptedAssertion ea = (EncryptedAssertion) eaB.buildObject();

            // Testing ends

            // Create Authentication Statement
            SAMLObjectBuilder authStatementBuilder = (SAMLObjectBuilder) SAMLWriter.getSAMLBuilder().getBuilder(AuthnStatement.DEFAULT_ELEMENT_NAME);
            AuthnStatement authnStatement = (AuthnStatement) authStatementBuilder.buildObject();
            //authnStatement.setSubject(subject);
            //authnStatement.setAuthenticationMethod(strAuthMethod);
            DateTime now2 = new DateTime();
            authnStatement.setAuthnInstant(now2);
            authnStatement.setSessionIndex(input.getSessionId());
            authnStatement.setSessionNotOnOrAfter(now2.plus(input.getMaxSessionTimeoutInMinutes()));

            SAMLObjectBuilder authContextBuilder = (SAMLObjectBuilder) SAMLWriter.getSAMLBuilder().getBuilder(AuthnContext.DEFAULT_ELEMENT_NAME);
            AuthnContext authnContext = (AuthnContext) authContextBuilder.buildObject();

            SAMLObjectBuilder authContextClassRefBuilder = (SAMLObjectBuilder) SAMLWriter.getSAMLBuilder().getBuilder(AuthnContextClassRef.DEFAULT_ELEMENT_NAME);
            AuthnContextClassRef authnContextClassRef = (AuthnContextClassRef) authContextClassRefBuilder.buildObject();
            authnContextClassRef.setAuthnContextClassRef("urn:oasis:names:tc:SAML:2.0:ac:classes:Password"); // TODO not sure exactly about this

            authnContext.setAuthnContextClassRef(authnContextClassRef);
            authnStatement.setAuthnContext(authnContext);

            // Builder Attributes
            SAMLObjectBuilder attrStatementBuilder = (SAMLObjectBuilder) SAMLWriter.getSAMLBuilder().getBuilder(AttributeStatement.DEFAULT_ELEMENT_NAME);
            AttributeStatement attrStatement = (AttributeStatement) attrStatementBuilder.buildObject();

            // Create the attribute statement
            Map attributes = input.getAttributes();
            if(attributes != null){
                Iterator keySet = attributes.keySet().iterator();
                while (keySet.hasNext()){
                    String key = keySet.next().toString();
                    String val = attributes.get(key).toString();
                    Attribute attrFirstName = buildStringAttribute(key, val, getSAMLBuilder());
                    attrStatement.getAttributes().add(attrFirstName);
                }
            }

            // Create the do-not-cache condition
            SAMLObjectBuilder doNotCacheConditionBuilder = (SAMLObjectBuilder) SAMLWriter.getSAMLBuilder().getBuilder(OneTimeUse.DEFAULT_ELEMENT_NAME);
            Condition condition = (Condition) doNotCacheConditionBuilder.buildObject();

            SAMLObjectBuilder conditionsBuilder = (SAMLObjectBuilder) SAMLWriter.getSAMLBuilder().getBuilder(Conditions.DEFAULT_ELEMENT_NAME);
            Conditions conditions = (Conditions) conditionsBuilder.buildObject();
            conditions.getConditions().add(condition);

            // Create Issuer
            SAMLObjectBuilder issuerBuilder = (SAMLObjectBuilder) SAMLWriter.getSAMLBuilder().getBuilder(Issuer.DEFAULT_ELEMENT_NAME);
            Issuer issuer = (Issuer) issuerBuilder.buildObject();
            issuer.setValue(input.getStrIssuer());

            // Create the assertion
            SAMLObjectBuilder assertionBuilder = (SAMLObjectBuilder) SAMLWriter.getSAMLBuilder().getBuilder(Assertion.DEFAULT_ELEMENT_NAME);
            Assertion assertion = (Assertion) assertionBuilder.buildObject();
            assertion.setIssuer(issuer);
            assertion.setIssueInstant(now);
            assertion.setVersion(SAMLVersion.VERSION_20);

            assertion.getAuthnStatements().add(authnStatement);
            assertion.getAttributeStatements().add(attrStatement);
            assertion.setConditions(conditions);

            return assertion;
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public static class SAMLInputContainer{
        private String strIssuer;
        private String strNameID;
        private String strNameQualifier;
        private String sessionId;
        private int maxSessionTimeoutInMinutes = 15; // default is 15 minutes

        private Map<String,String> attributes;

        public String getStrIssuer(){
            return strIssuer;
        }

        public void setStrIssuer(String strIssuer){
            this.strIssuer = strIssuer;
        }

        public String getStrNameID(){
            return strNameID;
        }

        public void setStrNameID(String strNameID){
            this.strNameID = strNameID;
        }

        public String getStrNameQualifier() {
            return strNameQualifier;
        }

        public void setStrNameQualifier(String strNameQualifier){
            this.strNameQualifier = strNameQualifier;
        }

        public void setAttributes(Map<String,String> attributes){
            this.attributes = attributes;
        }

        public Map<String,String> getAttributes(){
            return attributes;
        }
        public void setSessionId(String sessionId){
            this.sessionId = sessionId;
        }

        public String getSessionId(){
            return sessionId;
        }

        public void setMaxSessionTimeoutInMinutes(int maxSessionTimeoutInMinutes){
            this.maxSessionTimeoutInMinutes = maxSessionTimeoutInMinutes;
        }

        public int getMaxSessionTimeoutInMinutes(){
            return maxSessionTimeoutInMinutes;
        }
    }
}

