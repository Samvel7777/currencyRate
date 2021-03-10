package am.itspace.restapi.service;

import am.itspace.restapi.model.LastLogin;
import am.itspace.restapi.repository.LastLoginRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Service
@RequiredArgsConstructor
public class LastLoginService {

    private final LastLoginRepository lastLoginRepository;

    @Value("${ip.stack.url}")
    private String baseUrl;
    @Value("${ip.stack.key}")
    private String key;

    public void lastLoginApi(HttpServletRequest request, int userId) {

        String ip = request.getHeader("x-real-ip");
        if (ip == null) {
            ip = request.getLocalAddr();
        }
        LastLogin responseLoginAddress = getCountryByIP(ip);
        if (responseLoginAddress != null) {
            saveLastLogin(responseLoginAddress, userId);
        }
    }

    private LastLogin getCountryByIP(String ip) {
        RestTemplate restTemplate = new RestTemplate();
        return restTemplate.getForObject(String.format(baseUrl, ip, key), LastLogin.class);
    }

    private void saveLastLogin(am.itspace.restapi.model.LastLogin lastLogin, int userId) {
        lastLoginRepository.save(new LastLogin(lastLogin, userId));
    }

    public List<LastLogin> findByUserId(int userId) {
        return lastLoginRepository.findByUserId(userId);
    }
}

