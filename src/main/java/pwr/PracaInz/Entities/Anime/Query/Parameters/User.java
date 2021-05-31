package pwr.PracaInz.Entities.Anime.Query.Parameters;

import lombok.Getter;
import pwr.PracaInz.Entities.Anime.Query.Parameters.Connections.Reviews.Review;

import java.util.LinkedHashSet;
import java.util.Set;

@Getter
public class User {
    private final String userString;

    private User(String userString) {
        this.userString = userString;
    }

    public String getUserWithoutFieldName() {
        return this.userString.substring(5);
    }

    public static UserBuilder getUserBuilder() {
        return new UserBuilder();
    }

    public static final class UserBuilder {
        private final Set<ParameterString> user = new LinkedHashSet<>();

        public UserBuilder id() {
            user.add(new ParameterString("id\n"));
            return this;
        }

        public UserBuilder name() {
            user.add(new ParameterString("name\n"));
            return this;
        }

        public UserBuilder avatar() {
            user.add(new ParameterString("avatar {\nlarge\nmedium\n}\n"));
            return this;
        }

        public User buildUser() {
            if (user.isEmpty()) { throw new IllegalStateException("User should posses at least 1 parameter!"); }

            StringBuilder reviewBuilder = new StringBuilder("user {\n");

            user.forEach(reviewBuilder::append);

            reviewBuilder.append("}");

            return new User(reviewBuilder.toString());
        }
    }
}
