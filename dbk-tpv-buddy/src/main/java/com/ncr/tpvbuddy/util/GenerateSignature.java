//package com.ncr.tpvbuddy.util;
//
//public class GenerateSignature {
//    public void generateXMLDigitalSignature(String originalXmlFilePath,
//                                            String destnSignedXmlFilePath, String privateKeyFilePath, String publicKeyFilePath) {
////Get the XML Document object
//        Document doc = getXmlDocument(originalXmlFilePath);
////Create XML Signature Factory
//        XMLSignatureFactory xmlSigFactory = XMLSignatureFactory.getInstance("DOM");
//        PrivateKey privateKey = new KryptoUtil().getStoredPrivateKey(privateKeyFilePath);
//        DOMSignContext domSignCtx = new DOMSignContext(privateKey, doc.getDocumentElement());
//        Reference ref = null;
//        SignedInfo signedInfo = null;
//        try {
//            ref = xmlSigFactory.newReference("", xmlSigFactory.newDigestMethod(DigestMethod.SHA1, null),
//                    Collections.singletonList(xmlSigFactory.newTransform(Transform.ENVELOPED,
//                            (TransformParameterSpec) null)), null, null);
//            signedInfo = xmlSigFactory.newSignedInfo(
//                    xmlSigFactory.newCanonicalizationMethod(CanonicalizationMethod.INCLUSIVE,
//                            (C14NMethodParameterSpec) null),
//                    xmlSigFactory.newSignatureMethod(SignatureMethod.RSA_SHA1, null),
//                    Collections.singletonList(ref));
//        } catch (NoSuchAlgorithmException ex) {
//            ex.printStackTrace();
//        } catch (InvalidAlgorithmParameterException ex) {
//            ex.printStackTrace();
//        }
////Pass the Public Key File Path
//        KeyInfo keyInfo = getKeyInfo(xmlSigFactory, publicKeyFilePath);
////Create a new XML Signature
//        XMLSignature xmlSignature = xmlSigFactory.newXMLSignature(signedInfo, keyInfo);
//        try {
////Sign the document
//            xmlSignature.sign(domSignCtx);
//        } catch (MarshalException ex) {
//            ex.printStackTrace();
//        } catch (XMLSignatureException ex) {
//            ex.printStackTrace();
//        }
////Store the digitally signed document inta a location
//        storeSignedDoc(doc, destnSignedXmlFilePath);
//    }
//}
