package org.example.util;

import com.couchbase.client.java.Cluster;
import com.couchbase.client.java.Collection;
import com.couchbase.client.java.json.JsonObject;

public class CouchbaseUtil {
    private static final String COUCHBASE_CONNECTION_STRING = "couchbase://localhost";
    private static final String COUCHBASE_BUCKET_NAME = "test_bucket";

    public static Cluster connectToCluster(String username, String password) {
        return Cluster.connect(COUCHBASE_CONNECTION_STRING, username, password);
    }

    public static Collection getDefaultCollection(Cluster cluster) {
        return cluster.bucket(COUCHBASE_BUCKET_NAME).defaultCollection();
    }

    public static void upsertDocument(Collection collection, String documentId, String message) {
        JsonObject jsonObject = JsonObject.create().put("name", message);
        collection.upsert(documentId, jsonObject);
    }

    public static JsonObject getDocument(Collection collection, String documentId) {
        return collection.get(documentId).contentAsObject();
    }
}
