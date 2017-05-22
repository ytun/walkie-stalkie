//package com.ytun.walkie_stalkie;
//
//import com.restfb.Connection;
//import com.restfb.DefaultFacebookClient;
//import com.restfb.FacebookClient;
//import com.restfb.Parameter;
//import com.restfb.Version;
//import com.restfb.json.JsonObject;
//import com.restfb.types.User;
//
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//
//import java.util.Arrays;
//import java.util.List;
//
//public class Fb {
//
//    //	EAAGg7yaN8jABAE6lV5ayDAiQDmJ5F9Jtf0BgJt7MfmscplMJ8ILTYItwYTPm0O7L1vHBv7eUTUytrrdn98SHZCZB2Kl00BC9dIt8Mu0nlfA3kjbOoqKLE7faKi6IX6zjHOZC5Na4p0XwZA0DWT6KQny3pMl2RkfSG7bHZBZAL3gKIZBmy3dF3S8W3CO3ek9zSsZD
//    private static final String MY_ACCESS_TOKEN ="EAAGg7yaN8jABALVYsaYk51zCXASgObM2XZAKFtiGHy1fsxPO8zbC0lN3IZAq82grVROi1BaRSLyR1IORTlAbMguRXmNh7VWrNSEikGlWYZAQlM1MgzMfJrSZCBU3DhIl5QOYff7yzBlFsrTN4B8dA0CEzwpDi5EZD";
//
//    //	private static final String MY_ACCESS_TOKEN = "EAADdWsfVZAe0BAFLrG3rpJ87Cjh3SHXQD0k4XEDErpv33jCXX36j4ctRNA0oem7PgZBLYQ5hOf2MugnBslEcnEKwMy3nd9TYSWjdv5nWHaPGEq0uaGk6ZAsGZAoHfDk6WzQb48E1ytYnk9YyDpullO6K1YlTtvsG0pWFT4ZBKYQZDZD";
//    private static final FacebookClient facebookClient = new DefaultFacebookClient(MY_ACCESS_TOKEN, Version.VERSION_2_4);
//    private static final int LIMIT = 3;
//
//    //	private static final
//    static Logger LOGGER = LoggerFactory.getLogger(Fb.class);
//
//    static JsonObject result = new JsonObject();
//    //	private static final Logger LOGGER = Logger.getLogger(FB.class.getName(), FB.class);
//    //	http://restfb.com/documentation/
//	/*	RestFB supports paging, but you need to fetch the endpoint with the special FacebookClient.fetchConnection()
//	 *  method. One page of a pageable result set is called a connection. Now you can iterate over the connection and
//	 *  you’ll get a lot of lists of the fetched element. The loading of the next list is automatically done by RestFB
//	 *  so you don’t need to know how the URLs are built.
//	 */
//    public static User[] searchOnFb(String name){
//        //---- searching public
//       User[] resultArr = new User[LIMIT];
//
////        try{
//            Connection<User> searchResult = facebookClient.fetchConnection("search", User.class,
//                    Parameter.with("limit", LIMIT),
//                    Parameter.with("q", name),
//                    Parameter.with("type", "user"),
//                    Parameter.with("fields", "id,name,link"));//,gender,locale,timezone,updated_time,verified"));//,firstName,lastName,significantOther"));
//
//            int i = 0;
//            User user=null;
//
//            List<User> users= (searchResult.hasNext())? searchResult.getData(): null;
//            System.out.println("	usersize: "+ users.size()+"\n");
//
//
//
//            while(users!=null && i<LIMIT){
//                user= users.get(i);
//
//                resultArr[i]=user;
//
//                LOGGER.info(i + "\nName: "+ user.getName() + "\nLink: " + user.getLink());
//                LOGGER.debug(user.toString());
//                LOGGER.info("\n***LIST: "+ Arrays.toString(resultArr  ));
//                System.out.println("\n");
//
//                i++;
//            }
////        }
////        catch(Exception e){
////            LOGGER.error("Exception while fetching facebook search results, error_message=[{}], \nerror_stack=[{}]", e.getMessage(), e.getStackTrace());
////            return null;
////        }
//
//        return resultArr;
//    }
//
//    public static void image(){
//        try{
//            JsonObject picJson = facebookClient.fetchObject("me/picture", JsonObject.class, Parameter.with("redirect", "false"));
//
//            JsonObject data= (JsonObject) picJson.get("data");
//            System.out.println(data.getString("url", null));
////			URL url = new URL(picJson.get("data").get("url"));
//
//        }
//        catch(Exception e){
//            LOGGER.error("Exception while fetching facebook search results, error_message=[{}], \nerror_stack=[{}]", e.getMessage(), e.getStackTrace());
//        }
//
//        System.out.println();
//
//    }
//
//    public static void main(String[] args) {
////        image();
//
//        String name= "Mark Zuckerberg";
//
//        if(name.length()>0){ //& searchOnFb(name)){
//            User[] resultArr= searchOnFb(name);
//
//            for(int i=0; i<resultArr.length; i++){
//                LOGGER.debug(resultArr[i].getName());
//            }
//
//        }
//        //		searchPrivate();
//    }
//}
