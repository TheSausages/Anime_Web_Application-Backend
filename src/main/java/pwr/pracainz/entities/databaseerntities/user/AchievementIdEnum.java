package pwr.pracainz.entities.databaseerntities.user;

/**
 * Enum representing the Achievements in the database. It holds their ids for easier retrieval.
 */
public enum AchievementIdEnum {
	NrOfPostsAchievement_1(1),
	NrOfPostsAchievement_10(2),
	NrOfPostsAchievement_50(3),
	NrOfReviewsAchievement_1(4),
	NrOfReviewsAchievement_10(5),
	NrOfReviewsAchievement_50(6);

	public final int id;

	AchievementIdEnum(int id) { this.id = id; }
}
