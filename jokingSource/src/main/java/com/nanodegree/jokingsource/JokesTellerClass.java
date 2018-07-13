package com.nanodegree.jokingsource;

import java.util.Random;

public class JokesTellerClass {


    private static String[] mJokes = {
            "Two bytes meet. The first byte asks, \"Are you ill?\"" + "\n" +
                    "The second byte replies, No, just feeling a bit off.",
            "How many programmers does it take to change a light bulb?" + "\n" +
                    "None It’s a hardware problem",
            "All programmers are playwrights, and all computers are lousy actors.",
            "I just saw my life flash before my eyes and all I could see was a close tag…",
            "Debugging: Removing the needles from the haystack.",
            "A SQL query goes to a restaurant, walks up to 2 tables and says \"Can I join you\" ?",
            "How to make a million dollars: First, get a million dollars."
    };


    public static String getRandomJoke(){
        return mJokes[new Random().nextInt(mJokes.length)];
    }
}
