package com.ncr.tpvbuddy.util;

import com.onelogin.saml2.util.Util;
import org.apache.xml.security.exceptions.XMLSecurityException;
import org.w3c.dom.Document;

import javax.xml.xpath.XPathExpressionException;
import java.security.PrivateKey;
import java.security.cert.X509Certificate;

public class SignCertificate {
    // loads xml string into Document

    public static void main(String args[]){
        sign();
    }

    public static void sign() {
        String saml = "<saml:Assertion ID=\"pwbsxuI0eOaeBM2IJ-s4BPw2zTG\" IssueInstant=\"2022-02-22T14:59:59.793Z\" Version=\"2.0\"\n" +
                "                xmlns:saml=\"urn:oasis:names:tc:SAML:2.0:assertion\">\n" +
                "    <saml:Issuer>fairwinds.prod-intuit.ifs.prod</saml:Issuer>\n" +
                "    <saml:Subject/>\n" +
                "    <saml:NameID Format=\"urn:oasis:names:tc:SAML:1.1:nameid-format:unspecified\">36490188</saml:NameID>\n" +
                "    <saml:SubjectConfirmation Method=\"urn:oasis:names:tc:SAML:2.0:cm:bearer\"/>\n" +
                "    <saml:AuthnStatement AuthnInstant=\"2022-02-22T14:59:59.793Z\" SessionIndex=\"pwbsxuI0eOaeBM2IJ-s4BPw2zTG\">\n" +
                "        <saml:AuthnContext>\n" +
                "            <saml:AuthnContextClassRef>urn:oasis:names:tc:SAML:2.0:ac:classes:unspecified</saml:AuthnContextClassRef>\n" +
                "        </saml:AuthnContext>\n" +
                "    </saml:AuthnStatement>\n" +
                "    <saml:AttributeStatement>\n" +
                "        <saml:Attribute Name=\"USER_ACCOUNTS\" NameFormat=\"urn:oasis:names:tc:SAML:2.0:attrname-format:basic\">\n" +
                "            <saml:AttributeValue xsi:type=\"xs:string\" xmlns:xs=\"http://www.w3.org/2001/XMLSchema\"\n" +
                "                                 xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\">\n" +
                "                <![CDATA[<ns7:Accounts xmlns=\"http://schema.intuit.com/domain/banking/financialInfo/v2\" xmlns:ns2=\"http://schema.intuit.com/domain/banking/financialInstitution/v2\" xmlns:ns6=\"http://schema.intuit.com/domain/banking/notification/v2\" xmlns:ns7=\"http://schema.intuit.com/domain/banking/account/v2\"><ns7:account xsi:type=\"ns7:SAVINGS\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\"><ns7:id>IlujIIitfmdgpjop_8N_OaOXWgekh-k43VMyyHFAA4c</ns7:id><ns7:nickName>Membership Share</ns7:nickName><ns7:accountNumber><ns7:hostValue>75016305</ns7:hostValue><ns7:displayValue>75016305</ns7:displayValue><ns7:pfmValue>75016305</ns7:pfmValue><ns7:rdcAccountValue>75016305</ns7:rdcAccountValue><ns7:rawHostValue>75016305</ns7:rawHostValue></ns7:accountNumber></ns7:account><ns7:account xsi:type=\"ns7:CHECKING\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\"><ns7:id>5ip26bAoDWMd4Y8xZ0El1tX0VdrkIYFrI1lLarUh4Tw</ns7:id><ns7:nickName>Freedom Preferred Checking</ns7:nickName><ns7:accountNumber><ns7:hostValue>75016313</ns7:hostValue><ns7:displayValue>75016313</ns7:displayValue><ns7:billPayValue>75016313</ns7:billPayValue><ns7:pfmValue>75016313</ns7:pfmValue><ns7:rdcAccountValue>75016313</ns7:rdcAccountValue><ns7:rawHostValue>75016313</ns7:rawHostValue></ns7:accountNumber></ns7:account></ns7:Accounts>]]></saml:AttributeValue>\n" +
                "        </saml:Attribute>\n" +
                "        <saml:Attribute Name=\"USER_ID\" NameFormat=\"urn:oasis:names:tc:SAML:2.0:attrname-format:basic\">\n" +
                "            <saml:AttributeValue xsi:type=\"xs:string\" xmlns:xs=\"http://www.w3.org/2001/XMLSchema\"\n" +
                "                                 xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\">36490188\n" +
                "            </saml:AttributeValue>\n" +
                "        </saml:Attribute>\n" +
                "        <saml:Attribute Name=\"FI_ID\" NameFormat=\"urn:oasis:names:tc:SAML:2.0:attrname-format:basic\">\n" +
                "            <saml:AttributeValue xsi:type=\"xs:string\" xmlns:xs=\"http://www.w3.org/2001/XMLSchema\"\n" +
                "                                 xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\">DI1425\n" +
                "            </saml:AttributeValue>\n" +
                "        </saml:Attribute>\n" +
                "        <saml:Attribute Name=\"KEEP_ALIVE_URL\" NameFormat=\"urn:oasis:names:tc:SAML:2.0:attrname-format:basic\">\n" +
                "            <saml:AttributeValue xsi:type=\"xs:string\" xmlns:xs=\"http://www.w3.org/2001/XMLSchema\"\n" +
                "                                 xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\">\n" +
                "                https://www.fairwindsonline.com/site-olb/live/v1/launch?code=ping\n" +
                "            </saml:AttributeValue>\n" +
                "        </saml:Attribute>\n" +
                "    </saml:AttributeStatement>\n" +
                "</saml:Assertion>";

        Document document = Util.loadXML(saml);

        //String pubKeyBytes = "30820122300D06092A864886F70D01010105000382010F003082010A0282010100837C872F3D9C434C4A4CAC2FEB503109BDB8794AF8919DDC4A5AEFA46B9A1EE6FD5099F5953F7ED46DE7CBB5E092B1870176A070FD297BDA6D439501E5F398B20924672C36B8689A0E63AE02A6537B12786079761990FA32F5EF4BBE36E8B92A1AADC319B4721177B3566CD0A676458B580640C758E6103FE251A2A3613242DE68EDF46F6FD3AEC53828CD86018E40E95AEB2731D22BFDBDD4B94A1CB217075C358C17AF980ED9CD4DCBF1BC93FCF48B37F6103649B39CB8A24626148118F8CF0972D91371410D6270D03E2E48F007A4C007AF3013CD9F4116053276FAA0DFF3F441CE997D68181DCC9311BA591062F880486C7F620DAC754A8EE1F65FBFCEA30203010001";
        String pubKeyBytes = "";

        String privKeyBytes = "308204BC020100300D06092A864886F70D0101010500048204A6308204A20201000282010100837C872F3D9C434C4A4CAC2FEB503109BDB8794AF8919DDC4A5AEFA46B9A1EE6FD5099F5953F7ED46DE7CBB5E092B1870176A070FD297BDA6D439501E5F398B20924672C36B8689A0E63AE02A6537B12786079761990FA32F5EF4BBE36E8B92A1AADC319B4721177B3566CD0A676458B580640C758E6103FE251A2A3613242DE68EDF46F6FD3AEC53828CD86018E40E95AEB2731D22BFDBDD4B94A1CB217075C358C17AF980ED9CD4DCBF1BC93FCF48B37F6103649B39CB8A24626148118F8CF0972D91371410D6270D03E2E48F007A4C007AF3013CD9F4116053276FAA0DFF3F441CE997D68181DCC9311BA591062F880486C7F620DAC754A8EE1F65FBFCEA30203010001028201005BF2CB7E67B14D069EB6BB7ACF2DC6FD2EE5403D0352A17819AF3A025B14EE234E65FD2455F88F32F2A0B43E3E0B9785F7AA034CC05D3035714F5F76725E451BCB74DF8975C2C3B367F8A1094D52F7AFD4EA7245FA5EFDF8812F94623277D074F72F284E6377996B81C8205450F44871C75EA8123FC2B2FE59C008E203E86805AB58378670FBF15B925EEDD0B794565E4BC80E7A2A67B3F9D6746F1D642F2A95B984CFD51DB399733376A29959877D6BCECF81194B961BA1D1B2E04A8720101C748F1BFB0CA914B89A958702BC709ABF83941C8A2FAB88231CF2A92B588BE1017F2905BEF2C12FACECE23117F92ACE507DD43196DF5C1FFA631964EBA1EC51A102818100C496F1EFE838F9D4BC699630F44DF5325254DA85E43B2EC9C4C95F27D7DDCBF7885B4AEF980D50C7ED1F74D54A8372AD2C54C4C92576609A206104FAFEB1D130888D127685732166BCD8F77CD3FF45D247E228580EABE48A83F5ED66CC18FFFFBA609C461074953B60A73A7E7F3758CA2B240656A1C177271D7E55968E0A015B02818100AB38E8852E0421E65FDEE5EA2A032FCDF226FE835394EB3B479222339BA013178627546E537FDBCF66E5B86499787C407D45CC7A43B698FD77168D9494909B2A56114F1BB1B8CCAE8B35F3A6FDF166004860DEC9348616041B7ED476A0C6F1129427CBFB5E066952AC01D6876FF813E4A4A2E6538E4F515239B09DDD0CA9E259028180106197956B1F5C4A0ACD2D93B4B7CE47BC2E8FEAFA42A83A6DE86DC26F2D6F32982D2B485DBE364E02874D7FFC6E899EBE83EDC5E9163D247AB03AEF034C23E74C699CF13D5B839C00FE7F58EA7D69FF04B866AE37C2C10F7DC17F5FC2800ECCEB65E37FBF1D47D623CEAB8420EBA6BD69ECDD8E587A3B8889DF9E18268BD4E30281807424B31CFBDCD3BDF854337306B288AFD34D7E214905E016A2F2CE7E615750FC09050EB193B44C3F40961CE12875611B419EA218616C3014E6BE06A5BDACE583EF43E87C69AE683111BF341A977132F68822259D2822B8366B0C3A4489C4C8EC0851827D6D073E5454D71E555AAF559727D2541863950C1D91826B62F15F1F690281803E03BF52FE4C5816108FE25FADAA5D7EE483CA438913DF0DB2068B62197F107B00A1964D67BC15517A764FDB37F5A5129E4CE61EFDFF1BAD2C21903462EF5B4ABCC745267FC70C1A61FA2AC46221D41D3C965BD7A812BCFA113673C712CA197AC3D32BD6C1039AFB67A79E6CF97CDD14D5CD3B221227DE81E74047F3B6860E8A";
        // loads certificate and private key from string

        try {
            String cert = "-----BEGIN CERTIFICATE-----\n" +
                    "MIICgTCCAeoCCQCbOlrWDdX7FTANBgkqhkiG9w0BAQUFADCBhDELMAkGA1UEBhMC\n" +
                    "Tk8xGDAWBgNVBAgTD0FuZHJlYXMgU29sYmVyZzEMMAoGA1UEBxMDRm9vMRAwDgYD\n" +
                    "VQQKEwdVTklORVRUMRgwFgYDVQQDEw9mZWlkZS5lcmxhbmcubm8xITAfBgkqhkiG\n" +
                    "9w0BCQEWEmFuZHJlYXNAdW5pbmV0dC5ubzAeFw0wNzA2MTUxMjAxMzVaFw0wNzA4\n" +
                    "MTQxMjAxMzVaMIGEMQswCQYDVQQGEwJOTzEYMBYGA1UECBMPQW5kcmVhcyBTb2xi\n" +
                    "ZXJnMQwwCgYDVQQHEwNGb28xEDAOBgNVBAoTB1VOSU5FVFQxGDAWBgNVBAMTD2Zl\n" +
                    "aWRlLmVybGFuZy5ubzEhMB8GCSqGSIb3DQEJARYSYW5kcmVhc0B1bmluZXR0Lm5v\n" +
                    "MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQDivbhR7P516x/S3BqKxupQe0LO\n" +
                    "NoliupiBOesCO3SHbDrl3+q9IbfnfmE04rNuMcPsIxB161TdDpIesLCn7c8aPHIS\n" +
                    "KOtPlAeTZSnb8QAu7aRjZq3+PbrP5uW3TcfCGPtKTytHOge/OlJbo078dVhXQ14d\n" +
                    "1EDwXJW1rRXuUt4C8QIDAQABMA0GCSqGSIb3DQEBBQUAA4GBACDVfp86HObqY+e8\n" +
                    "BUoWQ9+VMQx1ASDohBjwOsg2WykUqRXF+dLfcUH9dWR63CtZIKFDbStNomPnQz7n\n" +
                    "bK+onygwBspVEbnHuUihZq3ZUdmumQqCw4Uvs/1Uvq3orOo/WJVhTyvLgFVK2Qar\n" +
                    "Q4/67OZfHd7R+POBXhophSMv1ZOo\n" +
                    "-----END CERTIFICATE-----";

            String certWithHeads = Util.formatCert(cert, true);
            X509Certificate certObject = Util.loadCert(certWithHeads);

            //X509Certificate cert = Util.loadCert(pubKeyBytes);



            //PrivateKey privateKey = Util.loadPrivateKey(privKeyBytes);

            PrivateKey privateKeyObject = Util.loadPrivateKey("-----BEGIN PRIVATE KEY-----\n" +
                    "MIICeAIBADANBgkqhkiG9w0BAQEFAASCAmIwggJeAgEAAoGBAOK9uFHs/nXrH9Lc\n" +
                    "GorG6lB7Qs42iWK6mIE56wI7dIdsOuXf6r0ht+d+YTTis24xw+wjEHXrVN0Okh6w\n" +
                    "sKftzxo8chIo60+UB5NlKdvxAC7tpGNmrf49us/m5bdNx8IY+0pPK0c6B786Uluj\n" +
                    "Tvx1WFdDXh3UQPBclbWtFe5S3gLxAgMBAAECgYAPj9ngtZVZXoPWowinUbOvRmZ1\n" +
                    "ZMTVI91nsSPyCUacLM92C4I+7NuEZeYiDRUnkP7TbCyrCzXN3jwlIxdczzORhlXB\n" +
                    "Bgg9Sw2fkV61CnDEMgw+aEeD5A0GDA6eTwkrawiOMs8vupjsi2/stPsa+bmpI6Rn\n" +
                    "fdEKBdyDP6iQQhAxiQJBAPNtM7IMvRzlZBXoDaTTpP9rN2FR0ZcX0LT5aRZJ81qi\n" +
                    "+ZOBFeHUb6MyWvzZKfPinj9JO3s/9e3JbMXemRWBmvcCQQDuc+NfAeW200QyjoC3\n" +
                    "Ed3jueLMrY1Q3zTcSUhRPw/0pIKgRGZJerro8N6QY2JziV2mxK855gKTwwBigMHL\n" +
                    "2S9XAkEAwuBfjGDqXOG/uFHn6laNNvWshjqsIdus99Tbrj5RlfP2/YFP9VTOcsXz\n" +
                    "VYy9K0P3EA8ekVLpHQ4uCFJmF3OEjQJBAMvwO69/HOufhv1CWZ25XzAsRGhPqsRX\n" +
                    "Eouw9XPfXpMavEm8FkuT9xXRJFkTVxl/i6RdJYx8Rwn/Rm34t0bUKqMCQQCrAtKC\n" +
                    "Un0PLcemAzPi8ADJlbMDG/IDXNbSej0Y4tw9Cdho1Q38XLZJi0RNdNvQJD1fWu3x\n" +
                    "9+QU/vJr7lMLzdoy\n" +
                    "-----END PRIVATE KEY-----");

            // signs the response
            String signedResponse;

            //signedResponse = Util.addSign(document, privateKey, cert, null);
            signedResponse = Util.addSign(document, privateKeyObject, certObject, null);
            System.out.println(" ############### Signed XML: \n" + signedResponse);
        } catch (XMLSecurityException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        } catch (XPathExpressionException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        } catch (Exception e){
            e.printStackTrace();
        }
    }


}
