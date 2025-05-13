package gr.aueb.cf.mutu.util;

import gr.aueb.cf.mutu.Authentication;
import gr.aueb.cf.mutu.model.*;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.io.File;
import java.sql.Timestamp;
import java.time.Instant;
import java.time.LocalDate;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class HibernateSeed {
//    public static void main(String[] args) {
//        Transaction transaction = null;
//        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
//            transaction = session.beginTransaction();
//
//            // Create Interests
//            Interest bridge = new Interest("bridge");
//            Interest dnd = new Interest("dnd");
//            Interest music = new Interest("music");
//            Interest politics = new Interest("politics");
//            Interest sports = new Interest("sports");
//            Interest travelling = new Interest("travelling");
//            Interest boardgames = new Interest("boardgames");
//            Interest reading = new Interest("reading");
//            Interest manga = new Interest("manga");
//
//            session.persist(bridge);
//            session.persist(dnd);
//            session.persist(music);
//            session.persist(politics);
//            session.persist(sports);
//            session.persist(travelling);
//            session.persist(boardgames);
//            session.persist(reading);
//            session.persist(manga);
//
//            // Create Users
//            User userGina = new User();
//            userGina.setEmail("gina@gina.com");
//            userGina.setName("Gina");
//            userGina.setBirthday(LocalDate.of(1987, 9, 1));
//            userGina.setHashedPassword(Authentication.hashPassword("gina"));
//            userGina.setHeight(162);
//            userGina.setWeight(58);
//            userGina.setBio("Looking for laughs and a little adventure.");
//            userGina.setInterests(Stream.of(bridge, dnd).collect(Collectors.toSet()));
//            session.persist(userGina);
//
//            User userDora = new User();
//            userDora.setEmail("dora@dora.com");
//            userDora.setName("Dora");
//            userDora.setBirthday(LocalDate.of(1994, 8, 20));
//            userDora.setHashedPassword(Authentication.hashPassword("dora"));
//            userDora.setHeight(160);
//            userDora.setWeight(60);
//            userDora.setBio("Big fan of movies and lazy Sundays.");
//            userDora.setInterests(Stream.of(boardgames, dnd, sports).collect(Collectors.toSet()));
//            session.persist(userDora);
//
//            User userRodia = new User();
//            userRodia.setEmail("rodia@rodia.com");
//            userRodia.setName("Rodia");
//            userRodia.setBirthday(LocalDate.of(1991, 8, 20));
//            userRodia.setHashedPassword(Authentication.hashPassword("rodia"));
//            userRodia.setHeight(170);
//            userRodia.setWeight(75);
//            userRodia.setBio("Always up for a walk or trying new food.");
//            userRodia.setInterests(Stream.of(music, manga, boardgames, bridge, politics).collect(Collectors.toSet()));
//            session.persist(userRodia);
//
//            User userAndreas = new User();
//            userAndreas.setEmail("andreas@andreas.com");
//            userAndreas.setName("Andreas");
//            userAndreas.setBirthday(LocalDate.of(1991, 8, 20));
//            userAndreas.setHashedPassword(Authentication.hashPassword("andreas"));
//            userAndreas.setHeight(170);
//            userAndreas.setWeight(70);
//            userAndreas.setBio("Hopeless romantic with a sarcastic sense of humor and a weakness for dogs.");
//            userAndreas.setInterests(Stream.of(sports, boardgames, bridge, politics).collect(Collectors.toSet()));
//            session.persist(userAndreas);
//
//            User userAnna = new User();
//            userAnna.setEmail("anna@anna.com");
//            userAnna.setName("Anna");
//            userAnna.setBirthday(LocalDate.of(1990, 10, 16));
//            userAnna.setHashedPassword(Authentication.hashPassword("anna"));
//            userAnna.setHeight(174);
//            userAnna.setWeight(55);
//            userAnna.setBio("Enjoy quiet nights and great conversations.");
//            userAnna.setInterests(Stream.of(music, manga, reading, politics).collect(Collectors.toSet()));
//            session.persist(userAnna);
//
//            User userAlex = new User();
//            userAlex.setEmail("alex@alex.com");
//            userAlex.setName("Alex");
//            userAlex.setBirthday(LocalDate.of(1991, 6, 26));
//            userAlex.setHashedPassword(Authentication.hashPassword("alex"));
//            userAlex.setHeight(174);
//            userAlex.setWeight(70);
//            userAlex.setBio("Loves trying new cuisines and exploring hidden gems in the city.");
//            userAlex.setInterests(Stream.of(dnd, reading, boardgames, bridge, politics).collect(Collectors.toSet()));
//            session.persist(userAlex);
//
//            // Create Pictures
//
//            String projectDir = new File(".").getAbsolutePath() + "/";
//            String imgDir = projectDir + "img/";
//
//            session.persist(new Picture("pic1", ImageLoader.loadImage( imgDir + "gina-profile-pic.jpg").getBytes(), 0, userGina));
//
//            session.persist(new Picture("pic1", ImageLoader.loadImage( imgDir + "rodia-profile-pic.jpg").getBytes(), 0, userRodia));
//            session.persist(new Picture("pic2", ImageLoader.loadImage( imgDir + "rodia-pic2.jpg").getBytes(), 0, userRodia));
//            session.persist(new Picture("pic4", ImageLoader.loadImage( imgDir + "rodia-pic4.jpg").getBytes(), 0, userRodia));
//
//            session.persist(new Picture("pic1", ImageLoader.loadImage( imgDir + "dora-profile-pic.jpg").getBytes(), 0, userDora));
//            session.persist(new Picture("pic2", ImageLoader.loadImage( imgDir + "dora-pic2.jpg").getBytes(), 0, userDora));
//            session.persist(new Picture("pic3", ImageLoader.loadImage( imgDir + "dora-pic3.jpg").getBytes(), 0, userDora));
//
//            session.persist(new Picture("pic1", ImageLoader.loadImage( imgDir + "andreas-profile-pic.jpg").getBytes(), 0, userAndreas));
//            session.persist(new Picture("pic2", ImageLoader.loadImage( imgDir + "andreas-pic2.jpg").getBytes(), 0, userAndreas));
//            session.persist(new Picture("pic3", ImageLoader.loadImage( imgDir + "andreas-pic3.jpg").getBytes(), 0, userAndreas));
//            session.persist(new Picture("pic4", ImageLoader.loadImage( imgDir + "andreas-pic4.jpg").getBytes(), 0, userAndreas));
//            session.persist(new Picture("pic5", ImageLoader.loadImage( imgDir + "andreas-pic5.jpg").getBytes(), 0, userAndreas));
//
//            session.persist(new Picture("pic1", ImageLoader.loadImage( imgDir + "anna-pic1.jpg").getBytes(), 0, userAnna));
//            session.persist(new Picture("pic2", ImageLoader.loadImage( imgDir + "anna-pic2.jpg").getBytes(), 0, userAnna));
//            session.persist(new Picture("pic3", ImageLoader.loadImage( imgDir + "anna-pic3.jpg").getBytes(), 0, userAnna));
//
//            session.persist(new Picture("pic1", ImageLoader.loadImage( imgDir + "alex-pic1.jpg").getBytes(), 0, userAlex));
//            session.persist(new Picture("pic2", ImageLoader.loadImage( imgDir + "alex-pic2.jpg").getBytes(), 0, userAlex));
//            session.persist(new Picture("pic3", ImageLoader.loadImage( imgDir + "alex-pic3.jpg").getBytes(), 0, userAlex));
//
//            // Create UserActions
//            session.persist(new UserAction(userGina, userDora, UserAction.Action.SWIPE_RIGHT, UserAction.Action.SWIPE_RIGHT));
//            session.persist(new UserAction(userAndreas, userDora, UserAction.Action.SWIPE_RIGHT, UserAction.Action.SWIPE_RIGHT));
//            session.persist(new UserAction(userAndreas, userGina, UserAction.Action.SWIPE_RIGHT, UserAction.Action.SWIPE_RIGHT));
//
//            // Create Messages
//            session.persist(new Message(userGina, userAndreas, "Hi, how are you?", Timestamp.from(Instant.now())));
//
//            session.persist(new Message(userGina, userDora, "Hello there!", Timestamp.from(Instant.now())));
//            session.persist(new Message(userGina, userDora, "Are you free tonight?", Timestamp.from(Instant.now())));
//            session.persist(new Message(userAndreas, userDora, "Hi!", Timestamp.from(Instant.now())));
//            session.persist(new Message(userDora, userAndreas, "How are you?", Timestamp.from(Instant.now())));
//
//            transaction.commit();
//
//        } catch (Exception e) {
//            if (transaction != null) {
//                transaction.rollback();
//            }
//            e.printStackTrace();
//        }
//
//        HibernateUtil.shutdown();
//    }
}
