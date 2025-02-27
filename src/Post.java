import java.util.Objects;

/**
 * This class represents the posts
 * All posts have uniques IDs and contents
 * Also every post is created by a user
 */
public class Post implements Comparable<Post>{
    User author;
    String ID;
    int likeCount; // count of the likes
    String content;
    AVLTree<User> likes; // the users who liked the post

    /**
     * Constructor for post with only ID
     * @param ID of post
     */
    public Post(String ID) {
        this.ID = ID;
    }

    /**
     * Constructor for post
     * @param ID of post
     * @param content the content
     * @param author the user who created the post
     */
    public Post(String ID, String content, User author) {
        this.author = author;
        this.ID = ID;
        this.content = content;
        this.likes = new AVLTree<>();
        this.likeCount = 0;
    }

    /**
     * This method represents clicking the like button
     * @param user the user who clicked the like button
     * @return it returns true if user liked the post and vice versa
     */
    public boolean toggleLike(User user){
        if (likes.contains(user)){ // if user already liked the post
            likeCount -=1;
            likes.delete(user); // delete the user from likes
            return false;
        }
        else {
            likes.insert(user);
            likeCount += 1;
            return true;
        }
    }

    /**
     * This method compares the posts by their like counts
     * if their like counts are equals, it calls compareTo method
     * @param o the post to be compared.
     * @return if the like count is less than the other one return negative, otherwise return positive, if equals return compareTo method
     */
    public int compareToByLike(Post o) {
        if (likeCount < o.likeCount){
            return -1;
        } else if (likeCount > o.likeCount) {
            return 1;
        }
        else { return compareTo(o);
        }
    }

    /**
     * This method compares the post by their IDs lexicographically
     * @param o the post to be compared.
     * @return if the ID of the post is less than the other one return negative, otherwise return positive
     */
    @Override
    public int compareTo(Post o) {
        if (ID.compareTo(o.ID)<0){
            return -1;
        } else if (ID.compareTo(o.ID)>0) {
            return 1;
        }
        else return 0;
    }
}
