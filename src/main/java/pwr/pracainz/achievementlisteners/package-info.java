/**
 * The <i>AchievementListeners</i> package holds all Achievement Listeners.
 * Each listener should be annotated with {@link pwr.pracainz.achievementlisteners.AchievementListener}.
 * <p>
 * Note! Remember to make the method (in services) in which a new event is published
 * transacional (with {@code @Transactional(propagation = Propagation.REQUIRED)})
 *
 * @since 1.0
 * @author Kacper Z.
 * @version 1
 */
package pwr.pracainz.achievementlisteners;