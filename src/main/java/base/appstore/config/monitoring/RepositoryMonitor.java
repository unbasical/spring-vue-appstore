package base.appstore.config.monitoring;

import com.microsoft.applicationinsights.TelemetryClient;
import com.microsoft.applicationinsights.telemetry.Duration;
import com.microsoft.applicationinsights.telemetry.ExceptionTelemetry;
import com.microsoft.applicationinsights.telemetry.RemoteDependencyTelemetry;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.concurrent.TimeUnit;

@Aspect
@Component
public class RepositoryMonitor {

    @Autowired
    TelemetryClient telemetryClient;

    @Around("execution(public !void org.springframework.data.repository.Repository+.*(..))")
    public Object measureMethodExecutionTime(ProceedingJoinPoint pjp) throws Throwable {
        // Measure execution
        long start = System.nanoTime();
        try {
            Object retval = pjp.proceed();
            trackDependency(start, true);
            return retval;
        } catch (Throwable e) {
            trackDependency(start, false);
            trackError(e);
            throw e;
        }

    }

    private void trackDependency(long start, boolean success) {
        long end = System.nanoTime();

        // Log metrics
        long deltaMillis = TimeUnit.NANOSECONDS.toMillis(end - start);
        RemoteDependencyTelemetry telemetry = new RemoteDependencyTelemetry();
        telemetry.setName("Datasource");
        telemetry.setDuration(new Duration(deltaMillis));
        telemetry.setType("PostgreSQL");
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
