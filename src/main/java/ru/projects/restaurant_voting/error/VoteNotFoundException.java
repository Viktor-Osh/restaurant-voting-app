package ru.projects.restaurant_voting.error;

public class VoteNotFoundException extends AppException{
    public VoteNotFoundException(String msg) {
        super(msg);
    }
}
