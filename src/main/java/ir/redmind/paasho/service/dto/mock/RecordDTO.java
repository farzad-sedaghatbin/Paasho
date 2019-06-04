package ir.redmind.paasho.service.dto.mock;

import java.util.List;

/**
 * Created by farzad on 8/10/2017.
 */
public class RecordDTO {
    public int rank;
    public List<User> users;
    public int score;
    private String prizeDescription;
    private String help;

    public String getPrizeDescription() {
        return prizeDescription;
    }

    public void setPrizeDescription(String prizeDescription) {
        this.prizeDescription = prizeDescription;
    }

    public String getHelp() {
        return help;
    }

    public void setHelp(String help) {
        this.help = help;
    }


    public static class User {
        public String avatar;
        public long score;
        public int index;
        public String user;

        public String getAvatar() {
            return avatar;
        }

        public long getScore() {
            return score;
        }

        public void setScore(long score) {
            this.score = score;
        }

        public int getIndex() {
            return index;
        }

        public void setIndex(int index) {
            this.index = index;
        }

        public String getUser() {
            return user;
        }

        public void setUser(String user) {
            this.user = user;
        }

        public void setAvatar(String s) {
            this.avatar=s;

        }
    }
}
