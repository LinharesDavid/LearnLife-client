package model;

public class UserVote {

    private String _id;
    private String user;
    private String challenge;


    public UserVote(String _id, String user, String challenge) {
        this._id = _id;
        this.user = user;
        this.challenge = challenge;
    }

    public String get_id() {
        return _id;
    }

    public UserVote set_id(String _id) {
        this._id = _id;
        return this;
    }

    public String getUser() {
        return user;
    }

    public UserVote setUser(String user) {
        this.user = user;
        return this;
    }

    public String getChallenge() {
        return challenge;
    }

    public UserVote setChallenge(String challenge) {
        this.challenge = challenge;
        return this;
    }

    @Override
    public String toString() {
        return "UserVote{" +
                "_id='" + _id + '\'' +
                ", user='" + user + '\'' +
                ", challenge='" + challenge + '\'' +
                '}';
    }
}
