import java.io.*;
import java.util.Arrays;

public class Main {
    public static void main(String[] args) throws FileNotFoundException {
        AVLTree<User> allUsers = new AVLTree<>(); // the tree that stores all the users
        AVLTree<Post> allPosts = new AVLTree<>(); // the tree that stores all the posts
        BufferedReader reader = new BufferedReader(new FileReader("type2_large.txt")); // read the input file
        String filePath = args[1]; // Specify your output file path
        File file = new File(filePath); // create new file
        try {
            // Check if the file exists
            if (!file.exists()) { // Create the file if it does not exist
                if (!file.createNewFile()){ // check if the file is created
                    return;
                }
            }
            BufferedWriter writer = new BufferedWriter(new FileWriter(file)); // create new writer
            String command; // create a string to assign the lines
            while((command = reader.readLine()) != null){ // until the text is read to the end
                String[] commandArray = command.split(" "); // split the words in the lines by spaces
                switch (commandArray[0]){  // check what is the command
                    case ("create_user"):
                        User newUser = new User(commandArray[1]); // create a new user by given ID
                        if (!allUsers.contains(newUser)) { // if the user was not created before
                            allUsers.insert(newUser); // add the user to all users
                            writer.write("Created user with Id " + commandArray[1] + ".");
                            writer.write('\n');
                        }
                        else{
                            writer.write("Some error occurred in create_user.");
                            writer.write('\n');
                        }
                        break;
                    case ("follow_user"):
                        User user1 = allUsers.find(allUsers.root, new User(commandArray[1])); // find the user1
                        User user2 = allUsers.find(allUsers.root, new User(commandArray[2])); // find the user2
                        if (user1 == null || user2 == null || user1 == user2 ){ // if either of the users do not exist or they are the same user
                            writer.write("Some error occurred in follow_user.");
                            writer.write('\n');

                        }
                        else if (user1.followed.contains(user2)){ // if user1 tries to follow user2 second time
                            writer.write("Some error occurred in follow_user.");
                            writer.write('\n');
                        }
                        else{
                            user1.follow(user2); // user1 follows user2
                            writer.write(user1.ID+" followed " + user2.ID +".");
                            writer.write('\n');
                        }
                        break;

                    case ("unfollow_user"):
                        User user3 = allUsers.find(allUsers.root, new User(commandArray[1])); // find the user3
                        User user4 = allUsers.find(allUsers.root, new User(commandArray[2])); // find the user4
                        if (user3 == null || user4 == null || user3 == user4){ // if either of the users do not exist or they are the same user
                            writer.write("Some error occurred in unfollow_user.");
                            writer.write('\n');
                        }
                        else if (!user3.followed.contains(user4)){ // if the user3 did not follow user4
                            writer.write("Some error occurred in unfollow_user.");
                            writer.write('\n');
                        }
                        else{
                            user3.unfollow(user4); // user3 unfollows user4
                            writer.write(user3.ID+" unfollowed " + user4.ID +".");
                            writer.write('\n');
                        }
                        break;
                    case("create_post"):
                        User user = allUsers.find(allUsers.root,new User(commandArray[1])); // find the user that will create a post
                        if (user == null){ // if the user does not exist
                            writer.write("Some error occurred in create_post.");
                            writer.write('\n');
                            break;
                        }
                        Post newPost = new Post(commandArray[2],commandArray[3],user); // create a new post
                        if (allPosts.contains(newPost)){ //  if the posts have been created before
                            writer.write("Some error occurred in create_post.");
                            writer.write('\n');
                        }
                        else{
                            user.post(newPost); // user posts the new post
                            allPosts.insert(newPost); // add the post to all posts
                            writer.write(commandArray[1] + " created a post with Id " + commandArray[2] + ".");
                            writer.write('\n');
                        }
                        break;
                    case ("see_post"):
                        User viewer = allUsers.find(allUsers.root,new User(commandArray[1])); // find the viewer
                        Post viewed = allPosts.find(allPosts.root,new Post(commandArray[2])); // find the viewed post
                        if (viewer == null || viewed == null){  // if either of them does not exist
                            writer.write("Some error occurred in see_post.");
                            writer.write('\n');
                        }
                        else {
                            viewer.seePost(viewed); // viewer sees the post
                            writer.write( commandArray[1] + " saw " + commandArray[2] + ".");
                            writer.write('\n');
                        }
                        break;
                    case ("see_all_posts_from_user"):
                        User viewer1 = allUsers.find(allUsers.root,new User(commandArray[1])); // find the viewer
                        User viewedUser = allUsers.find(allUsers.root,new User(commandArray[2])); // find the viewed user
                        if (viewer1 == null || viewedUser == null){ // if either of them does not exist
                            writer.write("Some error occurred in see_all_posts_from_user.");
                            writer.write('\n');
                            break;
                        }
                        else {
                            viewer1.seeAllPosts(viewedUser); // viewer sees all posts of viewed user
                            writer.write( commandArray[1] + " saw all posts of " + commandArray[2]+ ".");
                            writer.write('\n');
                        }
                        break;
                    case ("toggle_like"):
                        Post likedpost = allPosts.find(allPosts.root,new Post(commandArray[2])); // find the post that will be liked
                        User user5 = allUsers.find(allUsers.root,new User(commandArray[1])); // find the user that will like the post
                        if (likedpost == null || user5 == null){ // if the post or the user does not exist
                            writer.write("Some error occurred in toggle_like.");
                            writer.write('\n');
                            break;
                        }
                        else {
                            boolean liked_unliked = likedpost.toggleLike(user5); // click the like button
                            user5.seePost(likedpost); // the user sees the post
                            if (liked_unliked){ // if the user liked the post
                                writer.write(commandArray[1] + " liked " + commandArray[2] +".");
                                writer.write('\n');
                            }
                            else { // if the user unliked the post
                                writer.write(commandArray[1] + " unliked " + commandArray[2] +".");
                                writer.write('\n');
                            }
                        }
                        break;
                    case ("generate_feed"):
                        MaxHeap heap2 = new MaxHeap(new Post[1+Integer.parseInt(commandArray[2])]); // create a max heap with given number of feed
                        User user6 = allUsers.find(allUsers.root,new User(commandArray[1])); // find the user
                        if (user6 == null){ // if the user does not exist
                            writer.write("Some error occurred in generate_feed.");
                            writer.write('\n');
                            break;
                        }
                        user6.feed = heap2; // set the users feed to heap
                        user6.generateFeed(); // generate feed for user
                        MaxHeap heap = new MaxHeap(user6.feed.currentSize,Arrays.copyOf(user6.feed.table,user6.feed.table.length)); // copy the user's feed
                        writer.write("Feed for " + commandArray[1] +":");
                        writer.write('\n');
                        for (int i = 1; i < 1+ Integer.parseInt(commandArray[2]); i++) { // repeat the given number of times
                            Post post = heap.deleteMax(); // delete the max item
                            if (post == null){ // if number of posts is not enough
                                writer.write("No more posts available for " + commandArray[1] + ".");
                                writer.write('\n');
                                break;
                            }
                            writer.write("Post ID: " + post.ID + ", " + "Author: " + post.author.ID + ", " + "Likes: " + post.likeCount);
                            writer.write('\n');
                        }
                        break;
                    case ("scroll_through_feed"):
                        MaxHeap heap1 = new MaxHeap(new Post[1+Integer.parseInt(commandArray[2])]); // create a max heap with given number of feed
                        User user7 = allUsers.find(allUsers.root,new User(commandArray[1])); // find the user
                        if (user7 == null){ // if the user does not exist
                            writer.write("Some error occurred in scroll_through_feed.");
                            writer.write('\n');
                            break;
                        }
                        user7.feed = heap1; // set the users feed
                        user7.generateFeed();
                        writer.write(user7.ID + " is scrolling through feed:");
                        writer.write('\n');
                        MaxHeap feed = user7.feed; // retrieve the feed of user
                        int num = Integer.parseInt(commandArray[2]);
                        for (int i = 3; i < num + 3 ; i++) { // read the line after the first 3 words
                            int likeCondition = Integer.parseInt(commandArray[i]); // if user likes or dislikes the post in the feed
                            Post mostLiked = feed.deleteMax(); // retrieve the most liked post in the feed
                            if (mostLiked == null){ // if feed is ended
                                writer.write("No more posts in feed.");
                                writer.write('\n');
                                break;
                            }
                            user7.seePost(mostLiked);
                            if (likeCondition == 0){ // if user do not clicked the like button
                                writer.write(user7.ID + " saw "+ mostLiked.ID + " while scrolling.");
                                writer.write('\n');
                            }
                            else if (likeCondition == 1){ // if user clicked the like button
                                mostLiked.toggleLike(user7);
                                writer.write(user7.ID + " saw "+ mostLiked.ID + " while scrolling and clicked the like button.");
                                writer.write('\n');
                            }

                        }
                        break;
                    case ("sort_posts"):
                        User user8 = allUsers.find(allUsers.root,new User(commandArray[1])); // find the user
                        if (user8 == null){ // if the user does not exist
                            writer.write("Some error occurred in sort_posts.");
                            writer.write('\n');
                            break;
                        }
                        if (user8.posts.size == 0){ // if user did not post anything
                            writer.write("No posts from " + commandArray[1]);
                            writer.write('\n');
                            break;
                        }
                        writer.write("Sorting " + commandArray[1] +"'s posts:");
                        writer.write('\n');
                        user8.sort(); // sort the posts of the user
                        for (int i = 0; i < user8.posts.size; i++) { // until the all post are sorted
                            Post post = user8.sortedPosts.deleteMax(); // get most liked post
                            writer.write(post.ID + ", Likes: " + post.likeCount);
                            writer.write('\n');
                        }
                        break;

                }
                writer.flush();
            }
            writer.close();
        } catch (IOException e) { // if the input file does not exist
            System.err.println("An error occurred: " + e.getMessage());
        }
    }

}