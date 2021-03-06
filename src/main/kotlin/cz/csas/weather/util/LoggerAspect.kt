package cz.csas.weather.util

import org.aspectj.lang.JoinPoint
import org.aspectj.lang.annotation.Aspect
import org.aspectj.lang.annotation.Before
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Component

/**
 * Component for creating aspect for logging
 */
@Component
@Aspect
class LoggerAspect {
    private val log: Logger = LoggerFactory.getLogger(this.javaClass)

    /**
     * Logs every entry to public method in packages cz.csas.weather.service or cz.csas.weather.controller in debug level
     */
    @Before(
        "execution(public * cz.csas.weather.service.*.*(..)) " +
                "|| execution(public * cz.csas.weather.controller.*.*(..))"
    )
    fun logExecutedMethod(joinPoint: JoinPoint?) {
        val sb = StringBuilder()

        //print class and method name
        val signature = joinPoint?.signature ?: return
        sb.append("Entering $signature")

        //print arguments
        val args = joinPoint.args
        if(args != null && args.isNotEmpty())
            sb.append(" with parameters: ${args.joinToString ( ", " )}")

        log.debug(sb.toString())
    }
}
