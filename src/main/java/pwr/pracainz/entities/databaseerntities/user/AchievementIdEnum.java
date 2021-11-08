package pwr.pracainz.entities.databaseerntities.user;

public enum AchievementIdEnum {
	NrOfPostsAchievement_1(1),
	NrOfPostsAchievement_10(2),
	NrOfPostsAchievement_50(3);

	public final int id;

	AchievementIdEnum(int id) { this.id = id; }
}
