package pwr.pracainz.achievementlisteners;

import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Annotation for all achievement listeners. Each of the listeners is asynchronous.
 * <p>
 * It creates a new transaction for each listener.
 * <p>
 * Note! Remember to make the method (in services) in which a new event is published
 * transacional (with {@code @Transactional(propagation = Propagation.REQUIRED)})
 */
@Async
@EventListener
@Transactional(propagation = Propagation.REQUIRES_NEW)
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface AchievementListener { }
