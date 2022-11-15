package cn.com.xuxiaowei.authorizationserver;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.Module;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.security.jackson2.SecurityJackson2Modules;
import org.springframework.security.oauth2.server.authorization.client.JdbcRegisteredClientRepository;
import org.springframework.security.oauth2.server.authorization.jackson2.OAuth2AuthorizationServerJackson2Module;
import org.springframework.security.oauth2.server.authorization.settings.ClientSettings;
import org.springframework.security.oauth2.server.authorization.settings.ConfigurationSettingNames;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

/**
 * {@link ObjectMapper} 测试类
 *
 * @author xuxiaowei
 * @since 0.0.1
 */
@Slf4j
class ObjectMapperTests {

    @Test
    void map() throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();

        ClassLoader classLoader = JdbcRegisteredClientRepository.class.getClassLoader();
        List<Module> securityModules = SecurityJackson2Modules.getModules(classLoader);
        objectMapper.registerModules(securityModules);
        objectMapper.registerModule(new OAuth2AuthorizationServerJackson2Module());

        Map<String, Object> map = new HashMap<>();
        map.put(ConfigurationSettingNames.Client.REQUIRE_PROOF_KEY, true);

        String value = objectMapper.writeValueAsString(map);
        log.info(value);
    }

    @Test
    void clientSettings() throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();

        ClassLoader classLoader = JdbcRegisteredClientRepository.class.getClassLoader();
        List<Module> securityModules = SecurityJackson2Modules.getModules(classLoader);
        objectMapper.registerModules(securityModules);
        objectMapper.registerModule(new OAuth2AuthorizationServerJackson2Module());

        ClientSettings.Builder builder = ClientSettings.builder();
        ClientSettings clientSettings = builder.build();

        String value = objectMapper.writeValueAsString(clientSettings);

        log.info(value);
    }

    @Test
    void securityModules() throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();

        ClassLoader classLoader = JdbcRegisteredClientRepository.class.getClassLoader();
        List<Module> securityModules = SecurityJackson2Modules.getModules(classLoader);
        objectMapper.registerModules(securityModules);
        objectMapper.registerModule(new OAuth2AuthorizationServerJackson2Module());

        Map<String, String> map = new HashMap<>();
        map.put("uuid", UUID.randomUUID().toString());

        String value = objectMapper.writeValueAsString(map);
        log.info(value);
    }

}
