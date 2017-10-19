package com.dapid.agent.models;

/**
 * Created for K and M Consulting LLC.
 * Created by Jose M Leon 2017
 **/
public interface ResultListener<S, T> {
    void notifyResult(S id, T exitCode) throws Exception;
}
