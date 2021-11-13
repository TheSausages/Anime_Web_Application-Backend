package pwr.pracainz.entities.databaseerntities.user;

/**
 * Enum representing the Achievements in the database. It holds their ids for easier retrieval.
 */
public enum AchievementIdEnum {
	NrOfPostsAchievement_1(1),
	NrOfPostsAchievement_10(2),
	NrOfPostsAchievement_50(3);

	public final int id;

	AchievementIdEnum(int id) { this.id = id; }
}
