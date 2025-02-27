/**
 * This class represents the users in the program
 * It has all the functions related to user
 */
public class User implements Comparable<User> {
    String ID; // user ID
    int followCount; // number of followed people
    AVLTree<Post> posts; // post that are shared by user
    AVLTree<User> followed; // the other users that are followed
    AVLTree<Post> seenPosts;
    MaxHeap feed; // feed of user
    MaxHeap sortedPosts; // list of sorted posts

    /**
     * Constructor for user
     * @param ID is unique for all users
     */
    public User(String ID) {
        this.ID = ID;
        this.followCount = 0;
        this.posts = new AVLTree<>();
        this.followed = new AVLTree<>();
        this.seenPosts = new AVLTree<>();
        this.sortedPosts = new MaxHeap(new Post[1+posts.size]);
    }

    // user follows another user
    public void follow(User anotherUser){
        followed.insert(anotherUser);
        followCount += 1;
    }

    // user unfollows another user
    public void unfollow(User anotherUser){
        followed.delete(anotherUser);
        followCount -=1;
    }
    // user posts something
    public void post(Post post){
        posts.insert(post);
    }
    // user sees the given post
    public void seePost(Post post){
        seenPosts.insert(post);
    }
    // user sees the all posts of the given user
    public void seeAllPosts(User viewed){
        traversePost(viewed.posts.root);
    }
    // generate feed that contains the posts of followed users
    public void generateFeed(){
        generateFeed(followed.root);
    }
    // recursive method that travels all the followed users and their posts
    private void generateFeed(AvlNode<User> user){
        if (user == null)
            return;
        traversePostsForFeed(user.key.posts.root); // traverse all the posts of current user
        generateFeed(user.left); // go left subtree
        generateFeed(user.right); // go right subtree
    }
    // recursive method to traverse all the posts of given user
    private void traversePostsForFeed(AvlNode<Post> viewed){
        if (viewed == null)
            return;
        if (!seenPosts.contains(viewed.key)){ // if the post is already seen, it cannot br in the feed
            feed.insert(viewed.key);
        }
        traversePostsForFeed(viewed.left);
        traversePostsForFeed(viewed.right);
    }

    // sort all the posts
    public void sort(){
        traversePostsForSort(posts.root);
    }

    // recursive method to traverse and sort the posts
    private void traversePostsForSort(AvlNode<Post> viewed){
        if (viewed == null)
            return;
        sortedPosts.insert(viewed.key);
        traversePostsForSort(viewed.left);
        traversePostsForSort(viewed.right);
    }

    // recursive method to traverse all the posts and add them to the seen posts
    private void traversePost(AvlNode<Post> viewed){
        if (viewed == null)
            return;
        seenPosts.insert(viewed.key);
        traversePost(viewed.left);
        traversePost(viewed.right);
    }

    // compare method to compare users by their ID's lexicographically
    // it is used for trees
    @Override
    public int compareTo(User o) {
        if (ID.compareTo(o.ID)<0){
            return -1;
        } else if (ID.compareTo(o.ID)>0) {
            return 1;
        }
        else return 0;
    }

}
