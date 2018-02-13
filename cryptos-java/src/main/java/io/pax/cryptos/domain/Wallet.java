package io.pax.cryptos.domain;

/**
 * Created by AELION on 06/02/2018.
 */

public interface Wallet {

    int getId();
    User getUser();
    //default Optional<User> getUser(){
        //return null;
    //}
    String getName();
}
