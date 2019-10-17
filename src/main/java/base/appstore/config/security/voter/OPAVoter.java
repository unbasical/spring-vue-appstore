package base.appstore.config.security.voter;

import org.springframework.http.HttpEntity;
import org.springframework.security.access.AccessDecisionVoter;
import org.springframework.security.access.ConfigAttribute;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.FilterInvocation;
import org.springframework.web.client.RestTemplate;

import java.util.Collection;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;

public class OPAVoter implements AccessDecisionVoter<Object> {

    private String opaUrl;

    public OPAVoter(String opaUrl) {
        this.opaUrl = opaUrl;
    }

    @Override
    public boolean supports(ConfigAttribute attribute) {
        return true;
    }

    @Override
    public boolean supports(Class clazz) {
        return true;
    }

    @Override
    public int vote(Authentication authentication, Object obj, Collection<ConfigAttribute> attrs) {

        if (!(obj instanceof FilterInvocation)) {
            return ACCESS_ABSTAIN;
        }

        FilterInvocation filter = (FilterInvocation) obj;
        Map<String, String> headers = new HashMap<>();

        for (Enumeration<String> headerNames = filter.getRequest().getHeaderNames(); headerNames.hasMoreElements();) {
            String header = headerNames.nextElement();
            headers.put(header, filter.getRequest().getHeader(header));
        }

        String path = filter.getRequest().getRequestURI();

        Map<String, Object> input = new HashMap<>();
        input.put("method", filter.getRequest().getMethod());
        input.put("path", path);
        input.put("token", headers.getOrDefault("authorization", "n/a").replaceFirst("Bearer ", ""));

        RestTemplate client = new RestTemplate();
        HttpEntity<?> request = new HttpEntity<>(new OPADataRequest(input));
        OPADataResponse response = client.postForObject(this.opaUrl, request, OPADataResponse.class);

        if (response == null || !response.getResult()) {
            return ACCESS_DENIED;
        }

        return ACCESS_GRANTED;
    }

}
