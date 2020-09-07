package sbsdk.talentcard.bsnsdk.util.common;

import lombok.Data;

import java.security.PrivateKey;

@Data
public class UserCertInfo {
    String CSRPem;
    PrivateKey key;
}
