package com.example.streetlightfaultdetectionandlocationtracking;

import java.util.List;

public class ThingSpeakResponse {
    public Channel channel;
    public List<Feed> feeds;

    public static class Channel {
        public int id;
        public String name;
        // Add other fields as necessary
    }

    public static class Feed {
        public String created_at;
        public String field1;
        public String field2;
        public String field3;
        public String field4;
        // Add other fields as necessary
    }
}
