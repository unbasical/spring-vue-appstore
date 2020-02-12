package base.appstore.config.security.voter;

import com.microsoft.applicationinsights.TelemetryClient;
import com.microsoft.applicationinsights.telemetry.Duration;
import com.microsoft.applicationinsights.telemetry.ExceptionTelemetry;
import com.microsoft.applicationinsights.telemetry.RemoteDependencyTelemetry;
import com.microsoft.applicationinsights.telemetry.RequestTelemetry;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.security.access.AccessDecisionVoter;
import org.springframework.security.access.ConfigAttribute;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.FilterInvocation;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.*;
import java.util.concurrent.TimeUnit;

public class OPAVoter implements AccessDecisionVoter<Object> {

    private final TelemetryClient telemetryClient;
    private final String opaUrl;
    private final boolean allowAll;

    public OPAVoter(String opaUrl, TelemetryClient client) {
        this.opaUrl = opaUrl;
        this.telemetryClient = client;
        this.allowAll = "true".equals(System.getenv("ALLOW_ALL"));
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

        // Add possibility to bridge OPA
        if (allowAll) {
            return ACCESS_GRANTED;
        }

        if (!(obj instanceof FilterInvocation)) {
            return ACCESS_ABSTAIN;
        }

        FilterInvocation filter = (FilterInvocation) obj;
        Map<String, String> headers = new HashMap<>();

        for (Enumeration<String> headerNames = filter.getRequest().getHeaderNames(); headerNames.hasMoreElements(); ) {
            String header = headerNames.nextElement();
            headers.put(header, filter.getRequest().getHeader(header));
        }

        String path = filter.getRequest().getRequestURI();

        Map<String, Object> input = new HashMap<>();
        input.put("method", filter.getRequest().getMethod());
        input.put("path", path);
        input.put("token", headers.getOrDefault("authorization", "n/a").replaceFirst("Bearer ", ""));


        OPADataResponse response;
        long start = System.nanoTime();
        try {
            RestTemplate client = new RestTemplate();
            HttpEntity<?> request = new HttpEntity<>(new OPADataRequest(input));
            response = client.postForObject(this.opaUrl, request, OPADataResponse.class);
            trackDependency(start, true);
        } catch (Throwable e) {
            trackDependency(start, false);
            trackError(e);
            throw e;
        }

        if (response == null || !response.getResult()) {
            return ACCESS_DENIED;
        }

        return ACCESS_GRANTED;
    }

    private void trackDependency(long start, boolean success) {
        long end = System.nanoTime();

        // Log metrics
        long deltaMillis = TimeUnit.NANOSECONDS.toMillis(end - start);
        RemoteDependencyTelemetry telemetry = new RemoteDependencyTelemetry();
        telemetry.setType("PC");
        telemetry.setName("Kelon");
        telemetry.setDuration(new Duration(deltaMillis));
        telemetry.setSuccess(success);
        telemetry.setTimestamp(new Date());
        telemetryClient.trackDependency(telemetry);
    }

    private void trackError(Throwable e) {
        ExceptionTelemetry telemetry = new ExceptionTelemetry();
        telemetry.setException(e);
        telemetry.setTimestamp(new Date());
        telemetryClient.trackException(telemetry);
    }

}
