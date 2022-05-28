package cn.com.xuxiaowei.authorizationserver.properties;

import cn.hutool.crypto.asymmetric.RSA;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.security.PrivateKey;
import java.security.PublicKey;

/**
 * RSA 秘钥对
 *
 * @author xuxiaowei
 * @see <a href="https://github.com/xuxiaowei-com-cn/RSA">RSA 非对称性加密、签名工具（需要调整为 2048 位）</a>
 * @since 0.0.1
 */
@Data
@Component
@ConfigurationProperties("rsa")
public class RsaKeyProperties {

    /**
     * 公钥
     */
    private String publicKey;

    /**
     * 私钥
     */
    private String privateKey;

    /**
     * 获取公钥
     *
     * @return 返回 公钥
     */
    public PublicKey rsaPublicKey() {
        RSA rsa = new RSA(null, publicKey);
        return rsa.getPublicKey();
    }

    /**
     * 获取私钥
     *
     * @return 返回 私钥
     */
    public PrivateKey rsaPrivateKey() {
        RSA rsa = new RSA(privateKey, null);
        return rsa.getPrivateKey();
    }

}
