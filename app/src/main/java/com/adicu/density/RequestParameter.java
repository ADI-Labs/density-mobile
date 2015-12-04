package com.adicu.density;

import java.util.Date;

/**
 * Represents data that can be pulled from Density.
 */
public class RequestParameter {
    /**
     * Represents time intervals from which you can get Density data.
     *
     * LATEST gets the most recent data from Density.
     *
     * WINDOW gets the data within a time interval. You must specify a start and end date if using
     * WINDOW. Use the following date format:
     * The date should be given in Eastern Standard Time (EST). Additionally, the date must be
     * in ISO 8601 format: YYYY-MM-DD or YYYY-MM-DDThh:mm
     *
     * DAY gets the date for a day. You must specify a day using the following date format:
     *
     */
    public enum TimeInterval {
        LATEST("/latest"), WINDOW("/window"), DAY("/day");

        private String urlFragment;

        /**
         * Creates a TimeInterval with a URL fragment.
         * @param urlFragment the URL fragment used to get this information.
         */
        TimeInterval(String urlFragment) {
            this.urlFragment = urlFragment;
        }

        /**
         * Returns the URL fragment associated with this TimeInterval.
         * @return the URL fragment associated with this TimeInterval.
         */
        public String getURLFragment() {
            return urlFragment;
        }

        /**
         * Returns the URL fragment.
         * @return the URL fragment.
         */
        public String toString() {
            return urlFragment;
        }
    }

    /**
     * Represents the possible grouping options.
     *
     *
     * BUILDING represents one building.
     *
     * GROUP represents one router group.
     */
    public enum Grouping {
        BUILDING("/building"), GROUP("/group");

        private String urlFragment;

        /**
         * Creates a Grouping with a URL fragment.
         * @param urlFragment the URL fragment.
         */
        Grouping(String urlFragment) {
            this.urlFragment = urlFragment;
        }

        public String toString() {
            return urlFragment;
        }
    }

    /**
     * Represents the buildings you can get information from.
     * The currently allowed buildings include: Avery, Butler, East Asian Library, John Jay,
     * Lehman Library, Lerner, Northwest Corner Building, and Uris.
     */
    public enum Building {
        AVERY("Avery", 146), BUTLER("Butler", 103),  EAST_ASIAN("East Asian Library", 62),
        JOHN_JAY("John Jay", 75), LEHMAN("Lehman Library", 79), LERNER("Lerner", 84),
        NORTHWEST_CORNER("Northwest Corner Building", 15), URIS("Uris", 2);

        private String name;
        private int id;
        private String urlFragment;
        private Date openTime;
        private Date closeTime;

        /**
         * Creates a building with a name and id.
         * @param name the building name.
         * @param id the building id.
         */
        Building(String name, int id) {
            this.name = name;
            this.id = id;
            urlFragment = "/" + id;
        }

        /**
         * Returns the building name.
         * @return the building name.
         */
        public String getName() {
            return name;
        }

        /**
         * Retrusn the building ID.
         * @return the building ID.
         */
        public int getID() {
            return id;
        }

        /**
         * Returns the building ID.
         * @return the building ID.
         */
        public String toString() {
            return urlFragment;
        }
    }

    /**
     * Represents buildings that have multiple routers connected together.
     */
    public enum Group {
        BUTLER_301("Butler Library 301", 171),
        ARCHITECTURAL_1("Architectural and Fine Arts Library 1", 147),
        ARCHITECTURAL_2("Architectural and Fine Arts Library 2", 148),
        ARCHITECTURAL_3("Architectural and Fine Arts Library 3", 149),
        BUTLER_2("Butler Library 2", 130), BUTLER_3("Butler Library 3", 131),
        BUTLER_4("Butler Library 4", 132), BUTLER_5("Butler Library 5", 133),
        BUTLER_6("Butler Library 6", 134), BUTLER_STACK("Butler Library stk", 138),
        STARR("Starr East Asian Library", 1440), JJ("JJ's Place", 155),
        JOHN_JAY_DINING("John Jay Dining Hall", 125), LEHMAN_2("Lehman Library 2", 139),
        LEHMAN_3("Lehman Library 3", 140), LERNER_1("Lerner 1", 150), LERNER_2("Lerner 2", 151),
        LERNER_3("Lerner 3", 152), LERNER_4("Lerner 4", 153), LERNER_5("Lerner 5", 154),
        ROONE_ARLEDGE("Roone Arledge Auditorium", 85),
        SCIENCE_ENGINEERING("Science and Engineering Library", 145),
        URIS_WATSON("Uris/Watson Library", 23);

        private String name;
        private int id;
        private String urlFragment;

        /**
         * Creates a group with a name and id.
         * @param name the group name.
         * @param id the group id.
         */
        Group(String name, int id) {
            this.name = name;
            this.id = id;
            urlFragment = "/" + id;
        }

        /**
         * Returns the group name.
         * @return the group name.
         */
        public String getName() {
            return name;
        }

        /**
         * Returns the group id.
         * @return the group id.
         */
        public int getID() {
            return id;
        }

        public String getUrlFragment() {
            return urlFragment;
        }

        /**
         * Returns the group id.
         * @return the group id.
         */
        public String toString() {
            return urlFragment;
        }
    }
}
