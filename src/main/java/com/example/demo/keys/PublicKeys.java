package com.example.demo.keys;

import com.fasterxml.jackson.annotation.JsonView;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

public class PublicKeys{
        public interface PublicKeysView{};
        @Id String userId;
        @JsonView(PublicKeysView.class)
        String identity;
        @JsonView(PublicKeysView.class)
        String preSignedKey;
        @JsonView(PublicKeysView.class)
        String signature;
        @JsonView(PublicKeysView.class)
        String[]oneTimeKeys;

        public PublicKeys(String userId, String identity, String preSignedKey, String signature, String[] oneTimeKeys) {
                this.userId = userId;
                this.identity = identity;
                this.preSignedKey = preSignedKey;
                this.signature = signature;
                this.oneTimeKeys = oneTimeKeys;
        }

        public String getUserId() {
                return userId;
        }

        public void setUserId(String userId) {
                this.userId = userId;
        }

        public String getIdentity() {
                return identity;
        }

        public void setIdentity(String identity) {
                this.identity = identity;
        }

        public String getPreSignedKey() {
                return preSignedKey;
        }

        public void setPreSignedKey(String preSignedKey) {
                this.preSignedKey = preSignedKey;
        }

        public String getSignature() {
                return signature;
        }

        public void setSignature(String signature) {
                this.signature = signature;
        }

        public String[] getOneTimeKeys() {
                return oneTimeKeys;
        }

        public void setOneTimeKeys(String[] oneTimeKeys) {
                this.oneTimeKeys = oneTimeKeys;
        }
}

