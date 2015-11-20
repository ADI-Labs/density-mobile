package com.adicu.density;

/**
 * Wrapper that holds relevant information from a DensityRequest.
 */
public class DensityData {
    private double mClientCount;
    private String mDumpTime;
    private int mGroupId;
    private String mGroupName;
    private int mParentId;
    private String mParentName;
    private double mPercentFull;

    /**
     * Creates one set of Density information.
     * @param clientCount the client count.
     * @param dumpTime the request time.
     * @param groupId the group id.
     * @param groupName the group name.
     * @param parentName the parent name.
     * @param percentFull the percentage fullness.
     */
    public DensityData(double clientCount, String dumpTime, int groupId, String groupName,
                       int parentId, String parentName, double percentFull) {
        mClientCount = clientCount;
        mDumpTime = dumpTime;
        mGroupId = groupId;
        mGroupName = groupName;
        mParentId = parentId;
        mParentName = parentName;
        mPercentFull = percentFull;
    }

    /**
     * Returns the client count.
     * @return the client count.
     */
    public double getClientCount() {
        return mClientCount;
    }

    /**
     * Returns the dump time.
     * @return the dump time.
     */
    public String getDumpTime() {
        return mDumpTime;
    }

    /**
     * Returns the group id.
     * @return the group id.
     */
    public int getGroupId() {
        return mGroupId;
    }

    /**
     * Returns the group name.
     * @return the group name.
     */
    public String getGroupName() {
        return mGroupName;
    }

    public int getParentId() {
        return mParentId;
    }

    /**
     * Returns the parent name.
     * @return the parent name.
     */
    public String getParentName() {
        return mParentName;
    }

    /**
     * Returns the percentage fullness.
     * @return the percentage fullness.
     */
    public double getPercentFull() {
        return mPercentFull;
    }
}
