//package com.ncr.tpvbuddy.util;
//
//import com.sun.org.apache.xml.internal.security.signature.SignedInfo;
//import com.sun.org.apache.xml.internal.security.signature.XMLSignature;
//import org.joda.time.DateTime;
//import org.opensaml.Configuration;
//import org.opensaml.DefaultBootstrap;
//import org.opensaml.common.SAMLObjectBuilder;
//import org.opensaml.common.SAMLVersion;
//import org.opensaml.saml2.core.*;
//import org.opensaml.saml2.core.impl.AssertionMarshaller;
//import org.opensaml.xml.*;
//import org.opensaml.xml.encryption.CipherData;
//import org.opensaml.xml.encryption.EncryptedData;
//import org.opensaml.xml.encryption.EncryptionMethod;
//import org.opensaml.xml.encryption.EncryptionProperties;
//import org.opensaml.xml.io.MarshallingException;
//import org.opensaml.xml.schema.XSBooleanValue;
//import org.opensaml.xml.schema.XSString;
//import org.opensaml.xml.signature.KeyInfo;
//import org.opensaml.xml.signature.Signature;
//import org.opensaml.xml.signature.XMLSignatureBuilder;
//import org.opensaml.xml.signature.impl.KeyInfoBuilder;
//import org.opensaml.xml.signature.impl.SignatureBuilder;
//import org.opensaml.xml.util.IDIndex;
//import org.opensaml.xml.util.XMLHelper;
//import org.opensaml.xml.validation.ValidationException;
//import org.opensaml.xml.validation.Validator;
//import org.w3c.dom.Element;
//
//import javax.xml.namespace.QName;
//import java.util.*;
//
//
//public class SAMLWriter2 {
//    public static void main(String[] args) {
//        try {
//            SAMLInputContainer input = new SAMLInputContainer();
//            input.strIssuer = "http://synesty.com";
//            input.strNameID = "UserJohnSmith";
//            input.strNameQualifier = "My Website";
//            input.sessionId = "abcdedf1234567";
//
//
//            Map<String,String> customAttributes = new HashMap<String, String>();
//            customAttributes.put("FirstName", "John");
//            customAttributes.put("LastName", "Smith");
//            customAttributes.put("Email", "john.smith@yahoo.com");
//            customAttributes.put("PhoneNumber", "76373898998");
//            customAttributes.put("Locality", "USA");
//            customAttributes.put("Username", "John.Smith");
//
//            input.attributes = customAttributes;
//
//            Assertion assertion = SAMLWriter2.buildDefaultAssertion(input);
//            AssertionMarshaller marshaller = new AssertionMarshaller();
//            Element plaintextElement = marshaller.marshall(assertion);
//            String originalAssertionString = XMLHelper.nodeToString(plaintextElement);
//
//            System.out.println("Assertion String: " + originalAssertionString);
//
//            // TODO: now you can also add encryption....
//
//        } catch (MarshallingException e) {
//            // TODO Auto-generated catch block
//            e.printStackTrace();
//        }
//
//    }
//
//    private static XMLObjectBuilderFactory builderFactory;
//
//    public static XMLObjectBuilderFactory getSAMLBuilder() throws ConfigurationException{
//
//        if(builderFactory == null){
//            // OpenSAML 2.3
//            DefaultBootstrap.bootstrap();
//            builderFactory = Configuration.getBuilderFactory();
//        }
//
//        return builderFactory;
//    }
//
//    @SuppressWarnings("rawtypes")
//    public static Attribute buildStringAttribute(String name, String value, XMLObjectBuilderFactory builderFactory) throws ConfigurationException{
//        SAMLObjectBuilder attrBuilder = (SAMLObjectBuilder) getSAMLBuilder().getBuilder(Attribute.DEFAULT_ELEMENT_NAME);
//        Attribute attrFirstName = (Attribute) attrBuilder.buildObject();
//        attrFirstName.setName(name);
//
//        // Set custom Attributes
//        XMLObjectBuilder stringBuilder = getSAMLBuilder().getBuilder(XSString.TYPE_NAME);
//        XSString attrValueFirstName = (XSString) stringBuilder.buildObject(AttributeValue.DEFAULT_ELEMENT_NAME, XSString.TYPE_NAME);
//        attrValueFirstName.setValue(value);
//
//        attrFirstName.getAttributeValues().add(attrValueFirstName);
//        return attrFirstName;
//    }
//
//    /**
//     * Helper method which includes some basic SAML fields which are part of almost every SAML Assertion.
//     */
//    @SuppressWarnings("rawtypes")
//    public static Assertion buildDefaultAssertion(SAMLInputContainer input){
//        try {
//            // Create the NameIdentifier
//            SAMLObjectBuilder nameIdBuilder = (SAMLObjectBuilder) SAMLWriter2.getSAMLBuilder().getBuilder(NameID.DEFAULT_ELEMENT_NAME);
//            NameID nameId = (NameID) nameIdBuilder.buildObject();
//            nameId.setValue(input.getStrNameID());
//            nameId.setNameQualifier(input.getStrNameQualifier());
//            nameId.setFormat(NameID.UNSPECIFIED);
//
//            // Create the SubjectConfirmation
//
//            SAMLObjectBuilder confirmationMethodBuilder = (SAMLObjectBuilder)  SAMLWriter2.getSAMLBuilder().getBuilder(SubjectConfirmationData.DEFAULT_ELEMENT_NAME);
//            SubjectConfirmationData confirmationMethod = (SubjectConfirmationData) confirmationMethodBuilder.buildObject();
//            DateTime now = new DateTime();
//            confirmationMethod.setNotBefore(now);
//            confirmationMethod.setNotOnOrAfter(now.plusMinutes(2));
//
//            SAMLObjectBuilder subjectConfirmationBuilder = (SAMLObjectBuilder) SAMLWriter2.getSAMLBuilder().getBuilder(SubjectConfirmation.DEFAULT_ELEMENT_NAME);
//            SubjectConfirmation subjectConfirmation = (SubjectConfirmation) subjectConfirmationBuilder.buildObject();
//            subjectConfirmation.setSubjectConfirmationData(confirmationMethod);
//
//            // Create the Subject
//            SAMLObjectBuilder subjectBuilder = (SAMLObjectBuilder) SAMLWriter2.getSAMLBuilder().getBuilder(Subject.DEFAULT_ELEMENT_NAME);
//            Subject subject = (Subject) subjectBuilder.buildObject();
//
//            subject.setNameID(nameId);
//            subject.getSubjectConfirmations().add(subjectConfirmation);
//
//            // Testing starts
//
////            SignedInfoBu keyInfoBuilder = (KeyInfoBuilder) SAMLWriter2.getSAMLBuilder().getBuilder(SignedInfo);
////            KeyInfo keyInfo = (KeyInfo) keyInfoBuilder.buildObject();
////
////            org.apache.xml.security.signature.SignedInfo signedInfo = new org.apache.xml.security.signature.SignedInfo( );
////
////            KeyInfoBuilder keyInfoBuilder = (KeyInfoBuilder) SAMLWriter2.getSAMLBuilder().getBuilder(KeyInfo.DEFAULT_ELEMENT_NAME);
////            KeyInfo keyInfo = (KeyInfo) keyInfoBuilder.buildObject();
//
////            XMLSignatureBuilder xmlSignatureBuilder = (SAMLObjectBuilder) SAMLWriter2.getSAMLBuilder().getBuilder(XMLSignature);
////            KeyInfo keyInfo = (KeyInfo) keyInfoBuilder.buildObject();
//
//
//            SignatureBuilder signatureBuilder = (SignatureBuilder) SAMLWriter2.getSAMLBuilder().getBuilder(Signature.DEFAULT_ELEMENT_NAME);
//            Signature signature = (Signature) signatureBuilder.buildObject();
//
//            //signature.setKeyInfo(keyInfo);
//
//            signature.releaseDOM();
//
//
////            SAMLObjectBuilder encryptedAssertionBuilder = (SAMLObjectBuilder) SAMLWriter2.getSAMLBuilder().getBuilder(EncryptedAssertion.DEFAULT_ELEMENT_NAME);
////            EncryptedAssertion encryptedAssertion = (EncryptedAssertion) encryptedAssertionBuilder.buildObject();
////
////            encryptedAssertion.setEncryptedData(new EncryptedData() {
////                @Override
////                public String getID() {
////                    return null;
////                }
////
////                @Override
////                public void setID(String s) {
////
////                }
////
////                @Override
////                public String getType() {
////                    return null;
////                }
////
////                @Override
////                public void setType(String s) {
////
////                }
////
////                @Override
////                public String getMimeType() {
////                    return null;
////                }
////
////                @Override
////                public void setMimeType(String s) {
////
////                }
////
////                @Override
////                public String getEncoding() {
////                    return null;
////                }
////
////                @Override
////                public void setEncoding(String s) {
////
////                }
////
////                @Override
////                public EncryptionMethod getEncryptionMethod() {
////                    return null;
////                }
////
////                @Override
////                public void setEncryptionMethod(EncryptionMethod encryptionMethod) {
////
////                }
////
////                @Override
////                public KeyInfo getKeyInfo() {
////                    return null;
////                }
////
////                @Override
////                public void setKeyInfo(KeyInfo keyInfo) {
////
////                }
////
////                @Override
////                public CipherData getCipherData() {
////                    return null;
////                }
////
////                @Override
////                public void setCipherData(CipherData cipherData) {
////
////                }
////
////                @Override
////                public EncryptionProperties getEncryptionProperties() {
////                    return null;
////                }
////
////                @Override
////                public void setEncryptionProperties(EncryptionProperties encryptionProperties) {
////
////                }
////
////                @Override
////                public List<Validator> getValidators() {
////                    return null;
////                }
////
////                @Override
////                public void registerValidator(Validator validator) {
////
////                }
////
////                @Override
////                public void deregisterValidator(Validator validator) {
////
////                }
////
////                @Override
////                public void validate(boolean b) throws ValidationException {
////
////                }
////
////                @Override
////                public void addNamespace(Namespace namespace) {
////
////                }
////
////                @Override
////                public void detach() {
////
////                }
////
////                @Override
////                public Element getDOM() {
////                    return null;
////                }
////
////                @Override
////                public QName getElementQName() {
////                    return null;
////                }
////
////                @Override
////                public IDIndex getIDIndex() {
////                    return null;
////                }
////
////                @Override
////                public NamespaceManager getNamespaceManager() {
////                    return null;
////                }
////
////                @Override
////                public Set<Namespace> getNamespaces() {
////                    return null;
////                }
////
////                @Override
////                public String getNoNamespaceSchemaLocation() {
////                    return null;
////                }
////
////                @Override
////                public List<XMLObject> getOrderedChildren() {
////                    return null;
////                }
////
////                @Override
////                public XMLObject getParent() {
////                    return null;
////                }
////
////                @Override
////                public String getSchemaLocation() {
////                    return null;
////                }
////
////                @Override
////                public QName getSchemaType() {
////                    return null;
////                }
////
////                @Override
////                public boolean hasChildren() {
////                    return false;
////                }
////
////                @Override
////                public boolean hasParent() {
////                    return false;
////                }
////
////                @Override
////                public void releaseChildrenDOM(boolean b) {
////
////                }
////
////                @Override
////                public void releaseDOM() {
////
////                }
////
////                @Override
////                public void releaseParentDOM(boolean b) {
////
////                }
////
////                @Override
////                public void removeNamespace(Namespace namespace) {
////
////                }
////
////                @Override
////                public XMLObject resolveID(String s) {
////                    return null;
////                }
////
////                @Override
////                public XMLObject resolveIDFromRoot(String s) {
////                    return null;
////                }
////
////                @Override
////                public void setDOM(Element element) {
////
////                }
////
////                @Override
////                public void setNoNamespaceSchemaLocation(String s) {
////
////                }
////
////                @Override
////                public void setParent(XMLObject xmlObject) {
////
////                }
////
////                @Override
////                public void setSchemaLocation(String s) {
////
////                }
////
////                @Override
////                public Boolean isNil() {
////                    return null;
////                }
////
////                @Override
////                public XSBooleanValue isNilXSBoolean() {
////                    return null;
////                }
////
////                @Override
////                public void setNil(Boolean aBoolean) {
////
////                }
////
////                @Override
////                public void setNil(XSBooleanValue xsBooleanValue) {
////
////                }
////            });
//
//            // Testing ends
//
//            // Create Authentication Statement
//            SAMLObjectBuilder authStatementBuilder = (SAMLObjectBuilder) SAMLWriter2.getSAMLBuilder().getBuilder(AuthnStatement.DEFAULT_ELEMENT_NAME);
//            AuthnStatement authnStatement = (AuthnStatement) authStatementBuilder.buildObject();
//            //authnStatement.setSubject(subject);
//            //authnStatement.setAuthenticationMethod(strAuthMethod);
//            DateTime now2 = new DateTime();
//            authnStatement.setAuthnInstant(now2);
//            authnStatement.setSessionIndex(input.getSessionId());
//            authnStatement.setSessionNotOnOrAfter(now2.plus(input.getMaxSessionTimeoutInMinutes()));
//
//            SAMLObjectBuilder authContextBuilder = (SAMLObjectBuilder) SAMLWriter2.getSAMLBuilder().getBuilder(AuthnContext.DEFAULT_ELEMENT_NAME);
//            AuthnContext authnContext = (AuthnContext) authContextBuilder.buildObject();
//
//            SAMLObjectBuilder authContextClassRefBuilder = (SAMLObjectBuilder) SAMLWriter2.getSAMLBuilder().getBuilder(AuthnContextClassRef.DEFAULT_ELEMENT_NAME);
//            AuthnContextClassRef authnContextClassRef = (AuthnContextClassRef) authContextClassRefBuilder.buildObject();
//            authnContextClassRef.setAuthnContextClassRef("urn:oasis:names:tc:SAML:2.0:ac:classes:Password"); // TODO not sure exactly about this
//
//            authnContext.setAuthnContextClassRef(authnContextClassRef);
//            authnStatement.setAuthnContext(authnContext);
//
//            // Builder Attributes
//            SAMLObjectBuilder attrStatementBuilder = (SAMLObjectBuilder) SAMLWriter2.getSAMLBuilder().getBuilder(AttributeStatement.DEFAULT_ELEMENT_NAME);
//            AttributeStatement attrStatement = (AttributeStatement) attrStatementBuilder.buildObject();
//
//            // Create the attribute statement
//            Map attributes = input.getAttributes();
//            if(attributes != null){
//                Iterator keySet = attributes.keySet().iterator();
//                while (keySet.hasNext()){
//                    String key = keySet.next().toString();
//                    String val = attributes.get(key).toString();
//                    Attribute attrFirstName = buildStringAttribute(key, val, getSAMLBuilder());
//                    attrStatement.getAttributes().add(attrFirstName);
//                }
//            }
//
//            // Create the do-not-cache condition
//            SAMLObjectBuilder doNotCacheConditionBuilder = (SAMLObjectBuilder) SAMLWriter2.getSAMLBuilder().getBuilder(OneTimeUse.DEFAULT_ELEMENT_NAME);
//            Condition condition = (Condition) doNotCacheConditionBuilder.buildObject();
//
//            SAMLObjectBuilder conditionsBuilder = (SAMLObjectBuilder) SAMLWriter2.getSAMLBuilder().getBuilder(Conditions.DEFAULT_ELEMENT_NAME);
//            Conditions conditions = (Conditions) conditionsBuilder.buildObject();
//            conditions.getConditions().add(condition);
//
//            // Create Issuer
//            SAMLObjectBuilder issuerBuilder = (SAMLObjectBuilder) SAMLWriter2.getSAMLBuilder().getBuilder(Issuer.DEFAULT_ELEMENT_NAME);
//            Issuer issuer = (Issuer) issuerBuilder.buildObject();
//            issuer.setValue(input.getStrIssuer());
//
//            // Create the assertion
//            SAMLObjectBuilder assertionBuilder = (SAMLObjectBuilder) SAMLWriter2.getSAMLBuilder().getBuilder(Assertion.DEFAULT_ELEMENT_NAME);
//            Assertion assertion = (Assertion) assertionBuilder.buildObject();
//            assertion.setIssuer(issuer);
//            assertion.setIssueInstant(now);
//            assertion.setVersion(SAMLVersion.VERSION_20);
//
//            assertion.getAuthnStatements().add(authnStatement);
//            assertion.getAttributeStatements().add(attrStatement);
//            assertion.setConditions(conditions);
//
//            return assertion;
//        }
//        catch (Exception e) {
//            e.printStackTrace();
//        }
//        return null;
//    }
//
//    public static class SAMLInputContainer{
//        private String strIssuer;
//        private String strNameID;
//        private String strNameQualifier;
//        private String sessionId;
//        private int maxSessionTimeoutInMinutes = 15; // default is 15 minutes
//
//        private Map<String,String> attributes;
//
//        public String getStrIssuer(){
//            return strIssuer;
//        }
//
//        public void setStrIssuer(String strIssuer){
//            this.strIssuer = strIssuer;
//        }
//
//        public String getStrNameID(){
//            return strNameID;
//        }
//
//        public void setStrNameID(String strNameID){
//            this.strNameID = strNameID;
//        }
//
//        public String getStrNameQualifier() {
//            return strNameQualifier;
//        }
//
//        public void setStrNameQualifier(String strNameQualifier){
//            this.strNameQualifier = strNameQualifier;
//        }
//
//        public void setAttributes(Map<String,String> attributes){
//            this.attributes = attributes;
//        }
//
//        public Map<String,String> getAttributes(){
//            return attributes;
//        }
//        public void setSessionId(String sessionId){
//            this.sessionId = sessionId;
//        }
//
//        public String getSessionId(){
//            return sessionId;
//        }
//
//        public void setMaxSessionTimeoutInMinutes(int maxSessionTimeoutInMinutes){
//            this.maxSessionTimeoutInMinutes = maxSessionTimeoutInMinutes;
//        }
//
//        public int getMaxSessionTimeoutInMinutes(){
//            return maxSessionTimeoutInMinutes;
//        }
//    }
//}
//
